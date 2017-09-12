package com.github.drexler;

import com.jayway.jsonpath.*;
import net.minidev.json.JSONObject;

public class InputTool
{
   private String jsonString;

   public InputTool(String jsonString)
   {
      this.jsonString = jsonString;
   }

   public Object json(String jsonPathExpression)
   {
      Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
      try
      {
        Object value = JsonPath.read(document, jsonPathExpression);
        if (value instanceof String)
        {
          return new String("\"" + value + "\"");
        }
        return value == null ? "null" : value;
      }
      catch(PathNotFoundException e)
      {
        return new String("\"\"");
      }

   }
}
