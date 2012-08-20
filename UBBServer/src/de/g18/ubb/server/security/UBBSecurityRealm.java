package de.g18.ubb.server.security;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.server.service.UserServiceImpl;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class UBBSecurityRealm extends AuthorizingRealm {

    private UserServiceLocal userService;


    public UBBSecurityRealm() {
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken aToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) aToken;
        String email = upToken.getUsername();

        if (StringUtils.isEmpty(email)) {
            throw new AccountException("Username is required for authentification!");
        }

        User user;
        try {
            user = getUserService().loadByEMail(email);
        } catch (NotFoundExcpetion e) {
            throw new UnknownAccountException("No account found for user [" + email + "]");
        }

        byte[] salt = user.getSalt();
        String typedInPassword = new String(HashUtil.toMD5(upToken.getPassword(), salt));
        String userPassword = user.getPasswordHash();
        if (!ObjectUtil.equals(typedInPassword, userPassword)) {
            throw new AccountException("Wrong password for user " + email + "!");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(email, upToken.getPassword(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        return info;
    }

    private UserServiceLocal getUserService() {
        if (userService == null) {
            try {
                userService = (UserServiceLocal) new InitialContext().lookup("ejb:UBBServerEAR/UBBServer//" + UserServiceImpl.class.getSimpleName() + "!" + UserServiceLocal.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Service konnte nicht gefunden werden!", e);
            }
        }
        return userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }
}
