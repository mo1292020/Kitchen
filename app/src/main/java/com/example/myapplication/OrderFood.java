package com.example.myapplication;

//import libraries
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

//This is Food Activity For Showing Food of Kitchen request in ListView
public class OrderFood extends ToolbarMenu
{
    //FoodDataModel to receive FoodData from FoodCustomAdapter
    FoodDataModel foodDataModels;
    //KitchenDataModel to receive KitchenData from FoodCustomAdapter
    KitchenDataModel kitchenDataModel;

    //OrderFoodImageImageView to set FoodImage
    public ImageView OrderFoodImageImageView;
    //OrderFoodNameTextView to set FoodName
    public TextView OrderFoodNameTextView;
    //OrderFoodPriceTextView to set FoodPrice
    public TextView OrderFoodPriceTextView;

    //OnCreate method to lunch Kitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for Kitchen activity
        super.onCreate(savedInstanceState);
        //Set UI food activity for Kitchen activity
        setContentView(R.layout.activity_order_food);
        //FoodToolbar UI
        Toolbar toolbar= findViewById(R.id.my_toolbar);
        //Set FoodToolbar to our activity
        setSupportActionBar(toolbar);

        //extras to receive any message
        Bundle extras = getIntent().getExtras();
        //receive foodData from food activity from item selected
        foodDataModels = (FoodDataModel) extras.getSerializable("foodData");
        //receive kitchenData from food activity from item selected
        kitchenDataModel = (KitchenDataModel) extras.getSerializable("kitchenData");

        //set toolbar title
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        //set as Food
        toolbarTitle.setText(kitchenDataModel.getKitchenNameDataModel()+" "+foodDataModels.getFoodNameDataModel());

        //link with FoodImageImageView in xml
        OrderFoodImageImageView =  findViewById(R.id.OrderFoodImageImageView);
        //link with FoodNameTextView in xml
        OrderFoodNameTextView =  findViewById(R.id.OrderFoodNameTextView);
        //link with FoodPriceTextView in xml
        OrderFoodPriceTextView = findViewById(R.id.OrderFoodPriceTextView);

        //get FoodImage as a byte from FoodData to convert to Bitmap
        InputStream GetFoodImageBitmap = new ByteArrayInputStream(foodDataModels.getFoodImageDataModel());
        //convert it to bitmap
        Bitmap FoodImageBitmap = BitmapFactory.decodeStream(GetFoodImageBitmap);
        //set FoodImage as a Bitmap to OrderFoodImageImageView
        OrderFoodImageImageView.setImageBitmap(FoodImageBitmap);

        //set FoodName to OrderFoodName
        OrderFoodNameTextView.setText(foodDataModels.getFoodNameDataModel());
        //set FoodPrice to OrderFoodPrice
        OrderFoodPriceTextView.setText(foodDataModels.getFoodPriceDataModel());

    }
}