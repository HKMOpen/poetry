package nl.elastique.poetry.data.utils;

import com.j256.ormlite.dao.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoUtils
{
    static private final Logger sLogger = LoggerFactory.getLogger(DaoUtils.class);

    /**
     * Docs: http://www.sqlite.org/datatype3.html
     */
    public enum ColumnType
    {
        INTEGER, // for int and boolean
        REAL, // float,  double, etc.
        TEXT, // String, etc.
        BLOB,
        NUMERIC
    }

    private static void executeQuery(Dao<?, ?> dao, String query) throws java.sql.SQLException
    {
        sLogger.debug("query: {}", query);
        dao.executeRawNoArgs(query);
    }

    public static void addColumn(Dao<?, ?> dao, String name, ColumnType columnType) throws java.sql.SQLException
    {
        String query = String.format("ALTER TABLE %s ADD COLUMN %s %s",
            OrmliteUtils.getTableName(dao.getDataClass()),
            name,
            columnType.toString());

        executeQuery(dao, query);
    }

    public static void addColumn(Dao<?, ?> dao, String name, ColumnType columnType, String defaultValue) throws java.sql.SQLException
    {
        String query = String.format("ALTER TABLE %s ADD COLUMN %s %s DEFAULT %s",
            OrmliteUtils.getTableName(dao.getDataClass()),
            name,
            columnType.toString(),
            defaultValue);

        executeQuery(dao, query);
    }

    public static void copyColumn(Dao<?, ?> dao, String fromName, String toName) throws java.sql.SQLException
    {
        String query = String.format("UPDATE %s SET %s = %s",
            OrmliteUtils.getTableName(dao.getDataClass()),
            toName,
            fromName);

        executeQuery(dao, query);
    }

    public static void createIndex(Dao<?, ?> dao, String columnName, String indexName) throws java.sql.SQLException
    {
        String query = String.format("CREATE INDEX %s ON %s (%s)",
            indexName,
            OrmliteUtils.getTableName(dao.getDataClass()),
            columnName);

        executeQuery(dao, query);
    }

    public static void createIndex(Dao<?, ?> dao, String columnName) throws java.sql.SQLException
    {
        createIndex(dao, columnName, String.format("%s_index", columnName));
    }
}
