// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Map.Entry;
import java.util.NavigableMap;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.red.Tally;
import junit.framework.TestCase;

public class BinomialDistributionTest extends TestCase {
  public void testSimple() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 7));
    PDF pdf = PDF.of(distribution);
    Scalar prob = RealScalar.ZERO;
    for (int c = 0; c <= 10; ++c)
      prob = prob.add(pdf.at(RealScalar.of(c)));
    assertTrue(IntegerQ.of(prob));
    assertEquals(prob, RealScalar.ONE);
  }

  public void testValue() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 2));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(1, 1024));
    assertEquals(pdf.at(RealScalar.of(0.5)), RationalScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(5, 512));
  }

  public void testValue2() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(1024, 59049));
    // PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(5120, 59049));
    // PDF[BinomialDistribution[10, 1/3], 10] == 1/59049
    assertEquals(pdf.at(RealScalar.of(10)), RationalScalar.of(1, 59049));
  }

  public void testValue3() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(12)), RealScalar.ZERO);
  }

  public void testMean() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    PDF pdf = PDF.of(distribution);
    Tensor sum = RealScalar.ZERO;
    for (Tensor x : Range.of(0, 22))
      sum = sum.add(x.multiply(pdf.at(x.Get())));
    assertEquals(Expectation.mean(distribution), sum);
  }

  public void testMean2() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(3, 10));
    assertEquals(Expectation.mean(distribution), RealScalar.of(3));
  }

  public void testHigh() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    CDF cdf = CDF.of(distribution);
    cdf.p_lessThan(RealScalar.of(-1000000000));
    cdf.p_lessThan(RealScalar.of(+1000000000));
    cdf.p_lessEquals(RealScalar.of(-1000000000));
    cdf.p_lessEquals(RealScalar.of(+1000000000));
  }

  public void testCornerCase() {
    {
      Distribution distribution = BinomialDistribution.of(0, RationalScalar.ONE);
      assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    }
    {
      Distribution distribution = BinomialDistribution.of(0, RationalScalar.ZERO);
      assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    }
    {
      Distribution distribution = BinomialDistribution.of(0, RealScalar.of(.3));
      assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
    }
  }

  public void testBug() {
    assertTrue(10 < Tally.of(RandomVariate.of(BinomialDistribution.of(20, RationalScalar.of(2, 3)), 10000)).size());
    assertTrue(20 < Tally.of(RandomVariate.of(BinomialDistribution.of(100, RationalScalar.of(2, 3)), 10000)).size());
    assertTrue(50 < Tally.of(RandomVariate.of(BinomialDistribution.of(1207, RationalScalar.of(2, 3)), 10000)).size());
  }

  public void testSome() {
    for (int n = 10; n < 1200; n += 10) {
      AbstractDiscreteDistribution distribution = (AbstractDiscreteDistribution) BinomialDistribution.of(n, RealScalar.of(.333));
      double extreme = Math.nextDown(1);
      distribution.randomVariate(RealScalar.of(extreme));
      NavigableMap<Scalar, Scalar> navigableMap = distribution.inverse_cdf();
      Entry<Scalar, Scalar> entry = navigableMap.lastEntry();
      // System.out.println(n + " " + entry);
    }
  }

  public void testFailN() {
    try {
      BinomialDistribution.of(-1, RationalScalar.of(1, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailP() {
    try {
      BinomialDistribution.of(10, RationalScalar.of(-1, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      BinomialDistribution.of(10, RationalScalar.of(4, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
