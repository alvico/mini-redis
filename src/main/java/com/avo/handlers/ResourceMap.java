package com.avo.handlers;

import com.avo.helpers.Status;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResourceMap implements Resource {

  private ConcurrentHashMap<String, String> resource = null;

  public ResourceMap() {
    resource = new ConcurrentHashMap<String, String>();
  }


  public synchronized Status set(String key, String value) {
    resource.put(key, value);
    return Status.UPDATED;
  }

  public String get(String key) {
    return resource.get(key);
  }

  public int size() {
    return resource.size();
  }
}
