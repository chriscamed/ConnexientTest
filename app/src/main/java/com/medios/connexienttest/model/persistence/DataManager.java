package com.medios.connexienttest.model.persistence;

import com.medios.connexienttest.model.application.App;
import com.medios.connexienttest.model.objectsmodel.UserObjectModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Camilo on 7/15/17.
 */

public class DataManager {

    private static DataManager instance = null;

    /**
     * Gets only one instance of DataManger
     * @return
     */
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    /**
     * Saves the data in the local storage
     * @param usersData The object that is going to be stored
     */
    public void saveDataIntoLocalStorage(ArrayList<UserObjectModel> usersData) {
        String filename = "users.obj";
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(App.get().getFilesDir(),"") + File.separator + filename));
            out.writeObject(usersData);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the data from local storage
     * @return A list of users
     */
    public ArrayList<UserObjectModel> readDataFromLocalStorage(){
        ObjectInputStream input;
        String filename = "users.obj";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(App.get().getFilesDir(),"")+File.separator+filename)));
            @SuppressWarnings("unchecked")
            ArrayList<UserObjectModel> userList = (ArrayList) input.readObject();
            input.close();
            return userList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }
}
