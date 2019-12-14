public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        int[][] rr={{1,1,0,0},{1,1,0,0},{0,1,1,1},{0,0,1,1},{0,0,0,1}};//false--no partition
        int[][] rrr={{1,1,0,0},{1,1,0,0},{0,0,1,1},{0,0,1,1},{0,0,0,1}};//true--partition
        int[][] rr1={{1,1,1,0,0,0},{0,1,0,0,0,0},{1,1,1,1,0,0},{0,0,0,1,1,1},{0,0,0,1,1,1},{0,0,0,1,1,1}};//false--no partition
        int[][] rrr1={{1,1,1,0,0,0},{0,1,0,0,0,0},{1,1,1,0,0,0},{0,0,0,1,1,1},{0,0,0,1,1,1},{0,0,0,1,1,1}};//true--partition
        RoleTransfer rt=new RoleTransfer();
        System.out.println(rt.CheckPartition(rrr1));
    }
}
