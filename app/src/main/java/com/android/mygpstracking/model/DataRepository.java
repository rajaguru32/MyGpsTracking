package com.android.mygpstracking.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    public static DataRepository instance;
   private ArrayList<UserData> dataset= new ArrayList<>();
    double lat;

    UserData userData;

    //making singleton this class
    public static DataRepository getInstance(){
        if(instance==null){
            instance=new DataRepository();
        }
        return instance;
    }

    public LiveData<List<UserData>> getAllData(){

        MutableLiveData<List<UserData>> data =new MutableLiveData<>();
        userData=new UserData("rajaguru32@gmail.com", "123456",
                80.0000, 12.00000);
        data.setValue(dataset);
        return data;
    }
//
//   public LiveData<UserData> getLat(String long){
//
//        MutableLiveData<LiveData<UserData>> data =new MutableLiveData<>();
//        userData.getLatitude();
//        data.setValue();
//
//        return long;
//    }
}
