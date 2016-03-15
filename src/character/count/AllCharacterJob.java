package character.count;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.util.MongoConfigUtil;

public class AllCharacterJob {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String inputMongoURI = "mongodb://127.0.0.1:27017/allusion.inverted_index";
		String outputMongoURI = "mongodb://127.0.0.1:27017/allusion.all_character";
		Configuration conf = new Configuration();
		MongoConfigUtil.setInputURI(conf, inputMongoURI);
		MongoConfigUtil.setOutputURI(conf, outputMongoURI);

		Job job = Job.getInstance(conf, "AllCharacter");

		job.setJarByClass(character.count.CharacterJob.class);
		job.setMapperClass(character.count.AllCharacterMapper.class);
		job.setReducerClass(character.count.AllCharacterReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setInputFormatClass(MongoInputFormat.class);
		job.setOutputFormatClass(MongoOutputFormat.class);

		if (job.waitForCompletion(true)) {
			long end = System.currentTimeMillis();
			System.out.println("所用时间:" + (end - start));
			System.exit(0);
		} else {
			System.exit(1);
		}
	}

}
