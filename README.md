<img width="1400" height="798" alt="Capture d’écran, le 2026-09-29 à 00 18 17" src="https://github.com/user-attachments/assets/89c07324-7069-4421-836f-4c4e8dd1c9aa" />
🚗 Système de guidage routier — Devoir 2

📌 Description

Ce projet est une application Java avec interface graphique développée avec Java Swing.

L’application simule un système de guidage routier (GPS) permettant à un utilisateur de choisir un point de départ et une destination sur une carte. Le programme calcule ensuite automatiquement le chemin le plus court en utilisant une approche basée sur l’algorithme de Dijkstra.

Le conducteur peut ensuite déplacer une voiture sur la carte à l’aide des touches directionnelles du clavier.

⸻

✨ Fonctionnalités

* 🗺️ Affichage d’une carte sous forme de grille
* 📍 Sélection d’un point de départ
* 🏁 Sélection d’une destination
* 🧭 Calcul automatique du chemin le plus court
* 🚗 Déplacement d’une voiture sur la carte
* ⌨️ Contrôle avec les touches directionnelles du clavier
* 🚦 Gestion du trafic
* ⚠️ Gestion des accidents
* 🔄 Recalcul d’itinéraire lorsqu’un obstacle affecte le trajet
* 🎨 Affichage graphique des différents éléments de la carte

⸻

🧠 Algorithme utilisé

Le calcul du chemin est réalisé dans la classe :

AppGps.java

Le système utilise une approche basée sur l’algorithme de Dijkstra.

À partir du point de départ, l’algorithme explore les différentes routes accessibles afin de déterminer un chemin vers la destination.

Les cases représentant du terrain ou certaines zones de trafic ne sont pas considérées comme navigables.

Les déplacements sont effectués dans quatre directions :

* Haut
* Bas
* Gauche
* Droite

⸻

🏗️ Structure du projet

Devoir2/
│
├── src/
│   └── com/
│       └── uqo/
│           │
│           ├── appmain/
│           │   ├── AppMain.java
│           │   ├── AppConstantes.java
│           │   └── package-info.java
│           │
│           ├── applogique/
│           │   ├── AppGps.java
│           │   ├── AppObstacle.java
│           │   └── package-info.java
│           │
│           └── appgui/
│               ├── AppFenetrePrincipale.java
│               ├── AppGrille.java
│               ├── AppOptions.java
│               ├── AppVoiture.java
│               └── package-info.java
│
├── bin/
│
└── devoir 2.pdf

⸻

📂 Description des principales classes

AppMain

Point d’entrée de l’application.

Cette classe initialise l’interface graphique et ouvre la fenêtre principale.

public static void main(String[] args)

⸻

AppFenetrePrincipale

Gère la fenêtre principale de l’application.

Elle permet de regrouper la grille représentant la carte et le panneau contenant les options de navigation.

⸻

AppGrille

Gère l’affichage de la carte.

Elle permet notamment d’afficher :

* les routes ;
* le terrain ;
* les points de départ ;
* les destinations ;
* le chemin calculé ;
* le trafic ;
* les accidents ;
* la voiture.

⸻

AppOptions

Gère le panneau permettant à l’utilisateur d’interagir avec le GPS.

L’utilisateur peut notamment :

1. sélectionner un point de départ ;
2. sélectionner une destination ;
3. calculer l’itinéraire ;
4. commencer la navigation ;
5. quitter la navigation.

⸻

AppGps

Contient la logique principale du système GPS.

Cette classe est responsable du calcul du chemin entre le point de départ et la destination.

La méthode principale utilisée est :

calculerCheminPlusCourt(Point depart, Point fin)

Elle utilise une PriorityQueue afin d’explorer les différentes positions de la carte.

⸻

AppVoiture

Gère la voiture et ses déplacements sur la carte.

La voiture peut être déplacée à l’aide des touches directionnelles du clavier.

Cette classe vérifie également les interactions entre la voiture et les obstacles présents sur le trajet.

⸻

AppObstacle

Gère les obstacles et les événements liés au trafic.

Elle participe notamment au déclenchement du recalcul de l’itinéraire lorsqu’une modification de la circulation affecte le chemin.

⸻

AppConstantes

Contient les constantes utilisées par l’application.

La grille possède les dimensions suivantes :

Largeur : 46 cases
Hauteur : 27 cases

Les différents types de cases sont représentés par des constantes :

CASE_TERRAIN
CASE_ROUTE
CASE_DEBUT
CASE_FIN
CASE_ACCIDENT
CASE_TRAFIC
CASE_POINT_CHOISI_DEBUT
CASE_POINT_CHOISI_FIN
CASE_CHEMIN_CALCULE

⸻

🎨 Légende de la carte

Élément	Couleur
Terrain	🟢 Vert
Route	⚪ Gris
Point de départ	🔵 Bleu
Destination	🟣 Mauve
Trafic	🟡 Jaune
Accident	🟠 Orange
Départ sélectionné	Turquoise
Destination sélectionnée	Rose
Chemin calculé	Vert pâle

⸻

🎮 Utilisation

1. Lancer l’application

Exécuter la classe :

com.uqo.appmain.AppMain

⸻

2. Choisir le départ

Dans le panneau Options de Navigation, sélectionner un point de départ parmi les choix disponibles.

⸻

3. Choisir la destination

Sélectionner ensuite la destination souhaitée.

⸻

4. Calculer l’itinéraire

Cliquer sur :

Calculer l'itinéraire

Le GPS détermine automatiquement un chemin et l’affiche sur la carte.

⸻

5. Commencer la navigation

Cliquer sur :

Commencer

La voiture apparaît au point de départ.

⸻

6. Déplacer la voiture

Utiliser les touches :

↑  Haut
↓  Bas
←  Gauche
→  Droite

pour déplacer la voiture sur la carte.

⸻

⚠️ Gestion du trafic

Des congestions peuvent apparaître sur la carte.

Lorsque le trafic affecte le trajet, le système peut effectuer un recalcul du chemin afin de déterminer un nouvel itinéraire disponible.

Une collision avec certaines zones de trafic peut également provoquer un accident et affecter la navigation.

⸻

💻 Technologies utilisées

* Java
* Java Swing
* Java AWT
* Programmation orientée objet
* Structures de données Java
* PriorityQueue
* Algorithme de Dijkstra

⸻

📋 Prérequis

Pour exécuter le projet :

* Java JDK installé
* Un IDE Java tel que :
    * Eclipse
    * IntelliJ IDEA
    * Visual Studio Code avec les extensions Java

⸻

▶️ Compilation en ligne de commande

Depuis le dossier du projet :

javac -d bin src/com/uqo/appmain/*.java src/com/uqo/applogique/*.java src/com/uqo/appgui/*.java

Puis lancer l’application avec :

java -cp bin com.uqo.appmain.AppMain

⸻

🎯 Objectif pédagogique

Ce projet permet de mettre en pratique plusieurs concepts de programmation Java :

* programmation orientée objet ;
* interfaces graphiques avec Swing ;
* événements clavier ;
* manipulation d’une grille ;
* structures de données ;
* recherche de chemin ;
* algorithme de Dijkstra ;
* séparation entre interface graphique et logique métier.

⸻

📚 Référence

L’algorithme de recherche du chemin utilisé dans le projet est fortement inspiré de l’algorithme de Dijkstra.

⸻

👨‍💻 Projet universitaire

Projet réalisé dans le cadre d’un devoir de programmation à l’Université du Québec en Outaouais (UQO).<img width="1400" height="798" alt="Capture d’écran, le 2026-09-29 à 00 18 17" src="https://github.com/user-attachments/assets/a081c56d-9e84-4a7f-aecd-fa6d4dd1d492" />
<img width="1400" height="798" alt="Capture d’écran, le 2026-09-29 à 00 18 17" src="https://github.com/user-attachments/assets/293b0ace-c56d-4602-9cad-1c273ef8cf93" />
