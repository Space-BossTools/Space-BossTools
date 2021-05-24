package net.mrscauthd.boss_tools.procedures;

import net.mrscauthd.boss_tools.BossToolsModVariables;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.Collections;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class ConfigProcedure {
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void init(FMLCommonSetupEvent event) {
			executeProcedure(Collections.emptyMap());
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		String testconfig = "";
		String Meteor = "";
		String StarCrawler = "";
		String alienhouseconfig = "";
		String oxygen = "";
		String titleconfig = "";
		String AlienZombie = "";
		String EntityDamage = "";
		File boss_tools = new File(((System.getProperty("user.dir")) + "" + ("//config/")) + "/" + "space-bosstools-config.toml");
		try {
			final BufferedReader boss_toolsReader = new BufferedReader(new FileReader(boss_tools));
			String boss_toolsReadLine = null;
			boss_toolsReadLine = boss_toolsReader.readLine();
			titleconfig = (String) (boss_toolsReadLine);
			boss_toolsReader.close();
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		if ((!(((titleconfig)).equals("# Space-Bosstools-Config-v5.3#")))) {
			try {
				FileWriter boss_toolswriter = new FileWriter(boss_tools);
				BufferedWriter boss_toolsbw = new BufferedWriter(boss_toolswriter);
				boss_toolsbw.write("# Space-Bosstools-Config-v5.3#");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Alien Spawn = true");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Alien Village Structure = true");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Meteor Structure = true");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Player Oxygen System = true");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Star Crawler = true");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Alien Zombie = true");
				boss_toolsbw.newLine();
				boss_toolsbw.write("Entity Oxygen Damage = true");
				boss_toolsbw.close();
				boss_toolswriter.close();
			} catch (FileNotFoundException fileNotFoundException) {
				fileNotFoundException.printStackTrace();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		} // logic Alien Spawn
		try {
			final BufferedReader boss_toolsReader = new BufferedReader(new FileReader(boss_tools));
			String boss_toolsReadLine = null;
			boss_toolsReadLine = boss_toolsReader.readLine();
			titleconfig = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			testconfig = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			alienhouseconfig = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			Meteor = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			oxygen = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			StarCrawler = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			AlienZombie = (String) (boss_toolsReadLine);
			boss_toolsReadLine = boss_toolsReader.readLine();
			EntityDamage = (String) (boss_toolsReadLine);
			boss_toolsReader.close();
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		if ((((testconfig)).equals("Alien Spawn = true"))) {
			BossToolsModVariables.Config = (double) 1;
		}
		if ((((testconfig)).equals("Alien Spawn = false"))) {
			BossToolsModVariables.Config = (double) 2;
		}
		if (((!(((testconfig)).equals("Alien Spawn = true"))) && (!(((testconfig)).equals("Alien Spawn = false"))))) {
			BossToolsModVariables.Config = (double) 1;
		} // logic Alien House
		if ((((alienhouseconfig)).equals("Alien Village Structure = true"))) {
			BossToolsModVariables.Configalienhouse = (double) 1;
		}
		if ((((alienhouseconfig)).equals("Alien Village Structure = false"))) {
			BossToolsModVariables.Configalienhouse = (double) 2;
		}
		if (((!(((alienhouseconfig)).equals("Alien Village Structure = true")))
				&& (!(((alienhouseconfig)).equals("Alien Village Structure = false"))))) {
			BossToolsModVariables.Configalienhouse = (double) 1;
		} // logic Meteor
		if ((((Meteor)).equals("Meteor Structure = true"))) {
			BossToolsModVariables.ConfigMeteor = (double) 1;
		}
		if ((((Meteor)).equals("Meteor Structure = false"))) {
			BossToolsModVariables.ConfigMeteor = (double) 2;
		}
		if (((!(((Meteor)).equals("Meteor Structure = true"))) && (!(((Meteor)).equals("Meteor Structure = false"))))) {
			BossToolsModVariables.ConfigMeteor = (double) 1;
		} // logic oxygen System
		if ((((oxygen)).equals("Player Oxygen System = true"))) {
			BossToolsModVariables.oxygen_system = (double) 1;
		}
		if ((((oxygen)).equals("Player Oxygen System = false"))) {
			BossToolsModVariables.oxygen_system = (double) 2;
		}
		if (((!(((oxygen)).equals("Player Oxygen System = true"))) && (!(((oxygen)).equals("Player Oxygen System = false"))))) {
			BossToolsModVariables.oxygen_system = (double) 1;
		} // Star Crawler
		if ((((StarCrawler)).equals("Star Crawler = true"))) {
			BossToolsModVariables.StarCrawler = (double) 1;
		}
		if ((((StarCrawler)).equals("Star Crawler = false"))) {
			BossToolsModVariables.StarCrawler = (double) 2;
		}
		if (((!(((StarCrawler)).equals("Star Crawler = true"))) && (!(((StarCrawler)).equals("Star Crawler = false"))))) {
			BossToolsModVariables.StarCrawler = (double) 1;
		} // Alien Zombie
		if ((((AlienZombie)).equals("Alien Zombie = true"))) {
			BossToolsModVariables.AlienZombie = (double) 1;
		}
		if ((((AlienZombie)).equals("Alien Zombie = false"))) {
			BossToolsModVariables.AlienZombie = (double) 2;
		}
		if (((!(((AlienZombie)).equals("Alien Zombie = true"))) && (!(((AlienZombie)).equals("Alien Zombie = false"))))) {
			BossToolsModVariables.AlienZombie = (double) 1;
		} // Entity Damage
		if ((((EntityDamage)).equals("Entity Oxygen Damage = true"))) {
			BossToolsModVariables.EntityDamage = (double) 1;
		}
		if ((((EntityDamage)).equals("Entity Oxygen Damage = false"))) {
			BossToolsModVariables.EntityDamage = (double) 2;
		}
		if (((!(((EntityDamage)).equals("Entity Oxygen Damage = true"))) && (!(((EntityDamage)).equals("Entity Oxygen Damage = false"))))) {
			BossToolsModVariables.EntityDamage = (double) 1;
		}
	}
}
