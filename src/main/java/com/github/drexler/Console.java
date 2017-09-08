package com.github.drexler;

import java.io.LineNumberReader;
import java.io.StringReader;

public class Console {
   public static final String ANSI_RESET = "\u001B[0m";

   // Font colors
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

   public static void print(String outputString)
   {
      System.out.println(ANSI_GREEN + outputString + ANSI_RESET);
   }

   public static void print(String outputString, int errorLineNumber)
   {
      LineNumberReader br = new LineNumberReader(new StringReader(outputString));

      try
      {
         for (String line = br.readLine(); line != null; line = br.readLine())
         {
            if (br.getLineNumber() == errorLineNumber)
            {
               System.out.println(ANSI_CYAN_BACKGROUND + ANSI_RED + line + ANSI_RESET);
               continue;
            }
            System.out.println(ANSI_RED + line + ANSI_RESET);
         }
         br.close();
      }
      catch (Exception e)
      {
         System.out.println("UnexpectedError");
      }
   }
}
