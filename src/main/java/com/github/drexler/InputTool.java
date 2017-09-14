package com.github.drexler;

import com.jayway.jsonpath.*;

public class InputTool
{
   private Object document;

   public InputTool(String jsonString)
   {
      document = Configuration.defaultConfiguration()
                    .jsonProvider()
                    .parse(jsonString);
   }

   public Object json(String jsonPathExpression)
   {
      try
      {
         Object value = JsonPath.read(document, jsonPathExpression);
         if (value instanceof String)
         {
            return new String("\"" + value + "\"");
         }
         return value == null ? "null" : value;
      }
      catch (PathNotFoundException e)
      {
         return new String("\"\"");
      }
   }

   public Object path(String jsonPathExpression)
   {
      try
      {
         return JsonPath.read(document, jsonPathExpression);
      }
      catch (PathNotFoundException e)
      {
         return null;
      }
   }
}
