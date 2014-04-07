package org.saintandreas.scene;

import java.util.function.Consumer;

import org.saintandreas.gl.shaders.Program;

public class ShaderNode extends SceneNode {
  // mainly for debugging purposes.  the use of lambdas means I don't really need these
  @SuppressWarnings("unused")
  private final Program program;
  @SuppressWarnings("unused")
  private final Consumer<Program> closure;

  public ShaderNode(Program program, Consumer<Program> closure) {
    super(()->{
      program.link();
    }, ()->{
      program.use();
      closure.accept(program);
    }, ()->{
      Program.clear();
    });
    this.program = program;
    this.closure = closure;
  }

  public ShaderNode(Program program) {
    this(program, (p)->{});
  }
}
