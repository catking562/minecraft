package portal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class invenclick implements Listener {
	
	@EventHandler
	public void invenclick(InventoryClickEvent e) {
		YamlConfiguration messageconfig = main.config.get("message");
		YamlConfiguration portalconfig = main.config.get("portal");
		File portalfile = main.file.get("portal");
		if(e.getCurrentItem() != null) {
			for(String string : portalconfig.getStringList("list")) {
				if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§"))) {
					e.setCancelled(true);
					if(e.getCurrentItem().getType() != Material.AIR) {
						if(e.getCurrentItem().getItemMeta().hasDisplayName()) {
							if(e.getCurrentItem().getItemMeta().hasEnchant(Enchantment.ARROW_FIRE)) {
								if(e.getWhoClicked().getOpenInventory() != null) {
									e.getWhoClicked().closeInventory();
								}
								e.getWhoClicked().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("thisisloc").replace("&", "§"));
							}else {
								for(String string1 : portalconfig.getStringList(string + ".portallist")) {
									if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"))) {
										e.getWhoClicked().teleport(portalconfig.getLocation(string + ".portallocation." + string1));
									}
								}
							}
						}
					}
				}else if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§") + "§f설정")) {
					e.setCancelled(true);
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						if(e.getWhoClicked().getOpenInventory() != null) {
							e.getWhoClicked().closeInventory();
						}
						for(String string1 : messageconfig.getStringList("replacename")) {
							e.getWhoClicked().sendMessage(messageconfig.getString("접두어").replace("&", "§") + string1.replace("&", "§"));
						}
						main.replacename.put((Player) e.getWhoClicked(), string);
					}else if(e.getCurrentItem().getType().equals(Material.WRITTEN_BOOK)) {
						if(e.getWhoClicked().getOpenInventory() != null) {
							e.getWhoClicked().closeInventory();
						}
						Inventory inv = Bukkit.createInventory(e.getWhoClicked(), 54, portalconfig.getString(string + ".name").replace("&", "§") + "§f부포탈이름변경");
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
							ItemMeta m = i.getItemMeta();
							m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
							m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
							m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							i.setItemMeta(m);
							int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1));
							int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1));
							int a = ((cx + 1) * (cy + 1)) - 1;
							inv.setItem(a, i);
						}
						e.getWhoClicked().openInventory(inv);
					}else if(e.getCurrentItem().getType().equals(Material.CHEST)) {
						if(e.getWhoClicked().getOpenInventory() != null) {
							e.getWhoClicked().closeInventory();
						}
						Inventory inv = Bukkit.createInventory(e.getWhoClicked(), 54, portalconfig.getString(string + ".name").replace("&", "§") + "§f배치설정");
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
							ItemMeta m = i.getItemMeta();
							m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
							m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
							m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							i.setItemMeta(m);
							int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1));
							int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1));
							int a = ((cx + 1) * (cy + 1)) - 1;
							inv.setItem(a, i);
						}
						e.getWhoClicked().openInventory(inv);
					}else if(e.getCurrentItem().getType().equals(Material.ITEM_FRAME)) {
						if(e.getWhoClicked().getOpenInventory() != null) {
							e.getWhoClicked().closeInventory();
						}
						Inventory inv = Bukkit.createInventory(e.getWhoClicked(), 54, portalconfig.getString(string + ".name").replace("&", "§") + "§f아이템설정");
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
							ItemMeta m = i.getItemMeta();
							m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
							m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
							m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							i.setItemMeta(m);
							int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1));
							int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1));
							int a = ((cx + 1) * (cy + 1)) - 1;
							inv.setItem(a, i);
						}
						e.getWhoClicked().openInventory(inv);
					}else if(e.getCurrentItem().getType().equals(Material.BARRIER)) {
						if(e.getWhoClicked().getOpenInventory() != null) {
							e.getWhoClicked().closeInventory();
						}
						Inventory inv = Bukkit.createInventory(e.getWhoClicked(), 54, portalconfig.getString(string + ".name").replace("&", "§") + "§f부포탈삭제");
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
							ItemMeta m = i.getItemMeta();
							m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
							m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
							m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							i.setItemMeta(m);
							int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1));
							int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1));
							int a = ((cx + 1) * (cy + 1)) - 1;
							inv.setItem(a, i);
						}
						e.getWhoClicked().openInventory(inv);
					}
				}else if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§") + "§f부포탈이름변경")) {
					e.setCancelled(true);
					if(!e.getCurrentItem().getType().equals(Material.AIR)) {
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"))) {
								if(e.getWhoClicked().getOpenInventory() != null) {
									e.getWhoClicked().closeInventory();
								}
								for(String string2 : messageconfig.getStringList("replacename")) {
									e.getWhoClicked().sendMessage(messageconfig.getString("접두어").replace("&", "§") + string2.replace("&", "§"));
								}
								main.replacepname.put((Player) e.getWhoClicked(), string + ".portalname." + string1);
							}
						}
					}
				}else if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§") + "§f아이템설정")) {
					e.setCancelled(true);
					if(!e.getCurrentItem().getType().equals(Material.AIR)) {
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"))) {
								if(e.getWhoClicked().getOpenInventory() != null) {
									e.getWhoClicked().closeInventory();
								}
								Inventory inv = Bukkit.createInventory(e.getWhoClicked(), 27, portalconfig.getString(string + ".name").replace("&", "§") + "§a아이템설정");
								ItemStack i = new ItemStack(Material.RED_STAINED_GLASS_PANE);
								ItemMeta m = i.getItemMeta();
								m.setDisplayName("§c인벤토리에서 원하는 아이템을 클릭하세요");
								i.setItemMeta(m);
								for (int j = 0; j < 27; j++) {
									inv.setItem(j, i);
								}
								main.replaceitem.put((Player) e.getWhoClicked(), string + ".portalitem." + string1);
								e.getWhoClicked().openInventory(inv);
							}
						}
					}
				}else if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§") + "§a아이템설정")) {
					e.setCancelled(true);
					if(!e.getCurrentItem().equals(Material.AIR)) {
						if(e.getCurrentItem().getType() != null) {
							if(main.replaceitem.get(e.getWhoClicked()) != null) {
								String s = main.replaceitem.get(e.getWhoClicked());
								portalconfig.set(s, e.getCurrentItem());
								if(e.getWhoClicked().getOpenInventory() != null) {
									e.getWhoClicked().closeInventory();
								}
								Inventory inv = Bukkit.createInventory(e.getWhoClicked(), 54, portalconfig.getString(string + ".name").replace("&", "§") + "§f아이템설정");
								for(String string1 : portalconfig.getStringList(string + ".portallist")) {
									ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
									ItemMeta m = i.getItemMeta();
									m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
									m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
									m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
									i.setItemMeta(m);
									int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1));
									int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1));
									int a = ((cx + 1) * (cy + 1)) - 1;
									inv.setItem(a, i);
								}
								e.getWhoClicked().openInventory(inv);
							}
						}
					}
				}else if(e.getView().getTitle().equalsIgnoreCase(portalconfig.getString(string + ".name").replace("&", "§") + "§f부포탈삭제")) {
					e.setCancelled(true);
					if(!e.getCurrentItem().equals(Material.AIR)) {
						for(String string1 : portalconfig.getStringList(string + ".portallist")) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"))) {
								int a = 0;
								for(String s : portalconfig.getStringList(string + ".portallist")) {
									ArrayList<String> list = (ArrayList<String>) portalconfig.getStringList(string + ".portallist");
									if(s.contentEquals(string1)) {
										int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + list.get(a)));
										int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + list.get(a)));
										e.getView().setItem(((cx + 1) * (cy + 1)) - 1, null);
										list.remove(a);
										portalconfig.set(string + ".portallist", list);
									}else {
										a++;
									}
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
			e.getWhoClicked().sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("notsave").replace("&", "§"));
			e1.printStackTrace();
		}
	}
}
