package com.aranya.kayacms.beans.webpage;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
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
@Table(name = "WEB_PAGE_HISTORY")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPageHistory implements Serializable {

  private static final long serialVersionUID = -3293885849422219401L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEB_PAGE_HISTORY_ID")
  private Long webPageHistoryId;

  @Column(name = "TYPE", nullable = false, length = 50)
  private String type;

  @Column(name = "PATH", nullable = false, length = 1000)
  private String path;

  @Column(name = "TITLE", nullable = false, length = 255)
  private String title;

  @Column(name = "DESCRIPTION", nullable = false, length = 4000)
  private String description;

  @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "PARAMETERS", nullable = false, columnDefinition = "TEXT")
  private String parameters;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @Column(name = "PUBLISH_DATE", nullable = false)
  private Instant publishDate;

  @ManyToOne
  @JoinColumn(name = "WEB_PAGE_TEMPLATE_ID")
  private WebPageTemplate webPageTemplate;

  @ManyToOne
  @JoinColumn(name = "WEB_PAGE_ID")
  private WebPage webPage;
}
