package com.gb.calculator;

import java.util.Stack;

public class Calculator {
    public String stringToRPN(String expression) {
        String current = "";
        Stack<Character> charStack = new Stack<>();
        int priority;
        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(expression.charAt(i));
            if (priority == 0) {
                current += expression.charAt(i);
            }
            if (priority == 1) {
                charStack.push(expression.charAt(i));
            }
            if (priority > 1) {
                current += ' ';
                while (!charStack.empty()) {
                    if (getPriority(charStack.peek()) >= priority) {
                        current += charStack.pop();
                    } else break;
                }
                charStack.push(expression.charAt(i));
            }
            if (priority == -1) {
                current += ' ';
                while (getPriority(charStack.peek()) != 1) {
                    current += charStack.pop();
                }
                charStack.pop();
            }
        }
        while (!charStack.empty()) {
            current += charStack.pop();
        }


        return current;
    }

    public double result(String expression) {
        String rpn = stringToRPN(expression);
        String operand = new String();
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while ((rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0)) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand));
                operand = new String();
            } if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();
                if (rpn.charAt(i) == '+') {
                    stack.push(b + a);
                } else if (rpn.charAt(i) == '-') {
                    stack.push(b - a);
                } else if (rpn.charAt(i) == '*') {
                    stack.push(b * a);
                } else if (rpn.charAt(i) == '/') {
                    stack.push(b / a);
                }
            }
        }
        return stack.pop();
    }


    private int getPriority(char key) {
        if (key == '*' || key == '/') {
            return 3;
        } else if (key == '+' || key == '-') {
            return 2;
        } else if (key == '(') {
            return 1;
        } else if (key == ')') {
            return -1;
        } else return 0;
    }
}
