
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Source
{
  
  private String _filename;
  private String[] _code;
  
  public Source(String filename) throws IOException
  {
    _filename = filename;
    _code = null;
    
    BufferedReader br;
    int count = 0;
    
    br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
    
    while (br.readLine() != null)
      count++;
    
    br.close();
    
    if (count > 0)
      _code = new String[count];
    
    br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
    
    for (int k = 0; k < count; k++)
      _code[k] = br.readLine();
    
    br.close();
  }
  
  public String getFilename()
  {
    return _filename;
  }
  
  public String getLine(int lineNumber)
  {
    if (lineNumber >= 0 && lineNumber < _code.length)
      return _code[lineNumber];
    else
      return "";
  }
  
  public int getNumberOfLines()
  {
    return _code.length;
  }
  
}
