package uz.spiders.ecommerce.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    /* category */
    CATEGORY_CREATE,
    CATEGORY_LIST,
    CATEGORY_ONE,
    CATEGORY_EDIT,
    CATEGORY_DELETE,

    /* problem */
    PROBLEM_CREATE,
    //for fetch all problems
    PROBLEM_LIST,
    PROBLEM_ONE,
    PROBLEM_EDIT,
    PROBLEM_DELETE,

    /* role */
    ROLE_CREATE,
    ROLE_EDIT,
    ROLE_ONE,
    ROLE_LIST,
    ROLE_DELETE,

    /* user */
    USER_LIST,
    USER_ONE,
    USER_DELETE,
    USER_BLOCK,
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
