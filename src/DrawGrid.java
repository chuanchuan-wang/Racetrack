
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.util.Pair;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class DrawGrid extends JPanel {

    public DrawGrid(String[] customiseTrackName/*DesignTrack d*/) {
        System.out.println("DrawGrid!");
        customiseTrackNameArr = customiseTrackName;
        //designT = d;
        /*COLOR_ARR[0][0] = 255;
        COLOR_ARR[0][1] = 111;
        COLOR_ARR[0][2] = 255;

        COLOR_ARR[1][0] = 255;
        COLOR_ARR[1][1] = 179;
        COLOR_ARR[1][2] = 191;*/
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

    @Override
    protected void paintComponent(Graphics g) {
        //System.out.println("PaintGrid");
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
                /*if (init && horz != 0 && vert != 0) {
                    rectList.add(new Rectangle(x, y, size, size));
                    System.out.println("add " + rectList.size());
                }*/
                switch (arr[horz][vert]) {
                    case 0://finish line - white
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(x, y, size, size);
                        break;
                    case 1://finish line - black
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(x, y, size, size);
                        break;
                    case 2://barriers//rectBarrierList
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.fillRect(x, y, size, size);
                        /*if (init) {
                            rectBarrierList.add(new Rectangle(x - 1, y - 1, size + 2, size + 2));
                        }*/
                        break;
                    case 3://track
                    /*if (init) {
                            rectTrackList.add(new Rectangle(x - 1, y - 1, size + 2, size + 2));
                        }*/
                }
                x += size;
            }
            y += size;
        }
        //System.out.println("Rect:" + rectList.size() + " " + rectBarrierList.size() + " " + rectTrackList.size());
        //init = false;
        //startLegal = false;
        //finishLegal = false;
        //checkpointLegal = true;

        //Green is not comfirm color
        g2d.setColor(Color.GREEN);
        if (gridPanelDragged) {// not comfirm line
            g2d.fillOval((int) tempLine.getX1() - 3, (int) tempLine.getY1() - 3, size / 2, size / 2);
            g2d.fillOval((int) tempLine.getX2() - 3, (int) tempLine.getY2() - 3, size / 2, size / 2);
            g2d.draw(tempLine);
        }
        if (tempPointExist) {// not comfirm point - start point
            g2d.fillOval(tempPoint.x - 3, tempPoint.y - 3, size / 2, size / 2);
        }
        //Rainbow colour for comfirmed checkpointLines 
        g2d.setStroke(new BasicStroke(5));
        for (int index = 0; index < checkpointLine.size(); index++) {
            g2d.setColor(new Color(COLOR_ARR[index][0], COLOR_ARR[index][1], COLOR_ARR[index][2]));
            g2d.draw(checkpointLine.get(index));
            /*if (!checkTrack(checkpointLine.get(index))) {
                checkpointLegal = false;
            }*/
        }
        /*if (checkpointLine.isEmpty()) {
            checkpointLegal = false;
        }*/
        //PINK is selected checkpointLine color
        if (selectCheckpointLineBoolean) {
            g2d.setColor(Color.PINK);
            g2d.draw(selectCheckpointLine);
        }
        //CYAN is comfirmed finishLine color
        if (finishLineExist) {
            g2d.setColor(Color.CYAN);
            g2d.draw(finishLine);
            //finishLegal = checkTrack(finishLine);
        }
        g2d.setStroke(new BasicStroke(1));
        //Red is comfirmed startpoint color
        if (startPointExist) {
            //startPoint = tempPoint;
            g2d.setColor(Color.red);
            g2d.fillOval(startPoint.x - 3, startPoint.y - 3, size / 2, size / 2);
            //startLegal = checkTrack(startPoint);
        }

        if (trackChanged) {
            trackChanged = false;
        }
        //Blue is for drawing.
        g2d.setColor(Color.blue);
        if (pointList.size() == 1) {
            g2d.fillOval(pointList.getFirst().x - 3, pointList.getFirst().y - 3, size / 2, size / 2);
        } else {
            g2d.setStroke(new BasicStroke(3));
            for (int i = 0; i < pointList.size() - 1; i++) {
                Line2D l = new Line2D.Double(pointList.get(i), pointList.get(i + 1));
                g2d.draw(l);
            }
        }
        g2d.dispose();
        //designT.finishButtonEnable();
        //designT.checkStatus();
    }

    public int computePosition(int i, Boolean flag) {
        if (flag) {//compute x
            i = (i - (getWidth() - (size * total_width)) / 2) / size;
        } else {//compute y
            i = (i - (getHeight() - (size * total_height)) / 2) / size;
        }
        return i;
    }

    public boolean checkTrack(Point p) {
        for (int i = 0; i < rectTrackList.size(); i++) {
            Rectangle rect = rectTrackList.get(i);
            if (rect.contains(p)) {
                return true;
                /*System.out.println("Start point:" + p.x + " " + p.y);
                System.out.println(rect.x + " " + rect.y + " " + (rect.x + rect.width) + " " + (rect.y + rect.height));
                System.out.println();
                break;*/
            }
        }
        return false;
    }

    public boolean checkTrack(Line2D l) {
        //boolean finish = false;
        for (int i = 0; i < rectTrackList.size(); i++) {
            Rectangle rect = rectTrackList.get(i);
            if (rect.intersectsLine(l)) {
                return true;
                //break;
                //System.out.println("FinishLine: " + finishLine.getX1() + " " + finishLine.getY1() + " " + finishLine.getX2() + " " + finishLine.getY2());
                //System.out.println(rect.x + " " + rect.y + " " + (rect.x + rect.width) + " " + (rect.y + rect.height));
                //System.out.println();
            }
        }
        return false;
    }

    private final static int total_width = 40;
    private final static int total_height = 40;
    private int size;
    private boolean init = true;

    //private DesignTrack designT;
    public int arr[][] = new int[total_width][total_height];
    public boolean trackChanged = true;
    public boolean gridPanelDragged = false;
    public boolean selectCheckpointLineBoolean = false;
    public boolean tempPointExist = false;
    public boolean startPointExist = false;
    public boolean finishLineExist = false;
    public boolean drawStart = false;
    public boolean drawProgress = false;
    public boolean drawCrash = false;
    private String[] customiseTrackNameArr = new String[6];
    // rectangle
    public LinkedList<Rectangle> rectList = new LinkedList();
    private LinkedList<Rectangle> rectTrackList = new LinkedList();
    private LinkedList<Rectangle> rectBarrierList = new LinkedList();
    // decided point and line
    public Point startPoint;
    public Line2D finishLine;
    public LinkedList<Line2D> checkpointLine = new LinkedList();
    public Line2D selectCheckpointLine;

    // temp Point and Line
    public Point tempPoint;
    public Line2D tempLine;
    // colour setting for checkpoint line
    private int[][] COLOR_ARR = new int[7][3];

    //public boolean startLegal;
    //public boolean finishLegal;
    //public boolean checkpointLegal;
    public boolean drawLegal = false;//  draw success - true
    public boolean createFail = true;

    public LinkedList<Point> pointList = new LinkedList();// point for drawing
    public int drawCheckpointLineNumber = 0;
    private String trackStr = "";

    public String creatNewTrack(int trackNo) throws IOException {
        ArrayList<Pair<Integer, Integer>> p = new ArrayList();
        p.add(new Pair(computePosition(startPoint.x, true), computePosition(startPoint.y, false)));
        p.add(new Pair(computePosition((int) finishLine.getX1(), true), computePosition((int) finishLine.getY1(), false)));
        p.add(new Pair(computePosition((int) finishLine.getX2(), true), computePosition((int) finishLine.getY2(), false)));
        for (int i = 0; i < checkpointLine.size(); i++) {
            Line2D l = checkpointLine.get(i);
            p.add(new Pair(computePosition((int) l.getX1(), true), computePosition((int) l.getY1(), false)));
            p.add(new Pair(computePosition((int) l.getX2(), true), computePosition((int) l.getY2(), false)));
        }
        /*boolean version = false;
        OptimalPath optimalP = new OptimalPath(designT.arr, p, total_width, total_height, checkpointLine.size(), version);

        if (optimalP.optimalPathStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No route from start point and pass through all checkpoint line in order and reach the finish line!", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {*/
        String name = "";
        while (true) {
            boolean vaildName = true;
            name = JOptionPane.showInputDialog(null, "Track Name:", "Customised Track", 0);
            if (name == null) {
                break;
            } else if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Please entre track name!", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                vaildName = false;
            } else {
                for (int i = 0; i < 6; i++) {
                    if (customiseTrackNameArr[i].equals(name)) {
                        JOptionPane.showMessageDialog(null, "The same name of track exists!", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                        vaildName = false;
                    }
                }
            }
            if (vaildName) {
                break;
            }
        }
        if (name != null) {
            createFail = false;
            trackStr = name;
            String str = "3-" + trackNo + ".txt";
            FileWriter fw = new FileWriter(str, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(name);
            bw.newLine();
            //track
            for (int i = 0; i < total_width; i++) {
                String track = "";
                for (int j = 0; j < total_height; j++) {
                    switch (arr[i][j]) {
                        case 2:
                            track += "*";
                            break;
                        case 3:
                            track += " ";
                            break;
                    };
                }
                bw.write(track);
                bw.newLine();
            }
            for (int i = 0; i < p.size(); i++) {
                bw.write(p.get(i).getKey() + " " + p.get(i).getValue());
                bw.newLine();
            }
            bw.write("optimalpath");
            /*String strArr[] = optimalP.optimalPathStr.getFirst().split(" ");
                for (int i = 0; i < strArr.length; i += 2) {
                    bw.newLine();
                    bw.write(strArr[i] + " " + strArr[i + 1]);
                }*/
            bw.close();
            fw.close();
            System.out.println("Written Done.---create amd write: " + str);

            /*str = "cp-" + str;
                FileWriter cp_fw = new FileWriter(str, false);
                BufferedWriter cp_bw = new BufferedWriter(cp_fw);
                cp_bw.write(String.valueOf(optimalP.optimalPathStr.size()));
                for (int i = 0; i < optimalP.optimalPathStr.size(); i++) {
                    cp_bw.newLine();
                    cp_bw.write(optimalP.optimalPathStr.get(i));
                }
                cp_bw.close();
                cp_fw.close();
                System.out.println("Written Done.---create amd write: " + str);
                createFail = false;
            }*/
        }
        return name;
    }

    public void drawPath(Point p) {
        drawCrash = false;
        Point previousP = pointList.getLast();
        pointList.add(p);
        Line2D l = new Line2D.Double(previousP, p);
        for (int i = 0; i < rectBarrierList.size(); i++) {
            if (rectBarrierList.get(i).intersectsLine(l)) {
                JOptionPane.showMessageDialog(null, "Crash!! Please try again!", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                drawCrash = true;
                resetDrawPath();
                break;
            }
        }
        if (!pointList.isEmpty()) {
            if (drawCheckpointLineNumber == checkpointLine.size()) {
                if (finishLine.intersectsLine(l)) {
                    System.out.println("Pass finish line!!!");
                    drawLegal = true;
                    drawStart = false;
                    drawProgress = false;
                    //designT.checkStatus();
                }
            } else if (checkpointLine.get(drawCheckpointLineNumber).intersectsLine(l)) {
                System.out.println("Pass " + drawCheckpointLineNumber + " checkpoint Line!");
                drawCheckpointLineNumber++;
            }
        }
    }

    void creatNewTrackCP() throws IOException {
        ArrayList<Pair<Integer, Integer>> p = new ArrayList();
        p.add(new Pair(computePosition(startPoint.x, true), computePosition(startPoint.y, false)));
        p.add(new Pair(computePosition((int) finishLine.getX1(), true), computePosition((int) finishLine.getY1(), false)));
        p.add(new Pair(computePosition((int) finishLine.getX2(), true), computePosition((int) finishLine.getY2(), false)));
        for (int i = 0; i < checkpointLine.size(); i++) {
            Line2D l = checkpointLine.get(i);
            p.add(new Pair(computePosition((int) l.getX1(), true), computePosition((int) l.getY1(), false)));
            p.add(new Pair(computePosition((int) l.getX2(), true), computePosition((int) l.getY2(), false)));
        }
        boolean version = true;
        OptimalPath optimalP = new OptimalPath(arr, p, total_width, total_height, checkpointLine.size(), version);
        if (optimalP.optimalPathStr.isEmpty()) {
            version = false;
            optimalP = new OptimalPath(arr, p, total_width, total_height, checkpointLine.size(), version);
        }
        if (optimalP.optimalPathStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sorry. THe program could not find any route from start point and pass through all checkpoint line in order and reach the finish line!", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String str = "";
            boolean deleteAlready = false;
            for (int k = 0; k < 6; k++) {
                str = "3-" + k + ".txt";
                File f = new File(str);
                if (f.exists()) {
                    try {
                        // Check the track is still the same one
                        FileReader trackName = new FileReader(str);
                        try {
                            Scanner parser = new Scanner(trackName);
                            String dataStr = parser.nextLine();
                            if (dataStr.equals(trackStr)) {
                                for (int i = 0; i < total_width; i++) {
                                    dataStr = parser.nextLine();
                                    String track = "";
                                    for (int j = 0; j < total_height; j++) {
                                        switch (arr[i][j]) {
                                            case 2:
                                                track += "*";
                                                break;
                                            case 3:
                                                track += " ";
                                                break;
                                        };
                                    }
                                    if (!track.equals(dataStr)) {
                                        deleteAlready = true;
                                        break;
                                    }
                                }
                                for (int i = 0; i < p.size(); i++) {
                                    dataStr = parser.nextLine();
                                    String point = "";
                                    point = p.get(i).getKey() + " " + p.get(i).getValue();
                                    if (!point.equals(dataStr)) {
                                        deleteAlready = true;
                                        break;
                                    }
                                }
                                System.out.println("Track is different!!!");
                                break;
                            }
                        } finally {
                            trackName.close();
                        }
                    } catch (FileNotFoundException ex) {
                    }
                } else {
                    deleteAlready = true;
                    break;
                }
            }
            System.out.println("track name: " + trackStr + " txt: " + str);
            //String str = "3-" + trackNo + ".txt";
            //str = "cp-" + str;
            if (!deleteAlready) {

                FileWriter cp_fw = new FileWriter("cp-" + str, false);
                BufferedWriter cp_bw = new BufferedWriter(cp_fw);
                cp_bw.write(String.valueOf(optimalP.optimalPathStr.size()));
                for (int i = 0; i < optimalP.optimalPathStr.size(); i++) {
                    cp_bw.newLine();
                    cp_bw.write(optimalP.optimalPathStr.get(i));
                }
                cp_bw.close();
                cp_fw.close();
                System.out.println("Written Done.---create amd write: cp-" + str);

                FileWriter fw = new FileWriter(str, true);
                BufferedWriter bw = new BufferedWriter(fw);
                String strArr[] = optimalP.optimalPathStr.getFirst().split(" ");
                for (int i = 0; i < strArr.length; i += 2) {
                    bw.newLine();
                    bw.write(strArr[i] + " " + strArr[i + 1]);
                }
                bw.close();
                fw.close();
                System.out.println("Written Done.---append optimal path: " + str);

                createFail = false;
            }
        }
    }

    public void generateRectangleList() {
        System.out.println("generateRectangleList");
        size = Math.min(getWidth() - 4, getHeight() - 4) / 40;

        if (trackChanged) {
            rectTrackList.clear();
            rectBarrierList.clear();
            int y = (getHeight() - size * total_height) / 2;
            //drawing track part
            for (int horz = 0; horz < total_width; horz++) {
                int x = (getWidth() - (size * total_width)) / 2;
                for (int vert = 0; vert < total_height; vert++) {
                    if (init && horz != 0 && vert != 0) {
                        rectList.add(new Rectangle(x, y, size, size));
                    }
                    switch (arr[horz][vert]) {
                        /*case 0://finish line - white
                            break;
                        case 1://finish line - black
                            break;*/
                        case 2://barriers//rectBarrierList
                            rectBarrierList.add(new Rectangle(x - 1, y - 1, size + 2, size + 2));
                            break;
                        case 3://track
                            rectTrackList.add(new Rectangle(x - 1, y - 1, size + 2, size + 2));
                    }
                    x += size;
                }
                y += size;
            }
        }
        init = false;
        //System.out.println("(G)Rect:" + rectList.size() + " " + rectBarrierList.size() + " " + rectTrackList.size());
    }

    public void resetDrawPath() {
        pointList.clear();
        drawLegal = false;
        drawCheckpointLineNumber = 0;
        drawStart = false;
        drawProgress = false;
    }
}
