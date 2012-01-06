package me.darazo.ancasino;

import java.util.logging.Logger;

import me.darazo.ancasino.command.AnCommandExecutor;
import me.darazo.ancasino.listeners.AnBlockListener;
import me.darazo.ancasino.listeners.AnPlayerListener;
import me.darazo.ancasino.slot.RewardData;
import me.darazo.ancasino.slot.SlotData;
import me.darazo.ancasino.slot.TypeData;
import me.darazo.ancasino.util.ConfigData;
import me.darazo.ancasino.util.Permissions;
import me.darazo.ancasino.util.StatData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AnCasino extends JavaPlugin{
	
	protected AnCasino plugin;
	
	public String prefix = "[AnCasino]";
	private PluginDescriptionFile pdfFile;
	
	private AnPlayerListener playerListener = new AnPlayerListener(this);
	private AnBlockListener blockListener = new AnBlockListener(this);
	private AnCommandExecutor commandExecutor;
	
	public ConfigData configData = new ConfigData(this);
	public SlotData slotData = new SlotData(this);
	public TypeData typeData = new TypeData(this);
	public StatData statsData = new StatData(this);
	public RewardData rewardData = new RewardData(this);
	public Permissions permission = new Permissions(this);
	
	public Economy economy = null;
	public final Logger logger = Logger.getLogger("Minecraft");

	@Override
	public void onDisable() {
		
		this.logger.info(prefix + " Saving and unloading data..");
		
		configData.save();
		
		this.configData = null;
		this.slotData = null;
		this.typeData = null;
		this.statsData = null;
		this.rewardData = null;
		this.permission = null;
		
		this.logger.info(prefix + " Disabled.");
		
	}

	@Override
	public void onEnable() {
		
		configData.load();
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Priority.Highest, this);
		
		pdfFile = this.getDescription();
		
		commandExecutor = new AnCommandExecutor(this);
		getCommand("casino").setExecutor(commandExecutor);
		
		this.logger.info(prefix +" v" + pdfFile.getVersion() + " enabled.");
		
		if(!pm.isPluginEnabled("Vault")) {
			
			this.logger.warning(prefix +" Vault is required in order to use this plugin.");
			this.logger.warning(prefix +" dev.bukkit.org/server-mods/vault/");
			pm.disablePlugin(this);
		}
		else {
			
			if(!setupEconomy()) {
				this.logger.warning(prefix + " An economy plugin is required in order to use this plugin.");
				pm.disablePlugin(this);
			}
		}
		
	}
	
	// Sends a properly formatted message
	public void sendMessage(Player player, String message) {
		
		message = configData.prefixColor + prefix + configData.chatColor + " " + message;
		message = message.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
		player.sendMessage(message);	
	}
	
	// Registers economy with Vault
	private Boolean setupEconomy() {
		
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        
        return (economy != null); 
    }

}
