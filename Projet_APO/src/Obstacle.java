/**
 * Created by Martial TARDY on 05/01/2017.
 */
public class Obstacle extends Case {

    public Obstacle() {
        super();
    }

    public Obstacle(int m_abcisse, int m_ordonnee) {
        super(m_abcisse, m_ordonnee);
    }

    public boolean Penetrable() {
        return false;
    }

    @Override
    public void Afficher() {
        System.out.print("#");
    }
}
