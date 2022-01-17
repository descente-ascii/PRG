package fr.istic.prg1.list.util;

import fr.istic.prg1.list_util.Iterator;
import fr.istic.prg1.list_util.SuperT;

public class List<T extends SuperT> {
	// liste en double chainage par references

	private class Element {
		// element de List<Item> : (Item, Element, Element)
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}

		public Element(T value, Element left, Element right) {
			this.value = value;
			this.left = left;
			this.right = right;
		}

	} // class Element

	public class ListIterator implements Iterator<T> {
		private Element current;

		private ListIterator() {
			current=flag.right;
		}

		@Override
		public void goForward() { 
			current=current.right;
		}

		@Override
		public void goBackward() {
			current=current.left;
		}

		@Override
		public void restart() {
			current=flag.right;
		}

		@Override
		public boolean isOnFlag() { return current.equals(flag); }

		@Override
		public void remove() {
			try {
				assert current != flag : "\n\n\nimpossible de retirer le drapeau\n\n\n";
				current.left.right=current.right;
				current.right.left=current.left;
				current=current.right;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override		 
		public T getValue() { return current.value; }

		@Override
		public T nextValue() { 
			current=current.right;
			return current.value;
		}

		@Override
		public void addLeft(T v) {
			Element newElement = new Element(v,current.left,current);
			current.left.right = newElement;
			current.left = newElement;
			current=newElement;
		}

		@Override
		public void addRight(T v) {
			Element newElement = new Element(v,current.left,current);
			current.right.left = newElement;
			current.right = newElement;
			current=newElement;
		}

		@Override
		public void setValue(T v) {
			current.value = v;
		}

		@Override
		public void selfDestroy() { }

		@Override
		public String toString() {
			return "parcours de liste : pas d'affichage possible \n";
		}

	} // class IterateurListe

	private Element flag;

	public List() {
		flag=new Element();
		flag.right=flag;
		flag.left=flag;
	}

	public ListIterator iterator() { return new ListIterator(); }

	public boolean isEmpty() { return flag.right.equals(flag); }

	public void clear() {
		ListIterator it = this.iterator();
		it.restart();
		while(!it.isOnFlag()) {
			it.remove();
		}
	}

	public void setFlag(T v) { flag.value=v;}

	public void addHead(T v) {
		ListIterator it = this.iterator();
		it.addLeft(v);
	}

	public void addTail(T v) { 
		ListIterator it = this.iterator();
		it.goBackward();
		it.addLeft(v);
	}

	@SuppressWarnings("unchecked")
	public List<T> clone() {
		List<T> nouvListe = new List<T>();
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			nouvListe.addTail((T) p.getValue().clone());
			// UNE COPIE EST NECESSAIRE !!!
			p.goForward();
		}
		return nouvListe;
	}

	@Override
	public String toString() {
		String s = "contenu de la liste : \n";
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}
}