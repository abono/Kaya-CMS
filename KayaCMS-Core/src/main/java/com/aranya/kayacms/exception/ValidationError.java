package com.aranya.kayacms.exception;

import java.io.Serializable;
import lombok.Data;

@Data
public class ValidationError implements Serializable {

  private static final long serialVersionUID = 1L;

  private String propertyName;

  private String errorMessage;
}
