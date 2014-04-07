package org.saintandreas.scene;

import org.saintandreas.gl.MatrixStack;
import org.saintandreas.gl.MatrixTransformable;
import org.saintandreas.gl.Transformable;
import org.saintandreas.math.Matrix3f;
import org.saintandreas.math.Matrix4f;
import org.saintandreas.math.Quaternion;
import org.saintandreas.math.Vector2f;
import org.saintandreas.math.Vector3f;

public class TransformableNode extends TransformNode implements
    Transformable<MatrixTransformable> {
  protected MatrixTransformable transformable;

  public TransformableNode() {
    super();
  }

  public TransformableNode(boolean both) {
    super(both);
  }

  public TransformableNode(MatrixStack... stacks) {
    super(stacks);
  }

  public TransformableNode(Runnable render, boolean both) {
    super(render, both);
  }

  public TransformableNode(Runnable consumer, MatrixStack... stacks) {
    super(consumer, stacks);
  }

  public TransformableNode(Runnable render) {
    super(render);
  }


  @Override
  public Matrix4f getTransform() {
    return transformable.getTransform();
  }

  @Override
  public MatrixTransformable identity() {
    return transformable.identity();
  }

  @Override
  public MatrixTransformable transpose() {
    return transformable.transpose();
  }

  @Override
  public MatrixTransformable translate(float x) {
    return transformable.translate(x);
  }

  @Override
  public MatrixTransformable translate(Vector2f vec) {
    return transformable.translate(vec);
  }

  @Override
  public MatrixTransformable translate(Vector3f vec) {
    return transformable.translate(vec);
  }

  @Override
  public MatrixTransformable rotate(float angle, Vector3f axis) {
    return transformable.rotate(angle, axis);
  }

  @Override
  public MatrixTransformable rotate(Quaternion q) {
    return transformable.rotate(q);
  }

  @Override
  public MatrixTransformable rotate(Matrix3f m) {
    return transformable.rotate(m);
  }

  @Override
  public MatrixTransformable scale(Vector3f vec) {
    return transformable.scale(vec);
  }

  @Override
  public MatrixTransformable scale(float f) {
    return transformable.scale(f);
  }

  @Override
  public MatrixTransformable multiply(Matrix4f m) {
    return transformable.multiply(m);
  }

  @Override
  public MatrixTransformable preMultiply(Matrix4f m) {
    return transformable.preMultiply(m);
  }

  @Override
  public MatrixTransformable preTranslate(float x) {
    return transformable.preTranslate(x);
  }

  @Override
  public MatrixTransformable preTranslate(Vector2f v) {
    return transformable.preTranslate(v);
  }

  @Override
  public MatrixTransformable preTranslate(Vector3f v) {
    return transformable.preTranslate(v);
  }

  @Override
  public MatrixTransformable preRotate(float angle, Vector3f axis) {
    return transformable.preRotate(angle, axis);
  }

  @Override
  public MatrixTransformable preRotate(Quaternion q) {
    return transformable.preRotate(q);
  }

  @Override
  public MatrixTransformable preRotate(Matrix3f m) {
    return transformable.preRotate(m);
  }

  @Override
  public MatrixTransformable lookat(Vector3f eye, Vector3f center, Vector3f up) {
    return transformable.lookat(eye, center, up);
  }

  @Override
  public MatrixTransformable orthographic(float left, float right,
      float bottom, float top, float near, float far) {
    return transformable.orthographic(left, right, bottom, top, near, far);
  }

  @Override
  public MatrixTransformable perspective(float fovy, float aspect, float zNear,
      float zFar) {
    return transformable.perspective(fovy, aspect, zNear, zFar);
  }

  @Override
  public String toString() {
    return transformable.toString();
  }

}
