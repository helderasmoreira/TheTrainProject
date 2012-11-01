package pt.traincompany.search;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResult implements Parcelable {
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

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}

