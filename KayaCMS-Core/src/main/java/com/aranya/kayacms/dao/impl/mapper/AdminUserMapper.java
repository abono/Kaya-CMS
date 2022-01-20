package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserMapper extends AbstractRowMaster<AdminUser> {

  @Override
  public AdminUser mapRow(ResultSet rs, int rowNum) throws SQLException {
    return AdminUser.builder()
        .adminUserId(new AdminUserId(rs.getLong("admin_user_id")))
        .firstName(rs.getString("first_name"))
        .lastName(rs.getString("last_name"))
        .email(rs.getString("email"))
        .userName(rs.getString("user_name"))
        .password(rs.getString("password"))
        .createDate(extractDayAndTime(rs, "create_date"))
        .modifyDate(extractDayAndTime(rs, "modify_date"))
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .build();
  }
}
