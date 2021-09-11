package com.hit.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.*;


/**
 * the UI
 */
public class CacheUnitScreen extends JPanel implements ActionListener {


    private static long serialVersionUID = 1L;
    private PropertyChangeSupport listener;
    private JPanel container;
    private JPanel panel;
    private JPanel panel2;
    private JButton loadButton;
    private JButton statisticButton;
    private JButton clearButton;
    protected JTextArea textArea;
    private ButtonListener buttonListener;
    private JScrollPane scroll;

    public CacheUnitScreen(PropertyChangeSupport listener) {

        /**
         * @param listener - listen to changes to be updated
         */
        super();
        this.listener = listener;
        initializeUI();		//Initialize the panel
    }

    //Return the panel
    public JPanel getPanel() {
        return container;
    }

    /**
     * initialize the panel with Features
     */
    public void initializeUI() {

        // Initialize swing components
        container = new JPanel();
        panel = new JPanel();
        panel2 = new JPanel();
        loadButton = new JButton("Load a Request", new ImageIcon("src/main/resources/icons/loading.png"));
        loadButton.setPreferredSize(new Dimension(200,40));
        loadButton.setBackground(Color.white);
        statisticButton = new JButton("Show Statistics", new ImageIcon("src/main/resources/icons/analytics.png"));
        statisticButton.setPreferredSize(new Dimension(200,40));
        statisticButton.setBackground(Color.white);
        clearButton = new JButton(new ImageIcon("src/main/resources/icons/eraser.png"));
        clearButton.setPreferredSize(new Dimension(40,40));
        clearButton.setBackground(Color.white);
        buttonListener = new ButtonListener();
        textArea = new JTextArea(25, 40);
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Components settings
        textArea.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        textArea.setAlignmentX(CENTER_ALIGNMENT);
        textArea.setAlignmentY(CENTER_ALIGNMENT);
        textArea.setEditable(false);
        // Add swing components to content pane
        panel.add(loadButton);
        panel.add(statisticButton);
        panel.add(clearButton);
        panel.setBackground(new Color(201,243,254));
        panel2.add(scroll);
        panel2.setBackground(new Color(74,217,253));
        container.add(panel);
        container.add(panel2);
        container.setBackground(new Color(201,243,254));
        // Add listeners
        loadButton.addActionListener(buttonListener);
        statisticButton.addActionListener(buttonListener);
        clearButton.addActionListener(buttonListener);
    }


    /**
     *  Set button
     *
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "Load a Request" : 	//if Load a Request Pressed
                {
                    String filePath = null;
                    JFileChooser chooser = new JFileChooser("resources/json_files.");	//Choose Json file
                    int status = chooser.showOpenDialog(null);
                    if(status != JFileChooser.APPROVE_OPTION) {	//Check if chosen File or cancel
                        textArea.setText("No file selected");
                    }
                    else {
                        File file = chooser.getSelectedFile();
                        filePath = file.getAbsolutePath();
                        try {
                            Scanner scan = new Scanner(new FileReader(filePath));	//read the file
                            String json = scan.next();
                            while(scan.hasNext()) {		//Convert the Json file to String
                                json += (String) scan.next();

                            }
                            listener.firePropertyChange(null, null, json);	//Send the Json String to ClientObserver
                            scan.close();	//close the scanner
                        }
                        catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                }

                case "Show Statistics" :	//if Show Statistics Pressed
                {
                    String stats = "stat";
                    listener.firePropertyChange(null, null, stats);	//Send the Statistics to ClientObserver
                    break;
                }
                case "" :	//if eraser is pressed
                    textArea.setText("");	//clean the Text Screen
            }

        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}