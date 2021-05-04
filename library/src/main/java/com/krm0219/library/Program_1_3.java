package com.krm0219.library;

import java.util.ArrayList;

public class Program_1_3 {

    /*
수포자는 수학을 포기한 사람의 준말입니다. 수포자 삼인방은 모의고사에 수학 문제를 전부 찍으려 합니다. 수포자는 1번 문제부터 마지막 문제까지 다음과 같이 찍습니다.

1번 수포자가 찍는 방식: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
2번 수포자가 찍는 방식: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
3번 수포자가 찍는 방식: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...

1번 문제부터 마지막 문제까지의 정답이 순서대로 들은 배열 answers가 주어졌을 때, 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하도록 solution 함수를 작성해주세요.
      */
    public static void main(String[] args) {

        solution(new int[]{1, 3, 2, 4, 2});
    }

    public static int[] solution(int[] answers) {

        ArrayList<Integer> result = new ArrayList<>();

        int[] per1 = new int[]{1, 2, 3, 4, 5};
        int[] per2 = new int[]{2, 1, 2, 3, 2, 4, 2, 5};
        int[] per3 = new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;

        for(int i=0; i<answers.length; i++) {

            if(answers[i] == per1[i%per1.length])
                sum1++;

            if(answers[i] == per2[i%per2.length])
                sum2++;

            if(answers[i] == per3[i%per3.length])
                sum3++;
        }

        int max = Math.max(sum1, sum2);
        int max1 = Math.max(max, sum3);

        if(sum1 == max1)
            result.add(1);
        if(sum2 == max1)
            result.add(2);
        if(sum3 == max1)
            result.add(3);


        int[] answer = new int[result.size()];

        for(int i=0; i<result.size(); i++) {

            answer[i] = result.get(i);
        }

        return answer;
    }
}