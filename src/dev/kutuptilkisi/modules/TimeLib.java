package dev.kutuptilkisi.modules;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.time.LocalDateTime;

public class TimeLib extends TwoArgFunction {
    private final long systemStartTime;

    public TimeLib(long systemStartTime){
        this.systemStartTime = systemStartTime;
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue env) {
        LuaTable table = new LuaTable();
        table.set("systemStartTime", systemStartTime);
        table.set("time", new Time());
        table.set("date", new Date());
        env.get("package").get("loaded").set("TimeLib", table);
        return null;
    }

    static class Time extends ZeroArgFunction{
        @Override
        public LuaValue call() {
            return valueOf(System.currentTimeMillis());
        }
    }

    static class Date extends ZeroArgFunction{

        @Override
        public LuaValue call() {
            LocalDateTime date = LocalDateTime.now();
            LuaTable table = new LuaTable();
            table.set("year", date.getYear());
            table.set("yearday", date.getDayOfYear());
            table.set("month", date.getMonthValue());
            table.set("weekday", date.getDayOfWeek().getValue());
            table.set("day", date.getDayOfMonth());
            table.set("hour", date.getHour());
            table.set("minute", date.getMinute());
            table.set("second", date.getSecond());
            table.set("microsecond", date.getNano());
            return table;
        }
    }
}
