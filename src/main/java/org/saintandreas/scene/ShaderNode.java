package org.saintandreas.scene;

import org.saintandreas.gl.shaders.Program;

public class ShaderNode extends SceneNode {
  public ShaderNode(Program program, Runnable closure) {
    super(()->{
      program.link();
    }, ()->{
      program.use();
      closure.run();
    }, ()->{
      Program.clear();
    });
  }

  public ShaderNode(Program program) {
    this(program, ()->{});
  }
}
