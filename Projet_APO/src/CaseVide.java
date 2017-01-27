/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class CaseVide extends Case {
    //Constructeur
    public CaseVide() {
        super();
    }

    /* Constructeur surcharge
     * parametres :
     *  > int : coordonnée des abcisses de la case vide
     *  > int : coordonnée des ordonnées de la case vide
     */
    public CaseVide(int m_abcisse, int m_ordonnee) {
        super(m_abcisse, m_ordonnee);
    }

    // par défaut la case vide est pénétrable
    public boolean Penetrable() {
        return true;
    }

    //On affiche le nombre de phéromones da la case vide.
    @Override
    public void Afficher() {
        System.out.print((int)m_pheromone);
    }
}
