package org.saintandreas.scene;

import org.saintandreas.gl.Geometry;
import org.saintandreas.gl.buffers.VertexArray;

public class GeometryNode extends SceneNode {

  public GeometryNode(Geometry g, Runnable closure) {
    super(()->{
      g.bindVertexArray();
    }, closure, ()->{
      VertexArray.unbind();
    });
  }

  public GeometryNode(Geometry g) {
    this(g, ()->{ g.draw(); });
  }
}
