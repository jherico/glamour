package org.saintandreas.resources;

public class BasicResource implements Resource {
  private final String path;
  public BasicResource(String path) {
    this.path = path;
  }
  public String getPath() {
    return path;
  }
}
