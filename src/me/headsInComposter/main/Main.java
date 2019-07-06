package me.headsInComposter.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	double chanceOfSuccessfulCompost = 0.65;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);
		super.onEnable();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}

	public void onDisable() {

	}
}
