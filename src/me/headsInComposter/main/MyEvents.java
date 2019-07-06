package me.headsInComposter.main;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MyEvents implements Listener {
	Main main;

	public MyEvents(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		// Checks if player right clicked the block
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = e.getPlayer();
			Block blockLookingAt = player.getTargetBlockExact(5);

			// Checks that the block right clicked is a composter
			if (blockLookingAt == null || blockLookingAt.getType() != Material.COMPOSTER) {
				return;
			} else if (player.isSneaking()) { // Checks if player is sneaking
				return;
			}

			int slot = player.getInventory().getHeldItemSlot();
			ItemStack[] contents = player.getInventory().getContents();

			if (player.getInventory().getContents()[slot] == null) {
				return;
			}

			if (isPlayerHoldingAHead(contents[slot].getType())) {
				Levelled compotserLevel = (Levelled) blockLookingAt.getBlockData();

				// Returns if composter is full
				if (compotserLevel.getLevel() == 8) {
					return;
				} else {
					e.setCancelled(true);
				}

				if (MyFuncs.Random.randomDoubleBetween(0, 100) <= 65) {
					compotserLevel.setLevel(compotserLevel.getLevel() + 1);
					player.playSound(player.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1f, 1f);
				} else {
					player.playSound(player.getLocation(), Sound.BLOCK_COMPOSTER_FILL, 1f, 1f);
				}

				contents[slot].setAmount(contents[slot].getAmount() - 1);

				blockLookingAt.setBlockData(compotserLevel);
			}
		}
	}

	/**
	 * Checks if player is holding a head
	 * 
	 * @param Type of item player is holding
	 * @return boolean
	 */
	private boolean isPlayerHoldingAHead(Material type) {
		switch (type) {
		case PLAYER_HEAD:
			return true;
		case ZOMBIE_HEAD:
			return true;
		case CREEPER_HEAD:
			return true;
		case SKELETON_SKULL:
			return true;
		case WITHER_SKELETON_SKULL:
			return true;
		default:
			return false;
		}
	}
}
