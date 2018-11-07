
import java.awt.Component;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.util.Pair;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chuan
 */
public class TrackFrame extends javax.swing.JFrame {

    private void addActionListenerToAll(JPanel parent) {
        Component[] compArray = parent.getComponents();
        int level = Integer.valueOf(parent.getName());
        for (int i = 0; i < compArray.length; i++) {
            //String str = compArray[i].getName();
            String str = parent.getName() + "-" + i;
            compArray[i].setName(str);
            // Add image
            if (level != 3) {
                JPanel p = (JPanel) compArray[i];
                Component[] pArray = p.getComponents();
                Icon image = new ImageIcon(getClass().getResource("TrackImage/" + str + ".png"));
                JLabel l = (JLabel) pArray[0];
                l.setIcon(image);
                System.out.println("Set image " + str);
            }
            // Mouse Listener for each tracks
            if ((level == 3 && i <= customiseTrack) || level != 3) {
                compArray[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        try {
                            addMouseClicked(evt, str);
                        } catch (IOException ex) {
                        }
                    }
                });
                System.out.print("Listener: " + str + " - ");
            }
            System.out.println(str);

        }
        System.out.println("Add Listener :" + parent.getName());
        if (prevous_level != -1) {
            parent = (JPanel) jTabbedPane.getComponent(prevous_level);
            Component[] compArrayP = parent.getComponents();
            for (int i = 0; i < compArrayP.length; i++) {
                MouseListener[] mouseL = compArrayP[i].getMouseListeners();
                for (int j = 0; j < mouseL.length; j++) {
                    compArrayP[i].removeMouseListener(mouseL[j]);
                }
            }
            System.out.println("Remove Listener :" + prevous_level);
        }
        prevous_level = level;
    }

    private void addMouseClicked(MouseEvent evt, String str) throws IOException {
        GameFrame gameF;
        /*int input = JOptionPane.showConfirmDialog(null, "Do you want to see optimal path?", "Optimal Path", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            gameF = new GameFrame(str,true);
        } else {
            gameF = new GameFrame(str,false);
        }*/
        gameF = new GameFrame(str, computerPlayerTF, hintTF,optimalLabel.getState());
        gameF.setVisible(true);
        if (ruleD != null) {
            ruleD.dispose();
        }
        this.dispose();
    }

    /**
     * Creates new form TrackFrame
     */
    public TrackFrame(boolean init, boolean computerPlayer, boolean hint) throws IOException {
        computerPlayerTF = computerPlayer;
        hintTF = hint;
        initComponents();
        computerPlayerY.setSelected(computerPlayerTF);
        hintLabel.setState(hintTF);
        optimalLabel.setState(false);

        createTrackB.setVisible(false);
        deleteTrackB.setVisible(false);

        if (hintTF) {
            ruleD = new RuleDialog(0);
            ruleD.setVisible(true);
        }

        //boolean vacancyTrack = false;
        //boolean zeroTrack = true;
        
        // check whether computer player exists or not
        // if not, run the algorithm to create.
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel();

            String str = "3-" + i;
            File f = new File(str + ".txt");
            if (f.exists()) {
                customiseTrack = i;
                try {
                    FileReader trackName = new FileReader(str + ".txt");
                    try {
                        Scanner parser = new Scanner(trackName);
                        String trackStr = parser.nextLine();
                        l.setText(trackStr);
                        l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        //customisedTrack[i] = true;
                        customisedTrackName[i] = trackStr;
                        //zeroTrack = false;
                    } finally {
                        trackName.close();
                    }
                } catch (FileNotFoundException ex) {
                    //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                }
                File cpF = new File("cp-" + str + ".txt");
                if (!cpF.exists() && init) {
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            System.out.println("Run in thread!");
                            try {
                                computeComputerPlayer(str + ".txt");
                            } catch (IOException ex) {
                                //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    thread.start();
                }
            } else {
                //customisedTrack[i] = false;
                customisedTrackName[i] = "";
                //vacancyTrack = true;
            }
            customisedPanel.add(l);
        }
        if (customiseTrack == -1) {// No track
            deleteTrackB.setEnabled(false);
        } else if (customiseTrack == 5) {// 6 tracks - full
            createTrackB.setEnabled(false);
        }
        //System.out.println("Vacancy for new Track = " + vacancyTrack + "; No track = " + zeroTrack);

        /*if (!vacancyTrack) {
            createTrackB.setEnabled(false);
        }
        if (zeroTrack) {
            deleteTrackB.setEnabled(false);
        }
        for (int level = 0; level < 4; level++) {
            jTabbedPane.getSelectedComponent().setName(Integer.toString(level));
            addActionListenerToAll((JPanel) jTabbedPane.getSelectedComponent());
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        easyPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        mediumPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        hardPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        customisedPanel = new javax.swing.JPanel();
        createTrackB = new javax.swing.JButton();
        deleteTrackB = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        ruleMenuButton = new javax.swing.JMenuItem();
        exitMenuButton = new javax.swing.JMenuItem();
        cpMenu = new javax.swing.JMenu();
        computerPlayerY = new javax.swing.JRadioButtonMenuItem();
        computerPlayerN = new javax.swing.JRadioButtonMenuItem();
        hintMenu = new javax.swing.JMenu();
        hintLabel = new javax.swing.JCheckBoxMenuItem();
        optimalLabel = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RaceTrack");
        setResizable(false);

        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        easyPanel.setPreferredSize(new java.awt.Dimension(500, 400));
        easyPanel.setLayout(new java.awt.GridLayout(1, 3));

        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setSize(new java.awt.Dimension(50, 50));
        jPanel1.add(jLabel1);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("0-0");
        jPanel1.add(jLabel11);

        easyPanel.add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel2);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("0-1");
        jPanel2.add(jLabel10);

        easyPanel.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel3);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0-2");
        jPanel3.add(jLabel12);

        easyPanel.add(jPanel3);

        jTabbedPane.addTab("Easy", easyPanel);

        mediumPanel.setLayout(new java.awt.GridLayout(1, 3));

        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel4);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("1-0");
        jPanel4.add(jLabel13);

        mediumPanel.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(2, 0));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel5);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("1-1");
        jPanel5.add(jLabel14);

        mediumPanel.add(jPanel5);

        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel6.add(jLabel6);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("1-2");
        jPanel6.add(jLabel15);

        mediumPanel.add(jPanel6);

        jTabbedPane.addTab("Medium", mediumPanel);

        hardPanel.setLayout(new java.awt.GridLayout(1, 3));

        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel7);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("2-0");
        jPanel7.add(jLabel16);

        hardPanel.add(jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(2, 0));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel8);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("2-1");
        jPanel8.add(jLabel17);

        hardPanel.add(jPanel8);

        jPanel9.setLayout(new java.awt.GridLayout(2, 0));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel9);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("2-2");
        jPanel9.add(jLabel18);

        hardPanel.add(jPanel9);

        jTabbedPane.addTab("Hard", hardPanel);

        customisedPanel.setLayout(new java.awt.GridLayout(2, 3));
        jTabbedPane.addTab("Customised", customisedPanel);

        createTrackB.setText("Create");
        createTrackB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTrackBActionPerformed(evt);
            }
        });

        deleteTrackB.setText("Delete");
        deleteTrackB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTrackBActionPerformed(evt);
            }
        });

        gameMenu.setText("Game");

        ruleMenuButton.setText("Rule");
        ruleMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruleMenuButtonActionPerformed(evt);
            }
        });
        gameMenu.add(ruleMenuButton);

        exitMenuButton.setText("Exit");
        exitMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuButtonActionPerformed(evt);
            }
        });
        gameMenu.add(exitMenuButton);

        jMenuBar1.add(gameMenu);

        cpMenu.setText("Computer Player");

        computerPlayerY.setText("Yes");
        group.add(computerPlayerY);
        computerPlayerY.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                computerPlayerYItemStateChanged(evt);
            }
        });
        cpMenu.add(computerPlayerY);

        computerPlayerN.setSelected(true);
        computerPlayerN.setText("No");
        group.add(computerPlayerN);
        cpMenu.add(computerPlayerN);

        jMenuBar1.add(cpMenu);

        hintMenu.setText("Help");

        hintLabel.setText("Guide");
        hintLabel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hintLabelItemStateChanged(evt);
            }
        });
        hintMenu.add(hintLabel);

        optimalLabel.setText("Optimal Race Path");
        optimalLabel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optimalLabelItemStateChanged(evt);
            }
        });
        hintMenu.add(optimalLabel);

        jMenuBar1.add(hintMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(490, Short.MAX_VALUE)
                        .addComponent(deleteTrackB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createTrackB))
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createTrackB)
                    .addComponent(deleteTrackB))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
        // TODO add your handling code here:
        jTabbedPane = (JTabbedPane) evt.getSource();
        System.out.println("Select pane :" + jTabbedPane.getSelectedIndex());
        if (jTabbedPane.getSelectedIndex() == 3) {
            createTrackB.setVisible(true);
            deleteTrackB.setVisible(true);
        } else {
            createTrackB.setVisible(false);
            deleteTrackB.setVisible(false);
        }

        String level = Integer.toString(jTabbedPane.getSelectedIndex());
        jTabbedPane.getSelectedComponent().setName(level);
        addActionListenerToAll((JPanel) jTabbedPane.getSelectedComponent());

    }//GEN-LAST:event_jTabbedPaneStateChanged

    private void createTrackBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTrackBActionPerformed
        // TODO add your handling code here:
        if (customiseTrack < 5) {
            DesignTrack designT = new DesignTrack(customiseTrack + 1, customisedTrackName, computerPlayerTF, hintTF);
            designT.setVisible(true);
            this.dispose();
            if (ruleD != null) {
                ruleD.dispose();
            }
        }
    }//GEN-LAST:event_createTrackBActionPerformed

    private void deleteTrackBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTrackBActionPerformed
        // TODO add your handling code here:
        if (customiseTrack >= 0) {
            String name = JOptionPane.showInputDialog(null, "Entre the track name that you want to delete:", "Customised Track", 0);
            if (name != null) {
                boolean wrongName = true;
                int rename = customiseTrack + 1;
                Component[] comp = customisedPanel.getComponents();
                for (int i = 0; i <= customiseTrack; i++) {
                    if (customisedTrackName[i].equals(name)) {
                        wrongName = false;
                        String str = "3-" + i + ".txt";
                        File f = new File(str);
                        f.delete();
                        File cpF = new File("cp-" + str);
                        if (cpF.exists()) {
                            cpF.delete();
                        }
                        File scoreF = new File("score-" + str);
                        if (scoreF.exists()) {
                            scoreF.delete();
                        }
                        //customisedTrack[i] = false;
                        customisedTrackName[i] = "";
                        //customisedPanel.remove(comp[j]);
                        createTrackB.setEnabled(true);
                        if (customiseTrack == 0) {
                            deleteTrackB.setEnabled(false);
                        }
                        rename = i;
                        System.out.println(customiseTrack - 1);
                    }
                    if (rename <= customiseTrack - 1) {
                        System.out.println("Rename: 3-"+i + "<- 3-" + (i + 1));
                        String emptyStr = "3-" + i + ".txt";
                        String dataStr = "3-" + (i + 1) + ".txt";
                        File emptyF = new File(emptyStr);
                        File dataF = new File(dataStr);
                        System.out.print(dataStr);
                        if (dataF.renameTo(emptyF)) {
                            System.out.println("File deleted successfully");
                        } else {
                            System.out.println("Failed to delete the file");
                        }

                        emptyF = new File("cp-" + emptyStr);
                        dataF = new File("cp-" + dataStr);
                        if (dataF.exists()) {
                            System.out.print("cp-" + dataStr);
                            if (dataF.renameTo(emptyF)) {
                                System.out.println("File deleted successfully");
                            } else {
                                System.out.println("Failed to delete the file");
                            }
                        }
                        emptyF = new File("score-" + emptyStr);
                        dataF = new File("score-" + dataStr);
                        if (dataF.exists()) {
                            System.out.print("score-" + dataStr);
                            if (dataF.renameTo(emptyF)) {
                                System.out.println("File deleted successfully");
                            } else {
                                System.out.println("Failed to delete the file");
                            }
                        }
                        customisedTrackName[i] = customisedTrackName[i + 1];
                        rename++;
                    }
                }
                if (wrongName) {
                    JOptionPane.showMessageDialog(rootPane, "Cannot find it!! Check the correct name.", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    customisedTrackName[customiseTrack] = "";
                    customiseTrack--;
                    refreshCustomisedTrack();
                    customisedPanel.updateUI();
                }
            }
        }
    }//GEN-LAST:event_deleteTrackBActionPerformed

    private void exitMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if (ruleD != null) {
            ruleD.dispose();
        }
    }//GEN-LAST:event_exitMenuButtonActionPerformed

    private void hintLabelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hintLabelItemStateChanged
        // TODO add your handling code here:
        //evt.getStateChange() == 1 selected// 2 deselected
        if (evt.getStateChange() == 1) {
            hintTF = true;
        } else {
            hintTF = false;
        }
        System.out.println("Hint selected:" + hintTF);
    }//GEN-LAST:event_hintLabelItemStateChanged

    private void computerPlayerYItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_computerPlayerYItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == 1) {
            computerPlayerTF = true;
        } else {
            computerPlayerTF = false;
        }
        System.out.println("yes changed!!!!" + evt.getStateChange() + " yes = " + computerPlayerTF);
    }//GEN-LAST:event_computerPlayerYItemStateChanged

    private void ruleMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruleMenuButtonActionPerformed
        // TODO add your handling code here:
        if (ruleD == null) {
            ruleD = new RuleDialog(0);
            ruleD.setVisible(true);
        } else {
            if (ruleD.isShowing()) {
                ruleD.setVisible(false);
            } else {
                ruleD.setVisible(true);
            }
        }
    }//GEN-LAST:event_ruleMenuButtonActionPerformed

    private void optimalLabelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optimalLabelItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optimalLabelItemStateChanged

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
            java.util.logging.Logger.getLogger(TrackFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrackFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrackFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrackFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*try {
                    new TrackFrame().setVisible(true);
                } catch (IOException ex) {
                    //Logger.getLogger(TrackFrame.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButtonMenuItem computerPlayerN;
    private javax.swing.JRadioButtonMenuItem computerPlayerY;
    private javax.swing.JMenu cpMenu;
    private javax.swing.JButton createTrackB;
    private javax.swing.JPanel customisedPanel;
    private javax.swing.JButton deleteTrackB;
    private javax.swing.JPanel easyPanel;
    private javax.swing.JMenuItem exitMenuButton;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JPanel hardPanel;
    private javax.swing.JCheckBoxMenuItem hintLabel;
    private javax.swing.JMenu hintMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JPanel mediumPanel;
    private javax.swing.JCheckBoxMenuItem optimalLabel;
    private javax.swing.JMenuItem ruleMenuButton;
    // End of variables declaration//GEN-END:variables
    private ButtonGroup group = new ButtonGroup();
    private int prevous_level = -1;
    private int customiseTrack = -1;
    //private boolean hint = false;
    //private boolean computerPlayer = false;
    //private boolean customisedTrack[] = new boolean[6];
    private String customisedTrackName[] = new String[6];
    private boolean computerPlayerTF;
    private boolean hintTF;

    private RuleDialog ruleD = null;

    private void refreshCustomisedTrack() {
        Component[] compArr = customisedPanel.getComponents();
        for (int i = 0; i < 6; i++) {
            JLabel l = (JLabel) compArr[i];
            l.setText(customisedTrackName[i]);
            if (i > customiseTrack) {
                MouseListener[] mouseL = l.getMouseListeners();
                for (int j = 0; j < mouseL.length; j++) {
                    l.removeMouseListener(mouseL[j]);
                }
                System.out.println("Remove Listener :" + i);
            }
        }
    }

    private void computeComputerPlayer(String strName) throws IOException {
        int total_width = 40;
        int total_height = 40;
        int arr[][] = new int[total_width][total_height];
        ArrayList<Pair<Integer, Integer>> points = new ArrayList();
        int checkPointNumber = 0;
        String trackStr = "";
        // Get the infromation of track
        try {
            FileReader trackData = new FileReader(strName);
            try {
                Scanner parser = new Scanner(trackData);
                trackStr = parser.nextLine();
                System.out.println(trackStr);
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
                    points.add(new Pair(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1])));
                }
                System.out.println("Points:" + points.size());
                String line = parser.nextLine();
                //int checkPointNumber = 0;
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
                System.out.println("Points:" + points.size());

            } finally {
                trackData.close();
            }
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }
        // run the algorithm
        boolean version = true;
        OptimalPath optimalP = new OptimalPath(arr, points, total_width, total_height, checkPointNumber, version);
        if (optimalP.optimalPathStr.isEmpty()) {
            version = false;
            optimalP = new OptimalPath(arr, points, total_width, total_height, checkPointNumber, version);
        }
        if (optimalP.optimalPathStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sorry. The program could not find any route from start point and pass through all checkpoint line in order and reach the finish line!", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String str = "";
            boolean deleteAlready = false;
            for (int k = 0; k < 6; k++) {
                str = "3-" + k + ".txt";
                File f = new File(str);
                if (f.exists()) {
                    // Make sure the track is the same one
                    try {
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
                                for (int i = 0; i < points.size(); i++) {
                                    dataStr = parser.nextLine();
                                    String point = "";
                                    point = points.get(i).getKey() + " " + points.get(i).getValue();
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
                // write the file for computer player
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
            }
        }
    }
}
