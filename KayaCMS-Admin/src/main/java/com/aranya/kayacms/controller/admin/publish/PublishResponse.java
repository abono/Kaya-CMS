package com.aranya.kayacms.controller.admin.publish;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PublishResponse {

  private List<WebPageTemplateSummary> webPageTemplates;

  private List<WebPageSummary> webPages;

  private List<MediaSummary> media;
}
