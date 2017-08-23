package com.drexler;


import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


public class VTLRunner {
    public static void main(String args[]){
        VelocityEngine ve = new VelocityEngine();
        StringWriter sw = new StringWriter();

        try {
        	
        	ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        	ve.init();
        	
        	
        	String templatePath = "testtemplate.vm";
            Template t = ve.getTemplate(templatePath);
            
            String jsonFile = new String(Files.readAllBytes(Paths.get("C:\\Repos\\personal\\velson\\Velson\\resources\\sample.json")));
            VelocityContext vc = new VelocityContext();
            vc.put("source", jsonFile);
            t.merge(vc, sw);

        }
        catch(Exception e) {
        	System.out.println(e);
        }

        System.out.println(sw);
    }
}
