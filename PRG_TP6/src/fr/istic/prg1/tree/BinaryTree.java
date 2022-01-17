package fr.istic.prg1.tree;

import java.util.Stack;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 4.0
 * @since 2015-06-15
 * @param <T>
 *            type formel d'objet pour la classe
 *
 *            Les arbres binaires sont construits par chaînage par références
 *            pour les fils et une pile de pères.
 */
public class BinaryTree<T> {

	/**
	 * Type représentant les noeuds.
	 */
	private class Element {
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}

		public boolean isEmpty() {
			return left == null && right == null;
		}
	}

	private Element root;

	public BinaryTree() {
		root = new Element();
	}

	/**
	 * @return Un nouvel iterateur sur l'arbre this. Le noeud courant de
	 *         l’itérateur est positionné sur la racine de l’arbre.
	 */
	public TreeIterator iterator() {
	    return new TreeIterator();
	}

	/**
	 * @return true si l'arbre this est vide, false sinon
	 */
	public boolean isEmpty() {
		//si la racine n'a pas de valeur attribuée, alors l'arbre est vide. 
	    return root.value == null;
	}

	/**
	 * Classe représentant les itérateurs sur les arbres binaires.
	 */
	public class TreeIterator implements Iterator<T> {
		private Element currentNode;
		private Stack<Element> stack;

		private TreeIterator() {
			stack = new Stack<Element>();
			currentNode = root;
		}

		/**
		 * L'itérateur se positionnne sur le fils gauche du noeud courant.
		 * 
		 * @pre Le noeud courant n’est pas un butoir.
		 */
		@Override
		public void goLeft() {
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			//On insère la node actuelle dans la pile, puis on se affecte la node gauche à currentNode
			stack.push(currentNode);
			currentNode = currentNode.left;
			
		}
		
		/**
		 * L'itérateur se positionnne sur le fils droit du noeud courant.
		 * 
		 * @pre Le noeud courant n’est pas un butoir.
		 */
		@Override
		public void goRight() {
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			//On insère la node actuelle dans la pile, puis on se affecte la node droite à currentNode
			stack.push(currentNode);
			currentNode = currentNode.right;
		}

		/**
		 * L'itérateur se positionnne sur le père du noeud courant.
		 * 
		 * @pre Le noeud courant n’est pas la racine.
		 */
		@Override
		public void goUp() {
			try {
				assert !stack.empty() : " la racine n'a pas de pere";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			//On se replace sur la node parente en la récupérant au sommet de la pile.
			currentNode = stack.pop();
		}

		/**
		 * L'itérateur se positionne sur la racine de l'arbre.
		 */
		@Override
		public void goRoot() {
			//Tant que la pile n'est pas vide, on continue de se place sur l'élément en sommet de pile. Une fois que la pile est vide, alors on est sur la racine.
			while(!stack.isEmpty())
				currentNode = stack.pop();
		}

		/**
		 * @return true si l'iterateur est sur un sous-arbre vide, false sinon
		 */
		@Override
		public boolean isEmpty() {
			//si la node sur laquelle on est n'a pas de valeur attribuée, alors c'est qu'elle est vide.
		    return currentNode.value == null;
		}

		/**
		 * @return Le genre du noeud courant.
		 */
		@Override
		public NodeType nodeType() {
			//si la node actuelle a un fils gauche un fils droit, alors c'est un noeud double.
			if(currentNode.left!=null && currentNode.right!=null)
				return NodeType.DOUBLE;
			//si la node actuelle n'a pas de node à gauche et à droite, on est sur une feuille.
			else if(currentNode.isEmpty())
				return NodeType.LEAF;
			//si la node actuelle n'a pas de node à gauche mais une à droite, on est sur une branche simple droite.
			else if(currentNode.left == null && currentNode.right!=null)
			return NodeType.SIMPLE_RIGHT;
			//si la node actuelle n'a pas de node à droite mais une à gauche, on est sur une branche simple gauche.
			else if (currentNode.right == null)
				return NodeType.SIMPLE_LEFT;
			//Si ce n'est aucun des cas précédent, alors la node est de type sentinel.
			return NodeType.SENTINEL;
		}

		/**
		 * Supprimer le noeud courant de l'arbre.
		 * 
		 * @pre Le noeud courant n'est pas un noeud double.
		 */
		@Override
		public void remove() {
			try {
				assert nodeType() != NodeType.DOUBLE : "retirer : retrait d'un noeud double non permis";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			//Si la node est de type simple_left, alors on passe le fils gauche en noeud courant et on fait disparaitre l'ancien noeud courant de l'arbre.
			if(nodeType() == NodeType.SIMPLE_LEFT){
				currentNode.value = currentNode.left.value;
				currentNode.right = currentNode.left.right;
				currentNode.left = currentNode.left.left;
			}
			//Si la node est de type simple_right, alors on passe le fils droit en noeud courant et on fait disparaitre l'ancien noeud courant de l'arbre.
			else if (nodeType() == NodeType.SIMPLE_RIGHT){
				currentNode.value = currentNode.right.value;
				currentNode.left = currentNode.right.left;
				currentNode.right = currentNode.right.right;
			}
			//sinon, on passe la node en butoire
			else {
				currentNode.value = null;
				currentNode.left = null;
				currentNode.right = null;
			}
		}

		/**
		 * Vider le sous–arbre référencé par le noeud courant, qui devient
		 * butoir.
		 */
		@Override
		public void clear() {
			//on passe tous les éléments du noeud courant à null. 
			currentNode.value = null;
			currentNode.left = null;
			currentNode.right = null;
		}

		/**
		 * @return La valeur du noeud courant.
		 */
		@Override
		public T getValue() {
		    return currentNode.value;
		}

		/**
		 * Créer un nouveau noeud de valeur v à cet endroit.
		 * 
		 * @pre Le noeud courant est un butoir.
		 * 
		 * @param v
		 *            Valeur à ajouter.
		 */

		@Override
		public void addValue(T v) {
			try {
				assert isEmpty() : "Ajouter : on n'est pas sur un butoir";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			//On assigne v en valeur courante, et on crée deux éléments vides en fils gauche et droit. 
			currentNode.value = v;
			currentNode.left = new Element();
			currentNode.right = new Element();
		}

		/**
		 * Affecter la valeur v au noeud courant.
		 * 
		 * @param v
		 *            La nouvelle valeur du noeud courant.
		 */
		@Override
		public void setValue(T v) {
			//On assigne v à la valeur du noeud courant. 
			currentNode.value = v;
		}

		private void ancestor(int i, int j) {
			try {
				assert !stack.empty() : "switchValue : argument trop grand";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			Element x = stack.pop();
			if (j < i) {
				ancestor(i, j + 1);
			} else {
				T v = x.value;
				x.value = currentNode.value;
				currentNode.value = v;
			}
			stack.push(x);
		}

		/**
		 * Échanger les valeurs associées au noeud courant et à son père d’ordre
		 * i (le noeud courant reste inchangé).
		 * 
		 * @pre i>= 0 et racine est père du noeud courant d’ordre >= i.
		 * 
		 * @param i
		 *            ordre du père
		 */
		@Override
		public void switchValue(int i) {
			try {
				assert i >= 0 : "switchValue : argument negatif";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			if (i > 0) {
				ancestor(i, 1);
			}
		}
	}
}
