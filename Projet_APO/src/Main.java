import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {


        String map = "map.txt";

    	Simulation simu = new Simulation(3,map);

        simu.FourmiSimulation();
        
    }

}
