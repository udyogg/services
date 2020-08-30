package com.udyogg.services.common.ReactiveContext;

import java.util.HashMap;

/**
 * @author Ravi Singh
 * @created 16/04/2020 - 14:33
 * @project contra
 */
public final class Contexts {

    private static final ThreadLocal<LocalContext> local = new ThreadLocal<>();

    public static LocalContext current() {
        LocalContext context = local.get();
        if (null == context) {
            context = new LocalContext();
            local.set(context);
        }
        return context;
    }

    public static void clear() {
        local.remove();
    }


    public static void init(LocalContext current) {
        LocalContext clone = (null == current) ? null : current.createClone();
        local.set(clone);
    }

    /**
     * Local Context for storing key value pair in thread local
     */
    public static final class LocalContext {

       private HashMap<String, String> map = null;

       private LocalContext() {
           this.map = new HashMap<>();
       }

       public void put(String key, String value) {
           this.map.put(key, value);
       }

       public String get(String key) {
           return this.map.get(key);
       }

       public LocalContext createClone() {
           LocalContext clone = new LocalContext();
           final HashMap<String, String> hashMap = new HashMap<>();
           this.map.entrySet().stream().forEach( stringStringEntry -> {
               hashMap.put(stringStringEntry.getKey(), stringStringEntry.getValue());
           });
           clone.map = hashMap;
           return clone;
       }
    }
}