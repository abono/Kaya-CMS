package com.aranya.kayacms.beans.media;

import com.aranya.kayacms.beans.website.WebSite;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MEDIA_HISTORY")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MediaHistory implements Serializable {

  private static final long serialVersionUID = 2863955838666894837L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEDIA_HISTORY_ID")
  private Long mediaId;

  @Column(name = "TYPE", nullable = false, length = 100)
  private String type;

  @Column(name = "PATH", nullable = false, length = 1000)
  private String path;

  @Column(name = "CONTENT", nullable = false, columnDefinition = "BLOB")
  private byte[] content;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @Column(name = "PUBLISH_DATE", nullable = false)
  private Instant publishDate;

  @ManyToOne
  @JoinColumn(name = "WEB_SITE_ID")
  private WebSite webSite;

  public byte[] getContent() {
    return (content == null) ? null : Arrays.copyOf(content, content.length);
  }

  public void setContent(byte[] content) {
    this.content = (content == null) ? null : Arrays.copyOf(content, content.length);
  }

  public MediaHistory(
      Long mediaId,
      String type,
      String path,
      byte[] content,
      Instant createDate,
      Instant modifyDate,
      Instant publishDate,
      WebSite webSite) {
    super();
    this.mediaId = mediaId;
    this.type = type;
    this.path = path;
    this.content = (content == null) ? null : Arrays.copyOf(content, content.length);
    this.createDate = createDate;
    this.modifyDate = modifyDate;
    this.publishDate = publishDate;
    this.webSite = webSite;
  }

  public static MediaHistoryBuilder builder() {
    return new MediaHistoryBuilder();
  }

  public static class MediaHistoryBuilder {
    private Long mediaId;

    private String type;

    private String path;

    private byte[] content;

    private Instant createDate;

    private Instant modifyDate;

    private Instant publishDate;

    private WebSite webSite;

    private MediaHistoryBuilder() {}

    public MediaHistoryBuilder mediaId(Long mediaId) {
      this.mediaId = mediaId;
      return this;
    }

    public MediaHistoryBuilder type(String type) {
      this.type = type;
      return this;
    }

    public MediaHistoryBuilder path(String path) {
      this.path = path;
      return this;
    }

    public MediaHistoryBuilder content(byte[] content) {
      this.content = (content == null) ? null : Arrays.copyOf(content, content.length);
      return this;
    }

    public MediaHistoryBuilder createDate(Instant createDate) {
      this.createDate = createDate;
      return this;
    }

    public MediaHistoryBuilder modifyDate(Instant modifyDate) {
      this.modifyDate = modifyDate;
      return this;
    }

    public MediaHistoryBuilder publishDate(Instant publishDate) {
      this.publishDate = publishDate;
      return this;
    }

    public MediaHistoryBuilder webSite(WebSite webSite) {
      this.webSite = webSite;
      return this;
    }

    public MediaHistory build() {
      return new MediaHistory(
          mediaId, type, path, content, createDate, modifyDate, publishDate, webSite);
    }
  }
}
