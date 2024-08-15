package project_Dvir_Siksik_Rotem_Ler;

import java.io.Serializable;

public class AmericanQuestion extends Question implements Serializable  {

	private static final long serialVersionUID = -6598277192959537987L; //According to victor's directions.
	//members:
	public final int MAX_ANSWERS = 10;
	private Answer[] specificAnswersForQuestion;
	private boolean[] isCorrectAnswerArray;
	private int totalAnswers;
	private int countCorrectAnswer;
	private int theCorrectAnswerLocation;
	// if there is no correct answer, the difoltive correct is in location 0=there
	// is no correct answer.

	//Constructor:
	public AmericanQuestion (String textQuestion , eLevel level,Stock theStock) {
		super (textQuestion , level,theStock);

		specificAnswersForQuestion = new Answer[MAX_ANSWERS];
		isCorrectAnswerArray = new boolean[MAX_ANSWERS];
		if (totalAnswers == 0)
			setDeafultAnswers();
	}

	//setters and getters:
	public int setCorrectAnswer() {
		if (countCorrectAnswer > 1) {
			theCorrectAnswerLocation = 1;
		} else if (countCorrectAnswer == 1) {
			for (int i = 0; i < MAX_ANSWERS; i++) {
				if (isCorrectAnswerArray[i]) {
					theCorrectAnswerLocation = i;
					i = MAX_ANSWERS;
				}
			}

		} else {
			theCorrectAnswerLocation = 0;
		}
		return theCorrectAnswerLocation;
	}

	private void setDeafultAnswers() {
		specificAnswersForQuestion[0] = new Answer("None answers is correct");
		specificAnswersForQuestion[1] = new Answer("More then one answers is correct");
		totalAnswers += 2;
	}

	public Answer getAnswer(int location) {
		return specificAnswersForQuestion[location];
	}

	public boolean getIfAnswerCorrect(int location) {
		return isCorrectAnswerArray[location]; 
	}

	public int getTotalAnswers() {
		return totalAnswers;
	}

	public int getCountCorrectAnswer() {
		return countCorrectAnswer;
	}

	//other methods:
	@Override
	public void setAnswersFormStock(Stock theStock, int locationInStock, boolean isCorrectAnswer) {
		specificAnswersForQuestion[totalAnswers] = theStock.getAllAnswersArray()[locationInStock];

		if (isCorrectAnswer) {
			isCorrectAnswerArray[totalAnswers] = true;
			countCorrectAnswer++;
			theCorrectAnswerLocation =totalAnswers;

		}
		totalAnswers++;
		setCorrectAnswer();


	}

	public boolean removeAnswersByLocationFromQuestion(Stock theStock, int location) {
		if (location < MAX_ANSWERS) {
			specificAnswersForQuestion[location] = specificAnswersForQuestion[totalAnswers - 1];
			specificAnswersForQuestion[totalAnswers] = null;
			if (isCorrectAnswerArray[location]) {
				countCorrectAnswer--;
				setCorrectAnswer();
			}
			isCorrectAnswerArray[location] = isCorrectAnswerArray[totalAnswers - 1];
			isCorrectAnswerArray[totalAnswers] = false;
			totalAnswers--;
			if (totalAnswers == 2) {
				for (int i = 0; i < theStock.getCurrentNumOfQuestion(); i++) {
					if (theStock.getAQuestion(i).getTextQuestion().equals(textQuestion)) {
						theStock.removeQuestionByLocationFromStock(i);
					}
				}
				return true;
			}

		}
		return false;
	}

	//toString:
	@Override
	public String toStringQuestionWithAnswer() {
		StringBuffer data = new StringBuffer(super.toStringQuestionWithAnswer() + "\n" + "\n");
		if (totalAnswers > 2) {
			for (int i = 0; i < totalAnswers; i++) {
				if (i == theCorrectAnswerLocation) {
					data.append(
							"  " + (i + 1) + ")" + specificAnswersForQuestion[i].toStringAnswer() + "(true)" + "\n");

				} else {
					data.append("  " + (i + 1) + ")" + specificAnswersForQuestion[i].toStringAnswer() + "\n");
				}
			}
		} else {
			data.append(" YOU HAVENT ENTERD ANSWER \n");
		}
		return data.toString();
	}
	@Override
	public String toStringQuestionWithOutCorrectAnswer() {
		StringBuffer data = new StringBuffer(super.toStringQuestionWithOutCorrectAnswer() + "\n" + "\n");
		if (totalAnswers > 2) {
			for (int i = 0; i < totalAnswers; i++) {

				data.append("  " + (i + 1) + ")" + specificAnswersForQuestion[i].toStringAnswer() + "\n");

			}
		}
		return data.toString();
	}
}
