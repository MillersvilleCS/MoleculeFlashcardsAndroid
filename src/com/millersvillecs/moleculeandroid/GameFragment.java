package com.millersvillecs.moleculeandroid;

import android.app.Fragment;
import android.os.Bundle;

import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.helper.Question;


/**
 * 
 * @author connor
 * 
 * This class stores all data necessary to reload a game after a rotation.
 * 
 * Never store references to an Activity or UI component here. Since this fragment
 * is marked to not be garbage collected while the rest of the activity is on rotation,
 * storing a reference to one of those components here will cause a memory leak.
 */
public class GameFragment extends Fragment {
	
	private Molecule[] molecules;
	private Question[] questions;
	private int[] buttonStates;
	private String gameSessionId;
	private int currentIndex, lastIndex, rank, score, scoreChange;
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
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setScoreChange(int scoreChange) {
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
	
	public int getScore() {
		return this.score;
	}
	
	public int getScoreChange() {
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
