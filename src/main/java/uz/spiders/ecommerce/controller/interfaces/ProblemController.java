package uz.spiders.ecommerce.controller.interfaces;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.utils.Constants;

import java.util.Map;
import java.util.UUID;

//@RequestMapping(ProblemController.BASE_PATH)
public interface ProblemController {
    /*String BASE_PATH = Constants.BASE_PATH + "/problem";
    @PostMapping
    @PreAuthorize(value = "hasAuthority('PROBLEM_CREATE')")
    ApiResult<?> createNewProblem(@RequestBody ProblemDTO problemDTO);

    @GetMapping("/all")
    ApiResult<?> getAllProblems(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort);

    @GetMapping("/{id}")
    ApiResult<?> getProblemById(@PathVariable UUID id);

    @GetMapping("/admin/all")
    @PreAuthorize("hasAnyAuthority('ADMIN_PROBLEM_LIST')")
    ApiResult<?> getAllProblemsForAdmin(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort);

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('PROBLEM_EDIT')")
    ApiResult<?> editProblem(@PathVariable UUID id, @RequestBody ProblemDTO problemDTO);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('PROBLEM_DELETE')")
    ApiResult<?> deleteProblem(@PathVariable UUID id);

    @GetMapping("/problem-level")
    ApiResult<Map<Integer, String>> getAllProblemLevel();*/

}
