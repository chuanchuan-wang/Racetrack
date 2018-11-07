
//import java.awt.Point;
//import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import java.util.Random;
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
public class OptimalPath {

    public OptimalPath(int[][] arr, ArrayList p, int row, int col, int checkPointNumber, boolean verison/*, boolean AI*/) {
        //verison = true -- old one
        //verison = false -- new one
        System.out.println("OptimalPath");
        points = p;
        /*track = new int[row + 1][col + 1][checkPointNumber + 1];
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= col; j++) {
                for (int k = 0; k <= checkPointNumber; k++) {
                    track[i][j][k] = 1000;
                }
            }
        }*/
        //track = arr;
        double y = 0;
        for (int i = 0; i < row; i++) {
            double x = 0;
            for (int j = 0; j < col; j++) {
                if (arr[i][j] == 2) {
                    double temp_x = x;
                    double temp_y = y;
                    double x_size = 1;
                    double y_size = 1;

                    boolean leftCorner = true;
                    boolean rightCorner = true;
                    boolean specialPointesUL = true;
                    boolean specialPointesUR = true;
                    boolean specialPointesDL = true;
                    boolean specialPointesDR = true;
                    if (i > 0) {
                        if (arr[i - 1][j] != 2) {
                            temp_y += 0.01;
                            y_size -= 0.01;
                        }
                    }
                    if (j > 0) {
                        if (arr[i][j - 1] != 2) {
                            temp_x += 0.01;
                            x_size -= 0.01;
                        } else {
                            leftCorner = false;
                        }
                    }
                    if (j + 1 < col) {
                        if (arr[i][j + 1] != 2) {
                            x_size -= 0.01;
                        } else {
                            rightCorner = false;
                        }
                    }
                    if (i + 1 < row) {
                        if (arr[i + 1][j] != 2) {
                            y_size -= 0.01;
                        } else {
                            leftCorner = false;
                            rightCorner = false;
                        }
                    }
                    //System.out.println(temp_x+" "+ temp_y);
                    if (leftCorner) {
                        if (i < row - 1 && j > 0) {
                            if (arr[i + 1][j - 1] == 2) {
                                int size = 1;
                                SpecialRect r1 = new SpecialRect(new Rectangle2D.Double(x - size + 0.01, y + 0.01, size - 0.02, size - 0.02), new Line2D.Double(x, y + 0.01, x, y + size - 0.01), new Line2D.Double(x - size + 0.01, y + size, x - 0.01, y + size));
                                SpecialRect r2 = new SpecialRect(new Rectangle2D.Double(x + 0.01, y + size + 0.01, size - 0.02, size - 0.02), new Line2D.Double(x, y + size + 0.01, x, y + size + size - 0.01), new Line2D.Double(x + 0.01, y + size, x + size - 0.01, y + size));
                                specialRectList.add(new Pair(new Point2D.Double(x, y + 1), new Pair(r1, r2)));

                            }
                        }
                    }
                    if (rightCorner) {
                        if (i < row - 1 && j < col - 1) {
                            if (arr[i + 1][j + 1] == 2) {
                                int size = 1;
                                SpecialRect r1 = new SpecialRect(new Rectangle2D.Double(x + size + 0.01, y + 0.01, size - 0.02, size - 0.02), new Line2D.Double(x + size, y + 0.01, x + size, y + size - 0.01), new Line2D.Double(x + size + 0.01, y + size, x + size + size - 0.01, y + size));
                                SpecialRect r2 = new SpecialRect(new Rectangle2D.Double(x + 0.01, y + size + 0.01, size - 0.02, size - 0.02), new Line2D.Double(x + size, y + size + 0.01, x + size, y + size + size - 0.01), new Line2D.Double(x + 0.01, y + size, x + size - 0.01, y + size));
                                specialRectList.add(new Pair(new Point2D.Double(x + 1, y + 1), new Pair(r1, r2)));
                            }
                        }
                    }
                    if (specialPointesUL) {
                        if (i > 0 && j > 0) {
                            if (arr[i - 1][j - 1] != 2) {
                                Line2D l1 = new Line2D.Double(temp_x + 0.01, temp_y, temp_x + x_size - 0.01, temp_y);
                                Line2D l2 = new Line2D.Double(temp_x, temp_y + 0.01, temp_x, temp_y + y_size - 0.01);
                                if (!specialLines.contains(l1)) {
                                    specialLines.add(l1);
                                }
                                if (!specialLines.contains(l2)) {
                                    specialLines.add(l2);
                                }
                                temp_x += 0.01;
                                x_size -= 0.01;
                                temp_y += 0.01;
                                y_size -= 0.01;
                            }
                        }
                    }
                    if (specialPointesUR) {
                        if (i > 0 && j < col - 1) {
                            if (arr[i - 1][j + 1] != 2) {
                                Line2D l1 = new Line2D.Double(temp_x, temp_y, temp_x + x_size - 0.01, temp_y);
                                Line2D l2 = new Line2D.Double(temp_x + x_size, temp_y + 0.01, temp_x + x_size, temp_y + y_size - 0.01);
                                if (!specialLines.contains(l1)) {
                                    specialLines.add(l1);
                                }
                                if (!specialLines.contains(l2)) {
                                    specialLines.add(l2);
                                }
                                //temp_x += 0.01;
                                x_size -= 0.01;
                                temp_y += 0.01;
                                y_size -= 0.01;
                            }
                        }
                    }
                    if (specialPointesDL) {
                        if (i < row - 1 && j > 0) {
                            if (arr[i + 1][j - 1] != 2) {
                                Line2D l1 = new Line2D.Double(temp_x, temp_y, temp_x, temp_y + y_size - 0.01);
                                Line2D l2 = new Line2D.Double(temp_x + 0.01, temp_y + y_size, temp_x + x_size - 0.01, temp_y + y_size);
                                if (!specialLines.contains(l1)) {
                                    specialLines.add(l1);
                                }
                                if (!specialLines.contains(l2)) {
                                    specialLines.add(l2);
                                }
                                temp_x += 0.01;
                                x_size -= 0.01;
                                //temp_y += 0.01;
                                y_size -= 0.01;
                            }
                        }
                    }
                    if (specialPointesDR) {
                        if (i < row - 1 && j < col - 1) {
                            if (arr[i + 1][j + 1] != 2) {
                                Line2D l1 = new Line2D.Double(temp_x + x_size, temp_y, temp_x + x_size, temp_y + y_size - 0.01);
                                Line2D l2 = new Line2D.Double(temp_x, temp_y + y_size, temp_x + x_size - 0.01, temp_y + y_size);
                                if (!specialLines.contains(l1)) {
                                    specialLines.add(l1);
                                }
                                if (!specialLines.contains(l2)) {
                                    specialLines.add(l2);
                                }
                                //temp_x += 0.01;
                                x_size -= 0.01;
                                //temp_y += 0.01;
                                y_size -= 0.01;
                            }
                        }
                    }
                    rectList.add(new Rectangle2D.Double(temp_x, temp_y, x_size, y_size));
                }
                x++;
            }
            y++;
        }
        //System.out.println("specialRectList = " + specialRectList.size());
        //System.out.println("specialLine = " + specialLines.size());

        finishLine = new Line2D.Double(points.get(1).getKey(), points.get(1).getValue(), points.get(2).getKey(), points.get(2).getValue());
        checkLine = new Line2D[checkPointNumber];
        for (int i = 0; i < checkPointNumber; i++) {
            checkLine[i] = new Line2D.Double(points.get(3 + (i * 2)).getKey(), points.get(3 + (i * 2)).getValue(), points.get(4 + (i * 2)).getKey(), points.get(4 + (i * 2)).getValue());
        }
        //track[points.get(0).getKey()][points.get(0).getValue()][0] = 0;
        Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect = null;
        for (int l = 0; l < specialRectList.size(); l++) {
            SpecialRect r1 = specialRectList.get(l).getValue().getKey();
            SpecialRect r2 = specialRectList.get(l).getValue().getValue();
            if (r1.getL1().contains(points.get(0).getKey(), points.get(0).getValue()) || r1.getL2().contains(points.get(0).getKey(), points.get(0).getValue())) {
                privousSpecialRect = new Pair(specialRectList.get(l).getKey(), new Pair(r1, r2));
            } else if (r2.getL1().contains(points.get(0).getKey(), points.get(0).getValue()) || r2.getL2().contains(points.get(0).getKey(), points.get(0).getValue())) {
                privousSpecialRect = new Pair(specialRectList.get(l).getKey(), new Pair(r2, r1));
            }
        }

        if (verison) {
            Move rootM = new Move(points.get(0).getKey(), points.get(0).getValue(), 0, 0);
            Tree rootT = new Tree(rootM, /*false, false,*/ 0, 0, privousSpecialRect);
            rootT.setNumber(0);

            nextMove = new LinkedList();
            moveTree = new HashMap<Move, Tree>();

            moveTree.put(rootM, rootT);
            nextMove.add(rootM);

            //int fastestMove = 0;
            while (!nextMove.isEmpty()) {
                findOptimalPathTree(nextMove.removeFirst());
                /*Move m = nextMove.removeFirst();
                Tree t = moveTree.get(m);
                int check = t.getCheckPointNumber();
                //System.out.println(fastestMove+" "+check);
                if (fastestMove <= check + 1) {
                    if (fastestMove < check) {
                        fastestMove = check;
                        System.out.println("Pass checkpoint line: " + fastestMove);
                    }
                    findOptimalPathTree(m);
                }*/
            }
        } else {
            Move_v2 rootM = new Move_v2(points.get(0).getKey(), points.get(0).getValue(), 0, 0, 0);
            Tree_v2 rootT = new Tree_v2(rootM,/* false, false*/ 0, privousSpecialRect);
            rootT.setNumber(0);

            nextMove_v2 = new LinkedList();
            moveTree_v2 = new HashMap<Move_v2, Tree_v2>();
            //optimalPath_v2 = new LinkedList();

            moveTree_v2.put(rootM, rootT);
            nextMove_v2.add(rootM);

            int fastestMove = 0;
            while (!nextMove_v2.isEmpty()) {
                Move_v2 m_v2 = nextMove_v2.removeFirst();
                int check = m_v2.getCheckPointNumber();
                if (fastestMove <= check + 1) {
                    if (check > fastestMove) {
                        fastestMove = check;
                        System.out.println("Pass checkpoint line: " + fastestMove);
                    }
                    boolean uniqueNode = true;
                    check++;
                    while (check <= checkLine.length) {
                        Move_v2 tempM = new Move_v2(m_v2.getX(), m_v2.getY(), m_v2.getVector_x(), m_v2.getVector_y(), check);
                        if (!moveTree_v2.containsKey(tempM)) {
                            check++;
                        } else {
                            //System.out.println("<Move> ( " + m_v2.getX() + " , " + m_v2.getY() + " , " + m_v2.getVector_x() + " , " + m_v2.getVector_y() + ") check = " + m_v2.getCheckPointNumber() + "  " + check);
                            uniqueNode = false;
                            break;
                        }
                    }
                    if (uniqueNode) {
                        findOptimalPathTree(m_v2);
                    }
                }
            }
        }
        /*for (int k = 0; k <= checkPointNumber; k++) {
            for (int i = 0; i <= row; i++) {
                for (int j = 0; j <= col; j++) {
                    if (track[j][i][k] == 1000) {
                        System.out.print(" * ");
                    } else if (track[j][i][k] < 10) {
                        System.out.print(" " + track[j][i][k] + " ");
                    } else {
                        System.out.print(track[j][i][k] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("specialRectList = " + specialRectList.size());
         */
        //System.out.println(verison + " Finish! Optimal number = " + optimalNumber);

    }
    //private int[][][] track;

// important point and lines
    private ArrayList<Pair<Integer, Integer>> points = new ArrayList();
    private Line2D finishLine;
    private Line2D checkLine[];
    // crash detecting
    private LinkedList<Rectangle2D> rectList = new LinkedList();
    private LinkedList<Pair<Point2D, Pair<SpecialRect, SpecialRect>>> specialRectList = new LinkedList();
    private LinkedList<Line2D> specialLines = new LinkedList();

    // optimal path and computer player 
    public LinkedList<String> optimalPathStr = new LinkedList();
    private String optimalStr = "";

    //public int optimalNumber = 1000;
    private Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect;

    // old verison
    private LinkedList<Move> nextMove;// = new LinkedList();
    private HashMap<Move, Tree> moveTree;// = new HashMap<Move, Tree>();

    // new verison
    private LinkedList<Move_v2> nextMove_v2;// = new LinkedList();
    private HashMap<Move_v2, Tree_v2> moveTree_v2;// = new HashMap<Move_v2, Tree_v2>();

    private void findOptimalPathTree(Move m/*, MovePlus mp, Tree t*/) {

        Tree t = moveTree.get(m);
        //boolean hasNextMove = false;
        //System.out.println("<Move> ( " + m.getX() + " , " + m.getY() + " , " + m.getVector_x() + " , " + m.getVector_y() + ") " + t.getNumber()+" checkc = "+t.getCheckPointNumber());

        for (int i = 0; i < 9; i++) {
            //boolean checkB = t.getCheckB();
            boolean finishB = false;//t.getFinishB();
            int checkNumber = t.getCheckPointNumber();
            //privousSpecialRect = null;
            //System.out.print("Direction " + i);
            Line2D line = direction(i, m.getX(), m.getY(), m.getVector_x(), m.getVector_y());
            privousSpecialRect = t.getPrivousSpecialRect();
            boolean crash = detectCrash(line/*, privousSpecialRect*/);
            if (!crash) {
                if (checkNumber < checkLine.length) {// not yet pass check point
                    if (line.intersectsLine(checkLine[checkNumber])) {
                        checkNumber++;
                        if (checkNumber == checkLine.length) {
                            finishB = line.intersectsLine(finishLine);
                        }
                    } else if (line.intersectsLine(finishLine)) {// not yet pass check point but cross finishline -- wrong direction
                        crash = true;
                    }
                } else {
                    finishB = line.intersectsLine(finishLine);
                }
            }
            //System.out.println("Crash = " + crash);
            int next_x = (int) line.getX2();
            int next_y = (int) line.getY2();
            if (!crash) {
                //hasNextMove = true;
                Move next_m = new Move(next_x, next_y, next_x - m.getX(), next_y - m.getY());
                if (finishB) {
                    //System.out.println("Reach finish line! Time = " + (t.getNumber() + 1));
                    t.addChild(next_m);
                    optimalStr = "";
                    //if (optimalPathStr.isEmpty()) {
                    if (optimalPathStr.size() < 100) {
                        //optimalPath.clear();
                        printOptimalPath(m);
                        //optimalPath.add(next_m);
                        optimalStr += next_m.getX() + " " + next_m.getY();
                        //System.out.println("(" + next_m.getX() + "," + next_m.getY() + ")");
                        //System.out.println("(" + next_m.getX() + "," + next_m.getY() + "," + next_m.getVector_x() + "," + next_m.getVector_y() + ")");
                        optimalPathStr.add(optimalStr);
                        //optimalNumber = t.getNumber() + 1;
                        //System.out.println("Best solution = " + (t.getNumber() + 1));
                    } else {
                        nextMove.clear();
                    }
                    //optimalNumber = t.getNumber() + 1;
                } else {
                    Tree childT;
                    if (!moveTree.containsKey(next_m)) {
                        //System.out.println(" ✔");
                        t.addChild(next_m);

                        childT = new Tree(next_m, /*checkB, finishB,*/ checkNumber, t.getNumber() + 1, privousSpecialRect);
                        childT.setParent(m);
                        moveTree.put(next_m, childT);
                        /*if (track[next_m.getX()][next_m.getY()][0] > t.getNumber() + 1) {
                            track[next_m.getX()][next_m.getY()][0] = t.getNumber() + 1;
                        }*/

                        if (!nextMove.contains(next_m)) {
                            nextMove.add(next_m);
                        }
                    }/* else {
                        System.out.println(" - already exists.");
                    }*/
                }
            }
        }
        /*if (!hasNextMove) {
            //System.out.println("<dead end_1> ( " + m.getX() + " , " + m.getY() + " , " + m.getVector_x() + " , " + m.getVector_y() + ")");
            t.setNumber(-1);
            removeData((Move) t.getParent());
        }*/

    }

    private void findOptimalPathTree(Move_v2 m/*, MovePlus mp, Tree t*/) {
        Tree_v2 t = moveTree_v2.get(m);
        //boolean hasNextMove = false;
        //System.out.println("[working]<Move> ( " + m.getX() + " , " + m.getY() + " , " + m.getVector_x() + " , " + m.getVector_y() + ") check = " + m.getCheckPointNumber() +  " " + t.getNumber());

        for (int i = 0; i < 9; i++) {
            //boolean checkB = false;//t.getCheckB();
            boolean finishB = false;//t.getFinishB();
            int checkNumber = m.getCheckPointNumber();
            //privousSpecialRect = null;
            //System.out.println("Direction " + i);
            Line2D line = direction(i, m.getX(), m.getY(), m.getVector_x(), m.getVector_y());
            privousSpecialRect = t.getPrivousSpecialRect();
            boolean crash = detectCrash(line/*, privousSpecialRect*/);
            if (checkNumber == checkLine.length) {
                finishB = line.intersectsLine(finishLine);
            } else {
                if (line.intersectsLine(checkLine[checkNumber])) {
                    //System.out.println(checkNumber+" ("+line.getX1()+","+line.getY1()+")("+line.getX2()+","+line.getY2()+")");
                    checkNumber++;
                    if (checkNumber == checkLine.length) {
                        //checkB = true;
                        finishB = line.intersectsLine(finishLine);
                    }
                }
            }
            int next_x = (int) line.getX2();
            int next_y = (int) line.getY2();
            if (!crash) {
                //hasNextMove = true;
                Move_v2 next_m = new Move_v2(next_x, next_y, next_x - m.getX(), next_y - m.getY(), checkNumber);
                if (finishB) {
                    //System.out.println("FINISH!!!");
                    t.addChild(next_m);
                    optimalStr = "";
                    //if (optimalPathStr.isEmpty()) {
                    if (optimalPathStr.size() < 100) {
                        //optimalPath_v2.clear();
                        printOptimalPath(m);
                        //optimalPath_v2.add(next_m);
                        optimalStr += next_m.getX() + " " + next_m.getY();
                        //System.out.println("(" + next_m.getX() + "," + next_m.getY() + ")");
                        optimalPathStr.add(optimalStr);
                        //optimalNumber = t.getNumber() + 1;
                    } else {
                        nextMove_v2.clear();
                    }
                } else {
                    Tree_v2 childT;
                    if (!moveTree_v2.containsKey(next_m)) {
                        //System.out.print("Direction " + i);
                        t.addChild(next_m);
                        childT = new Tree_v2(next_m,/* checkB, finishB*/ t.getNumber() + 1, privousSpecialRect);
                        childT.setParent(m);
                        moveTree_v2.put(next_m, childT);
                        //System.out.println("  (" + next_m.getX() + "," + next_m.getY() + ")");

                        /*if (track[next_m.getX()][next_m.getY()][checkNumber] > t.getNumber() + 1) {
                            track[next_m.getX()][next_m.getY()][checkNumber] = t.getNumber() + 1;
                        }*/
                        if (!nextMove_v2.contains(next_m)) {
                            nextMove_v2.add(next_m);
                        }
                    }
                }
            }
        }
        //}
        /*if (!hasNextMove) {
            t.setNumber(-1);
            removeData((Move_v2) t.getParent());
        }*/

    }

    private Line2D direction(int i, int x, int y, int vector_x, int vector_y) {
        Line2D line = new Line2D.Double();

        switch (i) {
            case 0:
                line = new Line2D.Double(x, y, x + vector_x - 1, y + vector_y - 1);
                //if (line.intersectsLine(line)) {
                //number = findOptimalPath(x + vector_x - 1, y + vector_y - 1, vector_x - 1, vector_y - 1);
                //}
                break;
            case 1:
                line = new Line2D.Double(x, y, x + vector_x, y + vector_y - 1);
                //number = findOptimalPath(x + vector_x - 1, y + vector_y, vector_x - 1, vector_y);
                break;
            case 2:
                line = new Line2D.Double(x, y, x + vector_x + 1, y + vector_y - 1);
                //number = findOptimalPath(x + vector_x - 1, y + vector_y + 1, vector_x - 1, vector_y + 1);
                break;
            case 3:
                line = new Line2D.Double(x, y, x + vector_x - 1, y + vector_y);
                //number = findOptimalPath(x + vector_x, y + vector_y - 1, vector_x, vector_y - 1);
                break;
            case 4:
                line = new Line2D.Double(x, y, x + vector_x, y + vector_y);
                //number = findOptimalPath(x + vector_x, y + vector_y, vector_x, vector_y);
                break;
            case 5:
                line = new Line2D.Double(x, y, x + vector_x + 1, y + vector_y);
                //number = findOptimalPath(x + vector_x, y + vector_y + 1, vector_x, vector_y + 1);
                break;
            case 6:
                line = new Line2D.Double(x, y, x + vector_x - 1, y + vector_y + 1);
                //number = findOptimalPath(x + vector_x + 1, y + vector_y - 1, vector_x + 1, vector_y - 1);
                break;
            case 7:
                line = new Line2D.Double(x, y, x + vector_x, y + vector_y + 1);
                //number = findOptimalPath(x + vector_x + 1, y + vector_y, vector_x + 1, vector_y);
                break;
            case 8:
                line = new Line2D.Double(x, y, x + vector_x + 1, y + vector_y + 1);
                //number = findOptimalPath(x + vector_x + 1, y + vector_y + 1, vector_x + 1, vector_y + 1);
                break;
        }
        return line;
    }

    private void updateData(List list, int number) {
        for (int i = 0; i < list.size(); i++) {
            Move m = (Move) list.get(i);
            Tree t = moveTree.get(m);
            if (t != null) {
                if (t.getNumber() > number + 1) {
                    /*if (track[m.getX()][m.getY()] > number) {
                        track[m.getX()][m.getY()] = number;
                    }*/
                    //System.out.println("<better path> ( " + m.getX() + " , " + m.getY() + " , " + m.getVector_x() + " , " + m.getVector_y() + ") old number = " + t.getNumber() + " ,new number = " + (number + 1));
                    t.setNumber(number + 1);
                    updateData(t.getChildren(), number + 1);
                }
            }
        }
    }

    private void removeData(Move m) {
        Tree parentT = moveTree.get(m);
        if (parentT != null) {
            List child = parentT.getChildren();
            boolean deadend = true;
            for (int i = 0; i < child.size(); i++) {
                Tree childT = moveTree.get((Move) child.get(i));
                if (childT == null) {
                    // System.out.print("Remove data... ");
                    //System.out.println(child.size());
                    //System.out.println(m.getX() + " " + m.getY() + " " + m.getVector_x() + " " + m.getVector_y());
                } else if (childT.getNumber() != -1) {
                    deadend = false;
                    break;
                }
            }
            if (deadend) {
                parentT.setNumber(-1);
                //System.out.println("<dead end_2> ( " + m.getX() + " , " + m.getY() + " , " + m.getVector_x() + " , " + m.getVector_y() + ")");
                removeData((Move) parentT.getParent());
            }
        }
    }

    private void removeData(Move_v2 m) {
        //Move parentM = (Move) t.getParent();
        Tree_v2 parentT = moveTree_v2.get(m);
        if (parentT != null) {
            List child = parentT.getChildren();
            boolean deadend = true;
            for (int i = 0; i < child.size(); i++) {
                Tree_v2 childT = moveTree_v2.get((Move_v2) child.get(i));
                if (childT == null) {
                    //System.out.print("Remove data... ");
                    //System.out.println(child.size());
                    //System.out.println(m.getX() + " " + m.getY() + " " + m.getVector_x() + " " + m.getVector_y());
                } else if (childT.getNumber() != -1) {
                    deadend = false;
                    break;
                }
            }
            if (deadend) {
                parentT.setNumber(-1);
                //System.out.println("<dead end_2> ( " + m.getX() + " , " + m.getY() + " , " + m.getVector_x() + " , " + m.getVector_y() + ")");
                removeData((Move_v2) parentT.getParent());
            }
        }
    }

    private void printOptimalPath(Move m) {
        if (m != null) {
            Tree parentT = moveTree.get(m);
            if (parentT != null) {
                //System.out.print("Parent = ");
                printOptimalPath((Move) parentT.getParent());
            }
            //optimalPath.add(m);
            optimalStr += m.getX() + " " + m.getY() + " ";
            //System.out.print("(" + m.getX() + "," + m.getY() + ")");
            //System.out.println("(" + m.getX() + "," + m.getY() + "," + m.getVector_x() + "," + m.getVector_y() + ") number = " + number);
        }
    }

    private void printOptimalPath(Move_v2 m) {
        if (m != null) {
            Tree_v2 parentT = moveTree_v2.get(m);
            if (parentT != null) {
                //System.out.print("Parent = ");
                printOptimalPath((Move_v2) parentT.getParent());
            }
            //optimalPath_v2.add(m);
            optimalStr += m.getX() + " " + m.getY() + " ";
            //System.out.print("(" + m.getX() + "," + m.getY() + ")");
            //System.out.println("(" + m.getX() + "," + m.getY() + "," + m.getVector_x() + "," + m.getVector_y() + ") number = " + number);
        }
    }

    private boolean detectCrash(Line2D line/*, Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect*/) {
        boolean crash = false;
        for (int index = 0; index < rectList.size(); index++) {//check for crash
            if (line.intersects(rectList.get(index))) {
                crash = true;
                //System.out.println("crash here-(-1)");
                break;
            }
        }
        if (!crash) {
            for (int k = 0; k < specialLines.size(); k++) {
                if (line.intersectsLine(specialLines.get(k))) {
                    crash = true;
                    //System.out.println("crash here-0");
                    break;
                }
            }
        }
        //Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect = t.getPrivousSpecialRect();
        if (!crash && privousSpecialRect != null) {
            Point2D specialP = privousSpecialRect.getKey();
            SpecialRect r1 = privousSpecialRect.getValue().getKey();
            SpecialRect r2 = privousSpecialRect.getValue().getValue();
            if (r2.getRect().intersectsLine(line) || r2.getL1().intersectsLine(line) || r2.getL2().intersectsLine(line)) {
                crash = true;
                //System.out.println("crash-0");
            } else if (r1.getRect().intersectsLine(line) || r1.getL1().intersectsLine(line) || r1.getL2().intersectsLine(line)) {
                // Stay
            } else if ((line.getX1() == specialP.getX() && line.getY1() == specialP.getY())
                    || (line.getX2() == specialP.getX() && line.getY2() == specialP.getY())) {
                // Stay
            } else {
                privousSpecialRect = null;
                // Leave special rectangle
            }
        }
        if (!crash) {
            for (int l = 0; l < specialRectList.size(); l++) {
                SpecialRect r1 = specialRectList.get(l).getValue().getKey();
                SpecialRect r2 = specialRectList.get(l).getValue().getValue();
                if (r1.getRect().intersectsLine(line) && r2.getRect().intersectsLine(line)) {
                    crash = true;
                    //System.out.println("crash-1");
                } else if (r1.getL1().intersectsLine(line) && r2.getL1().intersectsLine(line)) {
                    crash = true;
                    //System.out.println("crash-2");

                } else if (r1.getL2().intersectsLine(line) && r2.getL2().intersectsLine(line)) {
                    crash = true;
                    //System.out.println("crash-3");

                } else if (r1.getRect().intersectsLine(line) || r1.getL1().intersectsLine(line) || r1.getL2().intersectsLine(line)) {
                    privousSpecialRect = new Pair(specialRectList.get(l).getKey(), new Pair(r1, r2));
                } else if (r2.getRect().intersectsLine(line) || r2.getL1().intersectsLine(line) || r2.getL2().intersectsLine(line)) {
                    privousSpecialRect = new Pair(specialRectList.get(l).getKey(), new Pair(r2, r1));
                }
            }
        }
        return crash;
    }

    public void testing() {
        Move_v2 rootM = new Move_v2(points.get(0).getKey(), points.get(0).getValue(), 0, 0, 0);
        Tree_v2 rootT = new Tree_v2(rootM,/* false, false*/ 0, privousSpecialRect);
        rootT.setNumber(0);

        nextMove_v2 = new LinkedList();
        moveTree_v2 = new HashMap<Move_v2, Tree_v2>();
        //optimalPath_v2 = new LinkedList();

        moveTree_v2.put(rootM, rootT);
        nextMove_v2.add(rootM);
        //optimalNumber = 1000;
        while (!nextMove_v2.isEmpty()) {
            findOptimalPathTree(nextMove_v2.removeFirst());
        }
        //System.out.println("[Testing] Finish! Optimal number = " + optimalNumber);

    }

    public void generateComputerPlayer(String str) throws IOException {
        File cpF = new File("cp-" + str);
        if (cpF.exists()) {
            cpF.delete();
        }
        if (!optimalPathStr.isEmpty()) {
            FileWriter cp_fw = new FileWriter("cp-" + str, false);
            BufferedWriter cp_bw = new BufferedWriter(cp_fw);
            cp_bw.write(String.valueOf(optimalPathStr.size()));
            for (int i = 0; i < optimalPathStr.size(); i++) {
                cp_bw.newLine();
                cp_bw.write(optimalPathStr.get(i));
            }
            cp_bw.close();
            cp_fw.close();
            System.out.println("Written Done.---create amd write: cp-" + str);
        }
    }
}
