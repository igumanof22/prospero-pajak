package com.alurkerja.pajak.check;

import com.alurkerja.constant.ApplicationEnum;
import com.alurkerja.pajak.BasePajakService;
import com.alurkerja.pajak.Pajak;
import com.alurkerja.pajak.approve.ApproveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CheckService extends BasePajakService<Pajak, CheckDto, CheckRepository> {
    protected CheckService(CheckRepository simpleJpaRepository) {
        super(simpleJpaRepository);
    }

    @Override
    public boolean canAdd() {
        return false;
    }

    @Override
    public boolean canEdit() {
        return ApplicationEnum.Group.valueOf(getCurrentGroup()).equals(ApplicationEnum.Group.CHECKER);
    }

    @Override
    public boolean canDelete() {
        return false;
    }

    @Override
    public boolean canView() {
        return canEdit();
    }

    @Override
    public boolean canList() {
        return canEdit();
    }

    @Override
    public Page<Pajak> findAll(CheckDto keyword, Pageable pageable) {
        keyword.setStatus(ApplicationEnum.Status.CREATED.key);
        return super.findAll(keyword, pageable);
    }

    @Override
    public Pajak update(Pajak entity, CheckDto dto) {
        dto.setNoResi(null);
        dto.setStatus(ApplicationEnum.Status.CHECKED.key);
        return super.update(entity, dto);
    }
}
