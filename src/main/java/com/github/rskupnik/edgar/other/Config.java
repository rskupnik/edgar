package com.github.rskupnik.edgar.other;

import com.github.rskupnik.parrot.Parrot;

public class Config {

    private static Parrot parrot;

    public static void init() {
        parrot = new Parrot("config");
    }

    public static Parrot getParrot() {
        return parrot;
    }
}
