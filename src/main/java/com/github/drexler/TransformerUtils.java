package com.github.drexler;

public class TransformerUtils {
   public static int getErrorLineNumber(String errorMessage)
   {
      if (errorMessage.length() == 0)
      {
         return 0;
      }

      try
      {
         int    closingBracketPosition = errorMessage.indexOf(']');
         int    linePosition           = errorMessage.lastIndexOf("line");
         String errorLineNumber        = errorMessage.substring(linePosition + 4, closingBracketPosition).trim();
         return Integer.parseInt(errorLineNumber);
      }
      catch (Exception e)
      {
         System.out.println("Unable to locate erroneous line: " +  e.getMessage());
         return 0;
      }
   }
}
