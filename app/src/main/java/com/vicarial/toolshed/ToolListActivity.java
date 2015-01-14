package com.vicarial.toolshed;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseQueryAdapter;
import com.vicarial.toolshed.RequestedToolsAdapter;
import com.vicarial.toolshed.parse.Tool;

public class ToolListActivity extends ListActivity {

    private ParseQueryAdapter<Tool> mainAdapter;
    private RequestedToolsAdapter requestedToolsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setClickable(false);

        mainAdapter = new ParseQueryAdapter<Tool>(this, Tool.class);
        mainAdapter.setTextKey("name");
        mainAdapter.setImageKey("image");

        // Subclass of ParseQueryAdapter
   //     requestedToolsAdapter = new RequestedToolsAdapter(this);

        // Default view is all meals
        setListAdapter(mainAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * Posting meals and refreshing the list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateToolList();
                break;
            }

            case R.id.action_favorites: {
                showRequested();
                break;
            }

            case R.id.action_new: {
                newTool();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateToolList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }

    private void showRequested() {
        requestedToolsAdapter.loadObjects();
        setListAdapter(requestedToolsAdapter);
    }

    private void newTool() {
        Intent i = new Intent(this, NewToolActivity.class);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateToolList();
        }
    }

}