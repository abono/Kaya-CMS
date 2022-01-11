package com.aranya.kayacms.controller.admin.webpagetemplate;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WebPageTemplateResponse {

  private Long webPageTemplateId;

  private String name;

  private String content;

  private String nameEdits;

  private String contentEdits;

  private Instant createDate;

  private Instant modifyDate;

  private Instant publishDate;

  private boolean edited;
}
