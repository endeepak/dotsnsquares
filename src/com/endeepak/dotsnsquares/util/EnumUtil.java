package com.endeepak.dotsnsquares.util;

public class EnumUtil {
    public static String[] getNames(Class<? extends Enum<?>> e) {
        Enum<?>[] enumConstants = e.getEnumConstants();
        String[] nameValues = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            nameValues[i] = enumConstants[i].name();
        }
        return nameValues;
    }

    public static String[] getStrings(Class<? extends Enum<?>> e) {
        Enum<?>[] enumConstants = e.getEnumConstants();
        String[] strings = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            strings[i] = enumConstants[i].toString();
        }
        return strings;
    }

    public static Enum<?> valueOrDefault(String name, Enum<?> defaultValue) {
        try {
            return Enum.valueOf(defaultValue.getDeclaringClass(), name);
        } catch (IllegalArgumentException ex) {
           return defaultValue;
        }
    }

}
