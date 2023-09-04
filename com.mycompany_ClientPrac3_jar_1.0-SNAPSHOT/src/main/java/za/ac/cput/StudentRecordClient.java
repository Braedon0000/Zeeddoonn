
package za.ac.cput;

/**
 *
 * @author braed
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordClient implements ActionListener{
    //private static Object lstStudent;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static JFrame frame;
    private static JTextField nameField;
    private static JTextField idField;
    private static JTextField scoreField;
    private static JTextField searchField;
    private static JTextArea recordsTextArea;
    private static JButton addButton;
    private static JButton retrieveButton;
    private static JButton exitButton;
    private static JButton serachBtn;
    private static Socket socket;
    //private StudentRecordServer std;
// Connect to the server, create io streams, and call the method that defines the gui
       public StudentRecordClient(){
           try{
        socket = new Socket("127.0.0.1", 12345);
        
         out = new ObjectOutputStream(socket.getOutputStream());
         out.flush();
         in = new ObjectInputStream(socket.getInputStream());
           }
           
        catch(IOException e)
        {
          System.out.println("IOException: " + e.getMessage());
        }

    }//end constructor

//------------------------------------------------------------------------------    

//Create the swing-based gui
    private void createAndShowGUI() {
    frame = new JFrame("Student Record Management");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);
    frame.setLayout(new BorderLayout());

    JPanel inputPanel = new JPanel(new GridLayout(4, 2));
    inputPanel.add(new JLabel("Name:"));
    nameField = new JTextField();
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("ID:"));
    idField = new JTextField();
    inputPanel.add(idField);
    inputPanel.add(new JLabel("Score:"));
    scoreField = new JTextField();
    inputPanel.add(scoreField);
    inputPanel.add(new JLabel("Search"));
    searchField=new JTextField();
    inputPanel.add(searchField);
    

    frame.add(inputPanel, BorderLayout.NORTH);

    addButton = new JButton("Add Record");
    addButton.addActionListener(this);
    retrieveButton = new JButton("Retrieve Records");
    retrieveButton.addActionListener(this);
    exitButton = new JButton("Exit");
    exitButton.addActionListener(this);
    serachBtn=new JButton("Search");
    serachBtn.addActionListener(this);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(retrieveButton);
    buttonPanel.add(exitButton);
    buttonPanel.add(serachBtn);
    frame.add(buttonPanel, BorderLayout.CENTER);

    recordsTextArea = new JTextArea(10,10);
    recordsTextArea.setEditable(false);
    frame.add(new JScrollPane(recordsTextArea), BorderLayout.SOUTH);

    frame.setVisible(true);

    }//end createAndShowGUI()

//------------------------------------------------------------------------------    
    
// In this method, construct a Student object that is initialized with the values entered by the user on the gui.   
// Send the object to the server.
// Clear the textfields and place the cursor in the name textfield    
    private static void addStudentRecord() {
        String firstName= nameField.getText();
        String studnetNumber=idField.getText();
        Double score= Double.parseDouble(scoreField.getText());
        
        Student studentList=new Student(firstName,studnetNumber,score);
        try{
        out.writeObject(studentList);
        out.flush();
        nameField.setText("");
        idField.setText("");
        scoreField.setText("");
        }catch(IOException ioe){
           // System.out.println("IOException: " + ioe.getMessage());
            recordsTextArea.append("IO Excption in Client sendData():"+ioe.getMessage());
        
        }

    }//end addStudentRecord()

//------------------------------------------------------------------------------    

// In this method, send a string to the server that indicates a retrieve request.
// Read the Arraylist Object sent from the server, and call the method to display the student records.
    private static void retrieveStudentRecords() {
        try{
        recordsTextArea.setText("");   
        out.writeObject("retrive");
        out.flush();
        ArrayList<Student>studentData=new ArrayList<>();
         studentData=(ArrayList)in.readObject();
       studentData=(studentData);
        displayStudentRecords(studentData);
        
        
        }catch(IOException ioe){
           // System.out.println("IOException: " + ioe.getMessage());
            recordsTextArea.append("IO Excption in Client sendData():"+ioe.getMessage());
            
    }catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        }
    }//end retrieveStudentRecords()

//------------------------------------------------------------------------------    

// In this method, you must append the records in the arraylist (sent as a parameter) in the textarea.
    private static void displayStudentRecords(List<Student> studentList) {
        try{
        for(Student student:studentList){
        recordsTextArea.append(student.toString()+"\n");
        //recordsTextArea.append("Student Name:"+student.getStudentName()+"\n"+"Student Number"+student.getStudentNumber()+"\n"+"Score"+student.getScore());
        out.flush();
        }
        
        }catch(IOException ioe){
           // System.out.println("IOException: " + ioe.getMessage());
            recordsTextArea.append("IO Excption in Client sendData():"+ioe.getMessage());

        }
    }

//------------------------------------------------------------------------------    

// Send a string value to the server indicating an exit request. 
// Read the returning string from the server
// Close all connections and exit the application    
    private static void closeConnection() {
        try{
    out.writeObject("exit");
    out.flush();
    in.close();
    out.close();
    socket.close();
    //serverSocket.close();
    System.out.println("Server closing connection");
    System.exit(0);
    }
    catch(IOException e){
    e.printStackTrace();
    }

    }

//------------------------------------------------------------------------------    

// Handle all action events generated by the user-interaction with the buttons
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==addButton){
        addStudentRecord();
        }else if(e.getSource()==retrieveButton){
            retrieveStudentRecords();
        }else if(e.getSource()==exitButton){
           closeConnection();
        }else if(e.getSource()==serachBtn){
            search();
        }
            
            
    }//end actionPerformed
    
    public static void search(){
   recordsTextArea.setText("");       
   String query=searchField.getText();
   System.out.println("gets text");
    try{
        out.writeObject(query);
        out.flush();
        System.out.println("Step0");
        ArrayList<Student>studentData=new ArrayList<>();
        System.out.println("Step1");
        studentData=(ArrayList)in.readObject();
        System.out.println("Step2");
        studentData=(studentData);
        displayStudentRecords(studentData);
        
    }catch(IOException ioe){
           // System.out.println("IOException: " + ioe.getMessage());
            recordsTextArea.append("IO Excption in Client sendData():"+ioe.getMessage());

        }catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        }
    
    }

//------------------------------------------------------------------------------    

// Execute the application by calling the necessary methods   
    public static void main(String[] args) {
        StudentRecordClient client = new StudentRecordClient();
        client.createAndShowGUI();
      //  StudentRecordClient();
        

    }//end main
}//end class



