package com.krm0219.library;

import java.util.Stack;

public class MyClass {

    public static void main(String[] args) {

        System.out.print(solution1("2(hi)") + "\n");
        System.out.print(solution1("10(p)") + "\n");
        System.out.print(solution1("2(3(hi)co)") + "\n");
        System.out.print(solution1("2(2(hi)2(co))x2(bo)") + "\n\n");

        System.out.print(solution("2(hi)") + "\n");
        System.out.print(solution("10(p)") + "\n");
        System.out.print(solution("2(3(hi)co)") + "\n");
        System.out.print(solution("2(2(hi)2(co))x2(bo)") + "\n");
    }


//    private static String solution(String input) {
//        Stack<Character> stack2 = new Stack<>();
//
//        StringBuilder result = new StringBuilder();
//
//        for (char c : input.trim().toCharArray()) {
//            if (Character.isDigit(c)) {
//                stack2.push (c);
//            } else if (c == '(') {
//                stack2.push (c);
//            } else if (c == ')') {
//                StringBuilder str = new StringBuilder ();
//                while (!stack2.isEmpty() && stack2.peek() != '(') {
//                    str.append (stack2.pop());
//                }
//                str = str.reverse();
//                stack2.pop();
//
//                int num = Integer.parseInt(stack2.pop() + "");
//                if (str.length () == 0) {
//                    str = new StringBuilder(result);
//                    result.setLength (0);
//                }
//                for (int i = 0; i < num; i++) {
//                    result.append (str);
//                }
//                //result.append (String.valueOf (str).repeat (Math.max (0, num)));
//            } else {
//                if (stack2.empty()) {
//                    result.append(c);
//                } else {
//                    stack2.push (c);
//                }
//            }
//        }
//
//        return result.toString ();
//    }
////
//


    private static String solution(String input) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (char c : input.trim().toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                StringBuilder str = new StringBuilder();

                while (!stack.isEmpty() && stack.peek() != '(') {
                    if (stack.peek() == ')') {
                        stack.pop();
                    } else {
                        str.append(stack.pop());
                    }
                }

                str = str.reverse();

                if (str.length() == 0) {
                    str = new StringBuilder(result);
                    result.setLength(0);
                }

                stack.pop();

                StringBuilder numStr = new StringBuilder();

                while (!stack.isEmpty() && Character.isDigit(stack.peek())) {
                    numStr.append(stack.pop().toString());
                }
                numStr = numStr.reverse();
                int num = Integer.parseInt(numStr.toString());

                for (int i = 0; i < num; i++) {
                    result.append(str);
                }

                //  result.append (String.valueOf (str).repeat (Math.max (0, num)));
                stack.push(c);
            } else if (!stack.isEmpty() && stack.peek() == ')') {
                result.append(c);
            } else {
                stack.push(c);
            }
        }

        return result.toString();
    }


    public static String solution1(String compressed) {

        String str = compressed;

        while (str.contains("(")) {

            str = compress(str);
            if (!str.contains("(")) {
                break;
            }
        }

        return str;
    }


    public static String compress(String string) {

        StringBuilder sb = new StringBuilder();
        StringBuilder num = new StringBuilder();

        StringBuilder str = new StringBuilder();
        int count = 0;

        for (char c : string.toCharArray()) {

            if (c >= 48 && c <= 57) {    // 숫자

                if (count == 0) {

                    num.append(c);
                    if (sb.length() != 0) {
                        str.append(sb.toString());
                    }
                } else {

                    sb.append(c);
                }
            } else if (c == '(') {

                if (count == 0) {

                    sb = new StringBuilder();
                } else {

                    sb.append(c);
                }
                count++;
            } else if (c == ')') {

                count--;

                if (count == 0) {

                    for (int i = 0; i < Integer.parseInt(num.toString()); i++) {

                        str.append(sb.toString());
                    }

                    num = new StringBuilder();
                    sb = new StringBuilder();
                } else {

                    sb.append(c);
                }
            } else {

                sb.append(c);
                //  System.out.print("sb : " + sb.toString() + "\n");
            }
        }

        if (sb.length() != 0) {
            str.append(sb.toString());
        }

        return str.toString();
    }


}