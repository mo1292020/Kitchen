package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//This is  Activity to add new Kitchen
public class AddKitchen extends ToolbarMenu
{

    //EditText to input KitchenName
    EditText kitchenName;
    //EditText to input KitchenPassword
    EditText kitchenPassword;

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
        database.addKitchen(getKitchenName,getKitchenPassword);
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

}