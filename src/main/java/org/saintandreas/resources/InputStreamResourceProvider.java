package org.saintandreas.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

public abstract class InputStreamResourceProvider implements ResourceProvider {
  protected abstract InputStream getInputStream(Resource r) throws IOException;

  public String getAsString(Resource r) {
    try (InputStream is = getInputStream(r)) {
      return new String(ByteStreams.toByteArray(is), Charsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public ByteBuffer getAsByteBuffer(Resource r) {
    try (InputStream is = getInputStream(r)) {
      return ByteBuffer.wrap(ByteStreams.toByteArray(is));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
