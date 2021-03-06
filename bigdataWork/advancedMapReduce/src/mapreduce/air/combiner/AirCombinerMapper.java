package mapreduce.air.combiner;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AirCombinerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	private static final IntWritable outputVal = new IntWritable(1);
	private Text outputKey = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// value는 한 줄을 읽어온 값 즉, 한 줄 당 map 메서드 한 개가 실행된다.
		if(key.get() > 0) {
			String[] line = value.toString().split(",");
			
			String delay = line[15];
			
			if(!delay.equals("NA") && Integer.parseInt(delay) > 0) {
				outputKey.set(line[0] + "년 " + line[1] + "월");
				context.write(outputKey, outputVal);
			}
		}
	}
}
