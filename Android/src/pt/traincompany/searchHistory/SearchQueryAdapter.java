package pt.traincompany.searchHistory;

import pt.traincompany.main.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
