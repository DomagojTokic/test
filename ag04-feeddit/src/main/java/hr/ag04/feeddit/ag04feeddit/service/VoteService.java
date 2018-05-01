package hr.ag04.feeddit.ag04feeddit.service;

import hr.ag04.feeddit.ag04feeddit.dto.VoteDTO;
import hr.ag04.feeddit.ag04feeddit.model.Article;
import hr.ag04.feeddit.ag04feeddit.model.User;
import hr.ag04.feeddit.ag04feeddit.model.Vote;
import hr.ag04.feeddit.ag04feeddit.repository.ArticleRepository;
import hr.ag04.feeddit.ag04feeddit.repository.UserRepository;
import hr.ag04.feeddit.ag04feeddit.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service which implement service which vote controller uses to process vote requests.
 */
@Service
public class VoteService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	/**
	 * Creates vote from vote data transfer objects and updates it in database.
	 * @param voteDTO vote data transfer object
	 */
	public void vote(VoteDTO voteDTO) {
		User user = userRepository.findByName(voteDTO.getUsername()).get();
		Article article = articleRepository.getOne(voteDTO.getArticleId());
		boolean result = voteDTO.getResult();
		
		List<Vote> voteList = voteRepository.findVoteByArticleAndUser(article, user);
		if(voteList.isEmpty()) {
			Vote vote = new Vote(user, article, result);
			voteRepository.saveAndFlush(vote);
		} else {
			Vote existingVote = voteList.get(0);
			voteRepository.delete(existingVote);
			if(existingVote.isResultPositive() ^ result) {
				Vote vote = new Vote(user, article, result);
				voteRepository.saveAndFlush(vote);
			}
		}
		article.refreshVoteCount();
		articleRepository.saveAndFlush(article);
	}
	
}
