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

package Fixture;
import java.util.Random;

/**
 * An instance of this class is used to generate a pseudorandom yet
 * non-conflicting order of matchdates (array of matches).
 * The basic idea is: the user instantiates a Fixture passing it an array of
 * strings that represent the names of teams. During instantiation, the Fixture
 * object randomizes the order of the teams and generates a fixture using
 * a matrix with a pattern to avoid conflicts (such as one team playing two
 * matches on the same matchdate or not enough possible match-ups left to fill
 * a matchdate).
 * The resulting Fixture can be seen using the display() or toString() methods.
 * It is worth noting that the fixture-generating algorithm (implemented in the
 * method generate() ) is a random algorithm and the running it twice with the
 * same input DOES NOT guarantee the same output.
 * 
 * Logic:
 * - a Fixture is a Matchdate array
 * - a Matchdate is a Match array
 * - a Match is a pair of Teams
 * 
 * EDIT: (21'05'2014)
 * Goals:
 * 1 - make the algorithm not random (i.e. make randomization an option)
 * 2 - give the user more power over the creation and generation of the Fixture,
 *      a - make separate methods to randomize the order of teams and matchdates
 *      b - making the generation and randomization methods public
 *      c - allow the user to designate the name of the FREE string (i.e. if 
 *          there's an odd number of teams, on each matchdate one team will be
 *          FREE, and this must somehow be contemplated in each Matchdate) by
 *          passing it as a parameter to the constructor
 *
 * @author Haiko
 */
public class Fixture {
    
    /**
     * Matrix used to generate the pattern (and order) of matches.
     */
    private Integer [][] matrix;
    /**
     * List of teams.
     */
    private String [] teams;
    /**
     * The string that will be used to denote a free team on a matchdate. 
     * (e.g. "Free", "Libre", "FREI", "Doesn't play", etc.)
     */
    private String freeDefault;
    /**
     * True if the tournament is two rounds, False if just one.
     */
    private boolean round;
    /**
     * Number of teams.
     */
    private final Integer n;
    /**
     * True if odd number of teams, False otherwise.
     */
    private final Boolean odd;
    /**
     * Corresponding array of Matchdates.
     */
    private Matchdate [] dates;
    /**
     * Order of the matchdates
     */
    private Integer [] matchdatesOrder;
    
    /**
     * Full constructor
     * @param teams an array of strings with team names
     * @param freeDefault 
     * @param round two rounds or just one
     * @param autoRandomTeams true if the team order should be randomized
     * @param autoRandomDates true if the matchdate order should be randomized
     * @param autoGenerate true if the generation of the fixture should be 
     * automatic
     */
    public Fixture (String [] teams, String freeDefault, 
            boolean round, 
            boolean autoRandomTeams, 
            boolean autoRandomDates, 
            boolean autoGenerate)
    {
        n = teams.length;
        this.round = round;
        
        if (n % 2 == 0) 
        {
            this.teams = new String [n]; 
            odd = false;
        }
        else 
        { 
            this.teams = new String [n+1]; 
            odd = true; 
        }
        /* if there's an odd number of teams, the matrix will still be even, the
        extra "team" is free */
        System.arraycopy(teams, 0, this.teams, 0, n);
        
        /* determining the amount of matchdates */
        int aux;
        if(odd && round) aux = 2*n;
        else if(odd && !round) aux = n;
        else if(!odd && round) aux = 2*(n-1);
        else aux = n-1;
        
        matchdatesOrder = new Integer[aux];
        
        /* default order of matchdates */
        for(int i = 0; i < matchdatesOrder.length; i++)
        {
            matchdatesOrder[i] = i+1;
        }
        
        if(autoRandomTeams) randomizeTeamsOrder();
        if(autoRandomDates) randomizeMatchdatesOrder();
        if(autoGenerate)    generate();

        dates = new Matchdate[round ? (matrix.length-1)*2 : (matrix.length-1)];
        generateMatchdates();
    }
    
    /**
     * Constructor without freeDefault, otherwise same as full constructor.
     * The default free string is "Free"
     * @param teams
     * @param round
     * @param autoRandomTeams
     * @param autoRandomDates
     * @param autoGenerate
     */
    public Fixture (String [] teams, 
            boolean round, 
            boolean autoRandomTeams,
            boolean autoRandomDates, 
            boolean autoGenerate)
    {
        this(teams,"Free",round,autoRandomTeams,autoRandomDates,autoGenerate);
    }
    
    /**
     * Constructor with only one boolean parameter: round
     * All other are true as default.
     * @param teams
     * @param freeDefault
     * @param round
     */
    public Fixture (String [] teams, String freeDefault, boolean round)
    {
        this(teams,freeDefault,round,true,true,true);
    }
    
    /**
     * Constructor with only array of teams and boolean round.
     * freeDefault is "Free" and all other boolean are true
     * @param teams
     * @param round 
     */
    public Fixture (String [] teams, boolean round) {
        this(teams,"Free",round);
    }
    
    /**
     * Simple constructor.
     * freeDefault is "Free" and all boolean parameters are true
     * @param teams 
     */
    public Fixture (String [] teams) {
        this(teams,true);
    }
    
    /**
     * Both randomization methods are called
     */
    public void randomize () {
        randomizeTeamsOrder();
        randomizeMatchdatesOrder();
    }
    
    /**
     * The list of teams is randomized.
     */
    public void randomizeTeamsOrder() {
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
     * The list of numbers for matchdates is randomized.
     */
    public void randomizeMatchdatesOrder() {
        int x; int aux;
        Random random = new Random();
        for (int i = 0; i < matchdatesOrder.length; i++) {
            x = random.nextInt(matchdatesOrder.length);
            aux = matchdatesOrder[i];
            matchdatesOrder[i] = matchdatesOrder[x];
            matchdatesOrder[x] = aux;
        }
    }

    /**
     * The matrix and its pattern are generated.
     */
    public void generate() {
        if (odd) this.teams[n] = this.freeDefault;
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
    private void generateMatchdates() {
        
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
