/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class CaseVide extends Case {
    public CaseVide() {
        super();
    }

    public CaseVide(int m_abcisse, int m_ordonnee) {
        super(m_abcisse, m_ordonnee);
    }

    public boolean Penetrable() {
        return true;
    }

    @Override
    public void Afficher() {
        System.out.print((int)m_pheromone);
    }
}
