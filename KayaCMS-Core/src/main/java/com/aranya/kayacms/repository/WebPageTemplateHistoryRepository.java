package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageTemplateHistoryRepository
    extends JpaRepository<WebPageTemplateHistory, Long> {}
