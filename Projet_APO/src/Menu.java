import java.util.Iterator;
import java.util.Scanner;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {

	private static ArrayList<String> m_maps;
	private String m_map_choisie;
	private int m_nbFourmis;
	private int m_nbFourmisSenseurs;
	private int m_nbFourmisOrientation;
	
	public Menu()
	{
		m_maps = new ArrayList<String>();
		m_map_choisie = new String();
		m_nbFourmis = 0;
		m_nbFourmisSenseurs = 0;
		m_nbFourmisOrientation = 0;
		
	}
	
	public ArrayList<String> GetMaps()
	{
		return m_maps;
	}
	
	public String GetMap()
	{
		return m_map_choisie;
	}
	
	public int GetFourm()
	{
		return m_nbFourmis;
	}
	
	public int GetFourmS()
	{
		return m_nbFourmisSenseurs;
	}
	
	public int GetFourmO()
	{
		return m_nbFourmisOrientation;
	}
	
	public void SetMaps(ArrayList<String> maps)
	{
		m_maps = maps;
	}
	
	public void SetMap(String map)
	{
		this.m_map_choisie = map;
	}
	
	public void SetNbFourm(int nb)
	{
		this.m_nbFourmis = nb;
	}
	
	public void SetNbFourmS(int nb)
	{
		this.m_nbFourmisSenseurs = nb;
	}
	
	public void SetNbFourmO(int nb)
	{
		this.m_nbFourmisOrientation = nb;
	}
	
	public void AffMenu()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans Anthill !");
		System.out.println("Voici la liste de toutes les cartes disponibles pour la simulation :");
		try
		{			
			Menu.testDirectoryStream();
		}
		catch(IOException e)
		{
			System.out.println("Erreur : " + e.getMessage() );
		}
		for(int i=0;i<m_maps.size();i++)
		{
			System.out.println(i+1 +" : "+ m_maps.get(i));
		}
		System.out.println("Sélectionnez le numéro de la carte");
		int numMap = sc.nextInt();
		while((0>=numMap) || (numMap>m_maps.size()))
		{
			System.out.println("Veuillez choisir un numéro de carte valide");
			numMap = sc.nextInt();
		}
		m_map_choisie = m_maps.get(numMap-1);
		System.out.println("Combien de fourmis basiques voulez-vous?");
		m_nbFourmis = sc.nextInt();
		
		System.out.println("Combien de fourmis avec senseur voulez-vous?");
		m_nbFourmisSenseurs = sc.nextInt();
		
		System.out.println("Combien de fourmis avec orientation voulez-vous?");
		m_nbFourmisOrientation = sc.nextInt();
		sc.close();
	}
	
	public static void testDirectoryStream() throws IOException 
	{
		Path jdkPath = Paths.get("C:/documents/travail/info/Anthill/Projet_APO/data");
		DirectoryStream<Path> stream = Files.newDirectoryStream(jdkPath);
		try 
		{
			Iterator<Path> iterator = stream.iterator();
			while(iterator.hasNext()) 
			{
				Path p = iterator.next();
				String map = p.toString();
				map = map.replace("C:\\documents\\travail\\info\\Anthill\\Projet_APO\\data\\","");
				m_maps.add(map);
			}
		} 
		finally 
		{
			stream.close();
		}
	}
}
