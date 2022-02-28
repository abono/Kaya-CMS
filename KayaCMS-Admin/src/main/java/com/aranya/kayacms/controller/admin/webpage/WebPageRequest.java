package com.aranya.kayacms.controller.admin.webpage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WebPageRequest {

  private String typeEdits;

  private String pathEdits;

  private String titleEdits;

  private String descriptionEdits;

  private String contentEdits;

  private String parametersEdits;

  private Long webPageTemplateIdEdits;
}
