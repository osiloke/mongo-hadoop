

package com.mongodb.hadoop.typedbytes;

import java.io.IOException;

import com.mongodb.hadoop.mapred.input.TypedBytesMongoRecordReader;
import org.apache.commons.logging.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.typedbytes.TypedBytesWritable;

import com.mongodb.hadoop.MongoConfig;
import com.mongodb.hadoop.mapred.input.MongoInputSplit;

public class MongoInputFormat implements InputFormat<TypedBytesWritable, TypedBytesWritable> {

	@Override
	public RecordReader<TypedBytesWritable, TypedBytesWritable> getRecordReader(
			InputSplit split, JobConf job, Reporter reporter) throws IOException {

		if (!(split instanceof MongoInputSplit))
            throw new IllegalStateException("Creation of a new RecordReader requires a MongoInputSplit instance.");

        final MongoInputSplit mis = (MongoInputSplit) split;
        
        return (RecordReader<TypedBytesWritable, TypedBytesWritable>) new TypedBytesMongoRecordReader(mis);
		//return null;
	}

	@Override
	public InputSplit[] getSplits(JobConf job, int numSplits) throws IOException {

		final MongoConfig conf = new MongoConfig(job);

		if (conf.getLimit() > 0 || conf.getSkip() > 0)
        /**
         * TODO - If they specify skip or limit we create only one input
         * split
         */
            throw new IllegalArgumentException("skip() and limit() is not currently supported do to input split "
                                               + "issues.");
        else {
            /**
             * On the jobclient side we want *ONLY* the min and max ids for each
             * split; Actual querying will be done on the individual mappers.
             */
            /*final int splitSize = conf.getSplitSize();*/
            // For first release, no splits, no sharding
            InputSplit[] splits =
                    {(InputSplit) new MongoInputSplit(conf.getInputURI(), conf.getQuery(), conf.getFields(),
                                                      conf.getSort(), conf.getLimit(), conf.getSkip())};
            log.info("HADOOP BROKE:Calculated " + splits.length + " split objects.");
            return splits;
        }
	}
	
	public boolean verifyConfiguration(Configuration conf) {
        return true;
    }

    private static final Log log = LogFactory.getLog(MongoInputFormat.class);
}
