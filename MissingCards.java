import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.util.ArrayList;
public class MissingCards
{
    /*
    *Class extending Mapper Class
    */
    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>
    {
        /*
        *Mapper Function
        */
        public void map(LongWritable lw, Text t, Context c) throws IOException, InterruptedException
        {
     	    String s = t.toString();
          String[] split = s.split(" ");
          Text text = new Text(split[0]);
          IntWritable in = new IntWritable(Integer.parseInt(split[1]));
          c.write(text,in);
        }
    }
    /*
    *Class extending Reducer Class
    */
     public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>
    {   
        /*
        *Reducer Function
        */
        public void reduce(Text t, Iterable<IntWritable> iw, Context c)throws IOException, InterruptedException
        {
   	     int i = 1,cardPresent = 0;
         ArrayList<Integer> suite = new ArrayList<Integer>();
         
         for(i=1;i<=13;++i)
         {
           suite.add(i);
         }
         
         for(IntWritable cards : iw)
         {
           cardPresent = cards.get();
           if(suite.contains(cardPresent))
           {
             suite.remove(suite.indexOf(cardPresent));
           }
         }
         for(i=0;i<suite.size();++i)
         {
           c.write(t, new IntWritable(suite.get(i)));
         }
        }  
    }
    /*
    *Main Function which is also a driver function for Map Reduce Program
    */	
    public static void main(String[] args)throws Exception
    {
    	Configuration config = new Configuration();
        Job job = new Job(config, "MissingCards");
        
        job.setJarByClass(MissingCards.class);
        
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
