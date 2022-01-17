package fr.istic.prg1.tree;

import java.util.Scanner;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		Iterator<Node> it = this.iterator();
		return (isPixelOnAux(x,y,it,255/2,255/2,255,255));
	}

	private boolean isPixelOnAux(int x, int y, Iterator<Node> it, int noeudx, int noeudy,int oldNoeudx, int oldNoeudy) {
		if(it.getValue().state==2) {		//on verifie ne pas etre sur une feuille
			if(y<=noeudy) {					//on regarde si la feuille cherchée se trouve dans le sous arb droit ou gauche
				it.goLeft();
				oldNoeudy=noeudy;
				noeudy=noeudy+(noeudy - oldNoeudy)/2;				//on donne sa nouvelle valeure au noeud en fonction du parcour
			}
			else {
				it.goRight();
				oldNoeudy=noeudy;
				noeudy=noeudy+(oldNoeudy - noeudy)/2 + 1;
			}
		}

		if(it.getValue().state!=2)			//comme on a avancé dans l'arbre, on revérifie qu'on soit pas sur une feuille
			return(it.getValue().state==1);

		if(it.getValue().state==2) {		//sinon on re éfectue le meme traitement (cette foi ci pour les x)
			if(x<=noeudx) {
				it.goLeft();
				oldNoeudx=noeudx;
				noeudx=noeudx+(noeudx - oldNoeudx)/2;
			}
			else {
				it.goRight();
				oldNoeudx=noeudx;
				noeudx=noeudx+(oldNoeudx - noeudx)/2 + 1;
			}
		}

		if(it.getValue().state!=2)
			return(it.getValue().state==1);

		return isPixelOnAux(x,y,it,noeudx,noeudy,oldNoeudx,oldNoeudy);	//récurssive

	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2
	 *            image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node>it = this.iterator();
		Iterator<Node>it2 = image2.iterator();
		it.clear();
		affectRecu(it,it2);
	}


	public void affectRecu(Iterator<Node> it1, Iterator<Node> it2) {
		if(!it2.isEmpty()) {
			it1.addValue(it2.getValue());
			it1.goRight();it2.goRight();
			affectRecu(it1,it2);
			it1.goUp();it2.goUp();
			it1.goLeft();it2.goLeft();
			affectRecu(it1,it2);
			it1.goUp();it2.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		rotate180Aux(it,it2);
	}

	private void rotate180Aux(Iterator<Node> it , Iterator<Node> it2) {

		if(it2.isEmpty()) return;
		it.addValue(it2.getValue());
		it.goLeft();
		it2.goRight();
		rotate180Aux(it,it2);
		it.goUp();
		it2.goUp();
		it.goRight();
		it2.goLeft();
		rotate180Aux(it,it2);
		it.goUp();
		it2.goUp();

	}

	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction non demeand�e");
		System.out.println("-------------------------------------------------");
		System.out.println();	    
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		Iterator<Node> it = this.iterator();
		videoInverseAux(it);
	}

	private void videoInverseAux(Iterator<Node> it) {
		if(it.isEmpty()) return;
		if(it.getValue().state==1)
			it.setValue(Node.valueOf(0));
		else if(it.getValue().state==0)
			it.setValue(Node.valueOf(1));
		it.goLeft();
		videoInverseAux(it);
		it.goUp();
		it.goRight();
		videoInverseAux(it);
		it.goUp();
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		it.addValue(it2.getValue());
		it.goLeft();
		it2.goLeft();
		rotate180Aux(it,it2);
		it.goUp();
		it2.goUp();
		it.goRight();
		it2.goRight();
		rotate180Aux(it,it2);
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		it.addValue(it2.getValue());
		it.goLeft();
		it2.goRight();
		affectRecu(it,it2);
		it.goUp();
		it2.goUp();
		it.goRight();
		it2.goLeft();
		affectRecu(it,it2);
	}

	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		if(it2.getValue().state==1) {
			it.addValue(it2.getValue());
			return;
		}
		it2.goLeft();
		if(it2.getValue().state==1) {
			it.addValue(it2.getValue());
			return;
		}
		else if(it2.getValue().state==0) {
			return;
		}
		it2.goLeft();
		zoomInAux(it,it2);
	}
	private void zoomInAux(Iterator<Node> it, Iterator<Node> it2) {
		if(it2.isEmpty()) return;
		it.addValue(it2.getValue());
		it.goLeft();
		it2.goLeft();
		zoomInAux(it,it2);
		it.goUp();
		it2.goUp();
		it.goRight();
		it2.goRight();
		zoomInAux(it,it2);
		it.goUp();
		it2.goUp();
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		int it2height = image2.height();
		int finalHeight = 0;
		it.clear();
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0));
		it.goUp();
		it.goLeft();
		it.addValue(Node.valueOf(2));
		it.goRight();
		it.addValue(Node.valueOf(0));
		it.goUp();
		it.goLeft();
		zoomOutAux(it,it2,it2height,finalHeight);
	}
	private void zoomOutAux(Iterator<Node> it, Iterator<Node> it2, int height, int finalHeight) {
		if(it2.isEmpty()) return;
		it.addValue(it2.getValue());
		it.goLeft();it2.goLeft();
		zoomOutAux(it,it2,height,finalHeight++);
		it.goUp();it2.goUp();
		it.goRight(); it2.goRight();
		zoomOutAux(it,it2,height,finalHeight++);
		it.goUp();it2.goUp();

		//supprimer les étages qui dépassent de la hauteur d'image2
		/*if(it.nodeType()==NodeType.LEAF && finalHeight>height) {
			int leftValue=-1;
			int rightValue=-1;
			while(finalHeight>height) {
				it.goUp();
				it.goLeft();
				if(!it.isEmpty()) {
					leftValue=it.getValue().state;
					it.remove();
				}
				it.goUp();
				it.goRight();
				if(!it.isEmpty()) {
					rightValue = it.getValue().state;
					it.remove();
				}
				it.goUp();
				if(leftValue==1 || rightValue==1) {
					it.setValue(Node.valueOf(1));
				}
				leftValue=-1;
				rightValue=-1;
				finalHeight--;
			}
		}*/
	}


	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		Iterator<Node>it = this.iterator();
		Iterator<Node>it2 = image1.iterator();
		Iterator<Node>it3 = image2.iterator();
		it.clear();
		intersectionAux(it,it2,it3);
	}

	private void intersectionAux(Iterator<Node> it, Iterator<Node> it2,Iterator<Node> it3) {
		//cas d'arret
		if(it2.isEmpty() && it3.isEmpty()) return;
		//ajout dans it selon les differentes combinaisons de it2 et it3
		if(it2.getValue().state==2 && it3.getValue().state==2)
			it.addValue(it2.getValue());
		else if(it2.getValue().state==0) {
			it.addValue(it2.getValue());
			return;
		}
		else if(it3.getValue().state==0) {
			it.addValue(it3.getValue());
			return;
		}
		else if(it2.getValue().state==1 && it3.getValue().state==1) {
			it.addValue(it2.getValue());
			return;
		}
		else if(it2.getValue().state==1 && it3.getValue().state==2) {
			it.addValue(it3.getValue());
			it.goLeft();it3.goLeft();
			intersectionAux(it,it2,it3);
			it.goUp();it3.goUp();
			it.goRight();it3.goRight();
			intersectionAux(it,it2,it3);
			it.goUp();it3.goUp();
			return;
		}
		else if(it2.getValue().state==2 && it3.getValue().state==1) {
			it.addValue(it2.getValue());
			it.goLeft();it2.goLeft();
			intersectionAux(it,it2,it3);
			it.goUp();it2.goUp();
			it.goRight();it2.goRight();
			intersectionAux(it,it2,it3);
			it.goUp();it2.goUp();
			return;
		}


		//parcours de l'arbre
		it.goLeft();it2.goLeft();it3.goLeft();
		intersectionAux(it,it2,it3);
		it.goUp();it2.goUp();it3.goUp();
		it.goRight();it2.goRight();it3.goRight();
		intersectionAux(it,it2,it3);
		it.goUp();it2.goUp();it3.goUp();
		
		//vider l'arbre qui represente image vide
	}


	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		Iterator<Node>it = this.iterator();
		Iterator<Node>it2 = image1.iterator();
		Iterator<Node>it3 = image2.iterator();
		it.clear();
		unionAux(it,it2,it3);
	}

	private void unionAux(Iterator<Node> it, Iterator<Node> it2,Iterator<Node> it3) {
		//cas d'arret
		if(it2.isEmpty() && it3.isEmpty())
			return;
		//seule la branche d'it2 est vide, continue de donner a it les valeurs d'it3
		if(it2.isEmpty()) {
			it.addValue(it3.getValue());
			it.goLeft(); it3.goLeft();
			unionAux(it,it2,it3);
			it.goUp(); it3.goUp();
			it.goRight(); it3.goRight();
			unionAux(it,it2,it3);
			it.goUp(); it3.goUp();
			return;

		}
		//seule la branche d'it3 est vide, continue de donner a it les valeurs d'it2
		if(it3.isEmpty()) {
			it.addValue(it2.getValue());
			it.goLeft(); it2.goLeft();
			unionAux(it,it2,it3);
			it.goUp(); it2.goUp();
			it.goRight(); it2.goRight();
			unionAux(it,it2,it3);
			it.goUp(); it2.goUp();
			return;
		}
		//ajout dans it selon les differentes combinaisons de it2 et it3
		if(it2.getValue().state == 1) {
			it.addValue(it2.getValue());
			return;
		}
		else if(it3.getValue().state == 1) {
			it.addValue(it3.getValue());
			return;
		}
		else if(it2.getValue().state == 2 && it3.getValue().state == 2)
			it.addValue(it2.getValue());
		else if(it2.getValue().state == 2 && it3.getValue().state == 0)
			it.addValue(it2.getValue());
		else if(it2.getValue().state == 0 && it3.getValue().state == 2)
			it.addValue(it3.getValue());
		else if(it2.getValue().state == 0 && it3.getValue().state == 0)
			it.addValue(it2.getValue());

		//parcours de l'arbre
		it.goLeft(); it2.goLeft(); it3.goLeft();
		unionAux(it,it2,it3);
		it.goUp(); it2.goUp(); it3.goUp();
		it.goRight(); it2.goRight(); it3.goRight();
		unionAux(it,it2,it3);
		it.goUp(); it2.goUp(); it3.goUp();

		//gestion du cas ou noeud pere=2 et contient deux fils=1 ou deux fils=0
		int leftValue=-1;
		int rightValue=-1;
		if(it.nodeType()==NodeType.DOUBLE) {
			it.goLeft();
			if(it.getValue().state==1) leftValue=1;
			if(it.getValue().state==0) leftValue=0;
			it.goUp();
			it.goRight();
			if(it.getValue().state==1) rightValue=1;
			if(it.getValue().state==0) rightValue=0;
			it.goUp();
			if(leftValue==1 && rightValue==1 || leftValue==0 && rightValue==0) {
				it.goRight();
				it.remove();
				it.goUp();
				it.remove();
			}
		}
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
	}

}
