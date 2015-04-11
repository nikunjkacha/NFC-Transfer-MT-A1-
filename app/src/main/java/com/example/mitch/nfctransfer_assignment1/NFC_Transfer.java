package com.example.mitch.nfctransfer_assignment1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;


public class NFC_Transfer extends ActionBarActivity
{
    public String[] listOfFiles;

    public Activity getActivity() {return this;}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc__transfer);
        SetUpTabs();
        AddToListView();
        ReadListSelection();
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

        final Activity a = this;
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                if(tabId == "Text")
                {
                    Context c = getApplicationContext();
                    Send_Text sendtext = new Send_Text(a, c);
                }
            }
        });
    }

    private void AddToListView()
    {
        ListView listView = (ListView) findViewById(R.id.listOfFiles);

        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File file = new File(directory);
        listOfFiles = file.list();

        if(listOfFiles == null) //not finding images
        {
            Context context = getApplicationContext();
            String error = "There are no files in the Pictures Directory";
            Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listOfFiles);
            listView.setAdapter(adapter);
        }
    }

    private void ReadListSelection()
    {
        final ListView listView = (ListView) findViewById(R.id.listOfFiles);
        final Activity a = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Context c = getApplicationContext();
                Send_Item sendItem = new Send_Item(listOfFiles[position], a, c);

                TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
                tabHost.setCurrentTab(1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nfc__transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
