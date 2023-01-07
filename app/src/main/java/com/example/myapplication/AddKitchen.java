package com.example.myapplication;

//import libraries
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

//This is  Activity to add new Kitchen
public class AddKitchen extends ToolbarMenu
{

    //EditText to input KitchenName
    EditText kitchenName;
    //EditText to input KitchenPassword
    EditText kitchenPassword;
    //Image to add KitchenImageBitmap
    ImageView kitchenImageImageView;
    //Bitmap of KitchenImage
    Bitmap kitchenImageBitmap;
    //request code of PickImage
    int PICK_IMAGE = 1;

    //OnCreate method to lunch AddKitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for AddKitchen activity
        super.onCreate(savedInstanceState);
        //Set UI AddKitchen activity for AddKitchen activity
        setContentView(R.layout.activity_add_kitchen);
        //AddKitchenToolbar UI
        Toolbar addKitchenToolbar=(Toolbar) findViewById(R.id.AddKitchenToolbar);
        //Set AddKitchenToolbar to our activity
        setSupportActionBar(addKitchenToolbar);
    }

    // OnClick method for AddKitchenButton to add new Kitchen into database
    public void AddKitchenListenerButton(View addKitchenButton)
    {
        //link NameKitchenEditText to our EditText
        kitchenName =findViewById(R.id.AddKitchenEditName);
        //link PasswordKitchenEditText to our EditText
        kitchenPassword =findViewById(R.id.AddKitchenEditPassword);
        //get kitchenName from NameKitchenEditText
        String getKitchenName=String.valueOf(kitchenName.getText());
        //get kitchenPassword from PasswordKitchenEditText
        String getKitchenPassword=String.valueOf(kitchenPassword.getText());
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(AddKitchen.this);
        //add new kitchen to Table_Kitchen in database
        database.addKitchen(getKitchenName,getKitchenPassword,kitchenImageBitmap);
        //intent Activity to back to Kitchen activity after add
        Intent kitchenIntent = new Intent(this, Kitchen.class);
        //start kitchen activity
        startActivity(kitchenIntent);
    }

    // OnClick method for CancelAddKitchenButton to back to kitchen activity without add new kitchen
    public void CancelAddKitchenListenerButton(View cancelAddKitchenButton)
    {
        //intent Activity to back to Kitchen activity without add
        Intent kitchenIntent = new Intent(this, Kitchen.class);
        //start kitchen activity
        startActivity(kitchenIntent);
    }

    // OnClick method for AddKitchenImageImageView to add new KitchenImage
    public void AddKitchenImageListenerImageView(View AddKitchenImageImageView) throws IOException
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
                //link ImageKitchenImageView to our AddKitchenImageImageView
                kitchenImageImageView = findViewById(R.id.AddKitchenImageImageView);
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