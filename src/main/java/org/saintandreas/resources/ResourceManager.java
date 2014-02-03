package org.saintandreas.resources;

public class ResourceManager {
  private static ResourceProvider DEFAULT_PROVIDER = new ClasspathResourceProvider();

  public static ResourceProvider getProvider() {
    return DEFAULT_PROVIDER;
  }
}
