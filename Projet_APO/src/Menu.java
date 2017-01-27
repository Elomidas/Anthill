import java.util.Iterator;
import java.util.Scanner;
import java.nio.file.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
			Menu.ChercheFichier();
		}
		catch(IOException e)
		{
			System.out.println("Erreur : " + e.getMessage() );
		}
		for(int i=0;i<m_maps.size();i++)
		{
			System.out.println(i+1 +" : "+ m_maps.get(i));
			Menu.AffCarte(i);
		}
		System.out.println("SÈlectionnez le numÈro de la carte");
		int numMap = sc.nextInt();
		while((0>=numMap) || (numMap>m_maps.size()))
		{
			System.out.println("Veuillez choisir un numÈro de carte valide");
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
	
	public static void AffCarte(int num)
	{
		//int i =0, j=0;
        try {
            File f = new File("./data/" + m_maps.get(num));
            FileReader fr = new FileReader(f);
            try 
            {
                int c = fr.read();
                //System.out.print(c);
                while (c != -1)
                {
                	
                	
                    if ((c == 13 )&& (c == 10))
                    {
                    	System.out.println( (char)c );
                    	c = fr.read();
                    	/*
                        j++;
                        if (j == m_tabCase[i].length) {
                            i++;
                            j = 0;
                        }
						*/
                    }
                    else
                    	System.out.print( (char)c );
                    c = fr.read();
                    
                }
                fr.close();

            } catch (IOException exception) {
                System.out.println("Erreur lecture caract√®re");
            }
	        }
	        catch (FileNotFoundException exception)
	        {
            System.out.println("Le fichier n'a pas √©t√© trouv√©");
	        }
        	finally
        	{
        		System.out.println("");
        		System.out.println("");
        	}
	}
	
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
				m_maps.add(map);
			}
		} 
		finally 
		{
			lect.close();
		}
	}
}
