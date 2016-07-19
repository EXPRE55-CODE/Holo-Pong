public class ControleurLimites {
	private Balle balle;
	
	private Raquette raquetteJoueur;
	private Raquette raquetteOrdinateur;
	
	private Score scoreJoueur;
	private Score scoreOrdinateur;
	
	private int marge;
	private int hauteurFenetre;
	
	private boolean sortieBalle;
	
	private boolean boostRaquetteJoueur;
	private boolean boostRaquetteOrdinateur;
	
	public ControleurLimites(Balle balle, Raquette raquetteJoueur, Raquette raquetteOrdinateur, Score scoreJoueur, Score scoreOrdinateur, int marge, int hauteurFenetre) {
		this.balle = balle;
		
		this.raquetteJoueur = raquetteJoueur;
		this.raquetteOrdinateur = raquetteOrdinateur;
		
		this.scoreJoueur = scoreJoueur;
		this.scoreOrdinateur = scoreOrdinateur;
		
		this.marge = marge;
		this.hauteurFenetre = hauteurFenetre;
		
		this.sortieBalle = false;
		
		this.boostRaquetteJoueur = false;
		this.boostRaquetteOrdinateur = false;
	}
	
	public void traiterCollisions() {
		if(this.collisionHautBas())
			this.balle.inverserVertical();	
		
		if(this.collisionRaquetteJoueur()) {
			this.balle.inverserHorizontal();
			this.balle.optimiserOrientation(this.raquetteJoueur);
			if(this.raquetteJoueur.getÉtatAnim() != Raquette.ÉtatsAnimation.REPOS && !this.boostRaquetteJoueur) {
				this.boostRaquetteJoueur = true;
				this.balle.booster();
			} else {
				this.boostRaquetteJoueur = false;
			}
		} else if(this.collisionRaquetteOrdinateur()) {
			this.balle.inverserHorizontal();
			this.balle.optimiserOrientation(this.raquetteOrdinateur);
			if(this.raquetteOrdinateur.getÉtatAnim() != Raquette.ÉtatsAnimation.REPOS && !this.boostRaquetteOrdinateur) {
				this.balle.booster();
				this.boostRaquetteOrdinateur = true;
			} else {
				this.boostRaquetteOrdinateur = false;
			}
		}
	}
	
	public void traiterSorties() {
		if(this.sortieJoueur()) {
			this.scoreOrdinateur.incrémenter();
			this.actualiserStatutScores();
			this.balle.initialiser();
			this.balle.lancer();
			
			this.sortieBalle = true;
		} else if(this.sortieOrdinateur()) {
			this.scoreJoueur.incrémenter();
			this.actualiserStatutScores();
			this.balle.initialiser();
			this.balle.lancer();
			
			this.sortieBalle = true;
		} else {
			this.sortieBalle = false;
		}
	}
	
	private boolean collisionHautBas() {
		return this.balle.getPosY() - this.balle.getDiametre()/2 < this.marge && this.balle.orientationHaut()
			|| this.balle.getPosY() + this.balle.getDiametre()/2 > this.hauteurFenetre - this.marge && this.balle.orientationBas();
	}
	
	private boolean collisionRaquetteJoueur() {
		return (this.balle.getPosY() > this.raquetteJoueur.getPosY() && this.balle.getPosY() < this.raquetteJoueur.getPosY() + this.raquetteJoueur.getDimY()
			 &&(   this.balle.getPosX() - this.balle.getDiametre()/2 < this.raquetteJoueur.getPosX() + this.raquetteJoueur.getDimX())
			 && this.balle.orientationGauche());
	}
	private boolean collisionRaquetteOrdinateur() {
		 return (this.balle.getPosY() > this.raquetteOrdinateur.getPosY() && this.balle.getPosY() < this.raquetteOrdinateur.getPosY() + this.raquetteOrdinateur.getDimY()
			  &&(   this.balle.getPosX() + this.balle.getDiametre()/2 > this.raquetteOrdinateur.getPosX()
				 && this.balle.getPosX() + this.balle.getDiametre()/2 < this.raquetteOrdinateur.getPosX() + this.raquetteOrdinateur.getDimX() + this.marge)
				 && this.balle.orientationDroite());
	}
	
	private boolean sortieJoueur() {
		return this.balle.getPosX() < 0;
	}
	private boolean sortieOrdinateur() {
		return this.balle.getPosX() > this.raquetteOrdinateur.getPosX() + this.raquetteOrdinateur.getDimY();
	}

	public boolean isSortieBalle() {
		return sortieBalle;
	}
	
	private void actualiserStatutScores() {
		this.scoreJoueur.setGagnant(this.scoreJoueur.getValeur() > this.scoreOrdinateur.getValeur());
		this.scoreOrdinateur.setGagnant(this.scoreJoueur.getValeur() < this.scoreOrdinateur.getValeur());
	}
}
