package com.avo.handlers;


import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class ResourceHandlerTest {

  private ResourceMap rs;
  private final CyclicBarrier gate = new CyclicBarrier(3);

  private String randString(int length){
    return UUID.randomUUID().toString().substring(0, length);
  }


  @Test
  public void testRHSetOK() throws Exception {
    rs = new ResourceMap();
    ResourceHandler rh = new ResourceHandler(rs);
    assert rh.set(randString(4), randString(4));
  }


  @Test
  public void testRHSetMultithreadedDifferentKeys() throws Exception {
    rs = new ResourceMap();
    int timeout = 10;
    boolean result = false;
    Thread t1 = new Thread(){
      public void run(){
        ResourceHandler rh = new ResourceHandler(rs);
        try {
          gate.await();
          assert rh.set(randString(4), randString(4));
        } catch (Exception e) {
          assert false;
        }
      }
    };

    Thread t2 = new Thread() {
      public void run(){
        ResourceHandler rh = new ResourceHandler(rs);
        try {
          gate.await();
          assert rh.set(randString(4), randString(4));
        } catch (Exception e) {
          assert false;
        }
      }
    };

    assert rs.size() == 0;

    t1.start();
    t2.start();
    gate.await();

    while (timeout > 0) {
       if (rs.size() == 2) {
         result = true;
         timeout = 0;
       }
       else {
        TimeUnit.SECONDS.sleep(1);
        timeout --;
      }
    }
    assert result;
  }

  @Test
  public void testRHSetMultithreadedSameKeys() throws Exception {
    rs = new ResourceMap();
    final String key = "Key";
    final String val1 = "val1";
    final String val2 = "val2";
    int timeout = 10;
    boolean result = false;

    Thread t1 = new Thread(){
      public void run(){
        ResourceHandler rh = new ResourceHandler(rs);
        try {
          gate.await();
          assert rh.set(key, val1);
        } catch (Exception e) {
          assert false;
        }
      }
    };

    Thread t2 = new Thread() {
      public void run(){
        ResourceHandler rh = new ResourceHandler(rs);
        try {
          gate.await();
          assert rh.set(key, val2);
        } catch (Exception e) {
          assert false;
        }
      }
    };

    ResourceHandler resH = new ResourceHandler(rs);
    assert rs.size() == 0;

    t1.start();
    t2.start();
    gate.await();

    while (timeout > 0) {
      if (resH.size() == 1) {
        assert resH.get(key).equals(val1);
        result = true;
        timeout = 0;
      }
      else {
        TimeUnit.SECONDS.sleep(1);
        timeout --;
      }
    }
    assert result;
  }

}
