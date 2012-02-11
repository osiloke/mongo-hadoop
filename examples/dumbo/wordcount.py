import dumbo

def mapper(key, value):
    #value = " ".join(value.split("\n"))
    #for word in value.split(" "):
        #yield str(word), 1
    yield(key, 1)

def reducer(key, values):
    yield key, sum(values)

if __name__ == "__main__":
    job = dumbo.Job()
    job.additer(mapper, reducer)
    job.run()
    
    '''TO RUN:
    dumbo start examples/dumbo/wordcount.py -hadoop /usr/lib/hadoop \
    -libjar core/target/mongo-hadoop-core-1.0-SNAPSHOT.jar \
    -libjar mongo-2.6.3.jar \
    -libjar streaming/target/mongo-hadoop-streaming-1.0-SNAPSHOT.jar \
    -inputformat com.mongodb.hadoop.typedbytes.MongoInputFormat -input mongodb://127.0.0.1/schuuled.courses \
    -outputFormat com.mongodb.hadoop.mapred.MongoOutputFormat -output test.out -conf examples/dumbo/mongo-wordcount.xml
    dumbo rm test.out -hadoop /usr/lib/hadoop;dumbo start examples/dumbo/wordcount.py -hadoop /usr/lib/hadoop \
    -inputformat com.mongodb.hadoop.typedbytes.MongoInputFormat -input mongodb://127.0.0.1/schuuled.courses -inputURI mongodb://127.0.0.1/schuuled.courses \
    -outputFormat text -output test.out
    '''