//Class: Token
//Authors: Erica
//Date finished: 8/4/16
//Description:

public class Token {
	public static final int T_LOAD = 1;
	public static final int T_INTO = 2;
	public static final int T_DELETE = 3;
	public static final int T_KEEP = 4;
	public static final int T_FROM = 5;
	public static final int T_RECORD = 6;
	public static final int T_FIELD = 7;
	public static final int T_WHERE = 8;
	public static final int T_STRING = 9;
	public static final int T_NUMBER = 10;
	public static final int T_VARIABLE = 11;
	public static final int T_SEMICOLON = 12;
	public static final int T_RECORDS = 13;
	public static final int T_FIELDS = 14;
	public static final int T_COMMA = 15;
	public static final int T_TO = 16;
	public static final int T_SAVE = 17;
	public static final int T_PRINT = 18;
	/*
	 * public static final int T_("-") = 13; public static final int T_(",") =
	 * 14; public static final int T_("=") = 15; public static final int T_(">")
	 * = 16; public static final int T_("<") = 17; public static final int
	 * T_(">=") = 18; public static final int T_("<=") = 19; public static final
	 * int T_("+") = 20; public static final int T_("*") = 21; public static
	 * final int T_("/") = 22;
	 */

	private String myValue;
	private int myType;

	public Token(String value, int type) {
		myValue = value;
		myType = type;
	}

	public String getValue() {
		return myValue;
	}

	public int getType() // Not sure how this works because in Parser I use this
							// with T_SEMICOLON, not just integers
	{
		return myType;
	}

	public String toString() {
		return myValue + "[" + myType + "]";
	}

}
