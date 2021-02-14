package br.com.snowdev.swvip.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import br.com.snowdev.swvip.SwKey;
import br.com.snowdev.swvip.SwVIP;
import br.com.snowdev.swvip.interfaces.CommandPermissions;
import br.com.snowdev.swvip.utilities.Messaging;

@CommandPermissions({"swvip.admin", "swvip.keys"})
public class NewKey implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(args.length == 2){
			String group = args[0];
			
			Boolean group_exists = false;
			
			for(String iGroup : SwVIP.instance.getConfig().getStringList("vip_groups")){
				if(iGroup.trim().equalsIgnoreCase(group)){
					group_exists = true;
					group = iGroup.trim();
					break;
				}
			}
			
			if(group_exists){
				int days = Integer.parseInt(args[1]);
				
				if(days > 0){
					SwKey key = this.createNewKey(group, days);
					
					if(key != null) {
						String message = "§fKey: §a{key.code} §f({key.group}) - §a{key.days} §fDias.";
						message.replace("{key.code}", key.code);
						message.replace("{key.group}", key.group);
						message.replace("{key.days}", String.valueOf(key.days));
						
						sender.sendMessage(Messaging.format(message, true, false));
					} else {
						//aconteceu um erro ao criar a key vip
					}
				} else {
					//dias informado é invalido
				}
			} else {
				//grupo que esta tentando criar a key vip não existe
			}
		} else {
			//comando invalido, execute desse jeito...
		}
		
		return false;
	}
	
	private SwKey createNewKey(String group, int days){
		String key = SwVIP.FormatKey();
		
		if(SwVIP.flatFile){
			while(SwVIP.instance.getConfig().contains("keys." + key)){
				key = SwVIP.FormatKey();
			}
			
			SwVIP.instance.getConfig().set("keys." + key, group + ", " + Integer.toString(days));
			SwVIP.instance.saveConfig();
			SwVIP.instance.reloadConfig();
			
			return new SwKey(key, group, days);
		} else {
			//ThreadVZ nk = new ThreadVZ(plugin,"newkey",sender,grupo,dias,key);
			//nk.start();
		}
		
		return null;
	}
}
