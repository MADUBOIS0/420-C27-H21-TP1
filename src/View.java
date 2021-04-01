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
        scrollNotes.setPreferredSize(new Dimension(400,250));

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


        //MAIN PANEL
        JPanel mainPanel = new JPanel(new GridBagLayout());


        
        //Section des paramètres du frame
        frame = new JFrame("Marc-Antoine Dubois - 1909082");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000,600);
        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);

        frame.add(mainPanel);
        frame.setVisible(true);

       /* frame.add(scrollNotes);
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
        frame.add(txfTP2);*/
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


/*//Panel des notes
        JPanel notesPanel = new JPanel(new FlowLayout());
        notesPanel.add(scrollNotes);
        notesPanel.add(btnAdd);
        notesPanel.add(btnModify);
        notesPanel.add(btnDelete);
        mainPanel.add(notesPanel, BorderLayout.WEST);

        //Panel de controle
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(btnAdd);
        controlPanel.add(btnModify);
        controlPanel.add(btnDelete);
        mainPanel.add(controlPanel, BorderLayout.EAST);

        //Panel bas droit
        JPanel bottomRightPanel = new JPanel(());
        GridBagConstraints constraintBottomRightPanel = new GridBagConstraints();
        constraintBottomRightPanel.anchor = GridBagConstraints.LAST_LINE_END;
        bottomRightPanel.add(btnQuit);
        mainPanel.add(bottomRightPanel, constraintBottomRightPanel);*/
