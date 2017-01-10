/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Plateau {

    private Case[][] m_tabCase;

    public Plateau(int h, int l)
    {
        this.m_tabCase = new Case[h][l];
    }

    public Case getM_tabCase(int h, int l) {
        return m_tabCase[h][l];
    }

    public void setM_tabCase(Case m_tabCase, int h, int l) {
        this.m_tabCase[h][l] = m_tabCase;
    }


}
