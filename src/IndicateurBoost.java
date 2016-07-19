import java.awt.Color;

import processing.core.PApplet;

public class IndicateurBoost extends ÉlémentJeu {
	private int orientation;
	private Color couleur;
	
	private int variateurLongeur;
	
	private int marge;
	
	public IndicateurBoost(PApplet p, int sens, Color couleur, int marge) {
		super(p, p.width/2, p.height/2);

		this.orientation = sens;
		this.couleur = couleur;
		
		this.variateurLongeur = 0;
		
		this.marge = marge;
	}
	
	public void réinitialiser() {
		this.variateurLongeur = 0;
	}
	
	public void afficher() {
		this.p.pushMatrix();
			this.p.translate(this.posX, this.posY);
			this.p.rotate(PApplet.radians(this.orientation));
			
			if(this.boostPret())
				this.tracerHalos();

			if(!this.boostPret())
				this.p.fill(this.p.hue(this.couleur.getRGB()), 160, 255);
			else
				this.p.fill(this.p.hue(this.couleur.getRGB()), 64, 255);
			
			this.p.noStroke();
			this.p.rect(-this.p.width/2, -this.p.height/2, this.marge, this.p.height);
			this.p.rect(-this.p.width/2 - this.marge/2, this.p.height/2 - this.marge, this.variateurLongeur, this.marge);
			this.p.rect(-this.p.width/2 - this.marge/2, -this.p.height/2, this.variateurLongeur, this.marge);
			
		this.p.popMatrix();
	}
	private void tracerHalos() {
		int tailleHalos = 5;
		for (int i = 0; i < tailleHalos; i++) {
			this.p.stroke(this.couleur.getRGB(), 200 - i/10F * 200);
			this.p.line(-this.p.width/2 + this.marge/2 + i, -this.p.height/2, -this.p.width/2 + this.marge/2 + i, this.p.height/2);
			this.p.line(-this.p.width/2 + this.marge/2, this.p.height/2 - this.marge/2 - i, -this.p.width/2 + this.variateurLongeur - this.marge, this.p.height/2 - this.marge/2 - i);
			this.p.line(-this.p.width/2 + this.marge/2, -this.p.height/2 + this.marge/2 + i, -this.p.width/2 + this.variateurLongeur - this.marge, -this.p.height/2 + this.marge/2 + i);
		}
	}
	
	public void actualiserVariateur() {
		if(this.variateurLongeur < this.p.width/2 + this.marge/2)
			this.variateurLongeur += 2;
	}
	
	public boolean boostPret() {
		return this.variateurLongeur > this.p.width/2 + this.marge/2 - 3;
	}
}
