package com.aranya.kayacms.controller.admin.media;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MediaRequest {

  private String typeEdits;

  private String pathEdits;
}
