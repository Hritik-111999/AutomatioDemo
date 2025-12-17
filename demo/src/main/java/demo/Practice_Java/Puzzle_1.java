package demo.Practice_Java;

public class Puzzle_1 {
    int x = 10;
    static int y = 20;
    public static void main(String[] args) {
        Puzzle_1 p1 = new Puzzle_1();
        p1.x = 888;
        p1.y = 999;
        // System.out.println("p1.x = " + p1.x+", p1.y = " + p1.y);
        Puzzle_1 p2 = new Puzzle_1();
        System.out.println("p2.x = " + p2.x+", p2.y = " + p2.y);
    }
    
}

//options
//A. p2.x = 10, p2.y = 20
//B. p2.x = 888, p2.y = 20
//C. p2.x = 10, p2.y = 999
//D. p2.x = 888, p2.y = 999
