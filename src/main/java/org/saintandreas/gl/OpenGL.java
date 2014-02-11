package org.saintandreas.gl;

import static android.opengl.GLES20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.saintandreas.gl.buffers.IndexBuffer;
import org.saintandreas.gl.buffers.VertexBuffer;
import org.saintandreas.gl.shaders.Attribute;

import android.opengl.GLES20;
import android.renderscript.Float2;
import android.renderscript.Float3;
import android.renderscript.Float4;
import android.renderscript.Matrix4f;

public final class OpenGL {

  private OpenGL() {
  }

  public static int glGenBuffers() {
    IntBuffer ib = BufferUtils.getIntBuffer();
    GLES20.glGenBuffers(1, ib);
    ib.position(0);
    return ib.get();
  }

  public static void glDeleteBuffers(int buffer) {
    IntBuffer ib = BufferUtils.getIntBuffer();
    ib.position(0);
    ib.put(buffer);
    ib.position(0);
    GLES20.glDeleteBuffers(1, ib);
  }

  public static void checkError() {
    int error = glGetError();
    if (error != 0) {
      throw new IllegalStateException("GL error " + error);
    }
  }

  public static FloatBuffer toFloatBuffer(Matrix4f matrix) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    matrix.store(buffer);
    buffer.position(0);
    return buffer;
  }

  public static List<Float4> makeQuad(float size) {
    return makeQuad(new Float2(size, size));
  }

  public static List<Float4> makeQuad(Float2 size) {
    Float2 max = new Float2(size.x, size.y);
    max.scale(0.5f);
    Float2 min = new Float2(max.x, max.y);
    min.scale(-1.0f);
    return makeQuad(min, max);
  }

  public static List<Float4> makeQuad(Float2 min, Float2 max) {
    List<Float4> result = new ArrayList<>(4);
    result.add(new Float4(min.x, min.y, 0, 1));
    result.add(new Float4(max.x, min.y, 0, 1));
    result.add(new Float4(min.x, max.y, 0, 1));
    result.add(new Float4(max.x, max.y, 0, 1));
    return result;
  }

  public static List<Float4> transformed(Collection<Float4> vs, Matrix4f m) {
    List<Float4> result = new ArrayList<>(vs.size());
    for (Float4 v : vs) {
      result.add(Matrix4f.transform(m, v, new Float4()));
    }
    return result;
  }

  public static List<Float4> interleaveConstants(
      Collection<? extends ReadableFloat4> vs, ReadableFloat4... attributes) {
    List<Float4> result = new ArrayList<>(vs.size() * (attributes.length + 1));
    for (ReadableFloat4 v : vs) {
      result.add(new Float4(v));
      for (ReadableFloat4 a : attributes) {
        result.add(new Float4(a));
      }
    }
    return result;
  }

  public final static ReadableFloat3 V3_X = new Float3(1, 0, 0);
  public final static ReadableFloat3 V3_Y = new Float3(0, 1, 0);
  public final static ReadableFloat3 V3_Z = new Float3(0, 0, 1);
  public final static ReadableFloat3 V3_ONE = new Float3(1, 1, 1);
  public final static ReadableFloat3 V3_ZERO = new Float3(0, 0, 0);

  public final static ReadableFloat4 C_W = new Float4(1, 1, 1, 1);
  public final static ReadableFloat4 C_R = new Float4(1, 0, 0, 1);
  public final static ReadableFloat4 C_G = new Float4(0, 1, 0, 1);
  public final static ReadableFloat4 C_B = new Float4(0, 0, 1, 1);
  public final static ReadableFloat4 C_C = new Float4(0, 1, 1, 1);
  public final static ReadableFloat4 C_Y = new Float4(1, 1, 0, 1);
  public final static ReadableFloat4 C_M = new Float4(1, 0, 1, 1);
  public final static ReadableFloat4 C_K = new Float4(0, 0, 0, 1);

  public static IndexedGeometry COLOR_CUBE = null;
  private static final float TAU = (float) Math.PI * 2.0f;

  public static IndexedGeometry makeColorCube() {
    if (null == COLOR_CUBE) {
      VertexBuffer vertices = toVertexBuffer(makeColorCubeVertices());
      IndexBuffer indices = toIndexBuffer(makeColorCubeIndices());
      IndexedGeometry.Builder builder = new IndexedGeometry.Builder(indices,
          vertices, 6 * 4);
      builder.withDrawType(GL_TRIANGLE_STRIP).withAttribute(Attribute.POSITION)
          .withAttribute(Attribute.COLOR);
      COLOR_CUBE = builder.build();
    }
    return COLOR_CUBE;
  }

  protected static List<Short> makeColorCubeIndices() {
    List<Short> result = new ArrayList<>();
    short offset = 0;
    for (int i = 0; i < 6; ++i) {
      if (!result.isEmpty()) {
        result.add(Short.MAX_VALUE);
      }
      result.addAll(Lists.newArrayList(Short.valueOf((short) (offset + 0)),
          Short.valueOf((short) (offset + 1)),
          Short.valueOf((short) (offset + 2)),
          Short.valueOf((short) (offset + 3))));
      offset += 4;
    }
    return result;
  }

  protected static List<Float4> makeColorCubeVertices() {
    List<Float4> result = new ArrayList<>(6 * 4 * 2);
    Matrix4f m;
    List<Float4> q = makeQuad(1.0f);
    // Front
    m = new Matrix4f().translate(new Float3(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), C_B));

    // Back
    m = new Matrix4f().rotate(TAU / 2f, new Float3(V3_X)).translate(
        new Float3(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), C_Y));

    // Top
    m = new Matrix4f().rotate(TAU / -4f, new Float3(V3_X)).translate(
        new Float3(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), C_G));
    // Bottom
    m = new Matrix4f().rotate(TAU / 4f, new Float3(V3_X)).translate(
        new Float3(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), C_M));

    // Left
    m = new Matrix4f().rotate(TAU / -4f, new Float3(V3_Y)).translate(
        new Float3(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), C_R));

    // Right
    m = new Matrix4f().rotate(TAU / 4f, new Float3(V3_Y)).translate(
        new Float3(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), C_C));
    return result;
  }

  public static VertexBuffer toVertexBuffer(Collection<Float4> vertices) {
    FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.size() * 4);
    for (Float4 v : vertices) {
      v.store(fb);
    }
    fb.position(0);
    VertexBuffer result = new VertexBuffer();
    result.bind();
    result.setData(fb);
    result.unbind();
    return result;
  }

  public static IndexBuffer toIndexBuffer(Collection<Short> vertices) {
    ShortBuffer fb = BufferUtils.createShortBuffer(vertices.size());
    for (Short v : vertices) {
      fb.put(v);
    }
    fb.position(0);
    IndexBuffer result = new IndexBuffer();
    result.bind();
    result.setData(fb);
    result.unbind();
    return result;
  }

  public static IndexedGeometry makeTexturedQuad(Float2 min, Float2 max) {
    return makeTexturedQuad(min, max, new Float2(0, 0), new Float2(1, 1));
  }

  public static IndexedGeometry makeTexturedQuad(Float2 min, Float2 max,
      Float2 tmin, Float2 tmax) {
    VertexBuffer vertices;
    {
      List<Float4> result = new ArrayList<>();
      result.add(new Float4(min.x, min.y, 0, 1));
      result.add(new Float4(tmin.x, tmin.y, 0, 0));
      result.add(new Float4(max.x, min.y, 0, 1));
      result.add(new Float4(tmax.x, tmin.y, 0, 0));
      result.add(new Float4(min.x, max.y, 0, 1));
      result.add(new Float4(tmin.x, tmax.y, 0, 0));
      result.add(new Float4(max.x, max.y, 0, 1));
      result.add(new Float4(tmax.x, tmax.y, 0, 0));
      vertices = toVertexBuffer(makeColorCubeVertices());
    }
    IndexBuffer indices = toIndexBuffer(Lists.newArrayList(
        Short.valueOf((short) 0), Short.valueOf((short) 1),
        Short.valueOf((short) 2), Short.valueOf((short) 3)));
    IndexedGeometry.Builder builder = new IndexedGeometry.Builder(indices,
        vertices, 4);
    builder.withDrawType(GL_TRIANGLE_STRIP).withAttribute(Attribute.POSITION)
        .withAttribute(Attribute.TEX);
    return builder.build();
  }

  public static Matrix4f orthographic(float left, float right, float bottom,
      float top, float near, float far) {
    Matrix4f m = new Matrix4f();
    float x_orth = 2 / (right - left);
    float y_orth = 2 / (top - bottom);
    float z_orth = -2 / (far - near);

    float tx = -(right + left) / (right - left);
    float ty = -(top + bottom) / (top - bottom);
    float tz = -(far + near) / (far - near);

    m.m00 = x_orth;
    m.m10 = 0;
    m.m20 = 0;
    m.m30 = 0;
    m.m01 = 0;
    m.m11 = y_orth;
    m.m21 = 0;
    m.m31 = 0;
    m.m02 = 0;
    m.m12 = 0;
    m.m22 = z_orth;
    m.m32 = 0;
    m.m03 = tx;
    m.m13 = ty;
    m.m23 = tz;
    m.m33 = 1;
    return m;
  }

  public static Matrix4f lookat(Float3 eye, Float3 center, Float3 up) {
    Float3 f = Float3.sub(center, eye, new Float3());
    f.normalise();
    Float3 u = new Float3(up);
    u.normalise();
    Float3 s = Float3.cross(f, u, new Float3());
    s.normalise();
    u = Float3.cross(s, f, u);

    Matrix4f result = new Matrix4f();
    result.m00 = s.x;
    result.m10 = s.y;
    result.m20 = s.z;
    result.m01 = u.x;
    result.m11 = u.y;
    result.m21 = u.z;
    result.m02 = -f.x;
    result.m12 = -f.y;
    result.m22 = -f.z;
    return result.translate(new Float3(-eye.x, -eye.y, -eye.z));
  }

  public static Matrix4f perspective(float fovy, float aspect, float zNear,
      float zFar) {
    float radians = fovy / 2f * (float) Math.PI / 180f;
    float sine = (float) Math.sin(radians);
    float cotangent = (float) Math.cos(radians) / sine;
    float deltaZ = zFar - zNear;

    if ((deltaZ == 0) || (sine == 0) || (aspect == 0)) {
      throw new IllegalStateException();
    }

    Matrix4f result = new Matrix4f();
    result.m00 = cotangent / aspect;
    result.m11 = cotangent;
    result.m22 = -(zFar + zNear) / deltaZ;
    result.m23 = -1;
    result.m32 = -2 * zNear * zFar / deltaZ;
    result.m33 = 0;
    return result;
  }

}
