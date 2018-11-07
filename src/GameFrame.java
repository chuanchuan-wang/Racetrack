
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chuan
 */
public class GameFrame extends javax.swing.JFrame {

    /**
     * Creates new form GameFrame
     */
    public GameFrame(String str, boolean computerPlayer, boolean hint, boolean optimal) throws IOException {
        File f = new File("cp-" + str + ".txt");
        if (f.exists()) {
            optPath = optimal;
        } else if (optimal) {
            JOptionPane.showMessageDialog(null, "Sorry. There is not available to present optimal race path for this track right now. Please try again later. ", "WARNING", JOptionPane.INFORMATION_MESSAGE);
            optPath = false;
            computerPlayer = false;
        }

        if (f.exists()) {
            computerPlayerGF = computerPlayer;
        } else if (computerPlayer) {
            JOptionPane.showMessageDialog(null, "Sorry. There is no computer player for this track right now. Please play by your own right now and try it later. ", "WARNING", JOptionPane.INFORMATION_MESSAGE);
            computerPlayerGF = false;
        }

        initComponents();
        //hint label
        hintGF = hint;
        hintNowLabel.setVisible(hintGF);
        hintNextLabel.setVisible(hintGF);
        if (hintGF) {
            ruleD = new RuleDialog(1);
            ruleD.setVisible(true);
        }

        trackS = str;
        //optPath = optimal;

        grid = new Grid(trackS, optPath, computerPlayerGF);
        GridPanel.add(grid);
        GridPanel.repaint();

        //checkpointColour[1].setText("111");
        //checkpointPanel colour
        for (int k = 0; k < 7; k++) {
            System.out.println("k = " + k);
            JLabel l = new JLabel();
            //l.setText(String.valueOf(k));
            l.setOpaque(true);
            l.setBackground(new Color(grid.COLOR_ARR[k][0], grid.COLOR_ARR[k][1], grid.COLOR_ARR[k][2]));
            l.setName(Integer.toString(k));
            if (k < grid.checkLineArr.length) {
                l.setVisible(true);
            } else {
                l.setVisible(false);
            }
            checkpointPanel.add(l);
        }
        if (!optPath) {
            // Add Listener and Event
            addMouseListener();
            GridPanel.setFocusable(true);
            hint(false);
            keyDispatcher = new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent evt) {
                    if (evt.getID() == KeyEvent.KEY_RELEASED) {
                        controlByKey(evt);
                        return false;
                    } else {
                        return true;
                    }
                }
            };
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyDispatcher);
        } else {
            comfirm.setEnabled(false);
            timeLabel.setText("Time: " + (grid.optimalPathGrid.size() - 1));
        }
    }

    private void controlByKey(KeyEvent evt) {
        String str = "";
        switch (evt.getKeyCode()) {
            case VK_Q:
                str = "0";
                break;
            case VK_W:
                str = "1";
                break;
            case VK_E:
                str = "2";
                break;
            case VK_A:
                str = "3";
                break;
            case VK_S:
                str = "4";
                break;
            case VK_D:
                str = "5";
                break;
            case VK_Z:
                str = "6";
                break;
            case VK_X:
                str = "7";
                break;
            case VK_C:
                str = "8";
                break;
            case VK_SPACE:
                str = "9";
                break;
            case VK_ENTER:
                str = "9";
                break;
            default:
                str = "10";
        }
        if (Integer.valueOf(str) < 9) {//0-8
            grid.predictLine(str);
            GridPanel.repaint();
            selectedDirection = true;
            hint(false);
        } else if (!selectedDirection && str.equals("9")) {
            hint(true);
        } else if (selectedDirection && str.equals("9")) {//Enter
            grid.confirmDirection();
            try {
                check();
            } catch (IOException ex) {
                //Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            GridPanel.repaint();
            selectedDirection = false;
            time++;
            hint(false);
        } else {
            System.out.println("Don't press meaningless keyborad.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        backB = new javax.swing.JButton();
        emptyLabel = new javax.swing.JLabel();
        checkpointLabel = new javax.swing.JLabel();
        checkpointPanel = new javax.swing.JPanel();
        timeLabel = new javax.swing.JLabel();
        emptyLabel1 = new javax.swing.JLabel();
        ruleButton = new javax.swing.JButton();
        emptyPanel = new javax.swing.JPanel();
        emptyPanel1 = new javax.swing.JPanel();
        GridPanel = new javax.swing.JPanel();
        choosePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comfirm = new javax.swing.JButton();
        hintNowLabel = new javax.swing.JLabel();
        hintNextLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game");
        setPreferredSize(new java.awt.Dimension(725, 550));
        setResizable(false);

        jToolBar1.setRollover(true);

        backB.setText("↩");
        backB.setFocusable(false);
        backB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        backB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBMouseClicked(evt);
            }
        });
        jToolBar1.add(backB);

        emptyLabel.setText("       ");
        jToolBar1.add(emptyLabel);

        checkpointLabel.setText("Checkpoint: ");
        jToolBar1.add(checkpointLabel);

        checkpointPanel.setLayout(new java.awt.GridLayout(1, 7));
        jToolBar1.add(checkpointPanel);

        timeLabel.setText("Time: 0");
        jToolBar1.add(timeLabel);

        emptyLabel1.setText("       ");
        jToolBar1.add(emptyLabel1);

        ruleButton.setText("Rule");
        ruleButton.setFocusable(false);
        ruleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ruleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ruleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruleButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(ruleButton);

        emptyPanel.setLayout(new java.awt.GridLayout(1, 7));
        jToolBar1.add(emptyPanel);

        emptyPanel1.setLayout(new java.awt.GridLayout(1, 7));
        jToolBar1.add(emptyPanel1);

        GridPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        GridPanel.setLayout(new java.awt.BorderLayout());

        choosePanel.setLayout(new java.awt.GridLayout(3, 3, 10, 20));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("↖");
        choosePanel.add(jLabel1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("↑");
        choosePanel.add(jLabel2);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("↗");
        choosePanel.add(jLabel3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("←");
        choosePanel.add(jLabel4);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("x");
        choosePanel.add(jLabel5);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("→");
        choosePanel.add(jLabel6);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("↙");
        choosePanel.add(jLabel7);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("↓");
        choosePanel.add(jLabel8);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("↘");
        choosePanel.add(jLabel9);

        comfirm.setText("MOVE");
        comfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(comfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(hintNowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(choosePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(hintNextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(choosePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(comfirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hintNowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hintNextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(GridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBMouseClicked
        // TODO add your handling code here:
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);
        RemoveAllListener();

        TrackFrame trackF;
        try {
            trackF = new TrackFrame(false, computerPlayerGF, hintGF);
            trackF.setVisible(true);
        } catch (IOException ex) {
            //Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ruleD != null) {
            ruleD.dispose();
        }
        this.dispose();
    }//GEN-LAST:event_backBMouseClicked

    private void ruleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruleButtonActionPerformed
        // TODO add your handling code here:
        if (ruleD == null) {
            ruleD = new RuleDialog(1);
            ruleD.setVisible(true);
        } else {
            if (ruleD.isShowing()) {
                ruleD.setVisible(false);
            } else {
                ruleD.setVisible(true);
            }
        }
    }//GEN-LAST:event_ruleButtonActionPerformed

    private void comfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comfirmActionPerformed
        // TODO add your handling code here:
        if (selectedDirection) {
            grid.confirmDirection();
            try {
                check();
            } catch (IOException ex) {
                //Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            GridPanel.repaint();
            selectedDirection = false;
            time++;
            hint(false);
        } else {
            hint(true);
        }
    }//GEN-LAST:event_comfirmActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*try {
                    //new GameFrame("").setVisible(true);
                } catch (IOException ex) {
                    //LoggerchoosePanelger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GridPanel;
    private javax.swing.JButton backB;
    private javax.swing.JLabel checkpointLabel;
    private javax.swing.JPanel checkpointPanel;
    private javax.swing.JPanel choosePanel;
    private javax.swing.JButton comfirm;
    private javax.swing.JLabel emptyLabel;
    private javax.swing.JLabel emptyLabel1;
    private javax.swing.JPanel emptyPanel;
    private javax.swing.JPanel emptyPanel1;
    private javax.swing.JLabel hintNextLabel;
    private javax.swing.JLabel hintNowLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton ruleButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
    private ButtonGroup group = new ButtonGroup();
    private Grid grid;
    private KeyEventDispatcher keyDispatcher;
    //private JLabel[] checkpointColour = new JLabel[7];
    private boolean selectedDirection = false;
    private boolean optPath = false;
    private boolean hintGF = false;
    private boolean computerPlayerGF = false;
    private String trackS;
    private int time = 0;
    private int checkPointNoGF = 0;

    private RuleDialog ruleD = null;

    private void addMouseListener() {
        Component[] compArray = choosePanel.getComponents();
        for (int i = 0; i < compArray.length; i++) {
            //String str = compArray[i].getName();
            compArray[i].setName(Integer.toString(i));
            compArray[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addMouse(evt);
                }
            });

        }

    }

    private void addMouse(MouseEvent evt) {
        JLabel label = (JLabel) evt.getSource();
        grid.predictLine(label.getName());
        GridPanel.repaint();
        selectedDirection = true;
        GridPanel.setFocusable(true);
        hint(false);
    }

    private void check() throws IOException {
        if (grid.crash) {//user crash
            timeLabel.setText("Time: " + (time + 1));
            GridPanel.repaint();

            // Remove Action Listener
            RemoveAllListener();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);

            JOptionPane.showMessageDialog(rootPane, "Game Over", "WARNING", JOptionPane.INFORMATION_MESSAGE);
            //this.add(new ScoreDialog(null, 0,grid.scoreList));
            ScoreBoradDialog scoreD = new ScoreBoradDialog(this, true, null, -1, trackS, grid.optimalPathGrid.size() - 1, -1, computerPlayerGF, hintGF);
            scoreD.setVisible(true);
        } else if (grid.finishGame && grid.checkPointB) {//user win or tie
            timeLabel.setText("Time: " + (time + 1));
            GridPanel.repaint();

            String str = "";
            int tieOrWin = 0;//reach the goal without computer player
            if (grid.computerPlayerFinish && computerPlayerGF) {
                updateComputerPlayer();
                str = "Tie";
                tieOrWin = 1;
            } else if (computerPlayerGF) {
                updateComputerPlayer();
                str = "You win!";
                tieOrWin = 2;
            }
            System.out.println(str);

            // Remove Action Listener
            RemoveAllListener();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);

            JOptionPane.showMessageDialog(rootPane, "Finish Game! " + str, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            String name = JOptionPane.showInputDialog(null, "Player Name: ");
            System.out.println(name + " Score = " + (grid.drivingPoints.size()));

            ScoreBoradDialog scoreD = new ScoreBoradDialog(this, true, name, grid.drivingPoints.size() - 1, trackS, grid.optimalPathGrid.size() - 1, tieOrWin, computerPlayerGF, hintGF);
            scoreD.setVisible(true);
            //scorePanel.setPreferredSize(500,500);
        } else if (grid.computerPlayerFinish) {//computer player win
            timeLabel.setText("Time: " + (time + 1));
            GridPanel.repaint();

            // Remove Action Listener
            RemoveAllListener();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);

            JOptionPane.showMessageDialog(rootPane, "You lost!", "Result", JOptionPane.INFORMATION_MESSAGE);
            //this.add(new ScoreDialog(null, 0,grid.scoreList));
            ScoreBoradDialog scoreD = new ScoreBoradDialog(this, true, null, -1, trackS, grid.optimalPathGrid.size() - 1, 3, computerPlayerGF, hintGF);
            scoreD.setVisible(true);
        } else if (grid.checkPointB) {
            //||checkPointNoGF < grid.checkPointNo
            Component[] compArr = checkpointPanel.getComponents();
            for (int i = checkPointNoGF; i <= grid.checkPointNo; i++) {
                JLabel l = (JLabel) compArr[i];
                l.setText("✔");
                l.setForeground(Color.WHITE);
                l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            }
            checkPointNoGF = grid.checkPointNo;
        }
    }

    private void RemoveAllListener() {
        Component[] compArrayP = choosePanel.getComponents();
        for (int i = 0; i < compArrayP.length; i++) {
            MouseListener[] mouseL = compArrayP[i].getMouseListeners();
            for (int j = 0; j < mouseL.length; j++) {
                compArrayP[i].removeMouseListener(mouseL[j]);
            }
        }
        MouseListener[] mouseL = comfirm.getMouseListeners();
        for (int j = 0; j < mouseL.length; j++) {
            comfirm.removeMouseListener(mouseL[j]);
        }
        if (ruleD != null) {
            ruleD.dispose();
        }
    }

    private void hint(boolean warning) {
        String txt = "Now: ";
        if (grid.vector_x >= 0) {
            txt += "vector x: " + grid.vector_x + " → ";
        } else {
            txt += "vector x: " + (grid.vector_x * (-1)) + " ← ";
        }
        if (grid.vector_y > 0) {
            txt += " ,vector y: " + grid.vector_y + " ↓ ";
        } else {
            txt += " ,vector y: " + (grid.vector_y * (-1)) + " ↑ ";
        }
        //txt += "Time:" + time;
        hintNowLabel.setText(txt);
        timeLabel.setText("Time: " + time);
        txt = "";
        if (warning) {
            txt = "Please select the next dirction!!!";
        } else if (selectedDirection) {
            txt = "Next: ";
            if (grid.temp_vector_x >= 0) {
                txt += "vector x: " + grid.temp_vector_x + " → ";
            } else {
                txt += "vector x: " + (grid.temp_vector_x * (-1)) + " ← ";
            }
            if (grid.temp_vector_y > 0) {
                txt += " ,vector y: " + grid.temp_vector_y + " ↓ ";
            } else {
                txt += " ,vector y: " + (grid.temp_vector_y * (-1)) + " ↑ ";
            }
        }
        hintNextLabel.setText(txt);
    }

    private void updateComputerPlayer() throws IOException {
        String cpStr = "cp-" + trackS + ".txt";
        try {
            FileReader oldcpData = new FileReader(cpStr);
            FileWriter fw = new FileWriter("M" + cpStr, false);
            BufferedWriter bw = new BufferedWriter(fw);

            try {
                Scanner parser = new Scanner(oldcpData);
                String pathNumber = (parser.nextLine());
                int n = Integer.valueOf(pathNumber);
                int k = n;
                if (n == 100) {
                    Random rand = new Random();
                    k = rand.nextInt(n);
                } else {
                    n++;
                    k++;
                }
                System.out.println("Modified " + k);
                bw.write(String.valueOf(n));
                n = 0;
                String line = "";
                while (parser.hasNext()) {
                    line = parser.nextLine();
                    if (k == n) {
                        line = "";
                        for (int i = 0; i < grid.drivingPointsLGF.size() - 1; i++) {
                            line += grid.drivingPointsLGF.get(i).getKey() + " " + grid.drivingPointsLGF.get(i).getValue() + " ";
                        }
                        line += grid.drivingPointsLGF.getLast().getKey() + " " + grid.drivingPointsLGF.getLast().getValue();
                    }
                    bw.newLine();
                    bw.write(line);
                    n++;
                    //System.out.println(n + " " + k);
                }
                n++;
                if (k == n) {
                    line = "";
                    for (int i = 0; i < grid.drivingPointsLGF.size() - 1; i++) {
                        line += grid.drivingPointsLGF.get(i).getKey() + " " + grid.drivingPointsLGF.get(i).getValue() + " ";
                    }
                    line += grid.drivingPointsLGF.getLast().getKey() + " " + grid.drivingPointsLGF.getLast().getValue();
                    bw.newLine();
                    bw.write(line);
                }
                bw.close();
                fw.close();
                System.out.println("(Modified) Written Done.---create amd write: M" + cpStr);
            } finally {
                oldcpData.close();
            }
            File oldFile = new File(cpStr);
            /*if (oldFile.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }*/
            File newFile = new File("M" + cpStr);
            if (newFile.renameTo(oldFile)) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*private void restartGame() throws IOException {
        // TODO add your handling code here:
        GameFrame newgameF = new GameFrame(trackS, computerPlayerGF, hintGF);
        newgameF.setVisible(true);
        if (ruleD != null) {
            ruleD.dispose();
        }
        this.dispose();
    }*/
}
