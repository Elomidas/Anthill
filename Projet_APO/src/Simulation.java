import java.util.ArrayList;

public class Simulation 
{
	private Plateau m_plateau;
	private ArrayList<Fourmi> m_listeFourmis;
	
	public Simulation()
	{
		this.m_plateau = new Plateau();
		this.m_listeFourmis = new ArrayList<Fourmi>();
	}
	
	public Simulation(String map)
	{
		this.m_plateau = new Plateau(map);
		this.m_plateau.Initialisation(map);
		this.m_listeFourmis = new ArrayList<Fourmi>();
	}
	
	public Simulation(int nbFourmis,int nbFourmisSenseur, int nbFourmisOrientation, String map)
	{
		this.m_plateau = new Plateau(map);
		this.m_plateau.Initialisation(map);
		this.m_listeFourmis = new ArrayList<Fourmi>();
		for(int i=0;i<nbFourmis;i++)
		{
			m_listeFourmis.add(new Fourmi(m_plateau.GetFourmiliere()));
		}
		for(int i=0;i<nbFourmisSenseur;i++)
		{
			m_listeFourmis.add(new FourmiSenseur(m_plateau.GetFourmiliere()));
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

	public void StartSimulation()
	{

		for(int k=0;k<m_listeFourmis.size();k++)
		{
			m_listeFourmis.get(k).Start();


		}
	}

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
					if ((f.GetCase().getM_abcisse() == j) && (f.GetCase().getM_ordonnee() == i))
					{
						b=true;
					}

				}

				if(b)
				{
					System.out.print("f");
				}
				else
				{
					m_plateau.getM_tabCase(i,j).Afficher();
				}
			}
			System.out.println();
		}
		System.out.println("\n\n");
	}

	public void ActionSimul()
	{
		for(int k=0;k<m_listeFourmis.size();k++)
		{
			m_listeFourmis.get(k).Action(m_plateau.SuppSource());
		}
	}
	public void DecrementerPhero()
	{
		Plateau p = getM_plateau();
		for (int i = 0; i < p.getM_tabCase().length ; i++)
		{
			for (int j = 0; j < p.getM_tabCase()[i].length; j++)
			{
				if (( p.getM_tabCase()[i][j] instanceof CaseVide) && (p.getM_tabCase()[i][j].getM_pheromone() > 0))
				{
					p.getM_tabCase()[i][j].setM_pheromone(p.getM_tabCase()[i][j].getM_pheromone() - 0.1);
				}
			}

		}

	}
	
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

	public void FourmiSimulation()
	{
		StartSimulation();
		while((!Thread.interrupted()) && (!Fini()))
		{
			
			try
			{
				Thread.sleep(500);
			}
			catch(Exception e)
			{
				System.out.println("remond est " + e.getMessage());
			}
			
			ActionSimul();
			Afficher();
			DecrementerPhero();
		}
	}

}
