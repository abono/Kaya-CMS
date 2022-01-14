package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.properties.DayAndTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AdminUserMapper implements RowMapper<AdminUser> {

  @Override
  public AdminUser mapRow(ResultSet rs, int rowNum) throws SQLException {
    return AdminUser.builder()
        .adminUserId(new AdminUserId(rs.getLong("admin_user_id")))
        .firstName(rs.getString("first_name"))
        .lastName(rs.getString("last_name"))
        .email(rs.getString("email"))
        .userName(rs.getString("user_name"))
        .password(rs.getString("password"))
        .createDate(new DayAndTime(rs.getTimestamp("create_date").getTime()))
        .modifyDate(new DayAndTime(rs.getTimestamp("modify_date").getTime()))
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .build();
  }
}
