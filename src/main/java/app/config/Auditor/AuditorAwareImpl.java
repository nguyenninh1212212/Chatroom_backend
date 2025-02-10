package app.config.Auditor;

import org.springframework.data.domain.AuditorAware;

import app.enums.AuditUserType;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
    	 AuditUserType currentUser = AuditUserType.USER; 
         
         return Optional.of(currentUser.name());
    }
}
