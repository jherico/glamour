package org.saintandreas.gl;

import java.util.Stack;

import org.saintandreas.gl.shaders.Program;

import android.renderscript.Float2;
import android.renderscript.Float3;
import android.renderscript.Matrix4f;
import copied.com.jme3.math.Vector2f;
import copied.com.jme3.math.Vector3f;

@SuppressWarnings("serial")
public class MatrixStack extends Stack<Matrix4f> {
  public static final MatrixStack MODELVIEW = new MatrixStack();
  public static final MatrixStack PROJECTION = new MatrixStack();

  MatrixStack() {
    push(new Matrix4f());
  }

  public MatrixStack push() {
    Matrix4f newMatrix = new Matrix4f();
    newMatrix.load(peek());
    push(newMatrix);
    return this;
  }

  public MatrixStack identity() {
    peek().loadIdentity();
    return this;
  }

  public MatrixStack transpose() {
    peek().transpose();
    return this;
  }

  public MatrixStack translate(Float2 vec) {
    peek().translate(vec.x,  vec.y,  0);
    return this;
  }

  public MatrixStack translate(Float3 vec) {
    peek().translate(vec.x, vec.y, vec.z);
    return this;
  }

  public MatrixStack scale(Float3 vec) {
    peek().scale(vec.x, vec.y, vec.z);
    return this;
  }

  public MatrixStack rotate(float angle, Float3 axis) {
    peek().rotate(angle, axis.x, axis.y, axis.z);
    return this;
  }

  public MatrixStack orthographic(float left, float right, float bottom,
      float top, float near, float far) {
    peek().load(OpenGL.orthographic(left, right, bottom, top, near, far));
    return this;
  }

  public MatrixStack lookat(Vector3f eye, Vector3f center, Vector3f up) {
    peek().load(OpenGL.lookat(eye, center, up));
    return this;
  }

  public MatrixStack perspective(float fovy, float aspect, float zNear,
      float zFar) {
    peek().load(OpenGL.perspective(fovy, aspect, zNear, zFar));
    return this;
  }

  public static void bindProjection(Program program) {
    program.setUniform("Projection", MatrixStack.PROJECTION.peek());
  }

  public static void bindModelview(Program program) {
    program.setUniform("ModelView", MatrixStack.MODELVIEW.peek());
  }
  
  public static void bind(Program program) {
    bindProjection(program);
    bindModelview(program);
  }
  
  public MatrixStack preMultiply(Matrix4f m) {
    peek().load(Matrix4f.mul(m, peek(), new Matrix4f()));
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
