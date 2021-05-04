package com.krm0219.library;

import java.util.ArrayList;
import java.util.Collections;

public class Program_1_4 {

    /*
배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째에 있는 수를 구하려 합니다.

예를 들어 array가 [1, 5, 2, 6, 3, 7, 4], i = 2, j = 5, k = 3이라면

array의 2번째부터 5번째까지 자르면 [5, 2, 6, 3]입니다.
1에서 나온 배열을 정렬하면 [2, 3, 5, 6]입니다.
2에서 나온 배열의 3번째 숫자는 5입니다.
배열 array, [i, j, k]를 원소로 가진 2차원 배열 commands가 매개변수로 주어질 때, commands의 모든 원소에 대해 앞서 설명한 연산을 적용했을 때 나온 결과를 배열에 담아 return 하도록 solution 함수를 작성해주세요.
      */
    public static void main(String[] args) {

        solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}});
    }

    public static int[] solution(int[] array, int[][] commands) {

        int[] answer = new int[commands.length];

        for (int i = 0; i < commands.length; i++) {

            //  int[] split = new int[commands[i][1] - commands[i][0] + 1];
            ArrayList<Integer> split = new ArrayList<>();

            for (int j = commands[i][0] - 1; j < commands[i][1]; j++) {

                split.add(array[j]);
            }

            Collections.sort(split);
            System.out.println(split.toString());
            // System.out.println(commands[i][2]);

            System.out.println(split.get(commands[i][2] - 1));
            answer[i] = split.get(commands[i][2] - 1);
            //   System.out.println(split.toString());
        }


        return answer;
    }
}