package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    //Image to add FoodImageBitmap
    ImageView FoodImageImageView;
    //Bitmap of FoodImage
    Bitmap foodImageBitmap;
    //request code of PickImage
    int PICK_IMAGE = 1;

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
        //FoodName to edit
        String foodName = editFoodExtras.getString("foodName");
        //FoodPrice to edit
        String foodPrice = editFoodExtras.getString("foodPrice");
        //receive FoodImage from Food activity
        byte [] foodImage = editFoodExtras.getByteArray("foodImage");
        //convert FoodImage
        InputStream GetFoodImageBitmap = new ByteArrayInputStream(foodImage);
        //convert it to bitmap
        foodImageBitmap = BitmapFactory.decodeStream(GetFoodImageBitmap);
        //link FoodNameEditText to  EditFoodEditName
        foodNameEditText =findViewById(R.id.EditFoodEditName);
        //set foodName to FoodNameEditText
        foodNameEditText.setText(foodName);
        //link FoodPriceEditText to EditFoodEditPrice
        foodPriceEditText =findViewById(R.id.EditFoodEditPrice);
        //set foodPrice to foodPriceEditText
        foodPriceEditText.setText(foodPrice);
        //link FoodImageImageView to  EditFoodImageView
        FoodImageImageView =findViewById(R.id.EditFoodImageImageView);
        //set FoodImage to FoodImageEditText
        FoodImageImageView.setImageBitmap(foodImageBitmap);
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
        database.updateFood(foodId,getFoodName,getFoodPrice,foodImageBitmap);
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

    // OnClick method for AddFoodImageImageView to add new FoodImage
    public void EditFoodImageListenerImageView(View EditFoodImageImageView) throws IOException
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
            //get FoodImageUrl
            Uri foodImageUri = data.getData();
            try
            {
                //ByteArray to Compress Image at
                ByteArrayOutputStream bytesArray = new ByteArrayOutputStream();
                //FoodImage as Bitmap
                foodImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), foodImageUri);
                //compress FoodImage as byte at BytesArray
                foodImageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bytesArray);
                //link ImageFoodImageView to our EditFoodImageImageView
                FoodImageImageView = findViewById(R.id.EditFoodImageImageView);
                //set bitmap external image to FoodImage
                FoodImageImageView.setImageBitmap(foodImageBitmap);

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