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
	private int m_porteeSenseur;
	private int m_nbFourmisOrientation;
	
	//Constructeur de base
	public Menu()
	{
		m_maps = new ArrayList<String>();
		m_map_choisie = new String();
		m_nbFourmis = 0;
		m_nbFourmisSenseurs = 0;
		m_porteeSenseur = 0;
		m_nbFourmisOrientation = 0;
		
	}
	
	//Setter et getter
	public int GetPorteeSenseur()
	{
		return m_porteeSenseur;
	}
	
	public void SetPorteeSenseur(int portee)
	{
		m_porteeSenseur = portee;
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
	
	//Fonction affichant le menu
	public void AffMenu()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans Anthill !");
		System.out.println("Voici la liste de toutes les cartes disponibles pour la simulation :");
		try
		{			
			Menu.ChercheFichier();
		}
		catch(IOException e)
		{
			System.out.println("Erreur : " + e.getMessage() );
		}
		for(int i=0;i<m_maps.size();i++)
		{
			System.out.println(i+1 +" : "+ m_maps.get(i));
			Menu.AffCarte(m_maps.get(i));
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
		if(m_nbFourmisSenseurs > 0)
		{
			System.out.println("Quelle portée désirez-vous pour les senseurs  de vos fourmis?");
			m_porteeSenseur = sc.nextInt();
		}
		
		System.out.println("Combien de fourmis avec orientation voulez-vous?");
		m_nbFourmisOrientation = sc.nextInt();
		sc.close();
	}
	
	//Fonction affichant une carte 
	public static void AffCarte(String map)
	{
       char[][] carte = Plateau.Tableau(map);
       for(int i=0;i<carte.length;i++)
       {
    	   for(int j=0;j<carte[i].length;j++)
    	   {
    		   System.out.print(carte[i][j]);
    	   }
    	   System.out.println("");
       }
       System.out.println("");
    	   
    		   
	}
	
	//Fonction permettant de rechercher toutes les cartes contenues dans le dossier data
	public static void ChercheFichier() throws IOException 
	{
		Path repertoire = Paths.get("./data/");
			DirectoryStream<Path> lect = Files.newDirectoryStream(repertoire);
			try 
			{
				Iterator<Path> iterator = lect.iterator();
				while(iterator.hasNext()) 
				{
					Path p = iterator.next();
					String map = p.toString();
					map = map.replace(".\\data\\","");
					if(map.endsWith(".txt"))
						m_maps.add(map);
				}
			} 
			finally 
			{
				lect.close();
			}
		
	}
}
