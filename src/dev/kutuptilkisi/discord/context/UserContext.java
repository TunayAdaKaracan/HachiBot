package dev.kutuptilkisi.discord.context;

import dev.kutuptilkisi.discord.util.LuaUtil;
import net.dv8tion.jda.api.entities.User;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;

public class UserContext {

    private final User user;

    public boolean isBot;
    public String id;
    public String name;
    public String tag;
    public String avatarUrl;
    public String discriminator;
    public boolean hasPrivateChannel;
    public boolean isSystem;
    public String bannerUrl;
    public LuaValue accentColor;

    public UserContext(User user){
        this.user = user;

        isBot = user.isBot();
        id = user.getId();
        name = user.getName();
        tag = user.getAsTag();
        avatarUrl = user.getEffectiveAvatarUrl();
        discriminator = user.getDiscriminator();
        hasPrivateChannel = user.hasPrivateChannel();
        isSystem = user.isSystem();
        user.retrieveProfile().queue((profile) -> bannerUrl =  profile.getBannerUrl());


        accentColor = LuaUtil.getTableFromIntegerList(Arrays.asList(1, 2, 3));

        /* Solving error
        user.retrieveProfile().queue((color) -> {
            if(color.getAccentColor() != null) {
                accentColor = LuaUtil.getTableFromIntegerList(Arrays.asList(
                        color.getAccentColor().getRed(),
                color.getAccentColor().getGreen(),
                color.getAccentColor().getBlue()));
            } else {
                accentColor = new LuaTable();
                accentColor.set(0, LuaValue.NIL);
                accentColor.set(1, LuaValue.NIL);
                accentColor.set(2, LuaValue.NIL);
            }
         }); */
    }

    public void openPrivateChannel(){
        user.openPrivateChannel().queue((channel) -> {
            hasPrivateChannel = true;
        });
    }

    public void openPrivateChannel(LuaValue success){
        if(!success.isfunction()) throw new LuaError("Unexpected Argument");
        user.openPrivateChannel().queue((channel) -> {
            hasPrivateChannel = true;
            success.call(); // send channel context
        });
    }

    public void openPrivateChannel(LuaValue success, LuaValue err){
        if(!success.isfunction() && !success.isnil()) throw new LuaError("Unexpected Argument");
        if(!err.isfunction()) throw new LuaError("Unexpected Argument");

        user.openPrivateChannel().queue((channel) -> {
            hasPrivateChannel = true;
            if(success.isfunction()) {
                success.call(); // send channel context
            }
        }, (error) -> {
            err.call(LuaUtil.getErrorContext(error));
        });

    }

}
