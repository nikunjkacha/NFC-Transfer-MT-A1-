package com.example.mitch.nfctransfer_assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Mitch on 09/04/2015.
 */

public class Send_Text extends Activity
{
    Activity activity;
    Context context;

    public  Send_Text(Activity a, Context c, String fileToLoad)
    {
        this.activity = a;
        this.context = c;

        if (fileToLoad != "null")
        {
            LoadFiles(fileToLoad);
        }

        Button button = (Button) this.activity.findViewById(R.id.btn_Enter);
        button.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView textView = (TextView) activity.findViewById(R.id.txt_Title);
                try
                {
                    String fileName = textView.getText().toString();

                    try
                    {
                        textView= (TextView) activity.findViewById(R.id.txt_Message);
                        String textToSend = textView.getText().toString();

                        File fileToSend = CreateFile(fileName, textToSend);

                        if (fileToSend != null)
                        {
                            TestNFC(fileToSend);
                        }
                    }
                    catch (Exception e)
                    {
                        Toast toast = Toast.makeText(context, "Error: Cannot convert message to string", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                catch (Exception e)
                {
                    Toast toast = Toast.makeText(context, "Error: Cannot convert file name to string", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private File CreateFile(String fileName, String textToSend)
    {
        File fileToSend = null;
        try
        {
            File dir = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!dir.exists())
            {
                dir.mkdir();
            }
            fileToSend = new File(dir + File.separator + fileName + ".txt");
            FileWriter writeToFile = new FileWriter(fileToSend);
            writeToFile.append(textToSend);
            writeToFile.flush();
            writeToFile.close();
        }
        catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Error: Cannot create the file", Toast.LENGTH_SHORT);
            toast.show();
        }

        return fileToSend;
    }

    private void TestNFC(File fileToSend)
    {
        PackageManager pm = activity.getPackageManager();

        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC))
        {
            // NFC is not available on the device.
            Toast toast = Toast.makeText(getApplicationContext(), "This device doesn't have NFC hardware.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            // NFC and Android Beam file transfer is supported.
            RunNFC(fileToSend);
        }
    }

    private void RunNFC(File fileToSend)
    {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this.context);

        if(nfcAdapter.isEnabled())
        {
            Uri[] uriToBeam = new Uri[]{Uri.fromFile(fileToSend)};

            fileToSend.setReadable(true, false);
            nfcAdapter.setBeamPushUris(uriToBeam, this.activity);
        }
        else
        {
            // NFC is disabled
            Toast toast = Toast.makeText(this.context, "Please enable NFC.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void LoadFiles(String fileToLoad)
    {
        File fileDir = null;
        String fileContents = "";
        try
        {
            File dir = new File(Environment.getExternalStorageDirectory(), "Notes");
            fileDir = new File(dir + File.separator + fileToLoad);
            String i = "extra debug spot";
        }
        catch (Exception e)
        {
            Toast toast = Toast.makeText(this.context, "Error: Unable to find file", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (fileDir != null)
        {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDir));
                String line;

                while ((line = bufferedReader.readLine()) != null)
                {
                    fileContents += line;
                    fileContents += "\n";
                }

                bufferedReader.close();

                WriteFileToScreen(fileToLoad, fileContents);
            }
            catch (Exception e)
            {
                Toast toast = Toast.makeText(this.context, "Error: Unable to read from file", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void WriteFileToScreen(String fileToLoad, String fileContents)
    {
        TextView textView = (TextView) activity.findViewById(R.id.txt_Title);
        fileToLoad = fileToLoad.substring(0, (fileToLoad.length() - 4));
        textView.setText(fileToLoad);

        textView = (TextView) activity.findViewById(R.id.txt_Message);
        fileContents = fileContents.substring(0, (fileContents.length() - 1));
        textView.setText(fileContents);
    }
}
