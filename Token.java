public class Token 
{
 private String myValue;
 private int myType;
 
 public Token(String value, int type)
 {
  myValue = value;
  myType = type;
 }
 
 public String getValue()
 {
  return myValue;
 }
 
 public int getType() //Not sure how this works because in Parser I use this with T_SEMICOLON, not just integers
 {
  return myType;
 }
 
 }
 
}

