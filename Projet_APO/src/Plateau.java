import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Plateau {

    private Case[][] m_tabCase;

    public Plateau(String map)
    {
        int i = 1;
        int j = 0;

        try {
            File f = new File("./data/" + map);
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
                this.m_tabCase=new Case[ i ][ j ];


        } catch (IOException exception) {
            System.out.println("Erreur lecture caractère");
        }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }


    }

    Plateau(int h, int l)
    {
        this.m_tabCase = new Case[h][l];
    }
    
    /* Teste la conformite d'une map
     * -> Une seule fourmiliere
     * -> Au moins une source
     * -> Toutes les sources doivent �tre accessibles depuis la fourmiliere
     * -> Toutes les cases en bordure doivent etre des obstacles ou des cases inaccessibles
     */
    protected boolean Correct(char src, char obs, char frm, char[][] tab)
    {
    	char[][] map = new char[tab.length][tab[0].length];
    	int nbSrc = 0;
    	int nbFrm = 0;
    	int x = 0, y = 0;
    	//On initialise map comme etant que des cases non accessibles
    	for(int i = 0; i < tab.length; i++)
    	{
    		for(int j = 0; j < tab[0].length; j++)
    		{
    			map[i][j] = 'X';
    			if(tab[i][j] == frm) {
                    nbFrm++;
                    x = i;
                    y = j;
                }
    			else if(tab[i][j] == src)
    				nbSrc++;
    		}
    	}
    	//On sauvegarde le nombre total de fourmilieres et de sources sur la map
    	if(nbFrm < 1)
    	{
            System.out.println("Il n'y a pas de fourmiliere.");
    		return false;
    	}
    	if(nbFrm > 1)
    	{
    		System.out.println("Il y a " + nbFrm + " fourmilieres au lieu d'une seule.");
    		return false;
    	}
    	if(nbSrc == 0)
    	{
            System.out.println("Il n'y a pas de source.");
            return false;
    	}
    	int srcbis = nbSrc;
    	//On regarde quelles cases sont accessibles a partir de la fourmiliere
    	map = Verif(src, obs, frm, tab, map, x, y);
    	//Analyse du resultat
    	for(int i = 0; i < tab.length; i++)
    	{
    		for(int j = 0; j < tab[0].length; j++)
    		{
    			if((i == 0) || (i == (tab.length - 1)) || (j == 0) || (j == (tab[0].length)))
    			{
    				//Si une case en bordure est accessible et n'est pas un obstacle, on retourne une erreur.
    				if((map[i][j] != 'X') && (map[i][j] != 'O'))
    				{
    					System.out.println("Votre map n'est pas conforme : sortie possible par la case (" + i + ", " + j + ").");
    					return false;
    				}
    			}
    			if(map[i][j] == frm)
    				nbFrm--;
    			else if(tab[i][j] == src)
    				nbSrc--;
    		}
    	}
    	
    	//Conclusion
    	if(nbSrc == 0)
    		return true;

        if(nbSrc != 0)
            System.out.println("Il y a " + nbSrc + " sources inaccessibles.");
    	return false;
    }

    protected char[][] Verif(char src, char obs, char frm, char[][] tab, char[][] map, int x, int y)
    {
    	if((x >= 0) && (x < map.length) && (y >= 0) && (y < map[0].length) && (map[x][y] == 'X'))
    	{
    		//On ne teste cette case que si elle n'a pas deja ete testee
    		if(tab[x][y] == obs)
    			map[x][y] = 'O';
    		else
    		{
    			//Si la case n'est pas un obstacle, on traite ses voisines
    			if(tab[x][y] == src)
    				map[x][y] = 'S';
    			else if(tab[x][y] == frm)
    				map[x][y] = 'F';
    			else map[x][y] = '.';
    			//On verifie toutes les cases voisines
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

    public void Initialisation(String map) {

        int i =0, j=0;
        char[][] charTab = new char[m_tabCase.length][m_tabCase[0].length];
        try {
            File f = new File("./data/" + map);
            FileReader fr = new FileReader(f);
            try {
                int c = fr.read();
                //System.out.print(c);
                while ((c != -1) && (i < m_tabCase.length))
                {
                    if ((c != 13 )&& (c!= 10) )
                    {
                        charTab[i][j] = (char) c;

                        j++;
                        if (j == m_tabCase[i].length) {
                            i++;
                            j = 0;
                        }

                    }
                    c = fr.read();
                }

            } catch (IOException exception) {
                System.out.println("Erreur lecture caractère");
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Le fichier n'a pas été trouvé");
        }
        boolean b = Correct('S','#','F',charTab);
        System.out.print(b);
        if (b) {

            for (i = 0 ; i<m_tabCase.length ; i++)
            {
                for (j = 0 ;j < m_tabCase[i].length; j++)
                {
                    char c2 = charTab[i][j];
                    switch (c2)
                    {
                        case '#' : m_tabCase[i][j] = new Obstacle(j,i);
                            break;
                        case 'F' : m_tabCase[i][j] = new Fourmiliere(j,i);
                            break;
                        case 'S' : m_tabCase[i][j] = new Source(j,i,50);
                            break;
                        default: m_tabCase[i][j] = new CaseVide(j,i);

                    }
                }
            }

            //Pour le coin en haut à gauche, on lui attribue des cases adjacentes à droite, en bas, et en bas à droite :
            m_tabCase[0][0].setM_case_adj(getM_tabCase(0, 1), 4);
            m_tabCase[0][0].setM_case_adj(getM_tabCase(1, 1), 7);
            m_tabCase[0][0].setM_case_adj(getM_tabCase(1, 0), 6);

            //Pour le coin en haut � droite, on lui attribue des cases � gauche, en bas, et en bas � gauche
            m_tabCase[0][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(0, m_tabCase[0].length - 2), 3);
            m_tabCase[0][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(1, m_tabCase[0].length - 2), 5);
            m_tabCase[0][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(1, m_tabCase[0].length - 1), 6);

            //Pour le coin en bas � gauche, ...
            m_tabCase[m_tabCase.length - 1][0].setM_case_adj(getM_tabCase(m_tabCase.length - 2, 0), 1);
            m_tabCase[m_tabCase.length - 1][0].setM_case_adj(getM_tabCase(m_tabCase.length - 2, 1), 2);
            m_tabCase[m_tabCase.length - 1][0].setM_case_adj(getM_tabCase(m_tabCase.length - 1, 1), 4);

            //Pour le coin en bas � droite, ...
            m_tabCase[m_tabCase.length - 1][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(m_tabCase.length - 1, m_tabCase[0].length - 2), 3);
            m_tabCase[m_tabCase.length - 1][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(m_tabCase.length - 2, m_tabCase[0].length - 2), 0);
            m_tabCase[m_tabCase.length - 1][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(m_tabCase.length - 2, m_tabCase[0].length - 1), 1);

            //Les lignes
            for (j = 1; j < m_tabCase[0].length - 1; j++) {
                //Ligne du haut
                m_tabCase[0][j].setM_case_adj(getM_tabCase(0, j - 1), 3);
                m_tabCase[0][j].setM_case_adj(getM_tabCase(1, j - 1), 5);
                m_tabCase[0][j].setM_case_adj(getM_tabCase(1, j), 6);
                m_tabCase[0][j].setM_case_adj(getM_tabCase(1, j + 1), 7);
                m_tabCase[0][j].setM_case_adj(getM_tabCase(0, j + 1), 4);

                //Ligne du bas
                m_tabCase[m_tabCase.length - 1][j].setM_case_adj(getM_tabCase(m_tabCase.length - 1, j - 1), 3);
                m_tabCase[m_tabCase.length - 1][j].setM_case_adj(getM_tabCase(m_tabCase.length - 2, j - 1), 0);
                m_tabCase[m_tabCase.length - 1][j].setM_case_adj(getM_tabCase(m_tabCase.length - 2, j), 1);
                m_tabCase[m_tabCase.length - 1][j].setM_case_adj(getM_tabCase(m_tabCase.length - 2, j + 1), 2);
                m_tabCase[m_tabCase.length - 1][j].setM_case_adj(getM_tabCase(m_tabCase.length - 1, j + 1), 4);
            }
            for (i = 1; i < m_tabCase.length - 1; i++) {
                //Colonne de gauche
                m_tabCase[i][0].setM_case_adj(getM_tabCase(i - 1, 0), 1);
                m_tabCase[i][0].setM_case_adj(getM_tabCase(i - 1, 1), 2);
                m_tabCase[i][0].setM_case_adj(getM_tabCase(i, 1), 4);
                m_tabCase[i][0].setM_case_adj(getM_tabCase(i + 1, 1), 7);
                m_tabCase[i][0].setM_case_adj(getM_tabCase(i + 1, 0), 6);


                //Colonne de droite
                m_tabCase[i][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(i - 1, m_tabCase[0].length - 1), 1);
                m_tabCase[i][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(i - 1, m_tabCase[0].length - 2), 0);
                m_tabCase[i][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(i, m_tabCase[0].length - 2), 3);
                m_tabCase[i][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(i + 1, m_tabCase[0].length - 2), 5);
                m_tabCase[i][m_tabCase[0].length - 1].setM_case_adj(getM_tabCase(i + 1, m_tabCase[0].length - 1), 6);
            }

            //Interieur du plateau
            for (i = 1; i < m_tabCase.length - 1; i++) {
                for (j = 1; j < m_tabCase[i].length - 1; j++) {
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i - 1, j - 1), 0);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i - 1, j), 1);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i - 1, j + 1), 2);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i, j - 1), 3);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i, j + 1), 4);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i + 1, j - 1), 5);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i + 1, j), 6);
                    m_tabCase[i][j].setM_case_adj(getM_tabCase(i + 1, j + 1), 7);
                }
            }
        }
        else
        {
            System.out.println("erreur de initialisation carte");
        }
        //m_tabCase[0][0].getM_case_adj(4).Afficher();
    }

    public Fourmiliere GetFourmiliere()
    {
    	for(int i=0;i<m_tabCase.length;i++)
    	{
    		for(int j=0;j<m_tabCase[0].length;j++)
    		{
    			if(this.getM_tabCase(i, j) instanceof Fourmiliere)
    			{
    				return (Fourmiliere)this.getM_tabCase(i, j);
    			}
    		}
    	}
    	return new Fourmiliere();
    }

    public void SuppSource()
    {
        for(int i=0;i<m_tabCase.length;i++)
        {
            for(int j=0;j<m_tabCase[0].length;j++)
            {
                if((this.getM_tabCase(i, j) instanceof Source) && (((Source) this.getM_tabCase(i,j)).getM_nourriture() == 0))
                {
                    this.setM_tabCase(new CaseVide(),i,j);
                }
            }
        }

    }


}
