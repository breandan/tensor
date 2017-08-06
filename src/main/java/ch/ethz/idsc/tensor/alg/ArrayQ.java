// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** ArrayQ is <em>not</em> consistent with Mathematica for scalar input:
 * <pre>
 * Mathematica::ArrayQ[3] == False
 * Tensor::ArrayQ[Scalar] == True
 * </pre>
 * 
 * <p>The tensor library considers {@link Scalar}s
 * to be Tensors with regular (=array) structure of rank 0.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayQ.html">ArrayQ</a> */
public enum ArrayQ {
  ;
  /** Examples:
   * ArrayQ[17] == True
   * ArrayQ[{{1, 2, 1}, {3, 4, 7}}] == True
   * ArrayQ[{1, 2, {3}, 4}] == False
   * 
   * @return true if tensor structure is identical at all levels, else false.
   * true for {@link Scalar}s */
  public static boolean of(Tensor tensor) {
    return Dimensions.isArray(tensor);
  }

  /** @param tensor
   * @param rank
   * @return true, if tensor is an array of given rank
   * @see VectorQ
   * @see MatrixQ
   * @see TensorRank */
  public static boolean ofRank(Tensor tensor, int rank) {
    return Dimensions.isArrayWithRank(tensor, rank);
  }
}