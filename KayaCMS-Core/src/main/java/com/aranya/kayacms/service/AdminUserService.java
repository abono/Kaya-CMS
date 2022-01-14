package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;

public interface AdminUserService {

  boolean isAdminUserSetUp(WebSiteId webSiteId) throws KayaServiceException;

  SearchResults<AdminUser> searchAdminUser(AdminUserSearchCriteria criteria)
      throws KayaServiceException;

  AdminUser getAdminUser(WebSiteId webSiteId, String userName, String password)
      throws KayaServiceException;

  AdminUser getAdminUser(AdminUserId adminUserId) throws KayaServiceException;

  AdminUser createAdminUser(AdminUser adminUser) throws KayaServiceException;

  AdminUser updateAdminUser(AdminUser adminUser) throws KayaServiceException;

  void deleteAdminUser(AdminUserId adminUserId) throws KayaServiceException;
}
