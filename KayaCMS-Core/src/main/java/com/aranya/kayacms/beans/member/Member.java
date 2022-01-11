package com.aranya.kayacms.beans.member;

import com.aranya.kayacms.beans.website.WebSite;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "MEMBER",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"EMAIL", "WEB_SITE_ID"}),
      @UniqueConstraint(columnNames = {"USER_NAME", "WEB_SITE_ID"})
    })
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member implements Serializable {

  private static final long serialVersionUID = 3994691643854298478L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEMBER_ID")
  private Long memberId;

  @Column(name = "FIRST_NAME", nullable = false, length = 100)
  private String firstName;

  @Column(name = "LAST_NAME", nullable = false, length = 100)
  private String lastName;

  @Column(name = "EMAIL", nullable = false, length = 255)
  private String email;

  @Column(name = "USER_NAME", nullable = false, length = 100)
  private String userName;

  @Column(name = "PASSWORD", nullable = false, columnDefinition = "TEXT")
  private String password;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @ManyToOne
  @JoinColumn(name = "WEB_SITE_ID")
  private WebSite webSite;

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
        .webSite(member.getWebSite());
  }
}
