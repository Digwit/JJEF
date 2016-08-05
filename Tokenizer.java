//Class: Tokenizer
//Authors: Erica, Jocelyn, Fanbo
//Date finished: 8/4/16
//Description:

public class Tokenizer {
	private String myProgram;

	// constructor
	public Tokenizer(String program) {
		myProgram = program;
	}

	public Token getToken() {

		if ((myProgram.substring(0, 1).equals(" ")) || (myProgram.substring(0, 1).equals("/n"))) {
			myProgram = myProgram.substring(1);
			return getToken(); // This is the recursive bit
		} else {
			String s = myProgram.substring(0, myProgram.indexOf(" "));
			myProgram = myProgram.substring(myProgram.indexOf(" "));
			int t = 0;
			if (s.toUpperCase().equals("LOAD"))
				t = Token.T_LOAD;
			else if (s.toUpperCase().equals("INTO"))
				t = Token.T_INTO;
			else if (s.toUpperCase().equals("DELETE"))
				t = Token.T_DELETE;
			else if (s.toUpperCase().equals("KEEP"))
				t = Token.T_KEEP;
			else if (s.toUpperCase().equals("FROM"))
				t = Token.T_FROM;
			else if (s.toUpperCase().equals("RECORD"))
				t = Token.T_RECORD;
			else if (s.toUpperCase().equals("RECORDS"))
				t = Token.T_RECORDS;
			else if (s.toUpperCase().equals("FIELDS"))
				t = Token.T_FIELDS;
			else if (s.toUpperCase().equals("SAVE"))
				t = Token.T_SAVE;
			else if (s.toUpperCase().equals("PRINT"))
				t = Token.T_PRINT;
			else if (s.toUpperCase().equals("TO"))
				t = Token.T_TO;
			else if (s.toUpperCase().equals("WHERE"))
				t = Token.T_WHERE;
			else if (s.toUpperCase().equals(","))
				t = Token.T_COMMA;
			else if (s.substring(0, 1).equals("\"") && (s.substring(s.length() - 1, s.length()).equals("\"")))
				t = Token.T_STRING;

			else if (s.equals(";"))
				t = Token.T_SEMICOLON;

			else if (isVariable(s))
				t = Token.T_VARIABLE;
			else if (isNumber(s))
				t = Token.T_NUMBER;
			else
				System.out.println("Check to make sure you put a space before the ';' \n Check that you didn't put commas after variables \n Check that your filename is set off by quotation marks.");

			return new Token(s, t);
		}

	}

	private boolean isNumber(String s) {
		for (int k = 0; k < s.length(); k++) {
			if (!Character.isDigit(s.charAt(k)))
				return false;
		}
		return true;
	}

	private boolean isVariable(String s) {
		for (int k = 0; k < s.length(); k++) {
			if (!Character.isLetter(s.charAt(0)))
				return false;
			if (!Character.isLetterOrDigit(s.charAt(k)))
				return false;
		}
		return true;
	}

	public Token peekToken() {
		if ((myProgram.substring(0, 1).equals(" ")) || (myProgram.substring(0, 1).equals("/n"))) {
			myProgram = myProgram.substring(1);
			return peekToken(); // This is the recursive bit
		} else {
			String s = myProgram.substring(0, myProgram.indexOf(" "));
			int t = 0;
			if (s.toUpperCase().equals("LOAD"))
				t = Token.T_LOAD;
			else if (s.toUpperCase().equals("INTO"))
				t = Token.T_INTO;
			else if (s.toUpperCase().equals("DELETE"))
				t = Token.T_DELETE;
			else if (s.toUpperCase().equals("KEEP"))
				t = Token.T_KEEP;
			else if (s.toUpperCase().equals("FROM"))
				t = Token.T_FROM;
			else if (s.toUpperCase().equals("RECORD"))
				t = Token.T_RECORD;
			else if (s.toUpperCase().equals("FIELD"))
				t = Token.T_FIELD;
			else if (s.toUpperCase().equals("RECORDS"))
				t = Token.T_RECORDS;
			else if (s.toUpperCase().equals("FIELDS"))
				t = Token.T_FIELDS;
			else if (s.toUpperCase().equals("SAVE"))
				t = Token.T_SAVE;
			else if (s.toUpperCase().equals("TO"))
				t = Token.T_TO;
			else if (s.substring(0, 1).equals(","))
				t = Token.T_COMMA;
			else if (s.toUpperCase().equals("WHERE"))
				t = Token.T_WHERE;
			else if (s.toUpperCase().equals("PRINT"))
				t = Token.T_PRINT;
			else if (s.substring(0, 1).equals("\"") && (s.substring(s.length() - 1, s.length()).equals("\"")))
				t = Token.T_STRING;

			else if (s.equals(";"))
				t = Token.T_SEMICOLON;

			else if (isVariable(s))
				t = Token.T_VARIABLE;
			else if (isNumber(s))
				t = Token.T_NUMBER;
			else
				System.out.println(
						"Check to make sure you put a space before the ';' \n Check that you didn't put commas after variables \n Check that your filename is set off by quotation marks.");

			return new Token(s, t);
		}
	}

	public boolean moreTokens()

	{
		if (myProgram.equals(""))
			return false;
		else {
			for (int k = 0; k < myProgram.length(); k++) {
				if (!myProgram.substring(k, k + 1).equals(" ") && !myProgram.substring(k, k + 1).equals("\n"))
					return true;
			}
		}
		return false;
	}
}
