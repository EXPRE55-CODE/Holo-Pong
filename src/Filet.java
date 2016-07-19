import processing.core.PApplet;

public class Filet extends ÉlémentJeu {
	private int posCache;
	private int dimCache;
	private int dimX;
	
	private int marge;
	
	public Filet(PApplet p, int posX, int posY, int dimX, int marge) {
		super(p, posX, posY);

		this.dimCache = 500;
		this.posCache = -this.dimCache;
		
		this.dimX = dimX;
		
		this.marge = marge;
	}
	
	public void initialiserAnimation() {
		this.posCache = -this.dimCache;
	}
	
	public void afficher() {
		for (int y = this.marge; y < this.p.height - this.marge; y += 85) {
			this.p.noStroke();
			this.p.fill(255, 85);
			this.p.rect(this.posX, this.posY + y, this.marge, 50);
			if(this.posCache != -this.dimCache) {
				this.p.fill(0);
				this.p.rect(this.posX, this.posCache, this.dimX, this.dimCache);
			}
		}
	}
	
	public void animer() {
		this.posCache += 20;
	}

	public int getPosCache() {
		return posCache;
	}
	public void setPosCache(int posCache) {
		this.posCache = posCache;
	}
}
