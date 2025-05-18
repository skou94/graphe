package dijkstra.adaptator;

import dijkstra.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import maze.Maze;
import maze.regular.RegularMaze;
/**
 * Adaptateur transformant un labyrinthe en graphe pour l'algorithme de Dijkstra.
 */
public class GraphMaze<T> implements Graph<T> {
    private final Maze maze;
    /**
     * Constructeur de GraphMaze.
     *
     * @param maze Le labyrinthe à adapter en graphe
     */
    public GraphMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Renvoie la liste des arcs sortants d'un sommet du graphe.
     * Dans le contexte d'un labyrinthe, ces arcs correspondent aux
     * déplacements possibles vers les cellules voisines accessibles.
     *
     * @param cellId L'identifiant de la cellule (sommet) source
     * @return La liste des arcs sortants
     */
    @Override
    public List<Arc<T>> getSucc(T cellId) {
        List<Arc<T>> arcs = new ArrayList<>();
        Set<T> neighbors = maze.openedNeighbours(cellId);
        for (T neighbor : neighbors) {

            // Dans un labyrinthe, chaque déplacement a un coût de 1
            arcs.add(new Arc<>(1, neighbor));
        }
        return arcs;
    }
}
