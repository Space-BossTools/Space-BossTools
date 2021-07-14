package net.mrscauthd.boss_tools;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.FullscreenResolutionOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.GPUWarningScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestGui extends SettingsScreen {
	private static final ITextComponent field_241598_c_ = (new TranslationTextComponent("options.graphics.fabulous")).mergeStyle(TextFormatting.ITALIC);
	private static final ITextComponent field_241599_p_ = new TranslationTextComponent("options.graphics.warning.message", field_241598_c_, field_241598_c_);
	private static final ITextComponent field_241600_q_ = (new TranslationTextComponent("options.graphics.warning.title")).mergeStyle(TextFormatting.RED);
	private static final ITextComponent field_241601_r_ = new TranslationTextComponent("options.graphics.warning.accept");
	private static final ITextComponent field_241602_s_ = new TranslationTextComponent("options.graphics.warning.cancel");
	private static final ITextComponent field_241603_t_ = new StringTextComponent("\n");

	protected static ITextComponent getGenericValueComponent(ITextComponent valueMessage) {
		return new TranslationTextComponent("options.generic_value", null, valueMessage);
	}

	public static final SliderPercentageOption RENDER_DISTANCE = new SliderPercentageOption("Stars", 10.0D, 100.0D, 1.0F, (settings) -> {
		return (double)settings.renderDistanceChunks;
	}, (settings, optionValues) -> {
		settings.renderDistanceChunks = (int)optionValues.doubleValue();
		Minecraft.getInstance().worldRenderer.setDisplayListEntitiesDirty();
	}, (settings, optionValues) -> {
		double d0 = optionValues.get(settings);
		return getGenericValueComponent(new TranslationTextComponent("%", (int)d0));
	});

	private static final AbstractOption[] OPTIONS = new AbstractOption[]{RENDER_DISTANCE};
	private OptionsRowList optionsRowList;
	private final GPUWarning field_241604_x_;
	private final int mipmapLevels;

	public TestGui(Screen parentScreenIn, GameSettings gameSettingsIn) {
		super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("Space Settings"));
		this.field_241604_x_ = parentScreenIn.getMinecraft().getGPUWarning();
		this.field_241604_x_.func_241702_i_();
		if (gameSettingsIn.graphicFanciness == GraphicsFanciness.FABULOUS) {
			this.field_241604_x_.func_241698_e_();
		}

		this.mipmapLevels = gameSettingsIn.mipmapLevels;
	}

	protected void init() {
		this.optionsRowList = new OptionsRowList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
	//	this.optionsRowList.addOption(new FullscreenResolutionOption(this.minecraft.getMainWindow()));
	//	this.optionsRowList.addOption(AbstractOption.BIOME_BLEND_RADIUS);
		this.optionsRowList.addOptions(OPTIONS);
		this.children.add(this.optionsRowList);
		//Done Button
		this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, (p_213106_1_) -> {
			this.minecraft.gameSettings.saveOptions();
			this.minecraft.getMainWindow().update();
			this.minecraft.displayGuiScreen(this.parentScreen);
		}));
	}

	public void onClose() {
		if (this.gameSettings.mipmapLevels != this.mipmapLevels) {
			this.minecraft.setMipmapLevels(this.gameSettings.mipmapLevels);
			this.minecraft.scheduleResourcesRefresh();
		}

		super.onClose();
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		int i = this.gameSettings.guiScale;
		if (super.mouseClicked(mouseX, mouseY, button)) {
			if (this.gameSettings.guiScale != i) {
				this.minecraft.updateWindowSize();
			}

			if (this.field_241604_x_.func_241700_g_()) {
				List<ITextProperties> list = Lists.newArrayList(field_241599_p_, field_241603_t_);
				String s = this.field_241604_x_.func_241703_j_();
				if (s != null) {
					list.add(field_241603_t_);
					list.add((new TranslationTextComponent("options.graphics.warning.renderer", s)).mergeStyle(TextFormatting.GRAY));
				}

				String s1 = this.field_241604_x_.func_241705_l_();
				if (s1 != null) {
					list.add(field_241603_t_);
					list.add((new TranslationTextComponent("options.graphics.warning.vendor", s1)).mergeStyle(TextFormatting.GRAY));
				}

				String s2 = this.field_241604_x_.func_241704_k_();
				if (s2 != null) {
					list.add(field_241603_t_);
					list.add((new TranslationTextComponent("options.graphics.warning.version", s2)).mergeStyle(TextFormatting.GRAY));
				}

			/*	this.minecraft.displayGuiScreen(new GPUWarningScreen(field_241600_q_, list, ImmutableList.of(new GPUWarningScreen.Option(field_241601_r_, (p_241606_1_) -> {
					this.gameSettings.graphicFanciness = GraphicsFanciness.FABULOUS;
					Minecraft.getInstance().worldRenderer.loadRenderers();
					this.field_241604_x_.func_241698_e_();
					this.minecraft.displayGuiScreen(this);
				}), new GPUWarningScreen.Option(field_241602_s_, (p_241605_1_) -> {
					this.field_241604_x_.func_241699_f_();
					this.minecraft.displayGuiScreen(this);
				}))));*/
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		int i = this.gameSettings.guiScale;
		if (super.mouseReleased(mouseX, mouseY, button)) {
			return true;
		} else if (this.optionsRowList.mouseReleased(mouseX, mouseY, button)) {
			if (this.gameSettings.guiScale != i) {
				this.minecraft.updateWindowSize();
			}

			return true;
		} else {
			return false;
		}
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
		drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 5, 16777215);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		List<IReorderingProcessor> list = func_243293_a(this.optionsRowList, mouseX, mouseY);
		if (list != null) {
			this.renderTooltip(matrixStack, list, mouseX, mouseY);
		}

	}
}