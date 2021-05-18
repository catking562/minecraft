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
		if(messageconfig.getString("���ξ�") == null) {
			messageconfig.set("���ξ�", "&f[ &ateleport &f] ");
		}
		if(messageconfig.getString("list") == null) {
			messageconfig.set("list", "&f��Ż ����Ʈ :");
		}
		if(messageconfig.getString("listanything") == null) {
			messageconfig.set("listanything", "&c������ ��Ż�� �����ϴ�.");
		}
		if(messageconfig.getString("whatiscmd") == null) {
			messageconfig.set("whatiscmd", "&c... �̰� ���� ��ɾ���? �� �˾Ƶ�ھ��!");
		}
		if(messageconfig.getString("itisthis") == null) {
			messageconfig.set("itisthis", "&c�̹� �����ϴ� ��Ż�Դϴ�.");
		}
		if(messageconfig.getString("create") == null) {
			messageconfig.set("create", "&a���ο� ��Ż�� �����Ͽ����ϴ�.");
		}
		if(messageconfig.getString("whatisthis") == null) {
			messageconfig.set("whatisthis", "&c�������� �ʴ� ��Ż�Դϴ�.");
		}
		if(messageconfig.getString("remove") == null) {
			messageconfig.set("remove", "&a�ش� ��Ż�� �����Ǿ����ϴ�.");
		}
		if(messageconfig.getString("npc") == null) {
			messageconfig.set("npc", "&aĿ����npc�� ������");
		}
		if(messageconfig.getString("notsave") == null) {
			messageconfig.set("notsave", "&c���忡 �����߽��ϴ�.");
		}
		if(messageconfig.getString("donhasper") == null) {
			messageconfig.set("donhasper", "&c������ �����ϴ�.!");
		}
		if(messageconfig.getString("thisloc") == null) {
			messageconfig.set("thisloc", "��a������ġ");
		}
		if(messageconfig.getString("thisisloc") == null) {
			messageconfig.set("thisisloc", "��c������ġ�Դϴ�.!");
		}
		if(messageconfig.getString("cancel") == null) {
			messageconfig.set("cancel", "��f��ҵǾ����ϴ�.");
		}
		if(messageconfig.getString("replacename") == null) {
			messageconfig.set("replacename", Arrays.asList("��fä�ÿ� ������ �̸��� �Է����ּ���","��f�Ǵ� ��c��� ��f��� �Է��Ͽ� ��� �� �� �ֽ��ϴ�."));
		}
		if(messageconfig.getString("replacenametrue") == null) {
			messageconfig.set("replacenametrue", "��a�̸��� ���������� �ٲپ����ϴ�.");
		}
		if(messageconfig.getString("doncmd") == null) {
			messageconfig.set("doncmd", "��cĿ�ǵ� ��������!!! �̸����� ���ϼž���!");
		}
		try {
			messageconfig.save(messagefile);
			System.out.println("[ teleport ] �÷����� ������ ����(�Ǵ� ����)�Ͽ����ϴ�.");
		} catch (IOException e) {
			System.out.println("[ teleport ] �÷����� ������ ����(�Ǵ� ����)�ϴµ� ������ �߻��Ͽ����ϴ�.");
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
									        	Inventory inv = Bukkit.createInventory((InventoryHolder) p, 54, portalconfig.getString(string + ".name").replace("&", "��"));
										        if(portalconfig.getStringList(string + ".portallist") != null) {
													for(String string1 : portalconfig.getStringList(string + ".portallist")) {
														ItemStack i = new ItemStack(portalconfig.getItemStack(string + ".portalitem." + string1));
														ItemMeta m = i.getItemMeta();
														m.setDisplayName(portalconfig.getString(string + ".portalname." + string1).replace("&", "��"));
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
											p.sendMessage(messageconfig.getString("���ξ�") + messageconfig.getString("donhasper"));
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
			if(command.getName().contentEquals("portal") || command.getName().contentEquals("��Ż")) {
				if(args.length == 0) {
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż - ��Ż��ɾ���� ���ϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ���� ��ġ�� ��Ż�� �����մϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż npc <�̸�> <npc> - �ش� npc�� Ŭ���ϸ� ��Ż�� Ż �� �ְ� ����ϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� ���ϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż �߰� <������Ż�̸�> <���ο���Ż�̸�> - ���� ��ġ�� �ش���Ż�� ���ο���ġ�� �߰��մϴ�.");
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ��� - ��Ż ����� Ȯ���մϴ�.");
				}else if(args.length == 1) {
					if(args[0].toString().contentEquals("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
					}else if(args[0].toString().contentEquals("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
					}else if(args[0].toString().contentEquals("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
					}else if(args[0].toString().contentEquals("���")) {
						if(portalconfig.getList("list") != null) {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("list").replace("&", "��"));
							sender.sendMessage(portalconfig.getList("list").toString());
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("listanything").replace("&", "��"));
						}
					}else if(args[0].toString().contentEquals("npc")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż npc <�̸�> <npc> - �ش� npc�� Ŭ���ϸ� ��Ż�� Ż �� �ְ� ����ϴ�.");
					}else if(args[0].toString().contentEquals("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� ���ϴ�.");
					}else if(args[0].toString().equalsIgnoreCase("�߰�")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż �߱� <������Ż�̸�> <���ο���Ż�̸�> - ���� ��ġ�� �ش���Ż�� ���ο���ġ�� �߰��մϴ�.");
					}else {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatiscmd").replace("&", "��"));
					}
				}else if(args.length == 2) {
					if(args[0].toString().equalsIgnoreCase("����")) {
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
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("create").replace("&", "��"));
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("itisthis").replace("&", "��"));
						}
					}else if(args[0].toString().equalsIgnoreCase("����")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									b = true;
								}
							}
							if(b) {
								Inventory inv = Bukkit.createInventory((InventoryHolder) sender, 9, portalconfig.getString(args[1] + ".name").replace("&", "��") + "��f����");
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
								m.setDisplayName("��a��Ż �̸�����");
								m.setLore(Arrays.asList("��5��Ż�� �̸��� �����մϴ�."));
								m1.setDisplayName("��a����Ż �̸�����");
								m1.setLore(Arrays.asList("��5����Ż�� �̸��� �����մϴ�."));
								m2.setDisplayName("��aGUI ��ġ");
								m2.setLore(Arrays.asList("��5��Ż�� �������� ������ GUI�� �����մϴ�."));
								m3.setDisplayName("��a��Ż ������ ����");
								m3.setLore(Arrays.asList("��5GUI�� ������ �������� �����մϴ�."));
								m4.setDisplayName("��a����Ż ����");
								m4.setLore(Arrays.asList("��5������ ����Ż�� �����մϴ�."));
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
								sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
						}
					}else if(args[0].toString().equalsIgnoreCase("����")) {
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
						        sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("remove").replace("&", "��"));
							}else {
								sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
						}
					}else if(args[0].toString().equalsIgnoreCase("npc")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż npc <�̸�> <npc> - �ش� npc�� Ŭ���ϸ� ��Ż�� Ż �� �ְ� ����ϴ�.");
					}else if(args[0].toString().equalsIgnoreCase("����")) {
						if(portalconfig.getStringList("list") != null) {
							boolean b = false;
							for(String string : portalconfig.getStringList("list")) {
								if(string.equalsIgnoreCase(args[1])) {
									b = true;
								}
							}
							if(b) {
						        Inventory inv = Bukkit.createInventory((InventoryHolder) sender, 54, portalconfig.getString(args[1].toString() + ".name").replace("&", "��"));
						        if(portalconfig.getStringList(args[1].toString() + ".portallist") != null) {
									for(String string1 : portalconfig.getStringList(args[1].toString() + ".portallist")) {
										ItemStack i = new ItemStack(portalconfig.getItemStack(args[1].toString() + ".portalitem." + string1));
										ItemMeta m = i.getItemMeta();
										m.setDisplayName(portalconfig.getString(args[1].toString() + ".portalname." + string1).replace("&", "��"));
										i.setItemMeta(m);
										int cx = Integer.parseInt(portalconfig.getString(args[1].toString() + ".portalx." + string1));
										int cy = Integer.parseInt(portalconfig.getString(args[1].toString() + ".portaly." + string1));
										int c = ((cx + 1) * (cy + 1)) - 1;
										inv.setItem(c, i);
									}
						        }
						        ((Player) sender).openInventory(inv);
							}else {
								sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
						}
					}else if(args[0].toString().equalsIgnoreCase("�߰�")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż �߰� <������Ż�̸�> <���ο���Ż�̸�> - ���� ��ġ�� �ش���Ż�� ���ο���ġ�� �߰��մϴ�.");
					}else {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatiscmd"));
					}
				}else if(args.length == 3) {
					if(args[0].toString().equalsIgnoreCase("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ���� ��ġ�� ��Ż�� �����մϴ�.");
					}else if(args[0].toString().equalsIgnoreCase("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
					}else if(args[0].toString().equalsIgnoreCase("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� �����մϴ�.");
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
						        sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("npc").replace("&", "��"));
							}else {
								sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
						}
					}else if(args[0].toString().equalsIgnoreCase("����")) {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + "/��Ż ���� <�̸�> - ��Ż�� ���ϴ�.");
					}else if(args[0].toString().equalsIgnoreCase("�߰�")) {
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
							        	sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("create").replace("&", "��"));
						        	}else {
						        		sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("itisthis").replace("&", "��"));
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
							        	sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("create").replace("&", "��"));
						        	}else {
						        		sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("itisthis").replace("&", "��"));
						        	}
						        }
							}else {
								sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
							}
						}else {
							sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatisthis").replace("&", "��"));
						}
					}else {
						sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatiscmd").replace("&", "��"));
					}
				}else {
					sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("whatiscmd").replace("&", "��"));
				}
			}
		}else {
			sender.sendMessage("[ portal ] �÷��̾ �Է��ؾ��մϴ�.");
		}
		try {
			portalconfig.save(portalFile);
		} catch (IOException e) {
			sender.sendMessage(messageconfig.getString("���ξ�").replace("&", "��") + messageconfig.getString("notsave").replace("&", "��"));
		}
		return super.onCommand(sender, command, label, args);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
	    YamlConfiguration portalconfig = main.config.get("portal");
		if(command.getName().contentEquals("portal") || command.getName().equalsIgnoreCase("��Ż")) {
			ArrayList<String> list = new ArrayList<String>();
			if(args.length == 1) {
				list.add("����");
				list.add("����");
				list.add("����");
				list.add("npc");
				list.add("����");
				list.add("�߰�");
				list.add("���");
			}else if(args.length == 2) {
				if(args[0].toString().equalsIgnoreCase("����")) {
					list.add("<�̸�>");
				}else if(args[0].toString().equalsIgnoreCase("����")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("����")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("npc")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("����")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("�߰�")) {
					for(String string : portalconfig.getStringList("list")) {
						list.add(string);
					}
				}else if(args[0].toString().equalsIgnoreCase("���")) {
					list = null;
				}else {
					list = null;
				}
			}else if(args.length == 3) {
				if(args[0].toString().equalsIgnoreCase("����")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("����")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("����")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("npc")) {
					list.add("<npc�̸�>");
				}else if(args[0].toString().equalsIgnoreCase("����")) {
					list = null;
				}else if(args[0].toString().equalsIgnoreCase("�߰�")) {
					list.add("<����Ż�̸�>");
				}else if(args[0].toString().equalsIgnoreCase("���")) {
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
