// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;

/** wraps {@link AnimatedGifWriter} as {@link AnimationWriter} */
/* package */ class GifAnimationWriter implements AnimationWriter {
  private final AnimatedGifWriter gifAnimationWriter;

  public GifAnimationWriter(File file, int period) throws IOException {
    gifAnimationWriter = AnimatedGifWriter.of(file, period);
  }

  @Override
  public void append(BufferedImage bufferedImage) throws Exception {
    gifAnimationWriter.append(bufferedImage);
  }

  @Override
  public void append(Tensor tensor) throws Exception {
    append(ImageFormat.of(tensor));
  }

  @Override
  public void close() throws Exception {
    gifAnimationWriter.close();
  }
}