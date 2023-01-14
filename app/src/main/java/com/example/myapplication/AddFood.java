package com.example.myapplication;

//import libraries
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

//This is  Activity to add new Food
public class AddFood extends ToolbarMenu
{

    //EditText to input FoodName
    EditText foodName;
    //EditText to input FoodPrice
    EditText foodPrice;
    //to receive KitchenId for this food
    private static String kitchenId;
    //Image to add FoodImageBitmap
    ImageView foodImageImageView;
    //Bitmap of FoodImage
    Bitmap foodImageBitmap;
    //request code of PickImage
    int PICK_IMAGE = 1;

    //OnCreate method to lunch AddFood activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for AddFood activity
        super.onCreate(savedInstanceState);
        //Set UI AddFood activity for AddFood activity
        setContentView(R.layout.activity_add_food);
        //AddFoodToolbar UI
        Toolbar addFoodToolbar= findViewById(R.id.AddFoodToolbar);
        //Set AddFoodToolbar to our activity
        setSupportActionBar(addFoodToolbar);
        //Set logo to toolbar
        getSupportActionBar().setLogo(R.drawable.toolbar_icon);
        //AddFoodExtras to receive any message
        Bundle addFoodExtras = getIntent().getExtras();
        //receive kitchenId from Food activity
        kitchenId= addFoodExtras.getString("kitchenId");

        //handel OnBackPress of our activity
        AddFood.this.getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true)
                {
                    @Override
                    public void handleOnBackPressed()
                    {
                        //intent Activity to back to Food activity
                        Intent foodIntent = new Intent(AddFood.this, Food.class);
                        //send kitchen id to food activity to show food link with
                        foodIntent.putExtra("kitchenId",kitchenId);
                        //start Food activity
                        startActivity(foodIntent);
                    }
                });

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
        database.addFood(kitchenId,getFoodName,getFoodPrice,foodImageBitmap);
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

    // OnClick method for AddFoodImageImageView to add new FoodImage
    public void AddFoodImageListenerImageView(View AddFoodImageImageView) throws IOException
    {
        //intent Activity to open device storage media to pick image
        Intent PickExternalImageIntent = new Intent(Intent.ACTION_PICK);
        //set type of item that pick "image"
        PickExternalImageIntent.setType("image/*");
        //set action to our intent
        PickExternalImageIntent.setAction(Intent.ACTION_GET_CONTENT);
        //set possibility to crop image
        PickExternalImageIntent.putExtra("crop", "true");
        //intent width
        PickExternalImageIntent.putExtra("outputX", 100);
        //intent height
        PickExternalImageIntent.putExtra("outputY", 100);
        //set possibility to scale image
        PickExternalImageIntent.putExtra("scale", true);
        //set possibility to return data
        PickExternalImageIntent.putExtra("return-data", true);
        //start external storage to pick image with request code 1
        startActivityForResult(Intent.createChooser(PickExternalImageIntent, "Select Picture"), PICK_IMAGE);
    }


    //method to get image from external storage and convert to bitmap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //set RequestCode & ResultCode & data to super class
        super.onActivityResult(requestCode, resultCode, data);
        //check if intent sender with RequestCode PICK_IMAGE and not null data
        if (requestCode == PICK_IMAGE && data != null)
        {
            //get KitchenImageUrl
            Uri foodImageUri = data.getData();
            try
            {
                //ByteArray to Compress Image at
                ByteArrayOutputStream bytesArray = new ByteArrayOutputStream();
                //FoodImage as Bitmap
                foodImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), foodImageUri);
                //compress FoodImage as byte at BytesArray
                foodImageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bytesArray);
                //link ImageFoodImageView to our AddFoodImageImageView
                foodImageImageView = findViewById(R.id.AddFoodImageImageView);
                //set bitmap external image to FoodImage
                foodImageImageView.setImageBitmap(foodImageBitmap);
            }
            // handel exception
            catch (IOException e)
            {
                //print ExceptionDetails
                e.printStackTrace();
            }
        }

    }


}