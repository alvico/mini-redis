package com.avo.commands;

import com.avo.handlers.ResourceHandler;
import com.avo.helpers.ExceptionKeyNullOrEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SetCommand {

  private static Logger log = LoggerFactory.getLogger(SetCommand.class);

  /*
    Actual trigger for the set command
    receives a ResourceHandler
    Key Value should not be null
    both string? shouldn't key be a int or a long?
     -> check for correctness
   */
  public String set(String key, String value, ResourceHandler rh) throws ExceptionKeyNullOrEmpty {
    long requestTime = System.currentTimeMillis();

      if (key == null || key.equals("")) {
        throw new ExceptionKeyNullOrEmpty();
      }
    try {
      if (rh.set(key, value))
          return "OK";
      else //Conditions where not met
        return null;
    }
    catch (Exception e){
      //In case of error we log the exception
      log.error(e.getMessage());
      log.debug(e.getCause().toString());
      return null;
    }
  }

}



