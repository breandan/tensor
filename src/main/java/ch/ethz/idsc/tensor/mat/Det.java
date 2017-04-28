// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorRank;

/** implementation is not consistent with Mathematica when matrix is empty:
 * <code>Mathematica::Det[{{}}]</code> results in Exception
 * but our implementation gives
 * <code>Det[{{}}] == 0</code>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Det.html">Det</a> */
public enum Det {
  ;
  /** @param matrix
   * @return determinant of matrix */
  public static Scalar of(Tensor matrix) {
    if (isMatrix(matrix))
      return _of(matrix);
    throw TensorRuntimeException.of(matrix);
  }

  /** @param matrix square matrix
   * @return determinant of m */
  public static Scalar withoutAbs(Tensor matrix) {
    if (isMatrix(matrix))
      return _of(matrix);
    throw TensorRuntimeException.of(matrix);
  }

  /** @param tensor
   * @return true if tensor has array structure and rank 2 */
  private static boolean isMatrix(Tensor tensor) {
    return Dimensions.isArray(tensor) && TensorRank.of(tensor) == 2;
  }

  // helper function
  private static Scalar _of(Tensor matrix) {
    if (matrix.length() == matrix.get(0).length()) // square
      try {
        return new GaussianElimination(matrix, Array.zeros(matrix.length()), Pivot.firstNonZero).det();
      } catch (Exception exception) {
        // matrix is singular
      }
    return ZeroScalar.get();
  }
}