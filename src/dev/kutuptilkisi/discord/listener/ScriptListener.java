package dev.kutuptilkisi.discord.listener;

import dev.kutuptilkisi.Main;
import dev.kutuptilkisi.discord.context.MessageContext;
import dev.kutuptilkisi.discord.context.UserContext;
import dev.kutuptilkisi.manager.EventManager;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ScriptListener extends ListenerAdapter {

    private final EventManager em;

    public ScriptListener(){
        em = Main.getEm();
    }

    public void onReady(ReadyEvent e){
        em.sendEventToAll("ready", new LuaValue[]{});
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        // Context's
        MessageContext messageContext = new MessageContext(e.getMessage());
        UserContext userContext = new UserContext(e.getAuthor());

        // Table Create
        LuaTable luaContext = new LuaTable();

        // Table Set
        luaContext.set("message", CoerceJavaToLua.coerce(messageContext));
        luaContext.set("user", CoerceJavaToLua.coerce(userContext));

        // for test purposes
        luaContext.set("member", CoerceJavaToLua.coerce(e.getMember()));
        luaContext.set("guild", CoerceJavaToLua.coerce(e.getGuild()));

        luaContext.set("channel", CoerceJavaToLua.coerce(e.getChannel()));


        // Event Send
        em.sendEventToServer(Long.parseLong(e.getGuild().getId()), "message", new LuaValue[]{luaContext});
    }

}
