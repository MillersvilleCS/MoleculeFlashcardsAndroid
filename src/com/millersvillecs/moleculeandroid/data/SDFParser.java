package com.millersvillecs.moleculeandroid.data;

import java.util.Scanner;

public class SDFParser {
    private static int HEADER_INDEX = 5;
    
    /**
     * Parse an SDF molecule file. The content coming in has been truncated
     * at the line "M  END". Everything beyond this is garbage.
     * 
     * The top 4 lines of the file are also garbage, and reading the file in
     * produces 1 extra line, hence why index starts at 5.
     * 
     * @param content - the file read in, line by line
     * @return a Molecule object, made up of Atom and Bond objects for easy geometry creation
     */
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
    
    /**
     * Atoms are defined as 3 decimal numbers denoting their position and a type
     * such as 'H' for Hydrogen
     * 
     * @param line - the line of text to parse
     * @return an atom object
     */
    private static Atom parseAtomLine(String line) {
        
        Scanner scanner  = new Scanner(line);

        float x = (float) scanner.nextDouble();
        float y = (float) scanner.nextDouble();
        float z = (float) scanner.nextDouble();
        String type = scanner.nextLine().substring(1, 4);
        
        scanner.close();

        return new Atom(x, y, z, type);
    }
    
    /**
     * Bonds are denoted as indexes representing connections between atoms in the order
     * they are in the file. These connections are 1-based, so we change them to 0-based
     * so they align with our array
     * 
     * @param line - the line of text to parse
     * @return a bond object
     */
    private static Bond parseBondLine(String line) {;
        Scanner scanner = new Scanner(line);

        int from = scanner.nextInt() - 1;
        int to = scanner.nextInt() - 1;
        int type = scanner.nextInt();
        
        scanner.close();

        return new Bond(from, to, type);
    }
}
