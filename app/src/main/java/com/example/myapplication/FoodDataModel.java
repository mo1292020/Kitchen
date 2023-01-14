package com.example.myapplication;

//This is DataModel For food
public class FoodDataModel
{

    //String to store food id
    String FoodIdDataModel;
    //String to store food name
    String FoodNameDataModel;
    //String to store food price
    String FoodPriceDataModel;
    //String to store Kitchen Id of food
    String KitchenFoodIdDataModel;
    //byte to store food byte image
    byte [] FoodImageDataModel;

    //Constructor
    public FoodDataModel(String FoodIdDataModel, String FoodNameDataModel, String FoodPriceDataModel, String KitchenFoodIdDataModel, byte [] FoodImageDataModel)
    {
        //set FoodId
        this.FoodIdDataModel = FoodIdDataModel;
        //set FoodName
        this.FoodNameDataModel = FoodNameDataModel;
        //set FoodPrice
        this.FoodPriceDataModel = FoodPriceDataModel;
        //set KitchenFoodId
        this.KitchenFoodIdDataModel = KitchenFoodIdDataModel;
        //set FoodImage
        this.FoodImageDataModel = FoodImageDataModel;

    }

    //method to return FoodID
    public String getFoodIdDataModel()
    {
        //return FoodId
        return FoodIdDataModel;
    }

    //method to return FoodName
    public String getFoodNameDataModel()
    {
        //return FoodName
        return FoodNameDataModel;
    }

    //method to return FoodPrice
    public String getFoodPriceDataModel()
    {
        //return FoodPrice
        return FoodPriceDataModel;
    }

    //method to return KitchenFoodId
    public String getKitchenFoodIdDataModel()
    {
        //return KitchenFoodId
        return KitchenFoodIdDataModel;
    }

    //method to return FoodImage
    public byte[] getFoodImageDataModel()
    {
        //return KitchenImage
        return FoodImageDataModel;
    }



}
