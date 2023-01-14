package com.example.myapplication;

/*

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

*/

//import libraries
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

//This is RecycleViewAdapter For Store  Kitchen KitchenDataModelDataType
public class FoodCustomAdapter extends RecyclerView.Adapter<FoodCustomAdapter.ViewHolder>
{

    //Foods Data
    ArrayList<FoodDataModel> foodData;
    //OurContext
    Context context;

    //Constructor
    public FoodCustomAdapter(Context context, ArrayList<FoodDataModel> foodData)
    {
        //Store FoodData
        this.foodData =foodData;
        //Store context
        this.context=context;
    }

    //method to inflate RowFood XML in viewHolder call automatic when RecycleView need ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup FoodListView, int viewType)
    {
        //FoodListView as a parent
        LayoutInflater layoutInflater = LayoutInflater.from(FoodListView.getContext());
        //inflate food_row_item
        View FoodListItem= layoutInflater.inflate(R.layout.food_row_item, FoodListView, false);
        //link to viewHolder to set our data dynamic
        ViewHolder viewHolder = new ViewHolder(FoodListItem);
        //return viewHolder
        return viewHolder;
    }

    //RecycleView Adapter to display data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //set FoodName from FoodData by position
        holder.FoodNameTextView.setText(foodData.get(position).getFoodNameDataModel());
        //set FoodPrice from FoodData by position
        holder.FoodPriceTextView.setText(foodData.get(position).getFoodPriceDataModel());
        //get FoodImage as a byte from FoodData to convert to Bitmap
        InputStream GetFoodImageBitmap = new ByteArrayInputStream(foodData.get(position).getFoodImageDataModel());
        //convert it to bitmap
        Bitmap FoodImageBitmap = BitmapFactory.decodeStream(GetFoodImageBitmap);
        //set FoodImage as a Bitmap to FoodImageImageView
        holder.FoodImageImageView.setImageBitmap(FoodImageBitmap);

        //set onClickListener to every item as a RelativeLayout to only display info about it
        holder.FoodRelativeLayout.setOnClickListener(view ->
        {
            //get foodName from FoodAdapter
            String name = foodData.get(position).getFoodNameDataModel();
            //get foodPrice from FoodAdapter
            String price= foodData.get(position).getFoodPriceDataModel();
            //show them in a toast
            Toast.makeText(context,"Food "+name+" with price "+price+" ordered", Toast.LENGTH_SHORT).show();
        });

        //set onLongClickListener to every item as a RelativeLayout to Delete or Edit Food
        holder.FoodRelativeLayout.setOnLongClickListener(view ->
        {
            //Create FoodPopupMenu
            PopupMenu foodPopupMenu = new PopupMenu(context, view);
            //add our UI to FoodPopupMenu
            foodPopupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, foodPopupMenu.getMenu());
            //open connection from KitchenDatabaseHandler
            KitchenDatabaseHandler database = new KitchenDatabaseHandler(context);
            //add click listener for Edit or Delete food in FoodPopupMenu
            foodPopupMenu.setOnMenuItemClickListener(item -> {
                //if choose to delete food
                if(item.getTitle().equals("Delete"))
                {
                    //delete food from database
                    database.DeleteFood(foodData.get(position).getFoodIdDataModel());
                    //delete food from FoodData
                    foodData.remove(foodData.get(position));
                    //notify Change data to adapter to recycle
                    this.notifyDataSetChanged();
                }
                //if choose to edit kitchen
                if(item.getTitle().equals("Edit"))
                {
                    //intent for edit food
                    Intent editFoodIntent = new Intent(context,EditFood.class);
                    //send FoodId to activity edit food
                    editFoodIntent.putExtra("foodId", foodData.get(position).getFoodIdDataModel());
                    //send FoodName to activity edit food
                    editFoodIntent.putExtra("foodName", foodData.get(position).getFoodNameDataModel());
                    //send FoodPrice to activity edit food
                    editFoodIntent.putExtra("foodPrice", foodData.get(position).getFoodPriceDataModel());
                    //send FoodKitchenId to activity edit food
                    editFoodIntent.putExtra("kitchenId", foodData.get(position).getKitchenFoodIdDataModel());
                    //send FoodImage to activity edit food
                    editFoodIntent.putExtra("foodImage", foodData.get(position).getFoodImageDataModel());
                    //this for start activity from outside
                    editFoodIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //start edit food activity
                    context.startActivity(editFoodIntent);
                }

                return true;
            });
            //finally show FoodPopupMenu
            foodPopupMenu.show();
            return true;
        });

        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView,position);

    }

    //method return count of food
    @Override
    public int getItemCount()
    {
        //return Kitchens count
        return foodData.size();
    }

    //ViewHolder class to link FoodRow xml to dynamic holder to set data at
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //FoodImageImageView to set FoodImage
        public ImageView FoodImageImageView;
        //FoodNameTextView to set FoodName
        public TextView FoodNameTextView;
        //FoodPriceTextView to set FoodPrice
        public TextView FoodPriceTextView;
        //FoodRelativeLayout to set foodRow as RelativeLayout to set our action at
        public RelativeLayout FoodRelativeLayout;

        //Constructor
        public ViewHolder(View FoodListItem)
        {
            //extend from RecycleView.ViewHolder take Xml of RowFood
            super(FoodListItem);
            //link with FoodImageImageView in xml
            this.FoodImageImageView =  FoodListItem.findViewById(R.id.FoodImageImageView);
            //link with FoodNameTextView in xml
            this.FoodNameTextView =  FoodListItem.findViewById(R.id.FoodNameTextView);
            //link with FoodPriceTextView in xml
            this.FoodPriceTextView = FoodListItem.findViewById(R.id.FoodPriceTextView);
            //link with FoodRelativeLayout in xml
            this.FoodRelativeLayout = FoodListItem.findViewById(R.id.FoodRowItemRelativeLayout);
        }
    }

    //Here is the key method to apply the animation
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if(position<5)
        {
            //get our animation and load
            Animation animation = AnimationUtils.loadAnimation(context, (position % 2 == 0) ? R.anim.left_from_right : R.anim.right_from_left);
            //set our animation to our listview
            viewToAnimate.startAnimation(animation);
        }

    }
}
