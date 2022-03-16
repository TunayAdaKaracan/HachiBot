package dev.kutuptilkisi.discord.context;

import dev.kutuptilkisi.discord.util.LuaUtil;
import net.dv8tion.jda.api.entities.Message;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

public class MessageContext {

    private final Message message;

    public MessageContext(Message message){
        this.message = message;
    }

    public void addReaction(String emoji){
        message.addReaction(emoji).queue();
    }

    public void addReaction(String emoji, LuaValue success){
        if(!success.isfunction()) throw new LuaError("Unexpected Argument");
        message.addReaction(emoji).queue((nothing) -> {
            success.call();
        });
    }

    public void addReaction(String emoji, LuaValue success, LuaValue err){
        if(!success.isfunction() && !success.isnil()) throw new LuaError("Unexpected Argument");
        if(!err.isfunction()) throw new LuaError("Unexpected Argument");
        message.addReaction(emoji).queue((nothing) -> {
            if(success.isfunction()){
                success.call();
            }
        }, (error) -> {
            err.call(LuaUtil.getErrorContext(error));
        });
    }

    public void clearReactions(LuaValue success){
        if(!success.isfunction()) throw new LuaError("Unexpected Argument");
        message.clearReactions().queue((nothing) -> {
            success.call();
        });
    }

    public void clearReactions(LuaValue success, LuaValue err){
        if(!success.isfunction() && !success.isnil()) throw new LuaError("Unexpected Argument");
        if(!err.isfunction()) throw new LuaError("Unexpected Argument");
        message.clearReactions().queue((nothing) -> {
            if(success.isfunction()){
                success.call();
            }
        }, (error) -> {
            err.call(LuaUtil.getErrorContext(error));
        });
    }

    public void clearReaction(String emoji){
        message.clearReactions(emoji).queue();
    }

    public void clearReaction(String emoji, LuaValue success){
        if(!success.isfunction()) throw new LuaError("Unexpected Argument");
        message.clearReactions(emoji).queue((nothing) -> {
            success.call();
        });
    }

    public void clearReactions(String emoji, LuaValue success, LuaValue err){
        if(!success.isfunction() && !success.isnil()) throw new LuaError("Unexpected Argument");
        if(!err.isfunction()) throw new LuaError("Unexpected Argument");
        message.clearReactions(emoji).queue((nothing) -> {
            if(success.isfunction()){
                success.call();
            }
        }, (error) -> {
            err.call(LuaUtil.getErrorContext(error));
        });
    }



}
