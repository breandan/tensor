// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.Transpose;

/** The {@link Dimensions} of tensors that represent images are
 * <code>WIDTH x HEIGHT x 4</code>
 * 
 * <p>The 4 entries in the last dimension are RGBA.
 * 
 * <p>This convention is consistent with Java
 * {@link Graphics2D}, {@link BufferedImage}, ...
 * 
 * <p><code>Tensor::get(x, y)</code>
 * refers to the same pixel as
 * <code>BufferedImage::getRGB(x, y)</code>
 * 
 * <p>Consistent also with the screen size, for instance 1280 x 720. */
public enum ImageFormat {
  ;
  // ---
  /** @param bufferedImage with dimensions [width x height]
   * @return tensor with dimensions [width x height x 4] */
  public static Tensor from(BufferedImage bufferedImage) {
    return Tensors.matrix((i, j) -> ColorFormat.toVector(bufferedImage.getRGB(i, j)), //
        bufferedImage.getWidth(), bufferedImage.getHeight());
  }

  /** @param bufferedImage grayscale image with dimensions [width x height]
   * @return tensor with dimensions [width x height] */
  public static Tensor fromGrayscale(BufferedImage bufferedImage) {
    return Tensors.matrix((i, j) -> RealScalar.of(bufferedImage.getRGB(i, j) & 0xff), //
        bufferedImage.getWidth(), bufferedImage.getHeight());
  }

  /** image export works with PNG format.
   * 
   * <p>do not export to JPG, because the color channels are not compatible!
   * 
   * @param tensor
   * @return image of type BufferedImage.TYPE_BYTE_GRAY or BufferedImage.TYPE_INT_ARGB */
  public static BufferedImage of(Tensor tensor) {
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.size() == 2)
      return toTYPE_BYTE_GRAY(tensor, dims);
    return toTYPE_INT_ARGB(tensor, dims);
  }

  // helper function
  private static BufferedImage toTYPE_BYTE_GRAY(Tensor tensor, List<Integer> dims) {
    BufferedImage bufferedImage = new BufferedImage(dims.get(0), dims.get(1), BufferedImage.TYPE_BYTE_GRAY);
    int[] array = ExtractPrimitives.toArrayInt(Transpose.of(tensor));
    IntStream.range(0, array.length).parallel() //
        .forEach(index -> array[index] = inflate(array[index]));
    bufferedImage.setRGB(0, 0, dims.get(0), dims.get(1), array, 0, dims.get(0));
    return bufferedImage;
  }

  // helper function
  private static int inflate(int level) {
    // if (level < 0 || 255 < level)
    // throw new RuntimeException();
    return (level << 16) | (level << 8) | level;
  }

  // helper function
  private static BufferedImage toTYPE_INT_ARGB(Tensor tensor, List<Integer> dims) {
    BufferedImage bufferedImage = new BufferedImage(dims.get(0), dims.get(1), BufferedImage.TYPE_INT_ARGB);
    Tensor res = TensorMap.of(vector -> RealScalar.of(ColorFormat.toInt(vector)), tensor, 2);
    int[] array = ExtractPrimitives.toArrayInt(Transpose.of(res));
    bufferedImage.setRGB(0, 0, dims.get(0), dims.get(1), array, 0, dims.get(0));
    return bufferedImage;
  }
}