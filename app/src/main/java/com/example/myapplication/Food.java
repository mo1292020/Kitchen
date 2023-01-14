package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//This is Food Activity For Showing Food of Kitchen request in ListView
public class Food extends ToolbarMenu
{
    //ArrayList to store the Food from Database and then add to ArrayAdapter to set in ListView
    ArrayList<FoodDataModel> foodDataModels;
    //Our List View to show Food
    RecyclerView foodListView;
    //Array Adapter to manage recycle data
    private  FoodCustomAdapter foodAdapter;
    //KitchenId that request to show their food
    private static String kitchenId;

    //OnCreate method to lunch Kitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for Kitchen activity
        super.onCreate(savedInstanceState);
        //Set UI food activity for Kitchen activity
        setContentView(R.layout.activity_food);
        //FoodToolbar UI
        Toolbar toolbar= findViewById(R.id.FoodToolbar);
        //Set FoodToolbar to our activity
        setSupportActionBar(toolbar);
        //extras to receive any message
        Bundle extras = getIntent().getExtras();
        //receive kitchenId from Kitchen activity from item selected
        kitchenId = extras.getString("kitchenId");
        //method to fetch food data from database and add in ListView
        CreateList(kitchenId);
    }

    // OnClick method for ImageView Icon to add new Food
    public void AddFoodListenerIcon(View addFoodIcon)
    {
        //intent Activity Add Food
        Intent addFoodIntent = new Intent(this, AddFood.class);
        //send kitchen id to link food with it in database
        addFoodIntent.putExtra("kitchenId", kitchenId);
        //start add food activity
        startActivity(addFoodIntent);
    }

    //method to fetch food data from database and add in ListView
    public void CreateList(String kitchenId)
    {
        //Our UI Food ListView
        foodListView =findViewById(R.id.FoodListView);
        //ArrayList to store the Food in Database and then add to ArrayAdapter to set in ListView
        foodDataModels = new ArrayList<>();
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(Food.this);
        //cursor indicates the food in database Table_Food
        Cursor foodCursor = database.IndicatorFoodTable();

        //Fetch the data from Table_Kitchen
        if(foodCursor.moveToFirst())
        {
            do
            {
                //FoodId for food place in colum 0 in Table_Food
                String getFoodId=foodCursor.getString(0);
                //FoodName for food place in colum 1 in Table_Food
                String getFoodName= foodCursor.getString(1);
                //FoodPrice for food place in colum 2 in Table_Food
                String getFoodPrice= foodCursor.getString(2);
                //KitchenFoodName for food place in colum 3 in Table_Food
                String getKitchenFoodID= foodCursor.getString(3);
                //KitchenImage for Kitchen place in colum 3 in Table_Kitchen
                byte[] getFoodImage=foodCursor.getBlob(4);
                //Check if this food belong to this KitchenId or not
                if(getKitchenFoodID.equals(kitchenId))
                    //add FoodDetails to ArrayList FoodDataModels
                    foodDataModels.add(new FoodDataModel(getFoodId, getFoodName, getFoodPrice, getKitchenFoodID,getFoodImage));
            }
            while(foodCursor.moveToNext());
        }

        if(foodDataModels!=null)
        {
            //add our data to FoodAdapter and our context that recycle data in
            foodAdapter = new FoodCustomAdapter(getApplicationContext(), foodDataModels);
            //add LinearLayoutManager to FoodListView
            foodListView.setLayoutManager(new LinearLayoutManager(this));
            //add adapter to our FoodListView
            foodListView.setAdapter(foodAdapter);
        }

    }

}