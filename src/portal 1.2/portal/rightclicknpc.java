package portal;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class rightclicknpc implements Listener {
	
	@EventHandler
	public void rightclicknpc(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked() != null) {
			if(e.getRightClicked() instanceof HumanEntity) {
				YamlConfiguration messageconfig = main.config.get("message");
				YamlConfiguration portalconfig = main.config.get("portal");
				Player p = e.getPlayer();
				for(String string : portalconfig.getStringList("list")) {
					if(portalconfig.getString(string + ".npc").equalsIgnoreCase(e.getRightClicked().getName())) {
						if(p.hasPermission("portal.use")) {
							if(portalconfig.getStringList("list") != null) {
								boolean b = false;
								for(String string2 : portalconfig.getStringList("list")) {
									if(string2.equalsIgnoreCase(string)) {
										b = true;
									}
								}
								if(b) {
							        Inventory inv = Bukkit.createInventory((InventoryHolder) p, 54, messageconfig.getString(string + ".name"));
							        boolean b1 = true;
							        int a = 0;
							        if(portalconfig.getStringList(string + ".portallist") != null) {
							        	for(String string2 : portalconfig.getStringList(string + ".portallist")) {
								        	ItemStack i = new ItemStack(portalconfig.getItemStack(portalconfig.getString(string + ".portalitem." + string2)));
								        	ItemMeta m = i.getItemMeta();
								        	m.setDisplayName(portalconfig.getString(string + ".portalname." + string2));
								        	i.setItemMeta(m);
								        	int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string2));
								        	int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string2));
								        	inv.setItem(((cx + 1) * (cy + 1)) - 1, i);
								        }
							        }
							        ((Player) p).openInventory(inv);
								}else {
									p.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
								}
							}else {
								p.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
							}
						}else {
							p.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("donhasper").replace("&", "§"));
						}
					}
				}
			}
		}
	}
}
