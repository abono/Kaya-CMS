package com.aranya.kayacms.controller.admin.login;

import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class LogInResponse implements Serializable {

  private static final long serialVersionUID = 1443832902474812940L;

  private Long adminUserId;

  private String firstName;

  private String lastName;

  private String email;

  private String userName;
}
