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
public class WebPageTemplateSummary {

  private Long webPageTemplateId;

  private String name;

  private String nameEdits;

  private Instant createDate;

  private Instant modifyDate;

  private Instant publishDate;
}
