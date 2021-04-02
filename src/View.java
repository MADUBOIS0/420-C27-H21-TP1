/*
  Objectif:
  Auteur: Marc-Antoine Dubois
  Date: 2021-04-02 Session A2021
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class View extends JFrame{
    //region Déclaration des variables
    JFrame frame;
    JPanel notesPanel; //Le panneau de notes contient le JTable de statistiques et le scrollPane de notes
    JPanel controlPanel; //Le panneau de controle va contenir tout les textFields et JButton qui affecte le tableau de notes
    JPanel centerPanel; //Le center panel va contenir le controlPanel, ça me permet de mettre le controlPanel au Nord de celui ci
    JPanel bottomRightPanel; // Panneau à l'extreme droite, sert seulement a mettre le boutton quitter
    JTable tableNotes; //Tableau des notes des élèves, il est garni avec la fonction getNotesData
    JTable tableStats; //Tableau de statistique diverses par rapport au données dans le tableau de notes
    DefaultTableModel modelNotes; //Tablemodel pour tableNotes
    JButton btnAdd; //JButton pour ajouter des élèves à tableNotes
    JButton btnModify; //JButton qui modifie les entrées qui existe déja dans tableNotes
    JButton btnDelete; //Supprimer des entrées de tableNotes
    JButton btnQuit; //Quitter le programme et demander à l'utilisateur s'il veut sauvegarder
    JTextField txfDA; //JTextField du numéro d'admission qui sera ajouter/modifier/supprimer dans tableNotes
    JTextField txfExam1; //JTextField de la note d'exam 1 qui sera ajouter/modifier/supprimer dans tableNotes
    JTextField txfExam2; //JTextField de la note d'exam 2 qui sera ajouter/modifier/supprimer dans tableNotes
    JTextField txfTP1;//JTextField de la note du TP1 qui sera ajouter/modifier/supprimer dans tableNotes
    JTextField txfTP2;//JTextField de la note du TP2 qui sera ajouter/modifier/supprimer dans tableNotes
    JLabel lblDA; //Sert à indiquer que l'utilisateur modifie txfDA
    JLabel lblExam1; //Sert à indiquer que l'utilisateur modifie txfExam1
    JLabel lblExam2; //Sert à indiquer que l'utilisateur modifie txfExam2
    JLabel lblTP1; //Sert à indiquer que l'utilisateur modifie txfTP1
    JLabel lblTP2; //Sert à indiquer que l'utilisateur modifie txfTP2

    // Le keylistener sert a verifier que l'utilisateur entre seulement des entiers, il évite également la répetition
    KeyListener txfKeyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Verifie si valeur entré n'est pas un chiffre, que la touche n'est pas backspace et si la classe n'est pas un JTextfield
            if (!(Character.isDigit(e.getKeyChar())) && e.getKeyChar() != 8 && e.getSource().getClass() == JTextField.class){
                ((JTextField) e.getSource()).setText("");
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un nombre entre 0-9");
            }
        }
    };


    String[] colNamesNotes = {"DA", "Examen 1", "Examen 2", "TP1", "TP2", "Total %"}; //Liste des noms pour colonne de tableNotes
    String[][] dataNotes = getNotesData(); // Tableau 2d des DA et notes, reçoit ses données d'un fichier .txt
    //endregion

    public View() throws IOException {

        //region Section des JTables
        modelNotes = new DefaultTableModel(dataNotes,colNamesNotes){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableNotes = new JTable(modelNotes);
        tableNotes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableNotes.setRowSelectionInterval(0,0);
        JScrollPane scrollNotes = new JScrollPane(tableNotes);
        scrollNotes.setPreferredSize(new Dimension(600,400));
        //endregion

        //region Section des Labels
        lblDA = new JLabel("DA");
        lblExam1 = new JLabel("Examen 1");
        lblExam2 = new JLabel("Examen 2");
        lblTP1 = new JLabel("TP 1");
        lblTP2 = new JLabel("TP 2");
        //endregion

        //region Section des textfields
        txfDA = new JTextField();
        txfDA.addKeyListener(txfKeyListener);
        txfExam1 = new JTextField();
        txfExam1.addKeyListener(txfKeyListener);
        txfExam2 = new JTextField();
        txfExam2.addKeyListener(txfKeyListener);
        txfTP1 = new JTextField();
        txfTP1.addKeyListener(txfKeyListener);
        txfTP2 = new JTextField();
        txfTP2.addKeyListener(txfKeyListener);
        //endregion

        //region Déclaration des boutons
        btnAdd = new JButton("Ajouter");
        btnModify = new JButton("Modifier");
        btnDelete = new JButton("Supprimer");

        btnQuit = new JButton("Quitter");
        btnQuit.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous enregistrer avant de quitter?", "Confirmation enregistrement",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                try {
                    saveDataToNotes(Utils.convertT2D(modelNotes));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                frame.dispose();
            }
            else if(result == JOptionPane.NO_OPTION){
                frame.dispose();
            }
        });

        //endregion

        //region Panel gauche, panel qui contient la table de notes et de statistiques
        notesPanel = new JPanel();
        notesPanel.setLayout(new BorderLayout());
        notesPanel.add(scrollNotes, BorderLayout.NORTH);
        //notesPanel.add(blabla, BorderLayout.SOUTH);
        //endregion

        //region Panel controle, tout les controles pour modifier les notes
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.insets = new Insets(5,10,5,10);
        mainConstraints.ipadx = 25;

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;
        controlPanel.add(lblDA, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 0;
        controlPanel.add(txfDA,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 1;
        controlPanel.add(lblExam1,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 1;
        controlPanel.add(txfExam1,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 2;
        controlPanel.add(lblExam2,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 2;
        controlPanel.add(txfExam2,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 3;
        controlPanel.add(lblTP1,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 3;
        controlPanel.add(txfTP1,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 4;
        controlPanel.add(lblTP2,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 4;
        controlPanel.add(txfTP2,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 5;
        controlPanel.add(btnAdd,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 5;
        controlPanel.add(btnModify,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 2;
        mainConstraints.gridy = 5;
        controlPanel.add(btnDelete,mainConstraints);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(controlPanel, BorderLayout.NORTH);
        //endregion

        //region Panel pour le boutton quitter
        bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BorderLayout());
        bottomRightPanel.setPreferredSize(new Dimension(25,35));
        bottomRightPanel.add(btnQuit, BorderLayout.EAST);
        //endregion

        //region Section des paramètres du frame
        frame = new JFrame("Marc-Antoine Dubois - 1909082");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1000,600));
        frame.setLocationRelativeTo(null);

        frame.add(notesPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomRightPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
        //endregion
    }

    public static void main(String[] args) throws IOException {
        View myView = new View();
    }

    public static String[][] getNotesData() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("texte/notes.txt"));
        String[] tab;
        String line;
        int totalLines = (int)reader.lines().count(); // Le nombre total de lignes dans notes.txt
        String[][] data = new String[totalLines][6];
        reader = new BufferedReader(new FileReader("texte/notes.txt"));
        int linePos = 0;

        while((line = reader.readLine()) != null){
            tab = line.split(" ");
            data[linePos][0] = tab[0];
            data[linePos][1] = tab[1];
            data[linePos][2] = tab[2];
            data[linePos][3] = tab[3];
            data[linePos][4] = tab[4];

            //Sert a calculer le total des notes
            int total = 0; // total, sera afficher dans column Total %
            for (int i=1; i<=4; i++){
                total+=Integer.parseInt(tab[i]);
            }
            data[linePos][5] = String.valueOf(total/4);
            linePos++;
        }
        reader.close();
        return data;
    }

    public static void saveDataToNotes(int[][] tab) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("texte/notes.txt", false));
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                writer.write(tab[i][j] + " ");
            }
            writer.newLine();
        }
        writer.close();
    }
}




