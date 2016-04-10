package com.github.gotochan.command;

import org.bukkit.command.CommandSender;

public abstract class SubKitAbst {
	
	
	/**
	 * Abilityの名前を取得します。
	 * @return Abilityname
	 */
	public abstract String getKitName();
	
	/**
	 * kitに応じたコマンドを返します。
	 * args.length(2) args[1]
	 * @param sender
	 * @param args
	 * @return 各キットのクラス
	 */
	public abstract boolean runKitCommand(
			CommandSender sender, String[] args);
	
}
