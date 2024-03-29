package mytoll.test;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class StormHelloWorld{
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("word", new TestWordSpout(), 1);
        builder.setBolt("first", new FirstBolt(), 1).shuffleGrouping("word");
        builder.setBolt("print", new HbasePrintBolt(), 1).shuffleGrouping("first");

        Config conf = new Config();
        conf.setDebug(true);

        if (args != null && args.length > 0) {
          conf.setNumWorkers(3);

          StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {

          LocalCluster cluster = new LocalCluster();
          cluster.submitTopology("mytoll test", conf, builder.createTopology());
          Utils.sleep(20000);
          cluster.killTopology("mytoll test");
          cluster.shutdown();
        }
      }
}
