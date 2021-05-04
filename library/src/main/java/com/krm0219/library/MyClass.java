package com.krm0219.library;

import java.util.HashMap;
import java.util.Iterator;

public class MyClass {

    public static void main(String[] args) {

        //     System.out.println();

        solution("ababcdcdababcdcd");
    }

    public static int solution(String s) {

    //    System.out.println(s);


        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("test1", "I'm Test1");
        hashMap.put("test2", "I'm Test2");
        hashMap.put("test3", "I'm Test3");


        Iterator<String> keys = hashMap.keySet().iterator();

        while(keys.hasNext()) {

            String key = keys.next();
            String value = hashMap.get(key);
            System.out.println(key + " / " + value);
        }

        System.out.println("\n");

        if(hashMap.containsKey("test1")) {

            hashMap.put("test1", "Change Test1");
        }


        for(String key : hashMap.keySet()) {

            String value = hashMap.get(key);
            System.out.println(key + " / " + value);
        }


        int answer = 0;
        return answer;
    }
}