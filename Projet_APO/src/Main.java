
/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

	//Fonction principale
    public static void main(String[] args) {

    	//cr�ation du menu et demande des param�tres de la simulation
    	Menu m = new Menu();
    	m.AffMenu();
    	
    	//Cr�ation et lancement de la simulation en fonction des param�tres choisis lors du menu
    	Simulation simu = new Simulation(m.GetFourm(), m.GetFourmS(), m.GetFourmO(),m.GetPorteeSenseur(), m.GetMap());
        simu.FourmiSimulation();
    }

}
