package org.saintandreas.gl;

import java.util.Stack;

import org.saintandreas.gl.shaders.Program;

import org.saintandreas.math.Matrix3f;
import org.saintandreas.math.Matrix4f;
import org.saintandreas.math.Quaternion;
import org.saintandreas.math.Vector2f;
import org.saintandreas.math.Vector3f;

@SuppressWarnings("serial")
public class MatrixStack extends Stack<Matrix4f> {
  public static final MatrixStack MODELVIEW = new MatrixStack("ModelView");
  public static final MatrixStack PROJECTION = new MatrixStack("Projection");
  private final String bindPoint;

  MatrixStack(String bindPoint) {
    this.bindPoint = bindPoint;
    push(new Matrix4f());
  }
  
  @Override
  public Matrix4f pop() {
    Matrix4f result = super.pop();
    assert(size() > 0);
    return result;
  }

  public MatrixStack push() {
    push(peek());
    return this;
  }

  public Matrix4f top() {
    return peek();
  }

  public MatrixStack identity() {
    return set(new Matrix4f());
  }

  public MatrixStack transpose() {
    return set(peek().transpose());
  }

  public MatrixStack translate(Vector2f vec) {
    return set(peek().translate(vec));
  }

  public MatrixStack translate(Vector3f vec) {
    return set(peek().translate(vec));
  }

  public MatrixStack rotate(float angle, Vector3f axis) {
    return set(peek().rotate(angle, axis));
  }

  public MatrixStack rotate(Quaternion q) {
    return set(peek().rotate(q));
  }

  public MatrixStack rotate(Matrix3f m) {
    return set(peek().rotate(m));
  }

  public MatrixStack scale(Vector3f vec) {
    return set(peek().scale(vec));
  }

  public MatrixStack scale(float f) {
    return set(peek().scale(f));
  }

  public MatrixStack orthographic(float left, float right, float bottom,
      float top, float near, float far) {
    return set(Matrix4f.orthographic(left, right, bottom, top, near, far));
  }

  public MatrixStack lookat(Vector3f eye, Vector3f center, Vector3f up) {
    return set(Matrix4f.lookat(eye, center, up));
  }

  public MatrixStack perspective(float fovy, float aspect, float zNear,
      float zFar) {
    return set(Matrix4f.perspective(fovy, aspect, zNear, zFar));
  }

  public MatrixStack set(Matrix4f m) {
    set(size() - 1, m);
    return this;
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
    program.setUniform(bindPoint, peek());
    return this;
  }


  public MatrixStack multiply(Matrix4f m) {
    set(peek().mult(m));
    return this;
  }

  public MatrixStack preMultiply(Matrix4f m) {
    set(m.mult(peek()));
    return this;
  }

  public MatrixStack preTranslate(float x) {
    return preTranslate(new Vector2f(x, 0));
  }

  public MatrixStack preTranslate(Vector2f v) {
    return preMultiply(new Matrix4f().translate(v));
  }

  public MatrixStack preTranslate(Vector3f v) {
    return preMultiply(new Matrix4f().translate(v));
  }
}
