// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.ArgInterface;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ConjugateInterface;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.ImagInterface;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.RealInterface;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** complex number
 * 
 * <p>number() or Comparable interface is not supported */
public interface ComplexScalar extends Scalar, //
    ArgInterface, ConjugateInterface, ChopInterface, ImagInterface, NInterface, //
    RealInterface, SqrtInterface {
  /** complex number I == 0+1*I */
  static final Scalar I = of(0, 1);

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part
   * @throws Exception if re or im are {@link ComplexScalar} */
  static Scalar of(Scalar re, Scalar im) {
    return im.equals(ZeroScalar.get()) ? re : new ComplexScalarImpl(re, im);
  }

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part */
  static Scalar of(Number re, Number im) {
    return of(RealScalar.of(re), RealScalar.of(im));
  }

  /** @param abs radius
   * @param arg angle
   * @return complex scalar with polar coordinates abs and arg */
  static Scalar fromPolar(Scalar abs, Scalar arg) {
    return abs.multiply(of(Cos.function.apply(arg), Sin.function.apply(arg)));
  }
}
