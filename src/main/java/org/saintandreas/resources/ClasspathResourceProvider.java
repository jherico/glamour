package org.saintandreas.resources;

import java.io.InputStream;

public class ClasspathResourceProvider extends InputStreamResourceProvider {

  @Override
  public long getLastModified(Resource r) {
    return 0;
  }

  @Override
  protected InputStream getInputStream(Resource r) {
    String resourcePath = "/" + r.getPath();
    return getClass().getResourceAsStream(resourcePath);
  }

}
