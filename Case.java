
public class Case {
    private int couleur;
    private boolean libre;
    private char lettre;

    /**
    * pre-requis : uneCouleur est un entier entre 1 et 5
    * action : constructeur de Case
    */
    public Case(int uneCouleur){
    	this.couleur = uneCouleur;
    	this.libre = true;
    }

    /**
    * resultat : la couleur de this, un nombre entre 1 et 5
    */
    public int getCouleur(){
    	return this.couleur;
    }

    /**
    * pre-requis : cette case est recouverte
    */
    public char getLettre(){
    	return this.lettre;
    }

    /**
    * pre-requis : let est une lettre majuscule
    */
    public void setLettre(char let){
    	this.lettre = let;
    	this.libre = false;
    }   

    /**
    * resultat : vrai ssi la case est recouverte par une lettre
    */
    public boolean estRecouverte(){
    	return(this.libre == false);
    }

    public String toString(){ 
    	String retourne = "";
    	if (this.estRecouverte() == false) {
    		retourne += "Cette case ne contient pas de lettre " + "\n";
    	}
    	else {
    		retourne += "Cette case contient la lettre " + this.lettre + "\n";
    	}
		retourne += "Cette case compte ";
		if (this.couleur == 1) {
			retourne += "normalement";
		}
		else if (this.couleur == 2) {
			retourne += "double sur la lettre";
		}
		else if (this.couleur == 3) {
			retourne += "triple sur la lettre";
		}
		else if (this.couleur == 4) {
			retourne += "double sur le mot";
		}
		else if (this.couleur == 5) {
			retourne += "triple sur le mot";
		}
    	return retourne;
    }

}