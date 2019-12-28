package com.example.myapplication.sharepref;

import android.content.Context;

public class SharedPreferences
{

    private static String password = "password";

    private static String name = "name";

    private static String id = "id";

    private static String username = "username";

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SharedPreferences.password = password;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SharedPreferences.name = name;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        SharedPreferences.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SharedPreferences.username = username;
    }
}
