
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chuan
 */
public class Tree<Move> {

    private Node<Move> root;
    private int number;
    //private final boolean checkB;
    //private final boolean finishB;
    private int checkPointNumber;
    private Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect;
    //private int generations;
    //private String history;

    public Tree(Move rootData,/* boolean checkB, boolean finishB,*/ int checkPointNumber, int number,Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect) {
        root = new Node<Move>();
        root.data = rootData;
        root.children = new ArrayList<Move>();

        //this.checkB = checkB;
        //this.finishB = finishB;
        this.checkPointNumber = checkPointNumber;
        this.number = number;
        this.privousSpecialRect = privousSpecialRect;
    }

    public static class Node<Move> {

        private Move data;
        private Move parent;
        private List<Move> children;
    }

    public void addChild(Move childData) {
        root.children.add(childData);
    }

    public List<Move> getChildren() {
        return root.children;
    }

    public void setParent(Move parent) {
        root.parent = parent;
    }

    public Move getParent() {
        return (Move) root.parent;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    /*public Boolean getCheckB() {
        return checkB;
    }

    public Boolean getFinishB() {
        return finishB;
    }*/

    public Integer getCheckPointNumber() {
        return checkPointNumber;
    }

    public void setPrivousSpecialRect(Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect){
        this.privousSpecialRect = privousSpecialRect;
    }
    
    public Pair<Point2D, Pair<SpecialRect, SpecialRect>> getPrivousSpecialRect(){
        return privousSpecialRect;
    }
/*    
    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public Integer getGenerations() {
        return generations;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getHistory() {
        return history;
    }*/
}
