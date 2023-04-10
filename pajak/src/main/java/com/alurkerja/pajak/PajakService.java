package com.alurkerja.pajak;

import com.alurkerja.common.dto.UserDto;
import com.alurkerja.common.entity.User;
import com.alurkerja.common.service.RabbitMQSenderService;
import com.alurkerja.common.service.UserService;
import com.alurkerja.constant.ApplicationEnum;
import com.alurkerja.core.entity.CrudEntity;
import com.alurkerja.core.exception.AlurKerjaException;
import com.alurkerja.core.interfaces.AccessableInterface;
import com.alurkerja.spec.entity.BaseDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class PajakService extends BasePajakService<Pajak, PajakDto, PajakRepository> {
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitMQSenderService rabbitMQSenderService;

    protected PajakService(PajakRepository simpleJpaRepository) {
        super(simpleJpaRepository);
    }

    @Override
    public boolean canAdd() {
        return ApplicationEnum.Group.valueOf(getCurrentGroup()).equals(ApplicationEnum.Group.MAKER);
    }

    @Override
    public boolean canEdit() {
        return false;
    }

    @Override
    public boolean canDelete() {
        return false;
    }

    @Override
    public boolean canView() {
        return true;
    }

    @Override
    public boolean canList() {
        return true;
    }

    @SneakyThrows
    @Override
    public Pajak createFromDto(PajakDto dto) {
        AccessableInterface accessable = this;
        if (!accessable.canAdd()) {
            throw new AlurKerjaException(400, "Can't add new data");
        }

        if (!ObjectUtils.isEmpty(dto.getUser()) && !ObjectUtils.isEmpty(dto.getUser().getEmail())) {
            User user = userService.findByEmail(dto.getUser().getEmail());
            if (user != null) {
                dto.setUser(new UserDto().toDto(user));
            } else {
                dto.setUser(null);
            }
        }

        dto.setCreatedDate(new Date());
        dto.setCreatedBy(this.getCurrentUser());

        rabbitMQSenderService.send(dto);

        return dto.fromDto();
    }
}
