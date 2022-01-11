package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.webpage.WebPageSearchParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageSearchParameterRepository
    extends JpaRepository<WebPageSearchParameter, Long> {}
