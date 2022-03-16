package dev.kutuptilkisi.discord.listener;

import dev.kutuptilkisi.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class EventListener extends ListenerAdapter {


    public void onGuildJoin(GuildJoinEvent e){
        File file = new File("/plugins/"+e.getGuild().getId());
        file.mkdir();

    }

    public void onGuildLeave(GuildLeaveEvent e){
        File file = new File("/plugins/"+e.getGuild().getId());
        for(File script : file.listFiles()){
            script.delete();
        }
        file.delete();
    }

    public void onMessageReceived(MessageReceivedEvent e){
        if(!e.getAuthor().isBot() && e.getAuthor().getId().equals("877141719735476255") && e.getMessage().getContentRaw().equals(">reload")){
            Main.reloadEventManager();
        }
    }


}
