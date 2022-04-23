package com.aranya.kayacms.beans.website;

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
public class WebSite implements Serializable {

  private static final long serialVersionUID = -1593607673390608049L;

  private WebSiteId webSiteId;

  private String name;

  private String domainName;

  private Boolean setUpComplete;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  public static WebSite.WebSiteBuilder builderClone(WebSite webSite) {
    return builder()
        .webSiteId(webSite.getWebSiteId())
        .domainName(webSite.getDomainName())
        .setUpComplete(webSite.getSetUpComplete())
        .createDate(webSite.getCreateDate())
        .modifyDate(webSite.getModifyDate());
  }
}
