import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class View extends JFrame{
    JFrame frame;
    JTable tableNotes;
    JTable tableStats;

    String[] colNames = {"DA", "Examen 1", "Examen 2", "TP1", "TP2", "Total %"};
    String[][] data = returnNotesData();

    public View() throws IOException {
        frame = new JFrame("Marc-Antoine Dubois - TP1"); // NE PAS OUBLIER DE LE CHANGER
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        tableNotes = new JTable(data, colNames);
        JScrollPane scrollNotes = new JScrollPane(tableNotes);
        scrollNotes.setPreferredSize(new Dimension(400,100));

        frame.add(scrollNotes);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        View myView = new View();
    }

    public static String[][] returnNotesData() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("texte/notes.txt"));
        String[] tab;
        String line;
        int lineNumber = (int)reader.lines().count();
        String[][] data = new String[lineNumber][6];
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
}

