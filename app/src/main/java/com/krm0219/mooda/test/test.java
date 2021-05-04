package com.krm0219.mooda.test;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;

public class test {


    public static String[] uniqueNames(String[] names1, String[] names2) {

        ArrayList<String> names;
        names = (ArrayList<String>) Arrays.asList(names1);

        boolean isSame = false;

        for (int i = 0; i < names2.length; i++) {
            for (int j = 0; j < names.size(); j++) {

                if(names2[i].equalsIgnoreCase(names.get(j))) {

                    isSame = true;
                    break;
                }
            }

            if(!isSame) {
                names.add(names2[i]);
            }
        }


        String[] data = names.toArray(new String[names.size()]);


        for(int i=0; i<data.length; i++) {

            Log.e("krm0219", "data > " + data[i]);
        }


        return data;

      //  throw new UnsupportedOperationException("Waiting to be implemented.");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        String[] names1 = new String[]{"Ava", "Emma", "Olivia"};
        String[] names2 = new String[]{"Olivia", "Sophia", "Emma"};
        System.out.println(String.join(", ", uniqueNames(names1, names2))); // should print Ava, Emma, Olivia, Sophia
    }


}
