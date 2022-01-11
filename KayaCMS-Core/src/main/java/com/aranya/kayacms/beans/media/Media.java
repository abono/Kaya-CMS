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
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MEDIA", uniqueConstraints = @UniqueConstraint(columnNames = {"PATH", "WEB_SITE_ID"}))
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Media implements Serializable {

  private static final long serialVersionUID = 5630298641037940679L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEDIA_ID")
  private Long mediaId;

  @Column(name = "TYPE", nullable = false, length = 100)
  private String type;

  @Column(name = "PATH", nullable = false, length = 1000)
  private String path;

  @Column(name = "CONTENT", nullable = false, columnDefinition = "BLOB")
  private byte[] content;

  @Column(name = "TYPE_EDITS", nullable = false, length = 50)
  private String typeEdits;

  @Column(name = "PATH_EDITS", nullable = false, length = 1000)
  private String pathEdits;

  @Column(name = "CONTENT_EDITS", nullable = false, columnDefinition = "BLOB")
  private byte[] contentEdits;

  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Column(name = "MODIFY_DATE", nullable = false)
  private Instant modifyDate;

  @Column(name = "PUBLISH_DATE", nullable = true)
  private Instant publishDate;

  @ManyToOne
  @JoinColumn(name = "WEB_SITE_ID")
  private WebSite webSite;

  public Media(
      Long mediaId,
      String type,
      String path,
      byte[] content,
      String typeEdits,
      String pathEdits,
      byte[] contentEdits,
      Instant createDate,
      Instant modifyDate,
      Instant publishDate,
      WebSite webSite) {
    super();
    this.mediaId = mediaId;
    this.type = type;
    this.path = path;
    this.content = (content == null) ? null : Arrays.copyOf(content, content.length);
    this.typeEdits = typeEdits;
    this.pathEdits = pathEdits;
    this.contentEdits =
        (contentEdits == null) ? null : Arrays.copyOf(contentEdits, contentEdits.length);
    this.createDate = createDate;
    this.modifyDate = modifyDate;
    this.publishDate = publishDate;
    this.webSite = webSite;
  }

  public byte[] getContent() {
    return (content == null) ? null : Arrays.copyOf(content, content.length);
  }

  public void setContent(byte[] content) {
    this.content = (content == null) ? null : Arrays.copyOf(content, content.length);
  }

  public byte[] getContentEdits() {
    return (contentEdits == null) ? null : Arrays.copyOf(contentEdits, contentEdits.length);
  }

  public void setContentEdits(byte[] contentEdits) {
    this.contentEdits =
        (contentEdits == null) ? null : Arrays.copyOf(contentEdits, contentEdits.length);
  }

  public static MediaBuilder builderClone(Media media) {
    return Media.builder()
        .mediaId(media.getMediaId())
        .type(media.getType())
        .path(media.getPath())
        .content(media.getContent())
        .typeEdits(media.getTypeEdits())
        .pathEdits(media.getPathEdits())
        .contentEdits(media.getContentEdits())
        .createDate(media.getCreateDate())
        .modifyDate(media.getModifyDate())
        .publishDate(media.getPublishDate())
        .webSite(media.getWebSite());
  }

  public static MediaBuilder builder() {
    return new MediaBuilder();
  }

  public static class MediaBuilder {
    private Long mediaId;

    private String type;

    private String path;

    private byte[] content;

    private String typeEdits;

    private String pathEdits;

    private byte[] contentEdits;

    private Instant createDate;

    private Instant modifyDate;

    private Instant publishDate;

    private WebSite webSite;

    private MediaBuilder() {}

    public MediaBuilder mediaId(Long mediaId) {
      this.mediaId = mediaId;
      return this;
    }

    public MediaBuilder type(String type) {
      this.type = type;
      return this;
    }

    public MediaBuilder path(String path) {
      this.path = path;
      return this;
    }

    public MediaBuilder content(byte[] content) {
      this.content = (content == null) ? null : Arrays.copyOf(content, content.length);
      return this;
    }

    public MediaBuilder typeEdits(String typeEdits) {
      this.typeEdits = typeEdits;
      return this;
    }

    public MediaBuilder pathEdits(String pathEdits) {
      this.pathEdits = pathEdits;
      return this;
    }

    public MediaBuilder contentEdits(byte[] contentEdits) {
      this.contentEdits =
          (contentEdits == null) ? null : Arrays.copyOf(contentEdits, contentEdits.length);
      return this;
    }

    public MediaBuilder createDate(Instant createDate) {
      this.createDate = createDate;
      return this;
    }

    public MediaBuilder modifyDate(Instant modifyDate) {
      this.modifyDate = modifyDate;
      return this;
    }

    public MediaBuilder publishDate(Instant publishDate) {
      this.publishDate = publishDate;
      return this;
    }

    public MediaBuilder webSite(WebSite webSite) {
      this.webSite = webSite;
      return this;
    }

    public Media build() {
      return new Media(
          mediaId,
          type,
          path,
          content,
          typeEdits,
          pathEdits,
          contentEdits,
          createDate,
          modifyDate,
          publishDate,
          webSite);
    }
  }
}
