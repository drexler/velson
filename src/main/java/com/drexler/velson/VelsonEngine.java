package com.drexler.velson;

import java.io.StringWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.ToolManager;

import org.json.JSONObject;
import org.json.JSONException;

import com.drexler.velson.tools.InputTool;

/**
 * <p>
 * VelsonEngine provides a separate new-able instance of the
 * Velocity template engine.
 * </p>
 *
 * @author drexler
 */
public class VelsonEngine
{
   private VelocityEngine engine;
   private StringWriter writer;
   private String templatePath;
   private String jsonFile;
   private ResourceLocale locale;

   private VelsonEngine()
   {
      writer = new StringWriter();
      engine = new VelocityEngine();
      engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
   }

   public VelsonEngine(String templatePath, String jsonFile)
   {
      this();
      this.templatePath = templatePath;
      this.jsonFile     = jsonFile;
      this.locale       = ResourceLocale.classpath;
      configure();
   }

   public VelsonEngine(ResourceLocale locale, String templatePath, String jsonFile)
   {
      this();
      this.templatePath = templatePath;
      this.jsonFile     = jsonFile;
      this.locale       = locale;
      configure();
   }

   private void configure()
   {
      if (locale == ResourceLocale.classpath)
      {
         engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
         engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
      }
      else
      {
         engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templatePath);
      }
   }

   public JSONObject transform() throws TransformException
   {
      String     jsonString    = null;
      JSONObject formattedJson = null;

      try
      {
         engine.init();
         Template template = engine.getTemplate(templatePath);
         jsonString    = getFileContents(jsonFile);
         formattedJson = TransformerUtils.formatJson(jsonString);

         ToolManager toolManager = new ToolManager();
         toolManager.configure("velocity-tools.xml");

         VelocityContext context = new VelocityContext(toolManager.createContext());
         context.put("input", new InputTool(formattedJson.toString()));
         template.merge(context, writer);

         formattedJson = TransformerUtils.formatJson(writer.toString());
         return formattedJson;
      }
      catch (Exception e)
      {
         if (e instanceof JSONException)
         {
            String malformedJson = formattedJson == null ? jsonString : writer.toString();
            String message       = "Invalid JSON. See: " + e.getMessage() + "\n" +
                                   "malformedJson: " + "\n" + malformedJson;
            throw new TransformException(message, e);
         }
         else
         {
            throw new TransformException(e);
         }
      }
   }

   private String getFileContents(String fileName) throws IOException
   {
      ClassLoader classLoader = getClass().getClassLoader();
      File        file        = new File(classLoader.getResource(fileName).getFile());

      return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
   }
}
