package dev.kutuptilkisi.discord.util;

import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.List;

public class LuaUtil {

    public static LuaValue getTableFromStringList(List<String> stringList){
        LuaTable table = new LuaTable();

        for(int i=1;i<stringList.size()+1;i++){
            table.set(i, LuaValue.valueOf(stringList.get(i)));
        }

        return table;
    }

    public static LuaValue getTableFromIntegerList(List<Integer> intList){
        LuaTable table = new LuaTable();

        for(int i=1;i<intList.size()+1;i++){
            table.set(i, LuaValue.valueOf(intList.get(i-1)));
        }

        return table;
    }

    public static LuaValue getTableFromDoubleList(List<Double> doubleList){
        LuaTable table = new LuaTable();

        for(int i=1;i<doubleList.size()+1;i++){
            table.set(i, LuaValue.valueOf(doubleList.get(i-1)));
        }

        return table;
    }

    public static LuaValue getTableFromBooleanList(List<Boolean> booleanList){
        LuaTable table = new LuaTable();

        for(int i=1;i<booleanList.size()+1;i++){
            table.set(i, LuaValue.valueOf(booleanList.get(i-1)));
        }

        return table;
    }

    public static LuaValue getErrorContext(Throwable er){
        LuaValue luaError = new LuaTable();

        ErrorResponse errorResponse = ((ErrorResponseException)er).getErrorResponse();
        luaError.set("code", errorResponse.getCode());
        luaError.set("type", errorResponse.getMeaning());

        return luaError;
    }

}
