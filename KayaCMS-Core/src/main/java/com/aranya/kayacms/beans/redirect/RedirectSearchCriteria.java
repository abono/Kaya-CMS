package com.aranya.kayacms.beans.redirect;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class RedirectSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = 3147095751525948767L;

  private final WebSiteId webSiteId;

  public RedirectSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = null;
  }

  public RedirectSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSiteId webSiteId) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = webSiteId;
  }
}
