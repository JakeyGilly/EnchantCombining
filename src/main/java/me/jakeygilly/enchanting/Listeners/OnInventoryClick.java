package me.jakeygilly.enchanting.Listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;
public class OnInventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
            ItemStack weapon = event.getCurrentItem();
            ItemStack enchantbook = event.getCursor();
            if (weapon == null || enchantbook == null) return;
            EnchantmentStorageMeta meta;
            try {
                meta = (EnchantmentStorageMeta) enchantbook.getItemMeta();
            } catch (ClassCastException e) {
                return;
            }
            Map<Enchantment, Integer> enchantbookEnchant = meta.getStoredEnchants();
            Map<Enchantment, Integer> weaponEnchant = weapon.getEnchantments();
            if (enchantbookEnchant.isEmpty()) return;
            for (Enchantment enchantment : enchantbookEnchant.keySet()) {
                if (weaponEnchant.containsKey(enchantment)) {
                    if (weaponEnchant.get(enchantment) < enchantbookEnchant.get(enchantment)) {
                        event.setCancelled(true);
                        return;
                    } else {
                        weapon.removeEnchantment(enchantment);
                    }
                }
            }
            try {
                weapon.addUnsafeEnchantments(enchantbookEnchant);
            } catch (IllegalArgumentException e) {
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
            event.getWhoClicked().getInventory().setItem(event.getSlot(), weapon);
            event.setCursor(null);
        }
    }
}
