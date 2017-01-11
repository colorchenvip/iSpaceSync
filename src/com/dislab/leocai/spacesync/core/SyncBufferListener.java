package com.dislab.leocai.spacesync.core;

public interface SyncBufferListener {

	void dealWithSyncBuffer(MultiClientDataBuffer buffer, boolean isSyncTime);

}
