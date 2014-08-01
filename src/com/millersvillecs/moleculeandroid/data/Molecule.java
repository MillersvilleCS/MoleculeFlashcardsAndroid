package com.millersvillecs.moleculeandroid.data;

import java.util.ArrayList;

/**
 * 
 * @author connor
 * 
 * A collection of atoms and bonds - a convenience container for the
 * geometry constructor.
 * 
 */
public class Molecule {

    private ArrayList<Atom> atoms;
    private ArrayList<Bond> bonds;

    public Molecule() {
        this.atoms = new ArrayList<Atom>();
        this.bonds = new ArrayList<Bond>();
    }

    public ArrayList<Atom> getAtoms() {
        return this.atoms;
    }

    public ArrayList<Bond> getBonds() {
        return this.bonds;
    }

    public void addAtom(Atom atom) {
        this.atoms.add(atom);
    }

    public void addBond(Bond bond) {
        this.bonds.add(bond);
    }
}
