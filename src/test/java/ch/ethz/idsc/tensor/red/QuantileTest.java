// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class QuantileTest extends TestCase {
  public void testMultiple() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    Tensor q = Quantile.of(vector, Tensors.fromString("{0,1/5,2/5,3/5,4/5,1}"));
    Tensor r = Tensors.vector(0, 0, 1, 2, 3, 4);
    assertEquals(q, r);
  }

  public void testScalar() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    Tensor q = Quantile.of(vector, RealScalar.of(.71233));
    assertEquals(q, RealScalar.of(3));
  }

  public void testBounds() {
    Tensor vector = Tensors.vector(0, 2, 1, 4, 3);
    try {
      Quantile.ofSorted(vector, RealScalar.of(1.01));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantile.ofSorted(vector, RealScalar.of(-0.01));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLimitTheorem() {
    Random rnd = new Random();
    Tensor tensor = Array.of(l -> RealScalar.of(rnd.nextDouble()), 5000);
    Tensor weight = Tensors.vector(.76, .1, .25, .5, .05, .95, 0, .5, .99, 1);
    Tensor quantile = Quantile.of(tensor, weight);
    Tensor deviation = quantile.subtract(weight);
    Scalar maxError = Norm.INFINITY.of(deviation);
    assertTrue(Scalars.lessThan(maxError, RealScalar.of(0.05)));
  }
}