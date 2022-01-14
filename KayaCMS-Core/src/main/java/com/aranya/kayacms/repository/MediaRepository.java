package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.website.WebSite;
import com.veinhorn.spring.sqlfile.SqlFromResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

  @SqlFromResource(path = "db/scripts/media/select_unpublished.sql")
  List<Media> findByWebSite(WebSite webSite);
}
