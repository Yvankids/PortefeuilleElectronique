import java.util.ArrayList;
import java.util.Scanner;

class Transaction {

    private final String type;
    private final double montant;
    private final String description;

    Transaction(String type, double montant, String description) {
        this.type = type;
        this.montant = montant;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %10.2f FCFA | %s", type, montant, description);
    }
}

class Utilisateur {

    private final String nom;
    private final String numero;
    private double solde;
    private final ArrayList<Transaction> historique;

    Utilisateur(String nom, String numero, double soldeInitial) {
        this.nom = nom;
        this.numero = numero;
        this.solde = soldeInitial;
        this.historique = new ArrayList<>();

        if (soldeInitial > 0) {
            historique.add(new Transaction("DEPOT", soldeInitial, "Solde initial"));
        }
    }

    public String getNom() {
        return nom;
    }

    public String getNumero() {
        return numero;
    }

    public double getSolde() {
        return solde;
    }

    public ArrayList<Transaction> getHistorique() {
        return historique;
    }

    public void deposer(double montant) {
        solde = solde + montant;
        historique.add(new Transaction("DEPOT", montant, "Depot sur le compte"));
    }

    public void debiter(double montant, String description) {
        solde = solde - montant;
        historique.add(new Transaction("TRANSFERT -", montant, description));
    }

    public void crediter(double montant, String description) {
        solde = solde + montant;
        historique.add(new Transaction("TRANSFERT +", montant, description));
    }
}

public class PortefeuilleElectronique {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Utilisateur> utilisateurs = new ArrayList<>();

    public static void main(String[] args) {

        int choix;

        do {
            afficherMenu();
            choix = lireChoix();

            switch (choix) {
                case 1:
                    creerUtilisateur();
                    break;
                case 2:
                    consulterSolde();
                    break;
                case 3:
                    faireDepot();
                    break;
                case 4:
                    faireTransfert();
                    break;
                case 5:
                    consulterHistorique();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 0);

        scanner.close();
    }

    // Afficher le menu
    static void afficherMenu() {
        System.out.println("\n===== PORTEFEUILLE ELECTRONIQUE =====");
        System.out.println("1. Creer un utilisateur");
        System.out.println("2. Consulter le solde");
        System.out.println("3. Faire un depot");
        System.out.println("4. Faire un transfert");
        System.out.println("5. Consulter l'historique");
        System.out.println("0. Quitter");
    }

    // Lire le choix du menu, en rejetant les entrees non numeriques
    static int lireChoix() {

        System.out.print("Votre choix : ");

        while (!scanner.hasNextInt()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();
            System.out.print("Votre choix : ");
        }

        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }

    // Lire un montant strictement positif
    static double lireMontantPositif(String libelle) {

        System.out.print(libelle + " : ");

        while (!scanner.hasNextDouble()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();
            System.out.print(libelle + " : ");
        }

        double montant = scanner.nextDouble();
        scanner.nextLine();

        while (montant <= 0) {
            System.out.print("Le montant doit etre superieur a 0. " + libelle + " : ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next();
                System.out.print(libelle + " : ");
            }
            montant = scanner.nextDouble();
            scanner.nextLine();
        }

        return montant;
    }

    // Creer un utilisateur
    static void creerUtilisateur() {

        System.out.print("Numero : ");
        String numero = scanner.nextLine().trim();

        if (numero.isEmpty()) {
            System.out.println("Le numero ne peut pas etre vide.");
            return;
        }

        if (rechercherParNumero(numero) != null) {
            System.out.println("Un utilisateur avec ce numero existe deja.");
            return;
        }

        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();

        if (nom.isEmpty()) {
            System.out.println("Le nom ne peut pas etre vide.");
            return;
        }

        System.out.print("Solde initial (0 si aucun) : ");

        while (!scanner.hasNextDouble()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();
            System.out.print("Solde initial (0 si aucun) : ");
        }

        double soldeInitial = scanner.nextDouble();
        scanner.nextLine();

        if (soldeInitial < 0) {
            System.out.println("Le solde initial ne peut pas etre negatif.");
            return;
        }

        utilisateurs.add(new Utilisateur(nom, numero, soldeInitial));
        System.out.println("Utilisateur cree avec succes.");
    }

    // Consulter le solde d'un utilisateur
    static void consulterSolde() {

        Utilisateur utilisateur = demanderEtChercherUtilisateur();

        if (utilisateur == null) {
            return;
        }

        System.out.printf("Solde de %s : %.2f FCFA%n", utilisateur.getNom(), utilisateur.getSolde());
    }

    // Faire un depot sur le compte d'un utilisateur
    static void faireDepot() {

        Utilisateur utilisateur = demanderEtChercherUtilisateur();

        if (utilisateur == null) {
            return;
        }

        double montant = lireMontantPositif("Montant a deposer");

        utilisateur.deposer(montant);

        System.out.printf("Depot effectue. Nouveau solde : %.2f FCFA%n", utilisateur.getSolde());
    }

    // Faire un transfert entre deux utilisateurs
    static void faireTransfert() {

        System.out.print("Numero de l'expediteur : ");
        String numeroExpediteur = scanner.nextLine().trim();

        Utilisateur expediteur = rechercherParNumero(numeroExpediteur);

        if (expediteur == null) {
            System.out.println("Aucun utilisateur trouve avec ce numero.");
            return;
        }

        System.out.print("Numero du destinataire : ");
        String numeroDestinataire = scanner.nextLine().trim();

        Utilisateur destinataire = rechercherParNumero(numeroDestinataire);

        if (destinataire == null) {
            System.out.println("Aucun utilisateur trouve avec ce numero.");
            return;
        }

        if (numeroExpediteur.equalsIgnoreCase(numeroDestinataire)) {
            System.out.println("Impossible de faire un transfert vers soi-meme.");
            return;
        }

        double montant = lireMontantPositif("Montant a transferer");

        if (montant > expediteur.getSolde()) {
            System.out.println("Solde insuffisant pour effectuer ce transfert.");
            return;
        }

        expediteur.debiter(montant, "Vers " + destinataire.getNom() + " (" + destinataire.getNumero() + ")");
        destinataire.crediter(montant, "De " + expediteur.getNom() + " (" + expediteur.getNumero() + ")");

        System.out.println("Transfert effectue avec succes.");
        System.out.printf("Nouveau solde de %s : %.2f FCFA%n", expediteur.getNom(), expediteur.getSolde());
    }

    // Consulter l'historique des transactions d'un utilisateur
    static void consulterHistorique() {

        Utilisateur utilisateur = demanderEtChercherUtilisateur();

        if (utilisateur == null) {
            return;
        }

        if (utilisateur.getHistorique().isEmpty()) {
            System.out.println("Aucune transaction pour cet utilisateur.");
            return;
        }

        System.out.println("\n===== HISTORIQUE DE " + utilisateur.getNom().toUpperCase() + " =====");

        for (Transaction transaction : utilisateur.getHistorique()) {
            System.out.println(transaction);
        }
    }

    // Demande un numero au clavier et renvoie l'utilisateur correspondant (ou null)
    static Utilisateur demanderEtChercherUtilisateur() {

        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistre.");
            return null;
        }

        System.out.print("Numero de l'utilisateur : ");
        String numero = scanner.nextLine().trim();

        Utilisateur utilisateur = rechercherParNumero(numero);

        if (utilisateur == null) {
            System.out.println("Aucun utilisateur trouve avec ce numero.");
        }

        return utilisateur;
    }

    // Rechercher un utilisateur par son numero, renvoie null si non trouve
    static Utilisateur rechercherParNumero(String numero) {

        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getNumero().equalsIgnoreCase(numero)) {
                return utilisateur;
            }
        }

        return null;
    }
}
