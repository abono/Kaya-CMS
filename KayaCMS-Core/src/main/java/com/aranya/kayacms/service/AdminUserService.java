package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;

public interface AdminUserService {

  boolean isAdminUserSetUp(WebSite webSite) throws KayaServiceException;

  SearchResults<AdminUser> searchAdminUser(AdminUserSearchCriteria criteria)
      throws KayaServiceException;

  AdminUser getAdminUser(WebSite webSite, String userName, String password)
      throws KayaServiceException;

  AdminUser getAdminUser(Long adminUserId) throws KayaServiceException;

  AdminUser createAdminUser(AdminUser adminUser) throws KayaServiceException;

  AdminUser updateAdminUser(AdminUser adminUser) throws KayaServiceException;

  void deleteAdminUser(Long adminUserId) throws KayaServiceException;
}
