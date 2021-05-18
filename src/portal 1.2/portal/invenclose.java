package portal;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class invenclose implements Listener {
	
	@EventHandler
	public void invenclose(InventoryCloseEvent e) {
		YamlConfiguration messageconfig = main.config.get("message");
		YamlConfiguration portalconfig = main.config.get("portal");
		File portalfile = main.file.get("portal");
		for(String string : portalconfig.getStringList("list")) {
			if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§") + "§f배치설정")) {
				for(int i = 0; i <= e.getView().countSlots() - 1; i++) {
					if(e.getView().getItem(i) != null) {
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							if(e.getView().getItem(i).getType().equals(portalconfig.getItemStack(string + ".portalitem." + string1).getType())) {
								if(e.getView().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"))) {
									int x = i;
									int y = 0;
									portalconfig.set(string + ".portalx." + string1, x + "");
									portalconfig.set(string + ".portaly." + string1, y + "");
								}
							}
						}
					}
				}
			}
		}
		try {
			portalconfig.save(portalfile);
		} catch (IOException e1) {
			e.getPlayer().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("notsave").replace("&", "§"));
		}
	}
}
