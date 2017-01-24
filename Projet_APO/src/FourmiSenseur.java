/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class FourmiSenseur extends Fourmi {

    private int m_portee;
    
    public FourmiSenseur()
    {
    	super();
    	m_portee = 2;
    }
    
    public FourmiSenseur(Case c)
    {
    	super(c);
    }

    public int getPortee() 
    {
        return m_portee;
    }

    public void setPortee(int m_portee) 
    {
        this.m_portee = m_portee;
    }
    
    private void Senseur(int taille, int[][] map)
    {
    	/*On utilisera les codes suivants pour les cases :
    	 * 0 -> Inconnue
    	 * 1 -> Libre
    	 * 2 -> Source
    	 * 3 -> Obstacle
    	 * 
    	 * On va observer la carte à la "façon démineur"
    	 * Si une case n'est pas un obstacle, la fourmi peut voir les cases autour.
    	 * Si une case est un obstacle, la fourmi ne voit pas les cases autour.
    	 */
    	
    }
    
    public int ChoixDirection()
    {
    	int taille = 1 + (2 * m_portee);
    	int direction = 0;
    	int px = GetCase().getM_abcisse();
    	int py = GetCase().getM_ordonnee();
    	double[] proba = new double[8];
    	double[][] modif = new double[3][3];
    	boolean[][] obstacle = new boolean[taille][taille];
    	boolean[][] source = new boolean[taille][taille];
		//Position actuelle de la fourmi dans le tableau
		int[] pos = new int[] {m_portee, m_portee};
    	//On initialise tous les tableaux
		for(int i = 0; i < 8; i++)
			proba[i] = 0;
    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			//On initialise les probas à 0
    			if((i < 3) && (j < 3))
    				modif[i][j] = 1;
    	    	//On regarde s'il y a des sources ou des obstacles à portée de senseur
    			//On récupère la case
    			Case c = m_case;
    			while((pos[0] != i) || (pos[1] != j))
    			{
    				int d = 0;
    				if(pos[0] > i)
    				{
    					//On veut une ligne supérieure
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus à gauche
    						d = 3;
    						pos[0]--;
    						pos[1]--;
    					}
    					else if(pos[1] < j)
    					{
    						//On veut une colonne plus à droite
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
    					//On veut une ligne inférieure
    					if(pos[1] > j)
    					{
    						//On veut une colonne plus à gauche
    						d = 1;
    						pos[0]++;
    						pos[1]--;
    					}
    					else if(pos[1] < j)
    					{
    						//On veut une colonne plus à droite
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
    						//On veut une colonne plus à gauche
    						d = 2;
    						pos[1]--;
    					}
    					else
    					{
    						//On veut une colonne plus à droite
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
    	
    	//Une fois qu'on connaît les obstacles et les sources à proximité, on regarde si on a accès aux sources en fonction des obstacles.

    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			if(source[i][j])
    			{
    				//Si il y a une source, on regarde si elle est accessible
    				if((direction = Navigation(obstacle, i, j, m_portee, m_portee)) != 0)
    				{
    					//On augmente la proba d'aller dans cette direction.
    					//On considère que si une source est accessible, la probabilité d'aller dans cette direction est multipliée par 2.
    					//On multiplie aussi la probabilité d'aller dans les direction adjacentes par 1.5 s'il n'y a pas d'obstacle.
    					//Par exemple si une source est disponnible en haut, la fourmi a 2 fois plus de chances d'aller en haut et 1.5 fois 
    					// plus de chances d'aller en haut à droite et en haut à gauche, sous reserve qu'il n'y ait pas d'obstacle.
    					
    					//On effectue un changement d'index afin de déduire de la direction les cases à modifier
    					int ix, iy, ixa, ixb, iya, iyb;
    					switch(direction)
    					{
    						case 1 :
    							ix = 2;
    							iy = 0;
    							ixa = 1;
    							iya = 0;
    							ixb = 2;
    							iyb = 1;
    							break;
    							
    						case 2 :
    							ix = 1;
    							iy = 0;
    							ixa = 0;
    							iya = 0;
    							ixb = 2;
    							iyb = 0;
    							break;
    							
    						case 3 :
    							ix = 0;
    							iy = 0;
    							ixa = 1;
    							iya = 0;
    							ixb = 0;
    							iyb = 1;
    							break;
    							
    						case 4 :
    							ix = 0;
    							iy = 1;
    							ixa = 0;
    							iya = 0;
    							ixb = 0;
    							iyb = 2;
    							break;
    							
    						case -1 :
    							ix = 0;
    							iy = 2;
    							ixa = 0;
    							iya = 1;
    							ixb = 1;
    							iyb = 1;
    							break;
    							
    						case -2 :
    							ix = 1;
    							iy = 2;
    							ixa = 0;
    							iya = 2;
    							ixb = 2;
    							iyb = 2;
    							break;
    							
    						case -3:
    							ix = 2;
    							iy = 2;
    							ixa = 1;
    							iya = 2;
    							ixb = 2;
    							iyb = 1;
    							break;
    							
    						case -4 :
    							ix = 2;
    							iy = 1;
    							ixa = 2;
    							iya = 0;
    							ixb = 2;
    							iyb = 2;
    							break;
    							
							default :
    							ix = 0;
    							iy = 0;
    							ixa = 0;
    							iya = 0;
    							ixb = 0;
    							iyb = 0;
    							break;
    					}
    					if(!obstacle[pos[0] + ix - 1][pos[1] + iy - 1])
    					{
    						modif[ix][iy] *= 2.0;
    					}
    					if(!obstacle[pos[0] + ixa - 1][pos[1] + iya - 1])
    					{
    						modif[ixa][iya] *= 1.5;
    					}
    					if(!obstacle[pos[0] + ixb - 1][pos[1] + iyb - 1])
    					{
    						modif[ixb][iyb] *= 1.5;
    					}
    				}
    			}
    		}
    	}
    	
    	//On évite d'aller sur les murs
    	for(int i = 0; i < 9; i++)
    	{
    		if(i == 4)
    			i++;
    		int x = (i / 3) - 1;
    		int y = (i % 3) - 1;
    		if(obstacle[pos[0]+x][pos[1]+y])
    			modif[1+x][x+y] = 0;
    	}
    	
    	//On calcul la probabilité d'aller sur chaque case
		int[] p = AffectationPoids(m_chemin.getLast());
		boolean possible = false;
		for(int i = 0; i < 8; i++)
		{
			//On adapte les index pour passer des tableaux 3D aux tableaux 2D
			int idg = (i < 4) ? i : i + 1;
			int idx = idg / 3;
			int idy = idg % 3;
			//On évite d'aller en arrière et on évite les obstacles
			proba[i] = ((p[i] * modif[idx][idy]) == 0) ? 0 : (p[i] * modif[idx][idy]) + GetPheroAdj(i);
			if(proba[i] != 0)
				possible = true;
		}
		//Si on est dans une impasse, on fait demi-tour
		if(!possible)
			return -m_chemin.getLast();
    	
		double somme = 0;
		for(int i = 0; i < 8; i++)
			somme += proba[i];
		for(int i = 0; i < 8; i++)
			proba[i] /= somme;
		
    	//Fin
    	return GetProba(proba);
    }
    
    /*
     * x, y : coordonnées que l'on veut atteindre
     * i, j : position de la fourmi
     * return d : direction choisie (0 si innaccessible)
     */
    private int Navigation(boolean[][] obs, int x, int y, int i, int j)
    {
		int a, b;
		if(x > i)
		{
			//On veut une ligne inférieure
			if(y > j)
			{
				//On veut une colonne plus à droite
				//On essaye d'aller sur la case en bas à droite
				a = i + 1;
				b = j + 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller à droite avant d'aller en bas
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
					//On essaye d'aller en bas avant d'aller à droite
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
				//On veut une colonne plus à gauche
				//On essaye d'aller sur la case en bas à gauche
				a = i + 1;
				b = j - 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return 1;
				if((j - y) > (x - i))
				{
					//On essaye d'aller à gauche avant d'aller en bas
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
					//On essaye d'aller en bas avant d'aller à gauche
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
					//On essaye d'aller en bas à droite avant d'aller en bas à gauche
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
					//On essaye d'aller en bas à gauche avant d'aller en bas à droite
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
			//On veut une ligne supérieure
			if(y > j)
			{
				//On veut une colonne plus à droite
				//On essaye d'aller sur la case en haut à droite
				a = i - 1;
				b = j + 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -1;
				if((y - j) > (x - i))
				{
					//On essaye d'aller à droite avant d'aller en haut
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
					//On essaye d'aller en haut avant d'aller à gauche
					a = i - 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 4;
					a = i;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -2;
				}
			}
			else if(y < j)
			{
				//On veut une colonne plus à gauche
				//On essaye d'aller en haut à gauche
				a = i - 1;
				b = j - 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return 3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller à gauche avant d'aller en haut
					a = i;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 2;
					a = i - 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 4;
				}
				else
				{
					//On essaye d'aller en haut avant d'aller à gauche
					a = i - 1;
					b = j;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 4;
					a = i;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 2;
				}
			}
			else
			{
				//On est sur la bonne colonne
				//On essaye d'abbord d'aller en haut
				a = i - 1;
				b = j;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return 4;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut à droite avant d'aller en haut à gauche
					a = i - 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -1;
					a = i - 1;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 3;
				}
				else
				{
					//On essaye d'aller en haut à gauche avant d'aller en haut à droite
					a = i - 1;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 3;
					a = i - 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -1;
				}
			}
		}
		else
		{
			//Ligne correcte
			if(y > j)
			{
				//On veut une colonne plus à droite
				//On essaye d'abbord d'aller en droite
				a = i;
				b = j + 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut à droite avant d'aller en bas à droite
					a = i - 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -1;
					a = i + 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -3;
				}
				else
				{
					//On essaye d'aller en bas à droite avant d'aller en haut à droite
					a = i + 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return -3;
					a = i - 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 1;
				}
			}
			else
			{
				//On veut une colonne plus à gauche
				//On essaye d'abbord d'aller en gauche
				a = i;
				b = j - 1;
				if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut à gauche avant d'aller en bas à gauche
					a = i - 1;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 3;
					a = i + 1;
					b = j - 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 1;
				}
				else
				{
					//On essaye d'aller en bas à gauche avant d'aller en haut à gauche
					a = i + 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 1;
					a = i - 1;
					b = j + 1;
					if(!obs[a][b] && (Navigation(obs, x, y, a, b) != 0))
						return 3;
				}
			}
		}
		return 0;
    }
}
