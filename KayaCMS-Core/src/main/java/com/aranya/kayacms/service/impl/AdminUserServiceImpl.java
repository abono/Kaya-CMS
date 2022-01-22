package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.AdminUserDAO;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.AdminUserService;
import com.aranya.kayacms.util.SearchResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

  private final AdminUserDAO adminUserDAO;

  @Override
  public boolean isAdminUserSetUp(WebSiteId webSiteId) throws KayaServiceException {
    try {
      AdminUserSearchCriteria criteria = new AdminUserSearchCriteria(1, 1, true, webSiteId);
      long count = adminUserDAO.searchAdminUser(criteria).getTotalItems();
      return count > 0;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public SearchResults<AdminUser> searchAdminUser(AdminUserSearchCriteria criteria)
      throws KayaServiceException {
    try {
      return adminUserDAO.searchAdminUser(criteria);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser getAdminUser(WebSiteId webSiteId, String userName, String password)
      throws KayaServiceException {
    try {
      return adminUserDAO.getAdminUser(webSiteId, userName, password);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser getAdminUser(AdminUserId adminUserId) throws KayaServiceException {
    try {
      return adminUserDAO.getAdminUser(adminUserId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser createAdminUser(AdminUser adminUser) throws KayaServiceException {
    try {
      AdminUserId adminUserId = adminUserDAO.insertAdminUser(adminUser);
      return adminUserDAO.getAdminUser(adminUserId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser updateAdminUser(AdminUser adminUser) throws KayaServiceException {
    try {
      if (adminUser.getAdminUserId() == null) {
        throw new KayaServiceException("Admin user ID not set.");
      } else {
        adminUserDAO.updateAdminUser(adminUser);
        return adminUserDAO.getAdminUser(adminUser.getAdminUserId());
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteAdminUser(AdminUserId adminUserId) throws KayaServiceException {
    try {
      if (adminUserDAO.getAdminUser(adminUserId) == null) {
        throw new KayaServiceException("Admin user with given ID does not exist!");
      }
      adminUserDAO.deleteAdminUser(adminUserId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
