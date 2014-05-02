package org.saintandreas.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {

  public static ByteBuffer getByteBuffer(int size) {
    ByteBuffer buffer = ByteBuffer.allocateDirect(size);
    buffer.order(ByteOrder.nativeOrder());
    return buffer;
  }

  public static ByteBuffer getByteBuffer() {
    return getByteBuffer(1);
  }

  public static IntBuffer getIntBuffer(int size) {
    return getByteBuffer(size * (Integer.SIZE >> 3)).asIntBuffer();
  }

  public static IntBuffer getIntBuffer() {
    return getIntBuffer(1);
  }

  public static ShortBuffer getShortBuffer(int size) {
    return getByteBuffer(size * (Short.SIZE >> 3)).asShortBuffer();
  }

  public static ShortBuffer getShortBuffer() {
    return getShortBuffer(1);
  }

  public static FloatBuffer getFloatBuffer(int size) {
    return getByteBuffer(size * (Float.SIZE >> 3)).asFloatBuffer();
  }

  public static FloatBuffer getFloatBuffer() {
    return getFloatBuffer(1);
  }
}
