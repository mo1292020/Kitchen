package com.example.myapplication;

//import libraries
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

//This is CustomAdapter For Store  Food FoodDataModelDataType
public class FoodCustomAdapter extends ArrayAdapter<FoodDataModel>
{

    //OurContext
    Context mContext;

    // View lookup cache
    private static class ViewHolder
    {
        //TextView to show name of food in item list
        TextView FoodNameTextView;
        //TextView to show price of food in item list
        TextView FoodPriceTextView;
    }

    //Constructor
    public FoodCustomAdapter(ArrayList<FoodDataModel> data, Context context)
    {
        //set food_row_item_layout and FoodData to our FoodCustomAdapter
        super(context, R.layout.food_row_item, data);
        //set our context
        this.mContext=context;
    }

    //GetView is a recycle to SetData and ConfirmChange
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the FoodData item for this position
        FoodDataModel dataModel = getItem(position);
        // view lookup cache stored in tag
        ViewHolder viewHolder=new ViewHolder();
        //view to link our view with it to set animations
        final View result;
        //flag store if view is null or not
        boolean flag=false;

        // Check if an existing view isn't reused to inflate the view
        if (convertView == null)
        {
            //get inflater to set our FoodRowItem
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //inflate food_row_item at RowItem of FoodListView
            convertView = inflater.inflate(R.layout.food_row_item, parent, false);
            //link FoodNameTextView with our FoodNameTextView
            viewHolder.FoodNameTextView = convertView.findViewById(R.id.Fname);
            //link FoodPriceTextView with our FoodPriceTextView
            viewHolder.FoodPriceTextView = convertView.findViewById(R.id.Fprice);
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

        //get FoodName from our DataModel and set to FoodNameTextView
        viewHolder.FoodNameTextView.setText(dataModel.getFoodNameDataModel());
        //get FoodPrice from our DataModel and set to FoodPriceTextView
        viewHolder.FoodPriceTextView.setText(dataModel.getFoodPriceDataModel());

        // Return the completed view to render on screen
        return convertView;
    }

}