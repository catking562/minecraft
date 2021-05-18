package portal;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class cmd implements Listener {
	
	@EventHandler
	public void oncmd(PlayerCommandPreprocessEvent e) {
		YamlConfiguration messageconfig = main.config.get("message");
		if(main.replacename.get(e.getPlayer()) != null || main.replacepname.get(e.getPlayer()) != null) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(messageconfig.getString("접두어") + messageconfig.getString("doncmd"));
		}
	}
}
