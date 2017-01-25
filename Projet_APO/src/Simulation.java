import java.util.ArrayList;

public class Simulation 
{
	private Plateau m_plateau;
	private ArrayList<Fourmi> m_listeFourmis;
	private boolean m_fini;
	private String m_niveau;
	
	public Simulation()
	{
		this.m_plateau = new Plateau();
		this.m_listeFourmis = new ArrayList<Fourmi>();
		m_fini=false;
		m_niveau = new String("map.txt");
	}
	
	public Simulation(int nbFourmis, String niveau)
	{
		this();
		m_niveau=niveau;
		for(int i=0;i<nbFourmis;i++)
		{
			m_listeFourmis.add(new Fourmi(m_plateau.GetFourmiliere()));
		}
		
	}
}
