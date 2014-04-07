package org.saintandreas.resources;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * An interface for loading resource files, abstracting the differences between
 * Android, classpath and file based resource loading
 * 
 * @author bdavis
 *
 */
public interface ResourceProvider {
  long getLastModified(Resource r);

  String getAsString(Resource r);

  ByteBuffer getAsByteBuffer(Resource r);

  InputStream getAsInputStream(Resource r);
}
