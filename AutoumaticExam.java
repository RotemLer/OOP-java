package project_Dvir_Siksik_Rotem_Ler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class  AutoumaticExam extends Exam implements Examable{
	public static Scanner s = new Scanner(System.in);
	//members:
	private boolean chosenQuestions[]; 
	private Answer [] chosenAnswersForQuestion;
	private int currntQuestion;
	private int theCorrectAnswerLocation;
	private final Answer defultAnswer;
	private File fExam;
	private File fSolution;
	private PrintWriter pwe;
	private PrintWriter pws;
	//constructor:
	public AutoumaticExam(Stock st, int numQ,String type) throws FileNotFoundException {
		super(st,numQ,type);
		defultAnswer= new Answer ("None answers is correct");
		chosenQuestions = new boolean [st.getCurrentNumOfQuestion()];
	}
	//other methods
	@Override
	public void creatExam(Stock theStock) throws Exception {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		fExam = new File(theStock.getStockName()+" exam_(auto)_" + ldt.format(dtf)+".txt");
		fSolution = new File(theStock.getStockName()+" solution_(auto)" + ldt.format(dtf)+".txt");
		pwe = new PrintWriter(fExam);
		pws = new PrintWriter(fSolution);
		pwe.print("new (auto) exam " + ldt.format(dtf) + " \n");
		pws.print(" the (auto) solutions: " + ldt.format(dtf) + "\n");
		for (int i = 0 ; i< numOfQuestions ; i++) {
			chosenAnswersForQuestion = new Answer[5];
			currntQuestion =randomQuestion(theStock);
			pwe.print("\n"+(i + 1) + ")" + toStringQuestionForAutoTest());
			pws.print("\n"+(i + 1) + ")" + toStringQuestionForSolution());

		}
		pwe.close();
		pws.close();
	}
	private static int checkRange(int maxRange, int minRange, int numQ) {
		int index=(int)(Math.random()*numQ);

		while (index < minRange) {
			index=(int)(Math.random()*numQ);
		}
		return (int)index;

	}
	private static boolean checkIfAmericanType(Question qs) {
		if (qs instanceof AmericanQuestion) {
			return true;
		}
		return false;
	} 
	private int randomQuestion(Stock st) throws Exception{
		boolean isOk=false;
		int random;
		random = checkRange(st.getCurrentNumOfQuestion(), 0, st.getCurrentNumOfQuestion());
		while (!isOk) {
			Question qs = st.getAQuestion(random);
			isOk = false;
			if(!chosenQuestions[random]) {
				if (!(checkIfAmericanType(qs))) {
					isOk=true; 
				}
				else{ 
					AmericanQuestion aqs =(AmericanQuestion)qs;
					try {
						if (aqs.getTotalAnswers() <= 4 ) {
							throw new Exception("THERE ARE NOT ENOUGH ANSWERS");
						}
						if (((aqs.getTotalAnswers()-2)-(aqs.getCountCorrectAnswer()-1)>=4)) {
							randomAnswer(st, aqs);
							isOk=true;
						}
					}
					catch(ExceptionNotEnoughAnswersTest e){
						System.out.println(e.getMessage());
						break;
					}
				}
				if(!isOk) {
					random = checkRange(st.getCurrentNumOfQuestion(), 0,st.getCurrentNumOfQuestion());
				}
			}
			else {
				random = checkRange(st.getCurrentNumOfQuestion(), 0,st.getCurrentNumOfQuestion());
			}
		}
		chosenQuestions[random]=true;
		return random;
	}
	private void randomAnswer(Stock st,AmericanQuestion quest) {
		int location;
		boolean noAnswerIsCorrect=true; 
		chosenAnswersForQuestion[0]=defultAnswer;
		for (int i = 1 ; i<5; i++) {
			boolean ok = false;
			while (!ok) {
				location=checkRange(quest.getTotalAnswers()-1, 2,quest.getTotalAnswers());
				//check if the answer is not exist in the chosen answers:
				if (!checkIfExist(quest, location)) {//the answer is not in the chosen answer array
					if(noAnswerIsCorrect) {//no answer is correct
						if(quest.getIfAnswerCorrect(location)) {//the answer is correct
							theCorrectAnswerLocation=i;
							noAnswerIsCorrect=false;
							chosenAnswersForQuestion[i]=quest.getAnswer(location);
							ok = true;
						}
						else {
							chosenAnswersForQuestion[i]=quest.getAnswer(location);
							ok = true;
						}
					}
					else {//if there is correct answer
						if (!quest.getIfAnswerCorrect(location)) {//the answer is not correct
							chosenAnswersForQuestion[i]=quest.getAnswer(location);
							ok = true;
						}
					}

				}

			} 
		}

	}

	private boolean checkIfExist(AmericanQuestion qs , int location) {
		for (int i=1;i<5;i++)
		{
			if (chosenAnswersForQuestion[i]!=null)
				if(chosenAnswersForQuestion[i].equals(qs.getAnswer(location))) {
					return true;
				}
		} 
		return false;
	}

	//to String:
	public String toStringQuestionForAutoTest() {
		Question qs = theStock.getAQuestion(currntQuestion);
		StringBuffer data = new StringBuffer(qs.textQuestion+"\n");
		if (checkIfAmericanType(qs)) {
			for (int i = 0 ; i<5 ; i++) {
				data.append(chosenAnswersForQuestion[i].toStringAnswer()+"\n");
			}
		}
		return data.toString();
	}

	public String toStringQuestionForSolution() {
		Question qs = theStock.getAQuestion(currntQuestion);
		StringBuffer data = new StringBuffer("The serial number is: "+qs.serialNumber+") "+qs.textQuestion+"\n");
		if (checkIfAmericanType(qs)) {
			for (int i = 0 ; i<5 ; i++) {
				if(i==theCorrectAnswerLocation) {
					data.append(chosenAnswersForQuestion[i].toStringAnswer()+"(true)\n");

				}
				data.append(chosenAnswersForQuestion[i].toStringAnswer()+"\n");
			}
		}
		else {
			OpenQuestion oqs = (OpenQuestion)qs;
			data.append(oqs.getAnswers().toStringAnswer()+"\n");
		}

		return data.toString();
	}	

}
