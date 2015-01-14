package com.vicarial.toolshed;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.vicarial.toolshed.parse.Tool;

/**
 * Created by jayl on 1/1/15.
 */
public class NewToolActivity extends Activity {

    private Tool mTool;
    private Uri mOutputFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mTool = new Tool();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,
        // NewMealFragment
        setContentView(R.layout.activity_new_tool);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewToolFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public Tool getCurrentTool() {
        return mTool;
    }

}