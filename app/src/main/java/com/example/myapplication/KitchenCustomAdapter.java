package com.example.myapplication;

//import libraries
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

//This is CustomAdapter For Store  Kitchen KitchenDataModelDataType
public class KitchenCustomAdapter extends ArrayAdapter<KitchenDataModel>
{

    //OurContext
    Context mContext;
    // View lookup cache
    private static class ViewHolder
    {
        //TextView to show name of Kitchen in item list
        TextView KitchenNameTextView;
        //ImageView to show image of kitchen in item list
        ImageView KitchenImageImageView;

    }

    //Constructor
    public KitchenCustomAdapter(ArrayList<KitchenDataModel> data, Context context)
    {
        //set kitchen_row_item_layout and KitchenData to our KitchenCustomAdapter
        super(context, R.layout.kitchen_row_item, data);
        //set our context
        this.mContext=context;
    }

    //GetView is a recycle to SetData and ConfirmChange
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the KitchenData item for this position
        KitchenDataModel dataModel = getItem(position);
        // view lookup cache stored in tag
        ViewHolder viewHolder=new ViewHolder();
        //view to link our view with it to set animations
        final View result;
        //flag store if view is null or not
        boolean flag=false;

        // Check if an existing view isn't reused to inflate the view
        if (convertView == null)
        {
            //get inflater to set our KitchenRowItem
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //inflate kitchen_row_item at RowItem of KitchenListView
            convertView = inflater.inflate(R.layout.kitchen_row_item, parent, false);
            //link KitchenNameTextView with our KitchenNameTextView
            viewHolder.KitchenNameTextView =  convertView.findViewById(R.id.KitchenName);
            //link KitchenImageImageView with our KitchenImageImageView
            viewHolder.KitchenImageImageView =  convertView.findViewById(R.id.KitchenImageImageView);
            //link our view with ResultView
            result=convertView;
            //store ViewHolderData at our view
            convertView.setTag(viewHolder);
            //set flag true that our view isn't null
            flag=true;
        }

        // our view is reused
        else
        {
            //get data at our view and set at ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
            //link our view with ResultView
            result=convertView;
        }

        //check if our view isn't reused and ItemPosition<=6 to set animation to only first 7 item
        if(flag&&position<=6)
        {
            //get our animation and load
            Animation animation = AnimationUtils.loadAnimation(mContext, (position % 2 == 0) ? R.anim.left_from_right : R.anim.right_from_left);
            //set our animation to our listview
            result.startAnimation(animation);
        }

        //get KitchenName from our DataModel and set to KitchenNameTextView
        viewHolder.KitchenNameTextView.setText(dataModel.getKitchenNameDataModel());
        //get KitchenImage from our DataModel
        InputStream GetKitchenImageBitmap = new ByteArrayInputStream(dataModel.getKitchenImageDataModel());
        //convert it to bitmap
        Bitmap KitchenImageBitmap = BitmapFactory.decodeStream(GetKitchenImageBitmap);
        // set to KitchenImageImageView
        viewHolder.KitchenImageImageView.setImageBitmap(KitchenImageBitmap);

        // Return the completed view to render on screen
        return convertView;
    }

}