SessionSkip
-----------
### Warning
SessionSkip can allow players to log in as your staff if you do not configure it properly.  Do not use this plugin if you aren't 100% certain of what you're doing.

### Description
SessionSkip is a simple BungeeCord plugin to skip authentication with the Minecraft session servers under certain conditions.

Please note: The plugin will not auto-generate the config file.  Create a plugins/SessionSkip folder and manually copy it before use.

### Configuration
SessionSkip allows players to skip authentication with the Mojang session servers when they meet one of the following configurable criterion:
* They connect to a specific listener/hostname combination.
* They connect using a specific hostname.
* They connect from a specific IP address.

While hostnames and IP addresses are fairly self explanatory, a listener takes two forms: A connection made directly to an IP address and port, or a connection made to an IP address and port with a specified hostname.  With that in mind, a listener could look like either of the following, depending on what the player types in when connecting:
* If the player types "172.16.0.1"
  * /172.16.0.1:25565
* If the player types "play.example.com"
  * play.example.com/172.16.0.1:25565

### Commands
* /sessionskip reload - Reloads the plugin and config file.
* /sessionskip enable - Enables SessionSkip until BungeeCord is restarted.
* /sessionskip disable - Disables SessionSkip until BungeeCord is restarted.

SessionSkip has a "enabled" setting in the config file, which will determine whether it is enabled (true) or disabled (false) by default when BungeeCord starts up.

### Requirements
* BungeeCord build 691+
* BungeeYAML