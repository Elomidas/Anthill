/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class FourmiSenseur extends Fourmi 
{

    protected int m_portee;
    //Portee par defaut d'une fourmi avec senseur
    protected static final int PORTEE = 2;
    
    public FourmiSenseur()
    {
    	super();
    	m_portee = PORTEE;
    }
    
    public FourmiSenseur(Case c)
    {
    	super(c);
    	m_portee = PORTEE;
    }

    public int getPortee() 
    {
        return m_portee;
    }

    public void setPortee(int portee) 
    {
        this.m_portee = (portee > 0) ? portee : m_portee;
    }
    
    protected int[][] Senseur(int[][] map, Case c, int x, int y)
    {
    	/*On utilisera les codes suivants pour les cases :
    	 * 0 -> Inconnue
    	 * 1 -> Libre
    	 * 2 -> Source
    	 * 3 -> Obstacle
    	 * 4 -> Fourmiliere
    	 * 
    	 * On va maperver la carte � la "fa�on d�mineur" par r�cursivit�
    	 * Si une case n'est pas un maptacle, la fourmi peut voir les cases autour.
    	 * Si une case est un maptacle, la fourmi ne voit pas les cases autour.
    	 */
    	
    	//On ne traite la case que si elle ne l'a pas encore �t� et qu'elle est dans le tableau
    	if((x >= 0) && (y >= 0) && (x < map.length) && (y < map[0].length) && (map[x][y] == 0))
    	{
    		//Si la case contient un maptacle, on s'arr�te l�
    		if(!c.Penetrable())
    		{
    			map[x][y] = 3;
    		}
    		else
    		{
    			if(c instanceof Source)
    			{
    				map[x][y] = 2;
    				System.out.println("Source en ("  + x + ", " + y + ").");
    			}
    			else if(c instanceof Fourmiliere)
    			{
    				map[x][y] = 4;
    			}
    			else
				{
    				map[x][y] = 1;
				}
    			//Sinon, on regarde une par une les cases voisines
    			//Haut gauche
    			map = Senseur(map, c.CaseVoisine(3), x-1, y-1);
    			//Haut
    			map = Senseur(map, c.CaseVoisine(4), x-1, y);
    			//Haut droite
    			map = Senseur(map, c.CaseVoisine(-1), x-1, y+1);
    			//Gauche
    			map = Senseur(map, c.CaseVoisine(2), x, y-1);
    			//Droite
    			map = Senseur(map, c.CaseVoisine(-2), x, y+1);
    			//Bas gauche
    			map = Senseur(map, c.CaseVoisine(1), x+1, y-1);
    			//Bas
    			map = Senseur(map, c.CaseVoisine(-4), x+1, y);
    			//Haut gauche
    			map = Senseur(map, c.CaseVoisine(-3), x+1, y+1);
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
    	
    	//Tableau r�sultat du scan gr�ce au senseur
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
		map = Senseur(map, GetCase(), m_portee, m_portee);
		
    	//Une fois qu'on conna�t les obstacles et les sources � proximit�, on regarde si on a acc�s aux sources en fonction des obstacles.
    	for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			if(map[i][j] == 2)
    			{
    				//Si il y a une source, on regarde si elle est accessible
    				if((direction = Navigation(map, i, j, m_portee, m_portee)) != 0)
    				{
    					/* On augmente la probabilit� d'aller en direction de la source.
    					 * On consid�re que si une source est accessible, la probabilit� d'aller dans cette direction est multipli�e par 2.
    					 * On multiplie aussi la probabilit� d'aller dans les direction adjacentes par 1.5 s'il n'y a pas d'obstacle.
    					 * Par exemple si une source est disponnible en haut, la fourmi a 2 fois plus de chances d'aller en haut et 1.5 fois 
    					 * plus de chances d'aller en haut � droite et en haut � gauche, sous reserve qu'il n'y ait pas d'obstacle.
    					 */
    					
    					//On effectue un changement d'index afin de d�duire de la direction les cases � modifier
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
    	
    	//On �vite d'aller sur les obstacles
    	for(int i = 0; i < 9; i++)
    	{
    		if(i == 4)
    		{
    			i++;
    		}
    		int x = (i / 3) - 1;
    		int y = (i % 3) - 1;
    		//Si la case est un obstacle, on evite d'y aller
    		if(map[pos[0]+x][pos[1]+y] == 3)
    			modif[1+x][1+y] = 0;
    	}
    	
    	//On calcul la probabilit� d'aller sur chaque case
    	int[] p;
    	int prec = 0;
    	if(!m_chemin.isEmpty())
    	{
			prec = m_chemin.getLast();
    	}
    	else
    	{
    		int r = (int)((Math.random() * 8) - 4);
    		if(r >= 0)
    			r++;
    		prec = r;
    	}
		p = AffectationPoids(prec);
		/* Rep�re sur le pav� num
		 * p[0] -> 2
		 * p[1] -> 1
		 * etc...
		 * On tourne dans le sense des aiguilles d'une montre.
		 */
		boolean possible = false;
		for(int i = 0; i < 8; i++)
		{
			//On adapte les index pour passer des tableaux 3D aux tableaux 2D
			//On fait correspondre les poids aux directions
			int idx = 0, idy = 0;
			switch(i)
			{
				case 0 :
					idx = 2;
					idy = 1;
					break;
				case 1 :
					idx = 2;
					idy = 0;
					break;
				case 2 :
					idx = 1;
					idy = 0;
					break;
				case 3 :
					idx = 0;
					idy = 0;
					break;
				case 4 :
					idx = 0;
					idy = 1;
					break;
				case 5 :
					idx = 0;
					idy = 2;
					break;
				case 6 :
					idx = 1;
					idy = 2;
					break;
				case 8 :
					idx = 2;
					idy = 2;
					break;
			}
			//On �vite d'aller en arri�re et on �vite les obstacles
			if((p[i] == 0) || (modif[idx][idy] == 0))
				proba[i] = 0;
			else proba[i] = (p[i] * modif[idx][idy]) + GetPheroAdj(i);
			if(proba[i] != 0)
				possible = true;
		}
		//Si et seulement si on est dans une impasse, on fait demi-tour
		if(!possible)
			return -prec;
    	
		double somme = 0;
		for(int i = 0; i < 8; i++)
			somme += proba[i];
		for(int i = 0; i < 8; i++)
		{
			proba[i] /= somme;
		}
		
    	//Fin
    	return GetProba(proba);
    }
    
    /*
     * x, y : coordonn�es que l'on veut atteindre
     * i, j : position de la fourmi
     * return d : direction choisie (0 si innaccessible)
     */
    private int Navigation(int[][] map, int x, int y, int i, int j)
    {
		int a, b;
		if((i == x) && (j == y))
			return 10;
		if(x > i)
		{
			//On veut une ligne inf�rieure
			if(y > j)
			{
				//On veut une colonne plus � droite
				//On essaye d'aller sur la case en bas � droite
				a = i + 1;
				b = j + 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller � droite avant d'aller en bas
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
					//On essaye d'aller en bas avant d'aller � droite
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
				//On veut une colonne plus � gauche
				//On essaye d'aller sur la case en bas � gauche
				a = i + 1;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 1;
				if((j - y) > (x - i))
				{
					//On essaye d'aller � gauche avant d'aller en bas
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
					//On essaye d'aller en bas avant d'aller � gauche
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
					//On essaye d'aller en bas � droite avant d'aller en bas � gauche
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
					//On essaye d'aller en bas � gauche avant d'aller en bas � droite
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
			//On veut une ligne sup�rieure
			if(y > j)
			{
				//On veut une colonne plus � droite
				//On essaye d'aller sur la case en haut � droite
				a = i - 1;
				b = j + 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -1;
				if((y - j) > (x - i))
				{
					//On essaye d'aller � droite avant d'aller en haut
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
					//On essaye d'aller en haut avant d'aller � gauche
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
				//On veut une colonne plus � gauche
				//On essaye d'aller en haut � gauche
				a = i - 1;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller � gauche avant d'aller en haut
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
					//On essaye d'aller en haut avant d'aller � gauche
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
					//On essaye d'aller en haut � droite avant d'aller en haut � gauche
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
					//On essaye d'aller en haut � gauche avant d'aller en haut � droite
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
				//On veut une colonne plus � droite
				//On essaye d'abbord d'aller en droite
				a = i;
				b = j + 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut � droite avant d'aller en bas � droite
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
					//On essaye d'aller en bas � droite avant d'aller en haut � droite
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
				//On veut une colonne plus � gauche
				//On essaye d'abbord d'aller en gauche
				a = i;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut � gauche avant d'aller en bas � gauche
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
					//On essaye d'aller en bas � gauche avant d'aller en haut � gauche
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
