package com.discordessentials.bot.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

	private MySQL mysql;

	public AccountManager(MySQL mysql) {
		this.setMysql(mysql);
		
	}
	
	public String getPrefix(String guildid) throws SQLException {
		PreparedStatement getPrefixStatement = this.mysql
				.getStatement("SELECT prefix from Guilds where guildid = ?");
		getPrefixStatement.setString(1, guildid);
		
		ResultSet prefix = getPrefixStatement.executeQuery();
		
		while (prefix.next()) {
			return prefix.getString("prefix");
		}
		
		return guildid;
	}


	public MySQL getMysql() {
		return mysql;
	}

	public void setMysql(MySQL mysql) {
		this.mysql = mysql;
	}

}