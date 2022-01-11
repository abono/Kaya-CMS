package com.aranya.kayacms.beans.adminuser;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class AdminUserSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = -5166574624914482304L;

  private final WebSite webSite;

  public AdminUserSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    webSite = null;
  }

  public AdminUserSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSite webSite) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = webSite;
  }
}
