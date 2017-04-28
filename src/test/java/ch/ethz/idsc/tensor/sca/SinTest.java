// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class SinTest extends TestCase {
  public void testReal() {
    Scalar i = RealScalar.of(2);
    Scalar c = Sin.function.apply(i);
    Scalar s = DoubleScalar.of(Math.sin(2));
    assertEquals(c, Sin.of(i));
    assertEquals(c, s);
  }

  public void testComplex() {
    Scalar c = Sin.function.apply(ComplexScalar.of(2, 3.));
    // 9.1544991469114295735 - 4.1689069599665643508 I
    Scalar s = Scalars.fromString("9.15449914691143-4.168906959966565*I");
    assertEquals(c, s);
  }
}