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
 
