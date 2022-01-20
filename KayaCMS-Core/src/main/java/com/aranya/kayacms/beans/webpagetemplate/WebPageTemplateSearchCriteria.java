package com.aranya.kayacms.beans.webpagetemplate;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class WebPageTemplateSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = 1198778205650038392L;

  private final WebSiteId webSiteId;

  public WebPageTemplateSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = null;
  }

  public WebPageTemplateSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSiteId webSiteId) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSiteId = webSiteId;
  }
}
