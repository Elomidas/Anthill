/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class FourmiSenseur extends Fourmi {

    protected int m_portee;
    
    public FourmiSenseur()
    {
    	super();
    	m_portee = 2;
    }
    
    public FourmiSenseur(Case c)
    {
    	super(c);
    	m_portee = 2;
    }

    public int getPortee() 
    {
        return m_portee;
    }

    public void setPortee(int portee) 
    {
        this.m_portee = (portee > 0) ? portee : m_portee;
    }
    
    protected int[][] Senseur(int taille, int[][] map, int px, int py, Case c)
    {
    	/*On utilisera les codes suivants pour les cases :
    	 * 0 -> Inconnue
    	 * 1 -> Libre
    	 * 2 -> Source
    	 * 3 -> maptacle
    	 * 
    	 * On va maperver la carte à la "façon démineur" par récursivité
    	 * Si une case n'est pas un maptacle, la fourmi peut voir les cases autour.
    	 * Si une case est un maptacle, la fourmi ne voit pas les cases autour.
    	 */
    	
    	//On ne traite la case que si elle ne l'a pas encore été et qu'elle est dans le tableau
    	if((px >= 0) && (py >= 0) && (px <= taille) && (py <= taille) && (map[px][py] == 0))
    	{
    		//Si la case contient un maptacle, on s'arrête là
    		if(!c.Penetrable())
    			map[px][py] = 3;
    		else
    		{
    			if(c instanceof Source)
    				map[px][py] = 2;
    			else map[px][py] = 1;
    			//Sinon, on regarde une par une les cases voisines
    			//Haut gauche
    			map = Senseur(taille, map, px-1, py-1, c.CaseVoisine(3));
    			//Haut
    			map = Senseur(taille, map, px-1, py, c.CaseVoisine(4));
    			//Haut gauche
    			map = Senseur(taille, map, px-1, py+1, c.CaseVoisine(-1));
    			//Gauche
    			map = Senseur(taille, map, px, py-1, c.CaseVoisine(2));
    			//Droite
    			map = Senseur(taille, map, px, py+1, c.CaseVoisine(-2));
    			//Bas gauche
    			map = Senseur(taille, map, px+1, py-1, c.CaseVoisine(1));
    			//Bas
    			map = Senseur(taille, map, px+1, py, c.CaseVoisine(-4));
    			//Haut gauche
    			map = Senseur(taille, map, px+1, py+1, c.CaseVoisine(-3));
    		}
    	}
		return map;
    }
    
    public int ChoixDirection()
    {
    	int taille = 1 + (2 * m_portee);
    	int direction = 0;
    	double[] proba = new double[8];
    	double[][] modif = new double[3][3];
    	
    	//Tableau résultat du scan grâce au senseur
    	int[][] map = new int[taille][taille];
    	
		//Position actuelle de la fourmi dans le tableau
		int[] pos = new int[] {m_portee, m_portee};
		
    	//On initialise tous les tableaux
		for(int i = 0; i < 8; i++)
			proba[i] = 0;
		for(int i = 0; i < 9; i++)
			modif[i/3][i%3] = 1;
		for(int i = 0; i < taille; i++)
		{
			for(int j = 0; j < taille; j++)
				map[i][j] = 0;
		}
		//On utilise le senseur pour regarder les environs
		for(int i = 0; i < taille; i++)
		{
			for(int j = 0; j < taille; j++)
			{
				map[i][j] = 0;
			}
		}
		
		//On se sert du senseur pour completer notre tableau
		map = Senseur(taille, map, pos[0], pos[1], GetCase());
		
    	//Une fois qu'on connaît les obstacles et les sources à proximité, on regarde si on a accès aux sources en fonction des obstacles.
    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			if(map[i][j] == 2)
    			{
    				//Si il y a une source, on regarde si elle est accessible
    				if((direction = Navigation(map, i, j, m_portee, m_portee)) != 0)
    				{
    					/* On augmente la probabilité d'aller en direction de la source.
    					 * On considère que si une source est accessible, la probabilité d'aller dans cette direction est multipliée par 2.
    					 * On multiplie aussi la probabilité d'aller dans les direction adjacentes par 1.5 s'il n'y a pas d'obstacle.
    					 * Par exemple si une source est disponnible en haut, la fourmi a 2 fois plus de chances d'aller en haut et 1.5 fois 
    					 * plus de chances d'aller en haut à droite et en haut à gauche, sous reserve qu'il n'y ait pas d'obstacle.
    					 */
    					
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
    					if(map[pos[0] + ix - 1][pos[1] + iy - 1] != 3)
    					{
    						modif[ix][iy] *= 2.0;
    					}
    					if(map[pos[0] + ixa - 1][pos[1] + iya - 1] != 3)
    					{
    						modif[ixa][iya] *= 1.5;
    					}
    					if(map[pos[0] + ixb - 1][pos[1] + iyb - 1] != 3)
    					{
    						modif[ixb][iyb] *= 1.5;
    					}
    				}
    			}
    		}
    	}
    	
    	//On évite d'aller sur les obstacles
    	for(int i = 0; i < 9; i++)
    	{
    		if(i == 4)
    			i++;
    		int x = (i / 3) - 1;
    		int y = (i % 3) - 1;
    		if(map[pos[0]+x][pos[1]+y] == 3)
    			modif[1+x][x+y] = 0;
    	}
    	
    	//On calcul la probabilité d'aller sur chaque case
		int[] p = AffectationPoids(m_chemin.getLast());
		/* Repère sur le pavé num
		 * p[0] -> 2
		 * p[1] -> 1
		 * etc...
		 */
		boolean possible = false;
		for(int i = 0; i < 8; i++)
		{
			//On adapte les index pour passer des tableaux 3D aux tableaux 2D
			//On fait correspondre les poids aux directions
			int idg = (i < 4) ? i : i + 1;
			int idx = idg / 3;
			int idy = idg % 3;
			int poids = 0;
			switch(i)
			{
				case 1 :
					poids = p[3];
					break;
				case 2 :
					poids = p[4];
					break;
				case 3 :
					poids = p[5];
					break;
				case 4 :
					poids = p[2];
					break;
				case 5 :
					poids = p[6];
					break;
				case 6 :
					poids = p[7];
					break;
				case 8 :
					poids = p[0];
					break;
			}
			//On évite d'aller en arrière et on évite les obstacles
			proba[i] = ((poids * modif[idx][idy]) == 0) ? 0 : (p[i] * modif[idx][idy]) + GetPheroAdj(i);
			if(proba[i] != 0)
				possible = true;
		}
		//Si et seulement si on est dans une impasse, on fait demi-tour
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
    private int Navigation(int[][] map, int x, int y, int i, int j)
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
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller à droite avant d'aller en bas
					a = i;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -2;
					a = i + 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -4;
				}
				else
				{
					//On essaye d'aller en bas avant d'aller à droite
					a = i + 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -4;
					a = i;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -2;
				}
			}
			else if(y < j)
			{
				//On veut une colonne plus à gauche
				//On essaye d'aller sur la case en bas à gauche
				a = i + 1;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 1;
				if((j - y) > (x - i))
				{
					//On essaye d'aller à gauche avant d'aller en bas
					a = i;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 2;
					a = i + 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -4;
				}
				else
				{
					//On essaye d'aller en bas avant d'aller à gauche
					a = i + 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -4;
					a = i;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 2;
				}
			}
			else
			{
				//On est sur la bonne colonne
				//On essaye d'abbord d'aller en bas
				a = i + 1;
				b = j;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -4;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en bas à droite avant d'aller en bas à gauche
					a = i + 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -3;
					a = i + 1;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 1;
				}
				else
				{
					//On essaye d'aller en bas à gauche avant d'aller en bas à droite
					a = i + 1;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 1;
					a = i + 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
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
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -1;
				if((y - j) > (x - i))
				{
					//On essaye d'aller à droite avant d'aller en haut
					a = i;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -2;
					a = i - 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 4;
				}
				else
				{
					//On essaye d'aller en haut avant d'aller à gauche
					a = i - 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 4;
					a = i;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -2;
				}
			}
			else if(y < j)
			{
				//On veut une colonne plus à gauche
				//On essaye d'aller en haut à gauche
				a = i - 1;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller à gauche avant d'aller en haut
					a = i;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 2;
					a = i - 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 4;
				}
				else
				{
					//On essaye d'aller en haut avant d'aller à gauche
					a = i - 1;
					b = j;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 4;
					a = i;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 2;
				}
			}
			else
			{
				//On est sur la bonne colonne
				//On essaye d'abbord d'aller en haut
				a = i - 1;
				b = j;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 4;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut à droite avant d'aller en haut à gauche
					a = i - 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -1;
					a = i - 1;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 3;
				}
				else
				{
					//On essaye d'aller en haut à gauche avant d'aller en haut à droite
					a = i - 1;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 3;
					a = i - 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
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
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut à droite avant d'aller en bas à droite
					a = i - 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -1;
					a = i + 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -3;
				}
				else
				{
					//On essaye d'aller en bas à droite avant d'aller en haut à droite
					a = i + 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return -3;
					a = i - 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 1;
				}
			}
			else
			{
				//On veut une colonne plus à gauche
				//On essaye d'abbord d'aller en gauche
				a = i;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut à gauche avant d'aller en bas à gauche
					a = i - 1;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 3;
					a = i + 1;
					b = j - 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 1;
				}
				else
				{
					//On essaye d'aller en bas à gauche avant d'aller en haut à gauche
					a = i + 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 1;
					a = i - 1;
					b = j + 1;
					if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
						return 3;
				}
			}
		}
		return 0;
    }
}
