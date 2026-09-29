package com.uqo.applogique;

import com.uqo.appmain.AppConstantes;

import java.awt.Point;
import java.util.*;

/**Classe: AppGps
 * Description:
 * Cette classe est la logique de calcul du chemin le plus rapide. 
 * Ce code est fortement inspire de l'algorithme de Dijkistra.
 * https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
 **/

public class AppGps {
    private int[][] grille;
    private Point choisiPointDepart;
    private Point choisiPointFin;
    
    // Initialisation de la grille
    public AppGps(int[][] grille) {
        this.grille = grille;
    }
    // Getters pour les points selectionnes par l'utilisateur
    public Point getChoisiPointDepart() {
        return choisiPointDepart;
    }

    public Point getChoisiPointFin() {
        return choisiPointFin;
    }

    // Setter pour les points selectionnes par l'utilisateur
    public void setChoisiPointDepart(Point depart) {
        this.choisiPointDepart = depart;
    }

    public void setChoisiPointFin(Point fin) {
        this.choisiPointFin = fin;
    }


    // Methode pour calculer le chemin le plus court entre les points selectionnes (fortement base sur l'algorithme de Dijkistra)
    public List<Point> calculerCheminPlusCourt(Point depart, Point fin) {
        int rows = grille.length;
        int cols = grille[0].length;

        // Les directions possibles
        int[] dy = { -1, 1, 0, 0 }; 
        int[] dx = { 0, 0, -1, 1 };

        // S'assurer que l'utilisateur peut se deplacer sur ses points selectionnes
        if (!estNavigable(depart.y, depart.x) || !estNavigable(fin.y, fin.x)) {
            return new ArrayList<>(); 
        }

        // Carte de distance initialisee
        int[][] distance = new int[rows][cols];
        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        distance[depart.y][depart.x] = 0; 

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        pq.add(new Node(depart.y, depart.x, 0));

        Point[][] prev = new Point[rows][cols];

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // Verification d'arrivee
            if (current.y == fin.y && current.x == fin.x) {
                return reconstruireChemin(prev, depart, new Point(current.x, current.y));
            }

            // Exploration des cases entourantes
            for (int i = 0; i < 4; i++) {
                int ny = current.y + dy[i];
                int nx = current.x + dx[i];

                if (ny >= 0 && nx >= 0 && ny < rows && nx < cols && estNavigable(ny, nx)) {
                    int caseCout = getCout(ny, nx); 
                    
                    // Ignorer les cellules non navigables
                    if (caseCout == Integer.MAX_VALUE) continue; 
                    int newDist = current.distance + caseCout;

                    // Consideration des cellules qui offrent le chemin le plus court seulement
                    if (newDist < distance[ny][nx]) {
                        distance[ny][nx] = newDist;
                        pq.add(new Node(ny, nx, newDist));
                        prev[ny][nx] = new Point(current.x, current.y); 
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    // Reconstruction du chemin (permettant la creation visuelle du chemin)
    private List<Point> reconstruireChemin(Point[][] prev, Point depart, Point fin) {
        List<Point> chemin = new ArrayList<>();
        Point current = fin;

        while (current != null && !current.equals(depart)) {
            chemin.add(current);
            current = prev[current.y][current.x]; 
        }

        chemin.add(depart); 
        Collections.reverse(chemin); 
        return chemin;
    }
    private boolean recalculTriggered = false;

    public boolean estRecalculTriggered() {
        return recalculTriggered;
    }

    // Recalcul du chemin en cas de trafic
    public List<Point> recalculerCheminTrafic(boolean traficPresent, List<Point> traficBlocks){
    	recalculTriggered = false;
    	// Cas ou il n;y a pas de traffic
    	if (!traficPresent) {
            return new ArrayList<>();
        }

    	// Prevention d'erreur null
        if (grille == null || traficBlocks == null) {
            return new ArrayList<>();
        }
        
        // Decision s'il y a un recalcul du chemin
        List<Point> newChemin = calculerCheminPlusCourt(choisiPointDepart, choisiPointFin);
        if (!newChemin.isEmpty()) {
            recalculTriggered = true;
            return newChemin;
        } 
        return new ArrayList<>();
    }

    // Methode pout verifier si une cellule permet la navigation
    public boolean estNavigable(int y, int x) {
        return grille[y][x] != AppConstantes.CASE_TRAFIC;
    }

    // Methode pour obtenir le cout de navigation d'une cellule
    private int getCout(int y, int x) {
        
    	// Blocs pour s'assurer que le chemin n'inclut pas de trafic ou de terrain
    	if (grille[y][x] == AppConstantes.CASE_TRAFIC) {
            return Integer.MAX_VALUE; 
        }
        else if (grille[y][x] == AppConstantes.CASE_TERRAIN) {
            return Integer.MAX_VALUE; 
        }
        return 1; 
    }

    private static class Node {
        int y, x, distance;

        public Node(int y, int x, int distance) {
            this.y = y;
            this.x = x;
            this.distance = distance;
        }
    }
}


