package com.discordessentials.bot.database.prefix;

import com.discordessentials.bot.database.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PrefixRegistry {

    private static final PrefixRegistry INSTANCE = new PrefixRegistry();

    private final String DEFAULT_PREFIX = (".de ");

    private HashMap<String, String> registeredPrefixes = new HashMap<String, String>();

    public void reloadRegistry(MySQL database) throws SQLException {
        this.registeredPrefixes.clear();

        PreparedStatement readCustomPrefixes = database.getStatement("SELECT * FROM Guilds");

        ResultSet customPrefixesQuery = readCustomPrefixes.executeQuery();

        while(customPrefixesQuery.next()) {
            String customPrefixGuildId = customPrefixesQuery.getString("guildId");
            String customPrefix = customPrefixesQuery.getString("prefix");

            this.registeredPrefixes.put(customPrefixGuildId, customPrefix);
        }
    }

    public void registerCustomPrefix(MySQL database, String guildId, String prefix) throws SQLException {
        PreparedStatement deletePreviousPrefixForGuild = database.getStatement("DELETE FROM Guilds WHERE guildId = ?");
        deletePreviousPrefixForGuild.setString(1, guildId);

        deletePreviousPrefixForGuild.execute();

        PreparedStatement insertPrefixForGuild = database.getStatement("INSERT INTO Guilds (guildId, prefix) VALUES (?, ?)");
        insertPrefixForGuild.setString(1, guildId);
        insertPrefixForGuild.setString(2, prefix);

        insertPrefixForGuild.execute();

        this.registeredPrefixes.put(guildId, prefix);
    }

    public boolean customPrefixExistsForGuild(String guildId) {
        return this.registeredPrefixes.containsKey(guildId);
    }

    public String getPrefixForGuild(String guildId) {
        return this.customPrefixExistsForGuild(guildId) ? this.registeredPrefixes.get(guildId) : this.DEFAULT_PREFIX;
    }

    public String getPrefixedCommand(String guildId, String command) {
        return this.getPrefixForGuild(guildId) + command;
    }

    public static PrefixRegistry getInstance() {
        return INSTANCE;
    }

}
