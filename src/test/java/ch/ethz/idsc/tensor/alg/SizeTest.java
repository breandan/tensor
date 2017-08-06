// code by jph
package ch.ethz.idsc.tensor.alg;

import junit.framework.TestCase;

public class SizeTest extends TestCase {
  public void testSimple() {
    Size size = Size.of(new int[] { 4, 2, 3 });
    // System.out.println(size.toString());
    assertEquals(size.indexOf(MultiIndex.of(0, 0, 0)), 0);
    assertEquals(size.indexOf(MultiIndex.of(3, 1, 2)), 23);
    // OuterProductInteger outerProductInteger = new OuterProductInteger(new int[] { 3, 2, 4 });
    // for (MultiIndex myMultiIndex : size)
    // System.out.println(myMultiIndex + " " + size.indexOf(myMultiIndex));
  }
}