package org.saintandreas.resources;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class ResourceManager {
  private static ResourceProvider DEFAULT_PROVIDER = new ClasspathResourceProvider();

  public static ResourceProvider getProvider() {
    return DEFAULT_PROVIDER;
  }

  public static long getLastModified(Resource r) {
    return getProvider().getLastModified(r);
  }

  public static String getAsString(Resource r) {
    return getProvider().getAsString(r);
  }

  public static ByteBuffer getAsByteBuffer(Resource r) {
    return getProvider().getAsByteBuffer(r);
  }

  public static InputStream getAsInputStream(Resource r) {
    return getProvider().getAsInputStream(r);
  }
}
