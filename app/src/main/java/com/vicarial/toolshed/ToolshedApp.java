package com.vicarial.toolshed;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.vicarial.toolshed.parse.Tool;

/**
 * Created by jayl on 12/29/14.
 */
public class ToolshedApp extends Application {

    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Tool.class);
        // Required - Initialize the Parse SDK
        Parse.initialize(this, "Jt1x8dKfFkavcQTB8y2FXTVkLGUfYlVGTgpKQsc4",
                "PRL5VHuqGMyq4L3sxsfGiSsYuYYo5fDkOHN1Rc67");

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseFacebookUtils.initialize("108482289190264");

        // Optional - If you don't want to allow Twitter login, you can
        // remove this line (and other related ParseTwitterUtils calls)
//        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
//                getString(R.string.twitter_consumer_secret));
    }

}
