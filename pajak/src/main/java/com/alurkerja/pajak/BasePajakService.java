package com.alurkerja.pajak;

import com.alurkerja.constant.CurrentUser;
import com.alurkerja.core.interfaces.AccessableInterface;
import com.alurkerja.core.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasePajakService<E, D, R> extends CrudService<E, D, R> implements AccessableInterface {
    @Autowired
    private CurrentUser currentUser;

    protected BasePajakService(R simpleJpaRepository) {
        super(simpleJpaRepository);
    }

    @Override
    public String getCurrentUser() {
        return currentUser.getUserId();
    }

    @Override
    public String getCurrentGroup() {
        return currentUser.getRole();
    }

    @Override
    public String getCurrentOrganization() {
        return "";
    }

    @Override
    public String getCurrentUserName() {
        return currentUser.getName();
    }
}
