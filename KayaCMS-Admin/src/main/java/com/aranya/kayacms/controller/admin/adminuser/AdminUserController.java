package com.aranya.kayacms.controller.admin.adminuser;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.controller.admin.BaseAdminController;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaResourceNotFoundException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.AdminUserService;
import com.aranya.kayacms.util.ListSearchResults;
import com.aranya.kayacms.util.RequestUtil;
import com.aranya.kayacms.util.SearchResults;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/adminUser")
@AllArgsConstructor
public class AdminUserController extends BaseAdminController {

  private final AdminUserService adminUserService;

  private void populateBase(AdminUser item, AdminUserResponse response) {
    response.setAdminUserId(item.getAdminUserId().getId());
    response.setFirstName(item.getFirstName());
    response.setLastName(item.getLastName());
    response.setEmail(item.getEmail());
    response.setUserName(item.getUserName());

    response.setCreateDate(item.getCreateDate().getDate());
    response.setModifyDate(item.getCreateDate().getDate());
  }

  private AdminUserResponse convertSearchItem(AdminUser item) {
    AdminUserResponse response = new AdminUserResponse();

    populateBase(item, response);

    return response;
  }

  private AdminUserResponse convertItem(AdminUser item) {
    AdminUserResponse response = new AdminUserResponse();

    populateBase(item, response);

    return response;
  }

  @GetMapping
  public SearchResults<AdminUserResponse> search(
      HttpServletRequest request,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "itemsPerPage", defaultValue = "50", required = false) int itemsPerPage)
      throws KayaServiceException, KayaAccessDeniedException {

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    verifyLoggedIn(request);

    AdminUserSearchCriteria criteria =
        new AdminUserSearchCriteria(itemsPerPage, page, false, webSiteId);
    SearchResults<AdminUser> searchResults = adminUserService.searchAdminUser(criteria);
    List<AdminUserResponse> items =
        searchResults.getItems().stream()
            .map(item -> convertSearchItem(item))
            .collect(Collectors.toList());
    return new ListSearchResults<AdminUserResponse>(
        items, page, itemsPerPage, searchResults.getPageCount(), searchResults.getTotalItems());
  }

  @GetMapping("/{id}")
  public @ResponseBody AdminUserResponse get(
      HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    AdminUser adminUser = adminUserService.getAdminUser(new AdminUserId(id));
    if (adminUser == null || !adminUser.getWebSiteId().equals(webSiteId)) {
      throw new KayaResourceNotFoundException(
          "Admin user not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    return convertItem(adminUser);
  }

  @PostMapping
  public ResponseEntity<AdminUserResponse> create(
      HttpServletRequest request, @RequestBody AdminUserRequest adminUserRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException {

    verifyLoggedIn(request);

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    AdminUser adminUser =
        AdminUser.builder()
            .firstName(adminUserRequest.getFirstName())
            .lastName(adminUserRequest.getLastName())
            .email(adminUserRequest.getEmail())
            .userName(adminUserRequest.getUserName())
            .password(adminUserRequest.getPassword())
            .webSiteId(webSiteId)
            .build();

    adminUser = adminUserService.createAdminUser(adminUser);

    return ResponseEntity.created(new URI("/api/admin/adminUser/" + adminUser.getAdminUserId()))
        .body(convertItem(adminUser));
  }

  @PutMapping("/{id}")
  public ResponseEntity<AdminUserResponse> update(
      HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody AdminUserRequest adminUserRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException,
          KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    AdminUser adminUser = adminUserService.getAdminUser(new AdminUserId(id));
    if (adminUser == null || !adminUser.getWebSiteId().equals(webSiteId)) {
      throw new KayaResourceNotFoundException(
          "Admin user not found", "entity.not.found", Collections.singletonMap("id", id));
    }

    adminUser =
        AdminUser.builder()
            .adminUserId(new AdminUserId(id))
            .firstName(adminUserRequest.getFirstName())
            .lastName(adminUserRequest.getLastName())
            .email(adminUserRequest.getEmail())
            .userName(adminUserRequest.getUserName())
            .password(
                StringUtils.isBlank(adminUserRequest.getPassword())
                    ? adminUser.getPassword()
                    : adminUserRequest.getPassword())
            .webSiteId(webSiteId)
            .build();

    adminUser = adminUserService.updateAdminUser(adminUser);

    return ResponseEntity.created(new URI("/api/admin/adminUser/" + adminUser.getAdminUserId()))
        .body(convertItem(adminUser));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AdminUserResponse> deleteAdminUser(
      HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException,
          KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    AdminUser adminUser = adminUserService.getAdminUser(new AdminUserId(id));
    if (adminUser == null || !adminUser.getWebSiteId().equals(webSiteId)) {
      throw new KayaResourceNotFoundException(
          "Admin user not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    adminUserService.deleteAdminUser(new AdminUserId(id));

    return ResponseEntity.ok().build();
  }
}
