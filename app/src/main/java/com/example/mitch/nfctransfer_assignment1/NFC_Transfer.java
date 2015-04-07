package com.example.mitch.nfctransfer_assignment1;

import android.app.ActionBar;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;


public class NFC_Transfer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc__transfer);
        SetUpTabs();
        AddToListView();
    }

    private void SetUpTabs()
    {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Files");
        tabSpec.setContent(R.id.Files);
        tabSpec.setIndicator("Files");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Send");
        tabSpec.setContent(R.id.Send);
        tabSpec.setIndicator("Send");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Text");
        tabSpec.setContent(R.id.Text);
        tabSpec.setIndicator("Text");
        tabHost.addTab(tabSpec);
    }

    private void AddToListView()
    {
        ListView listView = (ListView) findViewById(R.id.listOfFiles);

        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File file = new File(directory);
        String listOfFiles[] = file.list();
        //String nameOfFiles[] = new String[listOfFiles.length];

        //for(int i = 0; i < listOfFiles.length; i++)
        //{
        //    listOfFiles[i] = listOfFiles[i].toString();
        //}
        if(listOfFiles == null)
        {
            Context context = getApplicationContext();
            String error = "There are no files in the Pictures Directory";
            Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
        }
        else
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listOfFiles);
            listView.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nfc__transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
