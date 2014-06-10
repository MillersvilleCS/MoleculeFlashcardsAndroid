package com.millersvillecs.moleculeandroid.data;

import java.util.Scanner;

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
        
        Scanner scanner  = new Scanner(line);

        float x = (float) scanner.nextDouble();
        float y = (float) scanner.nextDouble();
        float z = (float) scanner.nextDouble();
        String type = scanner.nextLine().substring(1, 4);
        
        scanner.close();

        return new Atom(x, y, z, type);
    }
    
    private static Bond parseBondLine(String line) {;
        Scanner scanner = new Scanner(line);

        int from = scanner.nextInt() - 1;
        int to = scanner.nextInt() - 1;
        int type = scanner.nextInt();
        
        scanner.close();

        return new Bond(from, to, type);
    }
}
