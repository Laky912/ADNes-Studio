package studio.adnes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossManager implements Listener {
    private final JavaPlugin plugin;
    private final Map<UUID, Integer> phases = new HashMap<UUID, Integer>();
    private final Map<UUID, BossBar> bars = new HashMap<UUID, BossBar>();
    public BossManager(JavaPlugin plugin) { this.plugin=plugin; Bukkit.getPluginManager().registerEvents(this, plugin); }
    public void spawn(Player player) { spawnPhase(player.getLocation(), 1); }
    private void spawnPhase(Location at, int phase) {
        LivingEntity boss=(LivingEntity)at.getWorld().spawnEntity(at, EntityType.VINDICATOR);
        double hp=phase==1?300.0:phase==2?400.0:500.0;
        boss.setMaxHealth(hp); boss.setHealth(hp); boss.setCustomName(ChatColor.DARK_PURPLE+"Shadow Sorcerer"+(phase==3?" Core":""));
        boss.setCustomNameVisible(true); boss.setRemoveWhenFarAway(false);
        phases.put(boss.getUniqueId(), phase);
        BossBar bar=Bukkit.createBossBar(boss.getCustomName(), BarColor.PURPLE, BarStyle.SOLID);
        for(Player p:at.getWorld().getPlayers()) if(p.getLocation().distanceSquared(at)<6400) bar.addPlayer(p);
        bars.put(boss.getUniqueId(),bar);
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable(){ public void run(){
            if(!boss.isValid() || boss.isDead()) { BossBar b=bars.remove(boss.getUniqueId()); if(b!=null)b.removeAll(); return; }
            BossBar b=bars.get(boss.getUniqueId()); if(b!=null)b.setProgress(Math.max(0.0,Math.min(1.0,boss.getHealth()/boss.getMaxHealth())));
        }},1L,5L);
    }
    @EventHandler public void onDeath(EntityDeathEvent event) {
        Integer phase=phases.remove(event.getEntity().getUniqueId()); if(phase==null)return;
        BossBar bar=bars.remove(event.getEntity().getUniqueId()); if(bar!=null)bar.removeAll();
        if(phase<3) {
            final Location loc=event.getEntity().getLocation();
            plugin.getServer().getScheduler().runTaskLater(plugin,new Runnable(){public void run(){spawnPhase(loc,phase+1);}},40L);
        }
    }
    public void cleanup() { for(BossBar b:bars.values())b.removeAll(); bars.clear(); phases.clear(); }
}