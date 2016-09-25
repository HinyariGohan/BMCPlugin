package com.github.gotochan.command;

import com.github.gotochan.BMCPlayer;

public abstract class SubCommandAbst {

	/**
	 * コマンドを取得します。
	 * @return コマンド
	 */
	public abstract String getCommandName();

	/**
	 * コマンドを実行します。
	 * @param sender コマンド実行者(BMCPlayer)
	 * @param label ラベル
	 * @param args 引数
	 * @return コマンドが実行されたか(boolean)
	 */
	public abstract boolean runCommand(
			BMCPlayer player, String label, String[] args);


}
