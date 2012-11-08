package pt.traincompany.searchHistory;

import pt.traincompany.main.Home;
import pt.traincompany.main.R;
import pt.traincompany.search.Search;
import pt.traincompany.tickets.MyTickets;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchQueryAdapter extends ArrayAdapter<SearchQuery> {
	

	Context context;
	int layoutResourceId;
	SearchQuery data[] = null;

	public SearchQueryAdapter(Context context, int layoutResourceId,
			SearchQuery[] objects) {
		
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
			holder.txtFrom = (TextView) row.findViewById(R.id.from);
			holder.txtTo = (TextView) row.findViewById(R.id.to);
			holder.txtTime = (TextView) row.findViewById(R.id.time);
			
			row.setTag(holder);
		} else {
			holder = (ResultHolder) row.getTag();
		}
		
		final View row2 = row;
		row2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(v.getContext(), Search.class);
				Bundle bundle = new Bundle();
				
				ResultHolder query = (ResultHolder) row2.getTag();
				
				bundle.putString("from", query.txtFrom.getText().toString());
				bundle.putString("to", query.txtTo.getText().toString());
				bundle.putString("time", query.txtTime.getText().toString());
				bundle.putBoolean("fromHistory", true);
				myIntent.putExtras(bundle);
				
				v.getContext().startActivity(myIntent);
			}
		});
		
		SearchQuery query = data[position];
		holder.txtFrom.setText(query.from);
		holder.txtTo.setText(query.to);
		holder.txtTime.setText(query.time);
		
		return row;
	}
	
	static class ResultHolder {
		TextView txtFrom;
		TextView txtTo;
		TextView txtTime;
	}


}
