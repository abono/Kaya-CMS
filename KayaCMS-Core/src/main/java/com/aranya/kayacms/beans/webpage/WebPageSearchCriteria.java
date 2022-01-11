package com.aranya.kayacms.beans.webpage;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class WebPageSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = -8693721091351820792L;

  private final WebSite webSite;

  public WebPageSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = null;
  }

  public WebPageSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSite webSite) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = webSite;
  }
}
