package com.vicarial.toolshed;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.vicarial.toolshed.parse.Tool;

public class ToolAdapter extends ArrayAdapter<Tool> {

    private final Context mContext;
    private List<Tool> mTools;

    public ToolAdapter(Context context, List toolList) {
        super(context, R.layout.list_tool_item, toolList);
        this.mContext = context;
        this.mTools = toolList;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){

        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_tool_item, null);
        }

        Tool tool = mTools.get(position);

        ParseImageView mealImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = tool.getParseFile("image");
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