// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** access to resource data in jar files, for instance,
 * the content included in the tensor library.
 * 
 * <p>Examples of resources provided by the tensor library:
 * <pre>
 * /colorscheme/classic.csv
 * /colorscheme/hue.csv
 * /number/primes.vector
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ResourceData.html">ResourceData</a> */
public enum ResourceData {
  ;
  /** Example use:
   * Interpolation interpolation = LinearInterpolation.of(ResourceData.of("/colorscheme/classic.csv"));
   * 
   * @param string
   * @return imported tensor, or null if resource could not be loaded */
  public static Tensor of(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) { // auto closeable
      Filename filename = new Filename(new File(string)); // to determine file extension
      if (filename.hasExtension("csv"))
        return CsvFormat.parse(_lines(inputStream));
      if (filename.hasExtension("png"))
        return ImageFormat.from(ImageIO.read(inputStream));
      if (filename.hasExtension("vector"))
        return Tensor.of(_lines(inputStream).map(Scalars::fromString));
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  // helper function
  private static Stream<String> _lines(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream)).lines();
  }
}
