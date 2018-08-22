package com.test.oschina.mvcproject.entity.gen;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.test.oschina.mvcproject.entity.DetailPoint;

import com.test.oschina.mvcproject.entity.DeskPoint;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DESK_POINT".
*/
public class DeskPointDao extends AbstractDao<DeskPoint, Long> {

    public static final String TABLENAME = "DESK_POINT";

    /**
     * Properties of entity DeskPoint.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property DeskId = new Property(0, Long.class, "deskId", true, "_id");
        public final static Property Col = new Property(1, int.class, "col", false, "COL");
    }

    private DaoSession daoSession;


    public DeskPointDao(DaoConfig config) {
        super(config);
    }
    
    public DeskPointDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DESK_POINT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: deskId
                "\"COL\" INTEGER NOT NULL );"); // 1: col
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DESK_POINT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DeskPoint entity) {
        stmt.clearBindings();
 
        Long deskId = entity.getDeskId();
        if (deskId != null) {
            stmt.bindLong(1, deskId);
        }
        stmt.bindLong(2, entity.getCol());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DeskPoint entity) {
        stmt.clearBindings();
 
        Long deskId = entity.getDeskId();
        if (deskId != null) {
            stmt.bindLong(1, deskId);
        }
        stmt.bindLong(2, entity.getCol());
    }

    @Override
    protected final void attachEntity(DeskPoint entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DeskPoint readEntity(Cursor cursor, int offset) {
        DeskPoint entity = new DeskPoint( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // deskId
            cursor.getInt(offset + 1) // col
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DeskPoint entity, int offset) {
        entity.setDeskId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCol(cursor.getInt(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DeskPoint entity, long rowId) {
        entity.setDeskId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DeskPoint entity) {
        if(entity != null) {
            return entity.getDeskId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DeskPoint entity) {
        return entity.getDeskId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getDetailPointDao().getAllColumns());
            builder.append(" FROM DESK_POINT T");
            builder.append(" LEFT JOIN DETAIL_POINT T0 ON T.\"_id\"=T0.\"point_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected DeskPoint loadCurrentDeep(Cursor cursor, boolean lock) {
        DeskPoint entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        DetailPoint point = loadCurrentOther(daoSession.getDetailPointDao(), cursor, offset);
        entity.setPoint(point);

        return entity;    
    }

    public DeskPoint loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<DeskPoint> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<DeskPoint> list = new ArrayList<DeskPoint>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<DeskPoint> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<DeskPoint> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}