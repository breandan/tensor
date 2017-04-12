// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ch.ethz.idsc.tensor.Tensor;

public enum Serialization {
  ;
  /** encodes {@link Serializable} input {@link Object} as array of bytes.
   * 
   * <p>since {@link Tensor}s implement {@link Serializable},
   * function can be used to encode tensor as byte array.
   * 
   * <code>Files.write(Paths.get("filePath"), bytes)</code>
   * 
   * @param object
   * @return serialization of object 
   * @throws Exception */
  public static <T extends Serializable> byte[] of(T object) throws Exception {
    byte[] bytes = null;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(object);
    objectOutputStream.flush();
    bytes = byteArrayOutputStream.toByteArray();
    objectOutputStream.close();
    return bytes;
  }

  /** decodes {@link Serializable} object from array of bytes,
   * deserialization of object
   * 
   * <code>Files.readAllBytes(Paths.get("filePath"))</code>
   * 
   * @param bytes
   * @return {@link Serializable} object encoded in input bytes
   * @throws Exception */
  public static <T extends Serializable> T parse(byte[] bytes) throws Exception {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
    @SuppressWarnings("unchecked")
    T object = (T) objectInputStream.readObject();
    objectInputStream.close();
    return object;
  }
}
