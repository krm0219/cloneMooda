package com.krm0219.library;

import java.util.HashMap;
import java.util.Iterator;

public class Program_1_2 {

    /*
수많은 마라톤 선수들이 마라톤에 참여하였습니다. 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.

마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때, 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.

제한사항
마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
completion의 길이는 participant의 길이보다 1 작습니다.
참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
참가자 중에는 동명이인이 있을 수 있습니다.

입출력 예
participant	                                        completion	                                return
["leo", "kiki", "eden"]	                            ["eden", "kiki"]	                        "leo"
["marina", "josipa", "nikola", "vinko", "filipa"]	["josipa", "filipa", "marina", "nikola"]	"vinko"
["mislav", "stanko", "mislav", "ana"]	            ["stanko", "ana", "mislav"]	                "mislav"
      */
    public static void main(String[] args) {

        solution(new String[]{"marina", "josipa", "nikola", "vinko", "filipa"}, new String[]{"josipa", "filipa", "marina", "nikola"});
    }

    public static String solution(String[] participant, String[] completion) {

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < participant.length; i++) {

            int count = 1;

            if (hashMap.containsKey(participant[i])) {

                count = hashMap.get(participant[i]);
                count++;

            }

            hashMap.put(participant[i], count);
        }


        for (int i = 0; i < completion.length; i++) {

            String key = completion[i];

            if (hashMap.containsKey(key)) {

                int value = hashMap.get(key);
                value -= 1;

                hashMap.put(key, value);
            }
        }


        String answer = "";
        Iterator<String> iterator = hashMap.keySet().iterator();

        while (iterator.hasNext()) {

            String key = iterator.next();
            int value = hashMap.get(key);

            if (value == 1) {

                answer = key;
                break;
            }
        }


     //   System.out.println(answer);
        return answer;
    }
}