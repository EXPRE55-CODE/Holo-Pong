public class Ordinateur {
	private Raquette raquetteOrdinateur;
	private Balle balle;
	
	private boolean tentative;
	
	public Ordinateur(Raquette raquetteOrdinateur, Balle balle) {
		this.raquetteOrdinateur = raquetteOrdinateur;
		this.balle = balle;
		
		this.tentative = false;
	}

	public void actualiserPosition(int marge) {
		if (this.raquetteOrdinateur.get�tatAnim() == Raquette.�tatsAnimation.REPOS) {
			if (this.balle.getPosY() < this.raquetteOrdinateur.getPosY()
					+ this.raquetteOrdinateur.getDimY() / 2)
				this.raquetteOrdinateur.d�placerHaut(marge);
			if (this.balle.getPosY() > this.raquetteOrdinateur.getPosY()
					+ this.raquetteOrdinateur.getDimY() / 2)
				this.raquetteOrdinateur.d�placerBas(marge);
		}
	}
	
	public void g�rerBoost(IndicateurBoost indicateurBoostOrdi) {
		float proba = (float) Math.random();
		if(this.balle�Port�e()) {
			if(proba > 1/3F && !this.tentative) {
				this.raquetteOrdinateur.booster(-1);
				indicateurBoostOrdi.r�initialiser();
			} else {
				this.tentative = true;
			}
		} else {
			if(this.tentative)
				this.tentative = false;
		}
	}
	private boolean balle�Port�e() {
		return (
			this.balle.getPosX() + this.balle.getDiametre() > this.raquetteOrdinateur.getPosX() + Raquette.LIMITE_SORTIE/2
			&& this.balle.getPosX() - this.balle.getDiametre() < this.raquetteOrdinateur.getPosX() + this.raquetteOrdinateur.getDimX()
			&& this.balle.getPosY() > this.raquetteOrdinateur.getPosY() && this.balle.getPosY() < this.raquetteOrdinateur.getPosY() + this.raquetteOrdinateur.getDimY()
			&& this.balle.orientationDroite()
		);
	}
}
