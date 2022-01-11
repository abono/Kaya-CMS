package com.aranya.kayacms.controller.admin.webpage;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.controller.admin.BaseAdminController;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaResourceNotFoundException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.WebPageService;
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
@RequestMapping("/api/admin/webPage")
@AllArgsConstructor
public class WebPageController extends BaseAdminController {

  private final WebPageService webPageService;

  private void populateBase(WebPage item, WebPageResponse response) {
    response.setWebPageId(item.getWebPageId());
    response.setType(item.getType());
    response.setPath(item.getPath());
    response.setTitle(item.getTitle());
    response.setDescription(item.getDescription());

    response.setCreateDate(item.getCreateDate());
    response.setModifyDate(item.getCreateDate());
    response.setPublishDate(item.getCreateDate());
    response.setEdited(
        !StringUtils.isAllEmpty(item.getTypeEdits())
            || !StringUtils.isAllEmpty(item.getPathEdits())
            || !StringUtils.isAllEmpty(item.getTitleEdits())
            || !StringUtils.isAllEmpty(item.getDescriptionEdits())
            || !StringUtils.isAllEmpty(item.getContentEdits())
            || !StringUtils.isAllEmpty(item.getParametersEdits()));
  }

  private WebPageResponse convertSearchItem(WebPage item) {
    WebPageResponse response = new WebPageResponse();

    populateBase(item, response);

    return response;
  }

  private WebPageResponse convertItem(WebPage item) {
    WebPageResponse response = new WebPageResponse();

    populateBase(item, response);

    response.setContent(item.getContent());
    response.setParameters(item.getParameters());
    response.setTypeEdits(item.getTypeEdits());
    response.setPathEdits(item.getPathEdits());
    response.setTitleEdits(item.getTitleEdits());
    response.setDescriptionEdits(item.getDescriptionEdits());
    response.setContentEdits(item.getContentEdits());
    response.setParametersEdits(item.getParametersEdits());

    return response;
  }

  @GetMapping
  public SearchResults<WebPageResponse> search(
      HttpServletRequest request,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "itemsPerPage", defaultValue = "50", required = false) int itemsPerPage)
      throws KayaServiceException, KayaAccessDeniedException {

    WebSite webSite = RequestUtil.getWebSite(request);

    verifyLoggedIn(request);

    WebPageSearchCriteria criteria = new WebPageSearchCriteria(itemsPerPage, page, false, webSite);
    SearchResults<WebPage> searchResults = webPageService.searchWebPages(criteria);
    List<WebPageResponse> items =
        searchResults.getItems().stream()
            .map(item -> convertSearchItem(item))
            .collect(Collectors.toList());
    return new ListSearchResults<WebPageResponse>(
        items, page, itemsPerPage, searchResults.getPageCount(), searchResults.getTotalItems());
  }

  @GetMapping("/{id}")
  public @ResponseBody WebPageResponse get(HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebPage webPage = webPageService.getWebPage(id);
    if (webPage == null) {
      throw new KayaResourceNotFoundException(
          "Web page not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    return convertItem(webPage);
  }

  @PostMapping
  public ResponseEntity<WebPageResponse> create(
      HttpServletRequest request, @RequestBody WebPageRequest webPageRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException {

    verifyLoggedIn(request);

    WebPage webPage =
        WebPage.builder()
            .type(webPageRequest.getTypeEdits())
            .path(webPageRequest.getPathEdits())
            .title("")
            .description("")
            .content("")
            .parameters("{}")
            .typeEdits(webPageRequest.getTypeEdits())
            .pathEdits(webPageRequest.getPathEdits())
            .titleEdits(webPageRequest.getTitleEdits())
            .descriptionEdits(webPageRequest.getDescriptionEdits())
            .contentEdits(webPageRequest.getContentEdits())
            .parametersEdits(webPageRequest.getParametersEdits())
            .build();

    webPage = webPageService.createWebPage(webPage);

    return ResponseEntity.created(new URI("/api/admin/webPage/" + webPage.getWebPageId()))
        .body(convertItem(webPage));
  }

  @PutMapping("/{id}")
  public ResponseEntity<WebPageResponse> update(
      HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody WebPageRequest webPageRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException,
          KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebPage webPage = webPageService.getWebPage(id);
    if (webPage == null) {
      throw new KayaResourceNotFoundException(
          "Web page not found", "entity.not.found", Collections.singletonMap("id", id));
    }

    webPage =
        WebPage.builderClone(webPage)
            .typeEdits(webPageRequest.getTypeEdits())
            .pathEdits(webPageRequest.getPathEdits())
            .titleEdits(webPageRequest.getTitleEdits())
            .descriptionEdits(webPageRequest.getDescriptionEdits())
            .contentEdits(webPageRequest.getContentEdits())
            .parametersEdits(webPageRequest.getParametersEdits())
            .build();

    webPage = webPageService.updateWebPage(webPage);

    return ResponseEntity.created(new URI("/api/admin/webPage/" + webPage.getWebPageId()))
        .body(convertItem(webPage));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<WebPageResponse> deleteWebPage(
      HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException,
          KayaResourceNotFoundException {

    verifyLoggedIn(request);

    WebPage webPage = webPageService.getWebPage(id);
    if (webPage == null) {
      throw new KayaResourceNotFoundException(
          "Web page not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    webPageService.deleteWebPage(id);

    return ResponseEntity.ok().build();
  }
}
