package net.mrscauthd.boss_tools.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.OxygenLoaderGuiGuiWindow;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.jei.JeiPlugin.OxygenLoadingJeiCategory;
import net.mrscauthd.boss_tools.jei.JeiPlugin.OxygenMakingJeiCategory;

public class OxygenLoaderGuiContainerHandler implements IGuiContainerHandler<OxygenLoaderGuiGuiWindow> {

	public OxygenLoaderGuiContainerHandler() {

	}

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(OxygenLoaderGuiGuiWindow containerScreen, double mouseX, double mouseY) {
		return Collections.singleton(new IGuiClickableArea() {
			@Override
			public Rectangle2d getArea() {
				return GuiHelper.getOxygenBounds(OxygenLoaderGuiGuiWindow.OXYGEN_LEFT, OxygenLoaderGuiGuiWindow.OXYGEN_TOP);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
				recipesGui.showCategories(Arrays.asList(OxygenMakingJeiCategory.Uid, OxygenLoadingJeiCategory.Uid));
			}

			@Override
			public List<ITextComponent> getTooltipStrings() {
				List<ITextComponent> list = new ArrayList<>();
				list.add(GaugeValueHelper.getOxygen(containerScreen.getTileEntity().getOxygenPowerSystem()).getText());
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

}