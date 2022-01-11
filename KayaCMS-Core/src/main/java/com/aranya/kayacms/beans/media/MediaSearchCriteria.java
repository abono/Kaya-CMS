package com.aranya.kayacms.beans.media;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class MediaSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = -5884164417010420250L;

  private final WebSite webSite;

  public MediaSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = null;
  }

  public MediaSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSite webSite) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = webSite;
  }
}
