package org.saintandreas.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.common.io.Resources;

public class Images {
  public static BufferedImage load(Resource r) {
    try (InputStream is = ResourceManager.getAsInputStream(r)) {
      return ImageIO.read(is);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load resource as texture: " + r.getPath(), e);
    }
  }

  public static BufferedImage load(String resource) {
    return load(Resources.getResource(resource));
  }

  public static BufferedImage load(URL url) {
    try {
      return ImageIO.read(url);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load resource as texture: " + url.toString(), e);
    }
  }

}
