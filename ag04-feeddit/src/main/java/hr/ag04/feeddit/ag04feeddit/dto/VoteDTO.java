package hr.ag04.feeddit.ag04feeddit.dto;

public class VoteDTO {
	
	private String username;
	
	private Long articleId;
	
	private boolean result;
	
	public VoteDTO() {}
	
	public VoteDTO(String username, Long articleId, boolean result) {
		this.username = username;
		this.articleId = articleId;
		this.result = result;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getArticleId() {
		return articleId;
	}
	
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}
}
