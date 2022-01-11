package com.aranya.kayacms.beans.webpagetemplate;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "WEB_PAGE_TEMPLATE")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPageTemplate implements Serializable {

  private static final long serialVersionUID = -5987817225892610511L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEB_PAGE_TEMPLATE_ID")
  private Long webPageTemplateId;

  @Column(name = "NAME", nullable = false, length = 100)
  private String name;

  @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "NAME_EDITS", nullable = false, length = 100)
  private String nameEdits;

  @Column(name = "CONTENT_EDITS", nullable = false, columnDefinition = "TEXT")
  private String contentEdits;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @Column(name = "PUBLISH_DATE", nullable = true)
  private Instant publishDate;

  @ManyToOne
  @JoinColumn(name = "WEB_SITE_ID")
  private WebSite webSite;

  public static WebPageTemplate.WebPageTemplateBuilder builderClone(
      WebPageTemplate webPageTemplate) {
    return builder()
        .webPageTemplateId(webPageTemplate.getWebPageTemplateId())
        .name(webPageTemplate.getName())
        .content(webPageTemplate.getContent())
        .nameEdits(webPageTemplate.nameEdits)
        .contentEdits(webPageTemplate.getContentEdits())
        .createDate(webPageTemplate.getCreateDate())
        .modifyDate(webPageTemplate.getModifyDate())
        .publishDate(webPageTemplate.getPublishDate())
        .webSite(webPageTemplate.getWebSite());
  }
}
