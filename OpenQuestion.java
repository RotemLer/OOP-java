package project_Dvir_Siksik_Rotem_Ler;

import java.io.Serializable;

public class OpenQuestion extends Question implements Serializable  {
	private static final long serialVersionUID = -8802919508985513850L;//According to victor's directions.
	//members:
	private Answer theAnswer;

	//Constructor:
	public OpenQuestion(String textQuestion , eLevel level, Stock theStock) {
		super (textQuestion , level,theStock);
	}

	//setters and getters:
	public Answer getAnswers() {
		return theAnswer;
	}
	//other method
	@Override
	public void setAnswersFormStock(Stock theStock, int locationInStock, boolean isCorrectAnswer) {
		theAnswer = theStock.getAllAnswersArray()[locationInStock];			
	}
	//toString:
	@Override
	public String toStringQuestionWithAnswer() {
		StringBuffer data = new StringBuffer(super.toStringQuestionWithAnswer() + "\n"+"\n");
		data.append(theAnswer.toStringAnswer()+"\n");
		return data.toString();
	}

	@Override
	public String toStringQuestionWithOutCorrectAnswer() {
		return super.toStringQuestionWithOutCorrectAnswer()+"\n";
	}
}
