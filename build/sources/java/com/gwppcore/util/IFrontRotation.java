package com.gwppcore.util;

public interface IFrontRotation {
    boolean isFrontRotationValid(byte frontRotation, byte frontFacing);

    void rotateAroundFrontPlane(boolean direction);

    void forceSetRotationDoRender(byte frontRotation);

    byte getFrontRotation();
}
