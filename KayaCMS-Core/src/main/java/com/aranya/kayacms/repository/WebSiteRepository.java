package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.website.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSiteRepository extends JpaRepository<WebSite, Long> {}
