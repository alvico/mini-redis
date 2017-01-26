package com.avo.handlers;

import com.avo.helpers.ExceptionLocked;
import com.avo.helpers.ExceptionUnknownStatus;
import com.avo.helpers.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
  This class has the logic to handle operations on resources
  Commands don't known about what kind of data structure we do have
  @ResourceHandler does.
 */
public class ResourceHandler {

  private static Logger log = LoggerFactory.getLogger(ResourceHandler.class);
  private java.util.Random rand = new Random();
  private Resource res = null;
  private  int retry = 0;
  // should be set via conf parameter
  private static final int MAX_RETRIES = 10;

  public  ResourceHandler(Resource rs) {
    res = rs;
  }

  public boolean set(String key, String val) throws Exception {
    Status st = res.set(key, val);
    switch (st) {
      case UPDATED:
          retry = 0;
          log.info(String.format("Key %s updated with value %s", key, val));
        return true;
      default:
        throw new ExceptionUnknownStatus();
    }
  }

  public String get(String key) throws Exception {
    String val = res.get(key);
    if (val == null) {
      return null;
    }
   return val;
  }

  public String get(String key, String val) throws Exception {
    if (val.equals(res.get(key)))
      return "OK";
    else
      return null;
  }

  public int size() {
    return res.size();
  }

}
