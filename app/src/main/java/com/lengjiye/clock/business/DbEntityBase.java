package com.lengjiye.clock.business;

import java.io.Serializable;

/**
 * Created by lz on 2015/1/22.
 *
 * 该类是所有保存Db需要继承的类，对于该xutils的DbUtils在使用的过程中要增加id
 */
public abstract class DbEntityBase  implements Serializable {

   // @NoAutoIncrement // int,long类型的id默认自增，不想使用自增时添加此注解
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}