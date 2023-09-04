
package za.ac.cput;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
public class StudentRecordServer {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object receivedObject;

//In the constructor listen for incoming client connections    
public StudentRecordServer(){
    int port=12345;
    try{
        serverSocket=new ServerSocket(port);
        System.out.println("Server is listing on port "+port);
        
        clientSocket=serverSocket.accept();
        System.out.println("Client connected "+clientSocket.getInetAddress().getHostAddress());

    
    }
    catch(IOException e){
    e.printStackTrace();
    }

}

//------------------------------------------------------------------------------    
    
//create the io streams
public void getStreams(){
    try{
    out = new ObjectOutputStream(clientSocket.getOutputStream());
    out.flush();
    in = new ObjectInputStream(clientSocket.getInputStream());
    }
    catch(IOException e){
       e.printStackTrace();
    }
}//end getStreams()

//------------------------------------------------------------------------------    
    
//Declare arraylist and handle the communication between server and client    
    public void processClient() {
      ArrayList<Student>studentRecords=new ArrayList<>();  
      while(true){
          try{
              receivedObject=in.readObject();
              if(receivedObject instanceof Student){
                  Student newStudent=(Student)receivedObject ;
                  studentRecords.add(newStudent);
                  System.out.println("Added new student record:"+ newStudent);
                  
              }
              else if (receivedObject instanceof String && ((String)receivedObject).equals("retrive")){
              ArrayList lstStudent =(ArrayList)studentRecords.clone();
              out.writeObject(lstStudent);
              out.flush();
              System.out.println("list sent to client"+studentRecords);
          }else if(receivedObject instanceof String&&((String)receivedObject).equals("exit")){
          closeConnection();
          }
          else if(receivedObject instanceof String){
             ArrayList<Student>searchList=new ArrayList<>();  
             for(int i=0;i<studentRecords.size();i++){
             if(studentRecords.get(i).getStudentName().equals((String)receivedObject)||studentRecords.get(i).getStudentNumber().equals((String)receivedObject)){
                searchList.add(studentRecords.get(i));
             }
             }
             ArrayList lstStudent =(ArrayList)searchList.clone();
             out.writeObject(lstStudent);
                out.flush();
          }
          }    
          catch(ClassNotFoundException e){
             e.printStackTrace(); 
          }
          catch(IOException e){
          e.printStackTrace();
          }
      
      
      }

    }//end processClient

//------------------------------------------------------------------------------    

//close all connections to the server and exit the application   
    private static void closeConnection() {
    try{
    out.writeObject("exit");
    out.flush();
    in.close();
    out.close();
    clientSocket.close();
    serverSocket.close();
    System.out.println("Server closing connection");
    System.exit(0);
    }
    catch(IOException e){
    e.printStackTrace();
    }
    }//end closeConnection()

//------------------------------------------------------------------------------    

//execute the program and call all necessary methods
   public static void main(String[] args) {
    StudentRecordServer serv =new StudentRecordServer();
    serv.getStreams();
    serv.processClient();
    }//end main

}//end class


    

