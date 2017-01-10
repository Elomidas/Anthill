/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class FourmiSenseur extends Fourmi {

    private int m_portee;

    public int getPortee() 
    {
        return m_portee;
    }

    public void setPortee(int m_portee) 
    {
        this.m_portee = m_portee;
    }
    
    public int ChoixDirection()
    {
    	int taille = 1 + (2 * m_portee);
    	int actu = 1 + m_portee;
    	int direction = 0;
    	int[][] proba = new int[3][3];
    	boolean[][] obstacle = new boolean[taille][taille];
    	boolean[][] source = new boolean[taille][taille];
    	//On initialise tout à '0' ou à 'false'
    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			//On initialise les probas à 0
    			if((i < 3) && (j < 3))
    				proba[i][j] = 0;
    	    	//On regarde s'il y a des sources ou des obstacles à portée de senseur
    			//On récupère la case
    			Case c = m_case;
    			int[] pos = new int[] {actu, actu};
    			while((pos[0] != i) || (pos[1] != j))
    			{
    				int d = 0;
    				if(pos[0] > i)
    				{
    					//On veut une ligne inférieure
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus à gauche
    						
    					}
    					else if(pos[1] < j)
    					{
    						//On veut une colonne plus à droite
    					}
    					else
    					{
    						//On est sur la bonne colonne
    					}
    				}
    				else if(pos[0] < i)
    				{
    					//On veut une ligne supérieure
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus à gauche
    					}
    					else if(pos[1] < j)
    					{
    						//On veut une colonne plus à droite
    					}
    					else
    					{
    						//On est sur la bonne colonne
    					}
    				}
    				else
    				{
    					//Ligne correcte
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus à gauche
    					}
    					else
    					{
    						//On veut une colonne plus à droite
    					}
    				}
    				c = m_case.CaseVoisine(d);
    			}
    			obstacle[i][j] = false;
    			source[i][j] = false;
    		}
    	}
    	
    	
    	//On calcul la probabilité d'aller sur chaque case
    	
    	
    	//Fin
    	return direction;
    }
}
