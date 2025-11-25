package demo.Practice_Java;

import demo.App;

public class ConstructorsInJava {
     String a;
    int b;
    float c;

    public ConstructorsInJava(){
        this("STR",99,99.99f);
        System.out.println(" ---2--- App non parameterised Constructor Called");
    }

    public ConstructorsInJava(String msg, int num, float decimalNum){
        this("bye");
        this.a = msg;
        this.b = num;
        this.c = decimalNum;
        System.out.println(" ---3--- App parameterised Constructor Called with message: " + msg+" "+ num+" " + decimalNum);
    }
    public ConstructorsInJava(String mymsg){
        // this();
        this.a = mymsg;
        System.out.println(" ---4--- App single parameter Constructor Called with message: " + mymsg);
    }
    public static void main( String[] args )
    {
        System.out.println( " ---1---" );
        ConstructorsInJava constructorsInJava = new ConstructorsInJava();
        // ConstructorsInJava appWithMsg = new ConstructorsInJava("Custom Message",4,3.4f);
        // ConstructorsInJava appSingleParam = new ConstructorsInJava("Hello");
    }
    
}
