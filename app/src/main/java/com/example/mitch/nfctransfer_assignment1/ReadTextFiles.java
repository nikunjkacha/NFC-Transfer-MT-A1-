package com.example.mitch.nfctransfer_assignment1;

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


public class ReadTextFiles extends ActionBarActivity
{
    String[] listOfFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_text_files);

        LoadFiles();
        ReadFiles();
    }

    private void LoadFiles()
    {
        listOfFiles = null;
        ListView listView = (ListView) findViewById(R.id.lv_TextFiles);
        try
        {
            String directory = Environment.getExternalStorageDirectory().toString() + "/Notes";
            File file = new File(directory);
            listOfFiles = file.list();
        }
        catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Error: There are no saved file", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(listOfFiles != null) //not finding images
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listOfFiles);
            listView.setAdapter(adapter);
        }
    }

    private void ReadFiles()
    {
        final ListView listView = (ListView) findViewById(R.id.lv_TextFiles);
        final Activity a = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();
                intent.putExtra("fileToLoad",listOfFiles[position]);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_text_files, menu);
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
