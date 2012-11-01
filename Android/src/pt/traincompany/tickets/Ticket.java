package pt.traincompany.tickets;

import java.io.Serializable;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	public int id;
	public String date;
	public String from;
	public String to;
	public int duration;
	public double price;
	public boolean paid;

	public Ticket() {
		super();
	}

	public Ticket(int id, String date, String from, int duration, String to, double price, boolean paid) {
		super();
		this.date = date;
		this.from = from;
		this.duration = duration;
		this.to = to;
		this.price = price;
		this.paid = paid;
	}
}
