package com.nhance.technician.networking.downloadmanager.Utils.helper;

/**
 * Created by Rahul on 6/8/2017.
 */
public class SqlString {

    public static String Int(int number){
        return "'"+number+"'";
    }

    public static String String(String name){
        return "'"+name+"'";
    }
}
