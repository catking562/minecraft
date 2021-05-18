package portal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class main extends JavaPlugin{
	
	static Map<String, File> file = new HashMap<String, File>();
	static Map<String, YamlConfiguration> config = new HashMap<String, YamlConfiguration>();
	static Map<Player, String> replacename = new HashMap<Player, String>();
	static Map<Player, String> replacepname = new HashMap<Player, String>();
	static Map<Player, String> replaceitem = new HashMap<Player, String>();
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new rightclicknpc(), this);
		Bukkit.getPluginManager().registerEvents(new invenclick(), this);
		Bukkit.getPluginManager().registerEvents(new chat(), this);
		Bukkit.getPluginManager().registerEvents(new cmd(), this);
		Bukkit.getPluginManager().registerEvents(new invenclose(), this);
		useportal();
		File messagefile = new File(getDataFolder() + "/message.yml");
		YamlConfiguration messageconfig = YamlConfiguration.loadConfiguration(messagefile);
		File portalfile = new File(getDataFolder() + "/portal.yml");
		YamlConfiguration portalconfig = YamlConfiguration.loadConfiguration(portalfile);
		file.put("message", messagefile);
		config.put("message", messageconfig);
		file.put("portal", portalfile);
		config.put("portal", portalconfig);
		if(messageconfig.getString("접두어") == null) {
			messageconfig.set("접두어", "&f[ &ateleport &f] ");
		}
		if(messageconfig.getString("list") == null) {
			messageconfig.set("list", "&f포탈 리스트 :");
		}
		if(messageconfig.getString("listanything") == null) {
			messageconfig.set("listanything", "&c생성한 포탈이 없습니다.");
		}
		if(messageconfig.getString("whatiscmd") == null) {
			messageconfig.set("whatiscmd", "&c... 이게 무슨 명령어죠? 못 알아듣겠어요!");
		}
		if(messageconfig.getString("itisthis") == null) {
			messageconfig.set("itisthis", "&c이미 존재하는 포탈입니다.");
		}
		if(messageconfig.getString("create") == null) {
			messageconfig.set("create", "&a새로운 포탈을 생성하였습니다.");
		}
		if(messageconfig.getString("whatisthis") == null) {
			messageconfig.set("whatisthis", "&c존재하지 않는 포탈입니다.");
		}
		if(messageconfig.getString("remove") == null) {
			messageconfig.set("remove", "&a해당 포탈이 삭제되었습니다.");
		}
		if(messageconfig.getString("npc") == null) {
			messageconfig.set("npc", "&a커스텀npc와 연동됨");
		}
		if(messageconfig.getString("notsave") == null) {
			messageconfig.set("notsave", "&c저장에 실패했습니다.");
		}
		if(messageconfig.getString("donhasper") == null) {
			messageconfig.set("donhasper", "&c권한이 없습니다.!");
		}
		if(messageconfig.getString("thisloc") == null) {
			messageconfig.set("thisloc", "§a현재위치");
		}
		if(messageconfig.getString("thisisloc") == null) {
			messageconfig.set("thisisloc", "§c현재위치입니다.!");
		}
		if(messageconfig.getString("cancel") == null) {
			messageconfig.set("cancel", "§f취소되었습니다.");
		}
		if(messageconfig.getString("replacename") == null) {
			messageconfig.set("replacename", Arrays.asList("§f채팅에 변경할 이름을 입력해주세요","§f또는 §c취소 §f라고 입력하여 취소 할 수 있습니다."));
		}
		if(messageconfig.getString("replacenametrue") == null) {
			messageconfig.set("replacenametrue", "§a이름을 성공적으로 바꾸었습니다.");
		}
		if(messageconfig.getString("doncmd") == null) {
			messageconfig.set("doncmd", "§c커맨드 금지에욧!!! 이름먼저 정하셔야죠!");
		}
		try {
			messageconfig.save(messagefile);
			System.out.println("[ teleport ] 플러그인 파일을 생성(또는 저장)하였습니다.");
		} catch (IOException e) {
			System.out.println("[ teleport ] 플러그인 파일을 생성(또는 저장)하는데 오류가 발생하였습니다.");
		}
	}
	
	public void useportal() {
		BukkitRunnable brun = new BukkitRunnable() {
			@Override
			public void run() {
				File messagefile = file.get("message");
				YamlConfiguration messageconfig = config.get("message");
				File portalFile = file.get("portal");
				YamlConfiguration portalconfig = config.get("portal");
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.isSneaking()) {
						if(portalconfig.get("list") != null) {
							for(String string : portalconfig.getStringList("list")) {
								for(String string2 : portalconfig.getStringList(string + ".portallist")) {
									if(portalconfig.getLocation(string + ".portallocation." + string2).distance(p.getLocation()) <= 1D) {
										if(p.hasPermission("portal.use")) {
									        boolean b1 = true;
									        int a = 0;
									        if(portalconfig.getStringList(string + ".portallist") != null) {
									        	Inventory inv = Bukkit.createInventory((InventoryHolder) p, 54, portalconfig.getString(string + ".name").replace("&", "§"));
										        if(portalconfig.getStringList(string + ".portallist") != null) {
													for(String string1 : portalconfig.getStringList(string + ".portallist")) {
														ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
														ItemMeta m = i.getItemMeta();
														m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "§"));
														if(string2.contentEquals(string1)) {
															m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
															m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
														}
														i.setItemMeta(m);
														int cx = Integer.parseInt(portalconfig.getString(string + ".portalx." + string1));
														int cy = Integer.parseInt(portalconfig.getString(string + ".portaly." + string1));
														int c = ((cx + 1) * (cy + 1)) - 1;
														inv.setItem(c, i);
													}
										        }
										        ((Player) p).openInventory(inv);
									        }
										}else {
											p.sendMessage(messageconfig.getString("접두어") + messageconfig.getString("donhasper"));
										}
									}
								}
							}
						}
					}
				}
			}
		};brun.runTaskTimer(this, 0, 0);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		File messagefile = file.get("message");
		YamlConfiguration messageconfig = config.get("message");
		File portalFile = file.get("portal");
		YamlConfiguration portalconfig = config.get("portal");
		if(sender instanceof Player) {
			if(command.getName().contentEquals("portal") || command.getName().contentEquals("포탈")) {
				if(args.length == 0) {
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 - 포탈명령어들을 봅니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 생성 <이름> - 현재 위치에 포탈을 생성합니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 설정 <이름> - 포탈을 설정합니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 삭제 <이름> - 포탈을 삭제합니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 npc <이름> <npc> - 해당 npc를 클릭하면 포탈을 탈 수 있게 만듭니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 열기 <이름> - 포탈을 엽니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 추가 <기존포탈이름> <새로운포탈이름> - 현재 위치에 해당포탈의 새로운위치를 추가합니다.");
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 목록 - 포탈 목록을 확인합니다.");
				}else if(args.length == 1) {
					if(args[0].toString().contentEquals("생성")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 생성 <이름> - 포탈을 생성합니다.");
					}else if(args[0].toString().contentEquals("설정")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 설정 <이름> - 포탈을 설정합니다.");
					}else if(args[0].toString().contentEquals("삭제")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 삭제 <이름> - 포탈을 삭제합니다.");
					}else if(args[0].toString().contentEquals("목록")) {
						if(portalconfig.getList("list") != null) {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("list").replace("&", "§"));
							sender.sendMessage(portalconfig.getList("list").toString());
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("listanything").replace("&", "§"));
						}
					}else if(args[0].toString().contentEquals("npc")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 npc <이름> <npc> - 해당 npc를 클릭하면 포탈을 탈 수 있게 만듭니다.");
					}else if(args[0].toString().contentEquals("열기")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 열기 <이름> - 포탈을 엽니다.");
					}else if(args[0].toString().equalsIgnoreCase("추가")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 추기 <기존포탈이름> <새로운포탈이름> - 현재 위치에 해당포탈의 새로운위치를 추가합니다.");
					}else {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatiscmd").replace("&", "§"));
					}
				}else if(args.length == 2) {
					if(args[0].toString().equalsIgnoreCase("생성")) {
						boolean b = false;
						for(String string : portalconfig.getStringList("list")) {
							if(string.equalsIgnoreCase(args[1].toString())) {
								b = true;
							}
						}
						if(!b) {
							if(portalconfig.getStringList("list") != null) {
								ArrayList<String> llist = (ArrayList<String>) portalconfig.getStringList("list");
								llist.add(args[1].toString());
								portalconfig.set("list", llist);
								portalconfig.set(args[1].toString() + ".name", args[1].toString());
								portalconfig.set(args[1].toString() + ".portallist", new ArrayList<String>());
								portalconfig.set(args[1].toString() + ".portallocation", null);
								portalconfig.set(args[1].toString() + ".npc", "");
								portalconfig.set(args[1].toString() + ".portalx", null);
								portalconfig.set(args[1].toString() + ".portalitem", null);
								portalconfig.set(args[1].toString() + ".portalname", null);
								portalconfig.set(args[1].toString() + ".portaly", null);
							}else {
								ArrayList<String> llist = new ArrayList<String>();
								llist.add(args[1].toString());
								portalconfig.set("list", llist);
								portalconfig.set(args[1].toString() + ".name", args[1].toString());
								portalconfig.set(args[1].toString() + ".portallist", new ArrayList<String>());
								portalconfig.set(args[1].toString() + ".portallocation", null);
								portalconfig.set(args[1].toString() + ".npc", "");
								portalconfig.set(args[1].toString() + ".portalx", null);
								portalconfig.set(args[1].toString() + ".portalitem", null);
								portalconfig.set(args[1].toString() + ".portalname", null);
								portalconfig.set(args[1].toString() + ".portaly", null);
							}
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("create").replace("&", "§"));
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("itisthis").replace("&", "§"));
						}
					}else if(args[0].toString().equalsIgnoreCase("설정")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									b = true;
								}
							}
							if(b) {
								Inventory inv = Bukkit.createInventory((InventoryHolder) sender, 9, portalconfig.getString(args[1] + ".name").replace("&", "§") + "§f설정");
								ItemStack i = new ItemStack(Material.PAPER);
								ItemStack i1 = new ItemStack(Material.WRITTEN_BOOK);
								ItemStack i2 = new ItemStack(Material.CHEST);
								ItemStack i3 = new ItemStack(Material.ITEM_FRAME);
								ItemStack i4 = new ItemStack(Material.BARRIER);
								ItemMeta m = i.getItemMeta();
								ItemMeta m1 = i1.getItemMeta();
								ItemMeta m2 = i2.getItemMeta();
								ItemMeta m3 = i3.getItemMeta();
								ItemMeta m4 = i4.getItemMeta();
								m.setDisplayName("§a포탈 이름변경");
								m.setLore(Arrays.asList("§5포탈의 이름을 변경합니다."));
								m1.setDisplayName("§a부포탈 이름변경");
								m1.setLore(Arrays.asList("§5부포탈의 이름을 변경합니다."));
								m2.setDisplayName("§aGUI 배치");
								m2.setLore(Arrays.asList("§5포탈을 열었을때 나오는 GUI를 설정합니다."));
								m3.setDisplayName("§a포탈 아이템 변경");
								m3.setLore(Arrays.asList("§5GUI에 나오는 아이템을 변경합니다."));
								m4.setDisplayName("§a부포탈 삭제");
								m4.setLore(Arrays.asList("§5선택한 부포탈을 삭제합니다."));
								m.addEnchant(Enchantment.ARROW_FIRE, 1, true);
								m1.addEnchant(Enchantment.ARROW_FIRE, 1, true);
								m2.addEnchant(Enchantment.ARROW_FIRE, 1, true);
								m3.addEnchant(Enchantment.ARROW_FIRE, 1, true);
								m4.addEnchant(Enchantment.ARROW_FIRE, 1, true);
								m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								m1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								m2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								m3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								m4.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								i.setItemMeta(m);
								i1.setItemMeta(m1);
								i2.setItemMeta(m2);
								i3.setItemMeta(m3);
								i4.setItemMeta(m4);
								inv.setItem(2, i);
								inv.setItem(3, i1);
								inv.setItem(4, i2);
								inv.setItem(5, i3);
								inv.setItem(6, i4);
								((Player) sender).openInventory(inv);
							}else {
								sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
						}
					}else if(args[0].toString().equalsIgnoreCase("삭제")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							int a = 0;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									System.out.println(portalconfig.getStringList("list").get(a));
									ArrayList<String> list = (ArrayList<String>) portalconfig.getStringList("list");
									list.remove(a);
									System.out.println(list);
									portalconfig.set("list", list);
									portalconfig.set(string, null);
									b = true;
								}
								a++;
							}
							if(b) {
						        sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("remove").replace("&", "§"));
							}else {
								sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
						}
					}else if(args[0].toString().equalsIgnoreCase("npc")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 npc <이름> <npc> - 해당 npc를 클릭하면 포탈을 탈 수 있게 만듭니다.");
					}else if(args[0].toString().equalsIgnoreCase("열기")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									b = true;
								}
							}
							if(b) {
						        Inventory inv = Bukkit.createInventory((InventoryHolder) sender, 54, portalconfig.getString(args[1].toString() + ".name").replace("&", "§"));
						        if(portalconfig.getStringList(args[1].toString() + ".portallist") != null) {
									for(String string1 : portalconfig.getStringList(args[1].toString() + ".portallist")) {
										ItemStack i = new ItemStack(portalconfig.getItemStack(args[1].toString() + ".portalitem." + string1));
										ItemMeta m = i.getItemMeta();
										m.setDisplayName(portalconfig.getString(args[1].toString() + ".portalname." + string1).replace("&", "§"));
										i.setItemMeta(m);
										int cx = Integer.parseInt(portalconfig.getString(args[1].toString() + ".portalx." + string1));
										int cy = Integer.parseInt(portalconfig.getString(args[1].toString() + ".portaly." + string1));
										int c = ((cx + 1) * (cy + 1)) - 1;
										inv.setItem(c, i);
									}
						        }
						        ((Player) sender).openInventory(inv);
							}else {
								sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
						}
					}else if(args[0].toString().equalsIgnoreCase("추가")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 추가 <기존포탈이름> <새로운포탈이름> - 현재 위치에 해당포탈의 새로운위치를 추가합니다.");
					}else {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatiscmd"));
					}
				}else if(args.length == 3) {
					if(args[0].toString().equalsIgnoreCase("생성")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 생성 <이름> - 현재 위치에 포탈을 생성합니다.");
					}else if(args[0].toString().equalsIgnoreCase("설정")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 설정 <이름> - 포탈을 설정합니다.");
					}else if(args[0].toString().equalsIgnoreCase("삭제")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 삭제 <이름> - 포탈을 삭제합니다.");
					}else if(args[0].toString().equalsIgnoreCase("npc")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									b = true;
								}
							}
							if(b) {
						        portalconfig.getString(args[1].toString() + ".npc", args[2]);
						        sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("npc").replace("&", "§"));
							}else {
								sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
						}
					}else if(args[0].toString().equalsIgnoreCase("열기")) {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + "/포탈 열기 <이름> - 포탈을 엽니다.");
					}else if(args[0].toString().equalsIgnoreCase("추가")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									b = true;
								}
							}
							if(b) {
						        if(portalconfig.getStringList(args[1].toString() + ".portallist").size() != 0) {
						        	boolean t = true;
						        	for(String s : portalconfig.getStringList(args[1].toString() + ".portallist")) {
						        		if(s.contentEquals(args[2])) {
						        			t = false;
						        		}
						        	}
						        	if(t) {
						        		ArrayList<String> list = (ArrayList<String>) portalconfig.getStringList(args[1].toString() + ".portallist");
							        	list.add(args[2].toString());
							        	portalconfig.set(args[1].toString() + ".portallist", list);
							        	portalconfig.set(args[1].toString() + ".portallocation." + args[2].toString(), ((Player) sender).getLocation());
							        	int x = 0;
							        	int y = 0;
							        	boolean b1 = true;
							        	while (b1) {
											for(String string : portalconfig.getStringList(args[1].toString() + ".portallist")) {
												if(portalconfig.getString(args[1].toString() + ".portalx." + string) != null) {
													if(!(portalconfig.getString(args[1].toString() + ".portalx." + string).equalsIgnoreCase(x + "") && portalconfig.getString(args[1].toString() + ".portaly." + string).equalsIgnoreCase(y + ""))) {
														b1 = false;
													}else {
														if(x != 8) {
															x++;
														}else {
															y++;
															x = 0;
														}
													}
												}
											}
										}
							        	portalconfig.set(args[1].toString() + ".portalx." + args[2].toString(), x);
							        	portalconfig.set(args[1].toString() + ".portaly." + args[2].toString(), y);
							        	portalconfig.set(args[1].toString() + ".portalname." + args[2].toString(), args[2].toString());
							        	portalconfig.set(args[1].toString() + ".portalitem." + args[2].toString(), new ItemStack(Material.STONE));
							        	sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("create").replace("&", "§"));
						        	}else {
						        		sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("itisthis").replace("&", "§"));
						        	}
						        }else {
						        	boolean b2 = false;
						        	for(String string : portalconfig.getStringList(args[1].toString() + ".portallist")) {
						        		if(string.equalsIgnoreCase(args[2].toString())) {
						        			b2 = true;
						        		}
						        	}
						        	if(!b2) {
						        		ArrayList<String> list = (ArrayList<String>) portalconfig.getStringList(args[1].toString() + ".portallist");
							        	list.add(args[2].toString());
							        	portalconfig.set(args[1].toString() + ".portallist", list);
							        	portalconfig.set(args[1].toString() + ".portallocation." + args[2].toString(), ((Player) sender).getLocation());
							        	int x = 0;
							        	int y = 0;
							        	portalconfig.set(args[1].toString() + ".portalx." + args[2].toString(), x);
							        	portalconfig.set(args[1].toString() + ".portaly." + args[2].toString(), y);
							        	portalconfig.set(args[1].toString() + ".portalname." + args[2].toString(), args[2].toString());
							        	portalconfig.set(args[1].toString() + ".portalitem." + args[2].toString(), new ItemStack(Material.STONE));
							        	sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("create").replace("&", "§"));
						        	}else {
						        		sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("itisthis").replace("&", "§"));
						        	}
						        }
							}else {
								sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatisthis").replace("&", "§"));
						}
					}else {
						sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatiscmd").replace("&", "§"));
					}
				}else {
					sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("whatiscmd").replace("&", "§"));
				}
			}
		}else {
			sender.sendMessage("[ portal ] 플레이어가 입력해야합니다.");
		}
		try {
			portalconfig.save(portalFile);
		} catch (IOException e) {
			sender.sendMessage(messageconfig.getString("접두어").replace("&", "§") + messageconfig.getString("notsave").replace("&", "§"));
		}
		return super.onCommand(sender, command, label, args);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
	    YamlConfiguration portalconfig = main.config.get("portal");
		if(command.getName().contentEquals("portal") || command.getName().equalsIgnoreCase("포탈")) {
			ArrayList<String> list = new ArrayList<String>();
			if(args.length == 1) {
				list.add("생성");
				list.add("설정");
				list.add("삭제");
				list.add("npc");
				list.add("열기");
				list.add("추가");
				list.add("목록");
			}else if(args.length == 2) {
				if(args[0].toString().equalsIgnoreCase("생성")) {
					list.add("<이름>");
				}else if(args[0].toString().equalsIgnoreCase("설정")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("삭제")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("npc")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("열기")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("추가")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("목록")) {
					list = null;
				}else {
					list = null;
				}
			}else if(args.length == 3) {
				if(args[0].toString().equalsIgnoreCase("생성")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("설정")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("삭제")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("npc")) {
					list.add("<npc이름>");
				}else if(args[0].toString().equalsIgnoreCase("열기")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("추가")) {
					list.add("<부포탈이름>");
				}else if(args[0].toString().equalsIgnoreCase("목록")) {
					list = null;
				}else {
					list = null;
				}
			}else {
				list = null;
			}
			return list;
		}
		return super.onTabComplete(sender, command, alias, args);
	}
}
