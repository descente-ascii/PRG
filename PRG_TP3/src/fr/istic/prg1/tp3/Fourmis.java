package fr.istic.prg1.tp3;

public class Fourmis {

	/**
	 * @param ui, un element de la suite Fourmis
	 * @return l'element ui+1 de la suite Fourmis
	 */
	public static String next(String ui) {
		String res ="";
		int nbOccur =0;
		for (int i = 0; i < ui.length(); i++) {
			nbOccur = nbSuite(i,ui);
			res += String.valueOf(nbOccur)+String.valueOf(ui.charAt(i));
			if(i+nbOccur<=ui.length())
				i+=nbOccur-1;
			else
				return res;
		}
		return res;
	}

	/**
	 * Calcul le nombre de repetition d'un chiffre, donné par son indice,
	 * dans un terme de la suite (se compte lui-meme)
	 * @param indice
	 * @param ui, un element de la suite Fourmis
	 * @return le nombre de repetition du chiffre présent a l'indice indice
	 */
	public static int nbSuite(int indice, String ui) {
		if(indice==ui.length()-1)
			return 1;
		else {
			int res = 1;
			for (int i = indice+1; i < ui.length(); i++) {
				if(ui.charAt(i)==ui.charAt(indice)) {
					res++;
				}
				else
					return res;
			}
			return res++;
		}
	}

	/**
	 * 
	 * @param firstTerm, le premier terme de la suite Fourmis
	 * @param nbTerms, le nombre de terme que l'on veut afficher
	 * @return la suite Fourmis
	 */
	public static String suiteFourmis(String firstTerm, int nbTerms) {
		String res="";
		String next = next(firstTerm);
		if(nbTerms==0)
			return res;
		else
			res =firstTerm+ "\n" + next + "\n"+ suiteFourmis(next(next),nbTerms-2);
		return res;
	}
}
