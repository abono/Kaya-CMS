package com.aranya.kayacms.controller.admin.publish;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.controller.admin.BaseAdminController;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.PublisherService;
import com.aranya.kayacms.service.WebPageService;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.util.RequestUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/publish")
@AllArgsConstructor
public class PublishController extends BaseAdminController {

  private final WebPageTemplateService webPageTemplateService;

  private final WebPageService webPageService;

  private final PublisherService publisherService;

  private WebPageTemplateSummary convertItem(WebPageTemplate item) {
    WebPageTemplateSummary summary = new WebPageTemplateSummary();

    summary.setWebPageTemplateId(item.getWebPageTemplateId().getId());
    summary.setName(item.getName());
    summary.setNameEdits(item.getNameEdits());
    summary.setCreateDate(item.getCreateDate().getDate());
    summary.setModifyDate(item.getModifyDate().getDate());
    summary.setPublishDate(item.getPublishDate().getDate());

    return summary;
  }

  private WebPageSummary convertItem(WebPage item) {
    WebPageSummary summary = new WebPageSummary();

    summary.setWebPageId(item.getWebPageId().getId());
    summary.setType(item.getType());
    summary.setPath(item.getPath());
    summary.setTitle(item.getTitle());
    summary.setDescription(item.getDescription());
    summary.setTypeEdits(item.getTypeEdits());
    summary.setPathEdits(item.getPathEdits());
    summary.setTitleEdits(item.getTitleEdits());
    summary.setDescriptionEdits(item.getDescriptionEdits());
    summary.setCreateDate(item.getCreateDate().getDate());
    summary.setModifyDate(item.getModifyDate().getDate());
    summary.setPublishDate(item.getPublishDate().getDate());
    summary.setWebPageTemplateId(item.getWebPageTemplateId().getId());
    summary.setWebPageTemplateIdEdits(item.getWebPageTemplateIdEdits().getId());

    return summary;
  }

  private MediaSummary convertItem(Media item) {
    MediaSummary summary = new MediaSummary();

    summary.setMediaId(item.getMediaId());
    summary.setType(item.getType());
    summary.setPath(item.getPath());
    summary.setTypeEdits(item.getTypeEdits());
    summary.setPathEdits(item.getPathEdits());
    summary.setCreateDate(item.getCreateDate());
    summary.setModifyDate(item.getModifyDate());
    summary.setPublishDate(item.getPublishDate());

    return summary;
  }

  @GetMapping
  public @ResponseBody PublishResponse getUnpublishedSummaries(HttpServletRequest request)
      throws KayaAccessDeniedException, KayaServiceException {

    verifyLoggedIn(request);

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = new WebSiteId(webSite.getWebSiteId());

    List<WebPageTemplateSummary> webPageTemplateSummaries =
        webPageTemplateService.getUnpublishedWebPageTemplate(webSiteId).stream()
            .map(item -> convertItem(item))
            .collect(Collectors.toList());

    List<WebPageSummary> webPageSummaries =
        webPageService.getUnpublishedWebPage(webSiteId).stream()
            .map(item -> convertItem(item))
            .collect(Collectors.toList());

    List<MediaSummary> mediaSummaries =
        publisherService.getUnpublishedMedia(webSite).stream()
            .map(item -> convertItem(item))
            .collect(Collectors.toList());

    return PublishResponse.builder()
        .webPageTemplates(webPageTemplateSummaries)
        .webPages(webPageSummaries)
        .media(mediaSummaries)
        .build();
  }
}
