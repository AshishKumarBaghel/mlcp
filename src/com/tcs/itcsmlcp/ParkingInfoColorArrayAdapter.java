package com.tcs.itcsmlcp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingInfoColorArrayAdapter extends ArrayAdapter<String> {
	private final Context _context;
	private final String[] values;
	private final String[] values1;
	private final Integer[] images;
	private final LayoutInflater inflater;


	
	public ParkingInfoColorArrayAdapter(Context context, String[] values,String[] values1,
			Integer[] images) {
		super(context, R.layout.row_color_pinfolist, values);
		this._context = context;
		this.values = values;
		this.values1 = values1;
		this.images = images;
		inflater = (LayoutInflater) this._context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		if (convertView == null) {
			view = inflater.inflate(R.layout.row_color_pinfolist, parent, false);

		}

		Utils.setFontAllView((ViewGroup)view);
		view.setId(position);

		TextView textView = (TextView) view.findViewById(R.id.text_name);
		TextView textView1 = (TextView) view.findViewById(R.id.text_name1);
		ImageView imageView = (ImageView) view.findViewById(R.id.image_color);
		textView.setText(values[position]);
		textView1.setText(values1[position]);
		imageView.setImageResource(images[position]);

		return view;
	}

}
