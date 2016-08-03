
public class Parser
{
	public static void parseProgram(Tokenizer t)
	{
		parseStatements(t);
	}
	public static void parseStatements(Tokenizer t)
	{
		if (t.moreTokers())
		{
			parseStatement(t);
			if (t.peekToken().getType()== T_SEMICOLON)
				t.getToken();
			else
				System.out.println("Expected a semicolon! :(");
			parseStatements(t);
	}
	}
	
	public static void parseStatement(Tokenizer t)
	{
		if (t.peektoken().getType() == T_LOAD)
			parseLoadStatement(t);
		else if(t.peekToken().getType() == T_DELETE)
			parseDeleteStatement(t);
		else if(t.peekToken().getType() == T_KEEP)
			parseKeepStatement(t);
		else if(t.peekToken().getType() == T_PRINT)
			parsePrintStatement(t);
	}
	
	public static void parseLoadStatement(Tokenizer t)
	{
		Token filename;
		Token variable;
		t.gettoken();
		if (t.peekToken().getType() == T_STRING)
			filename = t.getToken();
		else
			System.out.println("You needed a filename.");
		if (t.peekToken().getType == T_INTO)
			t.gettoken();
		else
			System.out.println("You need the 'INTO' keyword...");
		if (t.peekToken().getType() == T_VARIABLE)
			variable = t.getToken();
		else
			System.out.println("You need a variable name for your table!");
		//Do somethihng with variable and filename that loads csv file into table
	}
	
	public static void parseDeleteStatement(Tokenizer t)
	{
		Token expression;
		Token variable;
		Int columns; //1 means column, 0 means row
		t.gettoken(); //Deletes 'DELETE' from strong
		if (t.peekToken().getType() == T_RECCORD)
		{
			t.getToken();
			columns = 1;	
		}
		if (t.peekTpken().getType() == T_FIELD)
		{
			t.gettoken();
			columns = 0;
		}
		else
			System.out.println("You need to specify if you want to delete record(s)(rows) or field(s)(columns)"";
		
		if (t.peekToken().getType == T_STRING)
			//Exression could be one of 3 options: integer; integer + "," + integer etc.; integer + "-" + integer
			expression = t.getToken();
		else
			System.out.println("You need to specify what you want to delete.");
		if (t.peekToken().getType() == T_FROM)
			t.getToken();
		else
			System.out.println("You need the keyword 'FROM' to specify which table.");
		if (t.peekToken().getType() == T_VARIABLE)
			variable = t.getToken();
		else
			System.out.println("You need the name of the table you want to delete.");
		//Somehow check expression contents and store if correct, then delete accordingly
	}
	
	public static void parseKeepStatement(Tokenizer t)
	{
		Token expression;
		Token variable;
		Int columns; //1 means column, 0 means row
		t.gettoken(); //Deletes 'DELETE' from strong
		if (t.peekToken().getType() == T_RECCORD)
		{
			t.getToken();
			columns = 1;	
		}
		if (t.peekTpken().getType() == T_FIELD)
		{
			t.gettoken();
			columns = 0;
		}
		else
			System.out.println("You need to specify if you want to keep record(s)(rows) or field(s)(columns)"";
		
		if (t.peekToken().getType == T_STRING)
			//Exression could be one of 3 options: integer; integer + "," + integer etc.; integer + "-" + integer
			expression = t.getToken();
		else
			System.out.println("You need to specify what you want to keep.");
		if (t.peekToken().getType() == T_FROM)
			t.getToken();
		else
			System.out.println("You need the keyword 'FROM' to specify which table.");
		if (t.peekToken().getType() == T_VARIABLE)
			variable = t.getToken();
		else
			System.out.println("You need the name of the table you want to manipulate.");
		//Somehow check expression contents and store if correct, then delete other records/fields accordingly
	}
	
	
	}
}
