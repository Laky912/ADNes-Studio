package studio.adnes;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
public class AbilityManager implements Listener {
 private final Map<String,Long> cd=new HashMap<String,Long>();
 public AbilityManager(JavaPlugin p){Bukkit.getPluginManager().registerEvents(this,p);}
 @EventHandler public void use(PlayerInteractEvent e){if(e.getAction()!=Action.RIGHT_CLICK_AIR&&e.getAction()!=Action.RIGHT_CLICK_BLOCK)return;String id=id(e.getItem());if(id==null)return;if(!(id.contains("sword")||id.contains("staff")||id.contains("hammer")||id.contains("scythe")||id.contains("spear")||id.contains("greatsword")))return;e.setCancelled(true);Player p=e.getPlayer();String k=p.getUniqueId()+id;long now=System.currentTimeMillis();if(cd.containsKey(k)&&cd.get(k)>now){p.sendMessage(ChatColor.GRAY+"Habilidad en espera: "+((cd.get(k)-now+999)/1000)+"s");return;}cd.put(k,now+8000L);cast(p,id);}
 private String id(ItemStack i){if(i==null||!i.hasItemMeta())return null;ItemMeta m=i.getItemMeta();if(!m.hasLore())return null;for(String s:m.getLore())if(s.startsWith(ChatColor.DARK_GRAY+"ID: "))return s.substring((ChatColor.DARK_GRAY+"ID: ").length());return null;}
 private LivingEntity target(Player p,int range){for(Entity e:p.getNearbyEntities(range,range,range))if(e instanceof LivingEntity&&e!=p){Vector d=p.getLocation().getDirection().normalize(),v=e.getLocation().add(0,1,0).toVector().subtract(p.getEyeLocation().toVector()).normalize();if(d.dot(v)>.94)return (LivingEntity)e;}return null;}
 private void cast(Player p,String id){LivingEntity t=target(p,16);Location l=p.getLocation();if(id.contains("staff")||id.contains("sword")){if(t!=null){t.damage(id.startsWith("nexus")?12:9,p);t.getWorld().strikeLightningEffect(t.getLocation());t.setVelocity(new Vector(0,.35,0));}p.getWorld().playSound(l,Sound.ENTITY_WITHER_SHOOT,1,1.2f);p.sendMessage(ChatColor.DARK_PURPLE+"✦ Descarga de sombra");}else if(id.contains("hammer")||id.contains("greatsword")){for(Entity x:p.getNearbyEntities(4,3,4))if(x instanceof LivingEntity&&x!=p){LivingEntity v=(LivingEntity)x;v.damage(10,p);Vector push=v.getLocation().toVector().subtract(l.toVector()).normalize().multiply(1.3);push.setY(.5);v.setVelocity(push);}p.getWorld().playEffect(l,Effect.EXPLOSION_LARGE,1);p.getWorld().playSound(l,Sound.ENTITY_GENERIC_EXPLODE,1,.8f);p.sendMessage(ChatColor.GOLD+"✦ Onda de impacto");}else if(id.contains("scythe")){double heal=0;for(Entity x:p.getNearbyEntities(4,3,4))if(x instanceof LivingEntity&&x!=p){((LivingEntity)x).damage(8,p);heal+=1.5;}p.setHealth(Math.min(p.getMaxHealth(),p.getHealth()+heal));p.getWorld().playEffect(l,Effect.PORTAL,30);p.sendMessage(ChatColor.LIGHT_PURPLE+"✦ Drenaje vital");}else if(id.contains("spear")){if(t!=null){t.damage(11,p);Vector pull=l.toVector().subtract(t.getLocation().toVector()).normalize().multiply(1.2);pull.setY(.25);t.setVelocity(pull);}p.getWorld().playSound(l,Sound.ENTITY_ENDERMEN_TELEPORT,1,1.3f);p.sendMessage(ChatColor.AQUA+"✦ Lanza astral");}}
}