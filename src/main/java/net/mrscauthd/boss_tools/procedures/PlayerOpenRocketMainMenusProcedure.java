package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.gui.Tier3mainMenuSpaceStation5Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenuSpaceStation4Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenuSpaceStation3Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenuSpaceStation2Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenuSpaceStation1Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenuGui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenu4Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenu3Gui;
import net.mrscauthd.boss_tools.gui.Tier3mainMenu2Gui;
import net.mrscauthd.boss_tools.gui.Tier3MainMenu5Gui;
import net.mrscauthd.boss_tools.gui.Tier2mainMenuSpaceStation3Gui;
import net.mrscauthd.boss_tools.gui.Tier2mainMenuSpaceStation2Gui;
import net.mrscauthd.boss_tools.gui.Tier2mainMenuSpaceStation1Gui;
import net.mrscauthd.boss_tools.gui.Tier2mainMenuGui;
import net.mrscauthd.boss_tools.gui.Tier2mainMenu3Gui;
import net.mrscauthd.boss_tools.gui.Tier2mainMenu2Gui;
import net.mrscauthd.boss_tools.gui.Tier1mainMenuSpaceStationGui;
import net.mrscauthd.boss_tools.gui.Tier1mainMenuSpaceStation2Gui;
import net.mrscauthd.boss_tools.gui.Tier1mainMenuGui;
import net.mrscauthd.boss_tools.gui.Tier1mainMenu2Gui;
import net.mrscauthd.boss_tools.gui.GeneratorGUIGui;
import net.mrscauthd.boss_tools.BossToolsMod;

import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

public class PlayerOpenRocketMainMenusProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.world;
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BossToolsMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerOpenRocketMainMenus!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BossToolsMod.LOGGER.warn("Failed to load dependency x for procedure PlayerOpenRocketMainMenus!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BossToolsMod.LOGGER.warn("Failed to load dependency y for procedure PlayerOpenRocketMainMenus!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BossToolsMod.LOGGER.warn("Failed to load dependency z for procedure PlayerOpenRocketMainMenus!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BossToolsMod.LOGGER.warn("Failed to load dependency world for procedure PlayerOpenRocketMainMenus!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		// Overworld
		if (((entity.getPersistentData().getDouble("Tier_1_open_main_menu_2")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier1mainMenu2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenu2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenu2Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Mars
		if (((entity.getPersistentData().getDouble("Tier_1_open_main_menu_3")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier1mainMenu2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenu2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenu2Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Mercury
		if (((entity.getPersistentData().getDouble("Tier_1_open_main_menu_4")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity && ((ClientPlayerEntity) entity).openContainer instanceof GeneratorGUIGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenu2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenu2Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Back to main menu
		if (((entity.getPersistentData().getDouble("Tier_1_open_main_menu_back")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier1mainMenuGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenu");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenuGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Open Main Menu
		if (((entity.getPersistentData().getDouble("Tier_1_open_main_menu")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier1mainMenuGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenu");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenuGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Player Movement
		if (((entity.getPersistentData().getDouble("Player_movement")) == 1)) {
			entity.setNoGravity((true));
			entity.getPersistentData().putBoolean("Oxygen_Bullet_Generator", (true));
			entity.getPersistentData().putDouble("timer_oxygen", 0);
		} // Space Station Menu
		if (((entity.getPersistentData().getDouble("Tier_1_space_station_open")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier1mainMenuSpaceStationGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenuSpaceStation");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenuSpaceStationGui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_1_space_station_open")) == 2)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier1mainMenuSpaceStation2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier1mainMenuSpaceStation2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier1mainMenuSpaceStation2Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Tier 2 Rocket
		if (((entity.getPersistentData().getDouble("Tier_2_open_main_menu")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier2mainMenuGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenu");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenuGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // main menu back
		if (((entity.getPersistentData().getDouble("Tier_2_open_main_menu_back")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier2mainMenuGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenu");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenuGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Overworld
		if (((entity.getPersistentData().getDouble("Tier_2_open_main_menu_2")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier2mainMenu2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenu2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenu2Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Mars
		if (((entity.getPersistentData().getDouble("Tier_2_open_main_menu_3")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier2mainMenu3Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenu3");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenu3Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Space Station Menu
		if (((entity.getPersistentData().getDouble("Tier_2_space_station_open")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier2mainMenuSpaceStation1Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenuSpaceStation1");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenuSpaceStation1Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_2_space_station_open")) == 2)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier2mainMenuSpaceStation2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenuSpaceStation2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenuSpaceStation2Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_2_space_station_open")) == 3)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuSpaceStation3Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier2mainMenuSpaceStation3");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier2mainMenuSpaceStation3Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Tier 3 Rocket
		if (((entity.getPersistentData().getDouble("Tier_3_open_main_menu")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenu");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // main menu back
		if (((entity.getPersistentData().getDouble("Tier_3_open_main_menu_back")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuGui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenu");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Overworld
		if (((entity.getPersistentData().getDouble("Tier_3_open_main_menu_2")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenu2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenu2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenu2Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Mars
		if (((entity.getPersistentData().getDouble("Tier_3_open_main_menu_3")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenu3Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenu3");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenu3Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Mercury
		if (((entity.getPersistentData().getDouble("Tier_3_open_main_menu_4")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenu4Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenu4");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenu4Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Venus
		if (((entity.getPersistentData().getDouble("Tier_3_open_main_menu_5")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3MainMenu5Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3MainMenu5");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3MainMenu5Gui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		} // Space Station Menu
		if (((entity.getPersistentData().getDouble("Tier_3_space_station_open")) == 1)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuSpaceStation1Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenuSpaceStation1");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuSpaceStation1Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_3_space_station_open")) == 2)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuSpaceStation2Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenuSpaceStation2");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuSpaceStation2Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_3_space_station_open")) == 3)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuSpaceStation3Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenuSpaceStation3");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuSpaceStation3Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_3_space_station_open")) == 4)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuSpaceStation4Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenuSpaceStation4");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuSpaceStation4Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
		if (((entity.getPersistentData().getDouble("Tier_3_space_station_open")) == 5)) {
			if ((!(entity instanceof ClientPlayerEntity
					&& ((ClientPlayerEntity) entity).openContainer instanceof Tier3mainMenuSpaceStation5Gui.GuiContainerMod))) {
				{
					Entity _ent = entity;
					if (_ent instanceof ServerPlayerEntity) {
						BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
						NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
							@Override
							public ITextComponent getDisplayName() {
								return new StringTextComponent("Tier3mainMenuSpaceStation5");
							}

							@Override
							public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
								return new Tier3mainMenuSpaceStation5Gui.GuiContainerMod(id, inventory,
										new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
				}
			}
		}
	}
}
