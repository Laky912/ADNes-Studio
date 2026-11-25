package studio.adnes;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.attribute.Attribute;

public class AdnesStudio extends JavaPlugin {
    private BossManager bossManager;
    public void onEnable() {
        saveDefaultConfig();
        bossManager = new BossManager(this);
        getLogger().info("ADNes Studio enabled for 1.12.2");
    }
    public void onDisable() { if (bossManager != null) bossManager.cleanup(); }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("adnes")) return false;
        if (args.length == 0) { sender.sendMessage(ChatColor.AQUA + "/adnes <items|give|boss|spawn|reload>"); return true; }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("adnes.admin")) return noPerm(sender);
            reloadConfig(); sender.sendMessage(ChatColor.GREEN + "Configuracion recargada."); return true;
        }
        if (args[0].equalsIgnoreCase("items")) {
            sender.sendMessage(ChatColor.GOLD + "ADNes Studio: Nexus y Lunar");
            sender.sendMessage(ChatColor.YELLOW + "Usa /adnes give <jugador> <nexus_sword|lunar_sword|nexus_helmet|lunar_helmet>");
            return true;
        }
        if (args[0].equalsIgnoreCase("give") && args.length >= 3) {
            if (!sender.hasPermission("adnes.admin")) return noPerm(sender);
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) { sender.sendMessage(ChatColor.RED + "Jugador no encontrado."); return true; }
            ItemStack item = CustomItems.create(this, args[2]);
            if (item == null) { sender.sendMessage(ChatColor.RED + "Objeto desconocido."); return true; }
            target.getInventory().addItem(item); sender.sendMessage(ChatColor.GREEN + "Objeto entregado."); return true;
        }
        if (args[0].equalsIgnoreCase("boss") || args[0].equalsIgnoreCase("spawn")) {
            if (!(sender instanceof Player)) { sender.sendMessage("Solo jugadores."); return true; }
            if (!sender.hasPermission("adnes.admin")) return noPerm(sender);
            bossManager.spawn((Player)sender); sender.sendMessage(ChatColor.DARK_PURPLE + "Shadow Sorcerer invocado."); return true;
        }
        sender.sendMessage(ChatColor.RED + "Subcomando no valido."); return true;
    }
    private boolean noPerm(CommandSender s) { s.sendMessage(ChatColor.RED + "No tienes permiso."); return true; }
}