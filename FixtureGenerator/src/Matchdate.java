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


import java.util.ArrayList;

/**
 * Class used by Fixture
 * @author Haiko
 */
public class Matchdate {
    
    protected int number;
    protected ArrayList<Match> matches;
    
    public Matchdate (int number) {
        this.number = number;
        matches = new ArrayList<>();
    }
    
    public void addMatch (Match match) {
        matches.add(match);
    }
    
    public void addFinalMatch (Match match) {
        matches.add(matches.size()-1,match);
    }
    
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
