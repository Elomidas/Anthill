import java.util.LinkedList;



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
        this.m_case = new Case();
    }
    
    public Fourmi(Case _case)
    {
    	this();
    	if(!(_case instanceof Obstacle))
    		this.m_case=_case;
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
    	int dir = ChoixDirection();
		
    	System.out.println("voisine : " + GetCase().CaseVoisine(dir) +"direction : " + dir);
    	if(!(this.GetCase().CaseVoisine(dir) instanceof Obstacle))
    	{
    		this.SetCase(this.GetCase().CaseVoisine(dir));
        	this.m_chemin.addLast(dir);
    	}     	
    }
    
    public int[] AffectationPoids(int dir)
    {
    	int[] c;
    	switch(dir)
		{
    		case -4 :  			
				c = new int[] {M_POIDS[0],M_POIDS[1],M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1]};
				break;
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
			case -1 :
				c = new int[] {M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1],M_POIDS[2]};
				break;
			case -2 :
				c = new int[] {M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0],M_POIDS[1]};
				break;
			case -3 :
				c = new int[] {M_POIDS[1],M_POIDS[2],M_POIDS[3],M_POIDS[4],M_POIDS[3],M_POIDS[2],M_POIDS[1],M_POIDS[0]};
				break;
			default :
				c = new int[8];
				break;
		}
    	return c;
    }
    
    public double GetPheroAdj(int i)
    {
    	return this.GetCase().getM_case_adj(i).getM_pheromone();
    }
    
    public int GetProba(double[] proba)
    {
    	int h=0;
		double random = Math.random();
		double probacum =proba[0];
		for(h = 0 ;random >= probacum;h++)
		{
			probacum +=proba[h+1];
		}
		// R�cup�ration de la direction � retourner en fonction de l'indice h de la proba Ph
		switch(h)
		{
			case 0 : 
				return -4;
			case 1 : 
				return 1;
			case 2 : 
				return 2;
			case 3 : 
				return 3;
			case 4 : 
				return 4;
			case 5 : 
				return -1;
			case 6 : 
				return -2;
			case 7 : 
				return -3;
			default :
				return 0;  			
		}
    }
    
    public int ChoixDirection()
    {

    	//Si c'est le premier d�placement, on le fais de mani�re al�atoire

    	if( m_chemin.isEmpty() )
    	{
    		int lower = -4;
    		int higher = 4;
    		int random = (int)(Math.random() * (higher-lower)) + lower; 
    		if(random >= 0)
    		{
    			random ++;
    		}
    		return random; 
    	}
    	else
    	{
    		// r�cup�ration de la derniere direction puis affectation des poids c[]
    		int dir = m_chemin.getLast();
    		int[] c =AffectationPoids(dir);
    		double[] phero = new double[8];
    		double[] proba = new double[8];
    		
    		// Calcul de la somme (pour les probas)
    		double somme=0;
    		for(int j=0;j<8;j++)
			{
				somme += c[j] + phero[j]; 				
			}
    		// Calcul des probas
    		for(int i=0;i<8;i++)
    		{
    			phero[i] = GetPheroAdj(i);
    			if(c[i] != 0)
    				proba[i] = (c[i] + phero[i])/somme ;
    			else
    				proba[i] = 0;
    		}
    		return GetProba(proba);  		
    	}
    }
    
    public void ChercheSource()
    {
    	if(!SourceTrouvee())
    	{
    		this.Bouger();
    		this.GetCase().IncrementePheromone();
    	}
    }
    
    public boolean SourceTrouvee()
    {
    	if(this.GetCase() instanceof Source)
    	{
    		this.IncrementeNourriture(((Source)this.GetCase()).DecrementeNourriture(M_CAPAFOURMI));
    		this.SetEtat(Etat.RETOUR);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public void Retour()
    {
    	if(!this.FourmTrouvee())
    	{
    		int dir = - this.getM_chemin().removeLast();
    		System.out.println(dir);
    		this.SetCase(this.GetCase().CaseVoisine(dir));
    		this.GetCase().IncrementePheromone();
    	}
    }
    
    public boolean FourmTrouvee()
    {
    	
    	if(this.GetCase() instanceof Fourmiliere)
    	{
    		System.out.println("OK");
    		((Fourmiliere)this.GetCase()).IncrementerNourriture(this.DecrementeNourriture());
    		this.SetEtat(Etat.ARRET);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public void Action()
    {
    	if(this.GetEtat() == Etat.ALLER)
    		this.ChercheSource();
    	else if(this.GetEtat() == Etat.RETOUR)
    		this.Retour();
    	
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
