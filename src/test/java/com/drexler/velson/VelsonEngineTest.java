import org.junit.Test;
import static org.junit.Assert.*;

import org.json.JSONObject;

import com.drexler.velson.VelsonEngine;
import com.drexler.velson.ResourceLocale;


public class VelsonEngineTest
{
   private String template = "template.vm";
   private String jsonFile = "test.json";

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
      String templatePath = testResourceFolderPath + template;
      String jsonFilePath = testResourceFolderPath + jsonFile;

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
