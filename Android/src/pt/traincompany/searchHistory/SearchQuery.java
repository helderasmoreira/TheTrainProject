package pt.traincompany.searchHistory;

public class SearchQuery {

	public String from;
	public String to;
	public String time;

	public SearchQuery() {
		super();
	}

	public SearchQuery(String from, String to, String time) {
		super();
		this.from = from;
		this.to = to;
		this.time = time;
	}
}
