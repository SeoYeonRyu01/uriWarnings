uriWarnings - Minecraft Bukkit/Spigot 1.8-1.16.

uriWarnings is a simple Minecraft plugin that allows server administrators to warn players and enables players to view their warnings at any time. Warnings can be saved locally or using a MySQL database.

Features:
  Warn players with customizable warning messages.
  Players can view their warnings at any time.
  Supports local file storage and MySQL for warning data.

Commands:
  /Ostrzezenia - Allows players to view their own warnings.
  /Ostrzez add <player_name> <warning_message> - Adds a warning to the specified player.
  /Ostrzez remove <player_name> <warning_id> - Removes a warning from the specified player by ID.
  /Ostrzez info <player_name> - Displays all warnings for the specified player.

In the plugin configuration file, you can specify whether to store warnings locally or in a MySQL database.
