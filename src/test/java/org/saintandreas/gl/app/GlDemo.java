package org.saintandreas.gl.app;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

import java.awt.Rectangle;

import org.saintandreas.gl.IndexedGeometry;
import org.saintandreas.gl.MatrixStack;
import org.saintandreas.gl.OpenGL;
import org.saintandreas.gl.buffers.VertexArray;
import org.saintandreas.gl.shaders.Program;
import org.saintandreas.math.Vector3f;
import org.saintandreas.resources.BasicResource;

public class GlDemo extends LwjglApp {

  IndexedGeometry cubeGeometry;
  Program cubeProgram;

  @Override
  protected void initGl() {
    super.initGl();
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glEnable(GL_PRIMITIVE_RESTART);
    glDisable(GL_CULL_FACE);
    glPrimitiveRestartIndex(Short.MAX_VALUE);
    MatrixStack.MODELVIEW.lookat(new Vector3f(0, 1, 3), new Vector3f(0, 0, 0),
        new Vector3f(0, 1, 0));

    cubeGeometry = OpenGL.makeColorCube();
    cubeProgram = new Program(
      new BasicResource("test.vs"),
      new BasicResource("test.fs"));
    cubeProgram.link();
  }

  @Override
  protected void onResize(int width, int height) {
    super.onResize(width, height);
    MatrixStack.PROJECTION.perspective(60f, aspect, 0.01f, 1000f);
  }

  @Override
  protected void update() {
    MatrixStack.MODELVIEW.rotate((float) (Math.PI / 100f), Vector3f.UNIT_Y);
  }

  @Override
  public void drawFrame() {
    OpenGL.checkError();
    glViewport(0, 0, width, height);
    glClearColor(.1f, .1f, .1f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    cubeProgram.use();
    MatrixStack.bind(cubeProgram);
    cubeGeometry.ibo.bind();
    cubeGeometry.bindVertexArray();

    cubeGeometry.draw();

    VertexArray.unbind();
    cubeGeometry.ibo.unbind();
    Program.clear();
  }

  @Override
  protected void onDestroy() {
  }

  @Override
  protected void setupDisplay() {
    setupDisplay(new Rectangle(100, 100, 300, 150));
  }

  public static void main(String[] args) {
    new GlDemo().run();
  }

}
