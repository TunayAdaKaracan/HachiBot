package dev.kutuptilkisi.modules;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DiscordLib extends TwoArgFunction{

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaTable t = tableOf();
        t.set("sendMessage", new SendMessage());
        env.get("package").get("loaded").set("DiscordLib", t);
        return t;
    }

    static class SendMessage extends TwoArgFunction{

        @Override
        public LuaValue call(LuaValue message, LuaValue channelId) {
            //System.out.println("Message Sended: " + message.toString() + ", " + channelId.toString());
            return valueOf("OK");
        }
    }

}