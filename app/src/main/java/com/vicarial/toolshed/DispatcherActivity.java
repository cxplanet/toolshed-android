package com.vicarial.toolshed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseUser;
import com.vicarial.toolshed.login.ParseLoginActivity;


public class DispatcherActivity extends Activity {

    final static int LOGIN_REQUEST = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add code to print out the key hash, which appears to be
        // different than what we get from the home/.android keystore
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.vicarial.toolshed",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            Log.d("Toolshed", "onCreate, got user,  "
                    + ParseUser.getCurrentUser().getUsername());
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            Log.d("Toolshed dispatcher", "onCreate, no user");
            startActivityForResult(new Intent(this, ParseLoginActivity.class), LOGIN_REQUEST);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == LOGIN_REQUEST && resultCode == RESULT_OK)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}