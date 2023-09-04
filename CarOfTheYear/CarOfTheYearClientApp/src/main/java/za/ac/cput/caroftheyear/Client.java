
package za.ac.cput.caroftheyear;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Leonard
 */

public class Client  extends JFrame implements ActionListener {
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;

    private JPanel panelNorth;
    private JPanel panelCenter, panelCenter1, panelCenter2;
    private JPanel panelSouth;
    
    //private JLabel lblLogo;
    private JLabel lblHeading;
            
    private JLabel lblCarFinalists;
    private JComboBox cboCarFinalists;
    
    private JButton btnVote, btnRetrieve, btnExit;
    private Font ft1, ft2, ft3;
    
    private static JTextArea recordsTextArea;

    
    public Client() {
        super("Car of the Year Voting App");
        
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelCenter1 = new JPanel();
        panelCenter2 = new JPanel();
        panelSouth = new JPanel();
        
        lblHeading = new JLabel("Vote for your Car of the Year");

        lblCarFinalists = new JLabel("Finalists: ");
        cboCarFinalists = new JComboBox<>(new String[]{"Ford Ranger", "Audi A3", "BMW X3", "Toyota Starlet", "Suzuki Swift" });
        
        btnVote = new JButton("Vote");
        btnRetrieve = new JButton("Retrieve");
        btnExit = new JButton("Exit");
        
        ft1 = new Font("Arial", Font.BOLD, 32);
        ft2 = new Font("Arial", Font.PLAIN, 22);
        ft3 = new Font("Arial", Font.PLAIN, 24);
        connectToServer();
    }
    
    public void setGUI() {
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(2, 1));
        panelSouth.setLayout(new GridLayout(1, 3));
        panelCenter1.setLayout(new FlowLayout());
        panelCenter2.setLayout(new FlowLayout());
        
        //panelNorth.add(lblLogo);
        panelNorth.add(lblHeading);
        lblHeading.setFont(ft1);
        lblHeading.setForeground(Color.yellow);
        panelNorth.setBackground(new Color(0, 106, 255));
        
        lblCarFinalists.setFont(ft2);
        lblCarFinalists.setHorizontalAlignment(JLabel.RIGHT);
        cboCarFinalists.setFont(ft2);
        recordsTextArea = new JTextArea(10,40);
        recordsTextArea.setEditable(false);
        panelCenter1.add(lblCarFinalists);
        panelCenter1.add(cboCarFinalists);
        panelCenter2.add(new JScrollPane(recordsTextArea), BorderLayout.SOUTH);       
        panelCenter2.setBackground(new Color(36, 145, 255));
        
        panelCenter.add(panelCenter1);
        panelCenter.add(panelCenter2);
        
        btnVote.setFont(ft3);
        btnRetrieve.setFont(ft3);
        btnExit.setFont(ft3);
        panelSouth.add(btnVote);
        panelSouth.add(btnRetrieve);
        panelSouth.add(btnExit);
        
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        btnVote.addActionListener(this);
        btnRetrieve.addActionListener(this);
        btnExit.addActionListener(this);
        
        this.setSize(900, 600);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    //complete the actionPerformed method so that one of the the 3 methods is
    // called depending on which button was clicked.
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().equals("Vote")) 
        {
            castVote(cboCarFinalists.getSelectedItem().toString());
            
        }
        else if (e.getActionCommand().equals("Retrieve")) 
        {
            retrieveStudentRecords();
            
        }
        else if (e.getActionCommand().equals("Exit") ) 
        {
            closeConnection();
        }

    }

    //Complete the method to connect to the server
    public void connectToServer()
    {
        try
        {
            socket = new Socket("localhost",12345);
            getStreams();
        }
        catch(IOException e)
        {
           recordsTextArea.append(e.getMessage());
        }

    }
    
    public void getStreams()
    {
        try
        {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
           
        }
        catch(IOException e)
        {
           recordsTextArea.append(e.getMessage());
        }
    }
    
    //Complete the method to close the connection to the server and exit the application
    private static void closeConnection() 
    {
        try
        {
            out.writeObject("exit");
            out.flush();
            
            String msg = (String)in.readObject();
            recordsTextArea.append("exit message received from server");
            if (msg.equals("exit"))
            {
                in.close();
                out.close();
                socket.close();
                recordsTextArea.append("Client closing");
                System.exit(0); 
            }
            
        }
        catch(ClassNotFoundException cnfe)
        {
           recordsTextArea.append(cnfe.getMessage());
        }
         catch(IOException e)
        {
           recordsTextArea.append(e.getMessage());
        }

    }
    

    //Complete the method to cast a vote
    private static void castVote(String vote) 
    {
        if (vote != null)
        {
            try
            {
                out.writeObject(vote);
                out.flush();
                String response = (String)in.readObject();
                JOptionPane.showMessageDialog(null, response);
            }
            catch (ClassNotFoundException cnfe) 
            {
                recordsTextArea.append(cnfe.getMessage());
            } catch (IOException e) 
            {
                recordsTextArea.append(e.getMessage());
            }
            
        }

    }


    //Complete the method to retrieve the list of cars and number of votes
    private static void retrieveStudentRecords() 
    {
        try
        {
            out.writeObject("retrieve");
            out.flush();
            String lstString = (String)in.readObject();
            System.out.println("lst String received from server : " + lstString);
            String[] arrString = lstString.split(",");
            ArrayList<String> voteList = new ArrayList<>(Arrays.asList(arrString));
            System.out.println("arrayList: " + voteList);
            
            displayVoteResults(voteList); 
        }
        catch (ClassNotFoundException cnfe) 
        {
            recordsTextArea.append(cnfe.getMessage());
        } catch (IOException e) 
        {
            recordsTextArea.append(e.getMessage());
        }

    }
    
    private static void displayVoteResults(ArrayList<String> voteList)
    {
        recordsTextArea.setText("Voting Results:\n");
        for(String result : voteList)
        {
            recordsTextArea.append(result + "\n");
        }
    }

    
    public static void main(String[] args) {
        new Client().setGUI();
    }    
}
