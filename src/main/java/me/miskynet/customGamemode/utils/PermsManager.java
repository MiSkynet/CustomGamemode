package me.miskynet.customGamemode.utils;


public class PermsManager {

    public enum Perms {

        COMMAND_TOGGLE_SCOREBOARD,
        COMMAND_INDEX,
        COMMAND_SHOP,
        COMMAND_SETTINGS,
        COMMAND_ECO_SET,
        COMMAND_ECO_GET,
        COMMAND_ECO_ADD,
        COMMAND_GET_LEVEL;

        public String toLowerString() {return this.name().toLowerCase();}
    }
}
