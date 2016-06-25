/**
 * Created by Woonghee on 2016-06-23.
 */
public class Word implements Comparable<Word>{
    int usernum;


    String tweet;


    Word(int usernum, String tweet){
        this.usernum = usernum;

        this.tweet = tweet;
    }

    @Override
    public int compareTo(final Word o) {
       return  this.tweet.compareTo(o.tweet);
    }
}
