package fr.istic.prg1.tp4;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 4.0
 * @since 2015-06-15
 */

public class SmallSet {

	/**
	 * taille de l'ensemble
	 */
	private static final int setSize = 256;

	/**
	 * Ensemble est représenté comme un tableau de booléens.
	 */
	private boolean[] tab = new boolean[setSize];

	public SmallSet() {
		for (int i = 0; i < setSize; ++i) {
			tab[i] = false;
		}
	}

	public SmallSet(boolean[] t) {
		for (int i = 0; i < setSize; ++i) {
			tab[i] = t[i];
		}
	}

	/**
	 * @return nombre de valeurs appartenant à l’ensemble
	 */
	public int size() {
		int compteur=0;
		for (int i = 0; i < tab.length; i++) {
			if(tab[i]==true) compteur++;
		}
		return compteur;
	}

	/**
	 * @param x
	 *            valeur à tester
	 * @return true, si l’entier x appartient à l’ensemble, false sinon
	 */
	public boolean contains(int x) {
		return tab[x];
	}

	/**
	 * @return true, si l’ensemble est vide, false sinon
	 */
	public boolean isEmpty() {
		for (int i = 0; i < tab.length; i++) {
			if(tab[i]) return false;
		}
		return true;
	}

	/**
	 * Ajoute x à l’ensemble (sans effet si x déjà présent)
	 *
	 * @param x
	 *            valeur à ajouter
	 * @pre 0 <= x <= 255
	 */
	public void add(int x) {
		tab[x]=true;
	}

	/**
	 * Retire x de l’ensemble (sans effet si x n’est pas présent)
	 *
	 * @param x
	 *            valeur à supprimer
	 * @pre 0 <= x <= 255
	 */
	public void remove(int x) {
		tab[x]=false;
	}

	/**
	 * Ajoute à l’ensemble les valeurs deb, deb+1, deb+2, ..., fin.
	 *
	 * @param begin
	 *            début de l’intervalle
	 * @param end
	 *            fin de l’intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void addInterval(int deb, int fin) {
		for (int i = deb; i <= fin; i++) {
			tab[i]=true;
		}
	}

	/**
	 * Retire de l’ensemble les valeurs deb, deb+1, deb+2, ..., fin.
	 *
	 * @param begin
	 *            début de l’intervalle
	 * @param end
	 *            fin de l’intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void removeInterval(int deb, int fin) {
		for (int i = deb; i <= fin; i++) {
			tab[i]=false;
		}
	}

	/**
	 * this devient l'union de this et set2
	 * 
	 * @param set2
	 *            deuxième ensemble
	 */
	public void union(SmallSet set2) {
		for (int i = 0; i < tab.length; i++) {
			if(set2.tab[i]==true)
				tab[i]=true;
		}	
	}

	/**
	 * this devient l'intersection de this et set2
	 * 
	 * @param set2
	 *            deuxième ensemble
	 */
	public void intersection(SmallSet set2) {
		for (int i = 0; i < tab.length; i++) {
			if(tab[i]!=set2.tab[i])
				tab[i]=false;
		}
	}

	/**
	 * this devient la différence de this et set2
	 * 
	 * @param set2
	 *            deuxième ensemble
	 */
	public void difference(SmallSet set2) {
		for (int i = 0; i < tab.length; i++) {
			if(tab[i]==true && set2.tab[i]==true)
				tab[i]=false;
		}
	}

	/**
	 * this devient la différence symétrique de this et set2
	 * 
	 * @param set2
	 *            deuxième ensemble
	 */
	public void symmetricDifference(SmallSet set2) {
		for (int i = 0; i < tab.length; i++) {
			if(tab[i]==set2.tab[i])
				tab[i]=false;
			if(tab[i]==false && set2.tab[i]==true)
				tab[i]=true;
		}
	}

	/**
	 * this devient le complément de this.
	 */
	public void complement() {
		for (int i = 0; i < tab.length; i++) {
			if(tab[i]==true)
				tab[i]=false;
			else
				tab[i]=true;
		}
	}

	/**
	 * this devient l'ensemble vide.
	 */
	public void clear() {
		tab = null;
	}

	/**
	 * @param set2
	 *            second ensemble
	 * @return true, si this est inclus dans set2, false sinon
	 */
	public boolean isIncludedIn(SmallSet set2) {
		if(this.equals(set2))
			return true;
		else {
			for (int i = 0; i < tab.length; i++) {
				if(tab[i]==true && set2.tab[i]==false) return false;
			}
			return true;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SmallSet)) {
			return false;
		}
		// il reste le cas quand obj est un SmallSet
		if(obj instanceof SmallSet) {
			return((SmallSet)obj).tab == tab;
		}
		return false;
	}

	/**
	 * @return copie de this
	 */
	public SmallSet clone() {
		return new SmallSet(tab);
	}

	@Override
	public String toString() {
		String s;
		s = "elements presents : ";
		for (int i = 0; i < setSize; ++i) {
			if (tab[i]) {
				s = s + i + " ";
			}
		}
		return s;
	}
}
