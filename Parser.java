import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
	private Tokenizer myTokenizer;
	private Map<String, Table> tableVariables;

	public Parser(Tokenizer t) {
		tableVariables = new HashMap<String, Table>();
		myTokenizer = t;
	}

	public void parseProgram() {
		parseStatements();
	}

	public void parseStatements() {
		if (myTokenizer.moreTokens()) {
			parseStatement();
			if (myTokenizer.peekToken().getType() == Token.T_SEMICOLON)
				myTokenizer.getToken();
			else
				parseError("Expected a semicolon! :(");
			parseStatements();
		}
	}

	public void parseStatement() {
		if (myTokenizer.peekToken().getType() == Token.T_LOAD)
			parseLoadStatement();
		else if (myTokenizer.peekToken().getType() == Token.T_DELETE)
			parseDeleteStatement();
		else if (myTokenizer.peekToken().getType() == Token.T_KEEP)
			parseKeepStatement();
		/*
		 * else if(myTokenizer.peekToken().getType() == Token.T_PRINT)
		 * parsePrintStatement();
		 */
	}

	public void parseLoadStatement() {
		Token filename = null;
		Token variable = null;

		myTokenizer.getToken();

		if (myTokenizer.peekToken().getType() == Token.T_STRING)
		{
			String temp = myTokenizer.getToken().getValue();
			temp = temp.substring(1);
			temp = temp.substring(0, temp.length() -1);
			filename = new Token(temp, Token.T_STRING);
		}
		else
			parseError("You needed a filename.");

		if (myTokenizer.peekToken().getType() == Token.T_INTO)
			myTokenizer.getToken();
		else
			parseError("You need the 'INTO' keyword...");

		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			parseError("You need a variable name for your table!");

		Table table = new Table(Table.convert(filename.getValue()));
		tableVariables.put(variable.getValue(), table);
	}

	public void parseDeleteStatement() 
	{
		Token expression = null;
		Token variable = null;
		List<Integer> expressions = null;
		int range = 0;

		int columns = -1; // 1 means column, 0 means row
		myTokenizer.getToken(); // Deletes 'DELETE' from strong
		if (myTokenizer.peekToken().getType() == Token.T_RECORD) {
			myTokenizer.getToken();
			columns = 1;
		} else if (myTokenizer.peekToken().getType() == Token.T_FIELD) {
			myTokenizer.getToken();
			columns = 0;
		} else if (myTokenizer.peekToken().getType() == Token.T_RECORDS) {
			myTokenizer.getToken();
			columns = 3;
		} else if (myTokenizer.peekToken().getType() == Token.T_FIELDS) {
			myTokenizer.getToken();
			columns = 2;
		} else
			parseError("You need to specify if you want to delete record(s)(rows) or field(s)(columns)");

		if (myTokenizer.peekToken().getType() == Token.T_NUMBER)
			// Expression could be one of 3 options: integer; integer + "," +
			// integer etc.; integer + "-" + integer
			expression = myTokenizer.getToken();
		else
			parseError("You need to specify what you want to delete.");

		if ((columns == 3) || (columns == 2)) {
			if (myTokenizer.peekToken().getType() == Token.T_COMMA) {
				expressions = new ArrayList<Integer>();
				expressions.add(Integer.parseInt(expression.getValue()));

				while (myTokenizer.peekToken().getType() == Token.T_COMMA) {
					myTokenizer.getToken();
					if (myTokenizer.peekToken().getType() == Token.T_NUMBER)
						// Expression could be one of 3 options: integer;
						// integer + "," + integer etc.; integer + "-" + integer
						expression = myTokenizer.getToken();
					else
						parseError("You need to specify what you want to delete.");
					expressions.add(Integer.parseInt(expression.getValue()));
				}
			}
			if (myTokenizer.peekToken().getType() == Token.T_TO) {
				myTokenizer.getToken();
				range = 1;
				expressions.add(Integer.parseInt(expression.getValue()));
				if (myTokenizer.peekToken().getType() == Token.T_NUMBER)
					expression = myTokenizer.getToken();
				else
					parseError("You need to specify the upper bound of the RECORDS you want to delete.");
				expressions.add(Integer.parseInt(expression.getValue()));
			}
		}

		if (myTokenizer.peekToken().getType() == Token.T_FROM)
			myTokenizer.getToken();
		else
			parseError("You need the keyword 'FROM' to specify which table.");
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			parseError("You need the name of the table you want to delete.");

		if (columns == 0) {
			tableVariables.put(variable.getValue(),
					Table.deleteRow(tableVariables.get(variable.getValue()), Integer.parseInt(expression.getValue())));
		} else if (columns == 1) {
			tableVariables.put(variable.getValue(), Table.deleteColumn(tableVariables.get(variable.getValue()),
					Integer.parseInt(expression.getValue())));
		} else if (columns == 2) {
			if (range == 0) {
				int deleted = 0;
				for (Integer e : expressions) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), e - deleted));
				}
			} else if (range == 1) {
				for (int k = expressions.get(1); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), k));
				}
			}
		}
	}

	public void parseKeepStatement() {
		Token expression = null;
		Token variable = null;
		int columns = -1; // 1 means column, 0 means row
		myTokenizer.getToken(); // Deletes 'DELETE' from strong
		if (myTokenizer.peekToken().getType() == Token.T_RECORD) {
			myTokenizer.getToken();
			columns = 1;
		}
		if (myTokenizer.peekToken().getType() == Token.T_FIELD) {
			myTokenizer.getToken();
			columns = 0;
		} else
			parseError("You need to specify if you want to keep record(s)(rows) or field(s)(columns)");

		if (myTokenizer.peekToken().getType() == Token.T_STRING)
			// Expression could be one of 3 options: integer; integer + "," +
			// integer etc.; integer + "-" + integer
			expression = myTokenizer.getToken();
		else
			parseError("You need to specify what you want to keep.");
		if (myTokenizer.peekToken().getType() == Token.T_FROM)
			myTokenizer.getToken();
		else
			parseError("You need the keyword 'FROM' to specify which table.");
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			parseError("You need the name of the table you want to manipulate.");
	}

	public void parseError(String message) {
		System.out.println(message);
		System.exit(1);
	}

}
