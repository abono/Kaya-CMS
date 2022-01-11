package com.aranya.kayacms.beans.website;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "WEB_SITE")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSite implements Serializable {

  private static final long serialVersionUID = 7369953167005887135L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEB_SITE_ID")
  private Long webSiteId;

  @Column(name = "DOMAIN_NAME", nullable = false, length = 255, unique = true)
  private String domainName;

  @Column(name = "SET_UP_COMPLETE", nullable = false)
  private Boolean setUpComplete;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  public static WebSite.WebSiteBuilder builderClone(WebSite webSite) {
    return builder()
        .webSiteId(webSite.getWebSiteId())
        .domainName(webSite.getDomainName())
        .setUpComplete(webSite.getSetUpComplete())
        .createDate(webSite.getCreateDate())
        .modifyDate(webSite.getModifyDate());
  }
}
