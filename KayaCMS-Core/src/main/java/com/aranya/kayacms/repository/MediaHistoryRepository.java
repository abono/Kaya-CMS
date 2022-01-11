package com.aranya.kayacms.repository;

import com.aranya.kayacms.beans.media.MediaHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaHistoryRepository extends JpaRepository<MediaHistory, Long> {}
