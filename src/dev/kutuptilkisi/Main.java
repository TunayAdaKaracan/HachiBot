package dev.kutuptilkisi;

import dev.kutuptilkisi.discord.Bot;
import dev.kutuptilkisi.manager.EventManager;

import javax.security.auth.login.LoginException;

public class Main {

    private static EventManager em;

    private static long systemStartTime;

    public static void main(String[] args) {

        systemStartTime = System.currentTimeMillis();

        em = new EventManager(systemStartTime);

        try {
            new Bot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static EventManager getEm() {
        return em;
    }

    public static void reloadEventManager(){
        em.reset();
    }
}
