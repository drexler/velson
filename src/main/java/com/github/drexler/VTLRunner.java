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
import org.json.JSONObject;
import org.json.JSONException;


public class VTLRunner {

   // Font colors
   public static final String ANSI_RESET  = "\u001B[0m";
   public static final String ANSI_BLACK  = "\u001B[30m";
   public static final String ANSI_RED    = "\u001B[31m";
   public static final String ANSI_GREEN  = "\u001B[32m";
   public static final String ANSI_YELLOW = "\u001B[33m";
   public static final String ANSI_BLUE   = "\u001B[34m";
   public static final String ANSI_PURPLE = "\u001B[35m";
   public static final String ANSI_CYAN   = "\u001B[36m";
   public static final String ANSI_WHITE  = "\u001B[37m";

   // Background colors
   public static final String ANSI_BLACK_BACKGROUND  = "\u001B[40m";
   public static final String ANSI_RED_BACKGROUND    = "\u001B[41m";
   public static final String ANSI_GREEN_BACKGROUND  = "\u001B[42m";
   public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
   public static final String ANSI_BLUE_BACKGROUND   = "\u001B[44m";
   public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
   public static final String ANSI_CYAN_BACKGROUND   = "\u001B[46m";
   public static final String ANSI_WHITE_BACKGROUND  = "\u001B[47m";

   public static void main(String args[])
   {
      VTLRunner      vtl             = new VTLRunner();
      VelocityEngine ve              = new VelocityEngine();
      StringWriter   sw              = new StringWriter();
      JSONObject     formattedOutput = null;

      try {
         ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
         ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
         ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
         ve.init();

         String   templatePath = "testtemplate.vm";
         Template t            = ve.getTemplate(templatePath);

         String jsonFile = vtl.getFileContents("sample.json");

         JSONObject      jsonObj = new JSONObject(jsonFile);
         VelocityContext context = new VelocityContext();
         context.put("source", jsonObj);
         t.merge(context, sw);
         formattedOutput = new JSONObject(sw.toString());
      }

      catch (Exception e) {
         if (e instanceof JSONException)
         {
            System.out.println("Invalid JSON generated. See: " + e.getMessage());
            System.out.println("================================================");
            System.out.println(sw);
         }
         else
         {
            System.out.println(e);
         }
      }
      if (formattedOutput != null)
      {
        System.out.println(ANSI_GREEN + formattedOutput.toString(3) + ANSI_RESET);
      }
   }

   public String getFileContents(String fileName) throws IOException
   {
      ClassLoader classLoader = getClass().getClassLoader();
      File        file        = new File(classLoader.getResource(fileName).getFile());
      return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
   }
}
