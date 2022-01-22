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
public class MediaHistory implements Serializable {

  private static final long serialVersionUID = 3526247900002252165L;

  private MediaHistoryId mediaHistoryId;

  private String type;

  private String path;

  private BinaryContent content;

  private DayAndTime createDate;

  private DayAndTime modifyDate;

  private DayAndTime publishDate;

  private WebSiteId webSiteId;

  private MediaId mediaId;
}
