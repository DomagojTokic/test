package hr.ag04.feeddit.ag04feeddit.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "article_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fk_user")
	private User user;
	
	@Column
	private String headline;
	
	@Column
	private String author;
	
	@Column
	private String url;
	
	@OneToMany(mappedBy = "article")
	private List<Vote> votes;
	
	@Column
	private Integer voteCount;
	
	public Article() {}
	
	public Article(User user, String headline, String author, String url) {
		this.user = user;
		this.headline = headline;
		this.author = author;
		this.url = url;
		votes = new ArrayList<>();
		voteCount = 0;
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
	
	public String getHeadline() {
		return headline;
	}
	
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Vote> getVotes() {
		return votes;
	}
	
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	
	public Integer getVoteCount() {
		return voteCount;
	}
	
	public void refreshVoteCount() {
		voteCount = 0;
		for(Vote v : votes) {
			if(v.isResultPositive()) {
				voteCount++;
			} else {
				voteCount--;
			}
		}
	}
	
	public void addVote(Vote vote) {
		votes.add(vote);
	}
	
	public void deleteVote(Vote vote) {
		votes.remove(vote);
	}
}
