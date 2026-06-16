package me.miskynet.customGamemode.custom.menu.shop;

import me.miskynet.customGamemode.custom.item.PlayerHead;
import me.miskynet.customGamemode.custom.item.shop.ShopItem;
import me.miskynet.customGamemode.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ShopListener implements Listener {

    private final Map<UUID, Long> clickCooldown = new HashMap<>();

    @EventHandler
    public void click(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof Shop) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            // make a cooldown so pages don't get skipped when scrolling
            long currentTime = System.currentTimeMillis();

            if (clickCooldown.containsKey(player.getUniqueId())) {

                long lastClick = clickCooldown.get(player.getUniqueId());
                if (currentTime - lastClick < 200) {
                    return;
                }
            }

            clickCooldown.put(player.getUniqueId(), currentTime);

            Shop shop = (Shop) event.getClickedInventory().getHolder();

            if (event.getSlot() == 48) {
                shop.decreasePage();
            }

            if (event.getSlot() == 50) {
                shop.increasePage();
            }

            if (!shop.getEmptySlots().contains(event.getSlot())) {
                Utils.giveItemOrDrop(player, ShopItem.fromItemStack(shop.getItem(event.getSlot())).toItemStack());
            }
        }
    }
}
