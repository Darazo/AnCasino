package me.darazo.ancasino.command;

import me.darazo.ancasino.AnCasino;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnCommandExecutor implements CommandExecutor{
	
	protected AnCasino plugin;
	private AnCommand cmd;
	
	public AnCommandExecutor(AnCasino plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandlabel, final String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			// Valid command format
			if(args.length >= 1) {
				
				// casino add
				if(args[0].equalsIgnoreCase("add")) {
					cmd = new CasinoAdd(plugin, args, player);
				}
				
				// casino addmanaged
				else if(args[0].equalsIgnoreCase("addmanaged")) {
					cmd = new CasinoAddManaged(plugin, args, player);
				}
				
				// casino remove
				else if(args[0].equalsIgnoreCase("remove")) {
					cmd = new CasinoRemove(plugin, args, player);
				}
				
				// casino list
				else if(args[0].equalsIgnoreCase("list")) {
					cmd = new CasinoList(plugin, args, player);
				}
				
				// casino reload
				else if(args[0].equalsIgnoreCase("reload")) {
					cmd = new CasinoReload(plugin, args, player);
				}
				
				// casino stats
				else if(args[0].equalsIgnoreCase("stats")) {
					cmd = new CasinoStats(plugin, args, player);
				}
				
				// casino edit
				else if(args[0].equalsIgnoreCase("type")) {
					cmd = new CasinoType(plugin, args, player);
				}
				
				// invalid command
				else {
					return false;
				}
				
			}
			
			// no arguments
			else {
				cmd = new Casino(plugin, args, player);
			}
			
			return cmd.process();
		}
		
		// No commands by console
		else {
			plugin.logger.info("This command cannot be executed as console.");
		}
		return true;
	}

}
