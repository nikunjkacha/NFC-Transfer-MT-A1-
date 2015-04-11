package com.example.mitch.nfctransfer_assignment1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.style.TtsSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Mitch on 08/04/2015.
 */
public class Send_Item extends Activity
{
    Activity activity;
    Context context;
    File file;

    public Send_Item(String itemName, Activity a, Context c)
    {
        this.activity = a;
        this.context = c;

        LoadTab(itemName);
        TestNFC();
    }

    private void LoadTab(String itemName)
    {
        //Load Image
        ImageView imageView = (ImageView) this.activity.findViewById(R.id.imgToSend);
        String imgLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + itemName;
        Bitmap bmImg = BitmapFactory.decodeFile(imgLocation);
        imageView.setImageBitmap(bmImg);

        //Load Text Info
        TextView tv1 = (TextView) this.activity.findViewById(R.id.ImgName);
        tv1.setText(itemName);

        file = new File(imgLocation);
        long imgSize = file.length();
        String unit = "B";

        if(imgSize > Math.pow(1000, 3))
        {
            imgSize /= Math.pow(1000, 3);
            unit = "GB";
        }
        else if (imgSize > Math.pow(1000, 2))
        {
            imgSize /= Math.pow(1000, 2);
            unit = "MB";
        }
        else if (imgSize > 1000)
        {
            imgSize /= 1000;
            unit = "KB";
        }

        tv1 = (TextView) this.activity.findViewById(R.id.ImgFileSize);
        tv1.setText(imgSize + unit);
    }

    private void TestNFC()
    {
        PackageManager pm = this.activity.getPackageManager();

        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC))
        {
            // NFC is not available on the device.
            Toast toast = Toast.makeText(this.activity, "This device doesn't have NFC hardware.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            // NFC and Android Beam file transfer is supported.
            RunNFC();
        }
    }

    private void RunNFC()
    {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this.context);

        if(nfcAdapter.isEnabled())
        {
            Uri[] uriToBeam = new Uri[]{Uri.fromFile(file)};

            file.setReadable(true, false);
            nfcAdapter.setBeamPushUris(uriToBeam, this.activity);

            ProgressBar progressBar = (ProgressBar) this.activity.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            TextView textView = (TextView) this.activity.findViewById(R.id.txt_SendingImage);
            textView.setVisibility(View.VISIBLE);
        }
        else
        {
            // NFC is disabled
            Toast toast = Toast.makeText(this.activity, "Please enable NFC.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
