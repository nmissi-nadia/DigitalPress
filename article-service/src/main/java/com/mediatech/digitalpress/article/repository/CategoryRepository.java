package com.mediatech.digitalpress.article.repository;

import com.mediatech.digitalpress.article.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
