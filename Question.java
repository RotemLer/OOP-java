package project_Dvir_Siksik_Rotem_Ler;

import java.io.Serializable;

public abstract class Question implements Serializable  {
	//final members:
	protected static final long serialVersionUID = 5289320956390625210L; //According to victor's directions.
	private final static int MIN_NUM_OF_Question=1;
	//members:	
	public enum eLevel {Easy , Medium , High};
	protected String textQuestion;
	protected static int counter;
	protected int serialNumber;
	protected eLevel theLevel;

	//Constructor:
	public Question (String textQuestion , eLevel level, Stock theStock) {
		setTextQuestion(textQuestion);
		theLevel = level;
		counter = theStock.getCurrentNumOfQuestion()+1;
		setSerialNumber ();
	}



	//setters and getters:
	protected boolean setTextQuestion(String text) {
		if (text != null) {
			textQuestion = text;
			return true;
		}
		return false;
	}

	protected void setSerialNumber() {
		serialNumber = counter;
		counter++;
	}
	protected abstract void setAnswersFormStock(Stock theStock, int locationInStock, boolean isCorrectAnswer);

	protected int getNumQuestion() {
		return serialNumber;
	}

	protected int getTotalNumQuestions() {
		return counter + MIN_NUM_OF_Question;
	}

	protected String getTextQuestion() {
		return textQuestion;
	}

	protected eLevel getELevelQuestion() {
		return theLevel;
	}

	//other methods:

	public String toStringQuestionWithOutCorrectAnswer() {
		return   textQuestion + " (Level: " + theLevel + ")" ;		
	}

	public String toStringQuestionWithAnswer() {
		return "The serial number: (" + serialNumber + ") " + textQuestion + " (Level: " + theLevel + ")";		
	}


}

