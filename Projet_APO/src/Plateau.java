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
        int i = 0;
        int[] size = new int[2];
        try {
            File f = new File("./data/map.txt");
            FileReader fr = new FileReader(f);
            try {
                int c = fr.read();
                //System.out.print(c);
                while ((c != -1) && (i < m_tabCase.length))
                {
                    if ((c != 13 )&& (c!= 10) ) {
                        size[i] = ;

                        System.out.println(size[i]);
                        i++;
                    }
                }
                this.m_tabCase=new Case[ size[0] ][ size[1] ];


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
