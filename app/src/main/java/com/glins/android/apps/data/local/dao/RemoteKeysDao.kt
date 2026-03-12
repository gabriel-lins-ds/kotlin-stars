package com.glins.android.apps.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glins.android.apps.data.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Long): RemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}