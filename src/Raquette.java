import java.awt.Color;

import processing.core.PApplet;

public class Raquette extends ÉlémentJeu {
	public final static int LIMITE_SORTIE = 10;

	private Color couleur;
	
	private int pPosY;
	private int posXInit;
	private int milieu;
	
	private int dimX;
	private int dimY;
	
	private int vitesseDéplacement;
	
	public enum ÉtatsAnimation {REPOS, EN_SORTIE, EN_RETOUR};
	private ÉtatsAnimation étatAnim;
	private int variateurDécalage;
	private int sensBoost;
	
	public Raquette(PApplet p, int posX, int posY, Color couleur) {
		super(p, posX, posY);
		
		this.couleur = couleur;
		
		this.pPosY = this.posY;
		this.posXInit = this.posX;
		this.milieu = this.posY;
		
		this.dimX = 25;
		this.dimY = 120;
		
		this.vitesseDéplacement = 8;
		
		this.variateurDécalage = 0;
		this.étatAnim = ÉtatsAnimation.REPOS;
	}

	public void réinitialiser() {
		this.posY = this.milieu;
	}
	
	public void animer() {
		switch (this.étatAnim) {
		case EN_SORTIE:
			if(this.variateurDécalage < Raquette.LIMITE_SORTIE - 1) {
				this.variateurDécalage += Math.max(1, (Raquette.LIMITE_SORTIE - this.variateurDécalage)/2F);
				this.posX += this.variateurDécalage * this.sensBoost;
			} else {
				this.étatAnim = ÉtatsAnimation.EN_RETOUR;
			}
			break;
		case EN_RETOUR:
			if((this.sensBoost == 1 && this.posX > this.posXInit) || (this.sensBoost == -1 && this.posX < this.posXInit)) {
				this.posX -= this.sensBoost;
				this.variateurDécalage--;
			} else {
				this.étatAnim = ÉtatsAnimation.REPOS;
				this.variateurDécalage = 0;
			}
			break;
		default:
			break;
		}
	}
	
	public void afficher() {
		int tailleHalo = 10;
		int tailleArrondi = 3;
		float variationAnim = 80 * (this.variateurDécalage/(Raquette.LIMITE_SORTIE * 1F));
		this.p.fill(this.p.hue(this.couleur.getRGB()), this.étatAnim == ÉtatsAnimation.REPOS ? 100 : 100 - variationAnim, 255);
		this.p.rect(this.posX + tailleHalo, this.posY + tailleHalo, this.dimX - tailleHalo*2, this.dimY - tailleHalo*2, tailleArrondi);
		
		this.p.noFill();
		for (int i = 0; i < tailleHalo; i++) {
			this.p.stroke(this.p.hue(this.couleur.getRGB()), this.étatAnim == ÉtatsAnimation.REPOS ? 100 : 100 - variationAnim, 255, 255 - i/(tailleHalo * 1F) * 255);
			this.p.rect(this.posX + tailleHalo - i, this.posY + tailleHalo - i, this.dimX - tailleHalo*2 + i*2, this.dimY - tailleHalo*2 + i*2, tailleArrondi);
		}
	}
	
	public void déplacerHaut(int marge) {
		if(this.posY > marge) {
			this.posY = this.posY - this.vitesseDéplacement;
		} else if(this.posY <= marge){
			this.posY = marge;
		}
	}
	public void déplacerBas(int marge) {
		if(this.posY + this.dimY < this.p.height - marge) {
			this.posY = this.posY + this.vitesseDéplacement;
		} else { 
			this.posY = this.p.height - marge - dimY;
		}
	}

	public void booster(int sens) {
		if(this.étatAnim == ÉtatsAnimation.REPOS) {
			this.étatAnim = ÉtatsAnimation.EN_SORTIE;
			this.sensBoost = sens;
			this.posXInit = this.posX;
		}
	}
	
	public void actualiserPosYPrecédente() {
		this.pPosY = this.posY;
	}
	
	public int sens() {
		if(this.pPosY == this.posY)
			return 0;
		else
			return (int) Math.signum(this.pPosY - this.posY);
	}
	
	public Color getCouleur() {
		return couleur;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	public int getDimX() {
		return dimX;
	}
	public void setDimX(int dimX) {
		this.dimX = dimX;
	}

	public int getDimY() {
		return dimY;
	}
	public void setDimY(int dimY) {
		this.dimY = dimY;
	}

	public ÉtatsAnimation getÉtatAnim() {
		return étatAnim;
	}
	public void setÉtatAnim(ÉtatsAnimation étatAnim) {
		this.étatAnim = étatAnim;
	}
}
