package org.saintandreas.gl.app;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;

import org.saintandreas.gl.MatrixStack;
import org.saintandreas.gl.OpenGL;
import org.saintandreas.math.Vector3f;

public class GlDemo extends LwjglApp {
  @Override
  protected void initGl() {
    super.initGl();
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glDisable(GL_CULL_FACE);
    MatrixStack.MODELVIEW.lookat(new Vector3f(0, 1, 3), new Vector3f(0, 0, 0),
        new Vector3f(0, 1, 0));
  }

  @Override
  protected void onResize(int width, int height) {
    super.onResize(width, height);
    MatrixStack.PROJECTION.perspective(60f, aspect, 0.01f, 1000f);
  }

  @Override
  protected void update() {
    MatrixStack.MODELVIEW.rotate((float) (Math.PI / 178f), Vector3f.UNIT_X);
    MatrixStack.MODELVIEW.rotate((float) (Math.PI / 100f), Vector3f.UNIT_Z);
  }

  @Override
  public void drawFrame() {
    OpenGL.checkError();
    glViewport(0, 0, width, height);
    glClearColor(.1f, .1f, .1f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    OpenGL.drawColorCube();
  }

  @Override
  protected void setupDisplay() {
    setupDisplay(new Rectangle(100, 100, 300, 200));
  }

  public static void main(String[] args) {
    new GlDemo().run();
  }

}
