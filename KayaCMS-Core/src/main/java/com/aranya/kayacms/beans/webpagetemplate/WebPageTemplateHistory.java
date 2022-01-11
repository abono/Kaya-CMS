package com.aranya.kayacms.beans.webpagetemplate;

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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "WEB_PAGE_TEMPLATE_HISTORY")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPageTemplateHistory implements Serializable {

  private static final long serialVersionUID = 6097892374101467591L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEB_PAGE_TEMPLATE_HISTORY_ID")
  private Long webPageTemplateHistoryId;

  @Column(name = "NAME", nullable = false, length = 100)
  private String name;

  @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @Column(name = "PUBLISH_DATE", nullable = false)
  private Instant publishDate;

  @ManyToOne
  @JoinColumn(name = "WEB_PAGE_TEMPLATE_ID")
  private WebPageTemplate webPageTemplate;
}
