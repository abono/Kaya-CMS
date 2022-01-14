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
public class WebPageSummary {

  private Long webPageId;

  private String type;

  private String path;

  private String title;

  private String description;

  private String typeEdits;

  private String pathEdits;

  private String titleEdits;

  private String descriptionEdits;

  private Instant createDate;

  private Instant modifyDate;

  private Instant publishDate;

  private Long webPageTemplateId;

  private Long webPageTemplateIdEdits;
}
