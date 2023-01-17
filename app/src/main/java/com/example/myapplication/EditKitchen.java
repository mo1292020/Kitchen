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

//This is  Activity to edit Kitchen
public class EditKitchen extends ToolbarMenu
{
    //EditText to input KitchenName
    private  EditText kitchenNameEditText;
    //EditText to input KitchenPassword
    private  EditText kitchenPasswordEditText;
    //KitchenId to edit
    private String kitchenId;
    //Image to add KitchenImageBitmap
    ImageView kitchenImageImageView;
    //Bitmap of KitchenImage
    Bitmap kitchenImageBitmap;
    //request code of PickImage
    int PICK_IMAGE = 1;

    //OnCreate method to lunch EditKitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for EditKitchen activity
        super.onCreate(savedInstanceState);
        //Set UI EditKitchen activity for EditKitchen activity
        setContentView(R.layout.activity_edit_kitchen);
        //EditKitchenToolbar UI
        Toolbar editKitchenToolbar=(Toolbar) findViewById(R.id.my_toolbar);
        //set toolbar title
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        //set as Edit Kitchen
        toolbarTitle.setText("Edit Kitchen");
        //Set EditKitchenToolbar to our activity
        setSupportActionBar(editKitchenToolbar);
        //EditKitchenExtras to receive any message
        Bundle editKitchenExtras = getIntent().getExtras();
        //receive KitchenId from Kitchen activity
        kitchenId = editKitchenExtras.getString("kitchenId");
        //receive KitchenName from Kitchen activity
        String kitchenName = editKitchenExtras.getString("kitchenName");
        //receive KitchenPassword from Kitchen activity
        String kitchenPassword = editKitchenExtras.getString("kitchenPassword");
        //receive KitchenImage from Kitchen activity
        byte [] kitchenImage = editKitchenExtras.getByteArray("kitchenImage");
        //convert KitchenImage
        InputStream GetKitchenImageBitmap = new ByteArrayInputStream(kitchenImage);
        //convert it to bitmap
        kitchenImageBitmap = BitmapFactory.decodeStream(GetKitchenImageBitmap);
        //link KitchenNameEditText to  EditKitchenEditName
        kitchenNameEditText =findViewById(R.id.EditKitchenEditName);
        //set kitchenName to KitchenNameEditText
        kitchenNameEditText.setText(kitchenName);
        //link KitchenPasswordEditText to  EditKitchenEditPassword
        kitchenPasswordEditText =findViewById(R.id.EditKitchenEditPassword);
        //set kitchenPassword to KitchenPasswordEditText
        kitchenPasswordEditText.setText(kitchenPassword);
        //link KitchenImageImageView to  EditKitchenImageView
        kitchenImageImageView =findViewById(R.id.EditKitchenImageImageView);
        //set kitchenImage to KitchenImageEditText
        kitchenImageImageView.setImageBitmap(kitchenImageBitmap);

    }

    // OnClick method for EditKitchenButton to edit kitchen
    public void EditKitchenListenerButton(View editKitchenButton)
    {
        //get kitchenName from KitchenNameEditText
        String getKitchenName=String.valueOf(kitchenNameEditText.getText());
        //get kitchenPassword from KitchenPasswordEditText
        String getKitchenPassword=String.valueOf(kitchenPasswordEditText.getText());
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(EditKitchen.this);
        //edit kitchen in Table_Kitchen in database by kitchenId
        database.updateKitchen(getKitchenName,getKitchenPassword,kitchenImageBitmap, kitchenId);
        //intent Activity to back to Kitchen activity after edit
        Intent kitchenIntent = new Intent(this, Kitchen.class);
        //start Kitchen activity
        startActivity(kitchenIntent);
    }


    // OnClick method for AddKitchenImageImageView to add new KitchenImage
    public void EditKitchenImageListenerImageView(View EditKitchenImageImageView) throws IOException
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
            Uri kitchenImageUri = data.getData();
            try
            {
                //ByteArray to Compress Image at
                ByteArrayOutputStream bytesArray = new ByteArrayOutputStream();
                //KitchenImage as Bitmap
                kitchenImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), kitchenImageUri);
                //compress KitchenImage as byte at BytesArray
                kitchenImageBitmap.compress(Bitmap.CompressFormat.PNG, 50, bytesArray);
                //link ImageKitchenImageView to our EditKitchenImageImageView
                kitchenImageImageView = findViewById(R.id.EditKitchenImageImageView);
                //set bitmap external image to KitchenImage
                kitchenImageImageView.setImageBitmap(kitchenImageBitmap);

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