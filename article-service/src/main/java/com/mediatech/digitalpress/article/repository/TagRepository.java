package com.mediatech.digitalpress.article.repository;

import com.mediatech.digitalpress.article.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
