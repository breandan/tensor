// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

public enum ExtractPrimitives {
  ;
  public static Stream<Number> vectorToStreamNumber(Tensor tensor) {
    return tensor.flatten(0) //
        .map(RealScalar.class::cast) //
        .map(RealScalar::number);
  }

  public static List<Double> vectorToListDouble(Tensor tensor) {
    return vectorToStreamNumber(tensor) //
        .map(Number::doubleValue).collect(Collectors.toList());
  }

  public static double[] vectorToArrayDouble(Tensor tensor) {
    return vectorToStreamNumber(tensor) //
        .mapToDouble(Number::doubleValue).toArray();
  }

  /** does not perform rounding, but uses Number::longValue
   * 
   * @param tensor
   * @return */
  public static List<Long> vectorToListLong(Tensor tensor) {
    return vectorToStreamNumber(tensor) //
        .map(Number::longValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Number::longValue
   * 
   * @param tensor
   * @return */
  public static long[] vectorToArrayLong(Tensor tensor) {
    return vectorToStreamNumber(tensor) //
        .mapToLong(Number::longValue).toArray();
  }

  /** does not perform rounding, but uses Number::intValue
   * 
   * @param tensor
   * @return */
  public static List<Integer> vectorToListInteger(Tensor tensor) {
    return vectorToStreamNumber(tensor) //
        .map(Number::intValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Number::intValue
   * 
   * @param tensor
   * @return */
  public static int[] vectorToArrayInt(Tensor tensor) {
    return vectorToStreamNumber(tensor) //
        .mapToInt(Number::intValue).toArray();
  }
}