package project_Dvir_Siksik_Rotem_Ler;
import java.io.Serializable;

public class Stock implements Serializable{
	//Members
	private static final long serialVersionUID = -403642400902725045L;//According to victor's directions.
	private int currentNumOfQuestion;
	private Question[] allQuestionsArray;
	private int currentNumOfAnswers;
	private String stockName;
	private  Answer[] allAnswersArray;
	// Constructor
	public Stock(String stockName) {
		currentNumOfQuestion=0;
		currentNumOfAnswers = 0;
		allAnswersArray = new Answer[currentNumOfAnswers];
		allQuestionsArray = new Question[currentNumOfQuestion];
		setStockName(stockName);
	}
	// getter and setters
	public String getStockName() {
		return stockName;
	}

	public Question getAQuestion(int location) {
		return allQuestionsArray[location];
	}

	public int getCurrentNumOfQuestion() {
		return currentNumOfQuestion;
	}

	public int getCurrentNumOfAnswers() {
		return currentNumOfAnswers;
	}
	public Question[] getAllQuestionsArray() {
		return allQuestionsArray;
	}
	public Answer[] getAllAnswersArray() {
		return allAnswersArray;
	}

	private boolean setStockName(String name) {
		if (name != null) {
			stockName=name;
			return true;
		}
		return false;
	}
	// Questions methods
	public void addQuestionToStock(Question newQuestions) {

		if (currentNumOfQuestion < allQuestionsArray.length) {
			allQuestionsArray[currentNumOfQuestion] = newQuestions;

		} else {
			Question[] tempQuestionArray = new Question[currentNumOfQuestion + 1];
			System.arraycopy(allQuestionsArray, 0, tempQuestionArray, 0, allQuestionsArray.length);
			tempQuestionArray[tempQuestionArray.length - 1] = newQuestions;

			allQuestionsArray = tempQuestionArray;
		}

		currentNumOfQuestion++;
	}
	public boolean removeQuestionByLocationFromStock(int location) {
		if (location < currentNumOfQuestion) {
			currentNumOfQuestion--;
			allQuestionsArray[location] = allQuestionsArray[currentNumOfQuestion];
			allQuestionsArray[currentNumOfQuestion] = null;
			Question[] tempQuestionArray = new Question[currentNumOfQuestion];
			System.arraycopy(allQuestionsArray, 0, tempQuestionArray, 0, allQuestionsArray.length-1);
			allQuestionsArray = tempQuestionArray;
			return true;
		}
		return false;
	}

	// Answer methods
	public void addAnswersToStock(Answer newAnswer) {
		if (currentNumOfAnswers < allAnswersArray.length) {
			allAnswersArray[currentNumOfAnswers] = newAnswer;

		} else {
			Answer[] tempAnswerArray = new Answer[currentNumOfAnswers + 1];
			System.arraycopy(allAnswersArray, 0, tempAnswerArray, 0, allAnswersArray.length);
			tempAnswerArray[tempAnswerArray.length - 1] = newAnswer;

			allAnswersArray = tempAnswerArray;
		}

		currentNumOfAnswers++;

	}
	// other methods
	public String toStringAnswersStock() {
		StringBuffer data = new StringBuffer("their are  " + currentNumOfAnswers + " answers \n ");
		if (currentNumOfAnswers > 0) {
			for (int i = 0; i < currentNumOfAnswers; i++) {
				data.append(i + 1 + ")" + allAnswersArray[i].toStringAnswer() + "\n");
			}
		}
		return data.toString();
	}
	public String toStringStockAndCorrectAnswers() {
		StringBuffer data = new StringBuffer("their are  " + currentNumOfQuestion + " questions \n ");
		if (currentNumOfQuestion > 0) {
			for (int i = 0; i < currentNumOfQuestion; i++) {

				data.append(allQuestionsArray[i].toStringQuestionWithAnswer() + "\n");

			}
		}
		return data.toString();
	}
public void recoverStock() {
	allAnswersArray= new Answer[0];
	allQuestionsArray=new Question [0];
}


}
