import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RoleTransfer1 {
    /***
     * Input: AnM × N matrix S that is the role repository matrix.
     * Output: TRUE if S has partitions; and FALSE if it has no partition.
     * 如：{{1,1,0,0},{1,1,0,0},{0,1,1,1},{0,0,1,1},{0,0,0,1}}
     * 为：
     * 1,1,0,0
     * 1,1,0,0
     * 0,1,1,1
     * 0,0,1,1
     * 0,0,0,1
     * * */
    public boolean CheckPartition(int[][] S_Matrices){
        int M=S_Matrices.length,N=S_Matrices[0].length;//M:size of agents;N:size of roles

        ArrayList<Integer> A=new ArrayList<>();//size:M
        for (int tempi=0;tempi<M;++tempi){
            A.add(0);
        }
        ArrayList<Integer> R=new ArrayList<>();//size:N
        for (int tempi=0;tempi<N;++tempi){
            R.add(0);
        }
        int i=0,j=0,asist=0;
        for (;i<M;++i){
            if (asist==1){
                i--;
                break;
            }
            for (;j<N;++j){
                if (S_Matrices[i][j]==1){
                    asist=1;
                    break;
                }
            }
        }
        if (i>0){
            return true;
        }
        A.set(i,1);
        R.set(j,1);
        int T=1;
        ArrayList<Integer> Crba=CRBA(S_Matrices,i);
        int CountOfAdd=0;
        while (T==1){
            CountOfAdd++;
            addRole(R,Crba);
            T=0;
            ArrayList<Integer> Cabr=new ArrayList<>();
            for (int temp_i=0;temp_i<N;++temp_i){
                if (R.get(temp_i)==1){
                    for (int temp_j=0;temp_j<M;++temp_j){
                        if (S_Matrices[temp_j][temp_i]==1){
                            Cabr.add(temp_j);
                        }
                    }
                }
            }
            HashSet<Integer> hs=new HashSet(Cabr);
            Cabr=new ArrayList<>(hs);
            if (Cabr.size()>CountOfAdd){
                T=1;
            }
            if (T==1){
                addAgent(A,Cabr);
                T=0;
                Crba=new ArrayList<>();
                for (int temp_j=0;temp_j<M;++temp_j){
                    if (A.get(temp_j)==1){
                        for (int temp_i=0;temp_i<N;++temp_i){
                            if (S_Matrices[temp_j][temp_i]==1){
                                Crba.add(temp_i);
                            }
                        }
                    }
                }
                HashSet<Integer> hs_crba=new HashSet(Crba);
                Crba=new ArrayList<>(hs_crba);
                if (Crba.size()>CountOfAdd){
                    T=1;
                }
            }
        }
        if (count(A)==M && count(R)==N){
            return false;
        }
        return true;

    }


    public int count(ArrayList<Integer> al){
        int count=0;
        for (int i=0;i<al.size();++i){
            count+=al.get(i);
        }
        return count;
    }
    public ArrayList<Integer> CRBA(int[][] S_Matrices,int i){
        ArrayList<Integer> L=new ArrayList<>();
        for (int j=0;j<S_Matrices[0].length;++j){
            if (S_Matrices[i][j]==1){
                L.add(j);
            }
        }
        return L;
    }
    public ArrayList<Integer> CABR(int[][] S_Matrices,int j){
        ArrayList<Integer> L=new ArrayList<>();
        for (int i=0;i<S_Matrices.length;++i){
            if (S_Matrices[i][j]==1){
                L.add(i);
            }
        }
        return L;
    }
    public boolean addRole(ArrayList<Integer> R,ArrayList<Integer> needToAdd){
        for (int i=0;i<R.size();++i){
            if (R.get(i)==0 && needToAdd.contains(i)){
                    R.set(i,1);
                    return true;
            }
        }
        return false;
    }
    public boolean addAgent(ArrayList<Integer> A,ArrayList<Integer> needToAdd){
        for (int i=0;i<A.size();++i){
            if (A.get(i)==0 && needToAdd.contains(i)){
                A.set(i,1);
                return true;
            }
        }
        return false;
    }

}
