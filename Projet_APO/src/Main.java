/**
 * Created by Martial TARDY on 12/01/2017.
 */
public class Main {

    public static void main(String[] args) {
    	Plateau p = new Plateau();
    	p.Initialisation();
    	p.Afficher();
    	
    	Case F = p.getM_tabCase(3,3);
        Fourmi f = new Fourmi(F);

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
            f.Action();
            System.out.println(f.GetCase());
        }
        
    }
}
