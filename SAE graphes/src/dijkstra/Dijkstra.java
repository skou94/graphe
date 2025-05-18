package dijkstra.dijkstra;

import dijkstra.graph.Graph;
import dijkstra.graph.ShortestPath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra<T> implements ShortestPath<T> {

    @Override
    public ShortestPath.Distances<T> compute(Graph<T> g, T src, ShortestPath.Animator<T> animator) throws IllegalArgumentException {
        // Initialisation des structures de données
        Map<T, Integer> dist = new HashMap<>();
        Map<T, T> pred = new HashMap<>();
        Set<T> visited = new HashSet<>();

        // File de priorité pour stocker les sommets à explorer
        // La priorité est basée sur la distance depuis la source
        PriorityQueue<VertexWithDistance> pq = new PriorityQueue<>((v1, v2) ->
                Integer.compare(v1.distance, v2.distance));

        // Initialisation du sommet source
        dist.put(src, 0);
        pred.put(src, null);
        pq.add(new VertexWithDistance(src, 0));

        // Tant qu'il reste des sommets à explorer
        while (!pq.isEmpty()) {
            // Extraire le sommet avec la plus petite distance
            VertexWithDistance current = pq.poll();
            T currentVertex = current.vertex;

            // Si le sommet a déjà été visité, ignorer
            if (visited.contains(currentVertex)) {
                continue;
            }

            // Marquer le sommet comme visité
            visited.add(currentVertex);

            // Notifier l'animateur
            animator.accept(currentVertex, dist.get(currentVertex));

            // Explorer les voisins du sommet courant
            for (Graph.Arc<T> arc : g.getSucc(currentVertex)) {
                T neighbor = arc.dst();
                int weight = arc.val();

                // Vérifier que les valuations sont positives
                if (weight < 0) {
                    throw new IllegalArgumentException("L'algorithme de Dijkstra ne fonctionne pas avec des valuations négatives");
                }

                // Calculer la nouvelle distance
                int newDist = dist.get(currentVertex) + weight;

                // Si le voisin n'a pas encore de distance attribuée ou si la nouvelle distance est plus courte
                if (!dist.containsKey(neighbor) || newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    pred.put(neighbor, currentVertex);
                    pq.add(new VertexWithDistance(neighbor, newDist));
                }
            }
        }

        return new ShortestPath.Distances<>(dist, pred);
    }

    // Classe interne pour stocker un sommet avec sa distance depuis la source
    private class VertexWithDistance {
        T vertex;
        int distance;

        public VertexWithDistance(T vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}
