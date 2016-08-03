
public class parser1
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
		Token 
		t.gettoken(); //Doesn't this return somethihng? Is this valid even?
		if (t.peekToken().getType() == T_STRING)
			filename = t.getToken();
		else
			System.out.println("You needed a filename.");
		if (t.peekToken().getType == T_INTO)
			t.getToken();
		else
			System.out.println("You need the 'INTO' keyword...");
		if (t.peekToken().getType() == T_VARIABLE)
			variable = t.getToken();
		else
			System.out.println("You need a variable name for your table!");
		//Do somethihng with variable and filename that loads csv file into table
	}
	
	
	}
}
