public class RoleTransfer2 {
    public boolean RoleTransfer(int[][] C0_Matrices,int[][] C1_Matrices,int[][] Q_Matrices){
        int M=C0_Matrices.length,N=C0_Matrices[0].length;//M:size of agents;N:size of roles
        //If (M = 0) then return failure;
        if (M==0){
            return false;
        }
        //If (in matrix Z = Q + C0 there is one column with all zeros,
        //i.e., Z[j, i] = 0(j = 0, 1, . . .,N − 1)) then return failure;
        //C1 := C0;
        int[][] Z_Matrices=new int[M][N];//maybe role repository
//        C1_Matrices=new int[M][N];//- WARN 不可采用初始化C1方案
        for (int i=0;i<M;++i){
            for (int j=0;j<N;++j){
                Z_Matrices[i][j]=C0_Matrices[i][j]+Q_Matrices[i][j];
                C1_Matrices[i][j]=C0_Matrices[i][j];
            }
        }
        for (int i=0;i<N;++i){
            int countColumn = countColumn(Z_Matrices, i);
            if (countColumn==0){
                return false;
            }
        }
        //For (all columns of C1)
        for (int j=0;j<N;++j){
            //If (column j has all zeros in C1)
            int countColumn = countColumn(C1_Matrices,j);
            if (countColumn==0){
                //FromQ, find row number i Q[i, j] = 1;
                for (int i=0;i<M;++i){
                    if (Q_Matrices[i][j]==1){
                        //In C1, check if this agent (i) has no current role, i.e.,it is a free agent;
                        int countRow=countRow(C1_Matrices,i);
                        if (countRow==1){//If (Yes)
                            C1_Matrices[i][j]=1;
                            Q_Matrices[i][j]=0;
                            //C0_:= C1 and Q_:= Q;
                            //Delete row i and column j from C0 and Q;
                            int[][] C0_=copyMatricesAndDeleteRowAndColumn(C1_Matrices,i,j);
                            int[][] Q_=copyMatricesAndDeleteRowAndColumn(Q_Matrices,i,j);
                            //移除了原文中的 ：M := M − 1, N := N − 1;  --原因：每次调用方法先根据传入的C0获取M，N
                            //新增：手动保存Q和C1的i行，j列值
                            int[] rowC1=new int[N];
                            int[] rowQ=new int[N];
                            int[] columnC1=new int[M];
                            int[] columnQ=new int[M];
                            for (int a=0;a<N;++a){
                                rowC1[a]=C1_Matrices[i][a];
                                rowQ[a]=Q_Matrices[i][a];
                            }
                            for (int a=0;a<M;++a){
                                columnC1[a]=C1_Matrices[a][j];
                                columnQ[a]=Q_Matrices[a][j];
                            }
                            //Call Process(C0 ,C1_,Q_,M,N);
                            RoleTransfer(C0_,C1_Matrices,Q_);
                            //Q := Q and C1 := C1 with keeping the original
                            //row i and column j;
                            Q_Matrices=copyMatricesAndRestoreRowAndColumn(Q_,i,j,rowQ,columnQ);
                            C1_Matrices=copyMatricesAndRestoreRowAndColumn(C1_Matrices,i,j,rowC1,columnC1);

                        }else {
                            //Find the column index k such that C1[i, k] = 1;
                            int k=0;
                            for (;k<N;++k){
                                if (C1_Matrices[i][k]==1){
                                    break;
                                }
                            }
                            //If (Yes)
                            if (k>=0 && k<N){
                                C1_Matrices[i][j]=1;Q_Matrices[i][j]=0;Q_Matrices[i][k]=1;C1_Matrices[i][k]=0;

                                //C0_:= C1 and Q_:= Q;
                                //Delete row i and column j from C0 and Q;
                                int[][] C0_=copyMatricesAndDeleteRowAndColumn(C1_Matrices,i,j);
                                int[][] Q_=copyMatricesAndDeleteRowAndColumn(Q_Matrices,i,j);
                                //移除了原文中的 ：M := M − 1, N := N − 1;  --原因：每次调用方法先根据传入的C0获取M，N
                                //新增：手动保存Q和C1的i行，j列值
                                int[] rowC1=new int[N];
                                int[] rowQ=new int[N];
                                int[] columnC1=new int[M];
                                int[] columnQ=new int[M];
                                for (int a=0;a<N;++a){
                                    rowC1[a]=C1_Matrices[i][a];
                                    rowQ[a]=Q_Matrices[i][a];
                                }
                                for (int a=0;a<M;++a){
                                    columnC1[a]=C1_Matrices[a][j];
                                    columnQ[a]=Q_Matrices[a][j];
                                }
                                //Call Process(C0 ,C1_,Q_,M,N);
                                RoleTransfer(C0_,C1_Matrices,Q_);
                                //Q := Q and C1 := C1 with keeping the original
                                //row i and column j;
                                Q_Matrices=copyMatricesAndRestoreRowAndColumn(Q_,i,j,rowQ,columnQ);
                                C1_Matrices=copyMatricesAndRestoreRowAndColumn(C1_Matrices,i,j,rowC1,columnC1);
                            }
                            else {
                                return false;//failure
                            }
                        }
                    }
                }
            }
        }

        return true;

    }
    /**
     * 返回传入矩阵第column列相加的值
     * */
    public int countColumn(int[][] Matrices,int Column){
        int M=Matrices.length,N=Matrices[0].length;//M:size of agents;N:size of roles
        int count=0;
        for (int i=0;i<M;++i){
            count+=Matrices[i][Column];
        }
        return count;
    }
    /**
     * 返回传入矩阵第row行相加的值
     * */
    public int countRow(int[][] Matrices,int row){
        int M=Matrices.length,N=Matrices[0].length;//M:size of agents;N:size of roles
        int count=0;
        for (int j=0;j<N;++j){
            count+=Matrices[row][j];
        }
        return count;
    }
    /**
     * 返回一个矩阵，
     * 该矩阵是传入矩阵去掉i行j列后的矩阵
     * */
    public int[][] copyMatricesAndDeleteRowAndColumn(int[][] Matrices,int i,int j){
        int M=Matrices.length,N=Matrices[0].length;//M:size of agents;N:size of roles
        int[][] res=new int[M-1][N-1];
        for (int a=0;a<M;++a){
            int c=a;
            if (a==i){
                continue;
            }else if (a>i){
                c--;
            }
            for (int b=0;b<N;++b){
                int d=b;
                if (b==j){
                    continue;
                }else if (b>j){
                    d--;
                }
                res[c][d]=Matrices[a][b];
            }
        }
        return res;
    }
    /**
     * 返回一个矩阵，
     * 该矩阵是传入矩阵的补充i行j列矩阵
     * */
    public int[][] copyMatricesAndRestoreRowAndColumn(int[][] Matrices,int i,int j,int[] row,int[] column){
        int M=Matrices.length,N=Matrices[0].length;//M:size of agents;N:size of roles
        int[][] res=new int[M+1][N+1];
        for (int a=0;a<M+1;++a){
            int c=a;
            if (a>i){
                c--;
            }
            for (int b=0;b<N+1;++b){
                int d=b;
                if (b>j){
                    d--;
                }
                if (a==i && b==j){
                    res[a][b]=row[b];//column[i]也可以
                    continue;
                }else if (a==i && b!=j){
                    res[a][b]=row[b];
                    continue;
                }else if (a!=i && b==j){
                    res[a][b]=column[a];
                    continue;
                }
                res[a][b]=Matrices[c][d];
            }
        }
        return res;
    }
    /**
     * 验证:数组作为传参是跟对象一样是引用传递
     * */
    public void testArray(int[][] Matrices){
        int M=Matrices.length,N=Matrices[0].length;
        for (int i=0;i<M;++i){
            for (int j=0;j<N;++j){
                Matrices[i][j]++;
            }
        }
        System.out.println("aa");
    }
    /**
     * 本方法中递归用法：传入变量应该指向新的矩阵
     * 建议，需要把新的矩阵作为返回值返回
     * */
    public void testArray2(int[][] Matrices){
        int M=Matrices.length,N=Matrices[0].length;
        Matrices=new int[M][N];
        for (int i=0;i<M;++i){
            for (int j=0;j<N;++j){
                Matrices[i][j]=9;
            }
        }
        System.out.println("aa");
    }
}
