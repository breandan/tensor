// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.BitSet;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MatrixPowerTest extends TestCase {
  private static void checkLow(Tensor m) {
    int n = m.length();
    assertEquals(MatrixPower.of(m, 0), IdentityMatrix.of(n));
    assertEquals(MatrixPower.of(m, 1), m);
    assertEquals(MatrixPower.of(m, -1), Inverse.of(m));
    assertEquals(MatrixPower.of(m, 2), m.dot(m));
    Tensor inv = Inverse.of(m);
    assertEquals(MatrixPower.of(m, -2), inv.dot(inv));
    assertEquals(MatrixPower.of(m, 3), m.dot(m).dot(m));
  }

  public void testHilbert() {
    checkLow(HilbertMatrix.of(4));
  }

  public void testFourier() {
    checkLow(FourierMatrix.of(3));
    checkLow(FourierMatrix.of(6));
  }

  public void testMathematicaEx() {
    assertEquals( //
        MatrixPower.of(Tensors.fromString("{{1, 1}, {1, 2}}"), 10), //
        Tensors.fromString("{{4181, 6765}, {6765, 10946}}") //
    );
  }

  public void testMathematicaInv2() {
    assertEquals( //
        MatrixPower.of(Tensors.fromString("{{1, 1}, {1, 2}}"), -2), //
        Tensors.fromString("{{5, -3}, {-3, 2}}") //
    );
  }

  static int log2Long(long n) {
    return 63 - Long.numberOfLeadingZeros(n);
  }

  static long powerOf(int x, long exp) {
    BitSet bitSet = BitSet.valueOf(new long[] { exp });
    long y = 1;
    for (int bitIndex = log2Long(exp); bitIndex >= 0; --bitIndex) {
      y = y * y;
      if (bitSet.get(bitIndex))
        y = y * x;
    }
    return y;
  }

  public void testSet() {
    assertEquals(powerOf(3, 5), 243);
    assertEquals(powerOf(2, 21), 2097152);
    assertEquals(powerOf(5, 6), 15625);
    assertEquals(powerOf(5, 0), 1);
  }
}
