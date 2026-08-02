package me.miskynet.customGamemode.utils;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionManager {

    public enum Perm {

        // general commands
        COMMAND_PLAYER_GET_LEVEL("customgamemode.command.player.getLevel", null),

        // settings
        COMMAND_PLAYER_TOGGLE_SCOREBOARD("customgamemode.command.player.togglescoreboard", null),
        COMMAND_PLAYER_SETTINGS("customgamemode.command.player.settings", null),

        // index
        COMMAND_PLAYER_INDEX("customgamemode.command.player.index", null),

        // shop
        COMMAND_PLAYER_SHOP("customgamemode.command.player.shop", null),

        // eco
        COMMAND_PLAYER_ECO_DEFAULT("customgamemode.command.player.eco.*", null),
        COMMAND_PLAYER_ECO_HELP("customgamemode.command.player.eco.help", Perm.COMMAND_PLAYER_ECO_DEFAULT),
        COMMAND_PLAYER_ECO_GET("customgamemode.command.player.eco.get", Perm.COMMAND_PLAYER_ECO_DEFAULT),

        COMMAND_ADMIN_ECO_DEFAULT("customgamemode.command.admin.eco.*", null),
        COMMAND_ADMIN_ECO_ADD("customgamemode.command.admin.eco.add", Perm.COMMAND_ADMIN_ECO_DEFAULT),
        COMMAND_ADMIN_ECO_SET("customgamemode.command.admin.eco.set", Perm.COMMAND_ADMIN_ECO_DEFAULT),
        COMMAND_ADMIN_ECO_REMOVE("customgamemode.command.admin.eco.remove", Perm.COMMAND_ADMIN_ECO_DEFAULT),

        // general admin commands
        COMMAND_ADMIN_RELOAD("customgamemode.command.admin.reload", null);

        private final String permission;
        private final Perm parentPermission;

        /**
         * Constructor for the {@link Perm} enum
         * */
        Perm(String permission, Perm parentPermission) {
            this.permission = permission;
            this.parentPermission = parentPermission;
        }

        /**
         * Returns the permission string in lowercase
         *
         * @return the permission string in lowercase
         * */
        public String toLowerCase() {
            return this.permission.toLowerCase();
        }

        /**
         * Checks if a {@link CommandSender} has the permission for this {@link Perm}
         *
         * @param sender the {@link CommandSender} to check
         * @return true if the {@link CommandSender} has the permission, false otherwise
         * */
        public boolean hasPermission(CommandSender sender) {

            if (sender.hasPermission(this.permission)) return true;

            if (parentPermission != null) {
                return parentPermission.hasPermission(sender);
            }

            return false;
        }
    }
}
