package org.saintandreas.gl.buffers;

import static android.opengl.GLES20.*;

public class IndexBuffer extends BaseBuffer {
  public IndexBuffer() {
    super(GL_ELEMENT_ARRAY_BUFFER);
  }
}
