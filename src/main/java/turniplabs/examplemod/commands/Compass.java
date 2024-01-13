package turniplabs.examplemod.commands;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

/**
 * This command gives the player a compass.
 ***********************************************************************************/

public class Compass extends Command {
	public Compass() {
        super("compass");
    }

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
		// Give the player a compass
		commandSender.getPlayer().inventory.insertItem(new ItemStack(Item.toolCompass), false);
		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		//commandSender.sendMessage("compass");
	}
}
