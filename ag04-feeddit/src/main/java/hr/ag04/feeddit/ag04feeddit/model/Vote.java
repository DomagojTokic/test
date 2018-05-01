package hr.ag04.feeddit.ag04feeddit.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Article vote (+1/+1)
 */
@Entity
@Table(name = "votes")
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vote_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fk_user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "fk_article")
	private Article article;
	
	@Column
	private boolean resultPositive;
	
	public Vote() {	}
	
	public Vote(User user, Article article, boolean resultPositive) {
		this.user = user;
		this.article = article;
		this.resultPositive = resultPositive;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Article getArticle() {
		return article;
	}
	
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public boolean isResultPositive() {
		return resultPositive;
	}
	
	public void setResultPositive(boolean resultPositive) {
		this.resultPositive = resultPositive;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Vote))
			return false;
		Vote vote = (Vote) o;
		return Objects.equals(user, vote.user) &&
				Objects.equals(article, vote.article);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(user, article);
	}
}
