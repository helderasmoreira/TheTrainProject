package pt.traincompany.search;


public class SearchResult {
	public String from;
	public String to;
	public String duration;
	public double price;

	public SearchResult() {
		super();
	}

	public SearchResult(String from, String to, String duration, double price) {
		super();
		this.from = from;
		this.to = to;
		this.price = price;
		this.duration = duration;
	}
}

