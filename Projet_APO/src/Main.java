/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {
        String map = "map.txt";


    	/*Case[] tab_case1 = new Case[] {new Case(),new Case(),new Case(),new Case(),new Case(),new Case(),new Case(),new Case()};
        Case[] tab_case = new Case[] {new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1)};

    	Case case1 = new Case(1,1,2,tab_case);*/
    	/*Case F = p.GetFourmiliere();
        Fourmi f = new Fourmi(F);

        f.Start();
        
        System.out.println(f.GetCase());*/

    	Simulation simu = new Simulation(3,map);

        simu.FourmiSimulation();
        
    }
}
