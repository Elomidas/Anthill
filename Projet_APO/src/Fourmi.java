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
    
    public void SetEtat()
    {
    	
    }
    
    
    
    
    
    public void IncrementeNourriture(double nbr)
    {
    	
    }
}
