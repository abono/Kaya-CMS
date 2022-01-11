package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageTemplateRepository extends JpaRepository<WebPageTemplate, Long> {}
