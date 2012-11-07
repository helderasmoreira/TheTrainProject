package pt.traincompany.searchHistory;

public class SearchQuery {

	public String from;
	public String to;
	public String time;
	public String date;

	public SearchQuery() {
		super();
	}

	public SearchQuery(String from, String to, String time, String date) {
		super();
		this.from = from;
		this.to = to;
		this.time = time;
		this.date = date;
	}
}
