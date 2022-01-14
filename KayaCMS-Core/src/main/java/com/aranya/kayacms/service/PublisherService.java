package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.website.WebSite;
import java.util.List;

public interface PublisherService {

  List<WebPageTemplate> getUnpublishedWebPageTemplate(WebSite webSite);

  List<WebPage> getUnpublishedWebPage(WebSite webSite);

  List<Media> getUnpublishedMedia(WebSite webSite);
}
