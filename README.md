[![Discord](https://img.shields.io/discord/899151012290498620.svg?label=discord&logo=discord)](https://discord.petrus.dev/)
[![Spigot](https://img.shields.io/spiget/downloads/105174?label=Spigot%20Downloads&logo=data%3Aimage%2Fpng%3Bbase64%2CiVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAABGdBTUEAALGPC%2FxhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAAElBMVEUAAAAAAAD%2F0ADi6D86Ruj%2F%2F%2F%2BilASZAAAAAXRSTlMAQObYZgAAAAFiS0dEBfhv6ccAAAAHdElNRQfmBRoTHQ%2BKPgYQAAAAAW9yTlQBz6J3mgAAAFBJREFUCNdljdERgDAIQ8OdAxicgHQCZQH3n0raWn98P7y7QADAYkfHiInFK4yZwLWkTaHy7IPeMupOUkvVFiu5XL3hyLAhRsK%2FzvVlu%2F%2FyAL5yBqQb5SdrAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDIyLTA1LTI2VDE5OjI5OjEzKzAwOjAw2Eq4fQAAACV0RVh0ZGF0ZTptb2RpZnkAMjAyMi0wNS0yNlQxOToyOToxMyswMDowMKkXAMEAAAAASUVORK5CYII%3D)](https://www.spigotmc.org/resources/105174/)

<p align="center">
  <img src="icon/icon.png" />
</p>

# BukkitPixelmonPlaceholders

Bridge Pixelmon Reforged data into the Bukkit/PlaceholderAPI ecosystem.

BukkitPixelmonPlaceholders is a Bukkit/Spigot plugin (v1.3.1) that exposes Pixelmon Reforged
data as PlaceholderAPI placeholders. It connects the Forge-based Pixelmon mod with Bukkit
plugins, giving server admins access to live party data, Pokedex statistics, battle state, and
detailed per-Pokemon attributes directly inside any PAPI-compatible plugin (scoreboards, chat
formatters, menus, etc.). It targets hybrid Forge/Bukkit servers (Arclight, Mohist, Crucible)
running Pixelmon Reforged.

---

## Features

- **Party size placeholders** - total, egg-only, able-only, and normal-only counts for a
  player's active party.
- **Pokedex statistics** - species caught, species seen, completion percentage, and total
  Pokedex size.
- **Battle state detection** - whether a player is currently in any battle, PvP or PvE.
- **Win/loss/KDR tracking** - battle stats surfaced as plain numbers.
- **Party-level aggregates** - average, lowest, and highest level across all party members.
- **Legendary spawn timer** - seconds, minutes, and a formatted countdown to the next legendary
  spawn event.
- **Catch-combo tracking** - current combo count, species identifier, and localized species name.
- **Per-slot Pokemon placeholders** via `%pixelmon_party_slot_N_<attr>%` (slots 1-6):
  - Identity: name, display name, species, Pokedex number, form, form (unlocalized).
  - Stats: level, exp, exp to level up, IV and EV totals and per-stat breakdowns, IV/EV
    percentages, Dynamax level, friendship value and percentage.
  - Types: first type, second type, and all types combined.
  - Nature: base nature with optional mint-nature annotation.
  - Ability, gender (color-coded), growth/size category.
  - Flags: shiny (boolean + formatted), legendary, ultra beast, egg/non-egg, breedability,
    Gigantamax factor.
  - Original trainer name and UUID.
  - Held item name, custom texture path, palette name (localized and unlocalized).
  - Moveset: up to four move names by slot index.
  - Hidden power type.
  - Egg steps progress (`walked/total`) and egg groups.
  - Mew clone count and Lake Trio (Azelf/Mesprit/Uxie) enchantment count.
  - Arbitrary spec-flag check: `specflag_contains_{flagName}`.
  - Spec filter check for a single slot: `spec_contains_{filters}`.
  - Sprite item NBT tag.
- **Party filter placeholder** - `party_contains_spec_{filters}` counts how many party members
  match a set of Boolean filters (egg, shiny, mega, ultrabeast, legendary).
- **`uncolorfy_` manipulator** - strips color codes from any inner placeholder result (e.g.
  `%pixelmon_uncolorfy_party_slot_1_gender%`).
- **Placeholder remapper** - `Remappings.yml` lets admins create aliases for any placeholder
  without restarting the server.
- **Configurable PAPI prefix** - defaults to `pixelmon`; can be changed to avoid conflicts with
  Neovitalism's PixelmonExtension.
- **Default value for empty party slots** - configurable fallback returned when a slot has no
  Pokemon (useful for DeluxeMenus and similar plugins that do not handle empty strings well).
- **Multi-version compat modules** - ships separate adapter jars for MC 1.12.2, 1.16.5, 1.20.2,
  and 1.21.1; the correct one is loaded automatically at runtime via reflection.
- **Hot reload** - `/bpp reload` reloads the config without a full server restart.
- **In-game placeholder browser** - `/bpp list` shows all available placeholders with live
  values and click-to-copy support.
- **bStats telemetry** (ID 16405) and SpigotMC listing (ID 105174) via EverNifeCore.

---

## Requirements

| Requirement | Details |
|---|---|
| Server platform | Arclight, Mohist, Crucible, or any other Forge/Bukkit hybrid |
| Minecraft version | 1.12.2, 1.16.5, 1.19.2, 1.20.2, or 1.21.1 (compat module must match the server version) |
| Pixelmon Reforged | A version compatible with the target MC version |
| Java runtime | Java 8 or later (plugin bytecode targets Java 8; JDK 21 required to build the v1_21_1 compat module) |
| EverNifeCore | Required - hard dependency declared in `plugin.yml` |
| PlaceholderAPI | Optional soft dependency - placeholders are only registered when PAPI is present |

---

## Installation

1. Install **EverNifeCore** into the `plugins/` folder - the plugin will not load without it.
2. Install **PlaceholderAPI** so that placeholders are available server-wide (strongly
   recommended).
3. Drop `BukkitPixelmonPlaceholders-<version>.jar` into the `plugins/` folder.
4. Start or restart the server. The plugin auto-detects the running MC version and loads the
   matching compat module from within the jar.
5. (Optional) Edit `plugins/BukkitPixelmonPlaceholders/config.yml` to change the PAPI prefix
   or the empty-slot fallback value, then restart the server (prefix change) or run
   `/bpp reload` (other settings).

> If Neovitalism's PixelmonExtension is also installed, you must change
> `Settings.PAPIPlaceholderPrefix` in `config.yml` to something other than `pixelmon` (for
> example `bpppixelmon`) and restart the server to avoid placeholder prefix collisions.

---

## Commands

| Command | Aliases | Description | Permission |
|---|---|---|---|
| `/bpp list [page]` | `/bpp test [page]` | Lists all available placeholders with live values for the executing player. Player only (not console). | `bukkitpixelmonplaceholders.command.test` |
| `/bpp reload` | - | Reloads the plugin configuration. | `bukkitpixelmonplaceholders.command.reload` |

The root command `/bpp` also accepts the full alias `/bukkitpixelmonplaceholders`.

---

## Permissions

| Permission | Description | Default |
|---|---|---|
| `bukkitpixelmonplaceholders.command.test` | Allows the player to browse all registered placeholders with live values. | op |
| `bukkitpixelmonplaceholders.command.reload` | Allows reloading the plugin configuration. | op |

---

## Configuration

The plugin creates two files under `plugins/BukkitPixelmonPlaceholders/`:

### `config.yml`

| Key | Default | Description |
|---|---|---|
| `Settings.PAPIPlaceholderPrefix` | `pixelmon` | The PAPI prefix for all placeholders (e.g. `%pixelmon_party_size_all%`). Change this if another plugin claims the `pixelmon` prefix. Requires a full server restart to take effect. |
| `Settings.DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON` | `""` (empty string) | Value returned when a party slot is empty. Set to a non-empty string (e.g. `None`) for compatibility with plugins such as DeluxeMenus that cannot handle empty placeholder results. |

### `Remappings.yml`

Allows remapping placeholder identifiers at runtime. Any entry here creates an alias that is
resolved before the default parser runs, enabling short or custom names for built-in
placeholders without a server restart.

---

## Placeholder Reference

All placeholders follow the pattern `%<prefix>_<id>%`. With the default prefix `pixelmon`:

**Player-level placeholders**

| Placeholder | Description |
|---|---|
| `%pixelmon_party_size_all%` | Total Pokemon (including eggs) in party |
| `%pixelmon_party_size_ableonly%` | Able (non-egg) Pokemon count |
| `%pixelmon_party_size_eggonly%` | Egg count |
| `%pixelmon_party_size_pokemononly%` | Non-egg Pokemon count |
| `%pixelmon_dex_count_caught%` | Species caught in the Pokedex |
| `%pixelmon_dex_count_seen%` | Species seen in the Pokedex |
| `%pixelmon_dex_percentage%` | Caught percentage (e.g. `47.3%`) |
| `%pixelmon_dex_total_size%` | Total number of species in the Pokedex |
| `%pixelmon_wins%` | Total battle wins |
| `%pixelmon_losses%` | Total battle losses |
| `%pixelmon_kdr%` | Win/loss ratio as percentage |
| `%pixelmon_average_level%` | Average level of party |
| `%pixelmon_lowest_level%` | Lowest level in party |
| `%pixelmon_highest_level%` | Highest level in party |
| `%pixelmon_next_legendary%` | Seconds until next legendary spawn |
| `%pixelmon_next_legendary_minutes%` | Minutes until next legendary spawn |
| `%pixelmon_next_legendary_formatted%` | Formatted countdown (e.g. `2m 30s`) |
| `%pixelmon_catchcombo_amount%` | Current catch combo count |
| `%pixelmon_catchcombo_species%` | Species internal name in catch combo |
| `%pixelmon_catchcombo_species_localized%` | Localized species name in catch combo |
| `%pixelmon_is_in_battle%` | `true`/`false` - player is in any battle |
| `%pixelmon_is_in_battle_pvp%` | `true`/`false` - player is in a PvP battle |
| `%pixelmon_is_in_battle_pve%` | `true`/`false` - player is in a PvE battle |
| `%pixelmon_party_contains_spec_[shiny=true\|legendary=false]%` | Count of party members matching the given filters |

**Per-slot placeholders** - replace `1` with the desired slot number (1-6):

| Placeholder | Description |
|---|---|
| `%pixelmon_party_slot_1_is_present%` | `true` if the slot contains a Pokemon |
| `%pixelmon_party_slot_1_name%` | Localized Pokemon name |
| `%pixelmon_party_slot_1_display_name%` | Display name |
| `%pixelmon_party_slot_1_species%` | Species localized name |
| `%pixelmon_party_slot_1_pokedex_number%` | National Pokedex index |
| `%pixelmon_party_slot_1_level%` | Level |
| `%pixelmon_party_slot_1_exp%` | Current experience points |
| `%pixelmon_party_slot_1_exp_to_level_up%` | Experience needed to reach the next level |
| `%pixelmon_party_slot_1_form%` | Form localized name |
| `%pixelmon_party_slot_1_form_unlocalized%` | Form translation key |
| `%pixelmon_party_slot_1_shiny%` | `true`/`false` |
| `%pixelmon_party_slot_1_shiny_special%` | Formatted shiny label, empty if not shiny |
| `%pixelmon_party_slot_1_is_egg%` | `true` if the slot holds an egg |
| `%pixelmon_party_slot_1_is_non_egg%` | `true` if the slot does not hold an egg |
| `%pixelmon_party_slot_1_egg_steps%` | Steps walked / steps total for an egg |
| `%pixelmon_party_slot_1_egg_groups%` | Egg group names |
| `%pixelmon_party_slot_1_is_legendary%` | `true`/`false` |
| `%pixelmon_party_slot_1_is_legendary_special%` | Formatted legendary label |
| `%pixelmon_party_slot_1_is_ultrabeast%` | `true`/`false` |
| `%pixelmon_party_slot_1_is_ultrabeast_special%` | Formatted ultra beast label |
| `%pixelmon_party_slot_1_ability%` | Ability localized name |
| `%pixelmon_party_slot_1_gender%` | Gender with color coding |
| `%pixelmon_party_slot_1_nature%` | Base nature, with mint nature annotation if applicable |
| `%pixelmon_party_slot_1_type_all%` | All types comma-separated |
| `%pixelmon_party_slot_1_type_1%` | First type |
| `%pixelmon_party_slot_1_type_2%` | Second type (empty if mono-type) |
| `%pixelmon_party_slot_1_growth%` | Growth/size category |
| `%pixelmon_party_slot_1_size%` | Alias for `growth` |
| `%pixelmon_party_slot_1_unbreedable%` | Colored breedable/unbreedable label |
| `%pixelmon_party_slot_1_dynamax_level%` | Dynamax level |
| `%pixelmon_party_slot_1_held_item%` | Held item name, empty if none |
| `%pixelmon_party_slot_1_texture%` | Custom texture path |
| `%pixelmon_party_slot_1_palette%` | Palette localized name |
| `%pixelmon_party_slot_1_palette_unlocalized%` | Palette internal name |
| `%pixelmon_party_slot_1_clones%` | Number of Mew clones (Mew only) |
| `%pixelmon_party_slot_1_enchantments%` | Lake Trio enchantment count |
| `%pixelmon_party_slot_1_hidden_power%` | Hidden power type |
| `%pixelmon_party_slot_1_can_gmax%` | `true` if has Gigantamax factor |
| `%pixelmon_party_slot_1_sprite%` | Sprite item NBT tag |
| `%pixelmon_party_slot_1_friendship%` | Friendship value (0-255) |
| `%pixelmon_party_slot_1_friendship_percentage%` | Friendship as percentage |
| `%pixelmon_party_slot_1_move1%` - `%pixelmon_party_slot_1_move4%` | Move names by index |
| `%pixelmon_party_slot_1_iv_total%` | Sum of all IVs |
| `%pixelmon_party_slot_1_iv_total_max%` | Maximum possible IV total |
| `%pixelmon_party_slot_1_iv_percentage%` | IV completeness percentage |
| `%pixelmon_party_slot_1_iv_hp%` / `iv_attack` / `iv_defense` / `iv_special_attack` / `iv_special_defense` / `iv_speed` | Per-stat IV values |
| `%pixelmon_party_slot_1_ev_total%` | Sum of all EVs |
| `%pixelmon_party_slot_1_ev_total_max%` | Maximum possible EV total |
| `%pixelmon_party_slot_1_ev_percentage%` | EV completeness percentage |
| `%pixelmon_party_slot_1_ev_hp%` / `ev_attack` / `ev_defense` / `ev_special_attack` / `ev_special_defense` / `ev_speed` | Per-stat EV values |
| `%pixelmon_party_slot_1_originaltrainer_name%` | Original trainer name |
| `%pixelmon_party_slot_1_originaltrainer_uuid%` | Original trainer UUID |
| `%pixelmon_party_slot_1_specflag_contains_{flagName}%` | `true`/`false` - checks a named spec flag |
| `%pixelmon_party_slot_1_spec_contains_{filters}%` | `true`/`false` - checks spec filters against this slot |

Use `/bpp list` in-game to browse all placeholders with live values for your current party.

---

## Building from Source

The project uses the Gradle wrapper. The recommended JDK is **JDK 25**
(`C:\Users\Petrus\.jdks\temurin-25.0.3`). Source and target compatibility are set to Java 8.

> The `modules/v1_21_1` compat subproject requires JDK 21 and currently has a hardcoded path
> (`C:\Program Files\Eclipse Adoptium\jdk-21.0.6.7-hotspot\`) in its `build.gradle`. Adjust
> this path to match your local JDK 21 installation before building.

**Step 1 - build compat modules and stage jars into `libs/`:**

```powershell
$env:JAVA_HOME = "C:\Users\Petrus\.jdks\temurin-25.0.3"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
.\gradlew buildAndMoveJars
```

**Step 2 - build the final shadow jar:**

```powershell
.\gradlew shadowJar
```

The deployable artifact is a **shadow jar** (the default `jar` task is disabled). Output:
`build/libs/BukkitPixelmonPlaceholders-<version>.jar`. The `buildAndMoveJars` task builds each
compat subproject with its own Gradle wrapper and copies the resulting `*-COMPAT.jar` into the
root `libs/` directory, which is then shaded into the final jar.

The `clean` task cascades into each compat subproject and removes all `*-COMPAT.jar` files
from `libs/`.

---

## Architecture

**Entry point:** `br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders`
extends `JavaPlugin` and is annotated with `@ECPlugin` (registers bStats ID 16405, SpigotMC
ID 105174). On enable it initializes config, registers commands via EverNifeCore's
`FinalCMDManager`, hooks into PAPI when available, and runs `ExternalCompatibilityChecker` to
warn about known prefix conflicts.

**Main systems:**

| System | Description |
|---|---|
| `config/ConfigManager` | Loads `config.yml` and `Remappings.yml` via EverNifeCore's `Config` |
| `placeholder/PixelmonPlaceholders` | Holds the two `RegexReplacer` instances (player-level and Pokemon-level) and registers them with PAPI |
| `commands/CMDCoreCommand` | Main admin command wired through EverNifeCore's `FinalCMDManager` |
| `integration/ExternalCompatibilityChecker` | Detects Neovitalism's PixelmonExtension and logs a severe warning about prefix conflicts |
| `modules/Commons` | Shared library: `PixelmonPlaceholderFactory` (abstract, version-dispatched), `BPPSettings`, `PlaceholderRemapper`, `PokemonWrapper`, `RemappableRegexReplacer` |
| `modules/v1_12_2` | Compat shim for MC 1.12.2 / Pixelmon Reforged on Forge 1.12.2 |
| `modules/v1_16_5` | Compat shim for MC 1.16.5 - contains the most complete placeholder set |
| `modules/v1_20_2` | Compat shim for MC 1.20.2 |
| `modules/v1_21_1` | Compat shim for MC 1.21.1 (requires JDK 21 to compile) |

`PixelmonPlaceholderFactory.create()` resolves the current MC version at runtime and loads
the matching `PixelmonPlaceholderFactoryImpl` via reflection. Each compat jar must be present
inside the shadow jar or the plugin will fail at runtime when the first placeholder is evaluated.

---

## Notes / Known Limitations

- **Requires a Forge/Bukkit hybrid server.** Pixelmon Reforged is a Forge mod; this plugin only
  works on hybrid platforms such as Arclight, Mohist, or Crucible.
- **Prefix change requires a server restart.** `Settings.PAPIPlaceholderPrefix` is read once at
  startup; `/bpp reload` does not apply a prefix change.
- **`party_contains_spec_` and `spec_contains_` filter syntax is brittle.** The filter string
  (e.g. `[shiny=true|legendary=false]`) must be formatted exactly or the parser throws a
  `RuntimeException`.
- **Neovitalism's PixelmonExtension conflict.** Both plugins default to the `pixelmon` PAPI
  prefix. If both are installed without changing the prefix, placeholder lookups will be
  ambiguous. The plugin logs a warning at startup but cannot resolve the conflict automatically.