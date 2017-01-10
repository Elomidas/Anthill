/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class FourmiSenseur extends Fourmi {

    private int m_portee;
    protected final static int[] m_poids = new int[] {50, 20, 10, 5, 0};

    public int getM_portee() 
    {
        return m_portee;
    }

    public void setM_portee(int m_portee) 
    {
        this.m_portee = m_portee;
    }
    
    public void ChoixDirection()
    {
    	int taille = 1 + (2 * m_portee);
    	int[][] proba = new int[taille][taille];
    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			proba[i][j] = 0;
    		}
    	}
    	
    }
}
