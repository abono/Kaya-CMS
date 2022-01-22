package com.aranya.kayacms.beans.media;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class MediaSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = -811466436583758791L;

  private final WebSiteId webSiteId;

  public MediaSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = null;
  }

  public MediaSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSiteId webSiteId) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = webSiteId;
  }
}
