package pt.traincompany.search;

import pt.traincompany.main.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<SearchResult> {
	
	Context context;
	int layoutResourceId;
	SearchResult data[] = null;

	public SearchResultAdapter(Context context, int layoutResourceId,
			SearchResult[] objects) {
		super(context, layoutResourceId, objects);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ResultHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ResultHolder();
			holder.txtFrom = (TextView) row.findViewById(R.id.search_result_from);
			holder.txtTo = (TextView) row.findViewById(R.id.search_result_to);
			holder.txtDuration = (TextView) row.findViewById(R.id.search_result_duration);
			holder.txtPrice = (TextView) row.findViewById(R.id.search_result_price);
			holder.imgIcon = (ImageView) row.findViewById(R.id.search_result_more);
			
			row.setTag(holder);
		} else {
			holder = (ResultHolder) row.getTag();
		}

		SearchResult ticket = data[position];
		holder.txtFrom.setText(ticket.from);
		holder.txtTo.setText(ticket.to);
		holder.txtDuration.setText(ticket.duration);
		holder.txtPrice.setText(ticket.price + "Û");
		
		holder.imgIcon.setImageResource(R.drawable.ic_launcher);

		return row;
	}
	
	static class ResultHolder {
		ImageView imgIcon;
		TextView txtFrom;
		TextView txtTo;
		TextView txtDuration;
		TextView txtPrice;
	}

}
