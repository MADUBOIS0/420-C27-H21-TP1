import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class View extends JFrame{
    JFrame frame;
    JTable tableNotes;
    JTable tableStats;
    DefaultTableModel model;
    JButton btnAdd;
    JButton btnModify;
    JButton btnDelete;
    JButton btnQuit;
    JTextField txfDA;
    JTextField txfExam1;
    JTextField txfExam2;
    JTextField txfTP1;
    JTextField txfTP2;
    JLabel lblDA;
    JLabel lblExam1;
    JLabel lblExam2;
    JLabel lblTP1;
    JLabel lblTP2;


    String[] colNames = {"DA", "Examen 1", "Examen 2", "TP1", "TP2", "Total %"};
    String[][] data = returnNotesData();

    public View() throws IOException {

        //Section de la table des notes
        model = new DefaultTableModel(data,colNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableNotes = new JTable(model);
        tableNotes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableNotes.setRowSelectionInterval(0,0);
        JScrollPane scrollNotes = new JScrollPane(tableNotes);
        scrollNotes.setPreferredSize(new Dimension(600,400));

        //Section des Labels
        lblDA = new JLabel("DA");
        lblExam1 = new JLabel("Examen 1");
        lblExam2 = new JLabel("Examen 2");
        lblTP1 = new JLabel("TP 1");
        lblTP2 = new JLabel("TP 2");

        //Section des textfields
        txfDA = new JTextField();
        txfDA.setPreferredSize(new Dimension(80,25));
        txfExam1 = new JTextField();
        txfExam1.setPreferredSize(new Dimension(80,25));
        txfExam2 = new JTextField();
        txfExam2.setPreferredSize(new Dimension(80,25));
        txfTP1 = new JTextField();
        txfTP1.setPreferredSize(new Dimension(80,25));
        txfTP2 = new JTextField();
        txfTP2.setPreferredSize(new Dimension(80,25));

        //BTNLIST
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
                    saveDataToNotes(Utils.convertT2D(model));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                frame.dispose();
            }
            else if(result == JOptionPane.NO_OPTION){
                frame.dispose();
            }
        });


        //Panel gauche
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BorderLayout());
        notesPanel.add(scrollNotes, BorderLayout.NORTH);
        //notesPanel.add(blabla, BorderLayout.SOUTH);


        //Panel

        //Panel controle
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();

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
        controlPanel.add(btnAdd,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 3;
        controlPanel.add(btnModify,mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.gridx = 2;
        mainConstraints.gridy = 3;
        controlPanel.add(btnDelete,mainConstraints);



        //Panel pour le boutton quitter (sert seulement a resize le boutton en réalité)
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BorderLayout());
        bottomRightPanel.setPreferredSize(new Dimension(25,35));
        bottomRightPanel.add(btnQuit, BorderLayout.EAST);



        //Section des paramètres du frame
        frame = new JFrame("Marc-Antoine Dubois - 1909082");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1000,600));
        frame.setLocationRelativeTo(null);

        frame.add(controlPanel, BorderLayout.CENTER);
        frame.add(notesPanel, BorderLayout.WEST);
        frame.add(bottomRightPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);

        frame.add(scrollNotes);
        frame.add(btnQuit);

        frame.add(lblDA);
        frame.add(txfDA);

        frame.add(lblExam1);
        frame.add(txfExam1);

        frame.add(lblExam2);
        frame.add(txfExam2);

        frame.add(lblTP1);
        frame.add(txfTP1);

        frame.add(lblTP2);
        frame.add(txfTP2);
    }

    public static void main(String[] args) throws IOException {
        View myView = new View();
    }


    public static String[][] returnNotesData() throws IOException {

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




