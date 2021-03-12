package me.lightdream.grapplinghook;

import com.sun.tools.doclint.HtmlTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Date;

public class GrappCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equals("grapp")){
            if(sender instanceof Player){
                Player player = (Player) sender;

                Block block = player.getTargetBlock(null, 50);

                if(block == null)
                    return true;
                
                if(block.getType() == Material.AIR)
                    return true;

                //System.out.println(block);

                if(block.getLocation().getY() < player.getLocation().getY())
                    return true;

                System.out.println(block.getLocation());

                World world = player.getWorld();
                Location l1 = player.getLocation();
                Location l2 = player.getLocation();

                l1.setX(l1.getX() + getDifX(Grapplinghook.config.getDouble("angle"), player.getLocation().getDirection().getX()));
                l1.setZ(l1.getZ() + getDifZ(Grapplinghook.config.getDouble("angle"), player.getLocation().getDirection().getZ()));

                l2.setX(l2.getX() - getDifX(Grapplinghook.config.getDouble("angle"), player.getLocation().getDirection().getX()));
                l2.setZ(l2.getZ() - getDifZ(Grapplinghook.config.getDouble("angle"), player.getLocation().getDirection().getZ()));


                LivingEntity le1 = (LivingEntity) world.spawnEntity(l1, EntityType.BAT);
                LivingEntity le2 = (LivingEntity) world.spawnEntity(l2, EntityType.BAT);

                LivingEntity holder1 = (LivingEntity) world.spawnEntity(player.getLocation(), EntityType.BAT);
                LivingEntity holder2 = (LivingEntity) world.spawnEntity(player.getLocation(), EntityType.BAT);
                holder1.setAI(false);
                holder2.setAI(false);
                le1.setGliding(true);
                le2.setGliding(true);
                le1.setInvulnerable(true);
                le2.setInvulnerable(true);
                holder1.setInvulnerable(true);
                holder2.setInvulnerable(true);
                le1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                le2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                holder1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                holder2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                le1.setLeashHolder(holder1);
                le2.setLeashHolder(holder2);

                new BukkitRunnable(){
                    final double x = (block.getLocation().getX() - player.getLocation().getX() * 1.0f) / Grapplinghook.config.getDouble("steps") * 1.0f;
                    final double y = Math.max((block.getLocation().getY() - player.getLocation().getY() * 1.0f) / Grapplinghook.config.getDouble("steps") * 1.0f, 0);
                    final double z = (block.getLocation().getZ() - player.getLocation().getZ() * 1.0f) / Grapplinghook.config.getDouble("steps") * 1.0f;

                    int it = 0;

                    @Override
                    public void run() {

                        System.out.println(y);

                        it++;
                        le1.teleport(new Location(world, le1.getLocation().getX() + x, le1.getLocation().getY() + y, le1.getLocation().getZ() + z));
                        le2.teleport(new Location(world, le2.getLocation().getX() + x, le2.getLocation().getY() + y, le2.getLocation().getZ() + z));

                        if(it>= Grapplinghook.config.getDouble("steps") + 1){
                            player.setVelocity(new Vector(block.getLocation().getX() - player.getLocation().getX() * 1.0f, Math.sqrt(block.getLocation().getY() - player.getLocation().getY() * 1.0f) / 2,block.getLocation().getZ() - player.getLocation().getZ() * 1.0f));
                            le1.setHealth(0);
                            le2.setHealth(0);
                            this.cancel();
                        }
                    }

                }.runTaskTimer(Grapplinghook.INSTANCE, 1, 1);
            }
        }



        return true;
    }

    double getDifX(double distance, double angle){
        angle += 1;
        return Math.cos(180 * angle) * distance;
    }

    double getDifZ(double distance, double angle){
        angle += 1;
        return Math.sin(180 * angle) * distance;
    }
}
