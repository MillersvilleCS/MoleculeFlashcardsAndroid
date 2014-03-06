package com.millersvillecs.moleculeandroid.data;

import android.graphics.Color;

public class Atom {
    public double x, y, z, radius;
    public String type;
    public int color;

    public void setType(String type) {
        //auto-generated
        //note: around 20 of the radii are different, which is why they are set in each "if", rather than iterating twice
        //note: moved "C" to the top, since it and H are the most common
        if(type.equals("H  ")) {
            this.color = Color.rgb(255, 255, 255);
            this.radius = 1.2;
        } else if(type.equals("C  ")) {
            this.color = Color.rgb(144, 144, 144);
            this.radius = 1.7;
        } else if(type.equals("HE ")) {
            this.color = Color.rgb(217, 255, 255);
            this.radius = 1.5;
        } else if(type.equals("LI ")) {
            this.color = Color.rgb(204, 128, 255);
            this.radius = 1.82;
        } else if(type.equals("BE ")) {
            this.color = Color.rgb(194, 255, 0);
            this.radius = 1.5;
        } else if(type.equals("B  ")) {
            this.color = Color.rgb(255, 181, 181);
            this.radius = 1.5;
        } else if(type.equals("N  ")) {
            this.color = Color.rgb(48, 80, 248);
            this.radius = 1.55;
        } else if(type.equals("O  ")) {
            this.color = Color.rgb(255, 13, 13);
            this.radius = 1.52;
        } else if(type.equals("F  ")) {
            this.color = Color.rgb(144, 224, 80);
            this.radius = 1.47;
        } else if(type.equals("NE ")) {
            this.color = Color.rgb(179, 227, 245);
            this.radius = 1.5;
        } else if(type.equals("NA ")) {
            this.color = Color.rgb(171, 92, 242);
            this.radius = 2.27;
        } else if(type.equals("MG ")) {
            this.color = Color.rgb(138, 255, 0);
            this.radius = 1.5;
        } else if(type.equals("AL ")) {
            this.color = Color.rgb(191, 166, 166);
            this.radius = 1.5;
        } else if(type.equals("SI ")) {
            this.color = Color.rgb(240, 200, 160);
            this.radius = 1.5;
        } else if(type.equals("P  ")) {
            this.color = Color.rgb(255, 128, 0);
            this.radius = 1.80;
        } else if(type.equals("S  ")) {
            this.color = Color.rgb(255, 255, 48);
            this.radius = 1.80;
        } else if(type.equals("CL ")) {
            this.color = Color.rgb(31, 240, 31);
            this.radius = 1.75;
        } else if(type.equals("AR ")) {
            this.color = Color.rgb(128, 209, 227);
            this.radius = 1.5;
        } else if(type.equals("K  ")) {
            this.color = Color.rgb(143, 64, 212);
            this.radius = 2.75;
        } else if(type.equals("CA ")) {
            this.color = Color.rgb(61, 255, 0);
            this.radius = 1.5;
        } else if(type.equals("SC ")) {
            this.color = Color.rgb(230, 230, 230);
            this.radius = 1.5;
        } else if(type.equals("TI ")) {
            this.color = Color.rgb(191, 194, 199);
            this.radius = 1.5;
        } else if(type.equals("V  ")) {
            this.color = Color.rgb(166, 166, 171);
            this.radius = 1.5;
        } else if(type.equals("CR ")) {
            this.color = Color.rgb(138, 153, 199);
            this.radius = 1.5;
        } else if(type.equals("MN ")) {
            this.color = Color.rgb(156, 122, 199);
            this.radius = 1.5;
        } else if(type.equals("FE ")) {
            this.color = Color.rgb(224, 102, 51);
            this.radius = 1.5;
        } else if(type.equals("CO ")) {
            this.color = Color.rgb(240, 144, 160);
            this.radius = 1.5;
        } else if(type.equals("NI ")) {
            this.color = Color.rgb(80, 208, 80);
            this.radius = 1.63;
        } else if(type.equals("CU ")) {
            this.color = Color.rgb(200, 128, 51);
            this.radius = 1.4;
        } else if(type.equals("ZN ")) {
            this.color = Color.rgb(125, 128, 176);
            this.radius = 1.39;
        } else if(type.equals("GA ")) {
            this.color = Color.rgb(194, 143, 143);
            this.radius = 1.5;
        } else if(type.equals("GE ")) {
            this.color = Color.rgb(102, 143, 143);
            this.radius = 1.5;
        } else if(type.equals("AS ")) {
            this.color = Color.rgb(189, 128, 227);
            this.radius = 1.5;
        } else if(type.equals("SE ")) {
            this.color = Color.rgb(255, 161, 0);
            this.radius = 1.90;
        } else if(type.equals("BR ")) {
            this.color = Color.rgb(166, 41, 41);
            this.radius = 1.85;
        } else if(type.equals("KR ")) {
            this.color = Color.rgb(92, 184, 209);
            this.radius = 1.5;
        } else if(type.equals("RB ")) {
            this.color = Color.rgb(112, 46, 176);
            this.radius = 1.5;
        } else if(type.equals("SR ")) {
            this.color = Color.rgb(0, 255, 0);
            this.radius = 1.5;
        } else if(type.equals("Y  ")) {
            this.color = Color.rgb(148, 255, 255);
            this.radius = 1.5;
        } else if(type.equals("ZR ")) {
            this.color = Color.rgb(148, 224, 224);
            this.radius = 1.5;
        } else if(type.equals("NB ")) {
            this.color = Color.rgb(115, 194, 201);
            this.radius = 1.5;
        } else if(type.equals("MO ")) {
            this.color = Color.rgb(84, 181, 181);
            this.radius = 1.5;
        } else if(type.equals("TC ")) {
            this.color = Color.rgb(59, 158, 158);
            this.radius = 1.5;
        } else if(type.equals("RU ")) {
            this.color = Color.rgb(36, 143, 143);
            this.radius = 1.5;
        } else if(type.equals("RH ")) {
            this.color = Color.rgb(10, 125, 140);
            this.radius = 1.5;
        } else if(type.equals("PD ")) {
            this.color = Color.rgb(0, 105, 133);
            this.radius = 1.5;
        } else if(type.equals("AG ")) {
            this.color = Color.rgb(192, 192, 192);
            this.radius = 1.5;
        } else if(type.equals("CD ")) {
            this.color = Color.rgb(255, 217, 143);
            this.radius = 1.5;
        } else if(type.equals("IN ")) {
            this.color = Color.rgb(166, 117, 115);
            this.radius = 1.5;
        } else if(type.equals("SN ")) {
            this.color = Color.rgb(102, 128, 128);
            this.radius = 1.5;
        } else if(type.equals("SB ")) {
            this.color = Color.rgb(158, 99, 181);
            this.radius = 1.5;
        } else if(type.equals("TE ")) {
            this.color = Color.rgb(212, 122, 0);
            this.radius = 1.5;
        } else if(type.equals("I  ")) {
            this.color = Color.rgb(148, 0, 148);
            this.radius = 1.5;
        } else if(type.equals("XE ")) {
            this.color = Color.rgb(66, 158, 176);
            this.radius = 1.5;
        } else if(type.equals("CS ")) {
            this.color = Color.rgb(87, 23, 143);
            this.radius = 1.5;
        } else if(type.equals("BA ")) {
            this.color = Color.rgb(0, 201, 0);
            this.radius = 1.5;
        } else if(type.equals("LA ")) {
            this.color = Color.rgb(112, 212, 255);
            this.radius = 1.5;
        } else if(type.equals("CE ")) {
            this.color = Color.rgb(255, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("PR ")) {
            this.color = Color.rgb(217, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("ND ")) {
            this.color = Color.rgb(199, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("PM ")) {
            this.color = Color.rgb(163, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("SM ")) {
            this.color = Color.rgb(143, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("EU ")) {
            this.color = Color.rgb(97, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("GD ")) {
            this.color = Color.rgb(69, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("TB ")) {
            this.color = Color.rgb(48, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("DY ")) {
            this.color = Color.rgb(31, 255, 199);
            this.radius = 1.5;
        } else if(type.equals("HO ")) {
            this.color = Color.rgb(0, 255, 156);
            this.radius = 1.5;
        } else if(type.equals("ER ")) {
            this.color = Color.rgb(0, 230, 117);
            this.radius = 1.5;
        } else if(type.equals("TM ")) {
            this.color = Color.rgb(0, 212, 82);
            this.radius = 1.5;
        } else if(type.equals("YB ")) {
            this.color = Color.rgb(0, 191, 56);
            this.radius = 1.5;
        } else if(type.equals("LU ")) {
            this.color = Color.rgb(0, 171, 36);
            this.radius = 1.5;
        } else if(type.equals("HF ")) {
            this.color = Color.rgb(77, 194, 255);
            this.radius = 1.5;
        } else if(type.equals("TA ")) {
            this.color = Color.rgb(77, 166, 255);
            this.radius = 1.5;
        } else if(type.equals("W  ")) {
            this.color = Color.rgb(33, 148, 214);
            this.radius = 1.5;
        } else if(type.equals("RE ")) {
            this.color = Color.rgb(38, 125, 171);
            this.radius = 1.5;
        } else if(type.equals("OS ")) {
            this.color = Color.rgb(38, 102, 150);
            this.radius = 1.5;
        } else if(type.equals("IR ")) {
            this.color = Color.rgb(23, 84, 135);
            this.radius = 1.5;
        } else if(type.equals("PT ")) {
            this.color = Color.rgb(208, 208, 224);
            this.radius = 1.5;
        } else if(type.equals("AU ")) {
            this.color = Color.rgb(255, 209, 35);
            this.radius = 1.5;
        } else if(type.equals("HG ")) {
            this.color = Color.rgb(184, 184, 208);
            this.radius = 1.5;
        } else if(type.equals("TL ")) {
            this.color = Color.rgb(166, 84, 77);
            this.radius = 1.5;
        } else if(type.equals("PB ")) {
            this.color = Color.rgb(87, 89, 97);
            this.radius = 1.5;
        } else if(type.equals("BI ")) {
            this.color = Color.rgb(158, 79, 181);
            this.radius = 1.5;
        } else if(type.equals("PO ")) {
            this.color = Color.rgb(171, 92, 0);
            this.radius = 1.5;
        } else if(type.equals("AT ")) {
            this.color = Color.rgb(117, 79, 69);
            this.radius = 1.5;
        } else if(type.equals("RN ")) {
            this.color = Color.rgb(66, 130, 150);
            this.radius = 1.5;
        } else if(type.equals("FR ")) {
            this.color = Color.rgb(66, 0, 102);
            this.radius = 1.5;
        } else if(type.equals("RA ")) {
            this.color = Color.rgb(0, 125, 0);
            this.radius = 1.5;
        } else if(type.equals("AC ")) {
            this.color = Color.rgb(112, 171, 250);
            this.radius = 1.5;
        } else if(type.equals("TH ")) {
            this.color = Color.rgb(0, 186, 255);
            this.radius = 1.5;
        } else if(type.equals("PA ")) {
            this.color = Color.rgb(0, 161, 255);
            this.radius = 1.5;
        } else if(type.equals("U  ")) {
            this.color = Color.rgb(0, 143, 255);
            this.radius = 1.5;
        } else if(type.equals("NP ")) {
            this.color = Color.rgb(0, 128, 255);
            this.radius = 1.5;
        } else if(type.equals("PU ")) {
            this.color = Color.rgb(0, 107, 255);
            this.radius = 1.5;
        } else if(type.equals("AM ")) {
            this.color = Color.rgb(84, 92, 242);
            this.radius = 1.5;
        } else if(type.equals("CM ")) {
            this.color = Color.rgb(120, 92, 227);
            this.radius = 1.5;
        } else if(type.equals("BK ")) {
            this.color = Color.rgb(138, 79, 227);
            this.radius = 1.5;
        } else if(type.equals("CF ")) {
            this.color = Color.rgb(161, 54, 212);
            this.radius = 1.5;
        } else if(type.equals("ES ")) {
            this.color = Color.rgb(179, 31, 212);
            this.radius = 1.5;
        } else if(type.equals("FM ")) {
            this.color = Color.rgb(179, 31, 186);
            this.radius = 1.5;
        } else if(type.equals("MD ")) {
            this.color = Color.rgb(179, 13, 166);
            this.radius = 1.5;
        } else if(type.equals("NO ")) {
            this.color = Color.rgb(189, 13, 135);
            this.radius = 1.5;
        } else if(type.equals("LR ")) {
            this.color = Color.rgb(199, 0, 102);
            this.radius = 1.5;
        } else if(type.equals("RF ")) {
            this.color = Color.rgb(204, 0, 89);
            this.radius = 1.5;
        } else if(type.equals("DB ")) {
            this.color = Color.rgb(209, 0, 79);
            this.radius = 1.5;
        } else if(type.equals("SG ")) {
            this.color = Color.rgb(217, 0, 69);
            this.radius = 1.5;
        } else if(type.equals("BH ")) {
            this.color = Color.rgb(224, 0, 56);
            this.radius = 1.5;
        } else if(type.equals("HS ")) {
            this.color = Color.rgb(230, 0, 46);
            this.radius = 1.5;
        } else if(type.equals("MT ")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("DS ")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("RG ")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("CN ")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("UUT")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("UUQ")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("UUP")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("UUH")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("UUS")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else if(type.equals("UUO")) {
            this.color = Color.rgb(235, 0, 38);
            this.radius = 1.5;
        } else {
            this.color = Color.rgb(0, 0, 0);
            this.radius = 1.5;
        }

        this.type = type.replace(" ", "");
    }
}
