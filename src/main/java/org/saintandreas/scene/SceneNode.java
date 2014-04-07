package org.saintandreas.scene;

import java.util.ArrayList;
import java.util.List;

public class SceneNode implements Renderable {
  private final List<Renderable> children = new ArrayList<>();
  protected Runnable preRender;
  protected Runnable postRender;
  protected Runnable render;

  protected SceneNode() {
    this(() -> {
    }, () -> {
    }, () -> {
    });
  }

  public <T extends Renderable> T addChild(T child) {
    children.add(child);
    return child;
  }

  /**
   * @param preRender
   *          the code to execute prior to rendering ones' self and children
   * @param render
   *          The code to execute in order to render this item on the scene
   *          graph
   * @param postRender
   *          The code to execute after rendering ones' self and children.
   *          Typically involves restoring any changed state incurred by the
   *          prerender step
   */
  public SceneNode(Runnable preRender, Runnable render, Runnable postRender) {
    this.render = render;
    this.preRender = preRender;
    this.postRender = postRender;
  }

  public SceneNode(Runnable preRender, Runnable postRender) {
    this(preRender, () -> {
    }, postRender);
  }

  public SceneNode(Runnable render) {
    this(() -> {
    }, render, () -> {
    });
  }

  @Override
  public final void render() {
    if (null != preRender) {
      preRender.run();
    }
    render.run();
    for (Renderable child : children) {
      child.render();
    }
    postRender.run();
  }
}
