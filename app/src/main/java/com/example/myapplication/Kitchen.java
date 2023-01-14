package com.example.myapplication;

//import libraries
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;

//This is Main Activity For Showing Kitchen in ListView
public class Kitchen extends ToolbarMenu
{

    //ArrayList to store the kitchen from Database and then add to ArrayAdapter to set in ListView
    ArrayList<KitchenDataModel> kitchenDataModels;
    //Our List View to show Kitchen
    RecyclerView kitchenListView;
    //Array Adapter to manage recycle data
    private  KitchenCustomAdapter kitchenAdapter;

    //OnCreate method to lunch Kitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for Kitchen activity
        super.onCreate(savedInstanceState);
        //Set UI kitchen activity for Kitchen activity
        setContentView(R.layout.activity_kitchen);
        //KitchenToolbar UI
        Toolbar kitchenToolbar=findViewById(R.id.KitchenToolbar);
        //Set KitchenToolbar to our activity
        setSupportActionBar(kitchenToolbar);
        //method to fetch kitchen data from database and add in ListView
        CreateKitchenListView();
        //handel OnBackPress of our activity
        Kitchen.this.getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true)
                {
                    @Override
                    public void handleOnBackPressed()
                    {
                        //empty stack and close app
                        finishAffinity();
                    }
                });
    }

    // OnClick method for ImageView Icon to add new Kitchen
    public void AddKitchenListenerIcon(View addKitchenIcon)
    {
        //intent Activity Add Kitchen
        Intent addKitchenIntent = new Intent(this, AddKitchen.class);
        //start add kitchen activity
        startActivity(addKitchenIntent);
    }

    //method to fetch kitchen data from database and add in ListView
    public void CreateKitchenListView()
    {
        //Our UI Kitchen ListView
        kitchenListView = findViewById(R.id.KitchenListView);
        //ArrayList to store the kitchen in Database and then add to ArrayAdapter to set in ListView
        kitchenDataModels = new ArrayList<>();
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(Kitchen.this);
        //cursor indicates the kitchen in database Table_Kitchen
        Cursor kitchenCursor = database.IndicatorKitchenTable();

        //Fetch the data from Table_Kitchen
        if(kitchenCursor.moveToFirst())
        {
            do
            {
                //KitchenId for kitchen place in colum 0 in Table_Kitchen
                String getKitchenId= kitchenCursor.getString(0);
                //KitchenName for kitchen place in colum 1 in Table_Kitchen
                String getKitchenName= kitchenCursor.getString(1);
                //KitchenPassword for kitchen place in colum 2 in Table_Kitchen
                String getKitchenPassword=kitchenCursor.getString(2);
                //KitchenImage for Kitchen place in colum 3 in Table_Kitchen
                byte[] getKitchenImage=kitchenCursor.getBlob(3);
                //add KitchenDetails to ArrayList KitchenDataModels
                kitchenDataModels.add(new KitchenDataModel(getKitchenId, getKitchenName,getKitchenPassword,getKitchenImage));
            }
            while(kitchenCursor.moveToNext());
        }

        if(kitchenDataModels!=null)
        {
            //add our data to KitchenAdapter and our context that recycle data in
            kitchenAdapter = new KitchenCustomAdapter(getApplicationContext(), kitchenDataModels);
            //add LinearLayoutManager to KitchenListView
            kitchenListView.setLayoutManager(new LinearLayoutManager(this));
            //add adapter to our KitchenListView
            kitchenListView.setAdapter(kitchenAdapter);
        }
  }

}