/**
 * Created by Martial TARDY on 05/01/2017.
 */

/* Fourmi se dirigeant de maniere plus efficace vers la fourmiliere lors du retour
 * Pas vraiment efficace s'il y a plusieurs obstacles alignes en travers du chemin.
 */

public class FourmiOrientation extends Fourmi 
{
	private int m_vertical, m_horizontal;
	private boolean m_r1, m_r2, m_r3;
	
	public FourmiOrientation()
	{
		super();
		m_vertical = m_horizontal = 0;
		m_r1 = m_r2 = m_r3 = true;
	}

    /* Constructeur surcharge
     * parametres :
     *  > Case : Case de départ de la fourmi
     */
	public FourmiOrientation(Case c)
	{
		super(c);
		m_vertical = m_horizontal = 0;
		m_r1 = m_r2 = m_r3 = true;
	}
	
	//Retourne un entier entre -4 et 4, en dehors de 0 et des valeurs passees en parametres
	/* Retourne un nombre aleatoire entre -4 et 4 compris, hors 0 et 0 a 3 autres valeurs
	 * parametres :
	 *  > int : premier nombre a ne pas retourner
	 *  > int : second nombre a ne pas retourner
	 *  > int : troisieme nombre a ne pas retourner
	 * retour :
	 *  > int : entier entre -4 et 4 compris, hors 0 et les entiers passes en parametres
	 */
	private int rand(int a, int b, int c)
	{
		//Donne un nombre entre 0 et 4
		int r = 0 + (int)(Math.random() * 5);
		for(int i = -4; i < 5; i++)
		{
			while((i == 0) || (i == a) || (i == b) || (i == c))
				i++;
			if(r == 0)
				return i;
			else r--;
		}
		return 1;
	}
	
	/* Redefinition de la fonction Retour() de la Fourmi classique
	 * parametres :
	 *  > boolean : indique a la Fourmi si elle doit s'arreter a la foumiliere a son arrivee ou si elle doit retourner chercher une source
	 */
	public void Retour(boolean b)
	{
		if(!this.FourmTrouvee(b))
    	{
			int dir = 0;
			if((m_vertical == 0) && (m_horizontal == 0))
			{
				//On recherche l'itineraire le plus direct
				while(!this.getM_chemin().isEmpty())
				{
					switch(-this.getM_chemin().removeLast())
					{
						case 1 :
							//Bas Gauche
							m_vertical--;
							m_horizontal--;
							break;
							
						case 2 :
							//Gauche
							m_horizontal--;
							break;
						
						case 3 :
							//Haut Gauche
							m_vertical++;
							m_horizontal--;
							break;
						
						case 4 :
							//Haut
							m_vertical++;
							break;
						
						case -4 :
							//Bas
							m_vertical--;
							break;
						
						case -3 :
							//Bas Droite
							m_vertical--;
							m_horizontal++;
							break;
						
						case -2 :
							//Droite
							m_horizontal++;
							break;
						
						case -1 :
							//Haut Droite
							m_vertical++;
							m_horizontal++;
							break;
						
						default:
							break;
					}
				}
				System.out.println("Constat :");
				System.out.println(" > Vertical : " + m_vertical);
				System.out.println(" > Horizontal : " + m_horizontal);
			}
			//On regarde dans quelle direction aller.
			//En fonction du nombre d'échecs prealables, on fait varier la direction
			// -> Hasard si on est bloque
			if(m_vertical > 0)
			{
				//On veut aller en haut
				if(m_horizontal > 0)
				{
					//On veut aller en haut a droite
					/* On essaye d'aller en haut à droite
					 * Si on n'a pas pu aller en haut a droite, on essaye d'aller en haut
					 * Si on n'a pas pu aller en haut, on essaye d'aller a droite
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? -1 : 
							(m_r2 ? 4 : 
								(m_r3 ? -2 : 
									rand(-1, 4, -2)));
				}
				else if(m_horizontal < 0)
				{
					//On veut aller en haut à gauche
					/* On essaye d'aller en haut a gauche
					 * Si on n'a pas pu aller en haut a gauche, on essaye d'aller a gauche
					 * Si on n'a pas pu aller a gauche, on essaye d'aller en haut
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? 3 : 
							(m_r2 ? 2 : 
								(m_r3 ? 4 : 
									rand(3, 2, 4)));
				}
				else
				{
					//On veut juste aller en haut
					/* On essaye d'aller en haut
					 * Si on n'a pas pu aller en haut, on essaye d'aller en haut a gauche
					 * Si on n'a pas pu aller en haut a gauche, on essaye d'aller en haut a droite
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? 4 : 
							(m_r2 ? 3 : 
								(m_r3 ? -1 : 
									rand(4, 3, -1)));
				}
			}
			else if(m_vertical < 0)
			{
				//On cherche à aller en bas
				if(m_horizontal > 0)
				{
					//On veut aller en bas a droite
					/* On essaye d'aller en bas a droite
					 * Si on n'a pas pu aller en bas à droite, on essaye d'aller en bas
					 * Si on n'a pas pu aller en bas, on essaye d'aller a droite
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? -3 : 
							(m_r2 ? -4 : 
								(m_r3 ? -2 : 
									rand(-3, -4, -2)));
				}
				else if(m_horizontal < 0)
				{
					//On veut aller en bas a gauche
					/* On essaye d'aller en bas à gauche
					 * Si on n'a pas pu aller en bas a gauche, on essaye d'aller en bas
					 * Si on n'a pas pu aller en bas, on essaye d'aller a gauche
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? 1 : 
							(m_r2 ? -4 : 
								(m_r3 ? 2 : 
									rand(1, -4, 2)));
				}
				else
				{
					//On veut juste aller en bas
					/* On essaye d'aller en bas
					 * Si on n'a pas pu aller en bas, on essaye d'aller en bas a droite
					 * Si on n'a pas pu aller en bas a droite, on essaye d'aller en bas a gauche
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? -4 : 
							(m_r2 ? -3 : 
								(m_r3 ? 1 : 
									rand(-4, -3, 1)));
				}
				
			}
			else
			{
				if(m_horizontal > 0)
				{
					//On veut aller à droite
					/* On essaye d'aller a droite
					 * Si on n'a pas pu aller a droite, on essaye d'aller en haut a droite
					 * Si on n'a pas pu aller en haut a droite, on essaye d'aller en bas a droite
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? -2 : 
							(m_r2 ? -1 : 
								(m_r3 ? -3 : 
									rand(-2, -1, -3)));
				}
				else
				{
					//On veut aller a gauche
					/* On essaye d'aller a gauche
					 * Si on n'a pas pu aller a gauche, on essaye d'aller en bas a gauche
					 * Si on n'a pas pu aller en bas a gauche, on essaye d'aller en haut a gauche
					 * Si on n'a pu aller dans aucune de ces trois directions, on en choisi une autre au hasard, en dehors de celles-ci
					 */
					dir = m_r1 ? 2 : 
							(m_r2 ? 1 : 
								(m_r3 ? 3 : 
									rand(2, 1, 3)));
				}
			}
			if(GetCase().CaseVoisine(dir).Penetrable())
			{
				//Si on peut aller sur la case choisie (ie si la case choisie n'est pas un obstacle), on y va
				m_r1 = m_r2 = m_r3 = true;
	    		this.SetCase(this.GetCase().CaseVoisine(dir));
	    		this.GetCase().IncrementePheromone();
			}
			else
			{
				//Sinon on indique que l'essai a echoue pour essayer une direction differente la fois suivante.
				if(m_r3)
				{
					if(!m_r2)
						m_r3 = false;
					else if(!m_r1)
						m_r2 = false;
					else m_r1 = false;
				}
			}
    	}
	}
}
