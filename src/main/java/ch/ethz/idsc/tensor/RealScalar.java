// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

/** instances of RealScalar implement number()
 * 
 * <p>abs() returns this or this.negate() depending on whichever is non-negative */
public abstract class RealScalar extends Scalar implements Comparable<RealScalar> {
  /** real scalar 1 in {@link ExactPrecision} */
  public static final RealScalar ONE = RealScalar.of(1);
  /** real scalar that encodes Infinity. value is backed by Double.POSITIVE_INFINITY */
  public static final RealScalar POSITIVE_INFINITY = of(Double.POSITIVE_INFINITY);
  /** real scalar that encodes -Infinity. value is backed by Double.NEGATIVE_INFINITY */
  public static final RealScalar NEGATIVE_INFINITY = of(Double.NEGATIVE_INFINITY);

  /** @param number
   * @return scalar with best possible accuracy to describe number */
  public static RealScalar of(Number number) {
    if (number instanceof Integer || number instanceof Long)
      return RationalScalar.of(number.longValue(), 1);
    if (number instanceof Float || number instanceof Double)
      return DoubleScalar.of(number.doubleValue());
    if (number instanceof BigInteger)
      return RationalScalar.of((BigInteger) number, BigInteger.ONE);
    throw new IllegalArgumentException();
  }

  /***************************************************/
  /** @param a
   * @param b
   * @return the smaller one among a and b */
  public static RealScalar min(RealScalar a, RealScalar b) {
    return a.compareTo(b) > 0 ? b : a;
  }

  /** @param a
   * @param b
   * @return the greater one among a and b */
  public static RealScalar max(RealScalar a, RealScalar b) {
    return a.compareTo(b) < 0 ? b : a;
  }

  /***************************************************/
  /** @return true if this scalar is strictly greater zero, false otherwise */
  protected abstract boolean isPositive();

  @Override // from Scalar
  public abstract RealScalar negate();

  @Override // from Scalar
  public abstract Number number();

  /***************************************************/
  /** @return gives -1, 0, or 1 depending on whether this is negative, zero, or positive. */
  public final int getSignInt() {
    return this instanceof ZeroScalar ? 0 : (isPositive() ? 1 : -1);
  }

  @Override // from Scalar
  public final RealScalar abs() {
    return isPositive() ? this : negate();
  }

  @Override // from Scalar
  public final Scalar absSquared() {
    return multiply(this);
  }

  @Override // from Scalar
  public final Scalar conjugate() {
    return this;
  }
}