import processing.core.PApplet;

public class Balle extends ÉlémentJeu {
	private int diamètre;
	private float vitesse;
	private float vitesseInit;
	private int variateurVitesse;
	
	private float compoVitesseX;
	private float compoVitesseY;
	
	private float orientation;
	private float angleEnnui;
	
	private int teinte;
	private int variateurTeinte;
	
	private int saturation;
	private int variateurSaturation;	
	
	public Balle(PApplet p, int posX, int posY) {
		super(p, posX, posY);
		
		this.diamètre = 50;
		this.vitesseInit = 0;
		this.variateurVitesse = 0;
		
		this.angleEnnui = PApplet.PI / 8F;
		
		this.teinte = (int) (Math.random() * 255);
		this.saturation = 255;
		
		this.initialiser();
	}

	public void initialiser() {
		this.posX = this.p.width/2;
		this.posY = this.p.height/2;
		this.orientation = 0;
		this.vitesse = 0;
		this.vitesseInit = 0;
	}
	
	public void lancer() {
		this.orientation = (float) (Math.random() * PApplet.TWO_PI);
		this.teinte = (int) (Math.random() * 255);
		
		this.vitesse = 12;
		this.vitesseInit = 12;
		this.variateurVitesse = 0;
		this.corrigerOrientation();
	}
	
	public void animer() {
		if(this.variateurVitesse > 1) {
			this.vitesse = vitesseInit + PApplet.sqrt(this.variateurVitesse);
			this.variateurVitesse --;
		} else if(this.variateurVitesse <= 1 && this.variateurVitesse > 0) {
			this.vitesse = 12;
			this.variateurVitesse = 0;
		}
		
		this.compoVitesseX = (float) (Math.cos(this.orientation) * this.vitesse);
		this.compoVitesseY = (float) (Math.sin(this.orientation) * this.vitesse);
		
		this.posX += this.compoVitesseX;
		this.posY += this.compoVitesseY;
	}
	
	public void afficher() {
		this.p.fill(this.teinte, 64 - (this.variateurVitesse/200F * 32), 255);
		this.p.strokeWeight(3);
		this.p.ellipseMode(PApplet.CENTER);
		this.p.ellipse(this.posX, this.posY, this.diamètre, this.diamètre);
		
		if(this.variateurVitesse != 0) {
			this.p.strokeWeight(1);
			this.p.noFill();
			for (int i = 0; i < (this.variateurVitesse == 0 ? this.variateurVitesse/50F : this.variateurVitesse/50F * 2); i++) {
				this.p.stroke(this.teinte, 42, 255, 255 - i/(this.variateurVitesse == 0 ? this.variateurVitesse/50F : this.variateurVitesse/50F * 2) * 255);
				this.p.ellipse(this.posX, this.posY, this.diamètre + i, this.diamètre + i);
			}
		}
	}
	
	public boolean orientationGauche() {
		return this.compoVitesseX < 0;
	}
	public boolean orientationDroite() {
		return this.compoVitesseX >= 0;
	}

	public boolean orientationHaut() {
		return this.compoVitesseY < 0;
	}
	public boolean orientationBas() {
		return this.compoVitesseY >= 0;
	}
	
	public void inverserVertical() {
		this.orientation = (float) (((Math.PI * 2) - this.orientation) % (Math.PI * 2));
	}
	public void inverserHorizontal() {
		if(this.orientation <= Math.PI)
			this.orientation = (float) ((Math.PI - this.orientation % PApplet.TWO_PI));
		else
			this.orientation = (float) ((PApplet.TWO_PI - (this.orientation - Math.PI)) % PApplet.TWO_PI);
	}
	
	public void booster() {
		this.variateurVitesse = 200;
		this.vitesseInit = this.vitesse;
	}
	
	public void optimiserOrientation(Raquette raquette) {
		if(raquette.sens() != 0) {
			float angleRelatif = this.angleRelatif();
			if((this.orientation > 0 && this.orientation <= PApplet.HALF_PI) || (this.orientation > 3 * PApplet.HALF_PI && this.orientation <= PApplet.TWO_PI)) {
				this.orientation += -raquette.sens() * PApplet.QUARTER_PI * (1 - angleRelatif / (PApplet.HALF_PI * 1F));
			} else {
				this.orientation += raquette.sens() * PApplet.QUARTER_PI * (1 - angleRelatif / (PApplet.HALF_PI * 1F));
			}
		}
		this.corrigerOrientation();
	}
	private float angleRelatif() {
		float res = 0;
		if(this.orientation > 0 && this.orientation <= PApplet.HALF_PI) {
			res = this.orientation;
		} else if(this.orientation > PApplet.HALF_PI && this.orientation <= PApplet.PI) {
			res = PApplet.PI - this.orientation;
		} else if(this.orientation > PApplet.PI && this.orientation <= 3 * PApplet.HALF_PI) {
			res = this.orientation - PApplet.PI;
		} else {
			res = PApplet.PI - (this.orientation - PApplet.PI);
		}
		return res;
	}
	private void corrigerOrientation() {
		if(this.orientation > PApplet.HALF_PI - this.angleEnnui && this.orientation < PApplet.HALF_PI + this.angleEnnui) {
			if(this.orientationDroite())
				this.orientation = PApplet.HALF_PI + this.angleEnnui;
			if(this.orientationGauche())
				this.orientation = PApplet.HALF_PI - this.angleEnnui;
		}
		if(this.orientation > 3 * PApplet.HALF_PI - this.angleEnnui && this.orientation < 3 * PApplet.HALF_PI + this.angleEnnui) {
			if(this.orientationGauche())
				this.orientation = 3 * PApplet.HALF_PI + this.angleEnnui;
			if(this.orientationDroite())
				this.orientation = 3 * PApplet.HALF_PI - this.angleEnnui;
		}
	}
	
	public int getDiametre() {
		return diamètre;
	}
	public void setDiametre(int diamètre) {
		this.diamètre = diamètre;
	}
	
	public float getVitesse() {
		return vitesse;
	}
	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}
	
	public float getCompoVitesseX() {
		return compoVitesseX;
	}

	public float getCompoVitesseY() {
		return compoVitesseY;
	}

	public int getVariateurVitesse() {
		return variateurVitesse;
	}
	public void setVariateurVitesse(int variateurVitesse) {
		this.variateurVitesse = variateurVitesse;
	}
	
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	public int getTeinte() {
		return teinte;
	}
	public void setTeinte(int teinte) {
		this.teinte = teinte;
	}

	public int getVariateurTeinte() {
		return variateurTeinte;
	}
	public void setVariateurTeinte(int variateurTeinte) {
		this.variateurTeinte = variateurTeinte;
	}
	
	public int getSaturation() {
		return saturation;
	}
	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}
	
	public int getVariateurSaturation() {
		return variateurSaturation;
	}
	public void setVariateurSaturation(int variateurSaturation) {
		this.variateurSaturation = variateurSaturation;
	}
}
