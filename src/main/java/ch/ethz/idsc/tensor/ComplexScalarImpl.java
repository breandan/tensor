// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class ComplexScalarImpl extends AbstractScalar implements ComplexScalar {
  /** suffix that is appended to imaginary part of {@link ComplexScalar} in function toString() */
  private static final String IMAGINARY_SUFFIX = "*I";
  // ---
  private final Scalar re;
  private final Scalar im;

  /* package */ ComplexScalarImpl(Scalar re, Scalar im) {
    if (re instanceof ComplexScalar || im instanceof ComplexScalar)
      throw TensorRuntimeException.of(re);
    this.re = re;
    this.im = im;
  }

  @Override // from RealInterface
  public Scalar real() {
    return re;
  }

  @Override // from ImagInterface
  public Scalar imag() {
    return im;
  }

  @Override // from Scalar
  public Scalar invert() {
    Scalar mag = re.multiply(re).add(im.multiply(im)).invert();
    return ComplexScalar.of(re.multiply(mag), im.negate().multiply(mag));
  }

  @Override // from Scalar
  public Scalar negate() {
    return ComplexScalar.of(re.negate(), im.negate());
  }

  @Override // from ConjugateInterface
  public Scalar conjugate() {
    return ComplexScalar.of(re, im.negate());
  }

  @Override // from Scalar
  public Scalar abs() {
    return Hypot.bifunction.apply(re, im);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return ComplexScalar.of( //
          re.multiply(z.real()).subtract(im.multiply(z.imag())), //
          re.multiply(z.imag()).add(im.multiply(z.real())));
    }
    return ComplexScalar.of(re.multiply(scalar), im.multiply(scalar));
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar z = (ComplexScalar) scalar;
      return ComplexScalar.of(re.add(z.real()), im.add(z.imag()));
    }
    if (scalar instanceof RealScalar)
      return ComplexScalar.of(re.add(scalar), im);
    throw TensorRuntimeException.of(scalar);
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return ComplexScalar.fromPolar( //
        Sqrt.function.apply(abs()), //
        arg().divide(RealScalar.of(2)));
  }

  @Override // from ArgInterface
  public Scalar arg() {
    return DoubleScalar.of(Math.atan2( //
        imag().number().doubleValue(), //
        real().number().doubleValue() //
    ));
  }

  @Override // from ChopInterface
  public Scalar chop(double threshold) {
    return ComplexScalar.of((Scalar) Chop.of(re, threshold), (Scalar) Chop.of(im, threshold));
  }

  @Override // from NInterface
  public Scalar n() {
    return ComplexScalar.of(N.function.apply(re), N.function.apply(im));
  }

  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(re, im);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) object;
      return re.equals(complexScalar.real()) && im.equals(complexScalar.imag());
    }
    return re.equals(object) && im.equals(ZeroScalar.get());
  }

  @Override // from AbstractScalar
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(48); // initial capacity
    if (re.equals(ZeroScalar.get())) {
      stringBuilder.append(im);
      stringBuilder.append(IMAGINARY_SUFFIX);
    } else {
      stringBuilder.append(re);
      String imag = im.toString();
      if (!imag.startsWith("-"))
        stringBuilder.append('+');
      stringBuilder.append(im);
      stringBuilder.append(IMAGINARY_SUFFIX);
    }
    return stringBuilder.toString();
  }
}
