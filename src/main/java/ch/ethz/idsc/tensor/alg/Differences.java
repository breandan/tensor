// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** simplified version of Mathematica::Differences
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Differences.html">Differences</a> */
public enum Differences {
  ;
  // ---
  /** Differences[{a, b, c, d, e}] == {b - a, c - b, d - c, e - d}
   * 
   * @param tensor
   * @return the successive differences of elements in tensor */
  public static Tensor of(Tensor tensor) {
    return Tensor.of(IntStream.range(0, tensor.length() - 1).boxed() //
        .parallel() //
        .map(index -> tensor.get(index + 1).subtract(tensor.get(index))));
  }
}
