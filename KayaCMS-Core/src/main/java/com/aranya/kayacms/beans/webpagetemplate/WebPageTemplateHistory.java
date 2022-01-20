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
public class WebPageTemplateHistory implements Serializable {

  private static final long serialVersionUID = -7166201744084085797L;

  private WebPageTemplateHistoryId webPageTemplateHistoryId;

  private String name;

  private String content;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private DayAndTime publishDate;

  private WebSiteId webSiteId;

  private WebPageTemplateId webPageTemplateId;
}
