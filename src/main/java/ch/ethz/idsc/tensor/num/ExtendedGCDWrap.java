// code by jph
package ch.ethz.idsc.tensor.num;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Mod;

/* package */ class ExtendedGCDWrap implements Serializable {
  private final Scalar one;

  public ExtendedGCDWrap(Scalar one) {
    this.one = one;
  }

  public ExtendedGCD function(Tensor vector) {
    return new ExtendedGCDImpl(vector);
  }

  private class ExtendedGCDImpl implements ExtendedGCD, Serializable {
    private final Scalar a;
    private final Scalar b;
    private final Scalar x;
    private final Scalar y;
    private final Scalar gcd;

    public ExtendedGCDImpl(Tensor vector) {
      this.a = vector.Get(0);
      this.b = vector.Get(1);
      if (Scalars.isZero(a)) {
        x = a;
        y = one;
        gcd = b;
      } else {
        ExtendedGCDImpl result = new ExtendedGCDImpl(Tensors.of(Mod.function(a).apply(b), a));
        x = result.y.subtract(Floor.FUNCTION.apply(b.divide(a)).multiply(result.x));
        y = result.x;
        gcd = result.gcd;
      }
    }

    @Override // from ExtendedGCD
    public Scalar gcd() {
      return gcd;
    }

    @Override // from ExtendedGCD
    public Tensor factors() {
      return Tensors.of(x, y);
    }
  }
}
