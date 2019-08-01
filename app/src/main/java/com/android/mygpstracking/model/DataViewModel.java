package com.android.mygpstracking.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DataViewModel extends ViewModel {

    DataRepository dataRepository;
    LiveData<List<UserData>> userDataLiveData;

    DataViewModel(){
        if (userDataLiveData !=null){

            return;
        }

        dataRepository=DataRepository.getInstance();
        userDataLiveData=dataRepository.getAllData();
        //userDataLiveData=dataRepository.getLat();

    }

    public LiveData<List<UserData>>getUserData(){
        Log.d("tag", "getUserData: get lang and lat" + userDataLiveData.getValue());

        return userDataLiveData;
    }


    void doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
        //   User user=new User();

        // user.setUser("raja");

        //  Log.d("tag", "doAction:  method started"+user.getUser() )

    }
}
