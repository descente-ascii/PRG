package fr.istic.prg1.tp3;
import java.util.Scanner;

public class InsertionInteger {

	private static final int SIZE_MAX = 10;
	private int size;
	private int[] array = new int [SIZE_MAX];

	public InsertionInteger() {

	}

	/**
	 * @return copie de la partie remplie du tableau
	 */
	public int[] toArray() {
		int[] res = new int[size];
		int compteur=0;
		for (int j = 0; j < SIZE_MAX; j++) {
			if(this.array[j]!=0) {
				res[compteur]=this.array[j];
				compteur++;
			}
		}
		return res;
	}

	public static void tri_selection(int[] tab){
		for (int i = 0; i < tab.length - 1; i++){
			int index = i;
			for (int j = i + 1; j < tab.length; j++){
				if (tab[j] < tab[index]){ 
					index = j;
				}
			}
			int min = tab[index];
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
	public boolean insert(int value) {
		if(size < SIZE_MAX) {
			tri_selection(array);        //premier tri pour mettre les 0 au db
			for(int i=0 ; i<SIZE_MAX ; i++) {        //boucle qui va faire office de filtre
				if(array[i] == value)        //conditionnelle de tri
					return false;
			}
			array[0]=value;        //place la value au db de array ( normalement a la place d'un 0 )
			size++;
			tri_selection(array);    //tri le tableau
			return true;
		}
		return false;
	}

	/**
	 * array est rempli, par ordre croissant, en utilisant la fonction insert avec les valeurs lues par scanner.
	 * @param scanner
	 */
	public void createArray(Scanner scanner) {

		int input =0;
		while(input!=-1 && size<SIZE_MAX) {
			input=scanner.nextInt();
			if(input==-1) break;
			if(insert(input)) {}
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