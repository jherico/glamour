package org.saintandreas.gl;

import java.util.Stack;

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

  public MatrixStack bind(Program program) {
    program.setUniform(bindPoint, top());
    return this;
  }
}
