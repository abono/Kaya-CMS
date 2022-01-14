package com.aranya.kayacms.properties;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class EntityId implements Serializable {

  private static final long serialVersionUID = -1355750743702848852L;

  private final long id;

  @Override
  public String toString() {
    return Long.toString(id);
  }

  @Override
  public boolean equals(Object o) {
    Class<?> c = getClass();
    return o != null
        && o.getClass().isAssignableFrom(c)
        && c.isAssignableFrom(o.getClass())
        && ((EntityId) o).id == id;
  }

  @Override
  public int hashCode() {
    return (int) id;
  }
}
