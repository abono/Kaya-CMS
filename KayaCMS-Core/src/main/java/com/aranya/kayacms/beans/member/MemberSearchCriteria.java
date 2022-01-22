package com.aranya.kayacms.beans.member;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class MemberSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = 3988242555604938632L;

  private final WebSiteId webSiteId;

  public MemberSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    webSiteId = null;
  }

  public MemberSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSiteId webSiteId) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = webSiteId;
  }
}
