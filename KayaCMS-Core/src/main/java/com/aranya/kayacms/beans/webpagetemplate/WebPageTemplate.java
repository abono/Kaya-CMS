package com.aranya.kayacms.beans.webpagetemplate;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.properties.DayAndTime;
import java.io.Serializable;
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
public class WebPageTemplate implements Serializable {

  private static final long serialVersionUID = -3793488307058119687L;

  private WebPageTemplateId webPageTemplateId;

  private String name;

  private String content;

  private String nameEdits;

  private String contentEdits;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private DayAndTime publishDate;

  private WebSiteId webSiteId;

  public static WebPageTemplate.WebPageTemplateBuilder builderClone(
      WebPageTemplate webPageTemplate) {
    return builder()
        .webPageTemplateId(webPageTemplate.getWebPageTemplateId())
        .name(webPageTemplate.getName())
        .content(webPageTemplate.getContent())
        .nameEdits(webPageTemplate.nameEdits)
        .contentEdits(webPageTemplate.getContentEdits())
        .createDate(webPageTemplate.getCreateDate())
        .modifyDate(webPageTemplate.getModifyDate())
        .publishDate(webPageTemplate.getPublishDate())
        .webSiteId(webPageTemplate.getWebSiteId());
  }
}
