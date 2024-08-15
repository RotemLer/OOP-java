package project_Dvir_Siksik_Rotem_Ler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ManualExam extends Exam  implements Examable {
	public static Scanner s = new Scanner(System.in);
	//members 
	private File fExam;
	private File fSolution;
	private PrintWriter pwe;
	private PrintWriter pws;
	private int locationOfQuestion;
	//Constructor
	public ManualExam(Stock st, int numQ, String type) throws FileNotFoundException {
		super(st,numQ,type);
	}
	//other methods
	@Override
	public void creatExam(Stock theStock) throws Exception  {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		fExam = new File(theStock.getStockName()+" exam_(manual)_" + ldt.format(dtf)+".txt");
		fSolution = new File(theStock.getStockName()+" solution_(manual)_" + ldt.format(dtf)+".txt");
		pwe = new PrintWriter(fExam);
		pws = new PrintWriter(fSolution);
		pwe.print("new exam " + ldt.format(dtf) + " \n");
		pws.print(" the solutions: " + ldt.format(dtf) + "\n");
		System.out.println(theStock.toStringStockAndCorrectAnswers());
		for (int i = 0; i < numOfQuestions; i++) {
			System.out.println("type the num of the " + (i + 1) + " question");
			locationOfQuestion = s.nextInt();
			locationOfQuestion=checkRange(locationOfQuestion, theStock.getCurrentNumOfQuestion(),1);
			if(checkIfAmericanType(theStock.getAQuestion(locationOfQuestion - 1))) {
				try {
					AmericanQuestion theQuestion = (AmericanQuestion)theStock.getAQuestion(locationOfQuestion - 1);
					if (theQuestion.getTotalAnswers() <= 4 ) {
						throw new Exception("THERE ARE NOT ENOUGH ANSWERS");
					}				}
				catch(ExceptionNotEnoughAnswersTest e){
					System.out.println(e.getMessage());
					break;
				}
				locationOfQuestion = checkRange(locationOfQuestion, theStock.getCurrentNumOfQuestion(), 1);
				pwe.print(i + 1 + ")" + theStock.getAQuestion(locationOfQuestion - 1).toStringQuestionWithOutCorrectAnswer() + "\n");
				pws.print(i + 1 + ")"
						+ theStock.getAQuestion(locationOfQuestion - 1).toStringQuestionWithAnswer() + "\n");
			}
		}
		pwe.close();
		pws.close();
	}
	private static int checkRange(int index, int maxRange, int minRange) {
		while (index > maxRange || index < minRange) {
			System.out.println("invalid num, enter a num between" + minRange + "-" + maxRange);
			index = s.nextInt();
		}
		return index;

	}
	private static boolean checkIfAmericanType(Question qs) {
		if (qs instanceof AmericanQuestion) {
			return true;
		}
		return false;
	} 
}
