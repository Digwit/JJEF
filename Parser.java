import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		else if (myTokenizer.peekToken().getType() == Token.T_SAVE)
			parseSaveStatement();
		else if (myTokenizer.peekToken().getType() == Token.T_PRINT)
			parsePrintStatement();

	}

	public void parseLoadStatement() {
		Token filename = null;
		Token variable = null;

		myTokenizer.getToken();

		if (myTokenizer.peekToken().getType() == Token.T_STRING) {
			String temp = myTokenizer.getToken().getValue();
			temp = temp.substring(1);
			temp = temp.substring(0, temp.length() - 1);
			filename = new Token(temp, Token.T_STRING);
		} else
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

	public void parseDeleteStatement() {
		Token expression = null;
		Token variable = null;
		List<Integer> expressions = null;
		int range = 0;

		int columns = -1; // 1 means column, 0 means row
		myTokenizer.getToken(); // Deletes 'DELETE' from string

		if (myTokenizer.peekToken().getType() == Token.T_RECORD) {
			myTokenizer.getToken();
			columns = 0;
		} else if (myTokenizer.peekToken().getType() == Token.T_FIELD) {
			myTokenizer.getToken();
			columns = 1;
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
						expression = myTokenizer.getToken();
					else
						parseError("You need to specify what you want to delete.");
					expressions.add(Integer.parseInt(expression.getValue()));
				}
			}
			else if (myTokenizer.peekToken().getType() == Token.T_TO) {
				expressions = new ArrayList<Integer>();
				expressions.add(Integer.parseInt(expression.getValue()));
				myTokenizer.getToken();
				range = 1;
				expressions.add(Integer.parseInt(expression.getValue()));
				if (myTokenizer.peekToken().getType() == Token.T_NUMBER)
				{
					expression = myTokenizer.getToken();
				}
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
		} else if (columns == 3) {
			if (range == 0) {
				int deleted = 0;
				Collections.sort(expressions);

				for (Integer e : expressions) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), e - deleted));
					deleted++;
				}
			} else if (range == 1) {
				// Assumes user inputs something sensible like '5 TO 11'
				for (int k = expressions.get(1); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), k));
				}
			}
		} else if (columns == 2) {
			if (range == 0) {
				int deleted = 0;
				Collections.sort(expressions);

				for (Integer e : expressions) {
					tableVariables.put(variable.getValue(),
							Table.deleteColumn(tableVariables.get(variable.getValue()), e - deleted));
					deleted++;
				}
			} else if (range == 1) {
				// Assumes user inputs something sensible like '5 TO 11'
				for (int k = expressions.get(1); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteColumn(tableVariables.get(variable.getValue()), k));
				}
			}
		}
	}

	public void parseKeepStatement() {
		Token expression = null;
		Token variable = null;
		List<Integer> expressions = null;
		int columns = -1; // 1 means column, 0 means row
		int range = 0;

		myTokenizer.getToken(); // Deletes 'KEEP' from strong
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
			parseError("You need to specify if you want to keep record(s)(rows) or field(s)(columns)");

		if (myTokenizer.peekToken().getType() == Token.T_NUMBER)
			expression = myTokenizer.getToken();
		else
			parseError("You need to specify what you want to keep.");

		if ((columns == 3) || (columns == 2))
			if (myTokenizer.peekToken().getType() == Token.T_COMMA) {
				expressions = new ArrayList<Integer>();
				expressions.add(Integer.parseInt(expression.getValue()));
				while (myTokenizer.peekToken().getType() == Token.T_COMMA) {
					myTokenizer.getToken();
					if (myTokenizer.peekToken().getType() == Token.T_NUMBER)
						expression = myTokenizer.getToken();
					else
						parseError("You need to specify what you want to keep.");
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
				parseError("You need to specify the upper bound of thte records you want to keep.");
			expressions.add(Integer.parseInt(expression.getValue()));
		}

		if (myTokenizer.peekToken().getType() == Token.T_FROM)
			myTokenizer.getToken();
		else
			parseError("You need the keyword 'FROM' to specify which table.");
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			parseError("You need the name of the table you want to manipulate.");

		if (columns == 0) {
			tableVariables.put(variable.getValue(), Table.deleteColumn(tableVariables.get(variable.getValue()),
					Integer.parseInt(expression.getValue())));
		} else if (columns == 1) {
			tableVariables.put(variable.getValue(),
					Table.deleteRow(tableVariables.get(variable.getValue()), Integer.parseInt(expression.getValue())));
		} else if (columns == 3) {
			if (range == 0) {
				Collections.sort(expressions);
				Collections.reverse(expressions);

				// From end to largest entry, stored at expressions.get(0)
				// #CHECK
				for (int k = tableVariables.get(variable).getRows(); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), k));
				}
				// From each entry to the next highest one
				for (int k = 0; k < expressions.size(); k++) {
					for (int i = expressions.get(k) - 1; i > expressions.get(k + 1); i--) {
						tableVariables.put(variable.getValue(),
								Table.deleteRow(tableVariables.get(variable.getValue()), i));
					}

				}
			} else if (range == 1) {
				Collections.sort(expressions);
				Collections.reverse(expressions);
				for (int k = tableVariables.get(variable.getValue()).getRows(); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), k));
				}
				for (int k = expressions.get(1) - 1; k >= 0; k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteRow(tableVariables.get(variable.getValue()), k));
				}
			}
		} else if (columns == 2) {
			if (range == 0) {
				Collections.sort(expressions);
				Collections.reverse(expressions);

				// From end to largest entry, stored at expressions.get(0)
				// #CHECK
				for (int k = tableVariables.get(variable).getCols(); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteColumn(tableVariables.get(variable.getValue()), k));
				}
				// From each entry to the next highest one
				for (int k = 0; k < expressions.size(); k++) {
					for (int i = expressions.get(k) - 1; i > expressions.get(k + 1); i--) {
						tableVariables.put(variable.getValue(),
								Table.deleteColumn(tableVariables.get(variable.getValue()), i));
					}

				}
			} else if (range == 1) {
				Collections.sort(expressions);
				Collections.reverse(expressions);
				for (int k = tableVariables.get(variable.getValue()).getCols(); k > expressions.get(0); k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteColumn(tableVariables.get(variable.getValue()), k));
				}
				for (int k = expressions.get(1) - 1; k >= 0; k--) {
					tableVariables.put(variable.getValue(),
							Table.deleteColumn(tableVariables.get(variable.getValue()), k));
				}
			}
		}

	}

	////////////////////////////////////////////////////////////////////////

	public void parseSaveStatement() {
		String filename = null;
		Token variable = null;
		myTokenizer.getToken(); // DELETES 'SAVE'
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE) {
			variable = myTokenizer.getToken();
			System.out.println(variable);
		} else {
			parseError("You need to specify which variable to save.");
		}
		if (myTokenizer.peekToken().getType() == Token.T_INTO) {
			myTokenizer.getToken();
		} else {
			parseError("You need the 'INTO' keyword.");
		}
		if (myTokenizer.peekToken().getType() == Token.T_STRING) {
			String temp = myTokenizer.getToken().getValue();
			temp = temp.substring(1);
			temp = temp.substring(0, temp.length() - 1);
			filename = temp; // DELETING THE '/'
		} else {
			parseError("U need a filename.");
		}

		String data = "";
		data = Table.save(tableVariables.get(variable.getValue()));
		
		 try
	     {
	            Table.writeFile("dummy.txt", "Here's a file.\nWith multiple lines.\nThree to be exact.");
	     }
	     catch (IOException e)
	     {
	      System.out.println("Error: Couldn't save file");
	     }
		 
//		try {
//			Table.writeFile(filename, data);
//		} catch (IOException e) {
//			parseError("Dunno what this does.");
//			e.printStackTrace();
//		}

	}

	public void parsePrintStatement() {
		String column = "";
		String variable = null;

		myTokenizer.getToken(); // Deletes 'PRINT' from string

		if (myTokenizer.peekToken().getType() == Token.T_STRING) {
			column = myTokenizer.getToken().getValue();
			column = column.substring(1);
			column = column.substring(0, column.length() - 1) ; //maybe -2 + \"?
		} else {
			parseError("You need to specify which column you which to get information from.");
		}

		if (myTokenizer.peekToken().getType() == Token.T_FROM) {
			myTokenizer.getToken();
		} else {
			parseError("You must use the keyword 'FROM' ");
		}
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE) {
			variable = myTokenizer.getToken().getValue();
		} else {
			parseError("You need to specify which table you this column is in.");
		}

		tableVariables.get(variable).printInfo(column);

	}

	//////////////////////////////////////////////////////////////
	public void parseError(String message) {
		System.out.println(message);
		System.exit(1);
	}

}
