import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @org.junit.jupiter.api.Test
    /**
     *Test du nombre minimal dans une colonne
     */
    void minEval() {
        int[][] testTable = {
                {11, 22, 55, 44, 33},
                {99, 84, 96, 98, 94}
        };
        int result = Utils.minEval(testTable, 0);
        int expectedResult = 11;
        assertEquals(expectedResult, result);
    }

    @org.junit.jupiter.api.Test
    /**
     * Test du nombre maximal dans une colonne
     */
    void maxEval() {
        int[][] testTable = {
                {11, 22, 55, 44, 33},
                {99, 84, 96, 98, 94}
        };
        int result = Utils.maxEval(testTable, 0);
        int expectedResult = 99;
        assertEquals(expectedResult, result);
    }

    @org.junit.jupiter.api.Test
    /**
     * Test de la moyenne d'une colonne
     */
    void moyenneEval() {
        int[][] testTable = {
                {11, 22, 55, 44, 33},
                {99, 84, 96, 98, 94}
        };
        double result = Utils.moyenneEval(testTable,0);
        double expectedResult = 55;
        assertEquals(expectedResult,result);
    }

    /**
     * Test si DA est dans la colonne 0 d'une table
     */
    @org.junit.jupiter.api.Test
    void isPresentDA() {
        int[][] testTable = {
                {11, 22, 55, 44, 33},
                {99, 84, 96, 98, 94}
        };
        boolean result  = Utils.isPresentDA(testTable, 99);
        assertTrue(result);
    }
}