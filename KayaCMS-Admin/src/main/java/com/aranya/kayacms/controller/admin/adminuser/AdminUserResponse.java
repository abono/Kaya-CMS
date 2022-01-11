package com.aranya.kayacms.controller.admin.adminuser;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AdminUserResponse {

  private Long adminUserId;

  private String firstName;

  private String lastName;

  private String email;

  private String userName;

  private Instant createDate;

  private Instant modifyDate;
}
