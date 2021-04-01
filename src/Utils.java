import javax.swing.table.DefaultTableModel;

public class Utils {

    public static int[][] convertT2D(DefaultTableModel model){
        int nbRow = model.getRowCount();
        int nbCol = model.getColumnCount();

            int[][] tab = new int[nbRow][nbCol];

        for (int i = 0; i < nbRow; i++){
            for (int j = 0; j < nbCol ; j++){
                tab[i][j] = Integer.parseInt((String) model.getValueAt(i,j));
            }
        }
        return tab;
    }

}
