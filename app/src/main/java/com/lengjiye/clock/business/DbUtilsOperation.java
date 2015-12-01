package com.lengjiye.clock.business;

import android.content.Context;

import com.lengjiye.clock.application.MallApplication;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2015-1-27
 * 版本1：1、增加了保存User的表
 */
public class DbUtilsOperation {
    private final String DATABASE_NAME = "clock.db";
    private final int DATABASE_VERSION = 2;
    public static final String CLOCK_DB = "clock";
    private DbUtils instance;
    private Context context;

    public DbUtilsOperation() {
        this.context = MallApplication.getInstance().appContext;//从全局的applicationContext中获取context对象
        instance = getDbUtils();
    }

    public DbUtilsOperation(Context contxt) {
        this.context = contxt.getApplicationContext();
        instance = getDbUtils();
    }

    private DbUtils getDbUtils() {
        synchronized (DbUtilsOperation.class) {
            DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
            config.setDbName(DATABASE_NAME);
            config.setDbVersion(DATABASE_VERSION);
            if (instance == null) {
                instance = DbUtils.create(config);
            }
        }
        return instance;
    }

    /**
     * 向数据库的插入数据(根据传入到的selector,可以插入无重复数据(推荐使用))
     *
     * @param selector 查询的条件；
     *                 eg："Selector.from(UserDbEntity.class).where(UserDbEntity.ColumnName.USER_ID, "=", userDb.getUserId())"
     * @param entity   表中的对应的该条数据的对象
     */
    public void insert(Object entity, Selector selector) {
        try {
            if (selector != null) {
                List<?> queryList = instance.findAll(selector);
                if (queryList == null || queryList.size() <= 0) {
                    instance.saveOrUpdate(entity);
                }
            } else {
                instance.saveOrUpdate(entity);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库的插入多条数据(会插入重复数据)
     *
     * @param entities 表中的对应的该条数据的对象
     */
    public void insert(List<?> entities) {
        try {
            instance.saveOrUpdateAll(entities);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 按条件查询数据库的第一条数据(推荐使用)
     *
     * @param selector 查询的条件
     * @return T 返回数据库的第一条数据
     */

    public <T> T queryFirst(Selector selector) {

        try {
            return instance.findFirst(selector);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询数据库的第一条数据(推荐使用)
     *
     * @param entityType 单个表的一条数据封装的类名
     * @return T 返回数据库的第一条数据
     */

    public <T> T queryFirst(Class<T> entityType) {

        try {
            return instance.findFirst(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按条件查询数据库的单条数据(不是很推荐)
     *
     * @param entityType 单个表的一条数据封装的类名
     * @param idValue    表中的对应的该条数据的对象
     * @return List<T> 所有数据的List集合
     */

    public <T> T query(Class<T> entityType, Object idValue) {

        try {
            return instance.findById(entityType, idValue);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按条件查询数据库的所有数据(推荐使用)
     *
     * @param selector 查询的条件
     * @return List<T> 所有数据的List集合
     */

    public <T> List<T> query(Selector selector) {
        List<T> entities = new ArrayList<T>();
        try {
            entities = instance.findAll(selector);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return entities;
    }

    /**
     * 查询数据库的所有数据(无条件查询 推荐使用)
     *
     * @param entityType 单个表的一条数据封装的类名
     * @return List<T> 所有数据的List集合
     */
    public <T> List<T> query(Class<T> entityType) {
        List<T> entities = new ArrayList<T>();
        try {
            entities = instance.findAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return entities;
    }

    /**
     * 更新数据库的某条数据(不是很推荐)
     *
     * @param entity            表中的对应的该条数据的对象
     * @param updateColumnNames 所更新的数据的列名
     */
    public void update(Object entity, String... updateColumnNames) {
        try {
            instance.update(entity, updateColumnNames);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 按条件去更新数据库的某条数据(推荐使用)
     *
     * @param entity            表中的对应的该条数据的对象
     * @param whereBuilder      更新的条件
     * @param updateColumnNames 所更新的数据的列名,该列名要和entity中更新的所在列名一致
     */
    public void update(Object entity, WhereBuilder whereBuilder, String... updateColumnNames) {
        try {
            instance.update(entity, whereBuilder, updateColumnNames);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新数据库的多条数据(不是很推荐)
     *
     * @param entities          表中的对应的多条数据的对象
     * @param updateColumnNames 所更新的数据的列名
     */
    public void update(List<?> entities, String... updateColumnNames) {
        try {
            instance.updateAll(entities, updateColumnNames);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按条件更新数据库的多条数据(推荐使用)
     *
     * @param entities          表中的对应的多条数据的对象
     * @param whereBuilder      更新的条件
     * @param updateColumnNames 所更新的数据的列名
     */
    public void update(List<?> entities, WhereBuilder whereBuilder, String... updateColumnNames) {
        try {
            instance.updateAll(entities, whereBuilder, updateColumnNames);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除该表中的某条数据(不是很推荐)
     *
     * @param entity 表中的对应的该条数据的对象
     */
    public void delete(Object entity) {
        try {
            instance.delete(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除该表中的多条数据(不是很推荐)
     *
     * @param entities 表中的对应的所有数据的对象
     */
    public void delete(List<?> entities) {
        try {
            instance.deleteAll(entities);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除该表中的某条数据(不是很推荐)
     *
     * @param entityType 单个表的一条数据封装的类名
     * @param idValue    表中的对应的该条数据的对象
     */
    public void delete(Class<?> entityType, Object idValue) {
        try {
            instance.deleteById(entityType, idValue);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除该表中所有的数据(推荐使用)
     *
     * @param entityType 单个表的一条数据封装的类名
     */
    public void delete(Class<?> entityType) {
        try {
            instance.deleteAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按条件删除该表中的数据(推荐使用)
     *
     * @param entityType   单个表的一条数据封装的类名
     * @param whereBuilder 删除的条件
     *                     eg: WhereBuilder builder =  WhereBuilder.b();
     *                     builder.and(UserDbEntity.ColumnName.USER_ID,"=",1);
     */
    public void delete(Class<?> entityType, WhereBuilder whereBuilder) {
        try {
            instance.delete(entityType, whereBuilder);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


}
