package com.millersvillecs.moleculeandroid.data;

import java.util.Scanner;

/*
 * Reference: http://www.epa.gov/ncct/dsstox/MoreonSDF.html
 *
 * Note: Commented code is a version using StringBuilders instead of Scanners but quick tests did not show a noticeable
 *       difference between the two.
 */
public class SDFParser {
    private static int HEADER_INDEX = 5;

    public static Molecule parse(String[] content) {
        int index = HEADER_INDEX;
        Molecule molecule = new Molecule();

        String line = content[index];
        while(line.startsWith("   ")) {
            molecule.addAtom(parseAtomLine(line));
            index++;
            line = content[index];
        }

        while(index < content.length) {
            line = content[index];
            molecule.addBond(parseBondLine(line));
            index++;
        }

        return molecule;
    }

    private static Atom parseAtomLine(String line) {
        Atom atom = new Atom();
        Scanner scanner  = new Scanner(line);

        atom.x = scanner.nextDouble();
        atom.y = scanner.nextDouble();
        atom.z = scanner.nextDouble();
        atom.setType(scanner.nextLine().substring(1, 4));
        
        scanner.close();

        return atom;
    }

    private static Bond parseBondLine(String line) {
        Bond bond = new Bond();
        Scanner scanner = new Scanner(line);

        bond.from = scanner.nextInt() - 1;
        bond.to = scanner.nextInt() - 1;
        bond.type = scanner.nextInt();
        
        scanner.close();

        return bond;
    }

    /*
    private static int nextInt(StringBuilder s) {
        StringBuilder value = new StringBuilder();
        boolean found = false;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' ') {
                if(found) {
                    s.delete(0, i);
                    break;
                }
            } else {
                value.append(s.charAt(i));
                found = true;
            }
        }

        return Integer.parseInt(value.toString());
    }

    private static double nextDouble(StringBuilder s) {
        StringBuilder value = new StringBuilder();
        boolean found = false;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' ') {
                if(found) {
                    s.delete(0, i);
                    break;
                }
            } else {
                value.append(s.charAt(i));
                found = true;
            }
        }

        return Double.parseDouble(value.toString());
    }

    private static char nextChar(StringBuilder s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ') {
                char c = s.charAt(i);
                s.delete(0, i);
                return c;
            }
        }

        return ' ';
    }

    */
}
