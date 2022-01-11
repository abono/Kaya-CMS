package com.aranya.kayacms.controller.admin.adminuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AdminUserRequest {

  private String firstName;

  private String lastName;

  private String email;

  private String userName;

  private String password;
}
