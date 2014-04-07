package org.saintandreas.scene;

import org.saintandreas.gl.MatrixStack;

/**
 * A node to contain matrix stack transformations that apply
 * to the children of the node.
 * @author bdavis
 *
 */
public class TransformNode extends SceneNode {
  protected final static MatrixStack BOTH[] = { MatrixStack.MODELVIEW, MatrixStack.PROJECTION };
  protected final static MatrixStack MV[] = { MatrixStack.MODELVIEW };

  protected final MatrixStack stacks[];

  public TransformNode(Runnable consumer, MatrixStack ... stacks) {
    super(()->{
      for (MatrixStack s : stacks) {
        s.push();
      }
    },
    consumer,
    ()->{
      for (MatrixStack s : stacks) {
        s.pop();
      }
    });
    this.stacks = stacks; 
  }

  public TransformNode(Runnable render, boolean both) {
    this(render, both ? BOTH : MV);
  }

  public TransformNode(Runnable render) {
    this(render, false);
  }

  public TransformNode(MatrixStack ... stacks) {
    this(() -> {}, stacks);
  }

  public TransformNode(boolean both) {
    this(both ? BOTH : MV);
  }

  public TransformNode() {
    this(false);
  }
}
