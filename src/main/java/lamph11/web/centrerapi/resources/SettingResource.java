package lamph11.web.centrerapi.resources;

import io.github.jhipster.service.filter.StringFilter;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.io.PageResponse;
import lamph11.web.centrerapi.resources.dto.setting.SettingDTO;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionDTO;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionFilter;
import lamph11.web.centrerapi.service.SettingOptionService;
import lamph11.web.centrerapi.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingResource {

    private final SettingService settingService;
    private final SettingOptionService settingOptionService;

    @GetMapping
    public ResponseEntity querySetting(@ModelAttribute SettingFilter filter) {
        return ResponseEntity.ok(PageResponse.from(settingService.querySetting(filter)));
    }

    @PostMapping
    public ResponseEntity createSetting(@Validated(Default.class) @RequestBody SettingDTO dto) {
        return ResponseEntity.ok(settingService.create(dto));
    }

    @RequestMapping("/{settingId}")
    public ResponseEntity infoSetting(@PathVariable("settingId") String settingId) throws ResourceNotFoundException {
        return ResponseEntity.ok(settingService.infoSetting(settingId));
    }

    @GetMapping("/{settingId}/options")
    public ResponseEntity getOptions(
            @PathVariable("settingId") String settingId, @ModelAttribute SettingOptionFilter filter
    ) {
        if (filter.getSetting() == null)
            filter.setSetting(new SettingFilter());

        StringFilter settingIdFilter = new StringFilter();
        settingIdFilter.setEquals(settingId);
        filter.getSetting().setId(settingIdFilter);
        return ResponseEntity.ok(
                PageResponse.from(settingOptionService.getPageBySetting(filter))
        );
    }

    @PostMapping("/{settingId}/options")
    public ResponseEntity<SettingOptionDTO> createOption(
            @PathVariable("settingId") String settingId, @Validated @RequestBody SettingOptionDTO settingOption
    ) throws LphException {
        SettingDTO setting = new SettingDTO();
        setting.setId(settingId);
        settingOption.setSetting(setting);
        return ResponseEntity.ok(
                settingOptionService.create(settingOption)
        );
    }

    @PostMapping("/{settingId}/options/{optionId}")
    public ResponseEntity<SettingOptionDTO> updateOption(
            @PathVariable("settingId") String settingId,
            @PathVariable("optionId") String optionId,
            @Validated @ModelAttribute SettingOptionDTO settingOption
    ) throws LphException {
        SettingDTO setting = new SettingDTO();
        setting.setId(settingId);
        settingOption.setId(optionId);
        settingOption.setSetting(setting);
        return ResponseEntity.ok(
                settingOptionService.update(settingOption)
        );
    }


}
