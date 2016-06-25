/**
 * Created by Woonghee on 2016-06-23.
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
public class Main {
    static Scanner user;
    static Scanner friend;
    static Scanner word;
    static String whichtweet="";
    public static void main(String[] args)throws IOException{
        user=new Scanner(new File("user.txt"));
        friend=new Scanner(new File("friend.txt"));
        word=new Scanner(new File("word.txt"));
        ReadDataFiles();
        Scanner in=new Scanner(System.in);


        System.out.println("0. Read data files\n" +
                "1. display statistics\n" +
                "2. Top 5 most tweeted words\n" +
                "3. Top 5 most tweeted users\n" +
                "4. Find users who tweeted a word (e.g., ¡¯¿¬¼¼´ë¡¯)\n" +
                "5. Find all people who are friends of the above users\n" +
                "6. Delete all mentions of a word\n" +
                "7. Delete all users who mentioned a word\n" +
                "8. Find strongly connected components\n" +
                "9. Find shortest path from a given user\n" +
                "99. Quit\n" +
                "Select Menu:");

        while(true) {
            int menu=in.nextInt();
            if (menu == 0) {
                ReadData();
                System.out.println("Select Menu:");
            }
            else if (menu == 1) {
                Statistics();
                System.out.println("Select Menu:");
            }
            else if (menu == 2) {
                Top5tweet();
                System.out.println("Select Menu:");
            }
            else if(menu==3){
                Top5tweetUsers();
                System.out.println("Select Menu:");
            }
            else if (menu == 4) {
                System.out.println("insert the word:");
                whichtweet=in.next();
                FindUsersTweeted();
                System.out.println("Select Menu:");
            }
            else if(menu==99){
                break;
            }
            else{
                System.out.println("You have to input from 1 to 9 or 99.\n"+
                "You gave the wrong input.");
            }
        }
    }



    static RedBlackTree<User> userRedBlackTree=new RedBlackTree();
    static RedBlackTree<Word> wordRedBlackTree=new RedBlackTree();
    static User userVertex[]=new User[5000];
    static Word wordVertex[]=new Word[5000];
    static RedBlackTree tweetTree=new RedBlackTree();
    static int friendships=0;
    static String[] sortedTweet=new String[5000];

    public static void ReadDataFiles(){
        int vertexNum=0;

        while(user.hasNext()) {
            int line1 = user.nextInt();
            user.nextLine();
            String line2 = user.nextLine();
            String line3 = user.nextLine();
            user.nextLine();
            User moment=new User();
            moment=new User(line1, line3);
            userVertex[vertexNum]=moment;
            userVertex[vertexNum].n=vertexNum;
            userRedBlackTree.insert(moment);
            vertexNum++;

        }
        int wordVertexNum=0;
        while(word.hasNext()){
            int line1= word.nextInt();
            word.nextLine();
            String line2= word.nextLine();
            String line3= word.nextLine();
            word.nextLine();
            Word word= new Word(line1, line3);
            wordVertex[wordVertexNum++]=word;

            wordRedBlackTree.insert(word);
            tweetTree.insert(word.tweet);
        }




        String tag="!";
        int tagnum=0;
        for(int i=0;i<tweetTree.size();i++){
            if(tag.equals(wordVertex[i].tweet)){
                tagnum++;
            }
        }
        for(int i=0;i<tagnum;i++){
            sortedTweet[i]=tag;
        }
        for(int i=0;i<tweetTree.size()-tagnum;i++){
            sortedTweet[i+tagnum]=(String)(tweetTree.getGreaterThan("!", tweetTree.size()).get(i));
        }
        int joongbok=0;


        while(friend.hasNext()){
            int line1=friend.nextInt();
            int line2=friend.nextInt();
            friend.nextLine();
            if(joongbok!=line2) {
                joongbok=line2;
                friendships++;
                int start = 0;
                int reach = 0;
                for (int i = 0; i < vertexNum; i++) {
                    if (line1 == userVertex[i].usernum) {
                        start = i;
                    }
                }
                for (int j = 0; j < vertexNum; j++) {
                    if (line2 == userVertex[j].usernum) {
                        reach = j;
                    }
                }
                userVertex[start].User_add(userVertex[start], userVertex[reach]);

            }

        }



    }
        public static void ReadData(){
         System.out.println("Total users: "+userRedBlackTree.size());
         System.out.println("Total friendship records:"+friendships);
         System.out.println("Total tweets: " + wordRedBlackTree.size());

    }
    public static void Statistics(){
        int minFriends1=0;
        int minFriends2=1700;
        int maxFriends1=0;
        int maxFriends2=0;
        for(int i=0;i<userRedBlackTree.size();i++) {
            for (ADJ adj = userVertex[i].first; adj != null; adj = adj.next) {
                minFriends1++;
            }
            if(minFriends1<=minFriends2){
                minFriends2=minFriends1;
            }
            minFriends1=0;
        }

        for(int i=0;i<userRedBlackTree.size();i++) {
            for (ADJ adj = userVertex[i].first; adj != null; adj = adj.next) {
                maxFriends1++;
            }

            if(maxFriends1>=maxFriends2){
                maxFriends2=maxFriends1;
            }
            maxFriends1=0;
        }
        int mintweet1=0;
        int mintweet2=2000;
        int usernumTweet=wordVertex[0].usernum;
        for(int i=0;i<wordRedBlackTree.size();i++){
            if(usernumTweet==wordVertex[i].usernum){
                mintweet1++;
            }
            else{
                usernumTweet=wordVertex[i].usernum;
                mintweet1=1;
            }
            if(mintweet1<=mintweet2){
                mintweet2=mintweet1;
            }
        }
        int maxtweet1=0;
        int maxtweet2=0;
        for(int i=0;i<wordRedBlackTree.size();i++){
            if(usernumTweet==wordVertex[i].usernum){
                maxtweet1++;
            }
            else{
                usernumTweet=wordVertex[i].usernum;
                maxtweet1=1;
            }
            if(maxtweet1>=maxtweet2){
                maxtweet2=maxtweet1;
            }
        }

        System.out.println("Average number of friends: "+friendships/userRedBlackTree.size()+
                "\nMinimum friends: "+minFriends2 );
                System.out.println( "Maximum number of friends: "+maxFriends2+
                "\nAverage tweets per user: " +wordRedBlackTree.size()/userRedBlackTree.size()+
                "\nMinimum tweets per user: " +mintweet2+
                "\nMaximum tweets per user: "+maxtweet2);
    }
    public static void Top5tweet(){

        int maxtweet1=0;
        int maxtweet2=0;
        int maxVertex=0;
        int sizedown=0;
        String first=sortedTweet[0];
        //for(int j=0;j<5;j++) {
            for (int i = 0; i < wordRedBlackTree.size()-sizedown; i++) {
                if (first.equals(sortedTweet[i])) {
                    maxtweet1++;
                } else {
                    first = sortedTweet[i];

                    maxtweet1 = 1;
                }
                if (maxtweet1 >= maxtweet2) {
                    maxtweet2 = maxtweet1;
                    maxVertex = i;
                }
            }
            System.out.println(sortedTweet[maxVertex] + " " + maxtweet2);
          /*  for(int i=maxVertex-maxtweet2+1;i<wordRedBlackTree.size()-(maxVertex-maxtweet2+1);i++){
                sortedTweet[i]=sortedTweet[maxVertex+i];
            }
            sizedown+=maxVertex-maxtweet2+1;
            maxtweet1=maxtweet2=maxVertex=0;
        }*/



    }

    public static void Top5tweetUsers(){
        int maxtweet1=0;
        int maxtweet2=0;
        int maxVertex=0;
        String[] Tweetusers=new String[500];
        String first="@";RedBlackTree usertweeted=new RedBlackTree();
        int arrnum=0;
        //for(int j=0;j<5;j++) {
        for (int i = 0; i < wordRedBlackTree.size(); i++) {
            if (first.equals(wordVertex[i].tweet)) {
                Tweetusers[arrnum++]=wordVertex[i+1].tweet;
                usertweeted.insert(wordVertex[i+1].tweet);
            }
        }
        String[] newTweetusers=new String[usertweeted.size()];

        String tag="!";

        for(int i=0;i<usertweeted.size();i++){
            newTweetusers[i]=(String)(usertweeted.getGreaterThan("!", usertweeted.size()).get(i));
        }
        System.out.println(usertweeted.getGreaterThan("!",usertweeted.size()));

        String temp=newTweetusers[0];
        for(int i=0; i<usertweeted.size();i++){
            if(temp.equals(Tweetusers[i])){
                maxtweet1++;
            }
            else{
                temp=newTweetusers[i];
                maxtweet1=1;
            }
            if(maxtweet1>=maxtweet2){
                maxtweet2=maxtweet1;
                maxVertex=i;
            }
        }
        System.out.println(newTweetusers[maxVertex] + " " + maxtweet2);




    }
    static int[] findfriends=new int[3000];
    static int findn=0;
    public static void FindUsersTweeted(){
        String users="";


        for(int i=0;i<wordRedBlackTree.size();i++){
            if(wordVertex[i].tweet.equals(whichtweet)){
                findfriends[findn]=i;
                findn++;
                System.out.print(wordVertex[i].usernum+" "+findn+" ");

                System.out.println();

            }
        }

    }
    public static void FindusersTweetedFriends(){

    }


}
