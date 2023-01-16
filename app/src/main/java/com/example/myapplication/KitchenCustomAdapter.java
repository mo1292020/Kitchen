package com.example.myapplication;

/*
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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

//This is RecycleViewAdapter For Store  Kitchen KitchenDataModelDataType
public class KitchenCustomAdapter extends RecyclerView.Adapter<KitchenCustomAdapter.ViewHolder>
{

    //Kitchens Data
    ArrayList<KitchenDataModel> kitchenData;
    //ArrayList to check every row has animation
    ArrayList<Boolean> hasAnimation=new ArrayList<>();
    //OurContext
    Context context;

    //Constructor
    public KitchenCustomAdapter(Context context, ArrayList<KitchenDataModel> kitchenData)
    {
        //Store KitchenData
        this.kitchenData=kitchenData;
        //Store context
        this.context=context;
    }

    //method to inflate RowKitchen XML in viewHolder call automatic when RecycleView need ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup kitchenListView, int viewType)
    {
        //kitchenListView as a parent
        LayoutInflater layoutInflater = LayoutInflater.from(kitchenListView.getContext());
        //inflate kitchen_row_item
        View kitchenListItem= layoutInflater.inflate(R.layout.kitchen_row_item, kitchenListView, false);
        //link to viewHolder to set our data dynamic
        ViewHolder viewHolder = new ViewHolder(kitchenListItem);
        //return viewHolder
        return viewHolder;
    }

    //RecycleView Adapter to display data
    @Override
    public void onBindViewHolder(@NonNull KitchenCustomAdapter.ViewHolder holder, int position)
    {
        //set KitchenName from KitchenData by position
        holder.KitchenNameTextView.setText(kitchenData.get(position).getKitchenNameDataModel());

        //get KitchenImage as a byte from KitchenData to convert to Bitmap
        InputStream GetKitchenImageBitmap = new ByteArrayInputStream(kitchenData.get(position).getKitchenImageDataModel());
        //convert it to bitmap
        Bitmap KitchenImageBitmap = BitmapFactory.decodeStream(GetKitchenImageBitmap);
        //set KitchenImage as a Bitmap to KitchenImageImageView
        holder.KitchenImageImageView.setImageBitmap(KitchenImageBitmap);
        //setBackground to every item based on theme
        holder.itemView.setBackgroundResource((context.getResources().getString(R.string.mode).equals("Day"))?R.drawable.list_view_shape_light:R.drawable.list_view_shape_dark);
        //set onClickListener to every item as a RelativeLayout to open their FoodList
        holder.KitchenRelativeLayout.setOnClickListener(view ->
        {
            //intent Food activity
            Intent foodIntent = new Intent(context, Food.class);
            //send KitchenId as a message to Activity Food
            foodIntent.putExtra("kitchenId", kitchenData.get(position).getKitchenIdDataModel());
            //send KitchenName as a message to Activity Food
            foodIntent.putExtra("kitchenName", kitchenData.get(position).getKitchenNameDataModel());
            //this for start activity from outside
            foodIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start Food Activity
            context.startActivity(foodIntent);
        });

        //set onLongClickListener to every item as a RelativeLayout to Delete or Edit Kitchen
        holder.KitchenRelativeLayout.setOnLongClickListener(view ->
        {
            //Create KitchenPopupMenu
            PopupMenu kitchenPopupMenu = new PopupMenu(context, view);
            //add our UI to KitchenPopupMenu
            kitchenPopupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, kitchenPopupMenu.getMenu());
            //open connection from KitchenDatabaseHandler
            KitchenDatabaseHandler database = new KitchenDatabaseHandler(context);
            //add click listener for Edit or Delete Kitchen in KitchenPopupMenu
            kitchenPopupMenu.setOnMenuItemClickListener(item -> {
                //if choose to delete kitchen
                if (item.getTitle().equals("Delete"))
                {
                    //delete kitchen from database
                    database.DeleteKitchen(kitchenData.get(position).getKitchenIdDataModel());
                    //delete kitchen from KitchenData
                    kitchenData.remove(kitchenData.get(position));
                    //notify Change data to adapter to recycle
                    this.notifyDataSetChanged();
                }
                //if choose to edit kitchen
                if (item.getTitle().equals("Edit"))
                {
                    //intent for edit kitchen
                    Intent editKitchenIntent = new Intent(context, EditKitchen.class);
                    //send KitchenId to activity edit kitchen
                    editKitchenIntent.putExtra("kitchenId", kitchenData.get(position).getKitchenIdDataModel());
                    //send KitchenName to activity edit kitchen
                    editKitchenIntent.putExtra("kitchenName", kitchenData.get(position).getKitchenNameDataModel());
                    //send KitchenPassword to activity edit kitchen
                    editKitchenIntent.putExtra("kitchenPassword", kitchenData.get(position).getKitchenPasswordDataModel());
                    //send KitchenImage to activity edit kitchen
                    editKitchenIntent.putExtra("kitchenImage", kitchenData.get(position).getKitchenImageDataModel());
                    //this for start activity from outside
                    editKitchenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //start edit food activity
                    context.startActivity(editKitchenIntent);
                }
                return true;
            });
            //finally show kitchenPopupMenu
            kitchenPopupMenu.show();
            return true;
        });

        //mark as a not hasAnimation
        hasAnimation.add(false);
        //check if it hasAnimation
        if(!hasAnimation.get(position)&&position<2)
        {
            // Here you apply the animation when the view is bound
            setAnimation(holder.itemView, position);
            //mark as hasAnimation
            hasAnimation.set(position,true);
        }
    }

    //method return count of kitchen
    @Override
    public int getItemCount()
    {
        //return Kitchens count
        return kitchenData.size();
    }

    //ViewHolder class to link KitchenRow xml to dynamic holder to set data at
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //KitchenImageImageView to set kitchenImage
        public ImageView KitchenImageImageView;
        //KitchenNameTextView to set kitchenName
        public TextView KitchenNameTextView;
        //KitchenNameTextView to set kitchenRow as RelativeLayout to set our action at
        public RelativeLayout KitchenRelativeLayout;

        //Constructor
        public ViewHolder(View kitchenListItem)
        {
            //extend from RecycleView.ViewHolder take Xml of RowKitchen
            super(kitchenListItem);
            //link with KitchenImageImageView in xml
            this.KitchenImageImageView =  kitchenListItem.findViewById(R.id.KitchenImageImageView);
            //link with KitchenNameTextView in xml
            this.KitchenNameTextView =  kitchenListItem.findViewById(R.id.KitchenNameTextView);
            //link with KitchenRelativeLayout in xml
            this.KitchenRelativeLayout = kitchenListItem.findViewById(R.id.KitchenRowItemRelativeLayout);
        }
    }


    //Here is the key method to apply the animation
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

            //get our animation and load
            Animation animation = AnimationUtils.loadAnimation(context, (position % 2 == 0) ? R.anim.left_from_right : R.anim.right_from_left);
            //set our animation to our listview
            viewToAnimate.startAnimation(animation);

    }
}
