package org.saintandreas.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FilesystemResourceProvider extends InputStreamResourceProvider {
  private final File basePath;

  public FilesystemResourceProvider(File file) {
    this.basePath = file;
  }

  public FilesystemResourceProvider(String file) {
    this(new File(file));
  }

  public long getLastModified(Resource r) {
    return getFile(r).lastModified();
  }

  protected File getFile(Resource r) {
    return new File(basePath, r.getPath());
  }

  protected InputStream getInputStream(Resource r) throws FileNotFoundException {
    return new FileInputStream(getFile(r));
  }
}
