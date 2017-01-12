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
    	int[][] modif = new int[3][3];
    	boolean[][] obstacle = new boolean[taille][taille];
    	boolean[][] source = new boolean[taille][taille];
    	//On initialise tous les tableaux
    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			//On initialise les probas � 0
    			if((i < 3) && (j < 3))
    			{
    				proba[i][j] = 0;
    				modif[i][j] = 0;
    			}
    	    	//On regarde s'il y a des sources ou des obstacles � port�e de senseur
    			//On r�cup�re la case
    			Case c = m_case;
    			int[] pos = new int[] {actu, actu};
    			while((pos[0] != i) || (pos[1] != j))
    			{
    				int d = 0;
    				if(pos[0] > i)
    				{
    					//On veut une ligne sup�rieure
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus � gauche
    						d = 3;
    						pos[0]--;
    						pos[1]--;
    					}
    					else if(pos[1] < j)
    					{
    						//On veut une colonne plus � droite
    						d = -1;
    						pos[0]--;
    						pos[1]++;
    					}
    					else
    					{
    						//On est sur la bonne colonne
    						d = 4;
    						pos[0]--;
    					}
    				}
    				else if(pos[0] < i)
    				{
    					//On veut une ligne inf�rieure
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus � gauche
    						d = 1;
    						pos[0]++;
    						pos[1]--;
    					}
    					else if(pos[1] < j)
    					{
    						//On veut une colonne plus � droite
    						d = -3;
    						pos[0]++;
    						pos[1]++;
    					}
    					else
    					{
    						//On est sur la bonne colonne
    						d = -4;
    						pos[0]++;
    					}
    				}
    				else
    				{
    					//Ligne correcte
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus � gauche
    						d = 2;
    						pos[1]--;
    					}
    					else
    					{
    						//On veut une colonne plus � droite
    						d = -2;
    						pos[1]++;
    					}
    				}
    				c = m_case.CaseVoisine(d);
    			}
    			obstacle[i][j] = !c.Penetrable();
    			source[i][j] = (c instanceof Source);
    		}
    	}
    	//Une fois qu'on conna�t les obstacles et les sources � proximit�, on regarde pour se d�placer

    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			if(source[i][j])
    			{
    				//Si il y a une source, on regarde si elle est accessible
    				
    			}
    		}
    	}
    	
    	
    	//On calcul la probabilit� d'aller sur chaque case
    	
    	
    	//Fin
    	return direction;
    }
    
    /*
     * x, y : coordonn�es que l'on veut atteindre
     * i, j : position de la fourmi
     * return d : direction choisie (0 si innaccessible)
     */
    private int Navigation(boolean[][] obs, int x, int y, int i, int j)
    {
    	int test = 0;
		int d = 0;
		int a, b;
		if(x > i)
		{
			//On veut une ligne inf�rieure
			if(y > j)
			{
				//On veut une colonne plus � droite
				//On essaye d'aller sur la case en bas � droite
				a = i + 1;
				b = j + 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller � droite avant d'aller en bas
					a = i;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -2;
					a = i + 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -4;
				}
				else
				{
					//On essaye d'aller en bas avant d'aller � droite
					a = i + 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -4;
					a = i;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -2;
				}
			}
			else if(y < j)
			{
				//On veut une colonne plus � gauche
				//On essaye d'aller sur la case en bas � gauche
				a = i + 1;
				b = j - 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return 1;
				if((j - y) > (x - i))
				{
					//On essaye d'aller � gauche avant d'aller en bas
					a = i;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 2;
					a = i + 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -4;
				}
				else
				{
					//On essaye d'aller en bas avant d'aller � gauche
					a = i + 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -4;
					a = i;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 2;
				}
			}
			else
			{
				//On est sur la bonne colonne
				//On essaye d'abbord d'aller en bas
				a = i + 1;
				b = j;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -4;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en bas � droite avant d'aller en bas � gauche
					a = i + 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -3;
					a = i + 1;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 1;
				}
				else
				{
					//On essaye d'aller en bas � gauche avant d'aller en bas � droite
					a = i + 1;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 1;
					a = i + 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -3;
				}
			}
		}
		else if(x < i)
		{
			//On veut une ligne sup�rieure
			if(y > j)
			{
				//On veut une colonne plus � droite
				//On essaye d'aller sur la case en haut � droite
				a = i - 1;
				b = j + 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -1;
				if((y - j) > (x - i))
				{
					//On essaye d'aller � droite avant d'aller en haut
					a = i;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -2;
					a = i - 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 4;
				}
				else
				{
					//On essaye d'aller en haut avant d'aller � gauche
					a = i - 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -4;
					a = i;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -2;
				}
			}
			/*
			else if(y < j)
			{
				//On veut une colonne plus � droite
				d = -1;
				pos[0]++;
				pos[1]++;
			}
			else
			{
				//On est sur la bonne colonne
				d = 4;
				pos[0]++;
			}
			*/
		}
		else
		{
			/*
			//Ligne correcte
			if(pos[1] > j)
			{
				//On veut une colonne plus � gauche
				d = 2;
				pos[1]--;
			}
			else
			{
				//On veut une colonne plus � droite
				d = -2;
				pos[1]++;
			}
			*/
		}
		
		
		return 0;
    }
}
