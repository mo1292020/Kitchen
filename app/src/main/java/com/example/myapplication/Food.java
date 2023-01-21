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
import android.widget.TextView;

import java.util.ArrayList;

//This is Food Activity For Showing Food of Kitchen request in ListView
public class Food extends ToolbarMenu
{
    //ArrayList to store the Food from Database and then add to ArrayAdapter to set in ListView
    ArrayList<FoodDataModel> foodDataModels;
    //Our List View to show Food
    RecyclerView FoodListView;
    //Array Adapter to manage recycle data
    private FoodCustomAdapter foodAdapter;
    //KitchenDataModel to receive Kitchen Data from KitchenCustomAdapter
    KitchenDataModel kitchenDataModel;


    //OnCreate method to lunch Kitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for Kitchen activity
        super.onCreate(savedInstanceState);
        //Set UI food activity for Kitchen activity
        setContentView(R.layout.activity_food);
        //FoodToolbar UI
        Toolbar toolbar= findViewById(R.id.my_toolbar);
        //Set FoodToolbar to our activity
        setSupportActionBar(toolbar);
        //extras to receive any message
        Bundle extras = getIntent().getExtras();
        //receive kitchenData from KitchenCustomAdapter  from item selected
        kitchenDataModel = (KitchenDataModel) extras.getSerializable("kitchenData");
        //set toolbar title
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        //set as Food
        toolbarTitle.setText(kitchenDataModel.getKitchenNameDataModel()+" Food");
        //method to fetch food data from database and add in ListView
        CreateList(kitchenDataModel.getKitchenIdDataModel());

    }

    // OnClick method for ImageView Icon to add new Food
    public void AddFoodListenerIcon(View addFoodIcon)
    {
        //intent Activity Add Food
        Intent addFoodIntent = new Intent(this, AddFood.class);
        //send kitchen data to link food with it in database
        addFoodIntent.putExtra("kitchenData", kitchenDataModel);
        //start add food activity
        startActivity(addFoodIntent);
    }

    //method to fetch food data from database and add in ListView
    public void CreateList(String kitchenId)
    {


            //add our data to FoodAdapter and our context that recycle data in
            foodAdapter = new FoodCustomAdapter(getApplicationContext(),kitchenDataModel);
            //Layout Manager to RecycleView to setOrientation
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            //when you want horizontal
            //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //when you want vertical
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //Our UI Food ListView
            FoodListView =findViewById(R.id.FoodListView);
            //add LinearLayoutManager to FoodListView
            FoodListView.setLayoutManager(linearLayoutManager);
            //hide scrollbar
            FoodListView.setScrollBarSize(0);
            //add adapter to our FoodListView
            FoodListView.setAdapter(foodAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //add our data to FoodAdapter and our context that recycle data in
        foodAdapter = new FoodCustomAdapter(getApplicationContext(),kitchenDataModel);
        //set foodAdapter to listView
        FoodListView.setAdapter(foodAdapter);
    }


}