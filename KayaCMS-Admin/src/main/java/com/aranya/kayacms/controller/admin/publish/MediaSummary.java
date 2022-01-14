package com.aranya.kayacms.controller.admin.publish;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MediaSummary {

  private Long mediaId;

  private String type;

  private String path;

  private String typeEdits;

  private String pathEdits;

  private Instant createDate;

  private Instant modifyDate;

  private Instant publishDate;
}
