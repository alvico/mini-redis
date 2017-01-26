package com.avo.handlers;

import com.avo.helpers.Status;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResourceMap implements Resource {

  private ConcurrentHashMap<String, String> resource = null;
  private AtomicBoolean lock = new AtomicBoolean();

  public ResourceMap() {
    lock.set(false);
    resource = new ConcurrentHashMap<String, String>();
  }

  /*
    Sets the value if it is not locked
  */
  public Status set(String key, String value) {
    if (!lock.getAndSet(true)) {
      resource.put(key, value);
      lock.set(false);
      return Status.UPDATED;
    }
    else
      return Status.LOCKED;
  }

  public String get(String key) {
    return resource.get(key);
  }

  public int size() {
    return resource.size();
  }
}
