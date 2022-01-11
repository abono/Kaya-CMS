package com.aranya.kayacms.beans.webpage;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "WEB_PAGE_SEARCH_PARAM")
@Getter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class WebPageSearchParameter implements Serializable {

  private static final long serialVersionUID = 8614013010140334062L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEB_PAGE_SEARCH_PARAM_ID")
  private final Long webPageSearchParameterId;

  @Column(name = "NAME", nullable = false, length = 100)
  private final String name;

  @Column(name = "VALUE", nullable = false, length = 255)
  private final String value;

  @Column(name = "CREATE_DATE", nullable = false)
  private final Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private final Instant modifyDate;

  @ManyToOne
  @JoinColumn(name = "WEB_PAGE_ID")
  private final WebPage webPage;
}
