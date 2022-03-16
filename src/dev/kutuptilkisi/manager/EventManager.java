package dev.kutuptilkisi.manager;

import dev.kutuptilkisi.modules.DiscordLib;
import dev.kutuptilkisi.modules.EventLib;
import dev.kutuptilkisi.modules.TimeLib;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventManager {


    private final HashMap<Long, List<Globals>> files;
    private final HashMap<Long, HashMap<String, List<LuaValue>>> events;

    private long systemStartTime;

    public EventManager(long systemStartTime){

        this.files = new HashMap<>();
        this.events = new HashMap<>();

        this.systemStartTime = systemStartTime;

        loadFiles();
    }

    public void reset(){
        files.clear();
        events.clear();

        loadFiles();
    }

    public void registerEvent(long serverId, String eventType, LuaValue function){
        if(events.get(serverId).containsKey(eventType)){
            events.get(serverId).get(eventType).add(function);
        } else {
            events.get(serverId).put(eventType, new ArrayList<>());
            events.get(serverId).get(eventType).add(function);
        }
    }

    public void unregisterEvent(long serverId, String eventType, LuaValue function){
        if(events.get(serverId).containsKey(eventType)){
            events.get(serverId).get(eventType).remove(function);
            if(events.get(serverId).get(eventType).size() == 0){
                events.get(serverId).remove(eventType);
            }
        } else {
            throw new LuaError("There is no registered event for this function");
        }
    }

    public void loadFiles() {
        File serverFolders = new File("plugins/");

        //noinspection ConstantConditions
        for (File serverFolder : serverFolders.listFiles()) {

            long serverId = Long.parseLong(serverFolder.getName());
            files.put(serverId, new ArrayList<>());
            events.put(serverId, new HashMap<>());

            //noinspection ConstantConditions
            for (File file : serverFolder.listFiles()) {

                // File
                System.out.println("Enabling: " + file.getPath());
                Globals luaFile = JsePlatform.standardGlobals();

                //Load Packages
                luaFile.load(new DiscordLib());
                luaFile.load(new EventLib(serverId, this));
                luaFile.load(new TimeLib(systemStartTime));

                // Delete Dangerous Libs
                luaFile.set("os", LuaValue.NIL);

                //Load File
                luaFile.get("dofile").call(LuaValue.valueOf(file.getPath()));

                // Add To Hashmap
                files.get(serverId).add(luaFile);

                // Call start function
                if(luaFile.get("start") != null && luaFile.get("start").isfunction()){
                    luaFile.get("start").call();
                }
            }
        }
    }


    public void sendEventToAll(String o, LuaValue[] args){
        for(HashMap<String, List<LuaValue>> map : events.values()){
            if(map.containsKey(o)) {
                for (LuaValue function : map.get(o)) {
                    processEvent(function, args);
                }
            }
        }
    }

    public void sendEventToServer(long serverId, String o, LuaValue[] args){
        if(events.get(serverId).containsKey(o)) {
            for (LuaValue luaFile : new ArrayList<>(events.get(serverId).get(o))) {
                processEvent(luaFile, args);

            }
        }
    }

    private void processEvent(LuaValue function, LuaValue[] args){
        switch(args.length){
            case 0:
                function.call();
                break;
            case 1:
                function.call(args[0]);
                break;
            case 2:
                function.call(args[0], args[1]);
                break;
            case 3:
                function.call(args[0], args[1], args[2]);
                break;
            default:
                break;
        }
    }

}
