package meloncommands;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import meloncommands.commands.*;
import meloncommands.commands.Kit;
import meloncommands.commands.Role;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.gamerule.GameRuleBoolean;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.commands.SpawnCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.CommandHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;



public class MelonCommands implements ModInitializer, GameStartEntrypoint{
    public static final String MOD_ID = "meloncommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Gson GSON = (new GsonBuilder()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(ItemStack.class, new ItemStackJsonAdapter()).create();

	//

	public static GameRuleBoolean FIRE_TICKS = GameRules.register(new GameRuleBoolean("doFireTick", true));

	//
	public static TomlConfigHandler config;

	public void updateConfig() {

	}

	@Override
	public void onInitialize() {
		updateConfig();
		LOGGER.info("MelonCommands initialized");
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		for(Command command : Commands.commands){
			if(command instanceof SpawnCommand){
				Commands.commands.remove(command);
				break;
			}
		}
		CommandHelper.createCommand(new Kit());
		CommandHelper.createCommand(new WhereAmI());
		CommandHelper.createCommand(new Kitten());
		// For summoning dummy lightningBolt
		// CommandHelper.createCommand(new Smite());
		CommandHelper.createCommand(new TPA());
		CommandHelper.createCommand(new TPAccept());
		CommandHelper.createCommand(new TPADeny());
		CommandHelper.createCommand(new Role());
		CommandHelper.createCommand(new meloncommands.commands.MelonCommands());
	}
}
