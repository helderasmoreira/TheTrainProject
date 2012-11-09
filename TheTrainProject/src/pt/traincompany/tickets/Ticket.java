package pt.traincompany.tickets;

import java.io.Serializable;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	public int id;
	public String date;
	public String from;
	public String to;
	public String duration;
	public double price;
	public String departureTime;
	public String arrivalTime;
	public boolean paid;

	public Ticket() {
		super();
	}

	public Ticket(int id, String departureTime, String arrivalTime,  String date, String from, String duration, String to, double price, boolean paid) {
		super();
		this.id = id;
		this.date = date;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.from = from;
		this.duration = duration;
		this.to = to;
		this.price = price;
		this.paid = paid;
	}
}
