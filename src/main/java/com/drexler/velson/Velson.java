package com.drexler.velson;

import org.json.JSONObject;
import org.json.JSONException;

public class Velson
{
   public static void main(String args[])
   {
      VelsonEngine velsonEngine = new VelsonEngine("testtemplate.vm", "sample.json");

      try
      {
         JSONObject formattedJson = velsonEngine.transform();
         Console.print(formattedJson.toString(3));
      }
      catch (Exception e)
      {
         Throwable rootCause = e.getCause();

         if (rootCause instanceof JSONException)
         {
            String message = e.getMessage();
            Console.print(TransformerUtils.getMalformedJson(message),
                          TransformerUtils.getErrorLineNumber(message));
         }
         else
         {
            System.out.println(e);
         }
      }
   }


}
