package com.fly.config.ds;



public class DatabaseContextHolder {

    private static final ThreadLocal<DatabaseType> contextHolder
            = new ThreadLocal<>();


    public static void setDatabaseType(DatabaseType type){
        contextHolder.set(type);
    }
    public static void clearDatabase(){
        contextHolder.remove();
    }

    public static DatabaseType getDatabaseType(){
        return contextHolder.get();
    }


}
