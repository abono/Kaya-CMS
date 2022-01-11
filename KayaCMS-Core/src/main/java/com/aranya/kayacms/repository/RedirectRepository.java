package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.redirect.Redirect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectRepository extends JpaRepository<Redirect, Long> {}
