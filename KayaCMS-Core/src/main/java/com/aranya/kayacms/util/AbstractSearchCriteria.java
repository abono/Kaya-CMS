package com.aranya.kayacms.util;

import lombok.Getter;

/**
 * @author Aaron
 *     <p>TODO Finish the JavaDocs for com.aranya.common.search.AbstractSearchCriteria
 */
@SuppressWarnings("serial")
@Getter
public abstract class AbstractSearchCriteria implements SearchCriteria {

  private final int itemsPerPage;

  private final int page;

  private final boolean inactiveIncluded;

  public AbstractSearchCriteria(int itemsPerPage, int page, boolean inactiveIncluded) {
    this.itemsPerPage = itemsPerPage;
    this.page = page;
    this.inactiveIncluded = inactiveIncluded;
  }

  /**
   * First is the first index of the items to be obtained. Therefore it will start at 0 for page 1.
   */
  @Override
  public int getFirst() {
    return itemsPerPage * (page - 1);
  }

  /**
   * Last is the last index of the items to be obtained. It may exceed the last index of the items
   * that are available.
   */
  @Override
  public int getLast() {
    return itemsPerPage * page;
  }
}
