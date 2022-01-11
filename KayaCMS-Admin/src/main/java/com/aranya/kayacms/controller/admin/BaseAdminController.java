package com.aranya.kayacms.controller.admin;

import com.aranya.kayacms.controller.BaseController;
import com.aranya.kayacms.controller.admin.login.LogInResponse;
import com.aranya.kayacms.controller.admin.util.AdminSessionUtil;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import javax.servlet.http.HttpServletRequest;

public class BaseAdminController extends BaseController {

  protected void verifyLoggedIn(HttpServletRequest request) throws KayaAccessDeniedException {
    LogInResponse logIn = AdminSessionUtil.getAdminUser(request);
    if (logIn == null) {
      throw new KayaAccessDeniedException("Not logged in");
    }
  }
}
