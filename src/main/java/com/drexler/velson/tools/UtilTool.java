package com.drexler.velson.tools;

import java.util.Base64;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * UtilTool provides a custom Velocity Tool similar to AWS built-in $util variable
 * for API Gateway mapping templates.
 *
 * @author drexler
 */
public class UtilTool
{
   public String base64Encode(String data)
    throws UnsupportedEncodingException
   {
      return Base64.getEncoder().encodeToString(data.getBytes("utf-8"));
   }

   public String base64Decode(String data)
    throws UnsupportedEncodingException
   {
     byte[] decodedData= Base64.getDecoder().decode(data);
     return new String(decodedData, "utf-8");
   }

   public String escapeJavaScript(String data) {
      return StringEscapeUtils.escapeJavaScript(data);
   }
}
