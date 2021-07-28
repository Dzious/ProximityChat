# Proximity Chat - by [Dzious]
___

### Current Recommended Versions
* [Latest Release] supports Minecraft 1.16.*.
* [Latest Pre-Release] supports Minecraft 1.16.*.

| Minecraft Version | EnchantControl Version |
|:----:|:----:|
| MC 1.16.* | Use the [Latest Release]. |

___

### Installation/Configuration

#### **Installation :**
- Download and install [PlaceholderAPI]
- Download the jar file form our [Latest Release] or the version corresponding to your **Minecraft Version**
- Safely shutdown your server
- Put the jar file in your server `plugins` folder
- Start the server
- The plugin is now install on your server

#### **Configuration :**
 - Simply edit the `config.yml` found in `plugins/ProximityChat` in your server files

___

### Commands/Permissions

#### **Commands :**
- /proximitychat \<help\|reload\>
    - **help** : Prints the help message
    - **reload** : Reload the plugin
- /chat \<join\> \<Channel\>
    - **join** : Join the channel given as argument
- /\<channel\> [message]
    - **empty** : switch to the channel
    - **message** : sends a message in the channel

#### **Permissions :**
- proximitychat.admin
    - allow use of /proximitychat commands
- proximitychat.chat.channel.*
    - allow player to talk in all channels
- proximitychat.chat.channel.\<channel\>
    - allow player to talk in selected \<channel\>

___

### Support

Here on github's [Issue Tracker] you can file [bug reports] or [feature requests].

If you still need help or just have a question, join us on the [Discord server], where you can have support from both developer and community and also discuss about the  development of the plugin.

___

### Placeholders API

Proximity Chat support placeholders in the prefix configuration.


#### **Proximity Chat Placeholders :**

| Placehoder | Description |
|:----:|:----:|
| `%proximitychat_channel_name%` | Channel the player talks in. |
| `%proximitychat_channel_range%` | Range of the channel talks in. |
| `%proximitychat_channel_worlds%` | Worlds in which the channel is active separated by commas. |

___

[Dzious]: https://github.com/Dzious

[Latest Release]: https://github.com/Dzious/ProximityChat/releases/latest
<!-- [Latest Pre-Release] -->

[Issue Tracker]: https://github.com/Dzious/ProximityChat/issues
[bug reports]: https://github.com/Dzious/ProximityChat/issues/new?assignees=&labels=bug&proximitychat=bug_report.md&title=
[feature requests]: https://github.com/Dzious/ProximityChat/issues/new?assignees=&labels=enhancement&proximitychat=feature_request.md&title=
<!-- [general questions] -->
[Discord server]: https://discord.gg/MNAeetQV4C

[PlaceholderAPI]: https://www.spigotmc.org/resources/placeholderapi.6245/
