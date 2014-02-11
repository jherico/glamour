package org.saintandreas.resources;

public class BasicResource implements Resource {
  private final String path;
  public BasicResource(String path) {
    this.path = path;
  }
  
  @Override
  public String getPath() {
    return path;
  }
}
