package org.saintandreas.scene;

import java.util.function.Consumer;

import org.saintandreas.gl.Geometry;
import org.saintandreas.gl.buffers.VertexArray;

public class GeometryNode extends SceneNode {
  @SuppressWarnings("unused")
  private final Geometry geometry;
  public Consumer<Geometry> drawClosure = g -> {
  };

  public GeometryNode(Geometry g) {
    super(()->{
      g.bindVertexArray();
    }, ()->{
      g.draw();
    }, ()->{
      VertexArray.unbind();
    });
    this.geometry = g;
  }
}
