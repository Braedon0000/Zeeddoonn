
package za.ac.cput.caroftheyear;

/**
 *
 * @author Leonard
 */
public class VotingPoll 
{
    private String vehicle;
    private int numberOfVotes;

    public VotingPoll() 
    {
        
    }

    public VotingPoll(String vehicle, int numberOfVotes)
    {
        this.vehicle = vehicle;
        this.numberOfVotes = numberOfVotes;
    }

    public String getVehicle() 
    {
        return vehicle;
    }

    public void setVehicle(String vehicle) 
    {
        this.vehicle = vehicle;
    }

    public int getNumberOfVotes()
    {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes)
    {
        this.numberOfVotes = numberOfVotes;
    }
    
    public void incrementNumberOfVotes()
    {
        this.numberOfVotes++;
    }
    
    @Override
    public String toString()
    {
        return "Vehicle : " + vehicle + ", Number Of Votes : " + numberOfVotes;
    }
    
    
}
