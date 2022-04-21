package com.qaqnus.news.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qaqnus.news.entity.News;
import com.qaqnus.news.repository.NewsRepository;

@Service
public class NewsService {
	
	@Autowired
	NewsRepository newsRepository;
	
	
	public List<News> getNewsListByApprovedTrue(){
		List<News> news_list = newsRepository.findAll();
		List<News> approved_list = new ArrayList<News>();
		if(news_list.isEmpty()) {
			return null;
			
		}
		
		for(News item : news_list) {
			
			if(item.isIs_approved()) {
				approved_list.add(item);
			}			
			
		}
		return approved_list;
	}
	
	public List<News> getNewsListByApprovedFalse(){
		
		List<News> news_list = newsRepository.findAll();
		List<News> approved_list = new ArrayList<News>();
		if(news_list.isEmpty()) {
			return null;
			
		}
		
		for(News item : news_list) {
			
			if(!item.isIs_approved()) {
				approved_list.add(item);
			}			
			
		}
		return approved_list;
	}
	
	public List<News> getAll(){
		if(newsRepository.findAll().isEmpty()) {
			return null;
			
		}
		return newsRepository.findAll();
	}
	
	public List<News> getNewsListByUserName(String username){
		List<News> newsList = new ArrayList<News>();
		for(News news : getAll()) {
			if(news.getUser().getUsername() == username) {
				newsList.add(news);
			}
		}
		
		return newsList;
	}
	

	
	public boolean updateNews(News news, Long id) {
		Optional<News> newNews = newsRepository.findById(id);
		
		if(newNews.isPresent()) {
			News newsEntity = newNews.get();
			newsEntity.setTitle(news.getTitle());
			newsEntity.setContext(news.getContext());
			newsEntity.setIs_approved(false);
			
			newsEntity = newsRepository.save(newsEntity);
			return true;
		}
		return false;
	}
	
	public News getNewsByIdIfExist(Long id) {
		News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		
		return news;
	}
	
	public boolean saveNews(News news) {
		newsRepository.save(news);
		return true;
	}
	
}
