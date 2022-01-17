package fr.istic.prg1.tp3;

/**
 * 
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2015-11-10
 * 
 *        Classe représentant des doublets non modifiables
 */

public class Pair implements Comparable<Pair> {
	
	int a, b;
	
	public Pair() {
		this.a=0;
		this.b=0;
	}
	
	public Pair(int a) {
		this.a=a;
		this.b=0;
	}
	
	public Pair(int a, int b) {
		this.a=a;
		this.b=b;
	}

	@Override
	public int compareTo(Pair d) {
		if(this.a<d.a || (this.a == d.a && this.b<d.b))
			return -1;
		if(this.a==d.a && this.b==d.b)
			return 0;
		else
			return 1;
	}

	@Override
	public Pair clone() {
	    return new Pair(this.a,this.b);
	}

	@Override
	public String toString() {
	    return "("+this.a+","+this.b+")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Pair)) {
			return false;
		}
		if (obj instanceof Pair) {
			if(((Pair)obj).a == this.a && ((Pair)obj).b == this.b )
				return true;
		}
		return false;
	}
}