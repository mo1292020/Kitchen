package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
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
    ListView foodListView;
    //Array Adapter to manage recycle data
    private  FoodCustomAdapter foodAdapter;
    //popup menu for edit and delete food
    private  PopupMenu foodPopupMenu;
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
        addFoodIntent.putExtra("KitchenId", kitchenId);
        //start add food activity
        startActivity(addFoodIntent);
    }

    //method to fetch food data from database and add in ListView
    public void CreateList(String KitchenId)
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
                //Check if this food belong to this KitchenId or not
                if(getKitchenFoodID.equals(KitchenId))
                    //add FoodDetails to ArrayList FoodDataModels
                    foodDataModels.add(new FoodDataModel(getFoodId, getFoodName, getFoodPrice, getKitchenFoodID));
            }
            while(foodCursor.moveToNext());
        }

        //add our data to FoodAdapter and our context that recycle data in
        foodAdapter = new FoodCustomAdapter(foodDataModels,getApplicationContext());
        //add adapter to our FoodListView
        foodListView.setAdapter(foodAdapter);

        //add click listener for every food to show food details until now
        foodListView.setOnItemClickListener((parent, view, position, id) ->
        {
            //get foodName from FoodAdapter
            String name = foodAdapter.getItem(position).getFoodNameDataModel();
            //get foodPrice from FoodAdapter
            String price= foodAdapter.getItem(position).getFoodPriceDataModel();
            //show them in a toast
            Toast.makeText(Food.this,"Food "+name+" with price "+price+" ordered", Toast.LENGTH_SHORT).show();
        });

        //add LongClick listener for every food to Edit or Delete food by PopMenu
        foodListView.setOnItemLongClickListener((arg0, view, index, arg3) ->
        {
            //Create FoodPopupMenu
            foodPopupMenu = new PopupMenu(Food.this, view);
            //add our UI to FoodPopupMenu
            foodPopupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, foodPopupMenu.getMenu());

            //add click listener for Edit or Delete food in FoodPopupMenu
            foodPopupMenu.setOnMenuItemClickListener(item -> {
                //if choose to delete food
                if(item.getTitle().equals("Delete"))
                {
                    //delete food from database
                    database.DeleteFood(foodAdapter.getItem(index).getFoodIdDataModel());
                    //delete food from FoodAdapter
                    foodAdapter.remove(foodDataModels.get(index));
                    //notify Change data to adapter to recycle
                    foodAdapter.notifyDataSetChanged();
                }
                //if choose to edit kitchen
                if(item.getTitle().equals("Edit"))
                {
                    //intent for edit food
                    Intent editFoodIntent = new Intent(getApplicationContext(),EditFood.class);
                    //send FoodId to activity edit food
                    editFoodIntent.putExtra("foodId", foodAdapter.getItem(index).getFoodIdDataModel());
                    //send FoodName to activity edit food
                    editFoodIntent.putExtra("foodName", foodAdapter.getItem(index).getFoodNameDataModel());
                    //send FoodPrice to activity edit food
                    editFoodIntent.putExtra("foodPrice", foodAdapter.getItem(index).getFoodPriceDataModel());
                    //send FoodKitchenId to activity edit food
                    editFoodIntent.putExtra("kitchenId", foodAdapter.getItem(index).getKitchenFoodIdDataModel());
                    //start edit food activity
                    startActivity(editFoodIntent);
                }

                return true;
            });
            foodPopupMenu.show();
            return true;
        });
    }

}