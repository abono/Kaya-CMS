package com.aranya.kayacms.controller.admin.login;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LogInRequest {

  private String userName;

  private String password;
}
