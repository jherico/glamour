package org.saintandreas.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.saintandreas.gl.buffers.IndexBuffer;
import org.saintandreas.gl.buffers.VertexBuffer;
import org.saintandreas.gl.shaders.Attribute;
import org.saintandreas.gl.textures.Texture;
import org.saintandreas.math.Matrix4f;
import org.saintandreas.math.Vector2f;
import org.saintandreas.math.Vector3f;
import org.saintandreas.math.Vector4f;
import org.saintandreas.resources.Images;
import org.saintandreas.resources.Resource;

import com.google.common.collect.Lists;

public final class OpenGL {


  private OpenGL() {
  }

  public static void checkError() {
    int error = glGetError();
    if (error != 0) {
      throw new IllegalStateException("GL error " + error);
    }
  }

  public static FloatBuffer toFloatBuffer(Matrix4f matrix) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    matrix.fillFloatBuffer(buffer);
    buffer.position(0);
    return buffer;
  }

  public static List<Vector4f> makeQuad(float size) {
    return makeQuad(new Vector2f(size, size));
  }

  public static List<Vector4f> makeQuad(Vector2f size) {
    Vector2f max = size.mult(0.5f);
    Vector2f min = max.mult(-1f);
    return makeQuad(min, max);
  }

  public static List<Vector4f> makeQuad(Vector2f min, Vector2f max) {
    List<Vector4f> result = new ArrayList<>(4);
    result.add(new Vector4f(min.x, min.y, 0, 1));
    result.add(new Vector4f(max.x, min.y, 0, 1));
    result.add(new Vector4f(min.x, max.y, 0, 1));
    result.add(new Vector4f(max.x, max.y, 0, 1));
    return result;
  }

  public static List<Vector4f> transformed(Collection<Vector4f> vs, Matrix4f m) {
    List<Vector4f> result = new ArrayList<>(vs.size());
    for (Vector4f v : vs) {
      result.add(m.mult(v));
    }
    return result;
  }

  public static List<Vector4f> interleaveConstants(
      Collection<? extends Vector4f> vs, Vector4f... attributes) {
    List<Vector4f> result = new ArrayList<>(vs.size() * (attributes.length + 1));
    for (Vector4f v : vs) {
      result.add(v);
      for (Vector4f a : attributes) {
        result.add(a);
      }
    }
    return result;
  }

  public static IndexedGeometry COLOR_CUBE = null;
  private static final float TAU = (float) Math.PI * 2.0f;

  public static IndexedGeometry makeColorCube() {
    if (null == COLOR_CUBE) {
      List<Vector4f> vertices = makeColorCubeVertices();
      List<Short> indices = makeColorCubeIndices();
      IndexedGeometry.Builder builder = new IndexedGeometry.Builder(indices, vertices);
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

  protected static List<Vector4f> makeColorCubeVertices() {
    List<Vector4f> result = new ArrayList<>(6 * 4 * 2);
    Matrix4f m;
    List<Vector4f> q = makeQuad(1.0f);
    // Front
    m = new Matrix4f().translate(new Vector3f(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), Colors.B));

    // Back
    m = new Matrix4f().rotate(TAU / 2f, Vector3f.UNIT_X).translate(
        new Vector3f(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), Colors.Y));

    // Top
    m = new Matrix4f().rotate(TAU / -4f, Vector3f.UNIT_X).translate(
        new Vector3f(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), Colors.G));
    
    // Bottom
    m = new Matrix4f().rotate(TAU / 4f, Vector3f.UNIT_X).translate(
        new Vector3f(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), Colors.M));

    // Left
    m = new Matrix4f().rotate(TAU / -4f, Vector3f.UNIT_Y).translate(
        new Vector3f(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), Colors.R));

    // Right
    m = new Matrix4f().rotate(TAU / 4f, Vector3f.UNIT_Y).translate(
        new Vector3f(0, 0, 0.5f));
    result.addAll(interleaveConstants(transformed(q, m), Colors.C));
    
    return result;
  }

  public static VertexBuffer toVertexBuffer(Collection<Vector4f> vertices) {
    FloatBuffer fb = BufferUtils.getFloatBuffer(vertices.size() * 4);
    for (Vector4f v : vertices) {
      v.fillBuffer(fb);
    }
    fb.position(0);
    VertexBuffer result = new VertexBuffer();
    result.bind();
    result.setData(fb);
    VertexBuffer.unbind();
    return result;
  }

  public static IndexBuffer toShortIndexBuffer(Collection<? extends Number> vertices) {
    ShortBuffer fb = BufferUtils.getShortBuffer(vertices.size());
    for (Number v : vertices) {
      fb.put(v.shortValue());
    }
    fb.position(0);
    IndexBuffer result = new IndexBuffer();
    result.bind();
    result.setData(fb);
    IndexBuffer.unbind();
    return result;
  }

  public static IndexBuffer toIntIndexBuffer(Collection<? extends Number> vertices) {
    IntBuffer fb = BufferUtils.getIntBuffer(vertices.size());
    for (Number v : vertices) {
      fb.put(v.intValue());
    }
    fb.position(0);
    IndexBuffer result = new IndexBuffer();
    result.bind();
    result.setData(fb);
    IndexBuffer.unbind();
    return result;
  }

  public static IndexedGeometry makeTexturedQuad() {
    return makeTexturedQuad(new Vector2f(-1), new Vector2f(1));
  }

  public static IndexedGeometry makeTexturedQuad(Vector2f min, Vector2f max) {
    return makeTexturedQuad(min, max, new Vector2f(0, 0), new Vector2f(1, 1));
  }

  public static IndexedGeometry makeTexturedQuad(Vector2f min, Vector2f max,
      Vector2f tmin, Vector2f tmax) {
    VertexBuffer vertices;
    {
      List<Vector4f> result = new ArrayList<>();
      result.add(new Vector4f(min.x, min.y, 0, 1));
      result.add(new Vector4f(tmin.x, tmin.y, 0, 0));
      result.add(new Vector4f(max.x, min.y, 0, 1));
      result.add(new Vector4f(tmax.x, tmin.y, 0, 0));
      result.add(new Vector4f(min.x, max.y, 0, 1));
      result.add(new Vector4f(tmin.x, tmax.y, 0, 0));
      result.add(new Vector4f(max.x, max.y, 0, 1));
      result.add(new Vector4f(tmax.x, tmax.y, 0, 0));
      vertices = toVertexBuffer(result);
    }
    IndexBuffer indices = toShortIndexBuffer(Lists.newArrayList(
        Short.valueOf((short) 0), Short.valueOf((short) 1),
        Short.valueOf((short) 2), Short.valueOf((short) 3)));
    IndexedGeometry.Builder builder = new IndexedGeometry.Builder(indices,
        vertices, 4);
    builder.withDrawType(GL_TRIANGLE_STRIP).withAttribute(Attribute.POSITION)
        .withAttribute(Attribute.TEX);
    return builder.build();
  }

  private static final Map<Resource, Texture> CUBE_MAPS = new HashMap<>();
  private static final int RESOURCE_ORDER[] = {
    GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
    GL_TEXTURE_CUBE_MAP_POSITIVE_X,
    GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
    GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
    GL_TEXTURE_CUBE_MAP_NEGATIVE_Z,
    GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
  };

  public static Texture getCubemapTextures(Resource ... resources) {
    assert(resources.length > 0);
    Resource firstResource = resources[0];
    assert(null != firstResource);
    if (!CUBE_MAPS.containsKey(firstResource)) {

      Texture texture = new Texture(GL_TEXTURE_CUBE_MAP);
      texture.bind();
      texture.parameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      texture.parameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      texture.parameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
      texture.parameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
      texture.parameter(GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
      for (int i = 0; i < 6 && i < resources.length; ++i) {
        Resource imageResource = resources[i];
        if (null == imageResource) {
          continue;
        }
        int loadTarget = RESOURCE_ORDER[i];
        texture.loadImageData(Images.load(imageResource), loadTarget);
      }
      texture.unbind();
      CUBE_MAPS.put(firstResource, texture);
    }
    return CUBE_MAPS.get(firstResource);
  }


}
