package hr.ag04.feeddit.ag04feeddit.dto;

/**
 * Object which view uses to send article data to controller.
 */
public class ArticleDTO {
	
	private int votes;
	private String username;
	private String headline;
	private String url;
	private String author;
	
	public ArticleDTO() {}
	
	public ArticleDTO(int votes, String username, String headline, String url, String author) {
		this.votes = votes;
		this.username = username;
		this.headline = headline;
		this.url = url;
		this.author = author;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public void setVotes(int vote) {
		this.votes = vote;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHeadline() {
		return headline;
	}
	
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
}
