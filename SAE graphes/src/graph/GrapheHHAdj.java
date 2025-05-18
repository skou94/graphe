package dijkstra.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation d'un graphe orienté valué utilisant des tables de hachage.
 * HH = HashMap-HashMap
 * Adj = structure d'adjacence
 */
public class GrapheHHAdj implements VarGraph {

	// Stockage des listes d'adjacence
	private Map<String, Map<String, Integer>> adjacence;

	/**
	 * Constructeur du graphe vide.
	 */
	public GrapheHHAdj() {
		adjacence = new HashMap<>();
	}

	@Override
	public void ajouterSommet(String noeud) {
		// Ne rien faire si le sommet existe déjà
		if (!adjacence.containsKey(noeud)) {
			adjacence.put(noeud, new HashMap<>());
		}
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		// Ajouter les sommets s'ils n'existent pas
		ajouterSommet(source);
		ajouterSommet(destination);

		// Vérifier si l'arc existe déjà
		if (adjacence.get(source).containsKey(destination)) {
			throw new IllegalArgumentException("L'arc " + source + "-" + destination + " existe déjà");
		}

		// Ajouter l'arc
		adjacence.get(source).put(destination, valeur);
	}

	@Override
	public List<Arc<String>> getSucc(String s) {
		List<Arc<String>> successors = new ArrayList<>();

		// Vérifier si le sommet existe
		if (!adjacence.containsKey(s)) {
			return successors; // Retourne une liste vide si le sommet n'existe pas
		}

		// Construire la liste des arcs sortants
		for (Map.Entry<String, Integer> entry : adjacence.get(s).entrySet()) {
			successors.add(new Arc<>(entry.getValue(), entry.getKey()));
		}

		return successors;
	}
}