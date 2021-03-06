import java.util.ArrayList;

/**
 * Created by Martial TARDY on 05/01/2017.
 */

/* Fourmi dotee d'un senseur lui permettant de "voir" les environs.
 * Cette fourmi peut detecter les obstacles et ainsi les eviter mais aussi les sources si elles sont dans le
 * champs d'action du senseur et ainsi s'y rendre plus efficacement.
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
    
    /* Constructeur surcharge
     * parametres :
     *  > Case : Case de d�part de la fourmi
     */
    public FourmiSenseur(Case c)
    {
    	super(c);
    	m_portee = PORTEE;
    }
    
    /* Constructeur surcharge
     * parametres :
     *  > Case : Case de d�part de la fourmi
     *  > portee : entier repr�sentant la port�e du senseur
     */
    public FourmiSenseur(Case c, int portee)
    {
    	super(c);
    	m_portee = portee;
    }

    /* Accesseur de la portee du senseur
     * retour :
     *  > int : portee actuelle du senseur
     */
    public int getPortee() 
    {
        return m_portee;
    }

    /* Mutateur de la portee du senseur
     * parametres :
     *  > int portee : nouvelle portee, doit etre superieure a 0
     */
    public void setPortee(int portee) 
    {
        this.m_portee = (portee > 0) ? portee : m_portee;
    }
    
    /* Fonction d'utilisation du senseur
	 * On va observer la carte � la "fa�on d'un demineur" par recursivite
	 * Si la case a deja ete observee, on s'arrete la.
	 * Si une case n'est pas un obstacle, la fourmi peut voir les cases autour, si elle sont dans le champ du senseur.
	 * Si une case est un onstacle, la fourmi ne voit pas les cases autour.
	 * parametres :
	 *  > int[][] 	: carte des cases qui ont deja ete traitees par le senseur
	 *  > Case 		: Case a traiter dans cette fonction
	 *  > int		: ligne dans laquelle dera la representation de la case dans notre resultat
	 *  > int		: colonne dans laquelle dera la representation de la case dans notre resultat
	 * retour :
	 *  > int[][]	: tableau contenant les representations de toutes les cases analysees
	 *    --> On utilisera les codes suivants pour les cases :
	 * 		0 -> Inconnue
	 * 		1 -> Libre
	 * 		2 -> Source
	 * 		3 -> Obstacle
	 * 		4 -> Fourmiliere
     */
    protected int[][] Senseur(int[][] map, Case c, int x, int y)
    {
    	//On ne traite la case que si elle ne l'a pas encore ete et qu'elle est dans le champs d'action du senseur (ie les dimmensions du tableau)
    	if((x >= 0) && (y >= 0) && (x < map.length) && (y < map[0].length) && (map[x][y] == 0))
    	{
    		//Si la case contient un obstacle, on s'arrete la
    		if(!c.Penetrable())
    		{
    			map[x][y] = 3;
    		}
    		else
    		{
    			if(c instanceof Source)
    			{
    				map[x][y] = 2;
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
    
    /* Redefinition de la fonction ChoixDirection() de la Fourmi classique
     * Choisi la direction de la Fourmi en analysant les environs grace au senseur de celle-ci
     * retour :
     *  > int : direction choisie par la Fourmi
     */
    public int ChoixDirection()
    {
    	int taille = 1 + (2 * m_portee);
    	double[] proba = new double[8];
    	int[][] modif = new int[3][3];
    	
    	//Tableau resultat du scan effectue par le senseur
    	int[][] map = new int[taille][taille];
		
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
		
		//On se sert du senseur pour completer notre tableau
		map = Senseur(map, GetCase(), m_portee, m_portee);
		
    	//Une fois qu'on connait les obstacles et les sources a proximite, on regarde si on a acces aux sources en fonction des obstacles.
		int dir = 0;
		for(int i = 0; i < taille; i++)
    	{
    		for(int j = 0; j < taille; j++)
    		{
    			if(map[i][j] == 2)
    			{
    				if((dir = Navigation(map, i, j, m_portee, m_portee)) != 0)
    					return dir;
    			}
    		}
    	}
    	
		//Si aucune n'est accessible, on ameliore le deplacement standard de la fourmi
    	//On evite d'aller sur les obstacles
    	for(int i = 0; i < 9; i++)
    	{
    		if(i == 4)
    		{
    			i++;
    		}
    		int x = (i / 3) - 1;
    		int y = (i % 3) - 1;
    		//Si la case est un obstacle, on evite d'y aller
    		if(map[m_portee + x][m_portee + y] == 3)
    			modif[1+x][1+y] = 0;
    	}
    	
    	//On calcul la probabilite d'aller sur chaque case
    	int[] p;
    	int prec = 0;
    	//Si la fourmi s'est deja deplacee, on recupere la direction du dernier deplacement.
    	//Sinon on choisi une direction aleatoirement
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
		/* Correspondance entre les poids et les directions (sur le pave numerique)
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
			int idx = 0, idy = 0, id = 0;
			switch(i)
			{
				case 0 :
					idx = 2;
					idy = 1;
					id = 6;
					break;
				case 1 :
					idx = 2;
					idy = 0;
					id = 5;
					break;
				case 2 :
					idx = 1;
					idy = 0;
					id = 3;
					break;
				case 3 :
					idx = 0;
					idy = 0;
					id = 0;
					break;
				case 4 :
					idx = 0;
					idy = 1;
					id = 1;
					break;
				case 5 :
					idx = 0;
					idy = 2;
					id = 2;
					break;
				case 6 :
					idx = 1;
					idy = 2;
					id = 4;
					break;
				case 7 :
					idx = 2;
					idy = 2;
					id = 7;
					break;
			}
			//On ignore les pheromones sur certaines cases pour ne pas aller en arriere ni sur le obstacles
			proba[i] = (p[i] + GetPheroAdj(id)) * (double)(modif[idx][idy]);
			//System.out.println(i + " : " + proba[i]);
			if(proba[i] != 0)
				possible = true;
		}
		//Si et seulement si on est dans une impasse, on fait demi-tour
		if(!possible)
			return -prec;
    	
		//On fait en sorte que la somme de nos proba fasse 1
		double somme = 0;
		for(int i = 0; i < 8; i++)
			somme += proba[i];
		for(int i = 0; i < 8; i++)
		{
			proba[i] /= somme;
		}
		
    	//On retourne la direction choisie
    	return GetProba(proba);
    }

    /* Regarde si une case est accessible depuis notre position actuelle en prenant en compte les obstacles
     * parametres :
     *  > int[][]	: tableau representant les environs, meme code que le resultat du senseur pour les elements
     *  > int		: ligne contenant la case que nous voulons atteindre
     *  > int		: colonne contenant la case que nous voulons atteindre
     *  > int		: ligne sur laquelle nous sommes
     *  > int		: colonne sur laquelle nous sommes
     *  > int		: derniere direction empruntee
     * retour :
     *  > int[] : direction dans laquelle aller pour atteindre la case voulue (0 si la case est innaccessible) et nombre de tours avant de l'atteindre
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
				//On essaye d'aller sur la case en bas a droite
				a = i + 1;
				b = j + 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller a droite avant d'aller en bas
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
					//On essaye d'aller en bas avant d'aller a droite
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
				//On essaye d'aller sur la case en bas a gauche
				a = i + 1;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 1;
				if((j - y) > (x - i))
				{
					//On essaye d'aller a gauche avant d'aller en bas
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
					//On essaye d'aller en bas avant d'aller a gauche
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
				//On essaye d'abbord d'aller en bas
				a = i + 1;
				b = j;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -4;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en bas a droite avant d'aller en bas a gauche
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
					//On essaye d'aller en bas a gauche avant d'aller en bas a droite
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
				//On essaye d'aller sur la case en haut a droite
				a = i - 1;
				b = j + 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -1;
				if((y - j) > (x - i))
				{
					//On essaye d'aller a droite avant d'aller en haut
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
					//On essaye d'aller en haut avant d'aller a gauche
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
				//On essaye d'aller en haut a gauche
				a = i - 1;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 3;
				if((y - j) > (x - i))
				{
					//On essaye d'aller a gauche avant d'aller en haut
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
					//On essaye d'aller en haut avant d'aller a gauche
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
				//On essaye d'abbord d'aller en haut
				a = i - 1;
				b = j;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return 4;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut a droite avant d'aller en haut a gauche
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
					//On essaye d'aller en haut a gauche avant d'aller en haut a droite
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
				//On essaye d'abbord d'aller a droite
				a = i;
				b = j + 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut a droite avant d'aller en bas a droite
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
					//On essaye d'aller en bas a droite avant d'aller en haut a droite
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
				//On essaye d'abbord d'aller a gauche
				a = i;
				b = j - 1;
				if((map[a][b] != 3) && (Navigation(map, x, y, a, b) != 0))
					return -2;
				if(Math.random() < 0.5)
				{
					//On essaye d'aller en haut a gauche avant d'aller en bas a gauche
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
					//On essaye d'aller en bas a gauche avant d'aller en haut a gauche
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
