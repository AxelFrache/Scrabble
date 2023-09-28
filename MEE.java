public class MEE {
    private int [] tabFreq; // tabFreq[i] est le nombre d'exemplaires
    // (frequence) de l'element i
    private int nbTotEx; // nombre total d'eexemplaires
    
    /**
    * pre-requis : max >= 0
    * action : cree un multi-ensemble vide dont les elements seront
    * inferieurs a max
    */
    public MEE (int max){
        this.tabFreq = new int[max];
        this.nbTotEx = 0;
    }

    /**
    * pre-requis : les elements de tab sont positifs ou nuls
    * action : cree un multi-ensemble dont le tableau de frequences est
    *. une copie de tab
    */
    public MEE (int[] tab){
        this.tabFreq = new int [tab.length];
        for(int i = 0;i<tabFreq.length;i++){
            this.tabFreq[i] = tab[i]; // on attribut les valeurs d'indice i du tableau mesVal au tableau ensTab
            this.nbTotEx += this.tabFreq[i]; // on incremente le cardinal de 1
        }
    }

    /**
     * constructeur par copie
     */
    public MEE (MEE e){
        this(e.tabFreq.length); //on recopie la taille du tableau initiale
        for(int i = 0; i< e.tabFreq.length;i++){
           this.tabFreq[i] = e.tabFreq[i];
           this.nbTotEx += this.tabFreq[i];
        }
    }

    /**
     * resultat : vrai ssi cet ensemble est vide
     */
    public boolean estVide (){
    	return(this.nbTotEx==0);
    }

    /**
    * pre-requis : 0 <= i < tabFreq.length
    * action : ajoute un exemplaire de i � this
    */
    public void ajoute (int i) {
    	this.tabFreq[i] = this.tabFreq[i] + 1;
    	nbTotEx++;
    }

    /**
    * pre-requis : 0 <= i < tabFreq.length
    * action/resultat : retire un exemplaire de i de this s il en existe,
    * et retourne vrai ssi cette action a pu etre effectuee
    */
    public boolean retire (int i) {
    	boolean valeurRetire = false;
    	if(this.tabFreq[i]>0){
    		this.tabFreq[i] = this.tabFreq[i] - 1;
    		nbTotEx--;
    		valeurRetire = true;
    	}
    return valeurRetire;
}

    /**
    * pre-requis : this est non vide
    * action/resultat : retire de this un exemplaire choisi aleatoirement
    * et le retourne
    */
    public int retireAleat () {
        int exAleatoire = Ut.randomMinMax(0, this.tabFreq.length-1);
        while(retire(exAleatoire) == false){
            exAleatoire = Ut.randomMinMax(0, this.tabFreq.length-1);
        }
        return exAleatoire;
    }
    /**
    * pre-requis : 0 <= i < tabFreq.length et i < e.tabFreq.length
    * action/resultat : transfere un exemplaire de i de this vers e s il
    * en existe, et retourne vrai ssi cette action a pu etre effectuee
    */
    public boolean transfere (MEE e, int i) {
    	
        boolean elmtTransfere = false;
        if(retire(i)==true){
            e.ajoute(i);
            elmtTransfere=true;
        }
        return elmtTransfere;
    }

    /** pre-requis : k >= 0 et tabFreq.length <= e.tabFreq.length
    * action : tranfere k exemplaires choisis aleatoirement de this vers e
    * dans la limite du contenu de this
    * resultat : le nombre d exemplaires effectivement transferes
    */
    public void transfereAleat (MEE e, int k) {
    	
        int r = Ut.randomMinMax(0,this.tabFreq.length-1); 
        int i = 0;
        while(i<k && e.estVide() == false){
            while(e.tabFreq[r]==0){
                r = Ut.randomMinMax(0,this.tabFreq.length-1); 
            }
            if(e.tabFreq[r] >= 1){
                e.transfere(this, r);
            }
            r = Ut.randomMinMax(0,this.tabFreq.length-1);
            i++;
        }
    }

    /**
    * pre-requis : tabFreq.length <= v.length
    * resultat : retourne la somme des valeurs des exemplaires des
    * elements de this, la valeur d un exemplaire d un element i
    * de this etant egale a v[i]
    */
    public int sommeValeurs (int[] v){
        int somme = 0;
        for(int i = 0; i < this.tabFreq.length; i++){
            somme = somme + (this.tabFreq[i] * v[i]);
        }
        return somme;
    }

    public String toString(){ //affiche le contenu du tableau cible
        for(int i = 0; i<this.tabFreq.length;i++){
            System.out.print(this.tabFreq[i] + " ");
        }
        System.out.println("");
        return("");
    }

    public int getNbTotEx(){
        return this.nbTotEx;
    }
    
    public boolean contient(char c){ //retourne vrai si l ensemble contient le caractere c, sinon retourne faux
        boolean estContenu = false;        
        if (Ut.majToIndex(c) <= 25 && Ut.majToIndex(c) >= 0) {
        	if(this.tabFreq[Ut.majToIndex(c)] >= 1){
                estContenu = true;
            }
		}
		else {
			if(this.tabFreq[Ut.alphaToIndex(c)] >= 1){
                estContenu = true;
            }
		}
        return estContenu;
    }
    
    //retourne un ensemble avec les elements communs entre l ensemble en parametre et this
    public MEE intersection(MEE e1){
	    MEE Ens = new MEE(this.tabFreq.length);
	    for(int j = 0; j<e1.tabFreq.length; j++) { 
	    	for(int i =0; i<this.tabFreq.length;i++){ // boucle sur l'ensemble du tableau 
	    		if (Ut.indexToMaj(i) <= 'Z' && Ut.indexToMaj(i) >= 'A') { //le caractere de la table est une majuscule
	    			if (this.contient(Ut.indexToMaj(i)) && e1.nbTotEx > Ens.nbTotEx && this.nbTotEx > Ens.nbTotEx && e1.tabFreq[i] > Ens.tabFreq[i] && this.tabFreq[i] > Ens.tabFreq[i]) {
	    				Ens.ajoute(i);
	    			}
	    		}
	    	}
	    }
	    return Ens;
	}
    
    //retourne vrai si le mot en parametre est inclus dans l ensemble en parametre, sinon retourne faux
    public static boolean motDansMEE(String mot, MEE e) {
		boolean retourne = false;
		MEE e2 = new MEE(e.tabFreq.length);
		for (int i = 0; i < mot.length(); i++) {
			if (Ut.majToIndex(mot.charAt(i)) <= 25 && Ut.majToIndex(mot.charAt(i)) >= 0) {
				e2.ajoute(Ut.majToIndex(mot.charAt(i)));
			}
			else {
				e2.ajoute(Ut.alphaToIndex(mot.charAt(i)));
			}
		}
    	if ((e.intersection(e2)).compareTab(e2) == true) {
			retourne = true;
		}
		return retourne;
	}
    
    //retourne vrai si deux ensembles sont identiques, sinon retourne faux
    public boolean compareTab(MEE e) {
    	boolean retourne = true;
    	int i = 0;
    	while (i < this.tabFreq.length && retourne != false) {
    		if (this.tabFreq[i] != e.tabFreq[i]) {
    			retourne = false;
    		}
    		i++;
    	}
    	return retourne;
    }
    
    //retourne un tableau de caracteres contenant les lettres presentent dans l ensemble this
    public char[] lettreDansMEE(char[] lettresJetons){
        char [] lettres = new char[26]; 
        int lettrePresente = 0;
        MEE copie = new MEE(this.tabFreq);
        for(int i = 0; i< this.tabFreq.length; i++){
            while (copie.tabFreq[i] > 0){
                lettres[lettrePresente] = lettresJetons[i];
                lettrePresente++;
                copie.tabFreq[i]--;
            }
        }
        return lettres;
    }
}
