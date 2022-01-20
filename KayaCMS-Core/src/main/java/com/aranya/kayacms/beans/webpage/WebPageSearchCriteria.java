package com.aranya.kayacms.beans.webpage;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class WebPageSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = 3878171034694693181L;

  private final WebSiteId webSiteId;

  public WebPageSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = null;
  }

  public WebPageSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSiteId webSiteId) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = webSiteId;
  }
}
