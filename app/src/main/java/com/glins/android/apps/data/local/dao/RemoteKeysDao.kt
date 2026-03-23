package com.glins.android.apps.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.glins.android.apps.data.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun getRemoteKeysRepoId(repoId: Long): RemoteKeys?

    @Upsert
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}