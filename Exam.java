package project_Dvir_Siksik_Rotem_Ler;

import java.io.File;
import java.io.FileNotFoundException;


public abstract class Exam implements Examable{
//members
	protected static int numOfQuestions;
	protected File fExam;
	protected File fSolution;
	protected int locationOfQuestion;
	protected Stock theStock;
//Constructor
	public Exam(Stock st, int numQ, String type) throws FileNotFoundException {
		try {
			exceptionTest(numQ);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		numOfQuestions=numQ;
			locationOfQuestion = 0;
			theStock=st;
		
		
	}
	//other methods
	public abstract void creatExam(Stock st) throws FileNotFoundException, Exception;

	private static void exceptionTest(int index)throws Exception {
		if (index>10) {
			throw new Exception("MAX QUESTIONS FOR TEST IS 10");
			
		}

	}




}
