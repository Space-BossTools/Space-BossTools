package net.mrscauthd.boss_tools;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
//import space_traveler.com.space_traveler.Space_traveler;

public enum AlienJobs {
    JOB1("job.boss_tools.job1",new ResourceLocation("boss_tools","textures/entity_alien.png"),0),
    JOB2("job.boss_tools.job2",new ResourceLocation("boss_tools","textures/entity_alien2.png"),1),
    JOB3("job.boss_tools.job3",new ResourceLocation("boss_tools","textures/entity_alien3.png"),2),
    JOB4("job.boss_tools.job4",new ResourceLocation("boss_tools","textures/entity_alien4.png"),3),
    JOB5("job.boss_tools.job5",new ResourceLocation("boss_tools","textures/entity_alien5.png"),4),
    JOB6("job.boss_tools.job6",new ResourceLocation("boss_tools","textures/entity_alien6.png"),5),
    JOB7("job.boss_tools.job7",new ResourceLocation("boss_tools","textures/entity_alien7.png"),6),
    JOB8("job.boss_tools.job8",new ResourceLocation("boss_tools","textures/entity_alien8.png"),7),
    JOB9("job.boss_tools.job9",new ResourceLocation("boss_tools","textures/entity_alien9.png"),8),
    JOB10("job.boss_tools.job10",new ResourceLocation("boss_tools","textures/entity_alien10.png"),9),
    JOB11("job.boss_tools.job11",new ResourceLocation("boss_tools","textures/entity_alien11.png"),10),
    JOB12("job.boss_tools.job12",new ResourceLocation("boss_tools","textures/entity_alien12.png"),11),
    JOB13("job.boss_tools.job13",new ResourceLocation("boss_tools","textures/entity_alien13.png"),12);

    public ResourceLocation TEXTURE;
    private String TTC;
    public int id;

    private AlienJobs(String TTC, ResourceLocation texture,int i){
        this.TEXTURE = texture;
        this.TTC = TTC;
        this.id = i;
    }

    private AlienJobs(ResourceLocation texture) {
        this.TEXTURE = texture;
    }
    public TranslationTextComponent getJobDisplayname() {
        return new TranslationTextComponent(TTC);
    }
}