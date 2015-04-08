package com.example.mitch.nfctransfer_assignment1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.style.TtsSpan;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Mitch on 08/04/2015.
 */
public class Send_Item //extends ActionBarActivity
{
    Activity activity;

    public Send_Item(String itemName, Activity a)
    {
        this.activity = a;

        LoadTab(itemName);
    }

    private void LoadTab(String itemName)
    {
        ImageView imageView = (ImageView) this.activity.findViewById(R.id.imgToSend);
        String imgLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + itemName;
        //File imgFile = new File(imgLocation);
        Bitmap bmImg = BitmapFactory.decodeFile(imgLocation);

    }
}
