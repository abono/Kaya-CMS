package com.aranya.kayacms.beans.member;

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
public class Member implements Serializable {

  private static final long serialVersionUID = -2810622681036016225L;

  private MemberId memberId;

  private String firstName;

  private String lastName;

  private String email;

  private String userName;

  private String password;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private WebSiteId webSiteId;

  public static Member.MemberBuilder builderClone(Member member) {
    return Member.builder()
        .memberId(member.getMemberId())
        .firstName(member.getFirstName())
        .lastName(member.getLastName())
        .email(member.getEmail())
        .userName(member.getUserName())
        .password(member.getPassword())
        .createDate(member.getCreateDate())
        .modifyDate(member.getModifyDate())
        .webSiteId(member.getWebSiteId());
  }
}
