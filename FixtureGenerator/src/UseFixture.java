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

import Fixture.Fixture;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class that uses the Fixture class.
 * @author Haiko
 */
public class UseFixture {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        String[] conmebol = {"Paraguay", "Brasil", "Argentina", "Perú", "Chile", "Ecuador", "Colombia"};

        Fixture liga = new Fixture(conmebol);

        liga.display();

        String file = "D:\\conmebol.txt";

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            out.println(liga);
        } catch (Exception e)
        {
            // do nothing
        }

    }
}
