
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
public class Tree_v2<Move_v2> {

    private Node<Move_v2> root;
    private int number;
    //private final boolean checkB;
    //private final boolean finishB;
    private Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect;
    //private int checkPointNumber;
    //private int generations;
    //private String history;

    public Tree_v2(Move_v2 rootData, /*boolean checkB, boolean finishB, int checkPointNumber*/ int number, Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect) {
        root = new Node<Move_v2>();
        root.data = rootData;
        root.children = new ArrayList<Move_v2>();

        // this.checkB = checkB;
        //this.finishB = finishB;
        //this.checkPointNumber = checkPointNumber;
        this.number = number;
        this.privousSpecialRect = privousSpecialRect;
    }

    public static class Node<Move_v2> {

        private Move_v2 data;
        private Move_v2 parent;
        private List<Move_v2> children;
    }

    public void addChild(Move_v2 childData) {
        root.children.add(childData);
    }

    public List<Move_v2> getChildren() {
        return root.children;
    }

    public void setParent(Move_v2 parent) {
        root.parent = parent;
    }

    public Move_v2 getParent() {
        return (Move_v2) root.parent;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
/*
    public Boolean getCheckB() {
        return checkB;
    }

    public Boolean getFinishB() {
        return finishB;
    }
*/
    public void setPrivousSpecialRect(Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect) {
        this.privousSpecialRect = privousSpecialRect;
    }

    public Pair<Point2D, Pair<SpecialRect, SpecialRect>> getPrivousSpecialRect() {
        return privousSpecialRect;
    }
    /*
    public Integer getCheckPointNumber() {
        return checkPointNumber;
    }

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
