package com.example.myapplication;

import android.net.Uri;

//This is DataModel For Kitchen
public class KitchenDataModel
{

    //String to store kitchen id
    String KitchenIdDataModel;
    //String to store kitchen name
    String KitchenNameDataModel;
    //String to store kitchen password
    String KitchenPasswordDataModel;
    //Uri to store kitchen uri image
    byte [] KitchenImageDataModel;


    //Constructor
    public KitchenDataModel(String KitchenIdDataModel, String KitchenNameDataModel, String KitchenPasswordDataModel, byte [] KitchenImageDataModel)
    {
        //set KitchenId
        this.KitchenIdDataModel = KitchenIdDataModel;
        //set KitchenName
        this.KitchenNameDataModel = KitchenNameDataModel;
        //set KitchenPassword
        this.KitchenPasswordDataModel = KitchenPasswordDataModel;
        //set KitchenImage
        this.KitchenImageDataModel = KitchenImageDataModel;

    }

    //method to return KitchenId
    public String getKitchenIdDataModel()
    {
        //return KitchenId;
        return KitchenIdDataModel;
    }

    //method to return KitchenName
    public String getKitchenNameDataModel()
    {
        //return KitchenName
        return KitchenNameDataModel;
    }

    //method to return KitchenPassword
    public String getKitchenPasswordDataModel()
    {
        //return KitchenPassword
        return KitchenPasswordDataModel;
    }

    //method to return KitchenImage
    public byte[] getKitchenImageDataModel()
    {
        //return KitchenImage
        return KitchenImageDataModel;
    }

}
