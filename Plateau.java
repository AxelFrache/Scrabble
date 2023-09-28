
public class Plateau {
	private Case [][] g; // g pour grille
	
	private static int[][] plateau = {
			{5,1,1,2,1,1,1,5,1,1,1,2,1,1,5},
			{1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
			{1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
			{2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
			{1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
			{1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
			{1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
			{5,1,1,2,1,1,1,4,1,1,1,2,1,1,5},
			{1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
			{1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
			{1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
			{2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
			{1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
			{1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
			{5,1,1,2,1,1,1,5,1,1,1,2,1,1,5}
	};
	
	private boolean estVide;
	
	//constructeur de Plateau
	public Plateau() {
		this.g=new Case[plateau.length][plateau[1].length];
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[i].length; j++) {
				this.g[i][j] = new Case(plateau[i][j]);
			}
		}
		this.estVide = true;
	}
	
	/**
	* resultat : chaine decrivant ce Plateau
	*/
	public String toString (){ 
		String affichage = "";
		System.out.println();
		affichage = "   1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 \n";
		for (int i = 1; i <= 15 ; i++) {
			if (i < 10) {
				affichage += i + " |";
			}
			else {
				affichage += i + "|";
			}
			for (int j = 1; j <= 15; j++) {
				if (plateau[i-1][j-1] == 1 && this.g[i-1][j-1].estRecouverte() == false) {
					affichage += "__|";
				}
				else if (plateau[i-1][j-1] != 1 && this.g[i-1][j-1].estRecouverte() == false){
					affichage += this.g[i-1][j-1].getCouleur() +" |";
				}
				else {
					affichage += this.g[i-1][j-1].getLettre() +" |";
				}
			}
			affichage += "\n";
		}
		return affichage;
	}
	
	/**
	* pre-requis : mot est un mot accepte par CapeloDico,
	* 0 <= numLig <= 14, 0 <= numCol <= 14, sens est un element
	* de {h,v} et l entier maximum prevu pour e est au moins 25
	* resultat : retourne vrai ssi le placement de mot sur this a partir
	* de la case (numLig, numCol) dans le sens donne par sens a l aide
	* des jetons de e est valide.
	*/
	public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e) { 
		boolean retourne = false;
		int longueurMot = mot.length();
		int derniereCaseLig, derniereCaseCol;
		if (sens == 'v') {
			derniereCaseLig = (longueurMot + (numLig-1));
			derniereCaseCol = numCol;
		}
		else {
			derniereCaseLig = numLig;
			derniereCaseCol = (longueurMot + (numCol-1));
		}
		if (estVide == true) { //la plateau est vide
			if (longueurMot >= 2) { //le mot est plus grand ou egal a 2
				if (sens == 'v') { //le mot est place a la verticale
					if (numCol == 8 && (derniereCaseLig) <= 15 && numLig <= numCol 
							&& (derniereCaseLig) >= numCol) { //le mot a un de ses caracteres qui est place a la case du milieu
						if (MEE.motDansMEE(mot, e) == true) { //verifie que les jetons dans le chevalet peuvent former le mot
							retourne = true; //retourne vrai si le placement est valide
						}
					}
				}
				else if (sens == 'h') { //le mot est place a l'horizontal
					if (numLig == 8 && (derniereCaseCol) <= 15 && numCol <= numLig 
							&& (derniereCaseCol) >= numLig) { //le mot a un de ses caracteres qui est place a la case du milieu
						if (MEE.motDansMEE(mot, e) == true) { // verifie que les jetons dans le chevalet peuvent former le mot
							retourne = true; //retourne vrai si le placement est valide
						}
					}
				}
			}
		}
		else { //le plateau n'est pas vide
			if (longueurMot >= 2) { //le mot est plus grand ou egal a 2
				if (sens == 'v') { //le mot est place a la verticale
					if (derniereCaseLig <= 15) { //le mot ne depasse pas du plateau par le bas du plateau
						if (numLig >= 1) {
							if (numLig > 1 && g[numLig-2][numCol-1].estRecouverte() == false && g[derniereCaseLig][derniereCaseCol-1].estRecouverte() == false //le mot ne commence pas au bord et n a pas de case recouverte avant et apres
								|| numLig==1 && g[derniereCaseLig][derniereCaseCol-1].estRecouverte() == false //le mot commence au bord et n'est pas suivi par une case recouverte
								|| derniereCaseLig ==15 && g[numLig-2][numCol-1].estRecouverte() == false){ //le mot se termine au bord et n'est pas preceder par une case recouverte
								int verif = 0, compteurAuMoinsUneNonRecouverte = 0, compteurAuMoinsUneRecouverteEtEgale = 0;
								for (int i = numLig; i <= derniereCaseLig; i++) {//parcourt la zone du mot
									if (g[i-1][numCol-1].estRecouverte() == true && g[i-1][numCol-1].getLettre() == mot.charAt(i-numLig)) { //une case recouverte et ayant la meme lettre que le mot
										compteurAuMoinsUneRecouverteEtEgale++;
									}
									else if (g[i-1][numCol-1].estRecouverte() == true && g[i-1][numCol-1].getLettre() != mot.charAt(i-numLig)) { // une case recouverte et n ayant pas la meme lettre que le mot
										verif = -1;
									}
									else if (g[i-1][numCol-1].estRecouverte() == false) { //une case non recouverte
									compteurAuMoinsUneNonRecouverte++;
									}
								}
								if (MEE.motDansMEE(mot, e) == true && verif != -1 && compteurAuMoinsUneRecouverteEtEgale > 0 && compteurAuMoinsUneNonRecouverte > 0) { //verifie que les jetons dans le chevalet peuvent former le mot
									retourne = true; //le placement est valide
								}
							}
						}
					}
				}
				else if (sens == 'h') {
					if (derniereCaseCol <= 15) { //le mot ne depasse pas du plateau par la droite du plateau
						if (numCol >= 1) { //Le mot ne depasse pas par la gauche du plateau
							if (numCol > 1 && g[numLig-1][numCol-2].estRecouverte() == false && g[derniereCaseLig-1][derniereCaseCol].estRecouverte() == false //le mot ne commence pas au bord et n'a pas de case recouverte avant et apres
									|| numCol==1 && g[derniereCaseLig-1][derniereCaseCol].estRecouverte() == false //le mot commence au bord et n'est pas suivi par une case recouverte
									|| derniereCaseCol ==15 && g[numLig-1][numCol-2].estRecouverte() == false){ //le mot se termine au bord et n'est pas preceder par une case recouverte
								int verif = 0, compteurAuMoinsUneNonRecouverte = 0, compteurAuMoinsUneRecouverteEtEgale = 0;
								for (int i = numCol; i <= derniereCaseCol; i++) {//parcours de la zone du mot
									if (g[numLig-1][i-1].estRecouverte() == true && g[numLig-1][i-1].getLettre() == mot.charAt(i-numCol)) { //une case recouverte et ayant la meme lettre que le mot
										compteurAuMoinsUneRecouverteEtEgale++;
									}
									else if (g[numLig-1][i-1].estRecouverte() == true && g[numLig-1][i-1].getLettre() != mot.charAt(i-numCol)) { //une case recouverte et n ayant pas la meme lettre que le mot
										verif = -1;
									}
									else if (g[numLig-1][i-1].estRecouverte() == false) { //une case non recouverte
										compteurAuMoinsUneNonRecouverte++;
									}
								}
								if (MEE.motDansMEE(mot, e) == true && verif != -1 && compteurAuMoinsUneRecouverteEtEgale > 0 && compteurAuMoinsUneNonRecouverte > 0) { //verifie que les jetons dans le chevalet peuvent former le mot
									retourne = true; //retourne vrai si le placement est valide
								}
							}
						}
					}
				}
			}
		}
		if (numLig < 0 || numLig > 15 || numCol < 0 || numCol >15) {
			retourne = false;
		}
		return retourne;
	}

	/**
	* pre-requis : le placement de mot sur this a partir de la case
	* (numLig, numCol) dans le sens donne par sens est valide
	* resultat : retourne le nombre de points rapportes par ce placement, le
	* nombre de points de chaque jeton etant donne par le tableau nbPointsJet.
	*/
	public static int nbPointsPlacement(String mot,int numLig,int numCol, char sens, int[] nbPointsJet){
        int motCompteDouble = 1;
        int motCompteTriple = 1;

        int nbPts = 0;
        int nbPtsTot = 0;
        for(int i = 0; i < mot.length();i++){
            if(sens=='h'){ //si le sens est horizontal

                if(plateau[numLig-1][(numCol-1)+i] == 4){ //verifie s il s agit d une case mot compte double
                    motCompteDouble = 2;
                    nbPts = nbPointsJet[Ut.majToIndex(mot.charAt(i))]; //recupere le nombre de points que vaut la lettre
                }
                else if(plateau[numLig-1][(numCol-1)+i] == 5){ //verifie s il s agit d une case mot compte triple
                    motCompteTriple = 3;
                    nbPts = nbPointsJet[Ut.majToIndex(mot.charAt(i))]; //recupere le nombre de points que vaut la lettre
                }
                else{
                    nbPts = nbPointsJet[Ut.majToIndex(mot.charAt(i))] * plateau[numLig][numCol+i];
                }
            }
            
            else{ //si le sens est vertical

                if(plateau[(numLig-1)+i][(numCol-1)] == 4){ //verifie s il s agit d une case mot compte double
                    motCompteDouble = 2;
                    nbPts = nbPointsJet[Ut.majToIndex(mot.charAt(i))]; //recupere le nombre de points que vaut la lettre
                }
                else if(plateau[(numLig-1)+i][(numCol-1)] == 5){ //verifie s il s agit d une case mot compte triple
                    motCompteTriple = 3;
                    nbPts = nbPointsJet[Ut.majToIndex(mot.charAt(i))]; //recupere le nombre de points que vaut la lettre
                }
                else{
                    nbPts = nbPointsJet[Ut.majToIndex(mot.charAt(i))] * plateau[numLig+i][numCol];
                }

            }
            nbPtsTot = nbPtsTot + nbPts;
        }
            
        return nbPtsTot * motCompteDouble * motCompteTriple; //retourne le nombre de points du placement
}
	
	/**
	* pre-requis : le placement de mot sur this a partir de la case
	* (numLig, numCol) dans le sens donne par sens a l aide des
	* jetons de e est valide.
	* action/resultat : effectue ce placement et retourne le
	* nombre de jetons retires de e.
	*/
	public int place(String mot, int numLig, int numCol, char sens, MEE e){ 

        int nbJetonRetire = 0; //compte le nombre de jeton retir� de l'ensemble

        for(int i = 0; i < mot.length();i++){

            if(e.contient(mot.charAt(i))==true){
            	if(sens=='v'){ //SI l'ensemble contient la lettre ALORS assigner la lettre i du mot a une case de la grille
            		if (this.g[(numLig-1)+i][numCol-1].estRecouverte() //verifie que la case est recouverte
                			&& this.g[(numLig-1)+i][numCol-1].getLettre() == mot.charAt(i)) {//verifie que la lettre dans la case correspond a la lettre que l on souhaite placer
            			System.out.println(this.g[numLig-1][(numCol-1)+i].getLettre());
            			this.g[(numLig-1)+i][(numCol-1)].setLettre(mot.charAt(i));
            		}
            		else{
            			this.g[(numLig-1)+i][(numCol-1)].setLettre(mot.charAt(i));
            			e.retire(Ut.majToIndex(mot.charAt(i))); //retire de l ensemble la lettre d'indice i du mot
                        nbJetonRetire++;
            		}
            		 
                }
            	else if(sens=='h'){
            		if (this.g[numLig-1][(numCol-1)+i].estRecouverte() //verifie que la case est recouverte
                			&& this.g[numLig-1][(numCol-1)+i].getLettre() == mot.charAt(i)) {//verifie que la lettre dans la case correspond a la lettre que l on souhaite placer
            			this.g[numLig-1][(numCol-1)+i].setLettre(mot.charAt(i)); 
            		}
            		else{
            			this.g[numLig-1][(numCol-1)+i].setLettre(mot.charAt(i));
            			e.retire(Ut.majToIndex(mot.charAt(i))); //retire de l ensemble la lettre d'indice i du mot
                        nbJetonRetire++;
            		}
                }
            }	
        }
        this.estVide=false;
        return nbJetonRetire;
    }

}
