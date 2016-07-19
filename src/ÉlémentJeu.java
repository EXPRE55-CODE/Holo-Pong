import processing.core.PApplet;

public abstract class ÉlémentJeu {
	protected PApplet p;
	
	protected int posX;
	protected int posY;
	
	public ÉlémentJeu(PApplet p, int posX, int posY) {
		this.p = p;
		this.posX = posX;
		this.posY = posY;
	}
	
	public abstract void afficher();
	
	public PApplet getP() {
		return p;
	}
	public void setP(PApplet p) {
		this.p = p;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
}
