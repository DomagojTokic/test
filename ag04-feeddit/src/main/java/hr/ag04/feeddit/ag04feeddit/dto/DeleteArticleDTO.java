package hr.ag04.feeddit.ag04feeddit.dto;

/**
 * Object which view uses to send articles for deletion to controller.
 */
public class DeleteArticleDTO {
	
	private String[] articleIds;
	
	public DeleteArticleDTO() {}
	
	public DeleteArticleDTO(String[] articleIds) {
		this.articleIds = articleIds;
	}
	
	public String[] getArticleIds() {
		return articleIds;
	}
	
	public void setArticleIds(String[] articleIds) {
		this.articleIds = articleIds;
	}
}
