/*
  Objectif:
  Auteur: Marc-Antoine Dubois
  Date: 2021-04-02 Session A2021
 */

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Utils {


    /**
     * Convertir un DefaultTableModel en Array 2D Integer
     * @param model Le DefaultTableModel à convertir
     * @return retourne un tableau 2d integer
     */
    public static int[][] convertT2D(DefaultTableModel model){
        int nbRow = model.getRowCount(); // nombre de lignes dans le model
        int nbCol = model.getColumnCount(); // nombre de colonnes dans le model


        int[][] tab = new int[nbRow][nbCol]; // Le tableau 2D à retourner

        for (int i = 0; i < nbRow; i++){
            for (int j = 0; j < nbCol ; j++){
               if(model.getValueAt(i,j) instanceof String){
                   tab[i][j] = Integer.parseInt((String) model.getValueAt(i,j));
               }
               else if(model.getValueAt(i,j) instanceof Integer) {
                   tab[i][j] =  (Integer)(model.getValueAt(i,j));
               }
            }
        }
        return tab;
    }

    /**
     * Trouver l'entier le plus petit dans une colonne spécifier d'un tableau 2D d'entiers
     * @param tab Tableau 2D d'entiers
     * @param col La colonne a verifier pour le minimum
     * @return Retourne le plus petit entier de la colonne.
     */
    public static int minEval(int[][] tab, int col){
        int min = tab[0][col]; //Le minimum à retourner
        for(int iRow = 0; iRow < tab.length; iRow++){
            if (tab[iRow][col] < min){
                min = tab[iRow][col];
            }
        }
        return min;
    }

    /**
     * Trouver l'entier le plus grand dans une colonne spécifier a partir d'un tableau 2D d'entiers
     * @param tab Un tableau 2D d'entiers
     * @param col La colonne où trouver le maximum
     * @return Retourne le plus grand entier d'une colonne du tableau
     */
    public static int maxEval(int[][] tab, int col){
        int max = 0; //le maximum à retourner
        for(int iRow = 0; iRow < tab[col].length; iRow++){
            if (tab[iRow][col] > max){
                max = tab[iRow][col];
            }
        }
        return max;
    }

    /**
     * Trouver la moyenne d'une colonne dans un tableau 2D d'entiers;
     * @param tab Tableau 2D d'entiers
     * @param col La colonne où trouver la moyenne
     * @return Retourne un double de la moyenne du tableau à la colonne fourni
     */
    public static double moyenneEval(int[][] tab, int col){
        double average=0; //La moyenne à retourner
        DecimalFormat df = new DecimalFormat( "0.00"); // sert à arondir la moyenne
        for(int iRow = 0; iRow < tab.length; iRow++){
            average+=tab[iRow][col];
        }
        average = Double.parseDouble(df.format(average/tab.length));
        return average;
    }


    /**
     * Échanger deux valeurs de positions
     * @param tab tableau de valeurs
     * @param i1 valeur 1
     * @param i2 valeur 2
     */
    public static void permute(int[] tab, int i1, int i2) {
        int temp = tab[i1];
        tab[i1] = tab[i2];
        tab[i2] = temp;
    }

    public static void quickSort(int[][] tab, int[] tabInd,int col, int g, int d){
        if (g < d){
            int index = partition(tab, tabInd, col, g, d);
            quickSort(tab, tabInd, col, g, index-1);
            quickSort(tab, tabInd,col, index+1, d);
        }
    }

    public static int partition(int[][] tab, int[] tabInd, int col,  int g, int d){
        int pivot = tab[tabInd[d]][col] ;
        for (int i = g; i < d; i++){
            if (tab[tabInd[i]][col] < pivot){
                permute(tabInd, i, g);
                g++;
            }
        }
        permute(tabInd, g, d);
        return g;
    }

    public static boolean isPresentDA(int[][] tab, int valueToFind){
        int[] tabIndex = new int[tab.length];

        for (int i = 0; i<tabIndex.length; i++){
            tabIndex[i] = i;
        }

        quickSort(tab, tabIndex,0 ,0, tab.length-1);
        int index = fouilleDichoDA(tab, tabIndex, valueToFind);
        if(index != -1){
            return true;
        }
        else return false;
    }

    public static int fouilleDichoDA(int[][] tab, int[] tabIndex, int valueToFind){
        int debut = 0;
        int millieu = 0;
        int fin = tab.length-1;
        boolean trouve = false;

        while(debut <= fin && !trouve){
            millieu = (debut + fin) / 2;

            if(valueToFind == tab[tabIndex[millieu]][0]){
                trouve = true;
            }
            else if( valueToFind < tab[tabIndex[millieu]][0]){
                fin = millieu - 1;
            }
            else{
                debut = millieu + 1;
            }
        }
        if(trouve){
            return millieu;
        }
        else return -1;
    }





}
