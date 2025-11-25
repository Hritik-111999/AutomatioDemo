package demo.Practice_Java;

public class InstanceAndStaticMethods {

    // Instance variable (belongs to each object)
    int a;

    // Static variable (shared by all objects)
    static int counter = 0;

    // Default constructor
    public InstanceAndStaticMethods() {
        counter++;     // increases each time an object is created
        System.out.println("---Default Constructor---");
    }

    // Parameterized constructor
    public InstanceAndStaticMethods(int number) {
        this();        // calls default constructor
        this.a = number;
        System.out.println("---Parameterized Constructor--- Value of a = " + number);
    }

    // Static method (can be called without creating an object)
    public static void fun1() {
        System.out.println("---fun1---   I am a Static Method");
        System.out.println("Static counter value: " + counter);
    }

    // Instance method (requires an object to call)
    public void fun2() {
        System.out.println("---fun2---   I am an Instance Method");
        System.out.println("Instance variable a = " + a);
    }

    public static void main(String[] args) {
        System.out.println("---Main Method---");

        // Calling static method directly
        fun1();

        // Creating object 1
        InstanceAndStaticMethods obj1 = new InstanceAndStaticMethods();
        obj1.fun2();

        // Creating object 2 with parameter
        InstanceAndStaticMethods obj2 = new InstanceAndStaticMethods(5555);
        obj2.fun2();

        // Calling static method again to see updated counter
        fun1();
    }
}
