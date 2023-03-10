package com.example.myapplication;

//import libraries
import androidx.activity.OnBackPressedCallback;
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
import android.widget.TextView;

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
    //KitchenFoodData to edit
    private static KitchenDataModel kitchenDataModel;
    //FoodData to edit
    private static FoodDataModel foodDataModel;

    //Image to add FoodImageBitmap
    ImageView FoodImageImageView;
    //Bitmap of FoodImage
    Bitmap foodImageBitmap;
    //receive data
    Bundle editFoodExtras;
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
        //EditFoodExtras to receive any message
        editFoodExtras = getIntent().getExtras();
        //receive KitchenData from Food activity
        kitchenDataModel=(KitchenDataModel) editFoodExtras.getSerializable("kitchenData");
        //receive KitchenData from Food activity
        foodDataModel=(FoodDataModel) editFoodExtras.getSerializable("foodData");

        //EditFoodToolbar UI
        Toolbar editFoodToolbar=(Toolbar) findViewById(R.id.my_toolbar);
        //set toolbar title
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        //set as EditFood
        toolbarTitle.setText("Edit "+ kitchenDataModel.getKitchenNameDataModel()+" "+foodDataModel.getFoodNameDataModel());
        //Set EditFoodToolbar to our activity
        setSupportActionBar(editFoodToolbar);

        //convert FoodImage
        InputStream GetFoodImageBitmap = new ByteArrayInputStream(foodDataModel.getFoodImageDataModel());
        //convert it to bitmap
        foodImageBitmap = BitmapFactory.decodeStream(GetFoodImageBitmap);
        //link FoodNameEditText to  EditFoodEditName
        foodNameEditText =findViewById(R.id.EditFoodEditName);
        //set foodName to FoodNameEditText
        foodNameEditText.setText(foodDataModel.getFoodNameDataModel());
        //link FoodPriceEditText to EditFoodEditPrice
        foodPriceEditText =findViewById(R.id.EditFoodEditPrice);
        //set foodPrice to foodPriceEditText
        foodPriceEditText.setText(foodDataModel.getFoodPriceDataModel());
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
        //FoodCustomAdapter  class manage our database
        FoodCustomAdapter foodCustomAdapter = new FoodCustomAdapter(EditFood.this,kitchenDataModel);
        //edit food in Table_Food in foodAdapter by foodId
        foodCustomAdapter.updateFood(foodDataModel.getFoodIdDataModel(),getFoodName,getFoodPrice,foodImageBitmap);
        //finish this activity
        finish();

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
                foodImageBitmap.compress(Bitmap.CompressFormat.PNG, 10, bytesArray);
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