package com.dmtrllv.nova.mixin;

import javax.annotation.Nullable;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.world.events.BloodMoonEvent;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	@Nullable
	private ClientLevel level;

	@Shadow
	@Nullable
	private VertexBuffer starBuffer;

	@Shadow
	@Nullable
	private VertexBuffer darkBuffer;

	@Shadow
	private int ticks;

	@Shadow
	private void renderEndSky(PoseStack poseStack)
	{
		throw new IllegalStateException("Mixin failed to shadow renderEndSky(PoseStack)!");
	}

	private static final ResourceLocation MOON_LOCATION = new ResourceLocation(Nova.MOD_ID, "textures/environment/moon_phases.png");
	private static final ResourceLocation BLOOD_MOON_LOCATION = new ResourceLocation(Nova.MOD_ID, "textures/environment/blood_moon_phases.png");
	private static final ResourceLocation SUN_LOCATION = new ResourceLocation(Nova.MOD_ID, "textures/environment/sun.png");

	@Shadow
	@Nullable
	private VertexBuffer skyBuffer;

	@Inject(at = @At("HEAD"), method = "renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/math/Matrix4f;FLjava/lang/Runnable;)V", cancellable = true)
	private void renderSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTicks, Runnable fogSetup, CallbackInfo callback)
	{
		fogSetup.run();

		net.minecraftforge.client.ISkyRenderHandler renderHandler = level.effects().getSkyRenderHandler();

		if (renderHandler != null)
		{
			renderHandler.render(ticks, partialTicks, poseStack, level, minecraft);
		}
		else if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.END)
		{
			this.renderEndSky(poseStack);
		}
		else if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL)
		{
			boolean isBloodMoonActive = BloodMoonEvent.isBloodMoonActive();

			RenderSystem.disableTexture();
			Vec3 vec3 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), partialTicks);
			float f = (float) vec3.x;
			float f1 = (float) vec3.y;
			float f2 = (float) vec3.z;

			// if(isBloodMoonActive)
				// RenderSystem.setShaderFogColor(BloodMoonEvent.getRedColor(f), f1, f2, 1.0F);
			// else
			FogRenderer.levelFogColor();

			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
			RenderSystem.depthMask(false);
			RenderSystem.setShaderColor(isBloodMoonActive ? f + 0.05f : f, f1, f2, 1.0F);
			
			ShaderInstance shaderinstance = RenderSystem.getShader();
			this.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
			
			RenderSystem.enableBlend();
			
			RenderSystem.defaultBlendFunc();
			float[] afloat = this.level.effects().getSunriseColor(this.level.getTimeOfDay(partialTicks), partialTicks);
			if (afloat != null)
			{
				RenderSystem.setShader(GameRenderer::getPositionColorShader);
				RenderSystem.disableTexture();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				poseStack.pushPose();
				poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
				float f3 = Mth.sin(this.level.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(f3));
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
				float f4 = afloat[0];
				float f5 = afloat[1];
				float f6 = afloat[2];
				Matrix4f matrix4f = poseStack.last().pose();
				bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
				bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();

				for (int j = 0; j <= 16; ++j)
				{
					float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
					float f8 = Mth.sin(f7);
					float f9 = Mth.cos(f7);
					bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
				}

				bufferbuilder.end();
				BufferUploader.end(bufferbuilder);
				poseStack.popPose();
			}

			RenderSystem.enableTexture();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			poseStack.pushPose();
			float f11 = 1.0F - this.level.getRainLevel(partialTicks);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
			poseStack.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(partialTicks) * 360.0F));
			Matrix4f matrix4f1 = poseStack.last().pose();
			float f12 = 30.0F;
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, SUN_LOCATION);
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
			bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
			bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
			bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
			bufferbuilder.end();
			BufferUploader.end(bufferbuilder);
			f12 = 20.0F;
			RenderSystem.setShaderTexture(0, isBloodMoonActive ? BLOOD_MOON_LOCATION : MOON_LOCATION);
			int k = this.level.getMoonPhase();
			int l = k % 4;
			int i1 = k / 4 % 2;
			float f13 = (float) (l + 0) / 4.0F;
			float f14 = (float) (i1 + 0) / 2.0F;
			float f15 = (float) (l + 1) / 4.0F;
			float f16 = (float) (i1 + 1) / 2.0F;
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
			bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
			bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
			bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
			bufferbuilder.end();
			BufferUploader.end(bufferbuilder);
			RenderSystem.disableTexture();
			float f10 = this.level.getStarBrightness(partialTicks) * f11;
			
			if (f10 > 0.0F)
			{
				RenderSystem.setShaderColor(isBloodMoonActive ? f10 + 0.05f : f10, f10, f10, f10);
				FogRenderer.setupNoFog();
				this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
				fogSetup.run();
			}

			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableBlend();
			poseStack.popPose();
			RenderSystem.disableTexture();
			RenderSystem.setShaderColor(isBloodMoonActive ? 0.01F : 0.0F, 0.0F, 0.0F, 1.0F);
			double d0 = this.minecraft.player.getEyePosition(partialTicks).y - this.level.getLevelData().getHorizonHeight(this.level);
			if (d0 < 0.0D)
			{
				poseStack.pushPose();
				poseStack.translate(0.0D, 12.0D, 0.0D);
				this.darkBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
				poseStack.popPose();
			}

			if (this.level.effects().hasGround())
			{
				RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
			}
			else
			{
				RenderSystem.setShaderColor(f, f1, f2, 1.0F);
			}

			RenderSystem.enableTexture();
			RenderSystem.depthMask(true);
		}

		callback.cancel();
	}
}
