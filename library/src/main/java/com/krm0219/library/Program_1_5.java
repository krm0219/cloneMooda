package com.krm0219.library;

import java.util.ArrayList;

public class Program_1_5 {

    /*
배열 arr가 주어집니다. 배열 arr의 각 원소는 숫자 0부터 9까지로 이루어져 있습니다. 이때, 배열 arr에서 연속적으로 나타나는 숫자는 하나만 남기고 전부 제거하려고 합니다. 단, 제거된 후 남은 수들을 반환할 때는 배열 arr의 원소들의 순서를 유지해야 합니다. 예를 들면,

arr = [1, 1, 3, 3, 0, 1, 1] 이면 [1, 3, 0, 1] 을 return 합니다.
arr = [4, 4, 4, 3, 3] 이면 [4, 3] 을 return 합니다.
배열 arr에서 연속적으로 나타나는 숫자는 제거하고 남은 수들을 return 하는 solution 함수를 완성해 주세요.
      */
    public static void main(String[] args) {

        solution(new int[]{1, 1, 3, 3, 0, 1, 1});
    }

    public static int[] solution(int[] arr) {

        ArrayList<Integer> list = new ArrayList<>();

        list.add(arr[0]);
        int pos = 0;

        for (int i = 1; i < arr.length; i++) {

            int num = arr[i];

            if (list.get(pos) != num) {

                list.add(num);
                pos++;
            }
        }


        int[] answer = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {

            answer[i] = list.get(i);
        }

        return answer;
    }
}