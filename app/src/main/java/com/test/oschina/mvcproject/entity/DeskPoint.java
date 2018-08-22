package com.test.oschina.mvcproject.entity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import com.test.oschina.mvcproject.entity.gen.DaoSession;
import com.test.oschina.mvcproject.entity.gen.DetailPointDao;
import com.test.oschina.mvcproject.entity.gen.DeskPointDao;

@Entity
public class DeskPoint {
    @Id(autoincrement = true) // id自增长
    private Long  deskId;
    private  int col;
    @ToOne(joinProperty = "deskId")
    private  DetailPoint point;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 653506056)
    private transient DeskPointDao myDao;

    @Generated(hash = 1351691137)
    public DeskPoint(Long deskId, int col) {
        this.deskId = deskId;
        this.col = col;
    }

    @Generated(hash = 32220044)
    public DeskPoint() {
    }

    public Long getDeskId() {
        return this.deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Generated(hash = 1150438727)
    private transient Long point__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 823180769)
    public DetailPoint getPoint() {
        Long __key = this.deskId;
        if (point__resolvedKey == null || !point__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DetailPointDao targetDao = daoSession.getDetailPointDao();
            DetailPoint pointNew = targetDao.load(__key);
            synchronized (this) {
                point = pointNew;
                point__resolvedKey = __key;
            }
        }
        return point;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 500158981)
    public void setPoint(DetailPoint point) {
        synchronized (this) {
            this.point = point;
            deskId = point == null ? null : point.getPointId();
            point__resolvedKey = deskId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2079178868)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDeskPointDao() : null;
    }

}
