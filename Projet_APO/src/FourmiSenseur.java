/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class FourmiSenseur extends Fourmi {

    private int m_portee;

    public FourmiSenseur(int[] m_chemin, double m_nourr_transp, int m_portee) {
        super(m_chemin, m_nourr_transp);
        this.m_portee = m_portee;
    }

    public int getM_portee() {
        return m_portee;
    }

    public void setM_portee(int m_portee) {
        this.m_portee = m_portee;
    }


}
