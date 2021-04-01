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
        //Section des paramètres du frame
        frame = new JFrame("Marc-Antoine Dubois - 1909082");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

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
        scrollNotes.setPreferredSize(new Dimension(400,100));

        btnQuit = new JButton("Quitter");
        /*btnQuit.addActionListener(e -> {
            try {
                btnQuitGo();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });*/

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        frame.add(scrollNotes);
        frame.add(btnQuit);
        frame.setVisible(true);
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

