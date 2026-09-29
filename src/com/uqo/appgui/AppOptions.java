package com.uqo.appgui;

import com.uqo.applogique.AppGps;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**Classe: AppOptions
 * Description:
 * Cette classe definit tout ce qui est afiche sur le paneau du cote droit de l'ecran afin de communiquer avec l'utilisateur.
 **/

public class AppOptions extends JPanel {

    private AppGps gps;
    private List<Point> chemin;
    private AppVoiture voiture;
    
    // Methode principale de la classe
    public AppOptions(Dimension dimension, AppGrille grillePanel) {
        
    	// Initialisation des variables necessaires
    	this.setPreferredSize(dimension);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(240, 240, 240));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gps = new AppGps(grillePanel.getGrille());

        Font titleFont = new Font("Arial", Font.BOLD, 25);
        Font regularFont = new Font("Arial", Font.PLAIN, 14);

        // Le titre
        JLabel titre = new JLabel("Options de Navigation");
        titre.setFont(titleFont);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(titre);
        this.add(Box.createVerticalStrut(15));

        // Instructions pour l'utilisateur
        JTextArea instructions = new JTextArea(
        		"Bienvenue ! Utilisez les flèches haut, bas, gauche, et droite de votre clavier pour bouger. " +
        		"\n\n"+
        		"Le GPS calculera automatiquement le chemin le plus court. " +
   				"\n\n"+
   				"Le chemin le plus court sera affiché en vert pâle."+
       			"\n"+
        		"Les congestions seront marquées en jaune, et les accidents en rouge." +
        		"\n"+
        		"Les cases de départ sont bleues."+
        		"\n"+
        		"La route est grise, et le terrain non-navigable est vert."+
        		"\n"+
  				"Les cases d’arrivée sont mauves"+
        		"\n\n"+
        		"Quand vous arriverez sur votre case d’arrivée, vous avez l’option de continuer votre parcours, ou de terminer la navigation." +
 				"\n\n"+
        		"Attention !"+
        		"\n"+
        		"Durant la navigation, vous avez la possibilité de toucher le trafic avec votre voiture." +
        		"Si vous touchez le trafic, vous causerez un accident, ce qui vous interdira tout mouvement dans la session de navigation actuelle. "
              );
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setFocusable(false);
        instructions.setBackground(new Color(240, 240, 240));
        instructions.setFont(regularFont);
        instructions.setMargin(new Insets(10, 10, 10, 10));
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(instructions);
        this.add(Box.createVerticalStrut(20));

        // Choix du point de depart
        JLabel startLabel = new JLabel("Point de départ:");
        startLabel.setFont(regularFont);
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(startLabel);
        this.add(Box.createVerticalStrut(5));

        String[] startLabels = {"Sélectionner...", "Départ A", "Départ B", "Départ C", "Départ D", "Départ E"};
        int[][] startPoints = {
            {26, 1}, {26, 23}, {26, 45}, {11, 45}, {19, 7}
        };
        JComboBox<String> startBox = new JComboBox<>(startLabels);
        startBox.setMaximumSize(new Dimension(150, 25));
        startBox.setFont(regularFont);
        startBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBox.addActionListener(e -> {
            int selected = startBox.getSelectedIndex() - 1;
            if (selected >= 0) {
                int[] coord = startPoints[selected];
                grillePanel.clearChemin();
                grillePanel.setChoisiPointDepart(coord[0], coord[1]);
                gps.setChoisiPointDepart(new Point(coord[0], coord[1]));
            }
        });
        this.add(startBox);
        this.add(Box.createVerticalStrut(20));

        // Choix du point d'arrivee
        JLabel endLabel = new JLabel("Point d'arrivée:");
        endLabel.setFont(regularFont);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(endLabel);
        this.add(Box.createVerticalStrut(5));

        String[] endLabels = {"Sélectionner...", "Destination A", "Destination B", "Destination C", "Destination D", "Destination E", "Destination F"};
        int[][] endPoints = {
            {10, 4}, {0, 19}, {15, 16}, {13, 31}, {5, 22}, {19, 38}
        };
        JComboBox<String> endBox = new JComboBox<>(endLabels);
        endBox.setMaximumSize(new Dimension(150, 25));
        endBox.setFont(regularFont);
        endBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        endBox.addActionListener(e -> {
            int selected = endBox.getSelectedIndex() - 1;
            if (selected >= 0) {
                int[] coord = endPoints[selected];
                grillePanel.clearChemin();
                grillePanel.setChoisiPointFin(coord[0], coord[1]);
                gps.setChoisiPointFin(new Point(coord[0], coord[1]));
            }
        });
        this.add(endBox);
        this.add(Box.createVerticalStrut(20));

        // Bouton qui permet de calculer l'itineraire
        JButton startBtn = new JButton("Calculer l'itinéraire");
        startBtn.setFont(regularFont);
        startBtn.setMaximumSize(new Dimension(160, 30));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(startBtn);
        startBtn.addActionListener(e -> {
            Point start = grillePanel.getChoisiPointDepart();
            Point end = grillePanel.getChoisiPointFin();

            gps.setChoisiPointDepart(start);
            gps.setChoisiPointFin(end);

            // S'assurer que l'utilisateur a choisi un point d'arrive et de depart
            if (start == null || end == null) {
                JOptionPane.showMessageDialog(this, "Veuillez choisir un point de départ et d'arrivée.");
                return;
            }

            // Calculer le chemin
            grillePanel.clearChemin();
            chemin = gps.calculerCheminPlusCourt(start, end);

            if (chemin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun chemin trouvé.");
            } else {
                grillePanel.setChemin(chemin);
                grillePanel.repaint();
                startLabel.setVisible(false);
                endLabel.setVisible(false);
                startBox.setVisible(false);
                endBox.setVisible(false);
                startBtn.setVisible(false);

                // Montrer le bouton de retour
                JButton goBackBtn = new JButton("Retour");
                goBackBtn.setFont(new Font("Arial", Font.PLAIN, 13));
                goBackBtn.setMaximumSize(new Dimension(160, 30));
                goBackBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                this.add(goBackBtn);
                this.revalidate();
                this.repaint();

                goBackBtn.addActionListener(goBackAction -> {
                    for (Component component : this.getComponents()) {
                        if (component instanceof JButton) {
                            JButton btn = (JButton) component;
                            if (btn.getText().equals("Commencer") || btn.getText().equals("Retour")||btn.getText().equals("Point d'arrivée:")||btn.getText().equals("Point de départ:")) {
                                btn.setVisible(false); 
                            }
                        }
                    }

                    // Resinitialiser l'ecran
                    startBox.setVisible(true);
                    endBox.setVisible(true);
                    startBtn.setVisible(true);

                    // Effacer le chemin calcule
                    grillePanel.clearChemin();
                    grillePanel.repaint();
                });

                // Montrer le bouton qui permet de commencer la navigation
                JButton commencerBtn = new JButton("Commencer");
                commencerBtn.setFont(new Font("Arial", Font.PLAIN, 13));
                commencerBtn.setMaximumSize(new Dimension(160, 30));
                commencerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                this.add(commencerBtn);
                this.revalidate();
                this.repaint();

                commencerBtn.addActionListener(commencerAction -> {
                	// Initialiser la voiture
                    grillePanel.spawnVoiture(start);
                    voiture = new AppVoiture(start.x, start.y, grillePanel, chemin);

                    commencerBtn.setVisible(false);
                    goBackBtn.setVisible(false);

                    // Creer le bouton qui permet de quitter la navigation
                    JButton exitNavBtn = new JButton("Quitter la Navigation");
                    exitNavBtn.setFont(new Font("Arial", Font.PLAIN, 13));
                    exitNavBtn.setMaximumSize(new Dimension(160, 30));
                    exitNavBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    exitNavBtn.addActionListener(exitNavAction -> {
                       
                    	// Tout effacer et/ou reinitialiser
                        grillePanel.clearCar();
                        grillePanel.clearAccidents();
                        grillePanel.resetPointsDepartFin();
                        grillePanel.clearTrafic(); 
                        if (voiture != null) {
                            voiture.resetTraficPresent(); 
                        }

                        startBox.setVisible(true);
                        endBox.setVisible(true);
                        startBtn.setVisible(true);
                        instructions.setVisible(true);

                        exitNavBtn.setVisible(false);
                        goBackBtn.setVisible(false);

                        this.revalidate();
                        this.repaint();
                    });

                    this.add(exitNavBtn);
                    this.revalidate();
                    this.repaint();
                });
            }
        });    
    }
}



