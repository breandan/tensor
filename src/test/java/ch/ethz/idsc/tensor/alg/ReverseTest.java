// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class ReverseTest extends TestCase {
  public void testRev() {
    Tensor tensor = Tensors.vectorInt(3, 2, 6, 5);
    Tensor rev = Reverse.of(tensor);
    Tensor res = Tensors.vectorInt(5, 6, 2, 3);
    assertEquals(rev, res);
  }

  public void testReverse() {
    Random r = new Random();
    int n = 5;
    Tensor m = Array.of(index -> RealScalar.of(r.nextInt(100)), n, n, n, n);
    Tensor v = Reverse.of(IdentityMatrix.of(n));
    Tensor t1 = BasisTransform.ofForm(m, v);
    Tensor t2 = Transpose.apply(m, Reverse::of);
    assertEquals(t1, t2);
  }
}
