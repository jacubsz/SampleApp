package com.github.jacubsz.sampleapp.persistence.database.migration

import androidx.core.database.getIntOrNull
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jacubsz.sampleapp.persistence.database.ToDoItemsDatabase
import com.github.jacubsz.sampleapp.persistence.database.migration.Migration.Companion.MIGRATION_1_2
import com.github.jacubsz.sampleapp.persistence.model.ToDoItem
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    companion object {
        private const val TEST_DB = "migration-test"

        private val ITEM_1 = ToDoItem(1, "Test item 1", true)
        private val ITEM_2 = ToDoItem(2, "Test item 2", false)
    }

    @Rule
    @JvmField
    val migrationHelper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ToDoItemsDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        migrationHelper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                "INSERT into todoitem " +
                        "VALUES (${ITEM_1.id}, '${ITEM_1.content}', ${ITEM_1.checked?.toInt()})," +
                        "(${ITEM_2.id}, '${ITEM_2.content}', ${ITEM_2.checked?.toInt()})"
            )
            close()
        }

        val migratedDatabase = migrationHelper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        val itemsQuery = migratedDatabase.query("SELECT * FROM todoitem")
        assertEquals(2, itemsQuery.count)

        val itemIterator = listOf(ITEM_1, ITEM_2).iterator()
        while (itemsQuery.moveToNext()) {
            val itemToCompare = itemIterator.next()
            assertEquals(itemToCompare.id, itemsQuery.getInt(0))
            assertEquals(itemToCompare.content, itemsQuery.getString(1))
            assertEquals(itemToCompare.checked?.toInt(), itemsQuery.getIntOrNull(2))
        }
    }

    private fun Boolean.toInt() = if (this) 1 else 0
    
}