package lamph11.web.centrerapi.service;

import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.entity.SettingOption;
import lamph11.web.centrerapi.repository.SettingOptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {LphException.class, Exception.class})
public class SettingOptionService extends QueryService<SettingOption> {

    private final ModelMapper modelMapper;
    private final SettingOptionRepository settingOptionRepository;



}
