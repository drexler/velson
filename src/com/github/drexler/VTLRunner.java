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
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.JSONObject;


public class VTLRunner {
    public static void main(String args[]){

    	VTLRunner vtl = new VTLRunner();
        VelocityEngine ve = new VelocityEngine();
        StringWriter sw = new StringWriter();

        try {
        	
        	ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        	ve.init();
        	
        	String templatePath = "testtemplate.vm";
            Template t = ve.getTemplate(templatePath);
            
            String jsonFile = vtl.getFileContents("sample.json");           
            
            JSONObject jsonObj = new JSONObject(jsonFile);
            VelocityContext context = new VelocityContext();
            context.put("source", jsonObj);  
            t.merge(context, sw);

        }
        catch(Exception e) {
        	System.out.println(e);
        }

        System.out.println(sw);
    }
    
    public String getFileContents(String fileName) throws IOException {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
    	return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}
