package com.example.android.cstogo.helpers;

import android.util.Log;

import com.example.android.cstogo.MyApplication;

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
        Log.i("TAG", "MatchList 0");

        File file = new File(MyApplication.getAppContext().getFilesDir(), "match_list.dat");
        if(!file.exists()){
            Log.i("TAG", "MatchList 0.5");
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
            Log.i("TAG", "MatchList 1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("TAG", "MatchList 2");
        }

        ObjectInputStream objectInStream;
        Log.i("TAG", "MatchList 3");
        try {
            Log.i("TAG", "MatchList 4");
            objectInStream = new ObjectInputStream(inStream);
            Log.i("TAG", "MatchList 5");
            int count = objectInStream.readInt(); // Get the number
            Log.i("TAG", "MatchList 6");
            matchList = new ArrayList<>();
            Log.i("TAG", "MatchList 7");
            for (int c=0; c < count; c++)
                matchList.add((Match) objectInStream.readObject());
            Log.i("TAG", "MatchList 8");
            objectInStream.close();
            Log.i("TAG", "MatchList 9");
        } catch (IOException | ClassNotFoundException e) {
            Log.i("TAG", "WTF");
            e.printStackTrace();
        }
    }

    private static MatchList instance;

    public static MatchList getInstance(){
        if (instance == null) instance = new MatchList();
        return instance;
    }
}
