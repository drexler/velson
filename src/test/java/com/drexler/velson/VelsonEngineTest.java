import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.containsString;

import org.json.JSONObject;

import com.drexler.velson.VelsonEngine;
import com.drexler.velson.ResourceLocale;
import com.drexler.velson.TransformException;


public class VelsonEngineTest
{
   private String template = "template.vm";
   private String jsonFile = "test.json";
   private String malformedJsonFile = "malformedTest.json";

   @Rule
   public ExpectedException thrown = ExpectedException.none();

   @Test
   public void givenNoLocaleEngineShouldDefaultToClasspathResources()
   {
      VelsonEngine velsonEngine = new VelsonEngine(template, jsonFile);

      try
      {
         JSONObject output = velsonEngine.transform();
         assertTrue(true);
      }
      catch (Exception ex)
      {
         fail("Exception shouldn't be thrown!");
      }
   }

   @Test(expected = TransformException.class)
   public void givenNoLocaleMissingTemplateEngineShouldThrowTransformException()
   {
      VelsonEngine velsonEngine = new VelsonEngine("missing.vm", jsonFile);
      JSONObject   output       = velsonEngine.transform();
   }

   @Test(expected = TransformException.class)
   public void givenNoLocaleMissingJsonFileEngineShouldThrowTransformException()
   {
      VelsonEngine velsonEngine = new VelsonEngine(template, "missing.json");
      JSONObject   output       = velsonEngine.transform();
   }

   @Test
   public void givenNoLocaleMalformedJsonFileEngineShouldThrowTransformException()
   {
      thrown.expect(TransformException.class);
      thrown.expectMessage(containsString("reason: Invalid JSON"));
      thrown.expectMessage(containsString("line 6"));
      VelsonEngine velsonEngine = new VelsonEngine(template, malformedJsonFile);
      JSONObject   output  = velsonEngine.transform();
   }

   @Test
   public void givenClasspathLocaleEngineShouldUseClasspathResources()
   {
      VelsonEngine velsonEngine = new VelsonEngine(ResourceLocale.classpath, template, jsonFile);

      try
      {
         JSONObject output = velsonEngine.transform();
         assertTrue(true);
      }
      catch (Exception ex)
      {
         fail("Exception shouldn't be thrown!");
      }
   }

   @Test
   public void givenFileSystemLocaleEngineShouldUseSpecifiedFiles()
   {
      String testClassPath = VelsonEngineTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

      // .getPath() returns a nix-style path, which breaks on Windows as it has a starting forward slash
      // Example: /C:/dir/foo/build/classes
      if (System.getProperty("os.name").toLowerCase().contains("windows"))
      {
         testClassPath = testClassPath.substring(1);
      }

      String testResourceFolderPath = testClassPath + "../../resources/test/";
      String templatePath           = testResourceFolderPath + template;
      String jsonFilePath           = testResourceFolderPath + jsonFile;

      VelsonEngine velsonEngine = new VelsonEngine(ResourceLocale.fileSystem, templatePath, jsonFilePath);

      try
      {
         JSONObject output = velsonEngine.transform();
         assertTrue(true);
      }
      catch (Exception ex)
      {
         fail("Exception shouldn't be thrown!");
      }
   }
}
