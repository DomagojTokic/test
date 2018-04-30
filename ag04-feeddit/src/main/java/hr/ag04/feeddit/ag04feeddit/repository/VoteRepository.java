package hr.ag04.feeddit.ag04feeddit.repository;

import hr.ag04.feeddit.ag04feeddit.model.Article;
import hr.ag04.feeddit.ag04feeddit.model.User;
import hr.ag04.feeddit.ag04feeddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	List<Vote> findVoteByArticleAndUser(Article article, User user);
}
