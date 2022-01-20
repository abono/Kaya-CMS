package com.aranya.kayacms.beans.webpage;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
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
public class WebPage implements Serializable {

  private static final long serialVersionUID = 3865710742790188930L;

  private WebPageId webPageId;

  private String type;

  private String path;

  private String title;

  private String description;

  private String content;

  private String parameters;

  private String typeEdits;

  private String pathEdits;

  private String titleEdits;

  private String descriptionEdits;

  private String contentEdits;

  private String parametersEdits;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private DayAndTime publishDate;

  private WebSiteId webSiteId;

  private WebPageTemplateId webPageTemplateId;

  private WebPageTemplateId webPageTemplateIdEdits;

  public static WebPage.WebPageBuilder builderClone(WebPage webPage) {
    return builder()
        .webPageId(webPage.getWebPageId())
        .type(webPage.getType())
        .path(webPage.getPath())
        .title(webPage.getTitle())
        .description(webPage.getDescription())
        .content(webPage.getContent())
        .parameters(webPage.getParameters())
        .typeEdits(webPage.getTypeEdits())
        .pathEdits(webPage.getPathEdits())
        .titleEdits(webPage.getTitleEdits())
        .descriptionEdits(webPage.getDescriptionEdits())
        .contentEdits(webPage.getContentEdits())
        .parametersEdits(webPage.getParametersEdits())
        .createDate(webPage.getCreateDate())
        .modifyDate(webPage.getModifyDate())
        .publishDate(webPage.getPublishDate())
        .webSiteId(webPage.getWebSiteId())
        .webPageTemplateId(webPage.getWebPageTemplateId())
        .webPageTemplateIdEdits(webPage.getWebPageTemplateIdEdits());
  }
}
