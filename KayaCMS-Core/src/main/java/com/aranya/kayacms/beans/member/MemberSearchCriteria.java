package com.aranya.kayacms.beans.member;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class MemberSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = -5166574624914482304L;

  private final WebSite webSite;

  public MemberSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    webSite = null;
  }

  public MemberSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSite webSite) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = webSite;
  }
}
