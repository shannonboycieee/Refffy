package com.reffy.shannon.reffy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<String[]> {

    private List<String[]> detailsList = new ArrayList<String[]>();

    static class ItemViewHolder {
        EditText title, publicationPlace,publicationDate,edition,author;
    }

    public ItemArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

	@Override
	public void add(String[] object) {
		detailsList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.detailsList.size();
	}

    @Override
	public String[] getItem(int index) {
		return this.detailsList.get(index);
	}

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        ItemViewHolder viewHolder = new ItemViewHolder();
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.item_layout, parent, false);

            viewHolder.title = (EditText) row.findViewById(R.id.title);
            viewHolder.publicationDate = (EditText) row.findViewById(R.id.publicationDate);
            viewHolder.publicationPlace = (EditText) row.findViewById(R.id.publicationPlace);
            viewHolder.edition = (EditText) row.findViewById(R.id.edition);
            viewHolder.author = (EditText) row.findViewById(R.id.authors);
            row.setTag(viewHolder);
		} else {
            viewHolder = (ItemViewHolder)row.getTag();
        }

            String[] isbn = getItem(position);
            viewHolder.title.setText(isbn[1]);
            viewHolder.publicationPlace.setText(isbn[2]);
            viewHolder.publicationDate.setText(isbn[3]);
            viewHolder.edition.setText(isbn[4]);
            viewHolder.author.setText(isbn[5]);

		return row;
	}
}
