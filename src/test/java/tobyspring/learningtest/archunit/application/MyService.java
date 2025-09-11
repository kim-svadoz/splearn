package tobyspring.learningtest.archunit.application;

public class MyService {

    MyService myService;

    void run() {
        myService = new MyService();
        System.out.println("myService = " + myService);
    }
}
