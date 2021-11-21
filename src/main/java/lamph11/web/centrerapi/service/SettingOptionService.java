package lamph11.web.centrerapi.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.utils.UUIDUtils;
import lamph11.web.centrerapi.entity.Setting;
import lamph11.web.centrerapi.entity.SettingOption;
import lamph11.web.centrerapi.entity.SettingOption_;
import lamph11.web.centrerapi.entity.Setting_;
import lamph11.web.centrerapi.repository.SettingOptionRepository;
import lamph11.web.centrerapi.repository.SettingRepository;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionDTO;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.JoinType;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {LphException.class, Exception.class})
public class SettingOptionService extends QueryService<SettingOption> {

    private final ModelMapper modelMapper;
    private final SettingRepository settingRepository;
    private final SettingOptionRepository settingOptionRepository;

    Function<Object, SettingOption> toSettingOption;
    Function<Object, SettingOptionDTO> toSettingOptionDTO;

    @PostConstruct
    public void init() {
        toSettingOption = obj -> modelMapper.map(obj, SettingOption.class);
        toSettingOptionDTO = obj -> modelMapper.map(obj, SettingOptionDTO.class);
    }

    /**
     * get page setting option
     *
     * @param filter query info
     * @return page setting option
     */
    @Transactional(readOnly = true)
    public Page<SettingOptionDTO> getPageBySetting(SettingOptionFilter filter) {
        Page<SettingOption> settingOptions = settingOptionRepository.findAll(
                buildSpecification(filter),
                filter.getPageable(),
                EntityGraphUtils.fromAttributePaths(SettingOption_.SETTING)
        );
        return settingOptions.map(toSettingOptionDTO);
    }


    /**
     * create new setting option
     *
     * @param dto setting option info
     * @return
     * @throws ResourceNotFoundException
     */
    public SettingOptionDTO create(SettingOptionDTO dto) throws ResourceNotFoundException {
        Optional<Setting> optionalSetting = settingRepository.findById(dto.getSetting().getCode());
        if (!optionalSetting.isPresent())
            throw new ResourceNotFoundException(Setting.class, dto.getSetting().getCode());

        Setting setting = optionalSetting.get();
        SettingOption settingOption = toSettingOption.apply(dto);
        settingOption.setId(UUIDUtils.generateId());
        settingOption.setSetting(setting);
        settingOptionRepository.save(settingOption);
        return toSettingOptionDTO.apply(settingOption);
    }

    /**
     * update setting option
     *
     * @param dto setting option info
     * @return
     * @throws ResourceNotFoundException
     */
    public SettingOptionDTO update(SettingOptionDTO dto) throws ResourceNotFoundException {
        Optional<SettingOption> optional = settingOptionRepository.findById(dto.getId());
        if (!optional.isPresent())
            throw new ResourceNotFoundException(SettingOption.class, dto.getId());

        Optional<Setting> optionalSetting = settingRepository.findById(dto.getSetting().getCode());
        if (!optionalSetting.isPresent())
            throw new ResourceNotFoundException(Setting.class, dto.getSetting().getCode());

        SettingOption settingOption = optional.get();
        modelMapper.map(dto, settingOption);
        settingOption.setSetting(optionalSetting.get());
        settingOptionRepository.save(settingOption);
        return toSettingOptionDTO.apply(settingOption);
    }

    /**
     * build setting option specification
     *
     * @param filter query info
     * @return specification
     */
    public Specification buildSpecification(SettingOptionFilter filter) {
        Specification specification = Specification.where((root, query, builder) -> {
//            if (filter.getSetting() != null) {
//                root.join(SettingOption_.SETTING, JoinType.INNER);
//            }
            return null;
        });

        if (filter == null)
            return specification;

        if (!StringUtils.isEmpty(filter.getKeyword()))
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.like(
                            builder.lower(root.get(SettingOption_.name)),
                            "%" + StringUtils.lowerCase(filter.getKeyword()).trim() + "%"
                    ),
                    builder.like(
                            builder.lower(root.get(SettingOption_.value)),
                            "%" + StringUtils.lowerCase(filter.getKeyword()).trim() + "%"
                    ),
                    builder.like(
                            builder.lower(root.get(SettingOption_.metadata)),
                            "%" + StringUtils.lowerCase(filter.getKeyword()).trim() + "%"
                    )
            ));

        if (!StringUtils.isEmpty(filter.getSetting()))
            specification = specification.and((root, query, builder) -> builder.equal(
                    root.get(SettingOption_.setting).get(Setting_.code),
                    StringUtils.upperCase(filter.getSetting()).trim()
            ));

        return specification;
    }

}
