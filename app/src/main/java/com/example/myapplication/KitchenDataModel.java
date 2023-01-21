package com.example.myapplication;

import android.net.Uri;

import java.io.Serializable;

//This is DataModel For Kitchen
public class KitchenDataModel implements Serializable
{

    //String to store kitchen id
    private String KitchenIdDataModel;
    //String to store kitchen name
    private String KitchenNameDataModel;
    //String to store kitchen password
    private String KitchenPasswordDataModel;
    //byte to store kitchen byte image
    private byte [] KitchenImageDataModel;


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
