package com.glins.android.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.glins.android.database.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun getRemoteKeysRepoId(repoId: Long): RemoteKeys?

    @Upsert
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}