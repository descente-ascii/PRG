package fr.istic.prg1.tp3;

import java.util.Scanner;

public class InsertionPair {
	private static final int SIZE_MAX = 10;
	private int size;
	private  Pair[] array = new Pair[SIZE_MAX];

	public InsertionPair() {

	}

	/**
	 * @return copie de la partie remplie du tableau
	 */
	public Pair[] toArray() {
		Pair[] res = new Pair[size];
		for (int i = 0; i < size; i++) {
			res[i]=this.array[i].clone();
		}
		return res;
	}

	public static void tri_selection(Pair[] tab){

		for (int i = 0; i < tab.length - 1; i++){
			int index = i;
			for (int j = i + 1; j < tab.length; j++){
				if(tab[j]!=null) { 
					if (tab[j].compareTo(tab[index])==-1){ 
						index = j;
					}
				}
			}
			Pair min = tab[index];
			tab[index] = tab[i]; 
			tab[i] = min;
		}
	}

	/**
	 * @param value, valeur à inserer
	 * @pre les valeurs de array[0..size-1] sont triées par ordre croissant
	 * @return false si x appartient à array[0..size-1] ou si array est completement rempli;
	 *            true si x n'appartient pas à array [0..size-1]
	 */
	public boolean insert(Pair value) {
		if(size<SIZE_MAX) {
			for(int i=0 ; i<size ; i++) {
				if(array[i].equals(value))
					return false;
			}
			array[size]=value;
			size++;
			tri_selection(array);
			return true;
		}
		else
			return false;
	}

	/**
	 * array est rempli, par ordre croissant, en utilisant la fonction insert avec les valeurs lues par scanner.
	 * @param scanner
	 */
	public void createArray(Scanner scanner) {

		int inputA =0, inputB=0;
		while((inputA!=-1 || inputB!=-1) && size<SIZE_MAX) {
			inputA=scanner.nextInt();
			if(inputA==-1) break;
			inputB=scanner.nextInt();
			if(inputA==-1 || inputB==-1) break;
			if(insert(new Pair(inputA,inputB))) {}
		}
	}

	@Override 
	public String toString() {
		String res ="[";
		for (int i = 0; i < array.length-1; i++) {
			res += this.array[i]+", ";
		}
		res+=this.array[this.array.length-1]+"]";
		return res;
	}
}