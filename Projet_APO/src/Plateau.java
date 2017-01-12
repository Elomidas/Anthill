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
        String[] plat = new String[6];
        int i =0;
        try {
            File f = new File("../data/map.txt");
            Scanner scanner = new Scanner(f);

            String ligne;
            while(true)
            {

                try {
                    ligne = scanner.next();
                    plat[i] = ligne;
                    i++;
                }
                catch(NoSuchElementException exception)
                {
                    break;
                }
            }
            scanner.close();
        }catch (FileNotFoundException exception)
        {
            System.out.println("Le fichier n'a pas été trouvé");
        }

        for(i = 0; i<6 ; i++)
        {
            System.out.println(plat[i]);
        }
    }


}
