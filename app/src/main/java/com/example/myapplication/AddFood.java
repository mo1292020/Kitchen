package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//This is  Activity to add new Food
public class AddFood extends ToolbarMenu
{

    //EditText to input FoodName
    EditText foodName;
    //EditText to input FoodPrice
    EditText foodPrice;
    //to receive KitchenId for this food
    private static String kitchenId;

    //OnCreate method to lunch AddFood activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for AddFood activity
        super.onCreate(savedInstanceState);
        //Set UI AddFood activity for AddFood activity
        setContentView(R.layout.activity_add_food);
        //AddFoodToolbar UI
        Toolbar addFoodToolbar=(Toolbar) findViewById(R.id.AddFoodToolbar);
        //Set AddFoodToolbar to our activity
        setSupportActionBar(addFoodToolbar);
        //AddFoodExtras to receive any message
        Bundle addFoodExtras = getIntent().getExtras();
        //receive kitchenId from Food activity
        kitchenId= addFoodExtras.getString("KitchenId");
    }

    // OnClick method for AddFoodButton to add new Food into database
    public void AddFoodListenerButton(View addFoodButton)
    {
        //link NameFoodEditText to our EditText
        foodName =findViewById(R.id.AddFoodEditName);
        //link PriceFoodEditText to our EditText
        foodPrice =findViewById(R.id.AddFoodEditPrice);
        //get FoodName from NameFoodEditText
        String getFoodName=String.valueOf(foodName.getText());
        //get FoodPrice from PriceKitchenEditText
        String getFoodPrice=String.valueOf(foodPrice.getText());
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(AddFood.this);
        //add new food to Table_Food in database
        database.addFood(kitchenId,getFoodName,getFoodPrice);
        //intent Activity to back to Food activity after add
        Intent foodIntent = new Intent(this, Food.class);
        //send kitchen id to food activity to show food link with
        foodIntent.putExtra("kitchenId",kitchenId);
        //start Food activity
        startActivity(foodIntent);
    }

    // OnClick method for CancelAddFoodButton to back to Food activity without add new food
    public void CancelAddFoodListenerButton(View cancelAddFoodButton)
    {
        //intent Activity to back to food activity without add
        Intent foodIntent = new Intent(this, Food.class);
        //send kitchen id to food activity to show food link with
        foodIntent.putExtra("kitchenId",kitchenId);
        //start Food activity
        startActivity(foodIntent);
    }

}