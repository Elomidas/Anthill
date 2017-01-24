import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Plateau {

    private Case[][] m_tabCase;

    public Plateau()
    {
        int i = 1;
        int j = 0;

        try {
            File f = new File("./data/map.txt");
            FileReader fr = new FileReader(f);
            try {
                int c = fr.read();
                //System.out.print(c);
                while (c != -1)
                {
                    if (c == 13 ) {
                        c = fr.read();
                        if (c== 10)
                        {
                            i++;
                            j = 0;
                        }

                    }
                    else
                    {
                        j++;
                    }
                    c = fr.read();
                }
                System.out.println(i + " " + j);
                this.m_tabCase=new Case[ i ][ j ];


        } catch (IOException exception) {
            System.out.println("Erreur lecture caract√®re");
        }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas √©t√© trouv√©");
        }


    }

    Plateau(int h, int l)
    {
        this.m_tabCase = new Case[h][l];
    }
    
    protected boolean Correct(char src, char obs, char frm, char[][] tab)
    {
    	char[][] map = new char[tab.length][tab[0].length];
    	int nbSrc = 0;
    	int nbFrm = 0;
    	for(int i = 0; i < tab.length; i++)
    	{
    		for(int j = 0; j < tab[0].length; j++)
    		{
    			map[i][j] = 'X';
    			if(tab[i][j] == frm)
    				nbFrm++;
    			else if(tab[i][j] == src)
    				nbSrc++;
    		}
    	}
    	int frmbis = nbFrm;
    	int srcbis = nbSrc;
    	map = Verif(src, obs, frm, tab, map, 0, 0);
    	//Analyse du rÈsultat
    	for(int i = 0; i < tab.length; i++)
    	{
    		for(int j = 0; j < tab[0].length; j++)
    		{
    			if(map[i][j] == frm)
    				nbFrm--;
    			else if(tab[i][j] == src)
    				nbSrc--;
    		}
    	}
    	//Conclusion
    	if((nbSrc == 0) && (nbFrm == 0) && (frmbis == 1) && (nbSrc > 0))
    		return true;
    	else
    	{
    		if(nbSrc != 0)
    			System.out.println("Il y a " + nbSrc + " sources inaccessibles.");
    		if(frmbis > 1)
    			System.out.println("Il y a " + frmbis + " fourmiliËres au lieu d'une seule.");
    		if(frmbis == 0)
    			System.out.println("Il n'y a pas de fourmiliËre.");
    		if(srcbis == 0)
    			System.out.println("Il n'y a pas de source.");
    	}
    	return false;
    }

    protected char[][] Verif(char src, char obs, char frm, char[][] tab, char[][] map, int x, int y)
    {
    	if((x >= 0) && (x < map.length) && (y >= 0) && (y < map[0].length) && (map[x][y] == 'X'))
    	{
    		//On ne teste cette case que si elle n'a pas dÈja ÈtÈ testÈe
    		if(tab[x][y] == obs)
    			map[x][y] = 'O';
    		else
    		{
    			//Si la case n'est pas un obstacle, on traite ses voisines
    			if(tab[x][y] == src)
    				map[x][y] = 'S';
    			if(tab[x][y] == frm)
    				map[x][y] = 'F';
    			//On vÈrifie toutes les cases voisines
    			for(int i = -1; i < 2; i++)
    			{
    				for(int j = -1; j < 2; j++)
    				{
    					if((i != 2) && (j != 2))
    						map = Verif(src, obs, frm, tab, map, x+i, y+j);
    				}
    			}
    		}
    	}
    	return map;
    }


    public Case[][] getM_tabCase() {
        return m_tabCase;
    }

    public Case getM_tabCase(int h, int l) {
        return m_tabCase[h][l];
    }

    public void setM_tabCase(Case m_tabCase, int h, int l) {
        this.m_tabCase[h][l] = m_tabCase;
    }

    public void Afficher()
    {
        Case [][] tabCase = getM_tabCase();
        for (int i = 0; i < tabCase.length ; i++)
        {
            for (int j = 0; j < tabCase[i].length ; j++)
            {
                getM_tabCase(i,j).Afficher();
            }
            System.out.println();
        }
    }

    public void Initialisation() {

        int i =0, j=0;
        Case[] caseAdj = new Case[8];

        try {
            File f = new File("./data/map.txt");
            FileReader fr = new FileReader(f);
            try {
                int c = fr.read();
                //System.out.print(c);
                while ((c != -1) && (i < m_tabCase.length))
                {
                    if ((c != 13 )&& (c!= 10) )
                    {

                        switch (c){
                            case '#' : m_tabCase[i][j] = new Obstacle();
                                break;
                            case 'F' : m_tabCase[i][j] = new Fourmiliere(0);
                                break;
                            case 'S' : m_tabCase[i][j] = new Source();
                                break;
                            default: m_tabCase[i][j] = new CaseVide();

                        }
                        j++;
                        if (j == m_tabCase[i].length) {
                            i++;
                            j = 0;
                        }

                    }
                    c = fr.read();
                }

            } catch (IOException exception) {
                System.out.println("Erreur lecture caract√®re");
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Le fichier n'a pas √©t√© trouv√©");
        }

        //Pour le coin en haut √† gauche, on lui attribue des cases adjacentes √† droite, en bas, et en bas √† droite :
        m_tabCase[0][0].setM_case_adj(getM_tabCase(0,1),4);
        m_tabCase[0][0].setM_case_adj(getM_tabCase(1,1),7);
        m_tabCase[0][0].setM_case_adj(getM_tabCase(1,0),6);
        
        //Pour le coin en haut ‡ droite, on lui attribue des cases ‡ gauche, en bas, et en bas ‡ gauche
        m_tabCase[0][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(0,m_tabCase[0].length-2),3);
        m_tabCase[0][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(1,m_tabCase[0].length-2),5);
        m_tabCase[0][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(1,m_tabCase[0].length-1),6);
        
        //Pour le coin en bas ‡ gauche, ...
        m_tabCase[m_tabCase.length-1][0].setM_case_adj(getM_tabCase(m_tabCase.length-2,0),1);
        m_tabCase[m_tabCase.length-1][0].setM_case_adj(getM_tabCase(m_tabCase.length-2,1),2);
        m_tabCase[m_tabCase.length-1][0].setM_case_adj(getM_tabCase(m_tabCase.length-1,1),4);
        
        //Pour le coin en bas ‡ droite, ...
        m_tabCase[m_tabCase.length-1][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(m_tabCase.length-1,m_tabCase[0].length-2),3);
        m_tabCase[m_tabCase.length-1][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(m_tabCase.length-2,m_tabCase[0].length-2),0);
        m_tabCase[m_tabCase.length-1][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(m_tabCase.length-2,m_tabCase[0].length-1),1);
        
        //Les lignes
        for(j=1;j<m_tabCase[0].length-1;j++)
        {
        	//Ligne du haut
        	m_tabCase[0][j].setM_case_adj(getM_tabCase(0,j-1),3);
        	m_tabCase[0][j].setM_case_adj(getM_tabCase(1,j-1),5);
        	m_tabCase[0][j].setM_case_adj(getM_tabCase(1,j),6);
        	m_tabCase[0][j].setM_case_adj(getM_tabCase(1,j+1),7);
        	m_tabCase[0][j].setM_case_adj(getM_tabCase(0,j+1),4);
        	
        	//Ligne du bas 
        	m_tabCase[m_tabCase.length-1][j].setM_case_adj(getM_tabCase(m_tabCase.length-1,j-1),3);
        	m_tabCase[m_tabCase.length-1][j].setM_case_adj(getM_tabCase(m_tabCase.length-2,j-1),0);
        	m_tabCase[m_tabCase.length-1][j].setM_case_adj(getM_tabCase(m_tabCase.length-2,j),1);
        	m_tabCase[m_tabCase.length-1][j].setM_case_adj(getM_tabCase(m_tabCase.length-2,j+1),2);
        	m_tabCase[m_tabCase.length-1][j].setM_case_adj(getM_tabCase(m_tabCase.length-1,j+1),4);
        }
        for(i=0;i<m_tabCase.length;i++)
        {
        	//Colonne de gauche
        	m_tabCase[i][0].setM_case_adj(getM_tabCase(i-1,0),1);
        	m_tabCase[i][0].setM_case_adj(getM_tabCase(i-1,1),2);
        	m_tabCase[i][0].setM_case_adj(getM_tabCase(i,1),4);
        	m_tabCase[i][0].setM_case_adj(getM_tabCase(i+1,1),7);
        	m_tabCase[i][0].setM_case_adj(getM_tabCase(i+1,0),6);
        	
        	
        	//Colonne de droite 
        	m_tabCase[i][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(i-1,m_tabCase[0].length-1),1);
        	m_tabCase[i][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(i-1,m_tabCase[0].length-2),0);
        	m_tabCase[i][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(i,m_tabCase[0].length-2),3);
        	m_tabCase[i][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(i+1,m_tabCase[0].length-2),5);
        	m_tabCase[i][m_tabCase[0].length-1].setM_case_adj(getM_tabCase(i+1,m_tabCase[0].length-1),6);
        }
        
        //Interieur du plateau
        for (i=0 ; i < m_tabCase.length ; i++)
        {
            for (j=0; j < m_tabCase[i].length; j++)
            {
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j-1),0);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j),1);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j+1),2);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i,j-1),3);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i,j+1),4);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j-1),5);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j),6);
            	m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j+1),7);
            }
        }
        //m_tabCase[0][0].getM_case_adj(4).Afficher();
}

    public void DecrementePheroPlateau()
    {
        for (int i = 0; i< m_tabCase.length ;i++)
        {
            for (int j = 0; j< m_tabCase.length ;j++)
            {

            }
        }
    }


}
