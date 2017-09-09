package com.github.drexler;

import java.io.LineNumberReader;
import java.io.StringReader;

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

   public static String deleteGhostProperties(String transformedJson)
   {
      StringBuilder cleanedTransform = new StringBuilder();
      LineNumberReader br = new LineNumberReader(new StringReader(transformedJson));
      try
      {
         for (String line = br.readLine(); line != null; line = br.readLine())
         {
            if (line.indexOf('$') == -1)
            {
              cleanedTransform.append(line);
            }
         }
         br.close();
      }
      catch (Exception e)
      {
        // throw new Exception("Error removing missing properties: " + e.getMessage());
      }
      return cleanedTransform.toString();
   }
}
