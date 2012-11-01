package pt.traincompany.tickets;

import pt.traincompany.main.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketAdapter extends ArrayAdapter<Ticket> {
	
	Context context;
	int layoutResourceId;
	Ticket data[] = null;

	public TicketAdapter(Context context, int layoutResourceId,
			Ticket[] objects) {
		super(context, layoutResourceId, objects);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TicketHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new TicketHolder();
			holder.imgIcon = (ImageView) row.findViewById(R.id.paid);
			holder.txtDate = (TextView) row.findViewById(R.id.date);
			holder.txtFrom = (TextView) row.findViewById(R.id.from);
			holder.txtTo = (TextView) row.findViewById(R.id.to);
			holder.txtPrice = (TextView) row.findViewById(R.id.price);
			row.setTag(holder);
		} else {
			holder = (TicketHolder) row.getTag();
		}

		Ticket ticket = data[position];
		holder.txtDate.setText(ticket.date);
		holder.txtFrom.setText(ticket.from);
		holder.txtTo.setText(ticket.to);
		holder.txtPrice.setText(ticket.price + "");
		
		if (ticket.paid)
			holder.imgIcon.setImageResource(R.drawable.ic_launcher);
		else
			holder.imgIcon.setImageResource(R.drawable.ic_launcher);

		return row;
	}
	
	static class TicketHolder {
		ImageView imgIcon;
		TextView txtDate;
		TextView txtFrom;
		TextView txtTo;
		TextView txtPrice;
	}

}
