package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.website.WebSite;
import java.util.List;

public interface PublisherService {

  List<Media> getUnpublishedMedia(WebSite webSite);
}
