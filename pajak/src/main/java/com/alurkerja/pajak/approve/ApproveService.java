package com.alurkerja.pajak.approve;

import com.alurkerja.constant.ApplicationEnum;
import com.alurkerja.pajak.BasePajakService;
import com.alurkerja.pajak.Pajak;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApproveService extends BasePajakService<Pajak, ApproveDto, ApproveRepository> {
    protected ApproveService(ApproveRepository simpleJpaRepository) {
        super(simpleJpaRepository);
    }

    @Override
    public boolean canAdd() {
        return false;
    }

    @Override
    public boolean canEdit() {
        return ApplicationEnum.Group.valueOf(getCurrentGroup()).equals(ApplicationEnum.Group.APPROVER);
    }

    @Override
    public boolean canDelete() {
        return false;
    }

    @Override
    public boolean canView() {
        return canAdd();
    }

    @Override
    public boolean canList() {
        return canAdd();
    }

    @Override
    public Page<Pajak> findAll(ApproveDto keyword, Pageable pageable) {
        keyword.setStatus(ApplicationEnum.Status.CHECKED.key);
        return super.findAll(keyword, pageable);
    }

    @Override
    public Pajak update(Pajak entity, ApproveDto dto) {
        dto.setNoResi(null);
        dto.setStatus(ApplicationEnum.Status.APPROVED.key);
        return super.update(entity, dto);
    }
}
