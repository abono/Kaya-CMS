package com.aranya.kayacms.properties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ByteArrayBinaryContent implements BinaryContent {

  private static final long serialVersionUID = 7979349525338829558L;

  private final byte[] bytes;

  public ByteArrayBinaryContent(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override
  public InputStream getData() {
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public int size() {
    return bytes.length;
  }
}
