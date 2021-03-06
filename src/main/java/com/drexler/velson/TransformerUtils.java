package com.drexler.velson;

import java.io.LineNumberReader;
import java.io.StringReader;
import org.json.JSONObject;

/**
 * TransformerUtils provides helper functions.
 *
 * @author drexler
 */
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
         System.out.println("Unable to locate erroneous line: " + e.getMessage());
         return 0;
      }
   }

   public static String deleteGhostProperties(String transformedJson)
   {
      StringBuilder    cleanedTransform = new StringBuilder();
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

   public static JSONObject formatJson(String jsonString)
   {
      return new JSONObject(jsonString);
   }

   public static String getMalformedJson(String errorMessage)
   {
      if (errorMessage.length() == 0)
      {
         return null;
      }

      String location = "malformedJson:";

      int    jsonBodyPosition = errorMessage.lastIndexOf(location);
      String malformedJson    = errorMessage.substring(jsonBodyPosition + location.length()).trim();
      return malformedJson;
   }

   public static String getErrorReason(String errorMessage)
   {
      if (errorMessage.length() == 0)
      {
         return null;
      }

      String location = "reason:";

      int    reasonPosition = errorMessage.lastIndexOf(location);
      int    malformedBodyLocation = errorMessage.lastIndexOf("malformedJson");
      String reason    = errorMessage.substring(reasonPosition + location.length(), malformedBodyLocation).trim();
      return reason;
   }
}
