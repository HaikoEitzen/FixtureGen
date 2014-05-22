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

/**
 * Class used by Matchdate and Fixture
 * @author Haiko
 */
public class Match {
    
    /**
     * Home team
     */
    private String home;
    /**
     * Away team, possibly null if Home team is Free
     */
    private String away;
    
    /**
     * Constructor
     * @param home team
     * @param away team, may be null
     */
    public Match (String home, String away) {
        this.home = home;
        this.away = away;
    }
    
    /**
     * Constructor if team is free
     * @param team free team
     */
    public Match (String team) {
        this(team,null);
    }

    /**
     * Get Home team from a Match
     * @return Home team
     */
    public String getHome() {
        return home;
    }

    /**
     * Sets Home team to home
     * @param home team
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Get Away team from a Match
     * @return Away team 
     */
    public String getAway() {
        return away;
    }

    /**
     * Sets Away team to away
     * @param away 
     */
    public void setAway(String away) {
        this.away = away;
    }
    
    /**
     * Makes a string out of the match
     * @return match as string
     */
    @Override
    public String toString () {
        StringBuilder x = new StringBuilder();
        if (away != null) {
            x.append(home);
            x.append("  vs.  ");
            x.append(away);
        } else {
            x.append("Free: ");
            x.append(home);
        }
        return x.toString();
    }
    
}
