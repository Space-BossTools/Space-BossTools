package net.mrscauthd.boss_tools.jei.machineguihandlers;

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
import net.mrscauthd.boss_tools.gui.CompressorGuiGuiWindow;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.jei.JeiPlugin.CompressorJeiCategory;

public class CompressorGuiContainerHandler implements IGuiContainerHandler<CompressorGuiGuiWindow> {

	public CompressorGuiContainerHandler() {

	}

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(CompressorGuiGuiWindow containerScreen, double mouseX, double mouseY) {
		return Collections.singleton(new IGuiClickableArea() {
			@Override
			public Rectangle2d getArea() {
				return GuiHelper.getArrowBounds(CompressorGuiGuiWindow.ARROW_LEFT, CompressorGuiGuiWindow.ARROW_TOP);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
				recipesGui.showCategories(Arrays.asList(CompressorJeiCategory.Uid));
			}

			@Override
			public List<ITextComponent> getTooltipStrings() {
				List<ITextComponent> list = new ArrayList<>();
				list.add(containerScreen.getTileEntity().getCookTimeGaugeData().getText());
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

}
