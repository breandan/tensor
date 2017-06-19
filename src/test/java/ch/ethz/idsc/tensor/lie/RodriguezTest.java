// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.MatrixExp;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RodriguezTest extends TestCase {
  private static void checkDiff(Tensor c) {
    Tensor d = Rodriguez.of(c).subtract(MatrixExp.of(Cross.of(c)));
    assertEquals(Chop.of(d), Array.zeros(3, 3));
  }

  public void testFormula() {
    checkDiff(Tensors.vector(-.2, .1, .3));
    checkDiff(Tensors.vector(-.5, -.1, .03));
    checkDiff(Tensors.vector(-.3, -.2, .1));
    checkDiff(Tensors.vector(-.3, -.2, -.3));
  }

  public void testFail() {
    try {
      Rodriguez.of(Tensors.vector(0, 0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRotZ() {
    Tensor matrix = Rodriguez.of(Tensors.vector(0, 0, 1));
    assertEquals(matrix.get(2, 0), RealScalar.ZERO);
    assertEquals(matrix.get(2, 1), RealScalar.ZERO);
    assertEquals(matrix.get(0, 2), RealScalar.ZERO);
    assertEquals(matrix.get(1, 2), RealScalar.ZERO);
    assertEquals(matrix.get(2, 2), RealScalar.ONE);
  }
}
