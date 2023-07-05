
package za.ac.cput.marathoneventregistration;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import za.ac.cput.marathoneventregistration.gui.MarathonEventGui;

/**
 *
 * @author Leonard
 */
public class Main {
    public static void main(String[] args) 
    {
        MarathonEventGui marathonEvent = new MarathonEventGui();
        marathonEvent.pack();
        marathonEvent.setGui();
        marathonEvent.setSize(400,265);
        marathonEvent.setDefaultCloseOperation(EXIT_ON_CLOSE);
        marathonEvent.setVisible(true);
    }
    
}
