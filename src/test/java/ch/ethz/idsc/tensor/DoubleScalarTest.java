// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class DoubleScalarTest extends TestCase {
  public void testAdd() {
    ZeroScalar.get().hashCode();
    Tensor a = DoubleScalar.of(1.23);
    Tensor b = DoubleScalar.of(2.3);
    assertTrue(a.add(b).equals(b.add(a)));
    Tensor c = DoubleScalar.of(1.23 + 2.3);
    assertTrue(a.add(b).equals(c));
  }

  public void testChop() {
    Scalar s = DoubleScalar.of(3.14);
    assertEquals(Chop.of(s), s);
    Scalar r = DoubleScalar.of(1e-14);
    assertEquals(Chop.of(r), ZeroScalar.get());
    assertEquals(Chop.of(ZeroScalar.get()), ZeroScalar.get());
  }

  public void testEquality() {
    assertEquals(RealScalar.ONE, DoubleScalar.of(1));
    assertEquals(DoubleScalar.of(1), RationalScalar.of(1, 1));
    assertEquals(DoubleScalar.of(1), IntegerScalar.of(1));
  }

  public void testInf() {
    Scalar inf = DoubleScalar.of(Double.POSITIVE_INFINITY);
    Scalar c = RealScalar.of(-2);
    assertEquals(inf.multiply(c), inf.negate());
    assertEquals(c.multiply(inf), inf.negate());
    Scalar nan = inf.multiply(ZeroScalar.get());
    assertTrue(Double.isNaN(nan.number().doubleValue()));
  }

  public void testMin() {
    Scalar a = RealScalar.of(3);
    Scalar b = RealScalar.of(7.2);
    assertEquals(Min.of(a, b), a);
  }

  public void testMax1() {
    Scalar a = RealScalar.of(3);
    Scalar b = RealScalar.of(7.2);
    assertEquals(Max.of(a, b), b);
  }

  public void testMax2() {
    Scalar a = RealScalar.of(0);
    Scalar b = RealScalar.of(7.2);
    assertEquals(Max.of(a, b), b);
  }

  public void testCompareFail() {
    Scalar a = RealScalar.of(7.2);
    Scalar b = GaussScalar.of(3, 5);
    try {
      Max.of(a, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Max.of(b, a);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}