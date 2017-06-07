// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Mod;
import ch.ethz.idsc.tensor.sca.Ramp;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class QuantityScalar1Test extends TestCase {
  public void testSimple() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(5), "m", RealScalar.ONE);
    assertTrue(Scalars.isZero(qs1.add(qs2).subtract(qs3)));
  }

  public void testNorm1() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(-3), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(-4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(7), "m", RealScalar.ONE);
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm._1.of(vec), qs3);
  }

  public void testNorm2() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(5), "m", RealScalar.ONE);
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm._2.of(vec), qs3);
  }

  public void testNormInf() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(-3), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(-4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm.Infinity.of(vec), qs3);
  }

  public void testSqrt() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(9), "m", RealScalar.of(2));
    Scalar qs2 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.of(1));
    assertEquals(Sqrt.of(qs1), qs2);
  }

  public void testScale() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(5), "m", RealScalar.ONE);
    Tensor vec = Tensors.of(qs1, qs2, qs3);
    Tensor sca = vec.multiply(RealScalar.of(3));
    assertEquals(sca.toString(), "{9[m^1], 12[m^1], 15[m^1]}");
  }

  public void testMultiply() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(-2), "s", RealScalar.ONE);
    assertEquals(qs1.multiply(qs2).toString(), "-6[m^1,s^1]");
  }

  public void testDivide() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(12), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ZERO);
    assertEquals(qs1.divide(qs2), qs3);
    assertTrue(qs3 instanceof RationalScalar);
  }

  public void testArcTan() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(12), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    Scalar qs0 = QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE);
    ArcTan.of(qs1, qs2);
    ArcTan.of(qs0, qs1);
  }

  public void testInvert() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(4), "m", RealScalar.ONE);
    assertEquals(qs1.invert().toString(), "1/4[m^-1]");
  }

  public void testCompare() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(3), "m", RealScalar.ONE);
    assertTrue(Scalars.lessThan(qs1, qs2));
  }

  public void testRamp() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(-2), "m", RealScalar.ONE);
    assertEquals(Ramp.of(qs1), qs1);
    assertEquals(Ramp.of(qs2), qs1.zero());
  }

  public void testRound() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(2.333), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(2), "m", RealScalar.ONE);
    assertEquals(Round.of(qs1), qs2);
  }

  public void testHypot() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(0), "m", RealScalar.ONE);
    assertEquals(Hypot.bifunction.apply(qs1, qs2), qs1);
  }

  public void testDiagon() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(2), "s", RealScalar.ONE);
    Tensor vec = Tensors.of(qs1, qs2);
    DiagonalMatrix.of(vec);
    // System.out.println(Pretty.of(mat));
  }

  public void testMod() {
    Scalar qs1 = QuantityScalar.of(RealScalar.of(5), "s", RealScalar.ONE);
    Scalar qs2 = QuantityScalar.of(RealScalar.of(3), "s", RealScalar.ONE);
    Scalar qs3 = QuantityScalar.of(RealScalar.of(2), "s", RealScalar.ONE);
    Scalar res = Mod.function(qs2).apply(qs1);
    // System.out.println(res);
    assertEquals(res, qs3);
  }
}
