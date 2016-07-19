import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

public class Score extends ÉlémentJeu {
	private PFont police150;
	private Color couleur;
	
	private int valeur;
	private boolean gagnant;
	
	public Score(PApplet p, int posX, int posY, PFont police, Color couleur) {
		super(p, posX, posY);
		
		this.police150 = police;
		this.couleur = couleur;
		
		this.valeur = 0;
		this.gagnant = false;
	}

	public void afficher() {
		if(this.gagnant) {
			this.p.fill(this.couleur.getRGB());
			this.p.textFont(this.police150, 180);
		} else {
			this.p.fill(255);
			this.p.textFont(this.police150, 150);
		}
		this.p.textAlign(PApplet.CENTER, PApplet.TOP);
		this.p.text(this.valeur, this.posX, this.posY);
	}
	
	public void réinitialiser() {
		this.valeur = 0;
		this.gagnant = false;
	}
	
	public void incrémenter() {
		this.valeur++;
	}
	
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public boolean isGagnant() {
		return gagnant;
	}
	public void setGagnant(boolean gagnant) {
		this.gagnant = gagnant;
	}
}
