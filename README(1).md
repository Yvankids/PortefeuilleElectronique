# Portefeuille Electronique

Petit programme en Java qui simule un portefeuille électronique en ligne de commande : créer des utilisateurs, consulter leur solde, faire des dépôts, faire des transferts entre utilisateurs, et consulter l'historique des transactions.

## Fonctionnalités

- Créer un utilisateur (nom, numéro, solde initial optionnel)
- Consulter le solde d'un utilisateur
- Faire un dépôt sur un compte
- Faire un transfert entre deux utilisateurs
- Consulter l'historique des transactions d'un utilisateur

## Comment ça marche

Chaque utilisateur a un numéro unique qui sert d'identifiant pour le retrouver. Un utilisateur possède un solde et une liste de transactions (dépôts, transferts envoyés, transferts reçus).

Pour un transfert, le programme vérifie que l'expéditeur et le destinataire existent, que ce n'est pas un transfert vers soi-même, et que l'expéditeur a un solde suffisant avant d'effectuer l'opération. Le transfert met à jour les deux comptes et enregistre la transaction dans l'historique de chacun.

## Lancer le programme

Compiler :
```
javac PortefeuilleElectronique.java
```

Exécuter :
```
java PortefeuilleElectronique
```

## Utilisation

Le programme affiche un menu :

```
===== PORTEFEUILLE ELECTRONIQUE =====
1. Creer un utilisateur
2. Consulter le solde
3. Faire un depot
4. Faire un transfert
5. Consulter l'historique
0. Quitter
```

Exemple d'utilisation :

```
Votre choix : 1
Numero : 001
Nom : Jorel
Solde initial (0 si aucun) : 5000
Utilisateur cree avec succes.

Votre choix : 3
Numero de l'utilisateur : 001
Montant a deposer : 2000
Depot effectue. Nouveau solde : 7000.00 FCFA
```

## Limites connues

- Les données ne sont pas sauvegardées : tout est perdu à la fermeture du programme
- Pas de mot de passe ou d'authentification pour protéger un compte
- Le solde initial et les montants ne sont pas plafonnés (pas de limite max)

## Pistes d'amélioration

- Sauvegarder les utilisateurs et transactions dans un fichier pour garder les données entre les exécutions
- Ajouter un code PIN pour sécuriser les opérations
- Ajouter une limite de montant par transaction ou par jour
- Ajouter la suppression d'un utilisateur
