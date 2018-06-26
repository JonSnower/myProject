package com.lambda;

import java.util.function.Function;

public class Test {  
    public static void main(String[] args) throws InterruptedException {  
        String name = "";  
        String name1 = "12";  
        System.out.println(validInput(name, inputStr -> inputStr.isEmpty() ? "名字不能为空":inputStr));  
        System.out.println(validInput(name1, inputStr -> inputStr.length() > 3 ? "名字过长":inputStr));  
    }  
      
    public static String validInput(String name,Function<String,String> function) {  
        return function.apply(name);  
    }  
}  