import java.util.ArrayList;

/**
 * Created by Woonghee on 2016-06-23.
 */
public class User implements Comparable<User>{
    int usernum;

    String username;

    int color;

    int parent;

    int n;

    ADJ first;

    User(){
        color=0;
        parent=-1;
        n=0;
        first=null;
        usernum=0;
        username=null;
    }

    User(int usernum, String username){
        this.usernum = usernum;

        this.username = username;

    }
    User(int usernum){
        this.usernum=usernum;
    }
    public void User_add(User self, User v){
        ADJ a=new ADJ();
        a.n=v.n;
        a.next=self.first;
        self.first=a;
    }



    @Override
    public int compareTo(final User o) {


        if(this.usernum<o.usernum) return -1;
        else return 0;
    }
}
