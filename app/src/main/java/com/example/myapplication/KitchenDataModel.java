package com.example.myapplication;

//This is DataModel For Kitchen
public class KitchenDataModel
{

    //String to store kitchen id
    String KitchenIdDataModel;
    //String to store kitchen name
    String KitchenNameDataModel;
    //String to store kitchen password
    String KitchenPasswordDataModel;

    //Constructor
    public KitchenDataModel(String KitchenIdDataModel, String KitchenNameDataModel, String KitchenPasswordDataModel)
    {
        //set KitchenId
        this.KitchenIdDataModel = KitchenIdDataModel;
        //set KitchenName
        this.KitchenNameDataModel = KitchenNameDataModel;
        //set KitchenPassword
        this.KitchenPasswordDataModel = KitchenPasswordDataModel;
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

}
