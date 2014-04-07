package org.saintandreas.resources;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.common.io.Resources;

public class ClasspathResourceProvider extends InputStreamResourceProvider {

  private static String getResourcePath(Resource r) {
    return "/" + r.getPath();
  }
  
  private static URL getResourceUrl(Resource r) {
    URL url = Resources.getResource(getResourcePath(r));
    return url;
  }
  
  @Override
  public long getLastModified(Resource r) {
    try {
      URL url = getResourceUrl(r);
      File f = null;
      try {
        f = new File(url.toURI());
      } catch(URISyntaxException e) {
        f = new File(url.getPath());
      }
      if (null != f && f.exists()) {
        return f.lastModified();
      }
    } catch(Exception e) {
    }

    return 0;
  }

  @Override
  public InputStream getAsInputStream(Resource r) {
    return getClass().getResourceAsStream(getResourcePath(r));
  }

}
