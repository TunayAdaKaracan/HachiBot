package dev.kutuptilkisi.discord;

import dev.kutuptilkisi.discord.listener.EventListener;
import dev.kutuptilkisi.discord.listener.ScriptListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot {

    private final JDA jda;

    public Bot() throws LoginException {
        //


        jda = JDABuilder.createDefault("TOKEN")
                .setActivity(Activity.competing("Other Anime Girls"))
                .addEventListeners(new EventListener())
                .addEventListeners(new ScriptListener()).enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();


    }

    public JDA getJda() {
        return jda;
    }
}
