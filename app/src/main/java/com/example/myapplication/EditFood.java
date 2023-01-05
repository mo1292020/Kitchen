package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//This is  Activity to edit Food
public class EditFood extends ToolbarMenu
{
    //EditText to input FoodName
    private  EditText foodNameEditText;
    //EditText to input FoodPrice
    private  EditText foodPriceEditText;
    //FoodId to edit
    private static String foodId;
    //KitchenFoodId to edit
    private static String kitchenId;


    //OnCreate method to lunch EditFood activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for EditFood activity
        super.onCreate(savedInstanceState);
        //Set UI EditFood activity for EditFood activity
        setContentView(R.layout.activity_edit_food);
        //EditFoodToolbar UI
        Toolbar editFoodToolbar=(Toolbar) findViewById(R.id.EditFoodToolbar);
        //Set EditFoodToolbar to our activity
        setSupportActionBar(editFoodToolbar);
        //EditFoodExtras to receive any message
        Bundle editFoodExtras = getIntent().getExtras();
        //receive FoodId from Food activity
        foodId= editFoodExtras.getString("foodId");
        //receive FoodName from Food activity
        kitchenId= editFoodExtras.getString("kitchenId");
        //receive FoodName from Food activity
        //FoodName to edit
        String foodName = editFoodExtras.getString("foodName");
        //link FoodNameEditText to  EditFoodEditName
        foodNameEditText =findViewById(R.id.EditFoodEditName);
        //set foodName to FoodNameEditText
        foodNameEditText.setText(foodName);
        //receive FoodPrice from Food activity
        //FoodPrice to edit
        String foodPrice = editFoodExtras.getString("foodPrice");
        //link FoodPriceEditText to EditFoodEditPrice
        foodPriceEditText =findViewById(R.id.EditFoodEditPrice);
        //set foodPrice to foodPriceEditText
        foodPriceEditText.setText(foodPrice);
    }

    // OnClick method for EditFoodButton to edit food
    public void EditFoodListenerButton(View editFoodButton)
    {
        //get foodName from foodNameEditText
        String getFoodName=String.valueOf(foodNameEditText.getText());
        //get foodPrice from foodPriceEditText
        String getFoodPrice=String.valueOf(foodPriceEditText.getText());
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(EditFood.this);
        //edit food in Table_Food in database by foodId
        database.updateFood(foodId,getFoodName,getFoodPrice);
        //intent Activity to back to Food activity after edit
        Intent foodIntent = new Intent(this, Food.class);
        //send kitchen id to food activity to show food link with
        foodIntent.putExtra("kitchenId",kitchenId);
        //start Food activity
        startActivity(foodIntent);
    }

    // OnClick method for CancelEditFoodButton to back to Food activity without edit food
    public void CancelEditFoodListenerButton(View cancelEditFoodButton)
    {
        //intent Activity to back to food activity without edit
        Intent foodIntent = new Intent(this, Food.class);
        //send kitchen id to food activity to show food link with
        foodIntent.putExtra("kitchenId",kitchenId);
        //start Food activity
        startActivity(foodIntent);
    }

}