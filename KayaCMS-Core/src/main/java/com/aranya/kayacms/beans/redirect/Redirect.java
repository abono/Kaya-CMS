package com.aranya.kayacms.beans.redirect;

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
public class Redirect implements Serializable {

  private static final long serialVersionUID = -7560216497811318370L;

  private RedirectId redirectId;

  private String fromPath;

  private String toPath;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private WebSiteId webSiteId;
}
