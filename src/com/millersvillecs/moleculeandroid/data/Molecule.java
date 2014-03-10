package com.millersvillecs.moleculeandroid.data;

import java.util.ArrayList;

import android.graphics.Color;

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

    public void constructGeometry() {
        //TODO
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < this.atoms.size(); i++) {
            Atom atom = this.atoms.get(i);
            s+= "Atom " + i + "\n";
            if(atom.x < 0) {
                s += "    X =   " + atom.x + "\n";
            } else {
                s += "    X =    " + atom.x + "\n";
            }
            if(atom.y < 0) {
                s += "    Y =   " + atom.y + "\n";
            } else {
                s += "    Y =    " + atom.y + "\n";
            }
            if(atom.z < 0) {
                s += "    Z =   " + atom.z + "\n";
            } else {
                s += "    Z =    " + atom.z + "\n";
            }

            s += "    TYPE = " + atom.type + ", Color = " + Color.red(atom.color) + " " + Color.green(atom.color) +
                 " " + Color.blue(atom.color) +  ", Radius = " + atom.radius + "\n";
        }

        s += "\n-----------------------------------------\n\n";

        for(Bond bond : this.bonds) {
            s += "FROM:" + bond.from;
            if(bond.from < 10) {
                s += "   TO:" + bond.to;
            } else {
                s += "  TO:" + bond.to;
            }
            if(bond.to < 10) {
                s += "   TYPE:" + bond.type + "\n";
            } else {
                s += "  TYPE:" + bond.type + "\n";
            }
        }

        return s;
    }
}
