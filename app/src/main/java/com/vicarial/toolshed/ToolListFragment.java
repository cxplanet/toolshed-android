package com.vicarial.toolshed;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.vicarial.toolshed.RequestedToolsAdapter;
import com.vicarial.toolshed.parse.Tool;

import java.util.ArrayList;
import java.util.List;

public class ToolListFragment extends ListFragment {

    private ToolAdapter mainAdapter;
    private RequestedToolsAdapter requestedToolsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        mainAdapter = new ToolAdapter(getActivity(), new ArrayList<Tool>());
//        mainAdapter.setTextKey("name");
//        mainAdapter.setImageKey("image");
        setListAdapter(mainAdapter);
        updateToolList();

        return super.onCreateView(inflater, parent, SavedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.action_refresh: {
//                updateToolList();
//                break;
//            }

//            case R.id.action_favorites: {
//                showRequested();
//                break;
//            }
//
//            case R.id.action_new: {
//                newTool();
//                break;
//            }
//
//            case R.id.action_logout: {
//                ParseFacebookUtils.getSession().closeAndClearTokenInformation();
//                ParseUser.logOut();
//                break;
//            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateToolList() {
        updateData();

    }

    public void updateData(){
        ParseQuery<Tool> query = ParseQuery.getQuery(Tool.class);
        // query.whereEqualTo("owner", ParseUser.getCurrentUser());
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Tool>() {
            @Override
            public void done(List<Tool> tasks, ParseException error) {
                if(tasks != null){
                    mainAdapter.clear();
                    for (int i = 0; i < tasks.size(); i++) {
                        mainAdapter.add(tasks.get(i));
                    }
                }
            }
        });
    }

    private void showRequested() {
        requestedToolsAdapter.loadObjects();
        setListAdapter(requestedToolsAdapter);
    }

//    private void newTool() {
//        Intent i = new Intent(this, NewToolActivity.class);
//        startActivityForResult(i, 0);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateToolList();
        }
    }

}