package com.datalife.services;

import com.datalife.entities.LoginUser;
import com.datalife.entities.User;
import com.datalife.repositories.LoginUserRepository;
import com.datalife.servicelayers.CommonService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by DATASCRIBE on 3/25/2015.
 */
@Service
public final class UtilityServices {
    /**
     * Reference to messageSource
     */
    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    LoginUserRepository loginUserRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CommonService commonService;

    protected static final Logger logger = Logger.getLogger(UtilityServices.class);

    public String getMessage( String key )
    {
        return getMessage( key, null, getLocale() );
    }

    /**
     * @param key
     * @param locale
     * @return
     */
    public String getMessage( String key, Locale locale )
    {
        return getMessage( key, null, locale );
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public String getMessage( String key, Object[] args )
    {
        return getMessage( key, null, getLocale() );
    }

    /**
     * @param key
     * @param args
     * @param locale
     * @return
     */
    public String getMessage( String key, Object[] args, Locale locale )
    {
        if ( StringUtils.hasLength(key) )
        {
            return getMessageForKey( key, args, locale );
        }
        return "";
    }

    /**
     * This method returns the locale. First it tries to fetch the current
     * HttpServletRequest and if there is a locale on that request, this method
     * returns it else returns the default locale (Locale.getDefault())
     *
     * @return Locale
     */
    public Locale getLocale()
    {
        HttpServletRequest request = getRequest();
        Locale locale = request != null ? request.getLocale() : Locale.getDefault();
        return locale == null ? Locale.getDefault() : locale;
    }

    /**
     * @return
     */
    public HttpServletRequest getRequest()
    {
        ServletRequestAttributes sra = ( (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes() );
        return sra.getRequest();
    }

    /**
     * @param key
     * @param args
     * @param locale
     * @return
     */
    private String getMessageForKey( String key, Object[] args, Locale locale )
    {
        // Determine the locale if it is not specified
        Locale loc = locale;
        if ( loc == null )
        {
            HttpServletRequest req = getRequest();
            loc = req != null ? req.getLocale() : Locale.getDefault();
        }
        String message = key;
        try
        {
            message = messageSource.getMessage( key, args, loc );
        }
        catch ( NoSuchMessageException e )
        {
            message = key;
        }
        return message;
    }

    public static String encodeData(String data){
        byte[]   bytesEncoded = Base64.encodeBase64(data.getBytes());
        return new String(bytesEncoded);
    }

    public static String decodeData(byte[] data) {
        byte[] valueDecoded = Base64.decodeBase64(data);
        return new String(valueDecoded);
    }

    public String initialAuthentication(User user){
        logger.info("In path : initialAuthentication");

            Calendar c = Calendar.getInstance();
            LoginUser loginUser = new LoginUser();
            loginUser.setUserId(user.getUserId());
            String token = UUID.randomUUID().toString().toUpperCase()+  user.getUserId()+""+c.getTimeInMillis();
            loginUser.setLoginToken(token);
            loginUser.setUserName(user.getUserName());
            loginUser.setRole(user.getRole());
            loginUserRepository.save(loginUser);

        logger.info("save login user with token : "+token);

        userDetailsManager.createUser(new DefaultUserDetails(loginUser.getLoginToken()));
        logger.info("createUser in spring security userDetailsManager : "+loginUser.getLoginToken());

        String decToken = UtilityServices.encodeData(loginUser.getLoginToken());
        String contextUrl =  this.messageSource.getMessage("b2bservices.accept.url",null,"Default b2bservices accept url",null);

        logger.info("redirect to path url : "+contextUrl+"/"+decToken);

        return contextUrl+"/"+decToken;
    }
}
