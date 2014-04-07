package org.saintandreas.scene;

import org.saintandreas.gl.MatrixStack;

public class RootNode extends SceneNode {
  private int entryModelviewSize;
  private int entryProjectionSize;

  public RootNode() {
    super();
    this.preRender = () -> {
      entryModelviewSize = MatrixStack.MODELVIEW.size();
      entryProjectionSize = MatrixStack.PROJECTION.size();
    };
    this.postRender = () -> {
      assert (entryModelviewSize == MatrixStack.MODELVIEW.size());
      assert (entryProjectionSize == MatrixStack.PROJECTION.size());
    };
  }
}
