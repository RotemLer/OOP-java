package project_Dvir_Siksik_Rotem_Ler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import project_Dvir_Siksik_Rotem_Ler.Question.eLevel;

public class Program   {

	public static Scanner s = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		Stock [] arrayStocks=new Stock[1];
		int stockIndex;
		arrayStocks=readStockArrray(arrayStocks); 
		stockIndex=chooseStock(arrayStocks);
		Stock st = arrayStocks[stockIndex];
		loadStock(st);
		// menu show stock and edit stock

		int inputNumAnswer;
		do {
			System.out.println("the current STOCK is: " +st.getStockName());
			System.out.println("choose one of the folowing: ");
			System.out.println("1. show the stock");
			System.out.println("2. add question to stock");
			System.out.println("3. add a new answer to question");
			System.out.println("4. remove question from stock");
			System.out.println("5. remove answer of question");
			System.out.println("6. creat a test");
			System.out.println("    ##########################    ");
			System.out.println("7. add subject of test");
			System.out.println("0. exit");

			inputNumAnswer = s.nextInt();

			switch (inputNumAnswer) {
			case 0:
				updateStock(st);
				saveArrayStocks(st, arrayStocks, stockIndex);
				System.out.println("GOOD BYE");
				break;
			case 1:
				System.out.println(st.toStringStockAndCorrectAnswers());
				break;
			case 2:
				creatNewQuestion(st);
				break;
			case 3:
				creatNewAnswer(st,getANum("to which question? ", st.getCurrentNumOfQuestion(), 1));
				break;
			case 4:
				st.removeQuestionByLocationFromStock(
						getANum("which number of question do you want to remove? ", st.getCurrentNumOfQuestion(), 1)- 1);
				break;
			case 5:
				removeAnswerFromQuestion(st , st.getAQuestion(getANum("from which number of question do you want to remove? ",
						st.getCurrentNumOfQuestion(), 1) - 1));
				break;
			case 6:
				chooseTypeOfTest(st);
				break;
			case 7:
				updateStockArray(arrayStocks,st);
				break;
			default:
				System.out.println("invalid option");
				break;
			}
		} while (inputNumAnswer != 0);

	}

	public static int getANum(String source, int maxRange, int minRange) {
		int number;
		System.out.println(source);
		number = s.nextInt();
		number=checkRange(number, maxRange, minRange);
		return number;

	}

	public static void creatNewQuestion(Stock theStock) {
		String text;
		int index;
		int indexENum;
		eLevel level=eLevel.Easy;
		System.out.println("what is the type of the question? for open question enter 1, \nfor american question enter any other num");
		index = s.nextInt();
		System.out.println("type a questuon: ");
		s.nextLine();
		text = s.nextLine();
		System.out.println("type a level of the question for MEDIUM enter 1, HIGH enter 2, EASY enter any other num ");
		indexENum = s.nextInt();
		switch(indexENum){
		case 1:
			level=	eLevel.Medium;
			break;
		case 2:
			level=	eLevel.High;
			break;
		default:

			break;
		}
		if(index==1) {
			Question newQ = new OpenQuestion(text , level,theStock);	
			theStock.addQuestionToStock(newQ);
			creatNewAnswer(theStock,theStock.getCurrentNumOfQuestion());

		}
		else {
			Question newQ = new AmericanQuestion (text , level,theStock);
			theStock.addQuestionToStock(newQ);
			System.out.println("how many answers do you wnat to add to this american question?");
			index = s.nextInt();
			if(index>0) {
				for (int i=0;i<index;i++) {
					creatNewAnswer(theStock, theStock.getCurrentNumOfQuestion());
				}
			}
		}

	}

	public static void creatNewAnswer(Stock theStock,int numOfQuestion) {
		String text;
		boolean isCorrect = false;
		int index; // first - using to know if add a new answer or take existing answer from stock.
		// second - point of location is Answers stock
		System.out.println(
				"to create new answer type 1," + "\nfor adding answer from an existnig answer click on other number ");
		index = s.nextInt();
		if(!checkIfAmericanType(theStock.getAQuestion(numOfQuestion - 1))) {
			System.out.println("**this is open question,adding anwer it's change the current answer."
					+ " \ndo you want to change? enter true for change or false to undo**");
			isCorrect=s.nextBoolean();
			if (!isCorrect) {
				return;
			}
		}
		else{
			AmericanQuestion theQuestion = (AmericanQuestion)theStock.getAQuestion(numOfQuestion - 1);
			if (theQuestion.getTotalAnswers() == 10) {
				System.out.println("you have reached to the max answers in this question");
			} 
		}
		if (index == 1) {
			System.out.println("type a answer ");
			s.nextLine();
			text = s.nextLine();
			Answer newA = new Answer(text);
			theStock.addAnswersToStock(newA);
			index = theStock.getCurrentNumOfAnswers();
		}

		else {
			if(theStock.getCurrentNumOfAnswers()==0) {
				System.out.println("***there are no answers in the stock***");
			}
			else {
				System.out.println(theStock.toStringAnswersStock());
				System.out.println("which answer do you want to add?");
				index = s.nextInt();
				index = checkRange(index, theStock.getCurrentNumOfAnswers(), 1);
			}
		}

		if (checkIfAmericanType(theStock.getAQuestion(numOfQuestion - 1))) {
			System.out.println("this answer is correct (type true or false)? ");
			isCorrect = s.nextBoolean();
			Question theQuestion = theStock.getAQuestion(numOfQuestion - 1);
			theQuestion.setAnswersFormStock(theStock, index - 1, isCorrect);
		}
		else {
			Question theQuestion = theStock.getAQuestion(numOfQuestion - 1);
			theQuestion.setAnswersFormStock(theStock, index - 1, true);
		}
	}



	public static int checkRange(int index, int maxRange, int minRange) {
		while (index > maxRange || index < minRange) {
			System.out.println("invalid num, enter a num between" + minRange + "-" + maxRange);
			index = s.nextInt();
		}
		return index;

	}

	public static boolean checkIfAmericanType(Question qs) {
		if (qs instanceof AmericanQuestion) {
			return true;
		}
		return false;
	} 

	public static void removeAnswerFromQuestion(Stock st ,Question qs) {
		if (checkIfAmericanType(qs)) {
			((AmericanQuestion)qs).removeAnswersByLocationFromQuestion(st,getANum("which number of answer do you want to remove? ", 10, 3)-1 );
		}
		else {
			System.out.println("**you can't remove answer from open question** ");
		}

	}
	public static Stock[]updateStockArray(Stock[] arrayStocks,Stock st) throws FileNotFoundException, IOException {
		String subjectName;
		Stock tempArrayStocks [];
		System.out.println("type the subject name: ");
		s.nextLine();
		subjectName=s.nextLine();
		Stock  newStock =new Stock (subjectName);
		tempArrayStocks=new Stock [arrayStocks.length+1];
		System.arraycopy(arrayStocks, 0, tempArrayStocks, 0, arrayStocks.length);
		tempArrayStocks[arrayStocks.length] = newStock;
		st.recoverStock();
		createBinaryArrayStock(tempArrayStocks);
		updateStock(newStock);
		return tempArrayStocks;
	}
	public static int chooseStock(Stock [] arrayStocks ) throws FileNotFoundException, ClassNotFoundException, IOException {
		int index;

		System.out.println("which stock do you want to upload?");
		for (int i=0;i<arrayStocks.length;i++ ) {
			if(arrayStocks[i]!=null) {
				System.out.println((i+1)+")"+arrayStocks[i].getStockName()+ "\n");
			}
		}
		index=s.nextInt();
		index=checkRange(index, arrayStocks.length, 1);
		return index-1;

	}
	public static void createBinaryArrayStock(Stock [] arrayStocks) throws FileNotFoundException, IOException {
		ObjectOutputStream outStocksarrayFile= new ObjectOutputStream(new FileOutputStream("All stock array.dat"));
		outStocksarrayFile.writeObject(arrayStocks);
		outStocksarrayFile.close();

	}
	public static Stock[] readStockArrray(Stock[] arrayStocks) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStocksarrayFile = new ObjectInputStream(new FileInputStream("All stock array.dat"));
		arrayStocks = (Stock[]) inStocksarrayFile.readObject();
		inStocksarrayFile.close();
		return arrayStocks;
	}
	public static void chooseTypeOfTest(Stock st) throws Exception {
		int numQ; 
		int num;  
		if(st.getCurrentNumOfQuestion()==0) {
			System.out.println("there are 0 questions you cant create exam, please add question first ");
		}
		else {
			System.out.println("for auto exam enter 1 for manual exam emter any other num");
			num= s.nextInt();
			try {
				System.out.println("how many question do you want?");
				numQ=s.nextInt();
				if (numQ > 10) {
					throw new ExceptionMaxNumQuestions("\n#### MAX QUESTIONS FOR TEST IS 10 ####\n");
				}

				if(num ==1) {
					Exam autoExam= new AutoumaticExam(st,numQ,"auto");
					if (autoExam instanceof Examable) {
						autoExam.creatExam(st);
					}
				}
				else {
					Exam manuExam= new ManualExam(st,numQ,"manual");
					if (manuExam instanceof Examable) {
						manuExam.creatExam(st);
					}
				}
			}
			catch(ExceptionMaxNumQuestions e){
				System.out.println(e.getMessage());
			} 
		}
	}
	public static void saveArrayStocks(Stock st,Stock [] arrayStocks,int index ) throws FileNotFoundException, IOException {
		arrayStocks[index]=st;
		createBinaryArrayStock(arrayStocks);
	}
	public static void updateStock(Stock st) throws FileNotFoundException, IOException {
		ObjectOutputStream outStockFile= new ObjectOutputStream(new FileOutputStream(st.getStockName()+".dat"));

		outStockFile.writeObject(st);
		outStockFile.close(); 
	}
	public static Stock loadStock(Stock st) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStockFile = new ObjectInputStream(new FileInputStream(st.getStockName()+".dat"));
		st=(Stock)inStockFile.readObject();
		inStockFile.close();
		return st;
	}
}
