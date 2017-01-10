/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class CaseVide extends Case {
    public CaseVide() {
        super();
    }

    public CaseVide(int m_abcisse, int m_ordonnee, double m_pheromone, Case[] m_case_adj) {
        super(m_abcisse, m_ordonnee, m_pheromone, m_case_adj);
    }

    public boolean Penetrable() {
        return true;
    }

    @Override
    public void Afficher() {
        super.Afficher();
    }
}
