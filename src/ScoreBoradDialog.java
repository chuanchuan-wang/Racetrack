/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chuan
 */
public class ScoreBoradDialog extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    /**
     * Creates new form ScoreBoradDialog
     */
    public ScoreBoradDialog(java.awt.Frame parent, boolean modal, String name, int score, String trackString, int optNumber, int computerPlayerFinish,boolean computerPlayer, boolean hint) throws IOException {
        super(parent, modal);
        gameF = parent;
        trackS = trackString;
        computerPlayerSB = computerPlayer;
        hintSB = hint;

        String str = "score-" + trackString + ".txt";
        boolean change = false;
        boolean breakRecord = false;
        boolean scoreboradFull = true;
        boolean scoreboradEmpty = false;

        //read scoreborad from file
        try {
            FileReader scoreData = new FileReader(str);
            try {
                Scanner parser = new Scanner(scoreData);
                int scoreboradNumber = 0;
                while (parser.hasNext()) {
                    String line = parser.nextLine();
                    String[] strArr = line.split(",");
                    if (score == -1 || name == null || name.equals("")) {//crash or didn't type name
                        scoreList.add(new Pair(Integer.valueOf(strArr[0]), strArr[1]));
                        scoreboradNumber = 10;
                    } else if (!breakRecord && score <= Integer.valueOf(strArr[0])) {
                        scoreList.add(new Pair(score, name));
                        scoreList.add(new Pair(Integer.valueOf(strArr[0]), strArr[1]));
                        change = true;
                        breakRecord = true;
                        scoreboradNumber++;
                    } else {
                        scoreList.add(new Pair(Integer.valueOf(strArr[0]), strArr[1]));
                    }
                    scoreboradNumber++;
                }
                System.out.println("scoreboradNumber = " + scoreboradNumber);
                if (scoreboradNumber < 10) {
                    scoreboradFull = false;
                    if (!breakRecord) {
                        scoreList.add(new Pair(score, name));
                        change = true;
                        breakRecord = true;
                    }
                }
            } finally {
                scoreData.close();
            }
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("score = " + score + " ,name = " + name);
            if (score != -1 && name != null && !name.equals("")) {
                breakRecord = true;
                createFile(str, score, name);
            } else {
                scoreboradEmpty = true;
            }
        }
        //write scoreborad back to file if there is any change
        if (change) {
            if (breakRecord && scoreboradFull) {
                System.out.println("Remove");
                scoreList.remove(scoreList.get(scoreList.size() - 1));
            }
            FileWriter fw = new FileWriter(str, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < scoreList.size(); i++) {
                bw.write(scoreList.get(i).getKey() + "," + scoreList.get(i).getValue() + "\n");
            }
            bw.close();
            fw.close();
            System.out.println("score-" + trackString + "- Written Done.");
        }

        //init table content
        String[] columnName = new String[]{"Rank", "Name", "Time"};
        Object[][] rowData = {};
        listTableModel = new DefaultTableModel(rowData, columnName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < scoreList.size(); i++) {
            listTableModel.addRow(new Object[]{(i + 1), scoreList.get(i).getValue(), scoreList.get(i).getKey()});
        }

        initComponents();
        setTitle("ScoreBorad");
        //
        String infoStr = "";
        if (optNumber > 0) {
            infoStr = "Optimal Time: " + optNumber + "<br/>";
        }
        if (scoreboradEmpty) {
            if (computerPlayerFinish == -1) {
                infoStr += "You crashed. :( <br/> Scoreborad is still empty!";
            } else {
                infoStr += "You lost. :( <br/> Scoreborad is still empty!";
            }
            this.scoreTable.setVisible(false);
        } else if (score == -1) {
            if (computerPlayerFinish == -1) {
                infoStr += "You crashed. :(";
            } else {
                infoStr += "You lost.";
            }
        } else if (name == null || name.equals("")) {
            if (computerPlayerFinish == 1) {//tie
                infoStr += "Tie!!<br/>";
            } else {//win
                infoStr += "You win!!<br/>";
            }
            infoStr += "Your score is " + score + " ,but you didn't enter your name. <br/> Therefore, it won't be on the score borad.";
        } else if (breakRecord) {
            if (computerPlayerFinish == 1) {//tie
                infoStr += "Tie!!<br/>";
            } else {//win
                infoStr += "You win!!<br/>";
            }
            infoStr += "You break the record!! :)";
        } else {
            if (computerPlayerFinish == 1) {//tie
                infoStr += "Tie!!<br/>";
            } else {//win
                infoStr += "You win!!<br/>";
            }
            infoStr += "You finished the game, but you didn't break the record. <br/>And your score is " + score;
        }
        this.informationLabel.setText("<html>" + infoStr + "</html>");

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.dispose();
                doClose(RET_CANCEL);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playAgainB = new javax.swing.JButton();
        changeTrackB = new javax.swing.JButton();
        exitB = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        scoreTable = new javax.swing.JTable();
        informationLabel = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        playAgainB.setText("Play Again");
        playAgainB.setMaximumSize(new java.awt.Dimension(130, 29));
        playAgainB.setPreferredSize(new java.awt.Dimension(200, 30));
        playAgainB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playAgainBMouseClicked(evt);
            }
        });

        changeTrackB.setText("Change Track");
        changeTrackB.setPreferredSize(new java.awt.Dimension(200, 30));
        changeTrackB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeTrackBMouseClicked(evt);
            }
        });

        exitB.setText("Exit");
        exitB.setPreferredSize(new java.awt.Dimension(200, 30));
        exitB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitBMouseClicked(evt);
            }
        });

        scoreTable.setModel(listTableModel);
        jScrollPane1.setViewportView(scoreTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(informationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(exitB, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changeTrackB, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(playAgainB, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(informationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playAgainB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeTrackB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(playAgainB);
        getRootPane().setDefaultButton(playAgainB);
        getRootPane().setDefaultButton(playAgainB);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void playAgainBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playAgainBMouseClicked
        // TODO add your handling code here:
        try {
            GameFrame newgameF = new GameFrame(trackS,computerPlayerSB,hintSB,false);
            newgameF.setVisible(true);
        } catch (IOException ex) {
            //Logger.getLogger(ScoreBoradDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
        gameF.dispose();
    }//GEN-LAST:event_playAgainBMouseClicked

    private void changeTrackBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeTrackBMouseClicked
        // TODO add your handling code here:
        TrackFrame trackF;
        try {
            trackF = new TrackFrame(false,computerPlayerSB, hintSB);
            trackF.setVisible(true);

        } catch (IOException ex) {
            //Logger.getLogger(ScoreBoradDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
        gameF.dispose();
    }//GEN-LAST:event_changeTrackBMouseClicked

    private void exitBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBMouseClicked
        // TODO add your handling code here:
        //dispose();
        gameF.dispose();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_exitBMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        gameF.dispose();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_formWindowClosed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        //dispose();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeTrackB;
    private javax.swing.JButton exitB;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton playAgainB;
    private javax.swing.JTable scoreTable;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
    private DefaultTableModel listTableModel;
    private ArrayList<Pair<Integer, String>> scoreList = new ArrayList();
    private Frame gameF;
    private String trackS;
    private boolean computerPlayerSB;
    private boolean hintSB;

    private void createFile(String str, int score, String name) throws IOException {
        scoreList.add(new Pair(score, name));

        FileWriter fw = new FileWriter(str, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(score + "," + name + "\n");
        bw.close();
        fw.close();
        System.out.println("Written Done.---create amd write "+str);
    }
}
