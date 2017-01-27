/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Obstacle extends Case {

    //Constructeur
    public Obstacle() {
        super();
    }

    /* Constructeur surcharge
     * parametres :
     *  > int : coordonnée des abcisses de l'obstacle
     *  > int : coordonnée des ordonnées de l'obstacle
     */
    public Obstacle(int m_abcisse, int m_ordonnee) {
        super(m_abcisse, m_ordonnee);
    }

    // par défaut la case obstacle n'est pas pénétrable
    public boolean Penetrable() {
        return false;
    }

    //Affiche le caractère # pour un obstacle
    @Override
    public void Afficher() {
        System.out.print("#");
    }
}
