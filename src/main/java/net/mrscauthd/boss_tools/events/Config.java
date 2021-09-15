package net.mrscauthd.boss_tools.events;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

@Mod.EventBusSubscriber(modid = "boss_tools",bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	public static boolean AlienSpawing = true;
	public static boolean AlienVillageStructure = true;
	public static boolean MeteorStructure = true;
	public static boolean PlayerOxygenSystem = true;
	public static boolean StarCrawlerSpawn = true;
	public static boolean AlienZombieSpawn = true;
	public static boolean EntityOxygenSystem = true;
	public static boolean VenusBulletStructure = true;
	public static boolean VenusTowerStructure = true;
	public static boolean OILWellStructure = true;
		@SubscribeEvent
		public static void init(FMLCommonSetupEvent event) {
			File boss_tools = new File((FMLPaths.GAMEDIR.get().toString() + "" + ("//config/")), File.separator + "space-bosstools-config.json");
			if (!boss_tools.exists()) {
				try {
					boss_tools.createNewFile();
					// new
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonObject Config = new JsonObject();
					Config.addProperty("Alien Spawn", (true));
					Config.addProperty("Star Crawler Spawn", (true));
					Config.addProperty("Alien Zombie Spawn", (true));
					Config.addProperty("Alien Village Structure", (true));
					Config.addProperty("Meteor Structure", (true));
					Config.addProperty("Venus Bullet Structure", (true));
					Config.addProperty("Venus Tower Structure", (true));
					Config.addProperty("Oil Well", (true));
					Config.addProperty("Player Oxygen System", (true));
					Config.addProperty("Entity Oxygen System", (true));
					try {
						FileWriter boss_toolsfw = new FileWriter(boss_tools);
						boss_toolsfw.write(gson.toJson(Config));
						boss_toolsfw.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
			{
				try {
					BufferedReader boss_toolsReader = new BufferedReader(new FileReader(boss_tools));
					StringBuilder jsonstringbuilder = new StringBuilder();
					String line;
					while ((line = boss_toolsReader.readLine()) != null) {
						jsonstringbuilder.append(line);
					}
					JsonObject Config = new Gson().fromJson(jsonstringbuilder.toString(), JsonObject.class);
					// First
					if (Config == null) { // Category check
						Gson gson = new GsonBuilder().setPrettyPrinting().create();
						Config = new JsonObject();
						Config.addProperty("Alien Spawn", (true));
						Config.addProperty("Star Crawler Spawn", (true));
						Config.addProperty("Alien Zombie Spawn", (true));
						Config.addProperty("Alien Village Structure", (true));
						Config.addProperty("Meteor Structure", (true));
						Config.addProperty("Venus Bullet Structure", (true));
						Config.addProperty("Venus Tower Structure", (true));
						Config.addProperty("Oil Well", (true));
						Config.addProperty("Player Oxygen System", (true));
						Config.addProperty("Entity Oxygen System", (true));
						try {
							FileWriter boss_toolsfw = new FileWriter(boss_tools);
							boss_toolsfw.write(gson.toJson(Config));
							boss_toolsfw.close();
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
					// in category check
					if (Config.get("Alien Spawn") == null || Config.get("Star Crawler Spawn") == null || Config.get("Alien Zombie Spawn") == null || Config.get("Alien Village Structure") == null || Config.get("Meteor Structure") == null || Config.get("Player Oxygen System") == null || Config.get("Entity Oxygen System") == null || Config.get("Venus Bullet Structure") == null || Config.get("Venus Tower Structure") == null || Config.get("Oil Well") == null) {
						Gson gson = new GsonBuilder().setPrettyPrinting().create();
						Config = new JsonObject();
						Config.addProperty("Alien Spawn", (true));
						Config.addProperty("Star Crawler Spawn", (true));
						Config.addProperty("Alien Zombie Spawn", (true));
						Config.addProperty("Alien Village Structure", (true));
						Config.addProperty("Meteor Structure", (true));
						Config.addProperty("Venus Bullet Structure", (true));
						Config.addProperty("Venus Tower Structure", (true));
						Config.addProperty("Oil Well", (true));
						Config.addProperty("Player Oxygen System", (true));
						Config.addProperty("Entity Oxygen System", (true));
						try {
							FileWriter boss_toolsfw = new FileWriter(boss_tools);
							boss_toolsfw.write(gson.toJson(Config));
							boss_toolsfw.close();
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
					// first end
					boss_toolsReader.close();
					// first
					AlienSpawing = (boolean) Config.get("Alien Spawn").getAsBoolean();
					StarCrawlerSpawn = (boolean) Config.get("Star Crawler Spawn").getAsBoolean();
					AlienZombieSpawn = (boolean) Config.get("Alien Zombie Spawn").getAsBoolean();
					// Category structures
					AlienVillageStructure = (boolean) Config.get("Alien Village Structure").getAsBoolean();
					MeteorStructure = (boolean) Config.get("Meteor Structure").getAsBoolean();
					VenusBulletStructure = (boolean) Config.get("Venus Bullet Structure").getAsBoolean();
					VenusTowerStructure = (boolean) Config.get("Venus Bullet Structure").getAsBoolean();
					OILWellStructure = (boolean) Config.get("Oil Well").getAsBoolean();
					// Oxygen System
					PlayerOxygenSystem = (boolean) Config.get("Player Oxygen System").getAsBoolean();
					EntityOxygenSystem = (boolean) Config.get("Entity Oxygen System").getAsBoolean();
					// next
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}
