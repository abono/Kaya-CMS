package com.aranya.kayacms.beans.webpage;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
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
    name = "WEB_PAGE",
    uniqueConstraints = @UniqueConstraint(columnNames = {"PATH", "WEB_SITE_ID"}))
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPage implements Serializable {

  private static final long serialVersionUID = -8171353773045436501L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEB_PAGE_ID")
  private Long webPageId;

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

  @Column(name = "TYPE_EDITS", nullable = false, length = 50)
  private String typeEdits;

  @Column(name = "PATH_EDITS", nullable = false, length = 1000)
  private String pathEdits;

  @Column(name = "TITLE_EDITS", nullable = false, length = 255)
  private String titleEdits;

  @Column(name = "DESCRIPTION_EDITS", nullable = false, length = 4000)
  private String descriptionEdits;

  @Column(name = "CONTENT_EDITS", nullable = false, columnDefinition = "TEXT")
  private String contentEdits;

  @Column(name = "PARAMETERS_EDITS", nullable = false, columnDefinition = "TEXT")
  private String parametersEdits;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @Column(name = "PUBLISH_DATE", nullable = true)
  private Instant publishDate;

  @ManyToOne
  @JoinColumn(name = "WEB_SITE_ID")
  private WebSite webSite;

  @ManyToOne
  @JoinColumn(name = "WEB_PAGE_TEMPLATE_ID")
  private WebPageTemplate webPageTemplate;

  @ManyToOne
  @JoinColumn(name = "WEB_PAGE_TEMPLATE_ID_EDITS")
  private WebPageTemplate webPageTemplateEdits;

  public static WebPage.WebPageBuilder builderClone(WebPage webPage) {
    return builder()
        .webPageId(webPage.getWebPageId())
        .type(webPage.getType())
        .path(webPage.getPath())
        .title(webPage.getTitle())
        .description(webPage.getDescription())
        .content(webPage.getContent())
        .parameters(webPage.getParameters())
        .typeEdits(webPage.getTypeEdits())
        .pathEdits(webPage.getPathEdits())
        .titleEdits(webPage.getTitleEdits())
        .descriptionEdits(webPage.getDescriptionEdits())
        .contentEdits(webPage.getContentEdits())
        .parametersEdits(webPage.getParametersEdits())
        .createDate(webPage.getCreateDate())
        .modifyDate(webPage.getModifyDate())
        .publishDate(webPage.getPublishDate())
        .webSite(webPage.getWebSite())
        .webPageTemplate(webPage.getWebPageTemplate())
        .webPageTemplateEdits(webPage.getWebPageTemplateEdits());
  }
}
