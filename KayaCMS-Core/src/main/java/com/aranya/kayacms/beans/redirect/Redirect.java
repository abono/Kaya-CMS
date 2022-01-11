package com.aranya.kayacms.beans.redirect;

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
    name = "REDIRECT",
    uniqueConstraints = @UniqueConstraint(columnNames = {"FROM_PATH", "WEB_SITE_ID"}))
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Redirect implements Serializable {

  private static final long serialVersionUID = -1534457586566591297L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REDIRECT_ID")
  private Long redirectId;

  @Column(name = "FROM_PATH", nullable = false, length = 1000)
  private String fromPath;

  @Column(name = "TO_PATH", nullable = false, length = 1000)
  private String toPath;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @ManyToOne
  @JoinColumn(name = "WEB_SITE_ID")
  private WebSite webSite;
}
