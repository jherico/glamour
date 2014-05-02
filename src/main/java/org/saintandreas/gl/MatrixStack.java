package org.saintandreas.gl;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Stack;
import java.util.function.Consumer;

import org.lwjgl.BufferUtils;
import org.saintandreas.gl.shaders.Program;
import org.saintandreas.math.Matrix4f;

public class MatrixStack extends AbstractTransformable<MatrixStack> {
  public static final MatrixStack MODELVIEW = new MatrixStack("ModelView");
  public static final MatrixStack PROJECTION = new MatrixStack("Projection");
  Stack<Matrix4f> stack = new Stack<>();
  private final String bindPoint;

  MatrixStack(String bindPoint) {
    this.bindPoint = bindPoint;
  }
  
  public int size() {
    return stack.size() + 1;
  }
  
  public MatrixStack withPush(Consumer<MatrixStack> closure) {
    int startSize = size();
    push();
    closure.accept(this);
    pop();
    assert(size() == startSize);
    return this;
  }

  public MatrixStack withPush(Runnable closure) {
    int startSize = size();
    push();
    closure.run();
    pop();
    assert(size() == startSize);
    return this;
  }
  
  public MatrixStack pop() {
    set(stack.pop());
    return this;
  }

  public MatrixStack push() {
    stack.push(getTransform());
    return this;
  }

  public Matrix4f top() {
    return getTransform();
  }


  public static void bindProjection(Program program) {
    PROJECTION.bind(program);
  }

  public static void bindModelview(Program program) {
    MODELVIEW.bind(program);
  }

  public static void bindAll(Program program) {
    bindProjection(program);
    bindModelview(program);
  }
  
  public static void bindAllGl() {
    FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    MatrixStack.PROJECTION.top().fillFloatBuffer(fb, true);
    fb.rewind();
    glMatrixMode(GL_PROJECTION);
    glLoadMatrix(fb);

    fb.rewind();
    MatrixStack.MODELVIEW.top().fillFloatBuffer(fb, true);
    fb.rewind();
    glMatrixMode(GL_MODELVIEW);
    glLoadMatrix(fb);
  }

  public MatrixStack bind(Program program) {
    program.setUniform(bindPoint, top());
    return this;
  }
}
