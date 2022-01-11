package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpage.WebPageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageHistoryRepository extends JpaRepository<WebPageHistory, Long> {}
