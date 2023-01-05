package com.example.myapplication;

//import libraries
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import java.util.ArrayList;

//This is Main Activity For Showing Kitchen in ListView
public class Kitchen extends ToolbarMenu
{

    //ArrayList to store the kitchen from Database and then add to ArrayAdapter to set in ListView
    ArrayList<KitchenDataModel> kitchenDataModels;
    //Our List View to show Kitchen
    ListView kitchenListView;
    //Array Adapter to manage recycle data
    private  KitchenCustomAdapter kitchenAdapter;
    //popup menu for edit and delete kitchen
    private  PopupMenu kitchenPopupMenu;

    //OnCreate method to lunch Kitchen activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for Kitchen activity
        super.onCreate(savedInstanceState);
        //Set UI kitchen activity for Kitchen activity
        setContentView(R.layout.activity_kitchen);
        //KitchenToolbar UI
        Toolbar kitchenToolbar=findViewById(R.id.KitchenToolbar);
        //Set KitchenToolbar to our activity
        setSupportActionBar(kitchenToolbar);
        //method to fetch kitchen data from database and add in ListView
        CreateKitchenListView();
    }

    // OnClick method for ImageView Icon to add new Kitchen
    public void AddKitchenListenerIcon(View addKitchenIcon)
    {
        //intent Activity Add Kitchen
        Intent addKitchenIntent = new Intent(this, AddKitchen.class);
        //start add kitchen activity
        startActivity(addKitchenIntent);
    }

    //method to fetch kitchen data from database and add in ListView
    public void CreateKitchenListView()
    {
        //Our UI Kitchen ListView
        kitchenListView = findViewById(R.id.KitchenListView);
        //ArrayList to store the kitchen in Database and then add to ArrayAdapter to set in ListView
        kitchenDataModels = new ArrayList<>();
        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(Kitchen.this);
        //cursor indicates the kitchen in database Table_Kitchen
        Cursor kitchenCursor = database.IndicatorKitchenTable();

        //Fetch the data from Table_Kitchen
        if(kitchenCursor.moveToFirst())
        {
            do
            {
                //KitchenId for kitchen place in colum 0 in Table_Kitchen
                String getKitchenId= kitchenCursor.getString(0);
                //KitchenName for kitchen place in colum 1 in Table_Kitchen
                String getKitchenName= kitchenCursor.getString(1);
                //KitchenPassword for kitchen place in colum 2 in Table_Kitchen
                String getKitchenPassword=kitchenCursor.getString(2);
                //add KitchenDetails to ArrayList KitchenDataModels
                kitchenDataModels.add(new KitchenDataModel(getKitchenId, getKitchenName,getKitchenPassword));
            }
            while(kitchenCursor.moveToNext());
        }

        //add our data to KitchenAdapter and our context that recycle data in
        kitchenAdapter = new KitchenCustomAdapter(kitchenDataModels,getApplicationContext());
        //add adapter to our KitchenListView
        kitchenListView.setAdapter(kitchenAdapter);

        //add click listener for every kitchen to show food that provide in new activity
        kitchenListView.setOnItemClickListener((parent, view, position, id) ->
        {
            //intent Food activity
            Intent intent = new Intent(getApplicationContext(),Food.class);
            //send KitchenId as a message to Activity Food
            intent.putExtra("kitchenId",kitchenDataModels.get(position).getKitchenIdDataModel());
            //start Food Activity
            startActivity(intent);
        });

        //add LongClick listener for every kitchen to Edit or Delete Kitchen by PopMenu
        kitchenListView.setOnItemLongClickListener((arg0, view, index, arg3) ->
        {
            //Create KitchenPopupMenu
            kitchenPopupMenu = new PopupMenu(Kitchen.this, view);
            //add our UI to KitchenPopupMenu
            kitchenPopupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, kitchenPopupMenu.getMenu());

            //add click listener for Edit or Delete Kitchen in KitchenPopupMenu
            kitchenPopupMenu.setOnMenuItemClickListener(item -> {
                //if choose to delete kitchen
                if(item.getTitle().equals("Delete"))
                {
                    //delete kitchen from database
                    database.DeleteKitchen(kitchenAdapter.getItem(index).getKitchenIdDataModel());
                    //delete kitchen from KitchenAdapter
                    kitchenAdapter.remove(kitchenDataModels.get(index));
                    //notify Change data to adapter to recycle
                    kitchenAdapter.notifyDataSetChanged();
                }
                //if choose to edit kitchen
                if(item.getTitle().equals("Edit"))
                {
                    //intent for edit kitchen
                    Intent editKitchenIntent = new Intent(getApplicationContext(),EditKitchen.class);
                    //send KitchenId to activity edit kitchen
                    editKitchenIntent.putExtra("KitchenId",kitchenAdapter.getItem(index).getKitchenIdDataModel());
                    //send KitchenName to activity edit kitchen
                    editKitchenIntent.putExtra("KitchenName", kitchenAdapter.getItem(index).getKitchenNameDataModel());
                    //send KitchenPassword to activity edit kitchen
                    editKitchenIntent.putExtra("KitchenPassword", kitchenAdapter.getItem(index).getKitchenPasswordDataModel());
                    //start edit food activity
                    startActivity(editKitchenIntent);
                }
                return true;
            });
            //finally show kitchenPopupMenu
            kitchenPopupMenu.show();
            return true;
        });
    }

}