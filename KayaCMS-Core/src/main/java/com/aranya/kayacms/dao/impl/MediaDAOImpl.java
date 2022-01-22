package com.aranya.kayacms.dao.impl;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaId;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.MediaDAO;
import com.aranya.kayacms.dao.impl.extractor.SearchExtractor;
import com.aranya.kayacms.dao.impl.mapper.MediaRowMapper;
import com.aranya.kayacms.db.DetailedSQLException;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MediaDAOImpl extends AbstractDAO implements MediaDAO {

  private final MediaRowMapper rowMapper = new MediaRowMapper(false);

  private final MediaRowMapper summaryMapper = new MediaRowMapper(true);

  @Autowired
  public MediaDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate, "media");
  }

  private String buildWhereClause(MediaSearchCriteria criteria, Map<String, Object> paramMap) {
    StringBuilder builder = new StringBuilder("where 1 = 1 ");

    WebSiteId webSiteId = criteria.getWebSiteId();
    if (webSiteId != null) {
      builder.append("and web_site_id = :webSiteId ");
      paramMap.put("webSiteId", webSiteId.getId());
    }

    return builder.toString();
  }

  @Override
  public SearchResults<Media> searchMedia(MediaSearchCriteria criteria) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    String whereClause = buildWhereClause(criteria, paramMap);

    String sql = getSQL("select.sql");

    try {
      return jdbcTemplate.query(
          sql + " " + whereClause, paramMap, new SearchExtractor<Media>(criteria, summaryMapper));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public List<Media> getUnpublishedMedia(WebSiteId webSiteId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());

    String sql = getSQL("selectUnpublished.sql");

    try {
      return jdbcTemplate.query(sql, paramMap, summaryMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public Media getMedia(WebSiteId webSiteId, String path) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());
    paramMap.put("path", path);

    String sql = getSQL("select.sql") + " where web_site_id = :webSiteId and path = :path";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public Media getMedia(MediaId mediaId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("mediaId", mediaId.getId());

    String sql = getSQL("select.sql") + " where media_id = :mediaId";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public MediaId insertMedia(Media media) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("typeEdits", media.getTypeEdits());
    paramMap.put("pathEdits", media.getPathEdits());
    paramMap.put("contentEdits", media.getContentEdits());
    paramMap.put("webSiteId", media.getWebSiteId().getId());

    String sql = getSQL("insert.sql");
    String sqlForId = getSQL("getLastId.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
      return jdbcTemplate.queryForObject(
          sqlForId, paramMap, (rs, rowNum) -> new MediaId(rs.getLong("media_id")));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void updateMedia(Media media) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("mediaId", media.getMediaId().getId());
    paramMap.put("typeEdits", media.getTypeEdits());
    paramMap.put("pathEdits", media.getPathEdits());
    paramMap.put("contentEdits", media.getContentEdits());

    String sql = getSQL("update.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void publishMedia(MediaId mediaId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("mediaId", mediaId.getId());

    String sql = getSQL("publish.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void deleteMedia(MediaId mediaId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("mediaId", mediaId.getId());

    String sql = getSQL("delete.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }
}
