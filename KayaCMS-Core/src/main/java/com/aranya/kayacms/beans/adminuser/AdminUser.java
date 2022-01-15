package com.aranya.kayacms.beans.adminuser;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.properties.DayAndTime;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AdminUser implements Serializable {

  private static final long serialVersionUID = 3994691643854298478L;

  private AdminUserId adminUserId;

  private String firstName;

  private String lastName;

  private String email;

  private String userName;

  private String password;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private WebSiteId webSiteId;

  public static AdminUser.AdminUserBuilder builderClone(AdminUser adminUser) {
    return AdminUser.builder()
        .adminUserId(adminUser.getAdminUserId())
        .firstName(adminUser.getFirstName())
        .lastName(adminUser.getLastName())
        .email(adminUser.getEmail())
        .userName(adminUser.getUserName())
        .password(adminUser.getPassword())
        .createDate(adminUser.getCreateDate())
        .modifyDate(adminUser.getModifyDate())
        .webSiteId(adminUser.getWebSiteId());
  }
}
