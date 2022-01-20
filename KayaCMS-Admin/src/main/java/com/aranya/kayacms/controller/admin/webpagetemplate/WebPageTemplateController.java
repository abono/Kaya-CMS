package com.aranya.kayacms.controller.admin.webpagetemplate;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.controller.admin.BaseAdminController;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaResourceNotFoundException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.util.ListSearchResults;
import com.aranya.kayacms.util.RequestUtil;
import com.aranya.kayacms.util.SearchResults;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/webPageTemplate")
@AllArgsConstructor
public class WebPageTemplateController extends BaseAdminController {

  private final WebPageTemplateService webPageTemplateService;

  private void populateBase(WebPageTemplate item, WebPageTemplateResponse response) {
    response.setWebPageTemplateId(item.getWebPageTemplateId().getId());
    response.setName(item.getName());
    response.setNameEdits(item.getNameEdits());

    response.setCreateDate(item.getCreateDate().getDate());
    response.setModifyDate(item.getCreateDate().getDate());
    response.setPublishDate(item.getCreateDate().getDate());
    response.setEdited(
        !StringUtils.isAllEmpty(item.getNameEdits())
            || !StringUtils.isAllEmpty(item.getContentEdits()));
  }

  private WebPageTemplateResponse convertSearchItem(WebPageTemplate item) {
    WebPageTemplateResponse response = new WebPageTemplateResponse();

    populateBase(item, response);

    return response;
  }

  private WebPageTemplateResponse convertItem(WebPageTemplate item) {
    WebPageTemplateResponse response = new WebPageTemplateResponse();

    populateBase(item, response);

    response.setContent(item.getContent());
    response.setContentEdits(item.getContentEdits());

    return response;
  }

  @GetMapping
  public @ResponseBody SearchResults<WebPageTemplateResponse> search(
      HttpServletRequest request,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "itemsPerPage", defaultValue = "50", required = false) int itemsPerPage)
      throws KayaServiceException, KayaAccessDeniedException {

    WebSite webSite = RequestUtil.getWebSite(request);

    verifyLoggedIn(request);

    WebPageTemplateSearchCriteria criteria =
        new WebPageTemplateSearchCriteria(
            itemsPerPage, page, false, new WebSiteId(webSite.getWebSiteId()));
    SearchResults<WebPageTemplate> searchResults =
        webPageTemplateService.searchWebPageTemplates(criteria);
    List<WebPageTemplateResponse> items =
        searchResults.getItems().stream()
            .map(item -> convertSearchItem(item))
            .collect(Collectors.toList());
    return new ListSearchResults<WebPageTemplateResponse>(
        items, page, itemsPerPage, searchResults.getPageCount(), searchResults.getTotalItems());
  }

  @GetMapping("/{id}")
  public @ResponseBody WebPageTemplateResponse get(
      HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebPageTemplate webPageTemplate =
        webPageTemplateService.getWebPageTemplate(new WebPageTemplateId(id));
    if (webPageTemplate == null) {
      throw new KayaResourceNotFoundException(
          "Web page template not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    return convertItem(webPageTemplate);
  }

  @PostMapping
  public ResponseEntity<WebPageTemplateResponse> create(
      HttpServletRequest request, @RequestBody WebPageTemplateRequest webPageTemplateRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException {

    WebSite webSite = RequestUtil.getWebSite(request);

    verifyLoggedIn(request);

    WebPageTemplate webPageTemplate =
        WebPageTemplate.builder()
            .name("")
            .content("")
            .nameEdits(webPageTemplateRequest.getNameEdits())
            .contentEdits(webPageTemplateRequest.getContentEdits())
            .webSiteId(new WebSiteId(webSite.getWebSiteId()))
            .build();

    webPageTemplate = webPageTemplateService.createWebPageTemplate(webPageTemplate);

    return ResponseEntity.created(
            new URI("/api/admin/webPageTemplate/" + webPageTemplate.getWebPageTemplateId()))
        .body(convertItem(webPageTemplate));
  }

  @PutMapping("/{id}")
  public ResponseEntity<WebPageTemplateResponse> update(
      HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody WebPageTemplateRequest webPageTemplateRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException {

    verifyLoggedIn(request);

    WebPageTemplate webPageTemplate =
        WebPageTemplate.builderClone(
                webPageTemplateService.getWebPageTemplate(new WebPageTemplateId(id)))
            .nameEdits(webPageTemplateRequest.getNameEdits())
            .contentEdits(webPageTemplateRequest.getContentEdits())
            .build();

    webPageTemplate = webPageTemplateService.updateWebPageTemplate(webPageTemplate);

    return ResponseEntity.created(
            new URI("/api/admin/webPageTemplate/" + webPageTemplate.getWebPageTemplateId()))
        .body(convertItem(webPageTemplate));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<WebPageTemplateResponse> deleteWebPageTemplate(
      HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException,
          KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebPageTemplate webPageTemplate =
        webPageTemplateService.getWebPageTemplate(new WebPageTemplateId(id));
    if (webPageTemplate == null) {
      throw new KayaResourceNotFoundException(
          "Web page template not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    webPageTemplateService.deleteWebPageTemplate(new WebPageTemplateId(id));

    return ResponseEntity.ok().build();
  }
}
