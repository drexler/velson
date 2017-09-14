package com.github.drexler;


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



public class VelocityTransformer
{
   public static void main(String args[])
   {
      VelocityTransformer vtl = new VelocityTransformer();
      VelocityEngine      ve  = new VelocityEngine();
      StringWriter        sw  = new StringWriter();

      JSONObject formattedJson = null;

      try
      {
         ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
         ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
         ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
         ve.init();

         String   templatePath = "testtemplate.vm";
         Template template     = ve.getTemplate(templatePath);
         String   jsonString   = vtl.getFileContents("sample.json");
         formattedJson = TransformerUtils.formatJson(jsonString);

         ToolManager toolManager = new ToolManager();
         toolManager.configure("velocity-tools.xml");

         VelocityContext context = new VelocityContext(toolManager.createContext());
         context.put("input", new InputTool(formattedJson.toString()));
         template.merge(context, sw);

         formattedJson = TransformerUtils.formatJson(sw.toString());
         if (formattedJson != null)
         {
            Console.print(formattedJson.toString(3));
         }
      }
      catch (Exception e)
      {
         String message = e.getMessage();
         if (e instanceof JSONException)
         {
            System.out.println("Invalid JSON. See: " + message);
            System.out.println("================================================");
            Console.print(sw.toString(), TransformerUtils.getErrorLineNumber(message));
         }
         else
         {
            System.out.println(e);
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
