/**
 * Created by Martial TARDY on 05/01/2017.
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
	
	public FourmiOrientation(Case c)
	{
		super(c);
	}
	
	//Retourne un entier entre -4 et 4, en dehors de 0 et des valeurs pass�es en param�tres
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
	
	public void Retour()
	{
		if(!this.FourmTrouvee())
    	{
			int dir = 0;
			if((m_vertical == 0) && (m_horizontal == 0))
			{
				//On recherche le plus court itin�raire
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
						m_vertical--;
						m_horizontal++;
						break;
					
					case -2 :
						m_horizontal++;
						break;
					
					case -1 :
						m_vertical++;
						m_horizontal++;
						break;
					
					default:
						break;
				}
			}
			//On regarde dans quelle direction aller.
			//En fonction du nombre d'�checs pr�alables, on fait varier la direction
			// -> Hasard si on est bloqu�
			if(m_vertical > 0)
			{
				//On veut aller en haut
				if(m_horizontal > 0)
				{
					//On veut aller en haut � droite
					dir = m_r1 ? -1 : 
							(m_r2 ? 4 : 
								(m_r3 ? -2 : 
									rand(-1, 4, -2)));
				}
				else if(m_horizontal < 0)
				{
					//On veut aller en haut � gauche
					dir = m_r1 ? 3 : 
							(m_r2 ? 2 : 
								(m_r3 ? 4 : 
									rand(3, 2, 4)));
				}
				else
				{
					//On veut juste aller en haut
					dir = m_r1 ? 4 : 
							(m_r2 ? 3 : 
								(m_r3 ? -1 : 
									rand(4, 3, -1)));
				}
			}
			else if(m_vertical < 0)
			{
				//On cherche � aller en bas
				if(m_horizontal > 0)
				{
					//On veut aller en bas � droite
					dir = m_r1 ? -3 : 
							(m_r2 ? -4 : 
								(m_r3 ? -2 : 
									rand(-3, -4, -2)));
				}
				else if(m_horizontal < 0)
				{
					//On veut aller en bas � gauche
					dir = m_r1 ? 1 : 
							(m_r2 ? -4 : 
								(m_r3 ? 2 : 
									rand(1, -4, 2)));
				}
				else
				{
					//On veut juste aller en bas
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
					//On veut aller � droite
					dir = m_r1 ? -2 : 
							(m_r2 ? -1 : 
								(m_r3 ? -3 : 
									rand(-2, -1, -3)));
				}
				else
				{
					//On veut aller � gauche
					dir = m_r1 ? 2 : 
							(m_r2 ? 1 : 
								(m_r3 ? 3 : 
									rand(2, 1, 3)));
				}
			}
			if(GetCase().CaseVoisine(dir).Penetrable())
			{
				//Si on peut aller sur la case choisie, on y va
				m_r1 = m_r2 = m_r3 = true;
	    		this.SetCase(this.GetCase().CaseVoisine(dir));
	    		this.GetCase().IncrementePheromone();
			}
			else
			{
				//Sinon on indique que l'essai a �chou�
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
