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
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingResource {

    private final SettingService settingService;
    private final SettingOptionService settingOptionService;

    @GetMapping
    public ResponseEntity querySetting(@ModelAttribute SettingFilter filter, HttpServletRequest request) {
        return ResponseEntity.ok(PageResponse.from(settingService.querySetting(filter)));
    }

    @PostMapping
    public ResponseEntity createSetting(@Validated(Default.class) @RequestBody SettingDTO dto) throws LphException {
        return ResponseEntity.ok(settingService.create(dto));
    }

    @GetMapping("/{settingCode}")
    public ResponseEntity infoSetting(@PathVariable("settingCode") String code) throws ResourceNotFoundException {
        return ResponseEntity.ok(settingService.infoSetting(code));
    }

    @GetMapping("/{settingCode}/options")
    public ResponseEntity getOptions(
            @PathVariable("settingCode") String settingCode, @ModelAttribute SettingOptionFilter filter
    ) {
        filter.setSetting(settingCode);
        return ResponseEntity.ok(
                PageResponse.from(settingOptionService.getPageBySetting(filter))
        );
    }

    @PostMapping("/{settingCode}/options")
    public ResponseEntity<SettingOptionDTO> createOption(
            @PathVariable("settingCode") String settingCode, @Validated @RequestBody SettingOptionDTO settingOption
    ) throws LphException {
        SettingDTO setting = new SettingDTO();
        setting.setCode(settingCode);
        settingOption.setSetting(setting);
        return ResponseEntity.ok(
                settingOptionService.create(settingOption)
        );
    }

    @PostMapping("/{settingCode}/options/{optionId}")
    public ResponseEntity<SettingOptionDTO> updateOption(
            @PathVariable("settingCode") String settingCode,
            @PathVariable("optionId") String optionId,
            @Validated @ModelAttribute SettingOptionDTO settingOption
    ) throws LphException {
        SettingDTO setting = new SettingDTO();
        setting.setCode(settingCode);
        settingOption.setId(optionId);
        settingOption.setSetting(setting);
        return ResponseEntity.ok(
                settingOptionService.update(settingOption)
        );
    }


}
