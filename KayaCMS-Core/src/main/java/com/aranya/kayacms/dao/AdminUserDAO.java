package com.aranya.kayacms.dao;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;

public interface AdminUserDAO {

  SearchResults<AdminUser> searchAdminUser(AdminUserSearchCriteria criteria) throws SQLException;

  AdminUser getAdminUser(WebSiteId webSiteId, String userName, String password) throws SQLException;

  AdminUser getAdminUser(AdminUserId adminUserId) throws SQLException;

  AdminUserId insertAdminUser(AdminUser adminUser) throws SQLException;

  void updateAdminUser(AdminUser adminUser) throws SQLException;

  void deleteAdminUser(AdminUserId adminUserId) throws SQLException;
}
