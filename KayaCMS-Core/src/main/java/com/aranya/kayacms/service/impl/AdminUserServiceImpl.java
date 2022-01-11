package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.AdminUserRepository;
import com.aranya.kayacms.service.AdminUserService;
import com.aranya.kayacms.util.ListSearchResults;
import com.aranya.kayacms.util.SearchResults;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

  private final AdminUserRepository adminUserRepository;

  @Override
  public boolean isAdminUserSetUp(WebSite webSite) throws KayaServiceException {
    try {
      AdminUser adminUser = AdminUser.builder().webSite(webSite).build();
      Example<AdminUser> example = Example.of(adminUser);
      long count = adminUserRepository.count(example);
      return count > 0;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public SearchResults<AdminUser> searchAdminUser(AdminUserSearchCriteria criteria)
      throws KayaServiceException {
    try {
      Sort sort = Sort.by("userName");
      Pageable pageable = PageRequest.of(criteria.getPage() - 1, criteria.getItemsPerPage(), sort);

      AdminUser adminUser = AdminUser.builder().webSite(criteria.getWebSite()).build();
      Example<AdminUser> example = Example.of(adminUser);

      Page<AdminUser> results = adminUserRepository.findAll(example, pageable);

      return new ListSearchResults<AdminUser>(
          results.getContent(),
          criteria.getPage(),
          criteria.getItemsPerPage(),
          results.getTotalPages());
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser getAdminUser(WebSite webSite, String userName, String password)
      throws KayaServiceException {
    try {
      AdminUser adminUser =
          AdminUser.builder().userName(userName).password(password).webSite(webSite).build();

      Example<AdminUser> example = Example.of(adminUser, ExampleMatcher.matchingAll());

      Optional<AdminUser> optional = adminUserRepository.findOne(example);
      if (optional.isPresent()) {
        return optional.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser getAdminUser(Long adminUserId) throws KayaServiceException {
    try {
      Optional<AdminUser> optional = adminUserRepository.findById(adminUserId);
      if (optional.isPresent()) {
        return optional.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser createAdminUser(AdminUser adminUser) throws KayaServiceException {
    try {
      AdminUser newAdminUser =
          AdminUser.builderClone(adminUser)
              .adminUserId(null)
              .createDate(Instant.now())
              .modifyDate(Instant.now())
              .build();

      adminUser = adminUserRepository.save(newAdminUser);

      return adminUser;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public AdminUser updateAdminUser(AdminUser entity) throws KayaServiceException {
    try {
      if (entity.getAdminUserId() == null) {
        throw new KayaServiceException("Admin user ID not set.");
      } else {
        Optional<AdminUser> adminUser = adminUserRepository.findById(entity.getAdminUserId());

        if (adminUser.isPresent()) {
          AdminUser newEntity =
              AdminUser.builderClone(adminUser.get())
                  .firstName(entity.getFirstName())
                  .lastName(entity.getLastName())
                  .email(entity.getEmail())
                  .userName(entity.getUserName())
                  .password(entity.getPassword())
                  .modifyDate(Instant.now())
                  .build();

          newEntity = adminUserRepository.save(newEntity);

          return newEntity;
        } else {
          throw new KayaServiceException("Admin user with given ID does not exist!");
        }
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteAdminUser(Long adminUserId) throws KayaServiceException {
    try {
      Optional<AdminUser> adminUser = adminUserRepository.findById(adminUserId);

      if (adminUser.isPresent()) {
        adminUserRepository.deleteById(adminUserId);
      } else {
        throw new KayaServiceException("Admin user with given ID does not exist!");
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
