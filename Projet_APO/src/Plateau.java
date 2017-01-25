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
                System.out.println("Erreur lecture caractère");
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Le fichier n'a pas été trouvé");
        }

        //Pour le coin en haut à gauche, on lui attribue des cases adjacentes à droite, en bas, et en bas à droite:
        m_tabCase[0][0].setM_case_adj(getM_tabCase(0,0),1);

        for (i=0 ; i<m_tabCase.length ; i++)
        {




            for (j=0; j < m_tabCase[i].length; j++)
            {

                /*
                if( j==m_tabCase[i].length - 1 )
                {

                    if (i==m_tabCase.length - 1 )
                    {
                        m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j),1);
                        m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j-1),0);
                        m_tabCase[i][j].setM_case_adj(getM_tabCase(i,j-1),3);
                    }
                    else
                    {
                        if ( i == 0)
                        {
                            m_tabCase[i][j].setM_case_adj(getM_tabCase(i,j-1),3);
                            m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j-1),5);
                            m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j),6);
                        }
                        else
                        {


                        }
                    }
                }
                else
                {
                   if (i == m_tabCase.length - 1 )
                   {
                       if (j == 0)
                       {
                           m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j),1);
                           m_tabCase[i][j].setM_case_adj(getM_tabCase(i-1,j+1),2);
                           m_tabCase[i][j].setM_case_adj(getM_tabCase(i,j+1),4);
                       }
                       else
                       {

                       }
                   }
                   else
                   {
                       if (i == 0)
                       {
                           if (j ==0)
                           {
                               m_tabCase[i][j].setM_case_adj(getM_tabCase(i,j+1),4);
                               m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j),6);
                               m_tabCase[i][j].setM_case_adj(getM_tabCase(i+1,j+1),7);
                           }
                           else
                           {

                           }
                       }
                       else
                       {

                       }
                   }
                    */
                }

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
