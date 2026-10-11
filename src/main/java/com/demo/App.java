package com.demo;

public class App {

    public String getMessage() {
        return "Online Examination System is running!";
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getMessage());
    }
}
