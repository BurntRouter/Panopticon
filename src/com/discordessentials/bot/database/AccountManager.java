package com.discordessentials.bot.database;

public class AccountManager {

	private MySQL mysql;

	public AccountManager(MySQL mysql) {
		this.setMysql(mysql);
		/**
		 * try { if (!this.todayRegisteredInRecord()) { DateFormat dateFormat = new
		 * SimpleDateFormat("yy/MM/dd"); Date date = new Date();
		 *
		 * this.mysql.getStatement("INSERT INTO DailyReports (commands, date) VALUES (0,
		 * '" + dateFormat.format(date) + "')").execute(); } }catch(SQLException e){
		 * e.printStackTrace(); }
		 **/
	}


	public MySQL getMysql() {
		return mysql;
	}

	public void setMysql(MySQL mysql) {
		this.mysql = mysql;
	}

}