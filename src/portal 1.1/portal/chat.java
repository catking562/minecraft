package portal;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class chat implements Listener {
	
	@EventHandler
	public void onchat(PlayerChatEvent e) {
		YamlConfiguration messageconfig = main.config.get("message");
		YamlConfiguration portalconfig = main.config.get("portal");
		File portalfile = main.file.get("portal");
		if(main.replacename.get(e.getPlayer()) != null) {
			e.setCancelled(true);
			if(e.getMessage().equalsIgnoreCase("취소")) {
				main.replacename.put(e.getPlayer(), null);
				e.getPlayer().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("cancel").replace("&", "§"));
			}else {
				portalconfig.set(main.replacename.get(e.getPlayer()) + ".name", e.getMessage());
				main.replacename.put(e.getPlayer(), null);
				e.getPlayer().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("replacenametrue").replace("&", "§"));
			    try {
					portalconfig.save(portalfile);
				} catch (IOException e1) {
					e.getPlayer().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("notsave").replace("&", "§"));
				}
			}
		}else if(main.replacepname.get(e.getPlayer()) != null) {
			e.setCancelled(true);
			if(e.getMessage().equalsIgnoreCase("취소")) {
				e.getPlayer().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("cancel").replace("&", "§"));
			}else {
				e.getPlayer().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("replacenametrue").replace("&", "§"));
				portalconfig.set(main.replacepname.get(e.getPlayer()), e.getMessage());
			}
			String[] s = main.replacepname.get(e.getPlayer()).replace(".", "#").split("#");
			String string = s[0];
			Inventory inv = Bukkit.createInventory(e.getPlayer(), 54, portalconfig.getString(string + ".name").replace("&", "§") + "§f부포탈이름변경");
			for(String string1 : portalconfig.getStringList(string + ".portallist")) {
				ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
				ItemMeta m = i.getItemMeta();
				m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
				m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
				m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				i.setItemMeta(m);
				int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1).replace("&", "§"));
				int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1).replace("&", "§"));
				int a = ((cx + 1) * (cy + 1)) - 1;
				inv.setItem(a, i);
			}
			e.getPlayer().openInventory(inv);
			main.replacepname.put(e.getPlayer(), null);
		}
	}
}
