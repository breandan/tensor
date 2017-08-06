// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.UnitVector;
import junit.framework.TestCase;

public class SquareMatrixQTest extends TestCase {
  public void testMatrix() {
    assertTrue(SquareMatrixQ.of(IdentityMatrix.of(10)));
    assertFalse(SquareMatrixQ.of(Array.zeros(3, 4)));
  }

  public void testVector() {
    assertFalse(SquareMatrixQ.of(UnitVector.of(10, 3)));
  }
}