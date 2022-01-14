package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.website.WebSite;
import com.veinhorn.spring.sqlfile.SqlFromResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageTemplateRepository extends JpaRepository<WebPageTemplate, Long> {

  @SqlFromResource(path = "db/scripts/web_page_template/select_unpublished.sql")
  List<WebPageTemplate> findByWebSite(WebSite webSite);
}
