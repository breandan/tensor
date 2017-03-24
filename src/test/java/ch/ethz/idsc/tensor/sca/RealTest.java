// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class RealTest extends TestCase {
  public void testReal() {
    Scalar s = Scalars.fromString("11");
    assertEquals(Real.function.apply(s), RealScalar.of(11));
    assertEquals(Imag.function.apply(s), RealScalar.of(0));
    assertEquals(Real.of(s), RealScalar.of(11));
    assertEquals(Imag.of(s), RealScalar.of(0));
  }

  public void testComplex() {
    Scalar s = Scalars.fromString("11+3.5*I");
    assertEquals(Real.function.apply(s), RealScalar.of(11));
    assertEquals(Imag.function.apply(s), RealScalar.of(3.5));
    assertEquals(Real.of(s), RealScalar.of(11));
    assertEquals(Imag.of(s), RealScalar.of(3.5));
  }
}
