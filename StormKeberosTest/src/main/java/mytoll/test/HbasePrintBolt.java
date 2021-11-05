package mytoll.test;

import java.util.Map;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbasePrintBolt extends BaseRichBolt {

	    private Connection connection;
	    private Table  testTable;

        private static Logger LOG = LoggerFactory.getLogger(PrintBolt.class);
        OutputCollector _collector;

        public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
			Configuration config = HBaseConfiguration.create();
			//config.addResource("/etc/hbase/conf/hbase-site.xml");
			config.addResource("hbase-site.xml");
			//config.set("hbase.zookeeper.quorum", "ip-172-31-17-43.ap-southeast-2.compute.internal");
			//config.set("hbase.zookeeper.property.clientPort", "2181");
			//config.set("hbase.security.authentication", "kerberos");
			//config.set("hbase.rpc.protection ", "privacy");
			config.set("hbase.client.retries.number", "1");
			config.set("hadoop.security.authentication", "kerberos");
			//config.set("hbase.master.kerberos.principal", "hbase/_HOST@test.com");
			//config.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@test.com");


			System.setProperty("java.security.krb5.conf","/etc/krb5.conf");
			//System.setProperty("java.security.krb5.conf","krb5.conf");
			System.setProperty("javax.security.auth.useSubjectCredsOnly","false");
			System.setProperty("sun.security.krb5.debug","true");

			
			try {
				UserGroupInformation.setConfiguration(config);
				UserGroupInformation.loginUserFromKeytab("storm@test.com","/etc/storm.keytab");

				connection = ConnectionFactory.createConnection(config);
				TableName tableName = TableName.valueOf("test");
				testTable = connection.getTable(tableName);
			} catch (Exception e){
                      LOG.error("hbase connection error", e);
            }

			_collector = collector;
        }

        public void execute(Tuple tuple) {
			String res = null;
			   try {
				   res = getTest();
				} catch (Exception e) {
				      LOG.error("bolt error", e);
						_collector.reportError(e);
				}
				LOG.info(tuple.getString(0) +res);
				_collector.ack(tuple);
        }


        public void declareOutputFields(OutputFieldsDeclarer declarer) {
        }

		private String getTest() throws IOException {

//			     try{
                        final String rowKey = "dong";
                        final String columnFamilyName = "info";
                        final String columnName = "age";
                        final Get get = new Get(Bytes.toBytes(rowKey.trim()));
                        get.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes(columnName));
                        final Result result = testTable.get(get);
                        final String res = new String(result.value());
                        System.out.println(res);
						return res;
//                } catch (final Exception e) {
 //                       System.out.println("error:"+e);
						//return null;
//						return e.getMessage();

 //               }
}	
}
