package hr.ag04.feeddit.ag04feeddit.enums;

public enum ArticleSortEnum {
	
	VOTES_ASC("Votes"),
	VOTES_DESC("Votes"),
	HEADLINE_ASC("Headline"),
	HEADLINE_DESC("Headline"),
	AUTHOR_ASC("Author"),
	AUTHOR_DESC("Author");

	private String name;
	
	ArticleSortEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
