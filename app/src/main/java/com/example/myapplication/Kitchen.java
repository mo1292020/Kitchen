package com.example.myapplication;

//import libraries
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

//This is Main Activity For Showing Kitchen in ListView
public class Kitchen extends ToolbarMenu
{

    //ArrayList to store the kitchen from Database and then add to ArrayAdapter to set in ListView
    ArrayList<KitchenDataModel> kitchenDataModels;
    //Our List View to show Kitchen
    RecyclerView KitchenListView;
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
        Toolbar kitchenToolbar=findViewById(R.id.my_toolbar);
        //set toolbar title
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        //set as Edit Kitchen
        toolbarTitle.setText("Kitchen");
        //Set KitchenToolbar to our activity
        setSupportActionBar(kitchenToolbar);
       //method to fetch kitchen data from database and add in ListView
        CreateKitchenListView();
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
            KitchenListView = findViewById(R.id.KitchenListView);
            //add our data to KitchenAdapter and our context that recycle data in
            kitchenAdapter = new KitchenCustomAdapter(getApplicationContext());
            //Layout Manager to RecycleView to setOrientation
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            //when you want horizontal
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //when you want vertical
            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //add LinearLayoutManager to KitchenListView
            KitchenListView.setLayoutManager(linearLayoutManager);
            //hide scrollbar
            KitchenListView.setScrollBarSize(0);
            //add adapter to our KitchenListView
            KitchenListView.setAdapter(kitchenAdapter);

  }

    @Override
    protected void onResume() {
        super.onResume();
        //add our data to FoodAdapter and our context that recycle data in
        kitchenAdapter = new KitchenCustomAdapter(getApplicationContext());
        //set foodAdapter to listView
        KitchenListView.setAdapter(kitchenAdapter);
    }

}