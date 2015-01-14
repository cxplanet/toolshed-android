package com.vicarial.toolshed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.vicarial.toolshed.parse.Tool;

import java.util.Arrays;

public class RequestedToolsAdapter extends ParseQueryAdapter<Tool> {

    public RequestedToolsAdapter(Context context) {
        super(context, new QueryFactory<Tool>() {
            public ParseQuery<Tool> create() {
                ParseQuery query = new ParseQuery("Tool");
                query.orderByDescending("name");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Tool tool, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_tool_item, null);
        }

        super.getItemView(tool, v, parent);

        ParseImageView mealImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = tool.getParseFile("photo");
        if (photoFile != null) {
            mealImage.setParseFile(photoFile);
            mealImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.tool_name);
        titleTextView.setText(tool.getName());
        TextView descTextView = (TextView) v
                .findViewById(R.id.tool_desc);
        descTextView.setText(tool.getDescription());
        return v;
    }

}