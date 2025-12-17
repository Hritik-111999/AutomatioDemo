package demo.Practice_Java;

import java.util.*;   // For List, ArrayList, HashMap

// ---------- Parent Class ----------
class Person {

    // Global (instance) variables
    String name;
    int age;

    // static variable (class-level)
    static String species = "Human";

    // final variable
    final String country = "India";

    // Constructor
    Person(String name, int age) {
        this.name = name;   // 'this' keyword
        this.age = age;
    }

    // Method (to be overridden)
    void introduce() {
        System.out.println("I am a person.");
    }

    // Method overloading
    void greet() {
        System.out.println("Hello!");
    }

    void greet(String msg) {
        System.out.println(msg);
    }
}

// ---------- Child Class (Inheritance) ----------
class Employee extends Person {

    int empId;

    // Constructor + super keyword
    Employee(String name, int age, int empId) {
        super(name, age);
        this.empId = empId;
    }

    // Method overriding
    @Override
    void introduce() {
        System.out.println("I am an employee. Name: " + name + ", Age: " + age);
    }
}

// ---------- Main Class ----------
public class JavaFundamentalsDemo {

    public static void main(String[] args) {

        // Local variable
        int number = 5;

        // if-else
        if (number > 3) {
            System.out.println("Number is greater than 3");
        } else {
            System.out.println("Number is 3 or less");
        }

        // for loop
        System.out.println("\nFor Loop:");
        for (int i = 1; i <= 3; i++) {
            System.out.println("i = " + i);
        }

        // while loop
        System.out.println("\nWhile Loop:");
        int j = 1;
        while (j <= 3) {
            System.out.println("j = " + j);
            j++;
        }

        // List (ArrayList)
        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Selenium");
        skills.add("Automation");

        System.out.println("\nList Example:");
        for (String skill : skills) {
            System.out.println(skill);
        }

        // HashMap
        HashMap<Integer, String> empMap = new HashMap<>();
        empMap.put(101, "Alice");
        empMap.put(102, "Bob");

        System.out.println("\nHashMap Example:");
        for (Integer key : empMap.keySet()) {
            System.out.println(key + " -> " + empMap.get(key));
        }

        // Object creation
        Person p = new Person("Rahul", 25);
        p.introduce();
        p.greet();
        p.greet("Welcome to Java!");

        Employee e = new Employee("Hritik", 28, 1001);
        e.introduce();

        // static variable access
        System.out.println("\nStatic Variable:");
        System.out.println(Person.species);

        // try-catch-finally
        try {
            int result = 10 / 0;  // Exception
            System.out.println(result);
        } catch (ArithmeticException ex) {
            System.out.println("Exception caught: " + ex.getMessage());
        } finally {
            System.out.println("Finally block always executes");
        }

        // final keyword demonstration
        final int MAX = 100;
        System.out.println("\nFinal variable: " + MAX);
    }
}
