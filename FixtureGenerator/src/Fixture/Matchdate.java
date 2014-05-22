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
import java.util.ArrayList;

/**
 * Class used by Fixture to represent a list of matches
 * @author Haiko
 */
public class Matchdate {
    
    /**
     * Sequential order of the matchdate
     */
    protected int number;
    /**
     * List of matches
     */
    protected ArrayList<Match> matches;
    
    /**
     * Constructor
     * @param number 
     */
    public Matchdate (int number) {
        this.number = number;
        matches = new ArrayList<>();
    }
    
    /**
     * Adds a Match to the Matchdate
     * @param match 
     */
    public void addMatch (Match match) {
        matches.add(match);
    }
    
    /**
     * Adds the last Match to a Matchdate
     * @param match 
     */
    public void addFinalMatch (Match match) {
        matches.add(matches.size()-1,match);
    }
    
    /**
     * Sets the sequential order of the Matchdate.
     * It is the user's responsibility to make sure that no number is
     * repeated. The behavior in any other case is unpredictable.
     * YOU HAVE BEEN WARNED. (You're of course allowed to improve that)
     * @param number 
     */
    public void setNumber (int number) {
        this.number = number;
    }
    
    /**
     * Convert the known data to a string.
     * Example:
     * Matchdate 3
     * England  vs.  Germany
     * Italy  vs.  France
     * Free: Spain
     * @return 
     */
    @Override
    public String toString () {
        StringBuilder x = new StringBuilder();
        x.append("Matchdate ");
        x.append(number);
        x.append(System.getProperty("line.separator"));
        for (Match m: matches) {
            if(m.getAway() != null) {
                x.append(m);
                x.append(System.getProperty("line.separator"));
            }
        }
        /*
         * Adds the free team if any
         */
        for (Match m: matches) {
            if(m.getAway() == null) {
                x.append(m);
                x.append(System.getProperty("line.separator"));
            }
        }
        
        return x.toString();
    }
}
