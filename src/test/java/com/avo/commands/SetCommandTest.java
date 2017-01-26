package com.avo.commands;

import com.avo.handlers.ResourceHandler;
import com.avo.handlers.ResourceMap;
import com.avo.helpers.ExceptionKeyNullOrEmpty;
import org.junit.Test;

import java.util.UUID;

public class SetCommandTest {


  private SetCommand sc = new SetCommand();
  private ResourceMap rs = new ResourceMap();
  private ResourceHandler rh = new ResourceHandler(rs);


  @Test(expected = ExceptionKeyNullOrEmpty.class)
  public void testSCommandEmptyKey() throws ExceptionKeyNullOrEmpty {
       sc.set("", "value", rh);
  }

  @Test(expected = ExceptionKeyNullOrEmpty.class)
  public void testSCommandNullKey() throws ExceptionKeyNullOrEmpty  {
    sc.set(null, "value", rh);
  }

  @Test()
  public void testSCommandKey() throws ExceptionKeyNullOrEmpty  {
    String uuid = UUID.randomUUID().toString();
    assert sc.set(uuid.substring(0,4), uuid.substring(5,8), rh).equals("OK");
  }

}
