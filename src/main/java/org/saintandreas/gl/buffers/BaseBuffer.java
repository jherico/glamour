package org.saintandreas.gl.buffers;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.saintandreas.gl.OpenGL;

public class BaseBuffer {

  int buffer = -1;
  final int target;

  public BaseBuffer(int buffer, int target) {
    this.target = target;
    this.buffer = buffer;
  }

  public BaseBuffer(int target) {
    this.target = target;
    buffer = OpenGL.glGenBuffers();
  }

  public void bind() {
    glBindBuffer(target, buffer);
  }

  public static void unbind(int target) {
    glBindBuffer(target, 0);
  }

  public void unbind() {
    unbind(target);
  }

  public void setData(ByteBuffer data) {
    setData(data, GL_STATIC_DRAW);
  }

  public void setData(FloatBuffer data) {
    setData(data, GL_STATIC_DRAW);
  }

  public void setData(IntBuffer data) {
    setData(data, GL_STATIC_DRAW);
  }

  public void setData(ShortBuffer data) {
    setData(data, GL_STATIC_DRAW);
  }

  public void setData(ByteBuffer data, int usage) {
    glBufferData(target, data.capacity(), data, usage);
  }

  public void setData(FloatBuffer data, int usage) {
    glBufferData(target, data.capacity() * 4, data, usage);
  }

  public void setData(IntBuffer data, int usage) {
    glBufferData(target, data.capacity() * 4, data, usage);
  }

  public void setData(ShortBuffer data, int usage) {
    glBufferData(target, data.capacity() * 2, data, usage);
  }
  
  public void destroy() {
    glDeleteBuffers(buffer);
    buffer = -1;
  }
}
