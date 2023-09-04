
package za.ac.cput.caroftheyear;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Leonard
 */
public class Server 
{
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object receivedObject;

//complete the constructor by accepting connection from a client    
public Server()
{
    
    try
    {
        serverSocket = new ServerSocket(12345, 1);
        
        System.out.println("Server is listening for a connection.....");
        clientSocket = serverSocket.accept();
        System.out.println(clientSocket.getInetAddress() + " has connected");
    }
    catch(IOException ioe)
    {
        System.out.println(ioe.getMessage());
    }
}

//complete the method to set up the IO streams
public void getStreams()
{
    try
    {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(clientSocket.getInputStream());
    }
    catch(IOException ioe)
    {
        System.out.println(ioe.getMessage());
    }

}

//complete the method to handle the requests from the client
    public void processClient() 
    {
        ArrayList<VotingPoll> votingRecords = new ArrayList<>();
        votingRecords.add(new VotingPoll("Ford Ranger", 0));
        votingRecords.add(new VotingPoll("Audi A3", 0));
        votingRecords.add(new VotingPoll("BMW X3", 0));
        votingRecords.add(new VotingPoll("Toyota Starlet", 0));
        votingRecords.add(new VotingPoll("Suzuki Swift", 0));
        ArrayList<VotingPoll> lstVotingResults;
        String arrayAsString;
            while (true) 
            {
                try
                {
                    receivedObject = in.readObject();
                    if (((String) receivedObject).equals("retrieve"))
                    {
                        arrayAsString = votingRecords.toString();
                        System.out.println("Array String: " + arrayAsString);
                        lstVotingResults = (ArrayList)votingRecords.clone();
                            for (int i = 0; i < lstVotingResults.size(); i++) 
                            {
                                System.out.println(lstVotingResults.get(i).getVehicle() + " " + lstVotingResults.get(i).getNumberOfVotes());
                            }
                        out.writeObject(arrayAsString);
                        out.flush();
                        System.out.println("list sent to client" + lstVotingResults);
                            
                        
                    } 
                    else if (((String) receivedObject).equals("exit")) 
                    {
                        closeConnection();
                        break;
                    } 
                    else 
                    {
                        for (int i = 0; i < votingRecords.size(); i++) 
                        {
                            if (votingRecords.get(i).getVehicle().equals(receivedObject)) {
                                votingRecords.get(i).incrementNumberOfVotes();
                                System.out.println("Voted for " + votingRecords.get(i).getVehicle());
                                break;
                            }
                        }
                        out.writeObject("Votes updated");
                        out.flush();

                    }
                }
                catch(ClassNotFoundException cnfe)
                {
                    System.out.println(cnfe.getMessage());
                }
                catch(IOException ioe)
                {
                    System.out.println(ioe.getMessage()); 
                }
            }//end while
    }//end processClient

//    complete the method to close connections to the client
    private static void closeConnection() 
    {
        try
        {
            out.writeObject("exit");
            out.flush();
            out.close();
            in.close();
            clientSocket.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }

    }

    public static void main(String[] args) 
    {
        Server serverObject = new Server();
        serverObject.getStreams();
        serverObject.processClient();
    }   
}