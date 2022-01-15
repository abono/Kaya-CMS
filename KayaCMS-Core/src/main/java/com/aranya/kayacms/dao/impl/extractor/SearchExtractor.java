package com.aranya.kayacms.dao.impl.extractor;

import com.aranya.kayacms.util.ListSearchResults;
import com.aranya.kayacms.util.SearchCriteria;
import com.aranya.kayacms.util.SearchResults;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

@RequiredArgsConstructor
public class SearchExtractor<T> implements ResultSetExtractor<SearchResults<T>> {

  private final SearchCriteria criteria;

  private final RowMapper<T> mapper;

  @Override
  public SearchResults<T> extractData(ResultSet rs) throws SQLException, DataAccessException {

    int index = 0;
    int start = criteria.getFirst();
    int end = criteria.getLast();
    int itemsPerPage = criteria.getItemsPerPage();
    List<T> adminUserList = new ArrayList<>(itemsPerPage);
    while (rs.next()) {
      if (index >= start && index <= end) {
        adminUserList.add(mapper.mapRow(rs, index));
      }
      ++index;
    }

    int page = criteria.getPage();
    int pageCount = index / itemsPerPage + (index % itemsPerPage != 0 ? 1 : 0);
    return new ListSearchResults<T>(adminUserList, page, itemsPerPage, pageCount, index);
  }
}
