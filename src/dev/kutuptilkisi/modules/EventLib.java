package dev.kutuptilkisi.modules;

import dev.kutuptilkisi.manager.EventManager;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.TwoArgFunction;

public class EventLib extends TwoArgFunction{

    private static EventManager manager;
    private final long serverId;

    public EventLib(long serverId, EventManager manager){
        EventLib.manager = manager;
        this.serverId = serverId;
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue env) {
        LuaTable table = new LuaTable();

        LuaTable eventTypes = new LuaTable();
        eventTypes.set("MessageEvent", "message");
        eventTypes.set("MemberJoinEvent", "memberJoin");
        eventTypes.set("MemberLeaveEvent", "memberLeave");
        eventTypes.set("ReadyEvent", "ready");

        table.set("types", eventTypes);

        table.set("registerEvent", new RegisterEvent());
        table.set("unregisterEvent", new UnRegisterEvent());

        env.get("package").get("loaded").set("EventLib", table);

        return table;
    }

    class RegisterEvent extends TwoArgFunction{

        @Override
        public LuaValue call(LuaValue type, LuaValue function) {

            if(!function.isfunction() || !type.isstring()){
                throw new LuaError("Unexpected Argument");
            }

            manager.registerEvent(EventLib.this.serverId, type.tojstring(), function);

            return null;
        }
    }

    class UnRegisterEvent extends TwoArgFunction{

        @Override
        public LuaValue call(LuaValue type, LuaValue function) {
            if(!function.isfunction() || !type.isstring()){
                throw new LuaError("Unexpected Argument");
            }
            manager.unregisterEvent(EventLib.this.serverId, type.tojstring(), function);
            return null;
        }
    }

}
