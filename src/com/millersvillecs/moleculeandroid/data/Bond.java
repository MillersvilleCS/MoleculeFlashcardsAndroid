package com.millersvillecs.moleculeandroid.data;

public class Bond {
	public enum BondType {
		SINGLE, DOUBLE, TRIPLE
	}

    private int from, to;
    private BondType type;
    
    public Bond(int from, int to, int type) {
    	setFrom(from);
    	setTo(to);
    	setType(type);
    }
    
    public void setFrom(int from) {
    	this.from = from;
    }
    
    public int getFrom() {
    	return from;
    }
    
    public void setTo(int to) {
    	this.to = to;
    }
    
    public int getTo() {
    	return to;
    }
    
    public BondType getType() {
    	return type;
    }
    
    public void setType(BondType type) {
    	this.type = type;
    }
    
    public void setType(int rep) {
    	switch(rep) {
    		case 1:
    			this.type = BondType.SINGLE;
    			break;
    		case 2:
    			this.type = BondType.DOUBLE;
    			break;
    		case 3:
    			this.type = BondType.TRIPLE;
    			break;
    		default:
    			this.type = BondType.SINGLE;
    	}
    }
}