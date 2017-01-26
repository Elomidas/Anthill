
/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {


        String map = "map.txt";

        //Simulation simu = new Simulation(3,map);
        Simulation simu = new Simulation(3, 2, 0, map);

        simu.FourmiSimulation();
        
    }

}
