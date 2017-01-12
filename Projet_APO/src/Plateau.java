import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Plateau {

    private Case[][] m_tabCase;

    public Plateau(int h, int l)
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
            for (int j = 0; j < tabCase.length ; j++)
            {
                getM_tabCase(i,j).Afficher();
            }
        }
    }

    public void Initialisation() {
        char[][] plat = new char[6][10];
        int i =0, j=0;

        try {
            File f = new File("./data/map.txt");
            FileReader fr = new FileReader(f);
            try {
                int c = fr.read();
                while (c != -1)
                {
                    plat[i][j] = (char)c;
                    j++;
                    if (j-1 == 9)
                    {
                        i++;
                        j=0;
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

        for(i = 0; i<10 ; i++)
        {
            for( j = 0; j < 6; j++)
            {
                System.out.println(plat[i][j]);
            }

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
