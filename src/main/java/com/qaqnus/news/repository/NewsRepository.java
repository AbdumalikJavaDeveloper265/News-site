package com.qaqnus.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qaqnus.news.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
