package com.github.gotochan.command;

import org.bukkit.command.CommandSender;

public abstract class SubCommandAbst {

	/**
	 * コマンドを取得します。
	 * @return コマンド
	 */
	public abstract String getCommandName();

	/**
	 * コマンドを実行します。
	 * @param sender コマンド実行者
	 * @param label ラベル
	 * @param args 引数
	 * @return コマンドが実行されたか(boolean)
	 */
	public abstract boolean runCommand(
			CommandSender sender, String label, String[] args);


}
