package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;

public class Validate {

    public static boolean checkKitchen(Context kitchenContext, String kitchenName){

        //KitchenDatabaseHandler  class manage our database
        KitchenDatabaseHandler database = new KitchenDatabaseHandler(kitchenContext);
        //cursor indicates the kitchen in database Table_Kitchen
        Cursor kitchenCursor=database.IndicatorKitchenTable();

        //Fetch the data from Table_Kitchen
        if(kitchenCursor.moveToFirst())
        {
            do
            {
                //KitchenName for kitchen place in colum 1 in Table_Kitchen
                String getKitchenName= kitchenCursor.getString(1);
                if(replaceAll(getKitchenName).equals(replaceAll(kitchenName)))
                    return false;
            }
            while(kitchenCursor.moveToNext());
        }

        return true;
    }

    //method to make string only lower case
    public static String replaceAll(String string){
        String result="";
        for(int h=0;h<string.length();h++){
            Character ch=string.charAt(h);
            ch=Character.toLowerCase(ch);
            if(Character.isLowerCase(ch))
                result+=ch;
        }
        return result;
    }


}
