
package za.ac.cput.marathoneventregistration.domain;

/**
 *
 * @author Leonard
 */
public class Race 
{
    private String RaceCode;
    private String FirstName;
    private String LastName;
    private String RaceType;
    private boolean club;

    public Race() 
    {
    }
    
    public Race(String RaceCode){
        this.RaceCode = RaceCode;
    }
        
    public Race(String RaceCode, String FirstName, String LastName, String RaceType, boolean club) 
    {
        this.RaceCode = RaceCode;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.RaceType = RaceType;
        this.club = club;
    }

    public String getRaceCode() 
    {
        return RaceCode;
    }

    public void setRaceCode(String RaceCode)
    {
        this.RaceCode = RaceCode;
    }

    public String getFirstName() 
    {
        return FirstName;
    }

    public void setFirstName(String FirstName) 
    {
        this.FirstName = FirstName;
    }

    public String getLastName() 
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public String getRaceType() 
    {
        return RaceType;
    }

    public void setRaceType(String RaceType) 
    {
        this.RaceType = RaceType;
    }

    public boolean isClub() 
    {
        return club;
    }

    public void setClub(boolean club) 
    {
        this.club = club;
    }

    @Override
    public String toString() 
    {
        return "Race [ " + "RaceCode : " + RaceCode + ", FirstName : " + FirstName + ", LastName : " + LastName + ", RaceType : " + RaceType + ", Club : " + club + " ]";
    }
    
    
}
