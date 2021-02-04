package com.impact.network;

import com.google.common.io.ByteArrayDataInput;

public abstract class ImpactPacket {

  public abstract int getPacketID();

  public abstract byte[] encode();

  public abstract Object decode(ByteArrayDataInput aData);

  public abstract void process();
}
