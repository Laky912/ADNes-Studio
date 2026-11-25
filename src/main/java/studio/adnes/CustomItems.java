package studio.adnes;

import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class CustomItems {
    private CustomItems() {}
    public static ItemStack create(AdnesStudio plugin, String id) {
        String key = id.toLowerCase();
        Material material; short durability = 0; String name;
        switch (key) {
            case "nexus_sword": material=Material.DIAMOND_SWORD; durability=1; name="Nexus Sword"; break;
            case "lunar_sword": material=Material.IRON_SWORD; durability=2; name="Lunar Sword"; break;
            case "nexus_helmet": material=Material.DIAMOND_HELMET; durability=3; name="Nexus Helmet"; break;
            case "lunar_helmet": material=Material.IRON_HELMET; durability=4; name="Lunar Helmet"; break;
            default: return null;
        }
        ItemStack item = new ItemStack(material, 1, durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + name);
        meta.setLore(Arrays.asList(ChatColor.GRAY + "ADNes Studio", ChatColor.DARK_GRAY + "ID: " + key));
        item.setItemMeta(meta);
        return item;
    }
}