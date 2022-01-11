package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpage.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Long> {}
