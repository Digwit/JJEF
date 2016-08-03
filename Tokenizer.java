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
  public String getTokenString()
  {
  String temp = " ";
  if(myValue.substring(0) == " ")
  {
  String delStr = " ";
  String newStr;
  newStr =  myValue.replace(delStr, "");
  return newStr;
  }
 
  else if(myValue.substring(0) == "\n")
  {
  String delStr = "\n";
  String newStr;
  newStr =  myValue.replace(delStr, "");
  return newStr;
  }
  
  else
  {
  String subStr1 = " ";
  String subStr2 = "\n";
 
  int indexStr1 = myValue.indexOf(subStr1);
  int indexStr2 = myValue.indexOf(subStr2);
  
  if(subStr1.equals(" "))
  {
  String getStr = myValue.substring(0, indexStr1);
  
  String delete1 = " ";
  String new1;
  new1 = myValue.replace(delete1, "");
  temp = new1;
  }
  else if(subStr2.equals("\n"))
  {
 String getStr = myValue.substring(0, indexStr2);
 
 String delete2 = "\n";
 String new2;
  new2 = myValue.replace(delete2, "");
  temp = new2;
  }
  
  return temp;
  }
  
  }
  
  public boolean moreToken()
  {
   
  }
  
  // similar to the front of queue
  public Token peakToken()
  {
   
  }
 
 }
