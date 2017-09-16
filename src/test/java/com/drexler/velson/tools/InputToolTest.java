import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.drexler.velson.tools.InputTool;


public class InputToolTest
{
   private InputTool inputTool;
   private String integerPropertyPath     = "$.integerProperty";
   private String numberPropertyPath      = "$.numberProperty";
   private String nullPropertyPath        = "$.nullProperty";
   private String stringPropertyPath      = "$.stringProperty";
   private String arrayPropertyPath       = "$.arrayProperty";
   private String booleanPropertyPath     = "$.booleanProperty";
   private String nonexistentPropertyPath = "$.nonexistentProperty";

   private String loadTestJson()
   {
      String testJsonFile = "test.json";

      try
      {
         ClassLoader classLoader = InputToolTest.class.getClassLoader();
         File        file        = new File(classLoader.getResource(testJsonFile).getFile());
         return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
      }
      catch (IOException e)
      {
         System.out.println("Unable to load " + testJsonFile);
         return null;
      }
   }

   @Test
   public void pathShouldReturnInstanceOfResultObjectType()
   {
      // Arrange
      InputTool inputTool = new InputTool(loadTestJson());

      //  Act && Assert
      assertTrue(inputTool.path(integerPropertyPath) instanceof Integer);
      assertTrue(inputTool.path(numberPropertyPath) instanceof Double);
      assertTrue(inputTool.path(arrayPropertyPath) instanceof JSONArray);
      assertTrue(inputTool.path(booleanPropertyPath) instanceof Boolean);
      assertTrue(inputTool.path(stringPropertyPath) instanceof String);
      assertTrue(inputTool.path(nullPropertyPath) == null);
      assertTrue(inputTool.path(nonexistentPropertyPath) == null);
   }

   @Test
   public void jsonShouldReturnResultAsJsonString()
   {
      // Arrange
      InputTool inputTool = new InputTool(loadTestJson());

      // Act
      JSONObject firstItem  = new JSONObject();
      firstItem.put("propertyOne", 1);
      firstItem.put("propertyTwo", "here");

      JSONObject secondItem = new JSONObject();
      secondItem.put("propertyOne", 2);
      secondItem.put("propertyTwo", "there");

      JSONArray expectedArray = new JSONArray();
      expectedArray.add(firstItem);
      expectedArray.add(secondItem);

      // Assert
      assertEquals(1, inputTool.json(integerPropertyPath));
      assertEquals(2.6, inputTool.json(numberPropertyPath));
      assertEquals("null", inputTool.json(nullPropertyPath));
      assertEquals("\"hello\"", inputTool.json(stringPropertyPath));
      assertEquals(true, inputTool.json(booleanPropertyPath));
      assertEquals("\"\"", inputTool.json(nonexistentPropertyPath));
      assertEquals(expectedArray, (JSONArray)inputTool.json(arrayPropertyPath));
   }
}
