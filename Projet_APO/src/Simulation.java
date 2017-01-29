import java.util.ArrayList;

public class Simulation 
{
	private Plateau m_plateau;
	private ArrayList<Fourmi> m_listeFourmis;

	//Constructeur
	public Simulation()
	{
		this.m_plateau = new Plateau();
		this.m_listeFourmis = new ArrayList<Fourmi>();
	}

	/* Constructeur surcharge
     * parametres :
     *  > int : nombre de fourmis demandÃ©es
     *  > int : nombre de fourmis Senseur demandÃ©es
     *  > int : nombre de fourmis Orientation demandÃ©es
     *  > String : nom du fichier du plateau
     */
	public Simulation(int nbFourmis,int nbFourmisSenseur, int nbFourmisOrientation, int porteeSenseur, String map)
	{
		this.m_plateau = new Plateau(map);
		this.m_listeFourmis = new ArrayList<Fourmi>();
		for(int i=0;i<nbFourmis;i++)
		{
			m_listeFourmis.add(new Fourmi(m_plateau.GetFourmiliere()));
		}
		for(int i=0;i<nbFourmisSenseur;i++)
		{
			m_listeFourmis.add(new FourmiSenseur(m_plateau.GetFourmiliere(),porteeSenseur));
		}
		for(int i=0;i<nbFourmisOrientation;i++)
		{
			m_listeFourmis.add(new FourmiOrientation(m_plateau.GetFourmiliere()));
		}
	}

	public Plateau getM_plateau() {
		return m_plateau;
	}

	public void setM_plateau(Plateau m_plateau) {
		this.m_plateau = m_plateau;
	}

	public ArrayList<Fourmi> getM_listeFourmis() {
		return m_listeFourmis;
	}

	public void setM_listeFourmis(ArrayList<Fourmi> m_listeFourmis) {
		this.m_listeFourmis = m_listeFourmis;
	}

	/* Change l'Ã©tat de toutes les fourmies
     */
	public void StartSimulation()
	{

		for(int k=0;k<m_listeFourmis.size();k++)
		{
			m_listeFourmis.get(k).Start();
		}
	}

	//Affiche le plateau en foction de chaque case et en fonction de la position de chaque fourmi
	public void Afficher()
	{
		Case [][] tabCase = m_plateau.getM_tabCase();
		for (int i = 0; i < tabCase.length ; i++)
		{
			for (int j = 0; j < tabCase[i].length ; j++)
			{
				boolean b = false;
				Fourmi f;
				for(int k=0;k<m_listeFourmis.size();k++)
				{
					f=m_listeFourmis.get(k);
					// si une fourmie parmis toute la liste a pour coordonÃ©es i et j alors vrai
					if ((f.GetCase().getM_abcisse() == j) && (f.GetCase().getM_ordonnee() == i))
					{
						b=true;
					}

				}
				// si il y a une fourmi on affiche f sinon on affiche la case
				if(b)
				{
					System.out.print(" f ");
				}
				else
				{
					m_plateau.getM_tabCase(i,j).Afficher();
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	// Lance la fonction action pour chaque fourmi de la liste
	public void ActionSimul()
	{
		for(int k=0;k<m_listeFourmis.size();k++)
		{
			m_listeFourmis.get(k).Action(m_plateau.SuppSource());
		}
	}

	//DÃ©crÃ©mente les phÃ©romones de chaque case du plateau
	public void DecrementerPhero()
	{
		Plateau p = getM_plateau();
		for (int i = 0; i < p.getM_tabCase().length ; i++)
		{
			for (int j = 0; j < p.getM_tabCase()[i].length; j++)
			{
				if (( p.getM_tabCase()[i][j] instanceof CaseVide) && (p.getM_tabCase()[i][j].getM_pheromone() > 0))
				{
					p.getM_tabCase()[i][j].DecrementePheromone();
				}
			}

		}

	}
	
	//Teste si la simulation est terminée
	public boolean Fini()
	{
		boolean b=true;
		for(int i=0;i<m_listeFourmis.size();i++)
		{
			if(!m_listeFourmis.get(i).Fini())
				b=false;
		}
		return b;
	}

	//Simulation permettant de répéter en boucle le déplacement de chaque fourmi
	public void FourmiSimulation()
	{
		StartSimulation();
		//Affichage de la carte au départ de la simulation
		Afficher();
		while((!Thread.interrupted()) && (!Fini()))
		{
			//Pause entre chaque boucle
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.out.println("Erreur : " + e.getMessage());
			}
			//Actions et affichage
			ActionSimul();
			Afficher();
			DecrementerPhero();
		}
	}

}
