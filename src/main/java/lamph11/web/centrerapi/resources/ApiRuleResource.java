package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.resources.dto.api_rule.ApiRuleDTO;
import lamph11.web.centrerapi.resources.dto.api_rule.ApiRuleFilter;
import lamph11.web.centrerapi.resources.dto.api_rule.CreateApiRule;
import lamph11.web.centrerapi.resources.dto.api_rule.UpdateApiRule;
import lamph11.web.centrerapi.service.ApiRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/api-rule")
@RequiredArgsConstructor
public class ApiRuleResource {

    private final ApiRuleService apiRuleService;

    @GetMapping
    public ResponseEntity<List<ApiRuleDTO>> query(@ModelAttribute ApiRuleFilter filter) {
        return ResponseEntity.ok(
                apiRuleService.query(filter)
        );
    }

    @PostMapping
    public ResponseEntity<ApiRuleDTO> create(@Valid @RequestBody CreateApiRule request) {
        return ResponseEntity.ok(
                apiRuleService.create(request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiRuleDTO> update(@PathVariable String id, @Valid @RequestBody UpdateApiRule request
    ) throws ResourceNotFoundException {
        request.setId(id);
        return ResponseEntity.ok(
                apiRuleService.update(request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRuleDTO> delete(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(apiRuleService.delete(id));
    }
}
