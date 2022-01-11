package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {}
