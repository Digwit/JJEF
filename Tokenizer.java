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

 /*
 //WENT THRU AND REPLACED MYVALUE WITH PROGRAM BC WE SWITCHED THIS FROM THE TOKEN CLASS TO THIS TOKENIZER CLASS. CORRECT ME IF IM WRONG
public class Tokenizer 
 {
  private String myProgram;
  
  // constructor 
  public Tokenizer(String program)
  {
   myProgram = program;
  }
  
public String getTokenString()
 {
  String temp = " ";
  if(program.substring(0) == " ")
  {
   String delStr = " ";
   String newStr;
   newStr =  program.replace(delStr, "");
   return newStr;
  }
  
  else if(program.substring(0) == "\n")
  {
   String delStr = "\n";
   String newStr;
   newStr =  program.replace(delStr, "");
   return newStr;
  }
  
  else
  {
   String subStr1 = " ";
   String subStr2 = "\n";
   
   int indexStr1 = program.indexOf(subStr1);
   int indexStr2 = program.indexOf(subStr2);
   
   if(subStr1.equals(" "))
   {
    String getStr = program.substring(0, indexStr1);
    
    String delete1 = " ";
    String new1;
    new1 = program.replace(delete1, "");
    temp = new1;
   }
   else if(subStr2.equals("\n"))
   {
    String getStr = program.substring(0, indexStr2);
    String delete2 = "\n";
    String new2;
    new2 = program.replace(delete2, "");
    temp = new2;
   }
   
   return temp;
  }
}
  
  public String peekToken(String s)
  {
    String temp = " ";
  if(program.substring(0) == " ")
  {
   String delStr = " ";
   String newStr;
   newStr =  program.replace(delStr, "");
   return newStr;
  }
  
  else if(program.substring(0) == "\n")
  {
   String delStr = "\n";
   String newStr;
   newStr =  program.replace(delStr, "");
   return newStr;
  }
  //CREATING NEW TOKEN : IDK IF THIS IS RIGHT
  new Token (String newStr)
  
    
  }
}
*/
