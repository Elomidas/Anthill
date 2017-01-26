import java.util.ArrayList;
import java.util.Scanner;

public class Simulation 
{
	private Plateau m_plateau;
	private ArrayList<Fourmi> m_listeFourmis;
	private boolean m_fini;
	private String m_niveau;
	
	public Simulation(String map)
	{
		this.m_plateau = new Plateau(map);
		this.m_listeFourmis = new ArrayList<Fourmi>();
		m_fini=false;
		m_niveau = map;
	}
	
	public Simulation(int nbFourmis, String map)
	{
		this.m_plateau = new Plateau(map);
		this.m_listeFourmis = new ArrayList<Fourmi>();
		m_fini=false;
		this.m_plateau.Initialisation(map);
		for(int i = 0; i < nbFourmis; i++)
		{
			m_listeFourmis.add(new Fourmi(m_plateau.GetFourmiliere()));
		}
	}
	
	public Simulation(int nbFourmis, String map, boolean b)
	{
		this.m_plateau = new Plateau(map);
		this.m_listeFourmis = new ArrayList<Fourmi>();
		m_fini=false;
		this.m_plateau.Initialisation(map);
		for(int i = 0; i < nbFourmis; i++)
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

	public boolean isM_fini() {
		return m_fini;
	}

	public void setM_fini(boolean m_fini) {
		this.m_fini = m_fini;
	}

	public String getM_niveau() {
		return m_niveau;
	}

	public void setM_niveau(String m_niveau) {
		this.m_niveau = m_niveau;
	}

	public void StartSimulation()
	{

		for(int k=0;k<m_listeFourmis.size();k++)
		{
			m_listeFourmis.get(k).Start();


		}
	}
	public void StopSimulation()
	{
		for(int k=0;k<m_listeFourmis.size();k++)
		{
			m_listeFourmis.get(k).Stop();
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

	public void FourmiSimulation()
	{
		StartSimulation();
		/*
		String str = "";
		Scanner sc = new Scanner(System.in);
		*/
		while(!Thread.interrupted())
		{
			
			try
			{
				Thread.sleep(500);
			}
			catch(Exception e)
			{
				System.out.println("remond est " + e.getMessage());
			}
			
			//str = sc.next();

			//System.out.println(f.ChoixDirection());
			ActionSimul();
			Afficher();
			DecrementerPhero();

		}
	}

}
