package meloncommands;

import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.TextFormatting;

import java.util.ArrayList;

public class RoleBuilder {

	public static String getCodeOrHex(String string){
		string = string.toLowerCase();
		if (MelonCommands.colorMap.containsKey(string)){
			string = MelonCommands.colorMap.get(string);
		} else{
			string = "§<" + string + ">";
		}
		return string;
	}

	public static String buildRoleDisplay(RoleData role){
		String roleDisplay = "";

		String borderColor = getCodeOrHex(role.displayBorderColor);
		String roleColor = getCodeOrHex(role.displayColor);

		if(role.isDisplayUnderlined){
			roleDisplay = roleDisplay + TextFormatting.UNDERLINE.toString();
		}
		if(role.isDisplayBold){
			roleDisplay = roleDisplay + TextFormatting.BOLD.toString();
		}
		if(role.isDisplayItalics){
			roleDisplay = roleDisplay + TextFormatting.ITALIC.toString();
		}

		roleDisplay = roleColor + roleDisplay + role.displayName;

		if(role.isDisplayBorderCustom){
			roleDisplay = borderColor + role.customDisplayBorderPrefix + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + role.customDisplayBorderSuffix + " ";
		} else if (role.isDisplayBorderBracket) {
			roleDisplay = borderColor + "[" + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + "] ";
		} else if (role.isDisplayBorderNone) {
			roleDisplay = roleDisplay + TextFormatting.RESET + " ";
		} else if (role.isDisplayBorderCaret) {
			roleDisplay = borderColor + "<" + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + "> ";
		} else if (role.isDisplayBorderCurly) {
			roleDisplay = borderColor + "{" + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + "} ";
		}

		roleDisplay = TextFormatting.RESET + roleDisplay + TextFormatting.RESET;

		return roleDisplay;
	}

	public static String buildRoleUsername(RoleData role, String username){
		String roleUsername = "";

		String borderColor = getCodeOrHex(role.usernameBorderColor);
		String roleColor = getCodeOrHex(role.usernameColor);

		if(role.isUsernameUnderlined){
			roleUsername = roleUsername + TextFormatting.UNDERLINE.toString();
		}
		if(role.isUsernameBold){
			roleUsername = roleUsername + TextFormatting.BOLD.toString();
		}
		if(role.isUsernameItalics){
			roleUsername = roleUsername + TextFormatting.ITALIC.toString();
		}

		roleUsername = roleColor + roleUsername + username;

		if(role.isUsernameBorderCustom){
			roleUsername = borderColor + role.customUsernameBorderPrefix + TextFormatting.RESET + roleUsername + TextFormatting.RESET + borderColor + role.customUsernameBorderSuffix + " ";
		} else if (role.isUsernameBorderBracket) {
			roleUsername = borderColor + "[" + TextFormatting.RESET + roleUsername + TextFormatting.RESET + borderColor + "] ";
		} else if (role.isUsernameBorderNone) {
			roleUsername = roleUsername + TextFormatting.RESET + " ";
		} else if (role.isUsernameBorderCaret) {
			roleUsername = borderColor + "<" + TextFormatting.RESET + roleUsername + TextFormatting.RESET + borderColor + "> ";
		} else if (role.isUsernameBorderCurly) {
			roleUsername = borderColor + "{" + TextFormatting.RESET + roleUsername + TextFormatting.RESET + borderColor + "} ";
		}

		roleUsername = TextFormatting.RESET + roleUsername + TextFormatting.RESET;

		return roleUsername;
	}

	public static String buildRoleTextFormat(RoleData role){
		String roleTextFormat = "";

		String color = getCodeOrHex(role.textColor);

		if(role.isTextUnderlined){
			roleTextFormat = roleTextFormat + TextFormatting.UNDERLINE.toString();
		}
		if(role.isTextBold){
			roleTextFormat = roleTextFormat + TextFormatting.BOLD.toString();
		}
		if(role.isTextItalics){
			roleTextFormat = roleTextFormat + TextFormatting.ITALIC.toString();
		}

		roleTextFormat = color + roleTextFormat;

		roleTextFormat = TextFormatting.RESET + roleTextFormat;

		return roleTextFormat;
	}

	public static String buildPlayerRoleDisplay(EntityPlayer player) {

		String defaultRoleDisplay;
		if(ConfigManager.roleHashMap.get(ConfigManager.getConfig("config").defaultRole) == null){
			defaultRoleDisplay = null;
		} else {
			defaultRoleDisplay = RoleBuilder.buildRoleDisplay(ConfigManager.roleHashMap.get(ConfigManager.getConfig("config").defaultRole));
		}

		StringBuilder roleDisplays = new StringBuilder();
		ArrayList<RoleData> rolesGranted = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			rolesGranted.add(null);
		}

		boolean hasBeenGrantedRole = false;
		for (RoleData role : ConfigManager.roleHashMap.values()) {
			if (role.playersGrantedRole.contains(player.username)) {
				rolesGranted.add(role.priority, role);
				hasBeenGrantedRole = true;
			}
		}

		String highestPriorityRoleDisplay = "";
		int tempPriority = Integer.MAX_VALUE;
		for (RoleData role : rolesGranted) {
			if (role != null && role.priority < tempPriority) {
				tempPriority = role.priority;
				highestPriorityRoleDisplay = buildRoleDisplay(role);
			}
		}

		for (int i = rolesGranted.size() - 1; i >= 0; i--) {
			if (rolesGranted.get(i) != null) {
				roleDisplays.append(buildRoleDisplay(rolesGranted.get(i)));
			}
		}

		if(hasBeenGrantedRole){
			if (ConfigManager.getConfig("config").displayMode.equals("multi")) {
				if(defaultRoleDisplay != null) {
					return defaultRoleDisplay + roleDisplays;
				} else {
					return "" + roleDisplays;
				}
			} else if (ConfigManager.getConfig("config").displayMode.equals("single")) {
				return highestPriorityRoleDisplay;
			}
		} else if(defaultRoleDisplay != null){
			return defaultRoleDisplay;
		}
		return "";
    }
}
