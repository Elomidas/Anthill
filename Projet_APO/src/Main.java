
/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {

    	Menu m = new Menu();
    	m.AffMenu();
    	
    	Simulation simu = new Simulation(m.GetFourm(), m.GetFourmS(), m.GetFourmO(), m.GetMap());

        simu.FourmiSimulation();
        
    	
    }

}
