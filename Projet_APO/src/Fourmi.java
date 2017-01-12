import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Fourmi {

    protected LinkedList<Integer> m_chemin;
    protected double m_nourr_transp;
    protected enum Etat {ARRET, ALLER, RETOUR};
    protected Etat m_etat;
    protected static final double M_CAPAFOURMI = 50.0;
    protected final static int[] M_POIDS = new int[] {50, 20, 10, 5, 0};
    protected Case m_case;
    
    public Fourmi()
    {
    	this.m_chemin = new LinkedList<Integer>();
        this.m_nourr_transp = 0;
        this.m_etat = Etat.ARRET;
    }

    public LinkedList<Integer> getM_chemin() 
    {
        return m_chemin;
    }

    public void setM_chemin(LinkedList<Integer> chemin) 
    {
        this.m_chemin = chemin;
    }

    public double getM_nourr_transp() 
    {
        return m_nourr_transp;
    }

    public void setM_nourr_transp(double nourr_transp) 
    {
        this.m_nourr_transp = nourr_transp;
    }
    
    public Etat GetEtat()
    {
    	return this.m_etat;
    }
    
    public void SetEtat(Etat etat)
    {
    	this.m_etat=etat;
    }
    
    public static double GetCapaFourmi()
    {
    	return Fourmi.M_CAPAFOURMI;
    }
    
    public static int[] GetPoids()
    {
    	return Fourmi.M_POIDS;
    }
    
    public Case GetCase()
    {
    	return this.m_case;
    }
    
    public void SetCase(Case caseF)
    {
    	this.m_case = caseF;
    }
    
    public void IncrementeNourriture(double nbr)
    {
    	this.setM_nourr_transp(nbr + this.getM_nourr_transp() );
    }
    
    public double DecrementeNourriture()
    {
    	double nourr = this.getM_nourr_transp();
    	this.setM_nourr_transp(0);
    	return nourr;
    }
    
    public void Bouger()
    {
    	
    }
    
    public int ChoixDirection()
    {
    	//Si c'est le premier d�placement, on le ais de man
    	if( m_chemin.isEmpty() )
    	{
    		int lower = -4;
    		int higher = 4;
    		int random = (int)(Math.random() * (higher-lower)) + lower; 
    		if(random > 0)
    		{
    			random ++;
    		}
    		return random;
    	}
    	else
    	{
    		int dir = m_chemin.getLast();
    		int[] c;
    		switch(dir)
    		{
    			case 1 :  			
    				c = new int[] {M_POIDS[1],M_POIDS[0],M_POIDS[1],M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2]};
    				break;
    			case 2:
    				c = new int[] {M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1],M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3]};
    				break;
    			case 3 :
    				c = new int[] {M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1],M_POIDS[2],M_POIDS[3],M_POIDS[4]};
    				break;
    			case 4 :
    				c = new int[] {M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1],M_POIDS[2],M_POIDS[3]};
    				break;
    			case 5 :
    				c = new int[] {M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1],M_POIDS[2]};
    				break;
    			case 6 :
    				c = new int[] {M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1]};
    				break;
    			case 7 :
    				c = new int[] {M_POIDS[1],M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0]};
    				break;			
    		}
    		for(int i=0;i<8;i++)
    		{
    			pi=
    		}
    		
    	}
    }
    
    public void ChercheSource()
    {
    	
    }
    
    public boolean SourceTrouvee()
    {
    	return true;
    }
    
    public void Retour()
    {
    	
    }
    
    public boolean FourmTrouvee()
    {
    	return true;
    }
    
    public void Start()
    {
    	this.m_etat = Etat.ALLER;
    }
    
    public void Stop()
    {
    	this.m_etat = Etat.ARRET;
    }
    
}
