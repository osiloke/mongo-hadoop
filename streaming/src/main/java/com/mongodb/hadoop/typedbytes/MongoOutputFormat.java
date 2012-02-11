package com.mongodb.hadoop.typedbytes;

import org.apache.hadoop.fs.FileSystem;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCommitter;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.TaskAttemptContext;

import org.apache.hadoop.util.Progressable;

import com.mongodb.hadoop.mapred.output.MongoOutputCommiter;
import com.mongodb.hadoop.typedbytes.MongoRecordWriter;
import com.mongodb.hadoop.util.MongoConfigUtil;

public class MongoOutputFormat<K, V> implements OutputFormat<K, V> {

	public MongoOutputFormat() { }

    public void checkOutputSpecs(FileSystem ignored, JobConf job) { }

    public OutputCommitter getOutputCommitter(TaskAttemptContext context) {
        return new MongoOutputCommiter();
    }

    public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress) {
        return (RecordWriter<K, V>) new MongoRecordWriter(MongoConfigUtil.getOutputCollection(job), job);
    }

}
