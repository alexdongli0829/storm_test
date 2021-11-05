package com.tollgroup.dem.dm.storm.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public final class HbaseConnector {

    /** The instance. */
    private static HbaseConnector instance = new HbaseConnector();
    
    /**
     * Gets the single instance of HbaseConnector.
     *
     * @return single instance of HbaseConnector
     */
    public static HbaseConnector getInstance() {
        return instance;
    }

    /** The configuration. */
    private final Configuration configuration;

    /**
     * Instantiates a new hbase connector.
     */
    private HbaseConnector() {
   		configuration = HBaseConfiguration.create();
//   		try {
//   		        configuration.set("zookeeper.znode.parent", "/hbase-secure");
   	            configuration.set("hbase.zookeeper.quorum", "ip-10-99-72-251.toll.com.au");
   	            configuration.set("hbase.zookeeper.property.clientPort", "2181");
   	     	    configuration.set("hadoop.security.authentication", "kerberos");
    	     	configuration.set("hbase.security.authentication", "kerberos");
    	     	configuration.set("hbase.cluster.distributed", "true");
   	            configuration.addResource("src/main/resources/hbase-site.xml");
   	            configuration.addResource("src/main/resources/core-site.xml");
   	            configuration.addResource("src/main/resources/hdfs-site.xml");
   	            configuration.set("hbase.rpc.protection", "privacy");
   	            configuration.set("HADOOP_USER_NAME","hbase");
   	            configuration.set("hadoop-user-kerberos","hbase");
   	            //System.setProperty("java.security.krb5.conf","/opt/toll/dem/storm/apache-storm-1.1.0/hbase/krb5.conf");
     	        configuration.set("hbase.master.keytab.file", "/opt/toll/dem/storm/apache-storm-1.1.0/hbase/storm.keytab");
//   	        System.setProperty("java.security.krb5.conf","src/main/resources/krb5.conf");
    	        System.setProperty("sun.security.krb5.debug", "true");
    	        System.setProperty("javax.security.auth.useSubjectCredsOnly","false");
//   	        String principal = System.getProperty("kerberosPrincipal","hbase/ip-10-99-72-251.toll.com.au@mytoll.kdc.com");
//   	        String keytabLocation = System.getProperty("kerberosKeytab","src/main/resources/hbase.keytab");
//   	        
//   	        UserGroupInformation.setConfiguration(configuration);
//   	        UserGroupInformation.loginUserFromKeytab(principal, keytabLocation);
//    	}
//        catch (Exception e){
//        	System.out.println("DAPHNE ... HBaseConnector configuration exception" + configuration);
//        	if (configuration != null)
//        	{
//        		System.out.println("DAPHNE ... HBase Configuration : " + configuration.toString());
//        	}
//        	e.printStackTrace();
//        }
    }

    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }
}
