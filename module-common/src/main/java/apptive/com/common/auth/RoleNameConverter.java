package apptive.com.common.auth;

import org.springframework.core.convert.converter.Converter;

public class RoleNameConverter implements Converter<String, RoleName> {

    @Override
    public RoleName convert(String roleName) {
        return RoleName.fromRole(roleName);
    }
}
