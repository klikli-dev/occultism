package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.client.render.entity.state.BeaverFamiliarRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BeaverFamiliarModel extends EntityModel<BeaverFamiliarRenderState> {
    private static final float PI = (float)Math.PI;
    public ModelPart body, tail, leftLeg1, head, leftArm1, tail2, rightLeg1, rightArm1, leftLeg2, mouth, leftEye, leftEar, rightEye, rightEar, nose, teeth, whiskers1, whiskers2, leftArm2, rightLeg2, rightArm2;
    public BeaverFamiliarModel(ModelPart part){super(part); body=part.getChild("body"); tail=body.getChild("tail"); leftLeg1=body.getChild("leftLeg1"); head=body.getChild("head"); leftArm1=body.getChild("leftArm1"); tail2=body.getChild("tail2"); rightLeg1=body.getChild("rightLeg1"); rightArm1=body.getChild("rightArm1"); leftLeg2=leftLeg1.getChild("leftLeg2"); mouth=head.getChild("mouth"); leftEye=head.getChild("leftEye"); leftEar=head.getChild("leftEar"); rightEye=head.getChild("rightEye"); rightEar=head.getChild("rightEar"); nose=mouth.getChild("nose"); teeth=mouth.getChild("teeth"); whiskers1=mouth.getChild("whiskers1"); whiskers2=mouth.getChild("whiskers2"); leftArm2=leftArm1.getChild("leftArm2"); rightLeg2=rightLeg1.getChild("rightLeg2"); rightArm2=rightArm1.getChild("rightArm2"); }
    public static LayerDefinition createBodyLayer(){return null;}
    @Override public void setupAnim(BeaverFamiliarRenderState s){super.setupAnim(s); leftEar.visible=s.hasEars; rightEar.visible=s.hasEars; whiskers1.visible=s.hasWhiskers; whiskers2.visible=s.hasWhiskers; tail.visible=!s.hasBigTail; tail2.visible=s.hasBigTail; rightLeg1.zRot=leftLeg1.zRot=rightArm1.zRot=leftArm1.zRot=0; body.xRot=0.09f; body.yRot=0; body.y=19.5f; leftLeg2.xRot=rightLeg2.xRot=leftArm2.xRot=rightArm2.xRot=0; head.xRot=toRads(0); head.yRot=toRads(0); rightLeg1.xRot=-0.07f + Mth.cos(s.walkAnimationPos*0.7f) * 0.8f * s.walkAnimationSpeed; leftLeg1.xRot=-0.07f + Mth.cos(s.walkAnimationPos*0.7f + PI) * 0.8f * s.walkAnimationSpeed; rightArm1.xRot=-0.07f + Mth.cos(s.walkAnimationPos*0.7f + PI) * 0.8f * s.walkAnimationSpeed; leftArm1.xRot=-0.07f + Mth.cos(s.walkAnimationPos*0.7f) * 0.8f * s.walkAnimationSpeed; tail.xRot=0.51f + Mth.cos(s.ageInTicks*0.1f)*toRads(20); tail2.xRot=tail.xRot; if(!s.isSitting && s.isInWater){rightLeg1.zRot=toRads(40); leftLeg1.zRot=-toRads(40); rightArm1.zRot=toRads(40); leftArm1.zRot=-toRads(40);} if(s.isSitting){body.xRot=toRads(-40); head.xRot=toRads(25); tail.xRot=tail2.xRot=toRads(70); leftLeg1.xRot=toRads(-20); leftLeg2.xRot=toRads(50); rightLeg1.xRot=toRads(-20); rightLeg2.xRot=toRads(50); leftArm1.xRot=toRads(10); leftArm2.xRot=toRads(40); rightArm1.xRot=toRads(10); rightArm2.xRot=toRads(40);} if(s.isPartying){body.xRot=toRads(90); body.yRot=s.ageInTicks*0.5f; body.y=12.5f; head.xRot=0; head.yRot=0; tail.xRot=tail2.xRot=Mth.cos(s.ageInTicks*0.8f)*toRads(50);} }
    private float toRads(float d){return (float)Math.toRadians(d);} }
