import java.io.*;
import java.util.ArrayList;


/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Plateau {

    private Case[][] m_tabCase;

    //Constructeur
    public Plateau ()
    {
        this.m_tabCase = new Case[60][60];
    }

    /* Constructeur surcharge
     * parametres :
     *  > String : nom du fichier de la carte à charger
     */
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
                fr.close();


        } catch (IOException exception) {
            System.out.println("Erreur lecture caractère");
        }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }

    }
    
    /* Constructeur surcharge
     * parametres :
     *  > int : hauteur
     *  > int : largeur
     */
    Plateau(int h, int l)
    {
        this.m_tabCase = new Case[h][l];
    }
    
    protected char[][] Lecture(ArrayList<Integer> listeSrc, String map)
    {
    	int i = 0;
        int j = 0;
        int hauteur = 0, largeur = 0, nbsrc = 0;
        char[][] tab;

        try 
        {
        	File f = new File("./data/" + map);
            FileReader fr = new FileReader(f);
            try 
            {
            	/* Pour les retour � la ligne on charche le caractere ASCII 10 (LF) et on ignore le caractere ASCII 13 (CR)
            	 * Ainsi le code fonctionne pour les retour a la ligne LF mais aussi pour les CRLF
            	 */
            	
            	//Lecture du fichier
            	char ch;
            	
            	//Hauteur
            	while((ch = (char)(fr.read())) != 10)
            	{
            		if((ch >= '0') && (ch <= '9'))
            			hauteur = (10 * hauteur) + (ch - '0');
            	}
            	
            	//Largeur
            	while((ch = (char)(fr.read())) != 10)
            	{
            		if((ch >= '0') && (ch <= '9'))
            			largeur = (10 * largeur) + (ch - '0');
            	}
            	
            	//Nombre de sources
            	while((ch = (char)(fr.read())) != 10)
            	{
            		if((ch >= '0') && (ch <= '9'))
            			nbsrc = (10 * nbsrc) + (ch - '0');
            	}
            	
            	//Quantite de nourriture par source
            	int nour = 0;
            	while((ch = (char)(fr.read())) != 10)
            	{
            		if((ch >= '0') && (ch <= '9'))
            			nour = (10 * nour) + (ch - '0');
            		if(ch == ' ')
            		{
            			//On change de nombre
            			//On l'ajoute au tableau
            			listeSrc.add(nour);
            			//On remet la quantite a 0
            			nour = 0;
            		}
            	}
            	
            	//Resultats
            	System.out.println("H = " + hauteur + "\nL = " + largeur + "\nS = " + nbsrc);
            	m_tabCase = new Case[hauteur][largeur];
            	tab = new char[hauteur][largeur];
            	
            	//Lecture de la carte
            	char obs = '#', src = 'o', vide = ' ', fourm = 'x';
                //System.out.print(c);
                while ((ch = (char)(fr.read())) != -1)
                {
                    if (ch == 13) 
                    {
                        ch = (char)(fr.read());
                        if (ch == 10)
                        {
                        	//Retour a la ligne CRLF
                            i++;
                            j = 0;
                        }
                    }
                    else if(ch == 10)
                    {
                    	//Retour a la ligne LF
                        i++;
                        j = 0;
                    }
                    else
                    {
                    	//Ajout du caractere
                    	if((ch == obs) || (ch == src) || (ch == vide) || (ch == fourm))
                    		tab[i][j] = ch;
                    	else
                    	{
                    		System.out.println("Caractere errone : '" + ch + "' (ASCII : " + ((int)ch) + ") ligne " + i + " colonne " + j + " considere comme un obstacle.");
                    		tab[i][j] = obs;
                    	}
                        j++;
                    }
                }
                fr.close();
                return tab;
	        }
	        catch (IOException exception)
            {
	        	System.out.println("Erreur lecture caractere");
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Impossible de trouver le fichier");
        }
        return new char[0][0];
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

    /* Initialise le plateau à partir d'un fichier txt
	 * parametres :
	 *  > String : nom du fichier plateau
	 */
    public void Initialisation(String map) {

        int i =0, j=0;
        //création d'un tableau 2D de caratères de la taille du tableau de case du plateau
        char[][] charTab = new char[m_tabCase.length][m_tabCase[0].length];
        try {
            //ouverture du fichier
            File f = new File("./data/" + map);
            FileReader fr = new FileReader(f);
            try {
                //lecture du premier cractère
                int c = fr.read();

                //tant que l'on est pas à la fin du fichier
                while ((c != -1) && (i < m_tabCase.length))
                {
                    // si on ne lit pas le caractère retour charriot (13 et 10)
                    if ((c != 13 )&& (c!= 10) )
                    {
                        charTab[i][j] = (char) c;
                        //on incrémente les colonnes
                        j++;
                        //si on est à la fin de la ligne
                        if (j == m_tabCase[i].length) {
                            //on incremente la ligne
                            i++;
                            //on reinitialise l'index de la colonne
                            j = 0;
                        }

                    }
                    //on lit le caractère
                    c = fr.read();
                }
                //fermeture du fichier
                fr.close();

            } catch (IOException exception) {
                System.out.println("Erreur lecture caractère");
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Le fichier n'a pas été trouvé");
        }
        //on verifie que le tableau de caractère est valide et suit toutes les conditions requises pour etre un plateau
        boolean b = Correct('S','#','F',charTab);
        //si oui alors on le rentre case par case dans le tableau 2D de cases du plateau (m_tabCase)
        if (b) {

            for (i = 0 ; i<m_tabCase.length ; i++)
            {
                for (j = 0 ;j < m_tabCase[i].length; j++)
                {
                    //on lit le caractère du tableau
                    char c2 = charTab[i][j];
                    //en fonction du caractère lu
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

            //Initialisation du tableau de case adjacentes de chaque case.

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
    }

    /* Retourne la case Fourmilière du plateau
	 * retour :
	 *  > Fourmilière
	 */
    public Fourmiliere GetFourmiliere()
    {
    	for(int i=0;i<m_tabCase.length;i++)
    	{
    		for(int j=0;j<m_tabCase[0].length;j++)
    		{
    		    //On verifie chaque case du plateau et si une case est de type fourmilière on la retourne
    			if(this.getM_tabCase(i, j) instanceof Fourmiliere)
    			{
    				return (Fourmiliere)this.getM_tabCase(i, j);
    			}
    		}
    	}
    	return new Fourmiliere();
    }

    //Supprime les sources vides et renvoie true si toutes les sources sont vides
    public boolean SuppSource()
    {
    	boolean b=true;
        for(int i=0;i<m_tabCase.length;i++)
        {
            for(int j=0;j<m_tabCase[0].length;j++)
            {
            	//Pour chaque source, b passe � faux tant qu'elle contient de la nourriture
                if(this.getM_tabCase(i, j) instanceof Source)
                {
                	b= false;
                	if(((Source)this.getM_tabCase(i,j)).getM_nourriture() == 0)
					{
						this.setM_tabCase(new CaseVide(),i,j);
						b=true;
					}                    
                }
            }
        }
        return b;
    }


}
