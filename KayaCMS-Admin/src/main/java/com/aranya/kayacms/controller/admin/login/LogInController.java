package com.aranya.kayacms.controller.admin.login;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.controller.BaseController;
import com.aranya.kayacms.controller.admin.util.AdminSessionUtil;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.AdminUserService;
import com.aranya.kayacms.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/admin")
public class LogInController extends BaseController {

  @Autowired private AdminUserService adminUserService;

  @RequestMapping(value = "/check", method = RequestMethod.GET)
  public @ResponseBody LogInResponse isLoggedIn(HttpServletRequest request)
      throws KayaServiceException {
    LogInResponse logIn = AdminSessionUtil.getAdminUser(request);
    return logIn;
  }

  @RequestMapping(value = "/logIn", method = RequestMethod.POST)
  public @ResponseBody LogInResponse logIn(
      HttpServletRequest request, @RequestBody LogInRequest logInRequest)
      throws KayaServiceException, KayaAccessDeniedException {

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    String userName = logInRequest.getUserName();
    String password = logInRequest.getPassword();
    AdminUser adminUser = adminUserService.getAdminUser(webSiteId, userName, password);
    if (adminUser == null) {
      throw new KayaAccessDeniedException("Invalid user name or password");
    } else {
      try {
        LogInResponse logIn =
            LogInResponse.builder()
                .adminUserId(adminUser.getAdminUserId().getId())
                .firstName(adminUser.getFirstName())
                .lastName(adminUser.getLastName())
                .email(adminUser.getEmail())
                .userName(adminUser.getUserName())
                .build();
        AdminSessionUtil.setAdminUser(request, logIn);
        return logIn;
      } catch (Exception e) {
        throw new KayaServiceException(e);
      }
    }
  }

  @RequestMapping(value = "/logOut", method = RequestMethod.GET)
  public @ResponseBody boolean logOut(HttpServletRequest request) throws KayaServiceException {
    AdminSessionUtil.setAdminUser(request, null);
    return true;
  }
}
