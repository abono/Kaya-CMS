package com.aranya.kayacms.controller.admin.media;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaId;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.controller.admin.BaseAdminController;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaResourceNotFoundException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.properties.ByteArrayBinaryContent;
import com.aranya.kayacms.service.MediaService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/admin/media")
@AllArgsConstructor
public class MediaController extends BaseAdminController {

  private final MediaService mediaService;

  private void populateBase(Media item, MediaResponse response) {
    response.setMediaId(item.getMediaId().getId());
    response.setType(item.getType());
    response.setPath(item.getPath());

    response.setCreateDate(item.getCreateDate().getDate());
    response.setModifyDate(item.getCreateDate().getDate());
    response.setPublishDate(item.getCreateDate() == null ? null : item.getCreateDate().getDate());
    response.setEdited(
        !StringUtils.isAllEmpty(item.getTypeEdits())
            || !StringUtils.isAllEmpty(item.getPathEdits())
            || item.getContentEdits().size() > 0);
  }

  private MediaResponse convertSearchItem(Media item) {
    MediaResponse response = new MediaResponse();

    populateBase(item, response);

    return response;
  }

  private MediaResponse convertItem(Media item) {
    MediaResponse response = new MediaResponse();

    populateBase(item, response);

    response.setTypeEdits(item.getTypeEdits());
    response.setPathEdits(item.getPathEdits());

    return response;
  }

  @GetMapping
  public @ResponseBody SearchResults<MediaResponse> search(
      HttpServletRequest request,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "itemsPerPage", defaultValue = "50", required = false) int itemsPerPage)
      throws KayaServiceException, KayaAccessDeniedException {

    verifyLoggedIn(request);

    WebSite webSite = RequestUtil.getWebSite(request);
    WebSiteId webSiteId = webSite.getWebSiteId();

    MediaSearchCriteria criteria = new MediaSearchCriteria(itemsPerPage, page, false, webSiteId);
    SearchResults<Media> searchResults = mediaService.searchMedias(criteria);
    List<MediaResponse> items =
        searchResults.getItems().stream()
            .map(item -> convertSearchItem(item))
            .collect(Collectors.toList());
    return new ListSearchResults<MediaResponse>(
        items, page, itemsPerPage, searchResults.getPageCount(), searchResults.getTotalItems());
  }

  @GetMapping("/{id}")
  public @ResponseBody MediaResponse get(HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, KayaResourceNotFoundException {

    verifyLoggedIn(request);

    Media media = mediaService.getMedia(new MediaId(id));
    if (media == null) {
      throw new KayaResourceNotFoundException(
          "Media not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    return convertItem(media);
  }

  @PostMapping
  public ResponseEntity<MediaResponse> create(
      HttpServletRequest request, @RequestBody MediaRequest mediaRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException {

    verifyLoggedIn(request);

    Media media =
        Media.builder()
            .path(mediaRequest.getPathEdits())
            .type(mediaRequest.getTypeEdits())
            .content(new ByteArrayBinaryContent(new byte[0]))
            .pathEdits(mediaRequest.getPathEdits())
            .typeEdits(mediaRequest.getTypeEdits())
            .build();
    // TODO media.setContentEdits(content);

    media = mediaService.createMedia(media);

    return ResponseEntity.created(new URI("/api/admin/media/" + media.getMediaId()))
        .body(convertItem(media));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MediaResponse> update(
      HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody MediaRequest mediaRequest)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException {

    verifyLoggedIn(request);

    // TODO Get the content uploaded.

    Media media =
        Media.builderClone(mediaService.getMedia(new MediaId(id)))
            .pathEdits(mediaRequest.getPathEdits())
            .typeEdits(mediaRequest.getTypeEdits())
            .build();
    // TODO media.setContentEdits(content);

    media = mediaService.updateMedia(media);

    return ResponseEntity.created(new URI("/api/admin/media/" + media.getMediaId()))
        .body(convertItem(media));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MediaResponse> deleteMedia(
      HttpServletRequest request, @PathVariable("id") Long id)
      throws KayaServiceException, KayaAccessDeniedException, URISyntaxException,
          KayaResourceNotFoundException {

    verifyLoggedIn(request);

    MediaId mediaId = new MediaId(id);
    Media media = mediaService.getMedia(mediaId);
    if (media == null) {
      throw new KayaResourceNotFoundException(
          "Media not found", "entity.not.found", Collections.singletonMap("id", id));
    }
    mediaService.deleteMedia(mediaId);

    return ResponseEntity.ok().build();
  }
}
