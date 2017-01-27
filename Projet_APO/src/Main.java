
/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {

    	Menu m = new Menu();
    	m.AffMenu();
    	
    	Simulation simu = new Simulation(m.GetFourm(), m.GetFourmS(), m.GetFourmO(), m.GetMap());
    	
        simu.FourmiSimulation();
    	/*
    	int nbF=0;int nbFS=0;int nbFO=0;
        for(int i=0;i<simu.getM_listeFourmis().size();i++)
        {
        	
        	if(simu.getM_listeFourmis().get(i) instanceof FourmiSenseur)
        		nbFS++;
        	else if(simu.getM_listeFourmis().get(i) instanceof FourmiOrientation)
        		nbFO++;
        	else if(simu.getM_listeFourmis().get(i) instanceof Fourmi)
        		nbF++;
        		
        }
        System.out.println("fourmi : " + nbF+ "fourmiS"+nbFS+"fourmiO"+nbFO );
        */
    	
    }

}
