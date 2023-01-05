package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//This is  Activity to edit Kitchen
public class EditKitchen extends ToolbarMenu
{
    //EditText to input KitchenName
    private  EditText kitchenNameEditText;
    //EditText to input KitchenPassword
    private  EditText kitchenPasswordEditText;
    //KitchenId to edit
    private String kitchenId;

    //OnCreate method to lunch EditKitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for EditKitchen activity
        super.onCreate(savedInstanceState);
        //Set UI EditKitchen activity for EditKitchen activity
        setContentView(R.layout.activity_edit_kitchen);
        //EditKitchenToolbar UI
        Toolbar editKitchenToolbar=(Toolbar) findViewById(R.id.EditKitchenToolbar);
        //Set EditKitchenToolbar to our activity
        setSupportActionBar(editKitchenToolbar);
        //EditKitchenExtras to receive any message
        Bundle editKitchenExtras = getIntent().getExtras();
        //receive KitchenId from Kitchen activity
        kitchenId = editKitchenExtras.getString("kitchenId");
        //receive KitchenName from Kitchen activity
        //KitchenName to edit
        String kitchenName = editKitchenExtras.getString("kitchenName");
        //receive KitchenPassword from Kitchen activity
        //KitchenPassword to edit
        String kitchenPassword = editKitchenExtras.getString("kitchenPassword");
        //link KitchenNameEditText to  EditKitchenEditName
        kitchenNameEditText =findViewById(R.id.EditKitchenEditName);
        //set kitchenName to KitchenNameEditText
        kitchenNameEditText.setText(kitchenName);
        //link KitchenPasswordEditText to  EditKitchenEditPassword
        kitchenPasswordEditText =findViewById(R.id.EditKitchenEditPassword);
        //set kitchenPassword to KitchenPasswordEditText
        kitchenPasswordEditText.setText(kitchenPassword);
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
        database.updateKitchen(getKitchenName,getKitchenPassword, kitchenId);
        //intent Activity to back to Kitchen activity after edit
        Intent kitchenIntent = new Intent(this, Kitchen.class);
        //start Kitchen activity
        startActivity(kitchenIntent);
    }

    public void CancelEditKitchenListenerButton(View CancelEditKitchenButton)
    {
        //intent Activity to back to kitchen activity without edit
        Intent intent = new Intent(this, Kitchen.class);
        //start Food activity
        startActivity(intent);
    }

}