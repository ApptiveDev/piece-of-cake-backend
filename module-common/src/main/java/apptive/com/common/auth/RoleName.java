package apptive.com.common.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleName {

    ROLE_STORE("STORE"), ROLE_USER("USER");

    private final String role;

    /**
     * String to Enum 조회
     * @param role Enum Name
     * @return Enum Data
     */
    public static RoleName fromRole(String role) {
        for(RoleName roleName : RoleName.values()) {
            if(roleName.getRole().equalsIgnoreCase(role)) {
                return roleName;
            }
        }

        throw new IllegalArgumentException("No Role Found!");
    }
}
