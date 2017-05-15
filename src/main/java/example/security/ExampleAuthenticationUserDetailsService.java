package example.security;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;

/**
 * Created : vincent
 * Date : 2017/5/13 上午11:03
 * Email : wangxiao@wafersystems.com
 */
public class ExampleAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        String principal = (String) token.getPrincipal();

        if (!StringUtils.isEmpty(principal)) {
            return new ExampleUserDetails(new ExampleUser(principal));
        }
        return null;
    }
}
