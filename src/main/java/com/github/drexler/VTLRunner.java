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
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;


public class VTLRunner {
   public static void main(String args[])
   {
      VTLRunner      vtl            = new VTLRunner();
      VelocityEngine ve             = new VelocityEngine();
      StringWriter   sw             = new StringWriter();
      JSONObject     compressedJson = null;

      try
      {
         ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
         ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
         ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
         ve.init();

         String     templatePath  = "testtemplate.vm";
         Template   t             = ve.getTemplate(templatePath);
         String     jsonFile      = vtl.getFileContents("sample.json");
         JSONObject awsJsonObject = (JSONObject)JSONValue.parseWithException(jsonFile);

         VelocityContext context = new VelocityContext();
         context.put("source", awsJsonObject);
         t.merge(context, sw);

         compressedJson = (JSONObject)JSONValue.parseWithException(sw.toString());
      }
      catch (Exception e)
      {
         if (e instanceof ParseException)
         {
            String message = e.getMessage();
            System.out.println("Invalid JSON. See: " + message);
            System.out.println("================================================");
            Console.print(sw.toString(), TransformerUtils.getErrorLineNumber(message));
         }
         else
         {
            System.out.println(e);
         }
      }
      if (compressedJson != null)
      {
         Console.print(TransformerUtils.decompressJsonString(compressedJson.toString()));
      }
   }

   private String getFileContents(String fileName) throws IOException
   {
      ClassLoader classLoader = getClass().getClassLoader();
      File        file        = new File(classLoader.getResource(fileName).getFile());

      return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
   }
}
