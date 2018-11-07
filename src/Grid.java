
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javafx.util.Pair;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chuan
 */
public class Grid extends JPanel {

    public Grid(String str, boolean opt, boolean computerPlayer) throws IOException {
        str += ".txt";
        System.out.println(str);
        //trackS = str;
        optB = opt;
        //optB = true;

        computerPlayerB = computerPlayer;
        try {
            // Get information of track
            FileReader trackData = new FileReader(str);
            try {
                Scanner parser = new Scanner(trackData);
                String trackName = parser.nextLine();
                System.out.println(trackName);
                for (int i = 0; i < total_width; i++) {
                    String line = parser.nextLine();
                    String[] strArr = line.split("");
                    for (int j = 0; j < total_height; j++) {
                        if (strArr[j].equals("*")) {//barriers 
                            arr[i][j] = 2;
                        } else if (strArr[j].equals(" ")) {//track
                            arr[i][j] = 3;
                        } else {//finish line
                            arr[i][j] = Integer.valueOf(strArr[j]);
                        }
                    }
                }
                int index = 0;
                for (; index < 6; index += 2) {
                    String line = parser.nextLine();
                    String[] strArr = line.split(" ");
                    //arr[total_width][index] = Integer.valueOf(strArr[0]);
                    //arr[total_width][index + 1] = Integer.valueOf(strArr[1]);
                    points.add(new Pair(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1])));
                }
                System.out.println("Points:" + points.size());
                String line = parser.nextLine();
                int checkPointNumber = 0;
                while (!line.equals("optimalpath")) {
                    System.out.println("index = " + index);
                    int temp = index;
                    for (int i = temp; i < temp + 4; i += 2) {
                        String[] strArr = line.split(" ");
                        //arr[total_width][index] = Integer.valueOf(strArr[0]);
                        //arr[total_width][index + 1] = Integer.valueOf(strArr[1]);
                        points.add(new Pair(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1])));
                        index += 2;
                        System.out.println("i = " + i + " index = " + index + " checkPointNumber = " + checkPointNumber);
                        line = parser.nextLine();
                    }
                    checkPointNumber++;
                }
                checkLineArr = new Line2D[checkPointNumber];
                System.out.println("Points:" + points.size());
                while (/*opt &&*/parser.hasNext()) {
                    line = parser.nextLine();
                    String[] strArr = line.split(" ");
                    optimalPathGrid.add(new Pair(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1])));
                    //optimalPath.add(new Pair(computePosition(Integer.valueOf(strArr[0]), false), computePosition(Integer.valueOf(strArr[1]), true)));
                }
                System.out.println("Size of Optimal path: " + optimalPathGrid.size());
                //arr[total_width][0] = start point-x
                //arr[total_width][1] = start point-y
                //arr[total_width][2] = finishLine(start) point-x
                //arr[total_width][3] = finishLine(start) point-y
                //arr[total_width][4] = finishLine(end) point-x
                //arr[total_width][5] = finishLine(end) point-y
                //first check point
                //arr[total_width][6] = checkLine(start) point-x
                //arr[total_width][7] = checkLine(start) point-y
                //arr[total_width][8] = checkLine(end) point-x
                //arr[total_width][9] = checkLine(end) point-y
                /*if (parser.nextLine().equals("score")) {
                    while (parser.hasNext()) {
                        String line = parser.nextLine();
                        String[] strArr = line.split(" ");
                        scoreList.add(new Pair(Integer.valueOf(strArr[0]), strArr[1]));
                    }
                }*/
            } finally {
                trackData.close();
            }
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // Get information of computer player
            FileReader cpData = new FileReader("cp-" + str);
            try {
                Scanner parser = new Scanner(cpData);
                String pathNumber = (parser.nextLine());
                Random rand = new Random();
                int n = rand.nextInt(Integer.valueOf(pathNumber));
                String line = parser.nextLine();
                while (n > 0) {
                    line = parser.nextLine();
                    n--;
                }
                String[] strArr = line.split(" ");
                for (int i = 0; i < strArr.length; i += 2) {
                    computerPlayerLGF.add(new Pair(Integer.valueOf(strArr[i]), Integer.valueOf(strArr[i + 1])));
                }
                System.out.println("Size of computer player: " + computerPlayerLGF.size());
            } finally {
                cpData.close();
            }
        } catch (FileNotFoundException ex) {
            computerPlayerLGF = optimalPathGrid;
            //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //computer player
        //OptimalPath optimalP = new OptimalPath(arr, points, total_width, total_height, checkLineArr.length, true);
        //optimalP.generateComputerPlayer(str);

        colorSetting();
    }

    /*public Dimension getPreferredSized(){
            return new Dimension(200,200);
        }*/
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("PaintGrid");
        super.paintComponent(g);
        //Graphics2D g2d = (Graphics2D) g.create();
        Graphics2D g2d = (Graphics2D) g;

        size = Math.min(getWidth() - 4, getHeight() - 4) / 40;
        //int width = getWidth() - size * 2;
        //int height = getHeight() - size * 2;

        int y = (getHeight() - size * total_height) / 2;
        //drawing track part
        for (int horz = 0; horz < total_width; horz++) {
            int x = (getWidth() - (size * total_width)) / 2;
            for (int vert = 0; vert < total_height; vert++) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(x, y, size, size);
                switch (arr[horz][vert]) {
                    case 0://finish line - white
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(x, y, size, size);
                        break;
                    case 1://finish line - black
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(x, y, size, size);
                        break;
                    case 2://barriers
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fillRect(x, y, size, size);
                        if (init) {
                            int temp_x = x;
                            int temp_y = y;
                            int temp_size_x = size;
                            int temp_size_y = size;

                            //boolean rect = true;
                            boolean leftCorner = true;
                            boolean rightCorner = true;
                            boolean specialPointesUL = true;
                            boolean specialPointesUR = true;
                            boolean specialPointesDL = true;
                            boolean specialPointesDR = true;
                            if (horz > 0) {
                                if (arr[horz - 1][vert] != 2) {
                                    temp_y++;
                                    temp_size_y--;
                                    specialPointesUL = false;
                                    specialPointesUR = false;
                                }
                            }
                            if (vert > 0) {
                                if (arr[horz][vert - 1] != 2) {
                                    temp_x++;
                                    temp_size_x--;
                                    specialPointesUL = false;
                                    specialPointesDL = false;
                                } else {
                                    leftCorner = false;
                                }
                            }
                            if (vert < total_height - 1) {
                                if (arr[horz][vert + 1] != 2) {
                                    temp_size_x--;
                                    specialPointesUR = false;
                                    specialPointesDR = false;
                                } else {
                                    rightCorner = false;
                                }
                            }
                            if (horz < total_width - 1) {
                                if (arr[horz + 1][vert] != 2) {
                                    temp_size_y--;
                                    specialPointesDL = false;
                                    specialPointesDR = false;
                                } else {
                                    leftCorner = false;
                                    rightCorner = false;
                                }
                            }
                            if (leftCorner) {
                                if (horz < total_width - 1 && vert > 0) {
                                    if (arr[horz + 1][vert - 1] == 2) {
                                        SpecialRect r1 = new SpecialRect(new Rectangle2D.Double(x - size + 1, y + 1, size - 2, size - 2), new Line2D.Double(x, y + 1, x, y + size - 1), new Line2D.Double(x - size + 1, y + size, x - 1, y + size));
                                        SpecialRect r2 = new SpecialRect(new Rectangle2D.Double(x + 1, y + size + 1, size - 2, size - 2), new Line2D.Double(x, y + size + 1, x, y + size + size - 1), new Line2D.Double(x + 1, y + size, x + size - 1, y + size));
                                        specialRectList.add(new Pair(new Point2D.Double(x, y + size), new Pair(r1, r2)));
                                    }
                                }
                            }
                            if (rightCorner) {
                                if (horz < total_width - 1 && vert < total_height - 1) {
                                    if (arr[horz + 1][vert + 1] == 2) {
                                        SpecialRect r1 = new SpecialRect(new Rectangle2D.Double(x + size + 1, y + 1, size - 2, size - 2), new Line2D.Double(x + size, y + 1, x + size, y + size - 1), new Line2D.Double(x + size + 1, y + size, x + size + size - 1, y + size));
                                        SpecialRect r2 = new SpecialRect(new Rectangle2D.Double(x + 1, y + size + 1, size - 2, size - 2), new Line2D.Double(x + size, y + size + 1, x + size, y + size + size - 1), new Line2D.Double(x + 1, y + size, x + size - 1, y + size));
                                        specialRectList.add(new Pair(new Point2D.Double(x + size, y + size), new Pair(r1, r2)));
                                    }
                                }
                            }
                            if (specialPointesUL) {
                                if (horz > 0 && vert > 0) {
                                    if (arr[horz - 1][vert - 1] != 2) {
                                        //specialPoints.add(new Point2D.Double(x, y));

                                        Line2D l1 = new Line2D.Double(temp_x + 1, temp_y, temp_x + temp_size_x - 1, temp_y);
                                        Line2D l2 = new Line2D.Double(temp_x, temp_y + 1, temp_x, temp_y + temp_size_y - 1);
                                        if (!specialLines.contains(l1)) {
                                            specialLines.add(l1);
                                        }
                                        if (!specialLines.contains(l2)) {
                                            specialLines.add(l2);
                                        }
                                        temp_x++;
                                        temp_size_x--;
                                        temp_y++;
                                        temp_size_y--;
                                    }
                                }
                            }
                            if (specialPointesUR) {
                                if (horz > 0 && vert < total_height - 1) {
                                    if (arr[horz - 1][vert + 1] != 2) {
                                        //specialPoints.add(new Point2D.Double(x + 1, y));

                                        Line2D l1 = new Line2D.Double(temp_x, temp_y, temp_x + temp_size_x - 1, temp_y);
                                        Line2D l2 = new Line2D.Double(temp_x + temp_size_x, temp_y + 1, temp_x + temp_size_x, temp_y + temp_size_y - 1);
                                        if (!specialLines.contains(l1)) {
                                            specialLines.add(l1);
                                        }
                                        if (!specialLines.contains(l2)) {
                                            specialLines.add(l2);
                                        }
                                        //temp_x++;
                                        temp_size_x--;
                                        temp_y++;
                                        temp_size_y--;
                                    }
                                }
                            }
                            if (specialPointesDL) {
                                if (horz < total_width - 1 && vert > 0) {
                                    if (arr[horz + 1][vert - 1] != 2) {
                                        //specialPoints.add(new Point2D.Double(x, y + 1));

                                        Line2D l1 = new Line2D.Double(temp_x, temp_y, temp_x, temp_y + temp_size_y - 1);
                                        Line2D l2 = new Line2D.Double(temp_x + 1, temp_y + temp_size_y, temp_x + temp_size_x - 1, temp_y + temp_size_y);
                                        if (!specialLines.contains(l1)) {
                                            specialLines.add(l1);
                                        }
                                        if (!specialLines.contains(l2)) {
                                            specialLines.add(l2);
                                        }
                                        temp_x++;
                                        temp_size_x--;
                                        //temp_y++;
                                        temp_size_y--;
                                    }
                                }
                            }
                            if (specialPointesDR) {
                                if (horz < total_width - 1 && vert < total_height - 1) {
                                    if (arr[horz + 1][vert + 1] != 2) {
                                        //specialPoints.add(new Point2D.Double(x + 1, y + 1));

                                        Line2D l1 = new Line2D.Double(temp_x + temp_size_x, temp_y, temp_x + temp_size_x, temp_y + temp_size_y - 1);
                                        Line2D l2 = new Line2D.Double(temp_x, temp_y + temp_size_y, temp_x + temp_size_x - 1, temp_y + temp_size_y);
                                        if (!specialLines.contains(l1)) {
                                            specialLines.add(l1);
                                        }
                                        if (!specialLines.contains(l2)) {
                                            specialLines.add(l2);
                                        }
                                        //temp_x++;
                                        temp_size_x--;
                                        //temp_y++;
                                        temp_size_y--;
                                    }
                                }
                            }
                            rectList.add(new Rectangle(temp_x, temp_y, temp_size_x, temp_size_y));
                        }
                        break;
                }
                x += size;
            }
            y += size;
        }
        System.out.println("specialRectList = " + specialRectList.size());

        /*for (int l = 0; l < specialRectList.size(); l++) {
            //Point2D tempP = specialRectList.get(l).getKey();
            //g2d.fillOval((int)tempP.getX() - 3, (int)tempP.getY() - 3, size / 2, size / 2);
            SpecialRect r = specialRectList.get(l).getValue().getKey();
            g2d.setColor(Color.pink);
            g2d.draw(r.getRect());
            g2d.setColor(Color.red);
            g2d.draw(r.getL1());
            g2d.draw(r.getL2());
            r = specialRectList.get(l).getValue().getValue();
            g2d.setColor(Color.pink);
            g2d.draw(r.getRect());
            g2d.setColor(Color.red);
            g2d.draw(r.getL1());
            g2d.draw(r.getL2());
        }
        g2d.setColor(Color.CYAN);
        for (int p = 0; p < specialLines.size(); p++) {
            g2d.draw(specialLines.get(p));
        }
        if (privousSpecialRect != null) {
            SpecialRect r1 = privousSpecialRect.getValue().getKey();
            SpecialRect r2 = privousSpecialRect.getValue().getValue();

            g2d.setColor(Color.red);
            g2d.draw(r1.getRect());
            g2d.setColor(Color.GREEN);
            g2d.draw(r2.getRect());
        }*/
        drawPoint();
        // print optimal race path
        if (optB) {
            Pair start_pointP = optimalPathGrid.get(0);
            int prevous_xP = computePosition((int) start_pointP.getKey(), true);
            int prevous_yP = computePosition((int) start_pointP.getValue(), false);

            g2d.setColor(Color.BLUE);
            g2d.fillOval(prevous_xP - 3, prevous_yP - 3, size / 2, size / 2);
            for (int index = 1; index < optimalPathGrid.size(); index++) {
                Pair temp = optimalPathGrid.get(index);
                int temp_x = computePosition((int) temp.getKey(), true);
                int temp_y = computePosition((int) temp.getValue(), false);
                g2d.drawLine(prevous_xP, prevous_yP, temp_x, temp_y);
                g2d.fillOval(temp_x - 3, temp_y - 3, size / 2, size / 2);

                //System.out.println("(" + temp_x + "," + temp_y + ")");
                prevous_xP = temp_x;
                prevous_yP = temp_y;
            }
            //System.out.println("Point number: " + (optimalPathGrid.size() - 1));
        } else {
            if (computerPlayerB) {// computer player
                Pair start_pointP = computerPlayerLGF.get(0);
                int prevous_xP = computePosition((int) start_pointP.getKey(), true);
                int prevous_yP = computePosition((int) start_pointP.getValue(), false);

                g2d.setColor(Color.BLUE);
                g2d.fillOval(prevous_xP - 3, prevous_yP - 3, size / 2, size / 2);
                int computer_size = drivingPoints.size();
                if (!pressButton) {
                    computer_size--;
                }
                if (computer_size >= computerPlayerLGF.size()) {
                    computer_size = computerPlayerLGF.size();
                    //computerPlayerFinish = true;
                }
                for (int index = 1; index < computer_size; index++) {
                    Pair temp = computerPlayerLGF.get(index);
                    int temp_x = computePosition((int) temp.getKey(), true);
                    int temp_y = computePosition((int) temp.getValue(), false);
                    g2d.drawLine(prevous_xP, prevous_yP, temp_x, temp_y);
                    g2d.fillOval(temp_x - 3, temp_y - 3, size / 2, size / 2);

                    //System.out.println("(" + temp_x + "," + temp_y + ")");
                    prevous_xP = temp_x;
                    prevous_yP = temp_y;
                }
            }
            // User
            Pair start_point = drivingPoints.get(0);
            int prevous_x = (int) start_point.getKey();
            int prevous_y = (int) start_point.getValue();

            g2d.setColor(Color.red);
            g2d.fillOval(prevous_x - 3, prevous_y - 3, size / 2, size / 2);
            for (int index = 1; index < drivingPoints.size(); index++) {
                Pair temp = drivingPoints.get(index);
                int temp_x = (int) temp.getKey();
                int temp_y = (int) temp.getValue();
                g2d.drawLine(prevous_x, prevous_y, temp_x, temp_y);
                g2d.fillOval(temp_x - 3, temp_y - 3, size / 2, size / 2);

                prevous_x = temp_x;
                prevous_y = temp_y;

            }
            if (!pressButton && drivingPoints.size() > 1) {
                //drivingPointsLGF.removeLast();
                drivingPoints.removeLast();
            }
            pressButton = false;
        }
        /*
        //passed checkpoint line
        g2d.setColor(Color.BLUE);//checkpoint
        for (int i = 0; i < checkPointNo; i++) {
            g2d.draw(checkLineArr[i]);
        }
        //current checkpoint line
        g2d.setColor(Color.GREEN);//checkpoint
        g2d.draw(checkLineArr[checkPointNo]);
        // not passed checkpoint line
        g2d.setColor(Color.MAGENTA);//checkpoint
        for (int i = checkPointNo + 1; i < checkLineArr.length; i++) {
            g2d.draw(checkLineArr[i]);
        }*/
        
        // checkpoint line
        g2d.setStroke(new BasicStroke(3));
        for (int index = 0; index < checkLineArr.length; index++) {
            g2d.setColor(new Color(COLOR_ARR[index][0], COLOR_ARR[index][1], COLOR_ARR[index][2]));
            g2d.draw(checkLineArr[index]);
        }
        g2d.setColor(Color.CYAN);//finishline
        g2d.draw(finishLine);
        g2d.setStroke(new BasicStroke(1));

        System.out.println("Point number: " + drivingPoints.size());
        System.out.println();

        g2d.dispose();
    }
    private static int total_width = 40;
    private static int total_height = 40;
    //private String trackS = "";// track name

    private int[][] arr = new int[total_width + 1][total_height];// track data - barrier and track
    private ArrayList<Pair<Integer, Integer>> points = new ArrayList();// track data - start point, finish line, checkpoint line
    public LinkedList<Pair<Integer, Integer>> optimalPathGrid = new LinkedList();// Optimal path
    public LinkedList<Pair<Integer, Integer>> drivingPoints = new LinkedList();// User path
    public LinkedList<Pair<Integer, Integer>> computerPlayerLGF = new LinkedList();// Computer player Path
    public LinkedList<Pair<Integer, Integer>> drivingPointsLGF = new LinkedList();// User path - prepare for storing as Computer player path
    //private LinkedList<Move> computerPlayer = new LinkedList();
    private LinkedList<Rectangle> rectList = new LinkedList();// Barrier
    private LinkedList<Pair<Point2D, Pair<SpecialRect, SpecialRect>>> specialRectList = new LinkedList();
    private Pair<Point2D, Pair<SpecialRect, SpecialRect>> privousSpecialRect;
    //private LinkedList<Point2D> specialPoints = new LinkedList();
    private LinkedList<Line2D> specialLines = new LinkedList();
    private Line2D finishLine;
    //private Line2D checkLine;// current check line
    public Line2D checkLineArr[];

    private boolean init = true;
    public boolean crash = false;
    public boolean finishGame = false;
    public boolean pressButton = false;// [GameFrame] comfirm direction
    public boolean checkPointB = false;
    //private int checkPointNumber = 0;// total checkpoint line
    public int checkPointNo = 0;// current checkpoint line
    public boolean optB;// show the optimal solution (useless?)
    public boolean computerPlayerB = false;
    public boolean computerPlayerFinish = false;// computer player reach the finish line

    //public int start_x;
    //public int start_y;
    private int size;

    //public int prevous_x = start_x;
    //public int prevous_y = start_y;
    public int vector_x = 0;
    public int vector_y = 0;
    public int temp_vector_x;
    public int temp_vector_y;

    private int next_x;
    private int next_y;

    public int[][] COLOR_ARR = new int[7][3];

    void predictLine(String str) {
        temp_vector_x = vector_x;
        temp_vector_y = vector_y;

        switch (Integer.valueOf(str)) {
            case 0://NW
                temp_vector_x = vector_x - 1;
                temp_vector_y = vector_y - 1;
                break;
            case 1://N
                //temp_vector_x = vector_x ;
                temp_vector_y = vector_y - 1;
                break;
            case 2://NE
                temp_vector_x = vector_x + 1;
                temp_vector_y = vector_y - 1;
                break;
            case 3://W
                temp_vector_x = vector_x - 1;
                //temp_vector_y = vector_y ;
                break;
            case 4://X
                //temp_vector_x = vector_x;
                //temp_vector_y = vector_y;
                break;
            case 5://E
                temp_vector_x = vector_x + 1;
                //vector_y = vector_y ;
                break;
            case 6://SW
                temp_vector_x = vector_x - 1;
                temp_vector_y = vector_y + 1;
                break;
            case 7://S
                //temp_vector_x = vector_x;
                temp_vector_y = vector_y + 1;
                break;
            case 8://SE
                temp_vector_x = vector_x + 1;
                temp_vector_y = vector_y + 1;
                break;
        }
        Pair prevous = drivingPoints.getLast();
        next_x = (int) prevous.getKey() + size * temp_vector_x;
        next_y = (int) prevous.getValue() + size * temp_vector_y;
        if (checkPointB && checkPointNo < checkLineArr.length - 1) {
            checkPointNo++;
            checkPointB = false;
            System.out.println("Change Check Point Line!!! checkPointNo = " + checkPointNo);
        }
    }

    private void drawPoint() {
        if (init) {//first time
            drivingPointsLGF.add(new Pair(points.get(0).getKey(), points.get(0).getValue()));
            drivingPoints.add(new Pair(computePosition(points.get(0).getKey(), true), computePosition(points.get(0).getValue(), false)));
            //drivingPoints.add(new Pair(computePosition(arr[total_width][0], false), computePosition(arr[total_width][1], true)));
            init = false;
            System.out.println("Start (x,y) = (" + points.get(0).getKey() + "," + points.get(0).getValue() + ")");
            System.out.println("Start (x,y) = (" + computePosition(points.get(0).getKey(), true) + "," + computePosition(points.get(0).getValue(), false) + ")");

            finishLine = new Line2D.Double(computePosition(points.get(1).getKey(), true), computePosition(points.get(1).getValue(), false), computePosition(points.get(2).getKey(), true), computePosition(points.get(2).getValue(), false));
            //checkLine = new Line2D.Double(computePosition(points.get(3).getKey(), true), computePosition(points.get(3).getValue(), false), computePosition(points.get(4).getKey(), true), computePosition(points.get(4).getValue(), false));

            //checkLineArr = new Line2D[checkPointNumber];
            int index = 0;
            //System.out.println(" number = "+checkLineArr.length);
            for (int i = 0; i < checkLineArr.length; i++) {
                //System.out.println("chekcpointline = "+i+" "+(index+3)+" "+(index+4));
                checkLineArr[i] = new Line2D.Double(computePosition(points.get(3 + index).getKey(), true), computePosition(points.get(3 + index).getValue(), false), computePosition(points.get(4 + index).getKey(), true), computePosition(points.get(4 + index).getValue(), false));
                index += 2;
            }
        } else {
            //drivingPointsLGF.add(new Pair(convertPosition(next_x, true), convertPosition(next_y, false)));
            drivingPoints.add(new Pair(next_x, next_y));
        }
    }

    void confirmDirection() {
        //drivingPoints.add(new Pair(next_x, next_y));
        vector_x = temp_vector_x;
        vector_y = temp_vector_y;
        pressButton = true;
        Line2D line = new Line2D.Double(next_x - size * vector_x, next_y - size * vector_y, next_x, next_y);
        // check for crash
        for (int i = 0; i < rectList.size(); i++) {
            //"hit" is not working on Mac for no reason, but work on Linux.
            /*if (g2d.hit(rectList.get(i), line, false)) {
                    crash = true;
                    break;
                }*/
            if (rectList.get(i).intersectsLine(line)) {
                crash = true;
                System.out.println("crash here-(-1)");
                break;
            }
        }
        if (!crash) {
            for (int i = 0; i < specialLines.size(); i++) {
                if (line.intersectsLine(specialLines.get(i))) {
                    crash = true;
                    System.out.println("crash here-0");
                    break;
                }
            }
        }
        if (!crash && privousSpecialRect != null) {
            Point2D specialP = privousSpecialRect.getKey();
            SpecialRect r1 = privousSpecialRect.getValue().getKey();
            SpecialRect r2 = privousSpecialRect.getValue().getValue();
            if (r2.getRect().intersectsLine(line) || r2.getL1().intersectsLine(line) || r2.getL2().intersectsLine(line)) {
                crash = true;
            } else if (r1.getRect().intersectsLine(line) || r1.getL1().intersectsLine(line) || r1.getL2().intersectsLine(line)) {
                System.out.println("Stay");
            } else if ((line.getX1() == specialP.getX() && line.getY1() == specialP.getY()) || (line.getX2() == specialP.getX() && line.getY2() == specialP.getY())) {
                System.out.println("Stay--");
            } else {
                privousSpecialRect = null;
                System.out.println("Leave special rectangle");
            }
        }
        if (!crash) {
            for (int l = 0; l < specialRectList.size(); l++) {
                SpecialRect r1 = specialRectList.get(l).getValue().getKey();
                SpecialRect r2 = specialRectList.get(l).getValue().getValue();
                if (r1.getRect().intersectsLine(line) && r2.getRect().intersectsLine(line)) {
                    crash = true;
                } else if (r1.getL1().intersectsLine(line) && r2.getL1().intersectsLine(line)) {
                    crash = true;
                } else if (r1.getL2().intersectsLine(line) && r2.getL2().intersectsLine(line)) {
                    crash = true;
                } else if (r1.getRect().intersectsLine(line) || r1.getL1().intersectsLine(line) || r1.getL2().intersectsLine(line)) {
                    privousSpecialRect = new Pair(specialRectList.get(l).getKey(), new Pair(r1, r2));
                    System.out.println("(" + line.getX1() + "," + line.getY1() + ")(" + line.getX2() + "," + line.getY2() + ")");
                    System.out.println(r1.getL1().intersectsLine(line) + " (" + r1.getL1().getX1() + "," + r1.getL1().getY1() + ")(" + r1.getL1().getX2() + "," + r1.getL1().getY2() + ")");
                    System.out.println(r1.getL2().intersectsLine(line) + " (" + r1.getL2().getX1() + "," + r1.getL2().getY1() + ")(" + r1.getL2().getX2() + "," + r1.getL2().getY2() + ")");
                    System.out.println(r1.getRect().intersectsLine(line) + " (" + r1.getRect().getMinX() + "," + r1.getRect().getMinY() + "," + r1.getRect().getMaxX() + "," + r1.getRect().getMaxY() + ")");
                } else if (r2.getRect().intersectsLine(line) || r2.getL1().intersectsLine(line) || r2.getL2().intersectsLine(line)) {
                    privousSpecialRect = new Pair(specialRectList.get(l).getKey(), new Pair(r2, r1));
                    System.out.println("(" + line.getX1() + "," + line.getY1() + ")(" + line.getX2() + "," + line.getY2() + ")");
                    System.out.println(r2.getL1().intersectsLine(line) + " (" + r2.getL1().getX1() + "," + r2.getL1().getY1() + ")(" + r2.getL1().getX2() + "," + r2.getL1().getY2() + ")");
                    System.out.println(r2.getL2().intersectsLine(line) + " (" + r2.getL2().getX1() + "," + r2.getL2().getY1() + ")(" + r2.getL2().getX2() + "," + r2.getL2().getY2() + ")");
                    System.out.println(r2.getRect().intersectsLine(line) + " (" + r2.getRect().getMinX() + "," + r2.getRect().getMinY() + "," + r2.getRect().getMaxX() + "," + r2.getRect().getMaxY() + ")");

                }
            }
        }
        System.out.println("Check crash = " + crash);
        drivingPointsLGF.add(new Pair(convertPosition(next_x, true), convertPosition(next_y, false)));
        //test for finish game
        if (!checkPointB) {
            //checkPointB = checkLine.intersectsLine(line);
            checkPointB = checkLineArr[checkPointNo].intersectsLine(line);
        }
        finishGame = finishLine.intersectsLine(line);

        System.out.println("Check checkpoint = " + checkPointB + " Check finish = " + finishGame);
        if (computerPlayerB) {
            if (drivingPoints.size() + 1 >= computerPlayerLGF.size()) {
                computerPlayerFinish = true;
            }
            System.out.println("test - " + computerPlayerLGF.size() + " " + drivingPoints.size() + " " + computerPlayerFinish);
        }
        //System.out.println("Button is working.");
        //System.out.println("NEXT (x,y) = (" + next_x + "," + next_y + ")");
        //System.out.println("Vector (x,y) = (" + vector_x + "," + vector_y + ")");
    }

    private int computePosition(int i, Boolean flag) {
        if (flag) {//compute x
            i = (getWidth() - (size * total_width)) / 2 + size * i;
        } else {//compute y
            i = (getHeight() - (size * total_height)) / 2 + size * i;
        }
        return i;
    }

    private int convertPosition(int i, Boolean flag) {
        if (flag) {//compute x
            i = (i - (getWidth() - (size * total_width)) / 2) / size;
        } else {//compute y
            i = (i - (getHeight() - (size * total_height)) / 2) / size;
        }
        return i;
    }

    private void colorSetting() {
        System.out.println("Colour setting");
        COLOR_ARR[0][0] = 255;
        COLOR_ARR[0][1] = 0;
        COLOR_ARR[0][2] = 0;

        COLOR_ARR[1][0] = 255;
        COLOR_ARR[1][1] = 128;
        COLOR_ARR[1][2] = 0;

        COLOR_ARR[2][0] = 255;
        COLOR_ARR[2][1] = 255;
        COLOR_ARR[2][2] = 51;

        COLOR_ARR[3][0] = 0;
        COLOR_ARR[3][1] = 255;
        COLOR_ARR[3][2] = 0;

        COLOR_ARR[4][0] = 0;
        COLOR_ARR[4][1] = 0;
        COLOR_ARR[4][2] = 230;

        COLOR_ARR[5][0] = 75;
        COLOR_ARR[5][1] = 0;
        COLOR_ARR[5][2] = 130;

        COLOR_ARR[6][0] = 143;
        COLOR_ARR[6][1] = 0;
        COLOR_ARR[6][2] = 255;
    }
}
