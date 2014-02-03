package org.saintandreas.resources;

import java.io.InputStream;

public class ClasspathResourceProvider extends InputStreamResourceProvider {

  public long getLastModified(Resource r) {
    return 0;
  }

  protected InputStream getInputStream(Resource r) {
    return getClass().getResourceAsStream("/" + r.getPath());
  }

}
