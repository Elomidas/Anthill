/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {
    	Plateau p = new Plateau();
    	p.Initialisation();
    	p.Afficher();
    	
    	/*Case[] tab_case1 = new Case[] {new Case(),new Case(),new Case(),new Case(),new Case(),new Case(),new Case(),new Case()};
        Case[] tab_case = new Case[] {new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1),new Case(0,0,1,tab_case1)};

    	Case case1 = new Case(1,1,2,tab_case);*/
    	Case F = p.getM_tabCase(5,1);
        FourmiSenseur f = new FourmiSenseur(F);
        f.Start();
        
        System.out.println(f.GetCase());
        while(!Thread.interrupted())
        {
        	try
            {
            	Thread.sleep(1000);
            }
            catch(Exception e)
            {
            	System.out.println("remond est " + e.getMessage());
            }
            System.out.println(f.ChoixDirection());
            f.Action();
            System.out.println(f.GetCase());
        }
        
    }
}
