/**
 *  Haiko's Fixture Generator
 *  Copyright (C) 2014  Haiko René Eitzen Bartel
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

import java.util.Random;

/**
 * Object used to represent a fixture
 * @author Haiko
 */
public class Fixture {
    
    private Integer [][] matrix; // will represent the order of matches
    private String [] teams; // a list of the teams
    private Boolean round; // if tournament is round robin or just one-way
    private final Integer n; // number of teams
    private final Boolean odd; // true if odd number of teams
    private Matchdate [] dates;
    
    /**
     * Complete constructor.
     * @param teams
     * @param round 
     */
    public Fixture (String [] teams, boolean round) {
        n = teams.length;
        this.round = round;
        if (n % 2 == 0) { this.teams = new String [n]; odd = false; }
        else { this.teams = new String [n+1]; odd = true; }
        // if there's an odd number of teams, the matrix will still be even, the extra "team" is LIBRE
        System.arraycopy(teams, 0, this.teams, 0, n);
        randomize();
        generate();
        dates = new Matchdate[round ? (matrix.length-1)*2 : (matrix.length-1)];
        runMatchdates();
    }
    
    /**
     * Simple constructor.
     * @param teams 
     */
    public Fixture (String [] teams) {
        this(teams,true);
    }
    
    /**
     * The list of teams is randomized. You could call
     * this the sorting process.
     */
    private void randomize () {
        int x; String aux;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            x = random.nextInt(n);
            aux = teams[i];
            teams[i] = teams[x];
            teams[x] = aux;
        }
    }

    /**
     * The matrix and its pattern are generated.
     */
    private void generate() {
        if (odd) this.teams[n] = "LIBRE";
        if (odd)
            matrix = new Integer [n+1][n+1]; // the matrix is generated according to the amount of teams
        else
            matrix = new Integer [n][n];
        int m = matrix.length; int aux;
        for (int i = 0; i < m; i++) {
            matrix[i][i] = 0; // the main diagonal is set to ZERO
        }
        for (int f = 1; f < m; f++) {
            matrix[f][0] = f*2; // all even numbers in the first column
        }
        /* Filling in the lower left half of the matrix */
        for (int c = 1; c < m-1; c++) {
            for (int f = c+1; f < m; f++) {
                if (c == 1) {
                    matrix[f][c] = f + 1; // all ordered numbers in second column
                }
                else
                {
                    matrix[f][c] = matrix[f-1][c-1] + 2;
                    // equal to the inmediately superior left value plus 2
                }
            }
        }
        /* Filling the upper right half of the matrix. */
        for (int f = 0; f < m -1; f++) {
            for (int c = f+1; c < m; c++) {
                matrix[f][c] = matrix[c][f] > (m-1) ? matrix[c][f] - (m-1) : matrix[c][f] + (m-1); 
            }
        }
        
        /* Switch half of the home/away matches (all evens) */
        int temp;
        for (int c = 1; c < m-1; c++) {
            for (int f = c+1; f < m; f++) {
                if (matrix[f][c] % 2 == 0) {
                    temp = matrix[f][c];
                    matrix[f][c] = matrix[c][f];
                    matrix[c][f] = temp;
                }
            }
        }
    }
    
    /**
     * Method that generates the array of matchdates.
     */
    private void runMatchdates() {
        
        for (int i = 0; i < dates.length; i++) {
            dates[i] = new Matchdate(i+1);
            for (int f = 0; f < matrix.length; f++) {
                for (int c = 0; c < matrix.length; c++) {
                    if (matrix[f][c] == i+1) {
                        if (teams[f].compareTo("LIBRE") == 0)
                            dates[i].addMatch(new Match(teams[c]));
                        else if (teams[c].compareTo("LIBRE") == 0)
                            dates[i].addMatch(new Match(teams[f]));
                        else
                            dates[i].addMatch(new Match(teams[f],teams[c]));
                        
                    }
                }
            }
        }
        
    }
    
    /**
     * Set round robin to true or false.
     * @param x 
     */
    public void setRound(boolean x) {
        round = x;
    }
    
    /**
     * Show the matrix, just in case.
     */
    public void printMatrix() {
        for (Integer[] matrix1 : matrix) {
            for (int c = 0; c < matrix.length; c++) {
                System.out.print(matrix1[c] + "  ");
            }
            System.out.println();
        }
    }
    
    /**
     * Creates a string from the entire fixture.
     * @return 
     */
    @Override
    public String toString() {
        
        StringBuilder x = new StringBuilder();
        
        for (Matchdate date : dates) {
            x.append(date);
            x.append(System.getProperty("line.separator"));
        }
        
        return x.toString();
    }
    
    /**
     * Displays the Fixture.
     */
    public void display () {
        System.out.println(this);
    }
    
}
