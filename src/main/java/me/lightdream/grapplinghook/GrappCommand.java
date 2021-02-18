package me.lightdream.grapplinghook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

                Block block = player.getTargetBlock(null, 100);
                World world = player.getWorld();
                Location l1 = player.getLocation();
                Location l2 = player.getLocation();

               //System.out.println(player.getLocation().getDirection().getX());
               //System.out.println(getDifX(0.4f, player.getLocation().getDirection().getX()));
               //l1.setZ(l1.getZ() + getDifZ(0.4f, player.getLocation().getDirection().getZ()));

                l1.setX(l1.getX() + getDifX(0.4f, player.getLocation().getDirection().getX()));
                l1.setZ(l1.getZ() + getDifZ(0.4f, player.getLocation().getDirection().getZ()));

                l2.setX(l2.getX() - getDifX(0.4f, player.getLocation().getDirection().getX()));
                l2.setZ(l2.getZ() - getDifZ(0.4f, player.getLocation().getDirection().getZ()));


                LivingEntity le1 = (LivingEntity) world.spawnEntity(l1, EntityType.BAT);
                LivingEntity le2 = (LivingEntity) world.spawnEntity(l2, EntityType.BAT);
                //le1.setAI(false);
                //le2.setAI(false);
                le1.setGliding(true);
                le2.setGliding(true);
                le1.setInvulnerable(true);
                le2.setInvulnerable(true);
                le1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                le2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
                le1.setLeashHolder(player);
                le2.setLeashHolder(player);

                new BukkitRunnable(){
                    final double x = (block.getLocation().getX() - player.getLocation().getX() * 1.0f) / 20.0f;
                    final double y = (block.getLocation().getY() - player.getLocation().getY() * 1.0f) / 20.0f;
                    final double z = (block.getLocation().getZ() - player.getLocation().getZ() * 1.0f) / 20.0f;

                    int it = 0;

                    @Override
                    public void run() {

                        System.out.println(y);

                        it++;
                        le1.teleport(new Location(world, le1.getLocation().getX() + x, le1.getLocation().getY() + y, le1.getLocation().getZ() + z));
                        le2.teleport(new Location(world, le2.getLocation().getX() + x, le2.getLocation().getY() + y, le2.getLocation().getZ() + z));

                        if(it>=25){
                            //System.out.println(new Vector(block.getLocation().getX() - player.getLocation().getX() * 1.0f, Math.sqrt(block.getLocation().getY() - player.getLocation().getY() * 1.0f) / 2,block.getLocation().getZ() - player.getLocation().getZ() * 1.0f));
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
