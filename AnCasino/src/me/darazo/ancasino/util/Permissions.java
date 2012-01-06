package me.darazo.ancasino.util;

import org.bukkit.entity.Player;

import me.darazo.ancasino.AnCasino;
import me.darazo.ancasino.slot.SlotMachine;
import me.darazo.ancasino.slot.Type;

public class Permissions {
	
	protected AnCasino plugin;
	
	private String admin = "ancasino.admin";
	private String create = "ancasino.create";
	private String manage = "ancasino.manage";
	private String use = "ancasino.use";
	
	// Initialize permissions
	public Permissions(AnCasino plugin) {
		this.plugin = plugin;
	}
	
	// Returns true if player has admin permissions
	public Boolean isAdmin(Player player) {
		
		if(player.isOp() || player.hasPermission(admin) ) {
			return true;
		}
		return false;
	}
	
	// Returns true if player can create slots
	public Boolean canCreate(Player player) {
			
		if(isAdmin(player) || player.hasPermission(create)) {
			return true;
		}
		return false;
	}
	
	// Returns true if player can create slots with the type
	public Boolean canCreate(Player player, Type type) {
		
		String name = type.getName();
		if(isAdmin(player) || player.hasPermission(create +"."+ name)) {
			return true;
		}
		return false;
	}
	
	// Returns true if player can create slots with the type
	public Boolean canCreate(Player player, String type) {
		
		if(isAdmin(player) || player.hasPermission(create +"."+ type)) {
			return true;
		}
		return false;
	}
	
	// Returns true if the player can manage slot machines
	public Boolean canManage(Player player) {
		
		if(isAdmin(player) || player.hasPermission(manage)) {
			return true;
		}
		return false;
	}
	
	// Returns true if the player can use the type
	public Boolean canUse(Player player, Type type) {
		
		String name = type.getName();
		if(isAdmin(player) || player.hasPermission(use +"."+ name)) {
			return true;
		}
		return false;
	}
	
	public Boolean isOwner(Player player, SlotMachine slot) {
		
		if(isAdmin(player) || slot.getOwner().equalsIgnoreCase(player.getName())) {
			return true;
		}
		return false;
	}

}
