package za.ac.cput.marathoneventregistration.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import za.ac.cput.marathoneventregistration.dao.RaceDao;
import za.ac.cput.marathoneventregistration.domain.Race;

/**
 *
 * @author Leonard
 */
public class MarathonEventGui extends JFrame {

    private JPanel panelNorth, panelNorth1, panelSouth, panelCenter, panelCenter1, panelCenter2, panelCenter3, panelCenter4, panelCenter5;
    private JLabel lblImage, lblHeading, lblRaceCode, lblFirstName, lblLastName, lblRaceType, lblClub;
    private JTextField txtRaceCode, txtFirstName, txtLastName;
    private JComboBox combBox;
    private JRadioButton btnYes, btnNo;
    private JButton btnSave, btnReset, btnExit;
    private ButtonGroup group;
    private ImageIcon imageIcon;
    private Race race;
    private RaceDao dao;

    public MarathonEventGui() {
        super("Marathon Event Registration App");
        panelNorth = new JPanel();
        panelNorth1 = new JPanel();
        panelNorth1.setBackground(Color.blue);
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        panelCenter1 = new JPanel();
        panelCenter2 = new JPanel();
        panelCenter3 = new JPanel();
        panelCenter4 = new JPanel();
        panelCenter5 = new JPanel();
        group = new ButtonGroup();

        lblImage = new JLabel(imageIcon = new ImageIcon(new ImageIcon("duke.running.gif").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        lblHeading = new JLabel("Marathon Event Registration");
        lblHeading.setForeground(Color.YELLOW);
        lblRaceCode = new JLabel("Race Code:");
        lblFirstName = new JLabel("First Name:");
        lblLastName = new JLabel("Last Name:");
        lblRaceType = new JLabel("Race Type:");
        lblClub = new JLabel("Do you belong to a club?:");

        txtRaceCode = new JTextField(20);
        txtFirstName = new JTextField(20);
        txtLastName = new JTextField(20);

        combBox = new JComboBox();

        btnYes = new JRadioButton("Yes");
        btnNo = new JRadioButton("No");

        btnSave = new JButton("Save");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");

        dao = new RaceDao();
    }

    public void setGui() {
        panelNorth.setLayout(new GridLayout(1, 1));
        panelNorth1.setLayout(new FlowLayout());
        panelSouth.setLayout(new GridLayout(1, 3));
        panelCenter.setLayout(new GridLayout(5, 1));
        panelCenter1.setLayout(new FlowLayout());
        panelCenter2.setLayout(new FlowLayout());
        panelCenter3.setLayout(new FlowLayout());
        panelCenter4.setLayout(new FlowLayout());
        panelCenter5.setLayout(new FlowLayout());

        panelNorth1.add(lblImage);
        panelNorth1.add(lblHeading);
        panelNorth.add(panelNorth1);

        panelSouth.add(btnSave);
        panelSouth.add(btnReset);
        panelSouth.add(btnExit);

        combBox = new JComboBox(new String[]{"Ultra Marathon", "Full Marathon", "Half Marathon"});

        panelCenter1.add(lblRaceCode);
        panelCenter1.add(txtRaceCode);
        panelCenter2.add(lblFirstName);
        panelCenter2.add(txtFirstName);
        panelCenter3.add(lblLastName);
        panelCenter3.add(txtLastName);
        panelCenter4.add(lblRaceType);
        panelCenter4.add(combBox);
        panelCenter5.add(lblClub);
        panelCenter5.add(btnYes);
        panelCenter5.add(btnNo);

        panelCenter.add(panelCenter1);
        panelCenter.add(panelCenter2);
        panelCenter.add(panelCenter3);
        panelCenter.add(panelCenter4);
        panelCenter.add(panelCenter5);

        group.add(btnYes);
        group.add(btnNo);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelCenter, BorderLayout.CENTER);

//        combBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) 
//                {
//                    String raceType = (String) e.getItem();
//                }
//            }
//
//        });
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (e.getSource() == btnSave) {

                    String raceCode = txtRaceCode.getText();
                    String firstName = txtFirstName.getText();
                    String lastName = txtLastName.getText();
                    String raceType = (String) combBox.getSelectedItem();
                    boolean club = btnYes.isSelected();
                    
                    
                    Race rac = new Race(raceCode, firstName, lastName, raceType, club);
                    dao.save(rac);
                    
                    txtRaceCode.setText("");
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    combBox.setSelectedIndex(0);
                    group.clearSelection();
                   
                }
            }
        });
      
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                   if (e.getSource() == btnReset) 
                   {
                    txtRaceCode.setText("");
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    combBox.setSelectedIndex(0);
                    group.clearSelection();
                }
            }
        }
        );

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                if (e.getSource() == btnExit) {
                    System.exit(0);
                }
            }
        }
        );
    }
}
