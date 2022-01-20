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
public class WebPageHistory implements Serializable {

  private static final long serialVersionUID = 2587176197089777712L;

  private WebPageHistoryId webPageHistoryId;

  private String type;

  private String path;

  private String title;

  private String description;

  private String content;

  private String parameters;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private DayAndTime publishDate;

  private WebSiteId webSiteId;

  private WebPageTemplateId webPageTemplateId;

  private WebPageId webPageId;
}
