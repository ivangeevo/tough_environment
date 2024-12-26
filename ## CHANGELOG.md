# 0.6 Alpha
+ Added Nether Sludge item. It's sludge time!
+ Added Loose Stone Bricks and Loose Cobbled Deepslate Bricks blocks & all of their variants.
+ Added some missing stairs and loose slab blocks variants for some blocks.
+ Added some extra block tags for loose blocks
+ Made Fire block inextinguishable by hand
+ Made hoe items have different breaking speeds when HC breaking speed is enabled
+ Changed (rebalanced) the strength and blast resistance values for some loose stone blocks slightly
+ Fixed Loose Bricks block & slab texture to be more "loosy". lol

# 0.5.1 Alpha
+ Added a missing recipe for converting loose bricks block to 8 bricks
+ Fixed deepslate ores loot tables to drop ore with any pickaxe
+ Fixed deepslate ores turning to regular converting stone on partial break
+ Fixed the name of "Loose Cobblestone" block to display properly
+ Fixed wrong "Loose Dirt Slab" & "Loose Calcite" names 
+ Fixed loose aggregate slabs to turn to their full counterparts instead of becoming "double slab"
+ Fixed a bug that caused andesite/granite/diorite blocks to drop stone brick on a pickaxe break (should drop only with chisel)
+ Fixed a bug where diamond chisel broke stone type blocks one by one stage instead of incrementing by 2 break levels.

# 0.5 Alpha
+ Added BTWR Shared Library as dependency + moved the item to block placement logic there
+ Added missing blocks to their appropriate tags
+ Added Clay Block (made out of 8 clay balls)
+ Added a block tag TURNED_FALLING_BLOCKS that makes blocks in it falling ones
+ Added Copper Nugget & Netherite Nugget items
+ Added Stone Brick item -> (moved from BTWR: Core)
+ Changed Primitive tools like stone axe, chisel, shovel, etc. to be slightly faster when mining blocks
+ Changed the exhaustion caused by breaking blocks to half of what it was
+ Changed the mod logo with a more high quality one
+ Improved the logic for hoe tools breaking blocks
+ Fixed loose slab blocks from being non-opaque (letting light go through them);
+ Fixed multiple block loot tables to drop properly
+ Fixed blocks to instantly change state without redrawing of the block to prevent phasing through converting blocks
+ Fixed cobblestone and cobbled deepslate slabs from being unable to be mortared
+ Fixed a bug that turned grass instantly to farmland by hoe breaking (it should go to dirt/loose dirt first)
+ Fixed a bug in breaking clay ore blocks dropped the wrong item
+ Fixed bug where breaking grass and dirt with hoe dropped dirt piles
+ Temporary fix for a bug that caused a slab placed above another one to turn the bottom block to double slab (a duplication bug)
+ Rounded the damage of all chisel items to be 1
+ Block Tag changes; added new mod tag "SIMPLE_DIRT_BLOCKS"
+ Removed the Andesite, Granite & Diorite stairs blocks added by the mod as they were redundant
+ Removed the collision of raw ore placed items, so entities like players and mobs won't collide with them
+ Updated the mod to require Fabric API 0.110.0 & Fabric Loader 0.16.9


# 0.4.1 Alpha
+ Added BTWR: Core v0.26 as a dependency as it was removed by mistake during one of the last updates.
+ Added another recipe for Furnace made from four loose cobblestone slabs.
  This makes getting furnace in the very early game much 
  more accessible because the previous recipe required eight whole loose blocks.
+ Fixed iron chisel being too slow when breaking stump blocks from Sturdy Trees.
+ Updated the mod to Fabric Loader 0.16.7 & Fabric API 0.107.0


# 0.4 Alpha

+ Added 2 new mod menu configuration options:
 1. Block Breaking Restrictions - Makes blocks unbreakable if the player is not using the correct tool.

 2. Stratification Toughness - Makes some blocks that are considered higher strata than stone level blocks require higher level pickaxes to mine effectively.
+ Improved code for breaking dirt into loose dirt and also added other dirt-like blocks to break in the same way.
+ General improvements of loot tables for blocks to drop the proper items and remove/fix wrong drops.
+ Brought back the ability to combine tools to repair them. (will only be modified in the btwr modpack)
+ Fixed placement code for ore chunk blocks on the ground to work as intended.
+ Fixed a bug that caused falling(loose) block stairs to turn to their full block counterpart when falling.
+ Fixed the hardcore mining speed configuration setting in Mod Menu to work properly.
+ Fixed mining speed of primitive chisels for higher tier ores to be slower.
+ Fixed mod icon in the creative mod menu to display properly instead of the "missing" texture.
+ Fixed some chisels making a ding sound on the wrong block breaks.
+ Changed diamond chisel's crafting recipe to use a diamond instead of diamond ingot by default.
+ Removed the new texture for copper ore chunk and reverted it to the vanilla original one for raw copper.
+ Other minor fixes/improvements which should've worked as intended.
+ Updated the mod to Minecraft 1.21.1, Fabric Loader 0.16.5 & Fabric API 0.104.0

# 0.3 Alpha
+ The mod now requires BTWR:Core v0.25. It didn't require it before, which caused some blocks to not drop correctly when mined.
+ Added Pile of Gold Dust and Pile of Copper Dust items.
+ Added Pile of Coal Dust and Wooden Chisel as viable fuel items.
+ Changed Anvil's recipe to only require ingots.
+ Changed Coarse Dirt, Farmland & Mycelium to drop loose dirt and piles when they should.
+ Changed names of Sharp Stick and Sharp Stone to be called "Chisels". Hi Zhil ;)
+ Changed (improved) code for falling of loose blocks.
+ Changed the mod to require Fabric API 0.92.2
+ Changes to conventional tags.
+ Fixed most loot tables for ore blocks to drop the correct items.
+ Fixed code to work as intended on the server side.

# 0.2 Alpha
+ Added missing "brick to loose brick" slab recipe.
+ Added dirt piles drop when clay blocks get broken.
+ Added wooden chisel to be usable as a spit for campfires from the Self-Sustainable mod.
+ Added missing loot table for Podzol breaking into loose dirt/dirt piles.
+ Added missing recipe for loose bricks block from loose brick slabs.
+ Made Raw ore items(chunks) to be placeable in the world as blocks.

+ Reworked breaking speeds logic and fixed a bug that made haste, efficiency and mining fatigue not apply to items.
+ Changed Stone and Iron pickaxes to no longer break strata 3 stones (deepslate & similar).
+ Changed Raw Iron, Raw Gold & Raw Copper item's display names to "Chunks of ore".
+ Changed Compass, Clock & Flint & Steel and Bucket recipes to use nuggets instead of ingots.

+ Fixed a bug with wrong recipe for loose bricks from item.
+ Fixed missing loose cobbled Deepslate drop from breaking Deepslate.

+ Temporary disabled config for hardcore speed until future update.


# 0.1.1 Alpha

+ Changed version numbering from 1.0 to 0.1
+ Fixed a bug where dirt wasn't dropping dirt piles when broken with hand.
+ Increased mining speed for pickaxes on depleted(broken stone) blocks.
+ Updated mod to Fabric Loader 0.5.11

# 0.1 Alpha

+ Initial release