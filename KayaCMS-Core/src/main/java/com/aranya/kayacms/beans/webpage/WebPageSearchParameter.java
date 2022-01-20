package com.aranya.kayacms.beans.webpage;

import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class WebPageSearchParameter implements Serializable {

  private static final long serialVersionUID = 1305261802082750151L;

  private final WebPageSearchParameterId webPageSearchParameterId;

  private final String name;

  private final String value;

  private final Instant createDate;

  private final Instant modifyDate;

  private final WebPageId webPageId;
}
