package com.aranya.kayacms.beans.media;

import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.properties.BinaryContent;
import com.aranya.kayacms.properties.DayAndTime;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Media implements Serializable {

  private static final long serialVersionUID = 5477612838590507883L;

  private MediaId mediaId;

  private String type;

  private String path;

  private BinaryContent content;

  private String typeEdits;

  private String pathEdits;

  private BinaryContent contentEdits;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private DayAndTime publishDate;

  private WebSiteId webSiteId;

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
        .webSiteId(media.getWebSiteId());
  }
}
