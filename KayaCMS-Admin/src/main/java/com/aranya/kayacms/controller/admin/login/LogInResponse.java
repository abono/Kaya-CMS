package com.aranya.kayacms.controller.admin.login;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LogInResponse {

  private Long adminUserId;

  private String firstName;

  private String lastName;

  private String email;

  private String userName;
}
