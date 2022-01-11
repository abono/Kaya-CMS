package com.aranya.kayacms.beans.webpagetemplate;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.util.AbstractSearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class WebPageTemplateSearchCriteria extends AbstractSearchCriteria {

  private static final long serialVersionUID = 3254422315223114293L;

  private final WebSite webSite;

  public WebPageTemplateSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = null;
  }

  public WebPageTemplateSearchCriteria(
      int itemsPerPage, int page, boolean inactiveIncluded, WebSite webSite) {
    super(itemsPerPage, page, inactiveIncluded);
    this.webSite = webSite;
  }
}
