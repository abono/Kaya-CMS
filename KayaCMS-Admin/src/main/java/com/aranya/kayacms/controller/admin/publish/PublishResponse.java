package com.aranya.kayacms.controller.admin.publish;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PublishResponse {

  private List<WebPageTemplateSummary> webPageTemplates;

  private List<WebPageSummary> webPages;

  private List<MediaSummary> media;

  public List<WebPageTemplateSummary> getWebPageTemplates() {
    return webPageTemplates == null
        ? null
        : new ArrayList<WebPageTemplateSummary>(webPageTemplates);
  }

  public List<WebPageSummary> getWebPages() {
    return webPages == null ? null : new ArrayList<WebPageSummary>(webPages);
  }

  public List<MediaSummary> getMedia() {
    return media == null ? null : new ArrayList<MediaSummary>(media);
  }
}
