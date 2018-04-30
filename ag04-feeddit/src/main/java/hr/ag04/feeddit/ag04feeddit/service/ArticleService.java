package hr.ag04.feeddit.ag04feeddit.service;

import hr.ag04.feeddit.ag04feeddit.dto.ArticleDTO;
import hr.ag04.feeddit.ag04feeddit.enums.ArticleSortEnum;
import hr.ag04.feeddit.ag04feeddit.model.Article;
import hr.ag04.feeddit.ag04feeddit.model.User;
import hr.ag04.feeddit.ag04feeddit.repository.ArticleRepository;
import hr.ag04.feeddit.ag04feeddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public static final int DEFAULT_PAGE_SIZE = 5;
	
	public List<Article> getPage(Integer page, Integer pageSize, String sortString, String search) {
		if(page == null) {
			page = 0;
		}
		if(pageSize == null) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		if(sortString == null || sortString.isEmpty()) {
			if(search == null || search.isEmpty()) {
				return articleRepository.findAll(PageRequest.of(page, pageSize)).getContent();
			} else {
				return articleRepository.findAllByHeadlineContainingOrderByHeadlineAsc(PageRequest.of(page, pageSize),
						search).getContent();
			}
			
		} else {
			ArticleSortEnum sort;
			try {
				sort = ArticleSortEnum.valueOf(sortString);
			} catch(IllegalArgumentException e) {
				return articleRepository.findAll(PageRequest.of(page, pageSize)).getContent();
			}
			if(search == null || search.isEmpty()) {
				switch (sort) {
					case VOTES_ASC:
						return articleRepository.findAllByOrderByVoteCountAsc(PageRequest.of(page, pageSize)).getContent();
					case VOTES_DESC:
						return articleRepository.findAllByOrderByVoteCountDesc(PageRequest.of(page, pageSize)).getContent();
					case HEADLINE_ASC:
						return articleRepository.findAllByOrderByHeadlineAsc(PageRequest.of(page, pageSize)).getContent();
					case HEADLINE_DESC:
						return articleRepository.findAllByOrderByHeadlineDesc(PageRequest.of(page, pageSize)).getContent();
					case AUTHOR_ASC:
						return articleRepository.findAllByOrderByAuthorAsc(PageRequest.of(page, pageSize)).getContent();
					case AUTHOR_DESC:
						return articleRepository.findAllByOrderByAuthorDesc(PageRequest.of(page, pageSize)).getContent();
					default:
						return articleRepository.findAll(PageRequest.of(page, pageSize)).getContent();
				}
			} else {
				switch (sort) {
					case VOTES_ASC:
						return articleRepository.findAllByHeadlineContainingOrderByVoteCountAsc(PageRequest.of(page,
								pageSize), search).getContent();
					case VOTES_DESC:
						return articleRepository.findAllByHeadlineContainingOrderByVoteCountDesc(PageRequest.of(page,
								pageSize), search).getContent();
					case HEADLINE_ASC:
						return articleRepository.findAllByHeadlineContainingOrderByHeadlineAsc(PageRequest.of(page,
								pageSize), search).getContent();
					case HEADLINE_DESC:
						return articleRepository.findAllByHeadlineContainingOrderByHeadlineDesc(PageRequest.of(page,
								pageSize), search).getContent();
					case AUTHOR_ASC:
						return articleRepository.findAllByHeadlineContainingOrderByAuthorAsc(PageRequest.of(page,
								pageSize), search).getContent();
					case AUTHOR_DESC:
						return articleRepository.findAllByHeadlineContainingOrderByAuthorDesc(PageRequest.of(page,
								pageSize), search).getContent();
					default:
						return articleRepository.findAllByHeadlineContaining(PageRequest.of(page,
								pageSize), search).getContent();
				}
			}
		}
	}
	
	public long getNumberOfPages(Integer pageSize) {
		if(articleRepository.count() == 0) {
			return 0;
		} else if(pageSize == null){
			return ((articleRepository.count()-1)/DEFAULT_PAGE_SIZE)+1;
		} else {
			return ((articleRepository.count()-1)/pageSize)+1;
		}
	}
	
	public boolean hasPage(Integer page) {
		if(page == null) {
			page = 0;
		}
		if(page == 0) {
			return articleRepository.count() > 0;
		} else {
			return (articleRepository.count() - 1) / (DEFAULT_PAGE_SIZE*page) >= 1;
		}
	}
	
	public void createArticle(ArticleDTO articleDTO) {
		User user = userRepository.findByName(articleDTO.getUsername()).get();
		Article article = new Article(user, articleDTO.getHeadline(), articleDTO.getAuthor(), articleDTO.getUrl());
		articleRepository.saveAndFlush(article);
	}
	
	public Map<String, String> getSortsMap(String selectedSort) {
		Map<String, String> sortsMap = new LinkedHashMap<>();
		sortsMap.put(ArticleSortEnum.VOTES_ASC.toString(), ArticleSortEnum.VOTES_ASC.getName());
		sortsMap.put(ArticleSortEnum.HEADLINE_ASC.toString(), ArticleSortEnum.HEADLINE_ASC.getName());
		sortsMap.put(ArticleSortEnum.AUTHOR_ASC.toString(), ArticleSortEnum.AUTHOR_ASC.getName());
		
		if(selectedSort == null) {
			return sortsMap;
		} else if(selectedSort.equals(ArticleSortEnum.VOTES_ASC.toString())) {
			sortsMap.remove(ArticleSortEnum.VOTES_ASC.toString());
			sortsMap.put(ArticleSortEnum.VOTES_DESC.toString(), ArticleSortEnum.VOTES_DESC.getName());
			
		} else if(selectedSort.equals(ArticleSortEnum.HEADLINE_ASC.toString())) {
			sortsMap.remove(ArticleSortEnum.HEADLINE_ASC.toString());
			sortsMap.put(ArticleSortEnum.HEADLINE_DESC.toString(), ArticleSortEnum.HEADLINE_DESC.getName());
			
		} else if(selectedSort.equals(ArticleSortEnum.AUTHOR_ASC.toString())) {
			sortsMap.remove(ArticleSortEnum.AUTHOR_ASC.toString());
			sortsMap.put(ArticleSortEnum.AUTHOR_DESC.toString(), ArticleSortEnum.AUTHOR_DESC.getName());
		}
		return sortsMap;
	}
	
	public Map<Integer, String> getPageSizes() {
		Map<Integer, String> sizesMap = new LinkedHashMap<>();
		sizesMap.put(5, "5");
		sizesMap.put(10, "10");
		sizesMap.put(25, "25");
		sizesMap.put(50, "50");
		return sizesMap;
	}
	
	public Integer deleteArticles(Long[] articleIds) {
		for(int i = 0; i < articleIds.length; i++) {
			articleRepository.deleteById(articleIds[i]);
		}
		return articleIds.length;
	}
}
