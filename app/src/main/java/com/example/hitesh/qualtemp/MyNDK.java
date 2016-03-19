package com.example.hitesh.qualtemp;

/**
 * Created by hitesh on 3/18/16.
 */

//native class interface
public class MyNDK {

    static
    {
        System.loadLibrary("MyLibrary");
    }

    //native method to convert temperature from Cel to Far
    public native int getMyString(int a);


}
