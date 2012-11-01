package tickets;

public class Ticket {
	public String date;
	public String from;
	public String to;
	public double price;
	public boolean paid;

	public Ticket() {
		super();
	}

	public Ticket(String date, String from, String to, double price, boolean paid) {
		super();
		this.date = date;
		this.from = from;
		this.to = to;
		this.price = price;
		this.paid = paid;
	}
}
