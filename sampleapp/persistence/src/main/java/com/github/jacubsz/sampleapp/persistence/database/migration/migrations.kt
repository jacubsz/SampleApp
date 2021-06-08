package com.github.jacubsz.sampleapp.persistence.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `todoitem2` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `content` TEXT, `checked` INTEGER)")
        database.execSQL("INSERT INTO todoitem2 (content, checked) SELECT content, checked FROM todoitem")
        database.execSQL("DROP TABLE todoitem")
        database.execSQL("ALTER TABLE `todoitem2` RENAME TO `todoitem`")
    }
}