package com.example.myapplication;

//import libraries
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//manage our ToolbarMenu
public class ToolbarMenu extends AppCompatActivity
{

    //ocCreateOptionMenu to link our MenuXml
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true our item added
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //get ItemSelectedId
        int MenuItemId = item.getItemId();

        //check if setting item is selected
        if (MenuItemId == R.id.action_settings)
        {
            //intent Activity to go to Setting activity
            Intent SettingIntent=new Intent(this,SettingsActivity.class);
            //start setting activity
            startActivity(SettingIntent);
        }

        //check if about item is selected
        else if (MenuItemId == R.id.action_about)
        {
            //only show message
            Toast.makeText(ToolbarMenu.this
                    ,"Our Team Build App\n" +
                            "Mohamed Elsayed\n"+
                            "Mohamed Abd Elnabi\n"+
                            "Mohamed Ibrahim"
                    , Toast.LENGTH_SHORT).show();
        }

        //return item again
        return super.onOptionsItemSelected(item);
    }


}
