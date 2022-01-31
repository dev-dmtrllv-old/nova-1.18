// package com.dmtrllv.nova.renderer;

// import java.util.Random;

// import javax.annotation.Nullable;

// import com.dmtrllv.nova.Nova;
// import com.dmtrllv.nova.world.events.BloodMoonEvent;
// import com.mojang.blaze3d.platform.GlStateManager;
// import com.mojang.blaze3d.systems.RenderSystem;
// import com.mojang.blaze3d.vertex.BufferBuilder;
// import com.mojang.blaze3d.vertex.BufferUploader;
// import com.mojang.blaze3d.vertex.DefaultVertexFormat;
// import com.mojang.blaze3d.vertex.PoseStack;
// import com.mojang.blaze3d.vertex.Tesselator;
// import com.mojang.blaze3d.vertex.VertexBuffer;
// import com.mojang.blaze3d.vertex.VertexFormat;
// import com.mojang.math.Matrix4f;
// import com.mojang.math.Vector3f;

// import net.minecraft.client.Minecraft;
// import net.minecraft.client.multiplayer.ClientLevel;
// import net.minecraft.client.renderer.FogRenderer;
// import net.minecraft.client.renderer.GameRenderer;
// import net.minecraft.client.renderer.ShaderInstance;
// import net.minecraft.client.renderer.DimensionSpecialEffects;
// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.util.Mth;
// import net.minecraft.world.level.Level;
// import net.minecraft.world.phys.Vec3;
// import net.minecraftforge.client.ISkyRenderHandler;
// import net.minecraftforge.client.event.EntityViewRenderEvent;
// import net.minecraftforge.client.event.RenderLevelLastEvent;

// public class NovaSkyRenderHandler implements ISkyRenderHandler
// {
// 	public static final NovaSkyRenderHandler INSTANCE = new NovaSkyRenderHandler();

// 	@Nullable
// 	private static Minecraft minecraft = null;

// 	// public static void onFogDensityEvent(EntityViewRenderEvent.FogDensity event)
// 	// {
// 		// if (minecraft != null && minecraft.player.level.dimension() == Level.OVERWORLD && BloodMoonEvent.isBloodMoonActive())
// 		// {
// 		// 	event.setDensity(0.35F);
// 		// }
// 		// event.setDensity(10000.0f);
// 	// }

// 	public static void onFogColorEvent(EntityViewRenderEvent.FogColors event)
// 	{
// 		if (minecraft != null && minecraft.player.level.dimension() == Level.OVERWORLD)
// 			event.setRed(BloodMoonEvent.getRedColor(event.getRed()));
// 	}

// 	public static void initialize(RenderLevelLastEvent event)
// 	{
// 		NovaSkyRenderHandler.minecraft = Minecraft.getInstance();
// 		minecraft.level.effects().setSkyRenderHandler(INSTANCE);
// 	}

// 	private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
// 	private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
// 	private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");

// 	private static final ResourceLocation BLOOD_MOON_LOCATION = new ResourceLocation(Nova.MOD_ID, "textures/environment/moon_phases.png");

// 	@Nullable
// 	private VertexBuffer starBuffer;

// 	@Nullable
// 	private VertexBuffer skyBuffer;

// 	@Nullable
// 	private VertexBuffer darkBuffer;

// 	@Nullable
// 	private VertexBuffer darkBloodMoonBuffer;

// 	private final ClientLevel level;

// 	private final VertexFormat skyFormat = DefaultVertexFormat.POSITION;

// 	private NovaSkyRenderHandler()
// 	{
// 		minecraft = Minecraft.getInstance();
// 		this.level = minecraft.level;
		
// 		this.createStars();
// 		this.createLightSky();
// 		this.createDarkSky();
// 		this.createBloodMoonSky();
// 	}

// 	private void createBloodMoonSky()
// 	{
// 		Tesselator tessellator = Tesselator.getInstance();
// 		BufferBuilder bufferbuilder = tessellator.getBuilder();
// 		if (this.darkBloodMoonBuffer != null)
// 			this.darkBloodMoonBuffer.close();

// 		this.darkBloodMoonBuffer = new VertexBuffer();
// 		buildSkyDisc(bufferbuilder, -16.0F);
// 		bufferbuilder.end();
// 		this.darkBloodMoonBuffer.upload(bufferbuilder);
// 	}

// 	private void createDarkSky()
// 	{
// 		Tesselator tesselator = Tesselator.getInstance();
// 		BufferBuilder bufferbuilder = tesselator.getBuilder();
// 		if (this.darkBuffer != null)
// 		{
// 			this.darkBuffer.close();
// 		}

// 		this.darkBuffer = new VertexBuffer();
// 		buildSkyDisc(bufferbuilder, -16.0F);
// 		this.darkBuffer.upload(bufferbuilder);
// 	}

// 	private void createStars()
// 	{
// 		Tesselator tessellator = Tesselator.getInstance();
// 		BufferBuilder bufferbuilder = tessellator.getBuilder();
// 		if (this.starBuffer != null)
// 		{
// 			this.starBuffer.close();
// 		}

// 		this.starBuffer = new VertexBuffer();
// 		this.drawStars(bufferbuilder);
// 		bufferbuilder.end();
// 		this.starBuffer.upload(bufferbuilder);
// 	}

// 	@SuppressWarnings("unused")
// 	private void drawStars(BufferBuilder bufferBuilder)
// 	{
// 		Random random = new Random(10842L);
// 		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

// 		for (int i = 0; i < 1500; ++i)
// 		{
// 			double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
// 			double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
// 			double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
// 			double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
// 			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
// 			if (d4 < 1.0D && d4 > 0.01D)
// 			{
// 				d4 = 1.0D / Math.sqrt(d4);
// 				d0 *= d4;
// 				d1 *= d4;
// 				d2 *= d4;
// 				double d5 = d0 * 100.0D;
// 				double d6 = d1 * 100.0D;
// 				double d7 = d2 * 100.0D;
// 				double d8 = Math.atan2(d0, d2);
// 				double d9 = Math.sin(d8);
// 				double d10 = Math.cos(d8);
// 				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
// 				double d12 = Math.sin(d11);
// 				double d13 = Math.cos(d11);
// 				double d14 = random.nextDouble() * Math.PI * 2.0D;
// 				double d15 = Math.sin(d14);
// 				double d16 = Math.cos(d14);

// 				for (int j = 0; j < 4; ++j)
// 				{
// 					double d17 = 0.0D;
// 					double d18 = (double) ((j & 2) - 1) * d3;
// 					double d19 = (double) ((j + 1 & 2) - 1) * d3;
// 					double d20 = 0.0D;
// 					double d21 = d18 * d16 - d19 * d15;
// 					double d22 = d19 * d16 + d18 * d15;
// 					double d23 = d21 * d12 + 0.0D * d13;
// 					double d24 = 0.0D * d12 - d21 * d13;
// 					double d25 = d24 * d9 - d22 * d10;
// 					double d26 = d22 * d9 + d24 * d10;
// 					bufferBuilder.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
// 				}
// 			}
// 		}

// 	}

// 	private void createLightSky()
// 	{
// 		Tesselator tesselator = Tesselator.getInstance();
// 		BufferBuilder bufferbuilder = tesselator.getBuilder();
// 		if (this.skyBuffer != null)
// 		{
// 			this.skyBuffer.close();
// 		}

// 		this.skyBuffer = new VertexBuffer();
// 		buildSkyDisc(bufferbuilder, 16.0F);
// 		this.skyBuffer.upload(bufferbuilder);
// 	}

// 	@SuppressWarnings("unused")
// 	private static void buildSkyDisc(BufferBuilder bufferBuilder, float fl)
// 	{
// 		float f = Math.signum(fl) * 512.0F;
// 		float f1 = 512.0F;
// 		RenderSystem.setShader(GameRenderer::getPositionShader);
// 		bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
// 		bufferBuilder.vertex(0.0D, (double) fl, 0.0D).endVertex();

// 		for (int i = -180; i <= 180; i += 45)
// 		{
// 			bufferBuilder.vertex((double) (f * Mth.cos((float) i * ((float) Math.PI / 180F))), (double) fl, (double) (512.0F * Mth.sin((float) i * ((float) Math.PI / 180F)))).endVertex();
// 		}

// 		bufferBuilder.end();
// 	}

// 	private void renderEndSky(PoseStack poseStack)
// 	{
// 		RenderSystem.enableBlend();
// 		RenderSystem.defaultBlendFunc();
// 		RenderSystem.depthMask(false);
// 		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
// 		RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
// 		Tesselator tesselator = Tesselator.getInstance();
// 		BufferBuilder bufferbuilder = tesselator.getBuilder();

// 		for (int i = 0; i < 6; ++i)
// 		{
// 			poseStack.pushPose();
// 			if (i == 1)
// 			{
// 				poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
// 			}

// 			if (i == 2)
// 			{
// 				poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
// 			}

// 			if (i == 3)
// 			{
// 				poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
// 			}

// 			if (i == 4)
// 			{
// 				poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
// 			}

// 			if (i == 5)
// 			{
// 				poseStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
// 			}

// 			Matrix4f matrix4f = poseStack.last().pose();
// 			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
// 			bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
// 			bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
// 			bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
// 			bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
// 			tesselator.end();
// 			poseStack.popPose();
// 		}

// 		RenderSystem.depthMask(true);
// 		RenderSystem.enableTexture();
// 		RenderSystem.disableBlend();
// 	}

// 	public void render(int ticks, float partialTicks, PoseStack matrixStack, ClientLevel world, Minecraft mc)
// 	{
// 		if (minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.END)
// 		{
// 			this.renderEndSky(matrixStack);
// 		} else if (minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL)
// 		{
// 			RenderSystem.disableTexture();
// 			Vec3 vec3 = this.level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), partialTicks);
// 			float f = (float) vec3.x;
// 			float f1 = (float) vec3.y;
// 			float f2 = (float) vec3.z;
// 			FogRenderer.levelFogColor();
// 			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
// 			RenderSystem.depthMask(false);
// 			RenderSystem.setShaderColor(f, f1, f2, 1.0F);

// 			ShaderInstance shaderinstance = RenderSystem.getShader();
// 			Matrix4f mat4f = minecraft.gameRenderer.getProjectionMatrix(partialTicks);
// 			this.skyBuffer.drawWithShader(matrixStack.last().pose(), mat4f, shaderinstance);
// 			RenderSystem.enableBlend();
// 			RenderSystem.defaultBlendFunc();
// 			float[] afloat = this.level.effects().getSunriseColor(this.level.getTimeOfDay(partialTicks), partialTicks);
// 			if (afloat != null)
// 			{
// 				RenderSystem.setShader(GameRenderer::getPositionColorShader);
// 				RenderSystem.disableTexture();
// 				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
// 				matrixStack.pushPose();
// 				matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
// 				float f3 = Mth.sin(this.level.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
// 				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3));
// 				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
// 				float f4 = afloat[0];
// 				float f5 = afloat[1];
// 				float f6 = afloat[2];

// 				Matrix4f matrix4f = matrixStack.last().pose();
// 				bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
// 				bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
// 				for (int j = 0; j <= 16; ++j)
// 				{
// 					float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
// 					float f8 = Mth.sin(f7);
// 					float f9 = Mth.cos(f7);
// 					bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
// 				}
// 				bufferbuilder.end();
// 				BufferUploader.end(bufferbuilder);
// 				matrixStack.popPose();
// 			}

// 			RenderSystem.enableTexture();
// 			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
// 			matrixStack.pushPose();
// 			float f11 = 1.0F - this.level.getRainLevel(partialTicks);
// 			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
// 			matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
// 			matrixStack.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(partialTicks) * 360.0F));
// 			Matrix4f matrix4f1 = matrixStack.last().pose();
// 			float f12 = 30.0F;
// 			RenderSystem.setShader(GameRenderer::getPositionTexShader);
// 			RenderSystem.setShaderTexture(0, SUN_LOCATION);
// 			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
// 			bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
// 			bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
// 			bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
// 			bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
// 			bufferbuilder.end();
// 			BufferUploader.end(bufferbuilder);
// 			f12 = 20.0F;

// 			RenderSystem.setShaderTexture(0, BloodMoonEvent.isBloodMoonActive() ? BLOOD_MOON_LOCATION : MOON_LOCATION);

// 			int k = this.level.getMoonPhase();
// 			int l = k % 4;
// 			int i1 = k / 4 % 2;
// 			float f13 = (float) (l + 0) / 4.0F;
// 			float f14 = (float) (i1 + 0) / 2.0F;
// 			float f15 = (float) (l + 1) / 4.0F;
// 			float f16 = (float) (i1 + 1) / 2.0F;
// 			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
// 			bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
// 			bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
// 			bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
// 			bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
// 			bufferbuilder.end();
// 			BufferUploader.end(bufferbuilder);
// 			RenderSystem.disableTexture();
// 			float f10 = this.level.getStarBrightness(partialTicks) * f11;
// 			if (f10 > 0.0F && !BloodMoonEvent.isBloodMoonActive())
// 			{
// 				RenderSystem.setShaderColor(f10, f10, f10, f10);
// 				FogRenderer.setupNoFog();
// 				this.skyFormat.setupBufferState();
// 				this.starBuffer.drawWithShader(matrixStack.last().pose(), mat4f, GameRenderer.getPositionShader());
// 				this.skyFormat.clearBufferState();
// 			}
// 			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
// 			RenderSystem.disableBlend();
// 			matrixStack.popPose();

// 			RenderSystem.disableTexture();
// 			RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
// 			double d0 = minecraft.player.getEyePosition(partialTicks).y - this.level.getLevelData().getHorizonHeight(this.level);

// 			if (d0 < 0.0D)
// 			{
// 				matrixStack.pushPose();
// 				matrixStack.translate(0.0D, 12.0D, 0.0D);
// 				this.darkBuffer.drawWithShader(matrixStack.last().pose(), mat4f, shaderinstance);
// 				matrixStack.popPose();
// 			}

// 			if (this.level.effects().hasGround())
// 			{
// 				RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
// 			}
// 			else
// 			{
// 				RenderSystem.setShaderColor(f, f1, f2, 1.0F);
// 			}

// 			RenderSystem.enableTexture();
// 			RenderSystem.depthMask(true);
// 		}
// 	}

// }
