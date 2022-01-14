package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.website.WebSite;
import com.veinhorn.spring.sqlfile.SqlFromResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Long> {

  @SqlFromResource(path = "db/scripts/web_page/select_unpublished.sql")
  List<WebPage> findByWebSite(WebSite webSite);
}
