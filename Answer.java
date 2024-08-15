package project_Dvir_Siksik_Rotem_Ler;

import java.io.Serializable;

public class Answer implements Serializable  {
		// member variables:

		private static final long serialVersionUID = -1049306765293863181L;
		private String textAnswers;
		// Constructor:
		public Answer(String textAnswers) {
			setTextAnswers(textAnswers);

		}
		// getters and setters:
		public boolean setTextAnswers(String text) {
			if (text != null) {
				textAnswers = text;

				return true;
			}
			return false;
		}
		// other methods:

		public String toStringAnswer() {

			return textAnswers;

		}

	}

