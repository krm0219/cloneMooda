package com.krm0219.library;

import java.util.PriorityQueue;

public class Program_2_1 {

    /*
    트럭 여러 대가 강을 가로지르는 일 차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 트럭은 1초에 1만큼 움직이며, 다리 길이는 bridge_length이고 다리는 무게 weight까지 견딥니다.
    ※ 트럭이 다리에 완전히 오르지 않은 경우, 이 트럭의 무게는 고려하지 않습니다.

    예를 들어, 길이가 2이고 10kg 무게를 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.
    * */
    public static void main(String[] args) {

        solution(100, 100, new int[]{10});
    }

    public static int solution(int bridge_length, int weight, int[] truck_weights) {

        PriorityQueue<Integer> bridge = new PriorityQueue<>();

        int answer = 0;
        int bridgeWeight = 0;

        for(int i=0; i<truck_weights.length; i++) {

            while(true) {

                if(bridge.isEmpty()) {

                    bridge.add(truck_weights[i]);
                    answer++;
                    bridgeWeight = truck_weights[i];
                    break;
                } else if(bridge.size() == bridge_length) {

                }


            }
        }




        //   System.out.println(answer);
        return answer;
    }
}