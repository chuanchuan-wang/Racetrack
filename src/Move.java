/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chuan
 */
public class Move {

    private final int x;
    private final int y;
    private final int vector_x;
    private final int vector_y;
    //private final boolean checkB;
    //private final boolean finishB;
    //private final int number;
    //private final int checkPointNumber;

    public Move(int x, int y, int vector_x, int vector_y/*, boolean checkB, boolean finishB, int number, int checkPointNumber*/) {
        this.x = x;
        this.y = y;
        this.vector_x = vector_x;
        this.vector_y = vector_y;
        //this.checkB = checkB;
        //this.finishB = finishB;
        //this.number = number;
        //this.checkPointNumber = checkPointNumber;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getVector_x() {
        return vector_x;
    }

    public Integer getVector_y() {
        return vector_y;
    }

    /*public Boolean getCheckB(){
        return checkB;
    }
    public Boolean getFinishB(){
        return finishB;
    }
    public Integer getNumber(){
        return number;
    }
    public Integer getCheckPointNumber(){
        return checkPointNumber;
    }*/
    @Override
    public boolean equals(Object obj) {
        Move m = (Move) obj;
        //System.out.println("Compare!!");
        return (this.x == m.x) && (this.y == m.y) && (this.vector_x == m.vector_x) && (this.vector_y == m.vector_y);
    }

    @Override
    public int hashCode() {
        //System.out.println("hashCode!!");
        return 0;
    }
}
