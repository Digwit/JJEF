import java.io.BufferedWriter;
import java.util.*;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Table
{
  //For test
  public static void main(String[] args)
  {
   Tokenizer t = new Tokenizer("Load \"C:/Users/chsieh/Documents/ERICA/DR_JAVA/JJEF/SacramentocrimeJanuary2006.csv\" into crime ; Delete record 2 from crime ; " );
//   while(t.moreTokens())
//    System.out.println(t.getToken());
   Parser p = new Parser(t);
   p.parseProgram();
   /*
    System.out.println(convert("C:/Users/chsieh/Documents/ERICA/DR_JAVA/JJEF/SacramentocrimeJanuary2006.csv"));
    Table attempt1 = new Table(convert("C:/Users/chsieh/Documents/ERICA/DR_JAVA/JJEF/SacramentocrimeJanuary2006.csv"));
    System.out.println("Index of grid: " + attempt1.myColumns.get("grid"));
    */
   
     try
     {
            writeFile("resultFile.txt", "Here's a file.\nWith multiple lines.\nThree to be exact.");
     }
     catch (IOException e)
     {
      System.out.println("Error: Couldn't save file");
     }
  }
  

    //Instance variables
    //Precondition: the  first row contains variable names (as Strings) for each column
    private Map<String, Integer> myColumns;
    
    private String[][] myArray;
    private int[] myRowIndex;
    private int[] myColIndex;
    
    
    //Constructor
    public Table(String[][] input)
    {
     myRowIndex = new int [input.length];
        for (int r = 0; r < input.length; r++)
        {
         myRowIndex[r] = r;
        }
        
        myColIndex = new int[input[0].length];
        for (int c = 0; c < input[0].length; c++)
        {
         myColIndex[c] = c;
        }
      myColumns = makeMap(input);
      myArray = input;
    }
    
    
    
    //METHODS
    public Map<String, Integer> makeMap(String[][] data)
    {
      myColumns = new HashMap<String, Integer>();
      
      for (int k = 0; k < data[0].length; k++)
      {
        myColumns.put(data[0][k], k);
      }
      return myColumns;
    }
    
    public int getRowIndex(int x)
    {
     return myRowIndex[x];
    }
    
    
    public int getColIndex(int x)
    {
     return myColIndex[x];
    }
    
    public void setRowIndex(int x, int y)
    {
     myRowIndex[x] = y;
    }
    
    
    //Convert changes a csv file into a String[][] (NOT a new table)
    public static String[][] convert(String fileName)
    {
      String[][] dataTable;
      
      
      Source s;
      try
      {
        s = new Source(fileName);
      }
      catch (Exception e)
      {
        System.out.println("oops");
        s = null;
      }
      
      //Sets size of output array
      String example = s.getLine(0);
      String[] fields = example.split(",");
      dataTable = new String[s.getNumberOfLines()][fields.length];
      
      for (int k = 0; k < s.getNumberOfLines(); k++)
      { 
        String line = s.getLine(k);
        String[] parts;
        parts = line.split(",");
        
        for (int i = 0; i < parts.length; i++)
        {
          dataTable[k][i] = parts[i];
        }
      }
      return dataTable;
    }
    
    public int getRows()
    {
     return myArray.length;
    }
    
    public int getCols()
    {
     return myArray[0].length;
    }
    //Precondition: input tables' first column is the identifying variable, for now :)
    //Precondition2: both input tables have same students in same order
    public static Table mergeSameRows(Table x, Table y)
    {
      String[][] merged;
      merged = new String[x.myArray.length][x.myArray[0].length + y.myArray[0].length - 1];
      for (int r = 0; r < x.myArray.length; r++)
      {
        for (int c = 0; c < x.myArray[0].length; c++)
        {
          merged[r][c] = x.myArray[x.getRowIndex(r)][x.getColIndex(c)];
        }
      }
      for (int r = 0; r < y.myArray.length; r++)
      {
        for (int c = 1; c < y.myArray[0].length - 1; c++)
        {
          merged[r][c + x.myArray[0].length] = y.myArray[y.getRowIndex(r)][y.getColIndex(c)];
        }
      }
      return new Table(merged);
    }
    
    public static Table mergeDiffRows(Table x, Table y)
    {
      
        String[][] merged;
        merged = new String[x.myArray.length + y.myArray.length -1][x.myArray[0].length];
        for (int r = 0; r < x.myArray.length; r++)
        {
          for (int c = 0; c < x.myArray[0].length; c++)
          {
            merged[r][c] = x.myArray[x.getRowIndex(r)][x.getColIndex(c)];
          }
        }
        //second table--> delete row 1
        for (int r = 1; r < y.myArray.length -1; r++)
        {
          for (int c = 0; c < y.myArray[0].length; c++)
          {
            merged[r + x.myArray.length][c] = y.myArray[y.getRowIndex(r)][y.getColIndex(c)];
          }
        }
        return new Table(merged);
      
    }
    
    
    
    public static Table deleteRow(Table x, int d) //the row that user wants to delete is int d
    {
      String[][] deleted;
      deleted = new String[x.myArray.length -1][x.myArray[0].length];
      
      for (int r = 0; r < d; r++)
      {
        for (int c = 0; c < x.myArray[0].length; c++)
        {
          deleted[r][c] = x.myArray[x.getRowIndex(r)][x.getColIndex(c)]; //copies over top half of original array
        }
      }
      
      for (int r = d + 1; r < x.myArray.length; r++)
      {
        for (int c = 0; c < x.myArray[0].length; c++)
        {
          deleted[r][c] = x.myArray[x.getRowIndex(r)][x.getColIndex(c)]; // copies over bottom half of original array
        }
      }
      return new Table(deleted);
    }
    
    public static Table deleteColumn(Table x, int d)
    { 
      String[][] deleted1;
      deleted1 = new String[x.myArray.length][x.myArray[0].length-1];
      
      for (int r = 0; r < x.myArray.length; r++)
      {
        for (int c = 0; c < d ; c++) 
        {
          deleted1 [r][c] = x.myArray[x.getRowIndex(r)][x.getColIndex(c)]; //copies over left half of original array
        }
      }
      //This next bit might not be working
      for (int r = 0; r < x.myArray.length; r++)
      {
        for (int c = d + 1; c < x.myArray[0].length; c++)
        {
          deleted1[r][c] = x.myArray[x.getRowIndex(r)][x.getColIndex(c)]; //NOTE: should perhaps delete the d from record as well
        }
      }
      return new Table(deleted1);
    }
    
    
    public void printInfo(String variable)
  {
    //Prints name of variable and number of observations
    System.out.println(variable + " (" + myArray.length + " observations):");
    
    //Calculates  and prints mean
    double mean = 0;
    for (int r = 1; r < myArray.length; r++)
    {
      mean = mean + Double.parseDouble(myArray[myRowIndex[r]][myColIndex[myColumns.get(variable)]]);
    }
    mean /= myArray.length;
    System.out.println("Mean: " + mean);
    
    
    //Calculates min and max and prints
    double max = Double.parseDouble(myArray[myRowIndex[1]][myColIndex[myColumns.get(variable)]]);
    double min = Double.parseDouble(myArray[myRowIndex[1]][myColIndex[myColumns.get(variable)]]);
    for (int r = 1; r < myArray.length; r++)
    {
      if (Double.parseDouble(myArray[myRowIndex[r]][myColIndex[myColumns.get(variable)]]) < min)
        min = Double.parseDouble(myArray[myRowIndex[r]][myColIndex[myColumns.get(variable)]]);
     if (Double.parseDouble(myArray[myRowIndex[r]][myColIndex[myColumns.get(variable)]]) > max)
        max = Double.parseDouble(myArray[myRowIndex[r]][myColIndex[myColumns.get(variable)]]);
    }
    mean /= myArray.length;
    System.out.println("Max: " + max + "\n Min: " + min);
    
    //Calculates and prints median
    double[] store;
    store = new double[myArray.length];
     for (int r = 1; r < myArray.length; r++)
    {
      store[r] = Double.parseDouble(myArray[r][myColumns.get(variable)]);
    }
     Arrays.sort(store);
     if (myArray.length % 2 == 0)
     {
      System.out.println((Double.parseDouble(myArray[(myArray.length/2)][myColIndex[myColumns.get(variable)]]) + Double.parseDouble(myArray[(myArray.length/2+1)][myColumns.get(variable)])) /2 );
     }
     else
     {
      System.out.println(Double.parseDouble(myArray[myArray.length/2 +1][myColIndex[myColumns.get(variable)]]));
     }
   }
    
    public static String save(Table t)
    {
     String output = "";
     
     for (int r = 0; r < t.getRows(); r++ )
     {
      for (int c = 0; c < t.getCols(); c++)
      {
       output = output +"\""+ t.myArray[r][c] +"\"" + ",";  
      }
      output = output + "\n";
     }
     return output;
     
}
    
    public static void writeFile(String resultFile, String data) throws IOException
    {
     BufferedWriter writer;
     
     writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFile)));
     
     writer.write(data, 0, data.length());
     
     writer.close();
    }
    

}
 

   
// convert from string to other objects    
// String s = "253";
// int x ;
// x = Integer.parseInt(s);
