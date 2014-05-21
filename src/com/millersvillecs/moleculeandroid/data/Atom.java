package com.millersvillecs.moleculeandroid.data;

import com.millersvillecs.moleculeandroid.graphics.Color;

public class Atom {
    public double x, y, z, radius;
    public String type;
    public Color color;

    public void setType(String type) {
        //auto-generated
        //note: around 20 of the radii are different, which is why they are set in each "if", rather than iterating twice
        //note: moved "C" to the top, since it and H are the most common
        if(type.equals("H  ")) {
            this.color = new Color((float)255 / (float)255, 255 / 255, (float)255 / 255);
            this.radius = 1.2;
        } else if(type.equals("C  ")) {
            this.color = new Color((float)144 / (float)255, 144 / 255, (float)144 / 255);
            this.radius = 1.7;
        } else if(type.equals("HE ")) {
            this.color = new Color((float)217 / 255, (float)255 / 255, (float)255 / 255);
            this.radius = 1.5;
        } else if(type.equals("LI ")) {
            this.color = new Color((float)204 / 255, (float)128 / 255, (float)255 / 255);
            this.radius = 1.82;
        } else if(type.equals("BE ")) {
            this.color = new Color((float)194 / 255, (float)255 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("B  ")) {
            this.color = new Color((float)255 / 255, (float)181 / 255, (float)181 / 255);
            this.radius = 1.5;
        } else if(type.equals("N  ")) {
            this.color = new Color((float)48 / 255, (float)80 / 255, (float)248 / 255);
            this.radius = 1.55;
        } else if(type.equals("O  ")) {
            this.color = new Color((float)255 / 255, (float)13 / 255, (float)13 / 255);
            this.radius = 1.52;
        } else if(type.equals("F  ")) {
            this.color = new Color((float)144 / 255, (float)224 / 255, (float)80 / 255);
            this.radius = 1.47;
        } else if(type.equals("NE ")) {
            this.color = new Color((float) 179 / 255, (float) 227 / 255, (float) 245 / 255);
            this.radius = 1.5;
        } else if(type.equals("NA ")) {
            this.color = new Color((float)171 / 255, 92 / 255, 242 / 255);
            this.radius = 2.27;
        } else if(type.equals("MG ")) {
            this.color = new Color(138 / 255, 255 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("AL ")) {
            this.color = new Color(191 / 255, 166 / 255, 166 / 255);
            this.radius = 1.5;
        } else if(type.equals("SI ")) {
            this.color = new Color(240 / 255, 200 / 255, 160 / 255);
            this.radius = 1.5;
        } else if(type.equals("P  ")) {
            this.color = new Color(255 / 255, 128 / 255, 0);
            this.radius = 1.80;
        } else if(type.equals("S  ")) {
            this.color = new Color(255 / 255, 255 / 255, 48 / 255);
            this.radius = 1.80;
        } else if(type.equals("CL ")) {
            this.color = new Color(31 / 255, 240 / 255, 31 / 255);
            this.radius = 1.75;
        } else if(type.equals("AR ")) {
            this.color = new Color(128 / 255, 209 / 255, 227 / 255);
            this.radius = 1.5;
        } else if(type.equals("K  ")) {
            this.color = new Color(143 / 255, 64 / 255, 212 / 255);
            this.radius = 2.75;
        } else if(type.equals("CA ")) {
            this.color = new Color(61 / 255, 255 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("SC ")) {
            this.color = new Color(230 / 255, 230 / 255, 230 / 255);
            this.radius = 1.5;
        } else if(type.equals("TI ")) {
            this.color = new Color(191 / 255, 194 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("V  ")) {
            this.color = new Color(166 / 255, 166 / 255, 171 / 255);
            this.radius = 1.5;
        } else if(type.equals("CR ")) {
            this.color = new Color(138 / 255, 153 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("MN ")) {
            this.color = new Color(156 / 255, 122 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("FE ")) {
            this.color = new Color(224 / 255, 102 / 255, 51 / 255);
            this.radius = 1.5;
        } else if(type.equals("CO ")) {
            this.color = new Color(240 / 255, 144 / 255, 160 / 255);
            this.radius = 1.5;
        } else if(type.equals("NI ")) {
            this.color = new Color(80 / 255, 208 / 255, 80 / 255);
            this.radius = 1.63;
        } else if(type.equals("CU ")) {
            this.color = new Color(200 / 255, 128 / 255, 51 / 255);
            this.radius = 1.4;
        } else if(type.equals("ZN ")) {
            this.color = new Color(125 / 255, 128 / 255, 176 / 255);
            this.radius = 1.39;
        } else if(type.equals("GA ")) {
            this.color = new Color(194 / 255, 143 / 255, 143 / 255);
            this.radius = 1.5;
        } else if(type.equals("GE ")) {
            this.color = new Color(102 / 255, 143 / 255, 143 / 255);
            this.radius = 1.5;
        } else if(type.equals("AS ")) {
            this.color = new Color(189 / 255, 128 / 255, 227 / 255);
            this.radius = 1.5;
        } else if(type.equals("SE ")) {
            this.color = new Color(255 / 255, 161 / 255, 0);
            this.radius = 1.90;
        } else if(type.equals("BR ")) {
            this.color = new Color(166 / 255, 41 / 255, 41 / 255);
            this.radius = 1.85;
        } else if(type.equals("KR ")) {
            this.color = new Color(92 / 255,184 / 255, 209 / 255);
            this.radius = 1.5;
        } else if(type.equals("RB ")) {
            this.color = new Color(112 / 255, 46 / 255, 176 / 255);
            this.radius = 1.5;
        } else if(type.equals("SR ")) {
            this.color = new Color(0, 255 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("Y  ")) {
            this.color = new Color(148 / 255, 255 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("ZR ")) {
            this.color = new Color(148 / 255, 224 / 255, 224 / 255);
            this.radius = 1.5;
        } else if(type.equals("NB ")) {
            this.color = new Color(115 / 255, 194 / 255, 201 / 255);
            this.radius = 1.5;
        } else if(type.equals("MO ")) {
            this.color = new Color(84 / 255, 181 / 255, 181 / 255);
            this.radius = 1.5;
        } else if(type.equals("TC ")) {
            this.color = new Color(59 / 255, 158 / 255, 158 / 255);
            this.radius = 1.5;
        } else if(type.equals("RU ")) {
            this.color = new Color(36 / 255, 143 / 255, 143 / 255);
            this.radius = 1.5;
        } else if(type.equals("RH ")) {
            this.color = new Color(10 / 255, 125 / 255, 140 / 255);
            this.radius = 1.5;
        } else if(type.equals("PD ")) {
            this.color = new Color(0, 105 / 255, 133 / 255);
            this.radius = 1.5;
        } else if(type.equals("AG ")) {
            this.color = new Color(192 / 255, 192 / 255, 192 / 255);
            this.radius = 1.5;
        } else if(type.equals("CD ")) {
            this.color = new Color(255 / 255, 217 / 255, 143 / 255);
            this.radius = 1.5;
        } else if(type.equals("IN ")) {
            this.color = new Color(166 / 255, 117 / 255, 115 / 255);
            this.radius = 1.5;
        } else if(type.equals("SN ")) {
            this.color = new Color(102 / 255, 128 / 255, 128 / 255);
            this.radius = 1.5;
        } else if(type.equals("SB ")) {
            this.color = new Color(158 / 255, 99 / 255, 181 / 255);
            this.radius = 1.5;
        } else if(type.equals("TE ")) {
            this.color = new Color(212 / 255, 122 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("I  ")) {
            this.color = new Color(148 / 255, 0, 148 / 255);
            this.radius = 1.5;
        } else if(type.equals("XE ")) {
            this.color = new Color(66 / 255, 158 / 255, 176 / 255);
            this.radius = 1.5;
        } else if(type.equals("CS ")) {
            this.color = new Color(87 / 255, 23 / 255, 143 / 255);
            this.radius = 1.5;
        } else if(type.equals("BA ")) {
            this.color = new Color(0, 201 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("LA ")) {
            this.color = new Color(112 / 255, 212 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("CE ")) {
            this.color = new Color(255 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("PR ")) {
            this.color = new Color(217 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("ND ")) {
            this.color = new Color(199 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("PM ")) {
            this.color = new Color(163 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("SM ")) {
            this.color = new Color(143 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("EU ")) {
            this.color = new Color(97 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("GD ")) {
            this.color = new Color(69 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("TB ")) {
            this.color = new Color(48 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("DY ")) {
            this.color = new Color(31 / 255, 255 / 255, 199 / 255);
            this.radius = 1.5;
        } else if(type.equals("HO ")) {
            this.color = new Color(0, 255 / 255, 156 / 255);
            this.radius = 1.5;
        } else if(type.equals("ER ")) {
            this.color = new Color(0, 230 / 255, 117 / 255);
            this.radius = 1.5;
        } else if(type.equals("TM ")) {
            this.color = new Color(0, 212 / 255, 82 / 255);
            this.radius = 1.5;
        } else if(type.equals("YB ")) {
            this.color = new Color(0, 191 / 255, 56 / 255);
            this.radius = 1.5;
        } else if(type.equals("LU ")) {
            this.color = new Color(0, 171 / 255, 36 / 255);
            this.radius = 1.5;
        } else if(type.equals("HF ")) {
            this.color = new Color(77 / 255, 194 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("TA ")) {
            this.color = new Color(77 / 255, 166 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("W  ")) {
            this.color = new Color(33 / 255, 148 / 255, 214 / 255);
            this.radius = 1.5;
        } else if(type.equals("RE ")) {
            this.color = new Color(38 / 255, 125 / 255, 171 / 255);
            this.radius = 1.5;
        } else if(type.equals("OS ")) {
            this.color = new Color(38 / 255, 102 / 255, 150 / 255);
            this.radius = 1.5;
        } else if(type.equals("IR ")) {
            this.color = new Color(23 / 255, 84 / 255, 135 / 255);
            this.radius = 1.5;
        } else if(type.equals("PT ")) {
            this.color = new Color(208 / 255, 208 / 255, 224 / 255);
            this.radius = 1.5;
        } else if(type.equals("AU ")) {
            this.color = new Color(255 / 255, 209 / 255, 35 / 255);
            this.radius = 1.5;
        } else if(type.equals("HG ")) {
            this.color = new Color(184 / 255, 184 / 255, 208 / 255);
            this.radius = 1.5;
        } else if(type.equals("TL ")) {
            this.color = new Color(166 / 255, 84 / 255, 77 / 255);
            this.radius = 1.5;
        } else if(type.equals("PB ")) {
            this.color = new Color(87 / 255, 89 / 255, 97 / 255);
            this.radius = 1.5;
        } else if(type.equals("BI ")) {
            this.color = new Color(158 / 255, 79 / 255, 181 / 255);
            this.radius = 1.5;
        } else if(type.equals("PO ")) {
            this.color = new Color(171 / 255, 92 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("AT ")) {
            this.color = new Color(117 / 255, 79 / 255, 69 / 255);
            this.radius = 1.5;
        } else if(type.equals("RN ")) {
            this.color = new Color(66 / 255, 13 / 2550, 150 / 255);
            this.radius = 1.5;
        } else if(type.equals("FR ")) {
            this.color = new Color(66 / 255, 0, 102 / 255);
            this.radius = 1.5;
        } else if(type.equals("RA ")) {
            this.color = new Color(0, 125 / 255, 0);
            this.radius = 1.5;
        } else if(type.equals("AC ")) {
            this.color = new Color(112 / 255, 171 / 255, 250 / 255);
            this.radius = 1.5;
        } else if(type.equals("TH ")) {
            this.color = new Color(0, 186 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("PA ")) {
            this.color = new Color(0, 161 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("U  ")) {
            this.color = new Color(0, 143 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("NP ")) {
            this.color = new Color(0, 128 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("PU ")) {
            this.color = new Color(0, 107 / 255, 255 / 255);
            this.radius = 1.5;
        } else if(type.equals("AM ")) {
            this.color = new Color(84 / 255, 92 / 255, 242 / 255);
            this.radius = 1.5;
        } else if(type.equals("CM ")) {
            this.color = new Color(120 / 255, 92 / 255, 227 / 255);
            this.radius = 1.5;
        } else if(type.equals("BK ")) {
            this.color = new Color(138 / 255, 79 / 255, 227 / 255);
            this.radius = 1.5;
        } else if(type.equals("CF ")) {
            this.color = new Color(161 / 255, 54 / 255, 212 / 255);
            this.radius = 1.5;
        } else if(type.equals("ES ")) {
            this.color = new Color(179 / 255, 31 / 255, 212 / 255);
            this.radius = 1.5;
        } else if(type.equals("FM ")) {
            this.color = new Color(179 / 255, 31 / 255, 186 / 255);
            this.radius = 1.5;
        } else if(type.equals("MD ")) {
            this.color = new Color(179 / 255, 13 / 255, 166 / 255);
            this.radius = 1.5;
        } else if(type.equals("NO ")) {
            this.color = new Color(189 / 255, 13 / 255, 135 / 255);
            this.radius = 1.5;
        } else if(type.equals("LR ")) {
            this.color = new Color(199 / 255, 0, 102 / 255);
            this.radius = 1.5;
        } else if(type.equals("RF ")) {
            this.color = new Color(204 / 255, 0, 89 / 255);
            this.radius = 1.5;
        } else if(type.equals("DB ")) {
            this.color = new Color(209 / 255, 0, 79 / 255);
            this.radius = 1.5;
        } else if(type.equals("SG ")) {
            this.color = new Color(217 / 255, 0, 69 / 255);
            this.radius = 1.5;
        } else if(type.equals("BH ")) {
            this.color = new Color(224 / 255, 0, 56 / 255);
            this.radius = 1.5;
        } else if(type.equals("HS ")) {
            this.color = new Color(230 / 255, 0, 46 / 255);
            this.radius = 1.5;
        } else if(type.equals("MT ")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("DS ")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("RG ")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("CN ")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("UUT")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("UUQ")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("UUP")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("UUH")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("UUS")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else if(type.equals("UUO")) {
            this.color = new Color(235 / 255, 0, 38 / 255);
            this.radius = 1.5;
        } else {
            this.color = new Color(0.5f, 0, 1);
            this.radius = 1.5;
        }

        this.type = type.replace(" ", "");
    }
}
