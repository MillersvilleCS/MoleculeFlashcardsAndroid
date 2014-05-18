package com.millersvillecs.moleculeandroid;

import android.app.Fragment;
import android.os.Bundle;

import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.helper.Question;

public class GameFragment extends Fragment {
	
	private Molecule[] molecules;
	private Question[] questions;
	private int[] buttonStates;
	private String gameSessionId;
	private double score, scoreChange;
	private int currentIndex, lastIndex, rank;
	private long timeLimit, timeStart;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // retain this fragment
        setRetainInstance(true);
    }
	
	public void setMolecules(Molecule[] molecules) {
		this.molecules = molecules;
	}
	
	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}
	
	public void setSessionId(String gameSessionId) {
		this.gameSessionId = gameSessionId;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public void setScoreChange(double scoreChange) {
		this.scoreChange = scoreChange;
	}
	
	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}
	
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setButtonStates(int[] buttonStates) {
		this.buttonStates = buttonStates;
	}
	
	public Molecule[] getMolecules() {
		return this.molecules;
	}
	
	public Question[] getQuestions() {
		return this.questions;
	}
	
	public String getGameSessionId() {
		return this.gameSessionId;
	}
	
	public double getScore() {
		return this.score;
	}
	
	public double getScoreChange() {
		return this.scoreChange;
	}
	
	public long getTimeLimit() {
		return this.timeLimit;
	}
	
	public long getTimeStart() {
		return this.timeStart;
	}
	
	public int getCurrentIndex() {
		return this.currentIndex;
	}
	
	public int getLastIndex() {
		return this.lastIndex;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public int[] getButtonStates() {
		return this.buttonStates;
	}
}
