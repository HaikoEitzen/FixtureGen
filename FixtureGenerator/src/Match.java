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


/**
 * Class used by Matchdate and Fixture
 * @author Haiko
 */
public class Match {
    
    private String home;
    private String away;
    
    public Match (String home, String away) {
        this.home = home;
        this.away = away;
    }
    
    public Match (String team) {
        this(team,null);
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }
    
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
