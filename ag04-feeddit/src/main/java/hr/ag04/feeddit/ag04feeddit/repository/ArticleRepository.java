package hr.ag04.feeddit.ag04feeddit.repository;

import hr.ag04.feeddit.ag04feeddit.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	Page<Article> findAllByOrderByVoteCountAsc(Pageable pageable);
	Page<Article> findAllByOrderByVoteCountDesc(Pageable pageable);
	Page<Article> findAllByOrderByHeadlineAsc(Pageable pageable);
	Page<Article> findAllByOrderByHeadlineDesc(Pageable pageable);
	Page<Article> findAllByOrderByAuthorAsc(Pageable pageable);
	Page<Article> findAllByOrderByAuthorDesc(Pageable pageable);
	
	Page<Article> findAllByHeadlineContainingOrderByVoteCountAsc(Pageable pageable, String place);
	Page<Article> findAllByHeadlineContainingOrderByVoteCountDesc(Pageable pageable, String place);
	Page<Article> findAllByHeadlineContainingOrderByHeadlineAsc(Pageable pageable, String place);
	Page<Article> findAllByHeadlineContainingOrderByHeadlineDesc(Pageable pageable, String place);
	Page<Article> findAllByHeadlineContainingOrderByAuthorAsc(Pageable pageable, String place);
	Page<Article> findAllByHeadlineContainingOrderByAuthorDesc(Pageable pageable, String place);
	
	Page<Article> findAllByHeadlineContaining(Pageable pageable, String place);
	
	@Transactional
	void deleteById(Long id);
}
