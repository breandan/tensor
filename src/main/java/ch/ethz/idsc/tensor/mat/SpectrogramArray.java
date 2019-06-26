// code by ob, jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.PadRight;
import ch.ethz.idsc.tensor.alg.Partition;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sqrt;
import ch.ethz.idsc.tensor.sca.win.DirichletWindow;

/** Mathematica::SpectrogramArray has the option to multiply the data segments with a window function,
 * with {@link DirichletWindow} as the default. The implementation in the tensor library uses the fixed
 * choice of {@link DirichletWindow}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/SpectrogramArray.html">SpectrogramArray</a> */
public class SpectrogramArray implements TensorUnaryOperator {
  private static final ScalarUnaryOperator LOG2 = Log.base(2);

  /** Mathematica default
   * 
   * @param vector
   * @return
   * @throws Exception if input is not a vector */
  public static Tensor of(Tensor vector) {
    int num = Scalars.intValueExact(Round.FUNCTION.apply(LOG2.apply(Sqrt.FUNCTION.apply(RealScalar.of(vector.length())))));
    int windowLength = 1 << ++num;
    return of(windowLength, Scalars.intValueExact(Round.FUNCTION.apply(RationalScalar.of(windowLength, 3)))).apply(vector);
  }

  /***************************************************/
  /** @param windowDuration
   * @param samplingFrequency
   * @param offset positive
   * @return */
  public static TensorUnaryOperator of(Scalar windowDuration, Scalar samplingFrequency, int offset) {
    return of(Scalars.intValueExact(Round.FUNCTION.apply(windowDuration.multiply(samplingFrequency))), offset);
  }

  /** @param windowLength
   * @param offset positive and not greater than windowLength
   * @return */
  public static TensorUnaryOperator of(int windowLength, int offset) {
    if (offset <= 0 || windowLength < offset)
      throw new RuntimeException(windowLength + " " + offset);
    return new SpectrogramArray(windowLength, offset);
  }

  // ---
  private final int windowLength;
  private final int offset;
  private final TensorUnaryOperator tensorUnaryOperator;

  private SpectrogramArray(int windowLength, int offset) {
    this.windowLength = windowLength;
    this.offset = offset;
    int highestOneBit = Integer.highestOneBit(windowLength);
    tensorUnaryOperator = windowLength == highestOneBit //
        ? t -> t //
        : PadRight.zeros(highestOneBit * 2);
  }

  @Override // from TensorUnaryOperator
  public Tensor apply(Tensor vector) {
    return Tensor.of(Partition.stream(vector, windowLength, offset) //
        .map(tensorUnaryOperator) //
        .map(Fourier::of));
  }
}