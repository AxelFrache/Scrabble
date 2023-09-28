
public class Joueur {
	private String nom;
	private MEE chevalet;
	private int score;
	
	public Joueur(String unnom) {
		this.nom=unnom;
		this.chevalet = new MEE(26);
		this.score = 0;
	}
	
	//affiche le nom et le score du joueur
	public String toString() {
		return ("nom : " + this.nom + "\n" + "score : " + this.score + "\n");
	}
	
	//recupere le score du joueur
	public int getScore(){
	    return this.score;
	}
	
	//ajoute des points au score total du joueur
	public void ajouteScore(int nb) {
		this.score += nb;
	}
	
	/**
	* pre-requis : nbPointsJet indique le nombre de points rapports pour chaque jeton/lettre
	* resultat : le nombre de points total sur le chevalet de ce joueur
	* suggestion : bien relire la classe MEE !
	*/
	public int nbPointsChevalet (int[] nbPointsJet){
		return this.chevalet.sommeValeurs(nbPointsJet);
	}
	
	/**
	* pre-requis : les elements de s sont inferieurs a 26
	* action : simule la prise de nbJetons jetons par this dans le sac s,
	* dans la limite de son contenu.
	*/
	public void prendJetons (MEE s, int nbJetons) {
		this.chevalet.transfereAleat(s, nbJetons);
	}
	
	/*
	* pre-requis : les elements de s sont inferieurs a 26
	* et nbPointsJet.length >= 26
	* action : simule le coup de this : this choisit de passer son tour,
	* d echanger des jetons ou de placer un mot
	* resultat : -1 si this a passe son tour, 1 si son chevalet est vide,
	* et 0 sinon
	*/
	public int joue(Plateau p, MEE s, int[] nbPointsJet) {
		System.out.println("Selectionnez une option parmi les suivantes:");
		System.out.print(" - Entrez -1 pour passer votre tour\n - Entrez 0 pour echanger des jetons\n - Entrez 1 pour placer un mot\n\n" + "Entrez votre choix: ");
		int res = Ut.saisirEntier();
		if (res == 0) { //le chevalet du joueur est plein et il souhaite echanger des jetons
			this.echangeJetons(s);
		}
		if (res == 1) {
			System.out.print(this.joueMot(p, s, nbPointsJet));
		}
		return res;
	}
	
	/** pre-requis : les elements de s sont inferieurs a 26
	* et nbPointsJet.length >= 26
	* action : simule le placement d�un mot de this :
	* a) le mot, sa position sur le plateau et sa direction, sont saisis
	* au clavier
	* b) verifie si le mot est valide
	* c) si le coup est valide, le mot est place sur le plateau
	* resultat : vrai ssi ce coup est valide, c est-a-dire accepte par
	* CapeloDico et satisfaisant les regles detaillaes plus haut
	* strategie : utilise la methode joueMotAux
	*/
	public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {
		boolean retourne = false;
		String mot = "ZZZZZZZZ";
		while (MEE.motDansMEE(mot, this.chevalet)!= true) {
			System.out.print("Veuillez saisir un mot valide: ");
			mot = Ut.saisirChaine();
		}
		System.out.println("Selectionnez la position a laquelle vous voulez placer votre mot: ");
		int numLigne = 17;
		while (numLigne <= 1 || numLigne >= 15) {
			System.out.print("Ligne : ");
			numLigne = Ut.saisirEntier();
		}
		int numCol = 17;
		while (numCol <= 1 || numCol >= 15) {
			System.out.print("Colonne : ");
			numCol = Ut.saisirEntier();
		}
		char sens = 'z';
		System.out.println();
		while (sens != 'v' && sens != 'h') {
			System.out.print("Veuillez saisir sa direction par h pour horizontal ou v pour verticale: ");
			sens = Ut.saisirCaractere();
		}
		if (p.placementValide(mot, numLigne, numCol, sens, this.chevalet)) { //on regarde si le joueur dispose du mot entre dans son chevalet
			this.joueMotAux(p, s, nbPointsJet, mot, numLigne, numCol, sens);
			retourne = true;
		}
		return retourne;
	}
	
	/** pre-requis : cf. joueMot et le placement de mot a partir de la case
	* (numLig, numCol) dans le sens donne par sens est valide
	* action : simule le placement d un mot de this
	*/
	public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot,
	int numLig, int numCol, char sens) {
		int nbRetire = p.place(mot, numLig, numCol, sens, this.chevalet);
		this.prendJetons(s, nbRetire);
		this.ajouteScore(Plateau.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));
		if (nbRetire == 7) { 
			this.ajouteScore(50);
		}
	}
	
	/**
	* pre-requis : sac peut contenir des entiers de 0 a 25
	* action : simule l echange de jetons de ce joueur :
	* - saisie de la suite de lettres du chevalet a echanger
	* en verifiant que la suite soit correcte
	* - echange de jetons entre le chevalet du joueur et le sac
	* strategie : appelle les methodes estCorrectPourEchange et echangeJetonsAux
	*/
	public void echangeJetons(MEE sac) {
		System.out.print("Saisissez les lettres que vous souhaitez echanger: ");
		String mot = Ut.saisirChaine();
		System.out.println();
		while (this.estCorrectPourEchange(mot) == false) {
			System.out.print("Saisissez les lettres que vous souhaitez echanger (en majuscule): ");
			mot = Ut.saisirChaine();
		}
		this.echangeJetonsAux(sac, mot);
	}
	
	/** resultat : vrai ssi les caracteres de mot correspondent tous a des
	* lettres majuscules et l ensemble de ces caracteres est un
	* sous-ensemble des jetons du chevalet de this
	*/
	public boolean estCorrectPourEchange (String mot) {
		boolean retourne = false;
		int compteur = 0;
		for (int i = 0; i < mot.length(); i++) {
			if (Ut.majToIndex(mot.charAt(i)) <= 25 && Ut.majToIndex(mot.charAt(i)) >= 0) {
				compteur++;
			}
		}
		if (compteur == mot.length()) {
			if (MEE.motDansMEE(mot, this.chevalet) == true) {
				retourne = true;
			}
		}
		return retourne;
	}
	
	/** pre-requis : sac peut contenir des entiers de 0 a 25 et ensJetons
	* est un ensemble de jetons correct pour l echange
	* action : simule l echange de jetons de ensJetons avec des
	* jetons du sac tires aleatoirement.
	*/
	public void echangeJetonsAux(MEE sac, String ensJetons) {// ensJetons = mot
		for (int i = 0; i < ensJetons.length(); i++) {
			int trans = Ut.majToIndex(ensJetons.charAt(i));// recupere l'indice de la lettre d'indice i dans this
			this.chevalet.transfere(sac, trans);// transfere l'indice de la lettre d'indice i de this dans sac
			this.chevalet.transfereAleat(sac, 1);//transfere un exemplaire aleatoire dans this.chevalet
		}
	}
	
	//retourne le nom du joueur
	public String getMot() {
		return this.nom;
	}

	//retourne le chevalet du joueur
	public MEE getChevalet(){
		return this.chevalet;
	}

}
