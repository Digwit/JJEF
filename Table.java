import java.util.*;
import java.util.Arrays;

public class Table
{
  //For test
  public static void main(String[] args)
  {
    System.out.println(convert("C:/Users/chsieh/Documents/ERICA/DR_JAVA/JJEF/SacramentocrimeJanuary2006.csv"));
    Table attempt1 = new Table(convert("C:/Users/chsieh/Documents/ERICA/DR_JAVA/JJEF/SacramentocrimeJanuary2006.csv"));
    System.out.println("Index of grid: " + attempt1.myColumns.get("grid"));
  }
  

    //Instance variables
    //Precondition: the  first row contains variable names (as Strings) for each column
    private Map<String, Integer> myColumns;
    
    private String[][] myArray;
  //  private int[] myRowIndex;
 //   private int[] myColIndex;
    
    
    //Constructor
    public Table(String[][] input)
    {
      myColumns = makeMap(input);
      myArray = input;
     // myRowIndex = new int[input.length];
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
          merged[r][c] = x.myArray[r][c];
        }
      }
      for (int r = 0; r < y.myArray.length; r++)
      {
        for (int c = 1; c < y.myArray[0].length - 1; c++)
        {
          merged[r][c + x.myArray[0].length] = y.myArray[r][c];
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
            merged[r][c] = x.myArray[r][c];
          }
        }
        //second table--> delete row 1
        for (int r = 1; r < y.myArray.length -1; r++)
        {
          for (int c = 0; c < y.myArray[0].length; c++)
          {
            merged[r + x.myArray.length][c] = y.myArray[r][c];
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
          deleted[r][c] = x.myArray[r][c]; //copies over top half of original array
        }
      }
      
      for (int r = d + 1; r < x.myArray.length; r++)
      {
        for (int c = 0; c < x.myArray[0].length; c++)
        {
          deleted[r + d][c] = x.myArray[r][c]; // copies over bottom half of original array
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
          deleted1 [r][c] = x.myArray[r][c]; //copies over left half of original array
        }
      }
      for (int r = 0; r < x.myArray.length; r++)
      {
        for (int c = d + 1; c < x.myArray[0].length; c++)
        {
          deleted1[r+d][c] = x.myArray[r+d][c];
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
      mean = mean + Double.parseDouble(myArray[r][myColumns.get(variable)]);
    }
    mean /= myArray.length;
    System.out.println("Mean: " + mean);
    
    
    //Calculates min and max and prints
    double max = Double.parseDouble(myArray[1][myColumns.get(variable)]);
    double min = Double.parseDouble(myArray[1][myColumns.get(variable)]);
    for (int r = 1; r < myArray.length; r++)
    {
      if (Double.parseDouble(myArray[r][myColumns.get(variable)]) < min)
        min = Double.parseDouble(myArray[r][myColumns.get(variable)]);
     if (Double.parseDouble(myArray[r][myColumns.get(variable)]) > max)
        max = Double.parseDouble(myArray[r][myColumns.get(variable)]);
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
    	 System.out.println((Double.parseDouble(myArray[(myArray.length/2)][myColumns.get(variable)]) + Double.parseDouble(myArray[(myArray.length/2+1)][myColumns.get(variable)])) /2 );
     }
     else
     {
    	 System.out.println(Double.parseDouble(myArray[myArray.length/2 +1][myColumns.get(variable)]));
     }
   }
    
    
  }