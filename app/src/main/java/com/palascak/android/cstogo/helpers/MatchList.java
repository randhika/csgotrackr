package com.palascak.android.cstogo.helpers;

import com.palascak.android.cstogo.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * - Yuro - 24.3.2015.
 */
public class MatchList {

    public ArrayList<Match> matchList;

    private MatchList(){
        FileInputStream inStream = null;

        File file = new File(MyApplication.getAppContext().getFilesDir(), "match_list.dat");
        if(!file.exists()){
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                FileOutputStream outStream = new FileOutputStream(file);
                ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
                objectOutStream.writeInt(0); // Save size first
                objectOutStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            inStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream objectInStream;
        try {
            objectInStream = new ObjectInputStream(inStream);
            int count = objectInStream.readInt(); // Get the number
            matchList = new ArrayList<>();
            for (int c=0; c < count; c++)
                matchList.add((Match) objectInStream.readObject());
            objectInStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static MatchList instance;

    public static MatchList getInstance(){
        if (instance == null) instance = new MatchList();
        return instance;
    }
}
