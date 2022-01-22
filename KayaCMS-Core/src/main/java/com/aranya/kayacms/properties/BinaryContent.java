package com.aranya.kayacms.properties;

import java.io.InputStream;
import java.io.Serializable;

public interface BinaryContent extends Serializable {

  InputStream getData();

  int size();
}
