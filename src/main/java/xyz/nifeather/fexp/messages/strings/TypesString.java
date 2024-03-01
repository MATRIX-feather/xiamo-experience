package xyz.nifeather.fexp.messages.strings;

import xiamomc.pluginbase.Messages.FormattableMessage;

public class TypesString extends AbstractMorphStrings
{
    public static FormattableMessage typeInteger()
    {
        return getFormattable(getKey("int"), "整数");
    }

    public static FormattableMessage typeBoolean()
    {
        return getFormattable(getKey("boolean"), "布尔值（true/false）");
    }

    public static FormattableMessage typeFloat()
    {
        return getFormattable(getKey("float"), "单精度浮点");
    }

    public static FormattableMessage typeDouble()
    {
        return getFormattable(getKey("double"), "双精度浮点");
    }

    public static FormattableMessage typeString()
    {
        return getFormattable(getKey("string"), "字符串");
    }

    private static String getKey(String key)
    {
        return "types." + key;
    }
}
