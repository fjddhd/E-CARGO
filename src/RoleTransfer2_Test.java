public class RoleTransfer2_Test {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        int[][] zero={{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        int[][] c0={{1,0,0,0,0,0,0,0},{0,1,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,1,0,0,0,0,0},
                {0,0,0,1,0,0,0,0},{0,0,0,0,1,0,0,0},{0,0,0,0,0,1,0,0},{0,0,0,0,0,0,1,0}};//c0
        int[][] q={{0,1,0,0,0,0,0,0},{1,0,1,0,0,0,0,0},{1,1,1,0,0,0,0,0},{1,1,0,0,0,0,0,0},
                {0,0,0,0,1,1,0,0},{0,0,0,0,0,1,0,0},{0,0,0,0,1,0,1,0},{0,0,0,0,0,1,0,1}};//q
        int[][] c1=new int[8][8];
        RoleTransfer2 rt2=new RoleTransfer2();
//        rt2.testArray2(zero);
        int[] one={1,2,3,4,5},two={1,2,3,4,5,6};
//        zero=rt2.copyMatricesAndRestoreRowAndColumn(zero,1,1,one,two);
        rt2.RoleTransfer(c0,c1,q);
        System.out.println();
        System.out.println("1");
    }
}
