package com.aranya.kayacms.controller.setup;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.controller.BaseController;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.AdminUserService;
import com.aranya.kayacms.util.RequestUtil;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/setup/adminUser")
public class AdminUserSetUpController extends BaseController {

  @Autowired private AdminUserService adminUserService;

  @RequestMapping(value = "/exists", method = RequestMethod.GET)
  public @ResponseBody boolean adminUserExists(HttpServletRequest request)
      throws KayaServiceException {
    WebSite webSite = RequestUtil.getWebSite(request);
    return adminUserService.isAdminUserSetUp(webSite);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody boolean createAdminUser(
      @RequestBody AdminUser adminUser, HttpServletRequest request) throws KayaServiceException {
    if (adminUserExists(request)) {
      throw new KayaServiceException("Admin user already exists!");
    }

    WebSite webSite = RequestUtil.getWebSite(request);

    AdminUser newAdminUser =
        AdminUser.builderClone(adminUser)
            .adminUserId(null)
            .createDate(Instant.now())
            .modifyDate(Instant.now())
            .webSite(webSite)
            .build();

    adminUserService.createAdminUser(newAdminUser);

    return true;
  }
}
