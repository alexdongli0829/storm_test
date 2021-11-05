package com.tollgroup.dem.dm.storm.hbase.security;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import java.io.IOException;
import java.util.Map;


/**
 * This class provides util methods for storm-hbase connector communicating
 * with secured HBase.
 */
public class HBaseSecurityUtil {
    //private static final Logger LOG = LoggerFactory.getLogger(HBaseSecurityUtil.class);

    public static final String STORM_KEYTAB_FILE_KEY = "storm.keytab.file";
    public static final String STORM_USER_NAME_KEY = "storm.kerberos.principal";
 

    public static UserGroupInformation login(Map conf, Configuration hbaseConfig) throws IOException {
        //Allowing keytab based login for backward compatibility.
        UserGroupInformation ugi = null;
        UserGroupInformation.setConfiguration(hbaseConfig);
        System.out.println("Inside function login");
        //insure that if keytab is used only one login per pHrocess executed
        if(ugi == null) {
            synchronized (HBaseSecurityUtil.class) {

                    String keytab = (String) conf.get(STORM_KEYTAB_FILE_KEY);
                    if (keytab != null) {
                        hbaseConfig.set(STORM_KEYTAB_FILE_KEY, keytab);
                    }
                    System.out.println("keytab after update from config:"+hbaseConfig.get(STORM_KEYTAB_FILE_KEY));
                    String userName = (String) conf.get(STORM_USER_NAME_KEY);
                    
                    if (userName != null) {
                        hbaseConfig.set(STORM_USER_NAME_KEY, userName);
                    }
                    System.out.println("userName after upate from config:"+hbaseConfig.get(STORM_KEYTAB_FILE_KEY));
                    UserGroupInformation.loginUserFromKeytab(userName,keytab );
                    ugi = UserGroupInformation.getLoginUser();
                   
                }
            }
        
        return ugi;
        
    }
}
