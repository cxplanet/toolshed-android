package com.vicarial.toolshed;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vicarial.toolshed.parse.Tool;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jayl on 1/1/15.
 */
public class NewToolActivity2 extends Activity {

    private final int YOUR_SELECT_PICTURE_REQUEST_CODE = 10001;

    private Tool mTool;
    private Uri mOutputFileUri;
    private TextView toolName;
    private TextView toolDesc;
    private TextView toolTags;
    private Button addItemButton;
    private ParseImageView toolPreview;
    private String mCurrImagename;

    private TextView.OnEditorActionListener multiLineListener =  new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(NewToolActivity2.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mTool = new Tool();
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,
        // NewMealFragment
        setContentView(R.layout.activity_new_item);
        toolName = ((EditText) findViewById(R.id.tool_name));
        toolDesc = ((EditText) findViewById(R.id.tool_desc));
        toolDesc.setOnEditorActionListener(multiLineListener);
        toolTags = ((EditText) findViewById(R.id.tool_tags));

        addItemButton = ((Button) findViewById(R.id.post_data_button));
        addItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveTool();

            }
        });

        toolPreview = ((ParseImageView) findViewById(R.id.tool_image));
        toolPreview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openImageIntent();
            }
        });
    }

    private void saveTool() {
        Tool tool = getCurrentTool();

        tool.setName(toolName.getText().toString());
        tool.setDescription(toolDesc.getText().toString());

        // Associate the item with the current user
        tool.setOwner(ParseUser.getCurrentUser());

        // If the user added a photo, that data will be
        // added in the CameraFragment

        // Save the meal and return
        tool.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Item saved",
                            Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.add_item)
        {
            saveTool();
        }

        return true;
    }

    public Tool getCurrentTool() {
        return mTool;
    }

    public void openImageIntent() {

//        // Camera.
//        final List<Intent> cameraIntents = new ArrayList<Intent>();
//        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        final PackageManager packageManager = getPackageManager();
//        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for(ResolveInfo res : listCam) {
//            final String packageName = res.activityInfo.packageName;
//            final Intent intent = new Intent(captureIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(packageName);
//            //intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//            cameraIntents.add(intent);
//        }
//
//        // Image picker
//
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        // Chooser of filesystem options.
//        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
//
//        // Add the camera options.
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            // simplistically, if there is a target uri, we assume it is a camera
            // that is delivering the data. Otherwise, assume it comes from the image gallery
            Uri targetUri = data.getData();
            if (targetUri != null)
            {
                mCurrImagename = getImagePath(targetUri);
            } else
            {
                Bundle b = data.getExtras(); // Kept as a Bundle to check for other things in my actual code
                Bitmap pic = (Bitmap) b.get("data");

                Bitmap toolImgScaled = Bitmap.createScaledBitmap(pic, 400, 400
                        * pic.getHeight() / pic.getWidth(), false);

                // Override Android default landscape orientation and save portrait
//                Matrix matrix = new Matrix();
//                matrix.postRotate(90);
//                Bitmap rotatedScaledToolImage = Bitmap.createBitmap(toolImgScaled, 0,
//                        0, toolImgScaled.getWidth(), toolImgScaled.getHeight(),
//                        matrix, true);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                toolImgScaled.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                byte[] scaledData = bos.toByteArray();

                mTool.setImage(new ParseFile("tool.jpg", scaledData));
                toolPreview.setParseFile(mTool.getImage());
                toolPreview.loadInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        toolPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

}