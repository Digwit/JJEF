public class Tokenizer 
{
  private String myProgram;
  
  // constructor 
  public Tokenizer(String program)
  {
    myProgram = program;
  }
  
  public Token getToken()
  {
    
    if(  (myProgram.substring(0,1) == " ") || (myProgram.substring(0,1) == "/n")  )
    {
      myProgram = myProgram.substring(1);
      return getToken(); //This is the recursive bit
    }
    else
    {
      String s = myProgram.substring(0, myProgram.indexOf(" ") - 1); //Should this be -1?
      myProgram = myProgram.substring(myProgram.indexOf(" ")); 
      //3 (below) is temporary int; we will make a numbered chart of tokens
      return new Token (s, 3);
    }
  }
  
   public Token peekToken()
  {
    
    if(  (myProgram.substring(0,1) == " ") || (myProgram.substring(0,1) == "/n")  )
    {
      myProgram = myProgram.substring(1);
      return peekToken();
    }
    else
    {
      return new Token (myProgram.substring(0, myProgram.indexOf(" ") - 1), 3);
    }
  }
  
  public boolean moreTokens() //Not sure if this works. What does peekToken() return if there are no more tokens?
  {
    if (myProgram == null)
      return false;
    else
    {
    for (int k = 0; k < myProgram.length() - 1; k++)
    {
      if (!myProgram.substring(k, k+1).equals(" ") && !myProgram.substring(k, k+1).equals("\n")  )
        return true;
    }
    }
    return false;
  }
}

