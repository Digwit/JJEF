
public class Parser
{
	private Tokenizer myTokenizer;
	public Parser(Tokenizer t)
	{
		myTokenizer = t;
	}
	public void parseProgram()
	{
		parseStatements();
	}
	public void parseStatements()
	{
		if (myTokenizer.moreTokens())
		{
			parseStatement();
			if (myTokenizer.peekToken().getType() == Token.T_SEMICOLON)
				myTokenizer.getToken();
			else
				System.out.println("Expected a semicolon! :(");
			parseStatements();
	}
	}
	
	public void parseStatement()
	{
		if (myTokenizer.peekToken().getType() == Token.T_LOAD)
			parseLoadStatement();
		else if(myTokenizer.peekToken().getType() == Token.T_DELETE)
			parseDeleteStatement();
		else if(myTokenizer.peekToken().getType() == Token.T_KEEP)
			parseKeepStatement();
		/*
		else if(myTokenizer.peekToken().getType() == Token.T_PRINT)
			parsePrintStatement();
			*/
	}
	
	public void parseLoadStatement()
	{
		Token filename;
		Token variable;
		System.out.println(myTokenizer.peekToken());
		myTokenizer.getToken();
		System.out.println(myTokenizer.peekToken());
		if (myTokenizer.peekToken().getType() == Token.T_STRING)
			filename = myTokenizer.getToken();
		else
			System.out.println("You needed a filename.");
		
		System.out.println(myTokenizer.peekToken());
		if (myTokenizer.peekToken().getType() == Token.T_INTO)
			myTokenizer.getToken();
		else
			System.out.println("You need the 'INTO' keyword...");
		
		System.out.println(myTokenizer.peekToken());
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			System.out.println("You need a variable name for your table!");
		//Do something with variable and filename that loads csv file into table
	}
	
	public void parseDeleteStatement()
	{
		Token expression;
		Token variable;
		int columns; //1 means column, 0 means row
		myTokenizer.getToken(); //Deletes 'DELETE' from strong
		if (myTokenizer.peekToken().getType() == Token.T_RECORD)
		{
			myTokenizer.getToken();
			columns = 1;	
		}
		if (myTokenizer.peekToken().getType() == Token.T_FIELD)
		{
			myTokenizer.getToken();
			columns = 0;
		}
		else
			System.out.println("You need to specify if you want to delete record(s)(rows) or field(s)(columns)");
		
		if (myTokenizer.peekToken().getType() == Token.T_STRING)
			//Expression could be one of 3 options: integer; integer + "," + integer etc.; integer + "-" + integer
			expression = myTokenizer.getToken();
		else
			System.out.println("You need to specify what you want to delete.");
		if (myTokenizer.peekToken().getType() == Token.T_FROM)
			myTokenizer.getToken();
		else
			System.out.println("You need the keyword 'FROM' to specify which table.");
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			System.out.println("You need the name of the table you want to delete.");
		//Somehow check expression contents and store if correct, then delete accordingly
	}
	
	public  void parseKeepStatement()
	{
		Token expression;
		Token variable;
		int columns; //1 means column, 0 means row
		myTokenizer.getToken(); //Deletes 'DELETE' from strong
		if (myTokenizer.peekToken().getType() == Token.T_RECORD)
		{
			myTokenizer.getToken();
			columns = 1;	
		}
		if (myTokenizer.peekToken().getType() == Token.T_FIELD)
		{
			myTokenizer.getToken();
			columns = 0;
		}
		else
			System.out.println("You need to specify if you want to keep record(s)(rows) or field(s)(columns)");
		
		if (myTokenizer.peekToken().getType() == Token.T_STRING)
			//Expression could be one of 3 options: integer; integer + "," + integer etc.; integer + "-" + integer
			expression = myTokenizer.getToken();
		else
			System.out.println("You need to specify what you want to keep.");
		if (myTokenizer.peekToken().getType() == Token.T_FROM)
			myTokenizer.getToken();
		else
			System.out.println("You need the keyword 'FROM' to specify which table.");
		if (myTokenizer.peekToken().getType() == Token.T_VARIABLE)
			variable = myTokenizer.getToken();
		else
			System.out.println("You need the name of the table you want to manipulate.");
		//Somehow check expression contents and store if correct, then delete other records/fields accordingly
	}
	
	
	
	
	
	
	}
