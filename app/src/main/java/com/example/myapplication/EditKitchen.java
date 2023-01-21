package com.example.myapplication;

//import libraries
import static com.example.myapplication.Validate.checkKitchen;
import static com.example.myapplication.Validate.replaceAll;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
    //KitchenDataModel to receive Kitchen Data from kitchenCustomAdapter
    KitchenDataModel kitchenDataModel;
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
        //Set EditKitchenToolbar to our activity
        setSupportActionBar(editKitchenToolbar);
        //EditKitchenExtras to receive any message
        Bundle editKitchenExtras = getIntent().getExtras();
        //receive KitchenData From KitchenCustomAdapter
        kitchenDataModel=(KitchenDataModel) editKitchenExtras.getSerializable("kitchenData");
        //set toolbar title
        TextView toolbarTitle=findViewById(R.id.toolbar_title);
        //set as Edit Kitchen
        toolbarTitle.setText("Edit " + kitchenDataModel.getKitchenNameDataModel()+ " Kitchen");
        //convert KitchenImage
        InputStream GetKitchenImageBitmap = new ByteArrayInputStream(kitchenDataModel.getKitchenImageDataModel());
        //convert it to bitmap
        kitchenImageBitmap = BitmapFactory.decodeStream(GetKitchenImageBitmap);

        //link KitchenNameEditText to  EditKitchenEditName
        kitchenNameEditText =findViewById(R.id.EditKitchenEditName);
        //set kitchenName to KitchenNameEditText
        kitchenNameEditText.setText(kitchenDataModel.getKitchenNameDataModel());
        //link KitchenPasswordEditText to  EditKitchenEditPassword
        kitchenPasswordEditText =findViewById(R.id.EditKitchenEditPassword);
        //set kitchenPassword to KitchenPasswordEditText
        kitchenPasswordEditText.setText(kitchenDataModel.getKitchenPasswordDataModel());
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
        //check if kitchenName not repeated
        boolean checkRepeatedName=checkKitchen(getApplicationContext(),getKitchenName);
        //check if kitchenName doesn't change
        boolean checkExistName=replaceAll(getKitchenName).equals(replaceAll(kitchenDataModel.getKitchenNameDataModel()));
        //check password length
        boolean checkPassword=getKitchenPassword.length()>=8;
        //validation name and password
        if((checkRepeatedName||checkExistName)&&(checkPassword))
        {
            //KitchenCustomAdapter  class manage our database
            KitchenCustomAdapter kitchenCustomAdapter = new KitchenCustomAdapter(EditKitchen.this);
            //add kitchen in Table_Kitchen in kitchenAdapter by kitchenId
            kitchenCustomAdapter.updateKitchen(getKitchenName, getKitchenPassword, kitchenImageBitmap,kitchenDataModel.getKitchenIdDataModel());
            //finish this activity
            finish();
        }
        else
        {
            //if password validate then name has error
            if(checkPassword)
            {
                //show message error at kitchenNameEditText
                kitchenNameEditText.setError("Kitchen Name is taken");
            }
            //invalidate password
            else
            {
                //show message error kitchenPasswordEditText
                kitchenPasswordEditText.setError("8 character at least");
            }
        }
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
                kitchenImageBitmap.compress(Bitmap.CompressFormat.PNG, 10, bytesArray);
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