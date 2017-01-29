import java.util.ArrayList;

/**
 * Created by Martial TARDY on 05/01/2017.
 */

/* Fourmi se dirigeant de maniere plus efficace vers la fourmiliere lors du retour
 * Pas vraiment efficace s'il y a plusieurs obstacles alignes en travers du chemin.
 */

public class FourmiOrientation extends Fourmi 
{
	private boolean m_traite;
	
	public FourmiOrientation()
	{
		super();
		m_traite = false;
	}

    /* Constructeur surcharge
     * parametres :
     *  > Case : Case de départ de la fourmi
     */
	public FourmiOrientation(Case c)
	{
		super(c);
		m_traite = false;
	}
	
	/* Redefinition de la fonction Retour() de la Fourmi classique
	 * parametres :
	 *  > boolean : indique a la Fourmi si elle doit s'arreter a la foumiliere a son arrivee ou si elle doit retourner chercher une source
	 */
	public void Retour(boolean b)
	{
		if(!this.FourmTrouvee(b))
    	{
			if(!m_traite)
				CheminOpti();
			super.Retour(b);
    	}
		else m_traite = false;
	}
	
	/* Optimise le chemin de retour de la fourmi
	 * Permet a la fourmi d'eviter de faire des boucles lors de son retour a la fourmiliere
	 */
	private void CheminOpti()
	{
		//Pour chaque case sur laquelle on a ete, on va verifie que nous ne somme pas a cote d'une case vue plus tôt dans le trajet
		//Si on remarque que la fourmi a ete sur deux cases adjacentes durant son parcours, on elimine tout le chemin effectue entre les deux
		int s = m_chemin.size() + 1;
		int[] dir = new int[s - 1];
		int[][] pos = new int[s][2];
		pos[0] = new int[] {0, 0};
		for(int i = 0; !m_chemin.isEmpty(); i++)
		{
			dir[i] = m_chemin.removeFirst();
			pos[i+1] = Dep(dir[i], pos[i]);
		}
		for(int a = 1; a <= pos.length; a++)
		{
			int i = pos.length - a;
			for(int j = 0; j < (i - 1); j++)
			{
				int diffX = Math.abs(pos[i][0] - pos[j][0]);
				int diffY = Math.abs(pos[i][1] - pos[j][1]);
				if(Math.max(diffX, diffY) <= 1)
				{
					s = (s - i) + j + 1;
					int[][] ptemp = new int[s][2];
					for(int k = 0; k < s; k++)
					{
						ptemp[k][0] = (k <= j) ? pos[k][0] : pos[(k - j) + i - 1][0];
						ptemp[k][1] = (k <= j) ? pos[k][1] : pos[(k - j) + i - 1][1];
					}
					pos = ptemp;
					//On recommence la boucle avec les nouveaux indices
					a--;
					j = i;
				}
			}
		}
		for(int i = 1; i < pos.length; i++)
		{
			int d = InvDep(pos[i-1], pos[i]);
			m_chemin.add(d);
		}
		m_traite = true;
	}
	
	//Retourne la nouvelle position de la fourmi grace a son ancienne position et la direction qu'elle a prise
	private int[] Dep(int dir, int[] pos)
	{
		int[] p = new int[] {pos[0], pos[1]};
		switch(dir)
		{
			case -4:
				p[0]++;
				break;
			case -3:
				p[0]++;
				p[1]++;
				break;
			case -2:
				p[1]++;
				break;
			case -1:
				p[0]--;
				p[1]++;
				break;
			case 4:
				p[0]--;
				break;
			case 3:
				p[0]--;
				p[1]--;
				break;
			case 2:
				p[1]--;
				break;
			case 1:
				p[0]++;
				p[1]--;
				break;
			default:
				break;
		}
		return p;
	}
	
	/* Donne la direction prise par la fourmi entre deux positions
	 * parametres
	 *  > int[] : position a l'instant t0
	 *  > int[] : position a l'instant t1
	 * retour :
	 *  > int : direction choisie par la fourmi
	 */
	private int InvDep(int[] p0, int[] p1)
	{
		int x = p1[0] - p0[0];
		int y = p1[1] - p0[1];
		if(x == -1)
		{
			//La fourmi est allee vers le haut
			if(y == -1)
				return 3;
			if(y == 0)
				return 4;
			return -1;
		}
		if(x == 0)
		{
			if(y == -1)
				return 2;
			return -2;
		}
		if(y == -1)
			return 1;
		if(y == 0)
			return -4;
		return -3;
	}
}
