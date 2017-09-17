import org.junit.Test;
import static org.junit.Assert.*;

import com.drexler.velson.tools.UtilTool;
import java.io.UnsupportedEncodingException;

public class UtilToolTest
{
   @Test
   public void base64EncodeWithValidStringReturnsEncodedString()
   {
      UtilTool util = new UtilTool();
      String expectedEncodedString = "aGVsbG8=";
      String result = "";
      try
      {
        result = util.base64Encode("hello");
      }
      catch(UnsupportedEncodingException ex)
      {
        fail("Exception shouldn't be thrown!");
      }
      assertEquals(expectedEncodedString, result);
   }

   @Test
   public void base64DecodeWithValidDataReturnsOriginalData()
   {
      UtilTool util = new UtilTool();
      String expectedDecodedString = "hello";
      String result = "";
      try
      {
        result = util.base64Decode("aGVsbG8=");
      }
      catch(UnsupportedEncodingException ex)
      {
        fail("Exception shouldn't be thrown!");
      }
      assertEquals(expectedDecodedString, result);
   }
}
