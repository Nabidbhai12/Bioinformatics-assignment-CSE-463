import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static int getScore(Vector<String> lmers, String consensus){
        int count = 0, n = consensus.length();
        for(String s : lmers){
            for(int i=0; i<n; i++)
                if(s.charAt(i) != consensus.charAt(i))
                    count++;
        }
        return count;
    }

    public static String findMotif(Vector<String> dataset, int motifLength){
        Random random = new Random();
        bimap bi = new bimap();
        int sequenceLength = dataset.get(0).length();
        Vector<String> lmers = new Vector<>();
        for (String s : dataset) {
            int n = random.nextInt(sequenceLength - motifLength + 1);
            lmers.add(s.substring(n, n + motifLength));
        }
        String consensus = "";
        int score = Integer.MAX_VALUE, trials = 5;
        while(true){
            int exclude = random.nextInt(lmers.size());
            int[][] count = new int[4][motifLength];
            for(int i=0; i< lmers.size(); i++){
                if(i==exclude) continue;
                for(int j=0; j<motifLength; j++)
                    count[bi.getIndex(lmers.get(i).charAt(j))][j]++;
            }
            int flag = 0;
            for(int i=0; i<4; i++)
                for(int j=0; j<motifLength; j++)
                    if(count[i][j]==0){
                        flag = 1;
                        break;
                    }
            if(flag==1){
                for(int i=0; i<4; i++)
                    for(int j=0; j<motifLength; j++)
                        count[i][j]++;
            }
            char[] consensusString = new char[motifLength];
            for(int i=0; i<motifLength; i++){
                int max = Math.max(Math.max(count[0][i],count[1][i]),Math.max(count[2][i],count[3][i]));
                Vector<Integer> maxIndex = new Vector<>();
                for(int j=0; j<4; j++)
                    if(count[j][i] == max)
                        maxIndex.add(j);
                consensusString[i] = bi.getBase(maxIndex.get(random.nextInt(maxIndex.size())));
            }
            double[][] profile = new double[4][motifLength];
            for(int i=0; i<4; i++)
                for(int j=0; j<motifLength; j++){
                    profile[i][j] = (double)count[i][j]/(count[0][j] + count[1][j] + count[2][j] + count[3][j]);
                }
            double[] probabilities = new double[sequenceLength-motifLength+1];
            double total = 0;
            for(int i=0; i<=sequenceLength-motifLength; i++){
                String temp = dataset.get(exclude).substring(i,i+motifLength);
                double d = 1;
                for(int j=0; j<motifLength; j++)
                    d *= profile[bi.getIndex(temp.charAt(j))][j];
                probabilities[i] = d;
                total += d;
            }
            for(int i=0; i<probabilities.length; i++)
                probabilities[i] /= total;
            double rand = Math.random();
            total = 0;
            for(int i=0; i<probabilities.length; i++){
                total += probabilities[i];
                if(total>rand){
                    lmers.set(exclude, dataset.get(exclude).substring(i, i+motifLength));
                    break;
                }
            }
            int tempScore =  getScore(lmers, new String(consensusString));
            if(tempScore<score){
                consensus = new String(consensusString);
                score = tempScore;
                trials = 5;
            } else {
                trials--;
                if(trials == 0)
                    break;
            }
        }
        return consensus;
    }

    public static void main(String[] args) {
        Vector<String> dataset1 = new Vector<>();
        Vector<String> dataset2 = new Vector<>();
        Vector<String> dataset3 = new Vector<>();
        try {
            File file = new File("hm03.txt");
            Scanner sc = new Scanner(file);

            while(sc.hasNext()){
                dataset1.add(sc.nextLine());
            }

            sc.close();
            file = new File("yst04r.txt");
            sc = new Scanner(file);

            while(sc.hasNext()){
                dataset2.add(sc.nextLine());
            }

            sc.close();
            file = new File("yst08r.txt");
            sc = new Scanner(file);

            while(sc.hasNext()){
                dataset3.add(sc.nextLine());
            }

        } catch (FileNotFoundException e){
            System.out.println(e);
        }

        Vector<String> motifs1 = new Vector<>();
        Vector<String> motifs2 = new Vector<>();
        Vector<String> motifs3 = new Vector<>();

        for(int i=0; i<9; i++){
            motifs1.add(findMotif(dataset1,i+8));
            motifs2.add(findMotif(dataset2,i+8));
            motifs3.add(findMotif(dataset3,i+8));
        }

        try {
            FileWriter fw = new FileWriter("output.txt");
            fw.write("hm03\n");
            for(String s : motifs1)
                fw.write(s + "\n");
            fw.write("\nyst04r\n");
            for(String s : motifs2)
                fw.write(s+"\n");
            fw.write("\nyst08r\n");
            for(String s : motifs3)
                fw.write(s+"\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class bimap {

    public int getIndex(char c){
        if(c == 'A')
            return 0;
        else if (c == 'T')
            return 1;
        else if (c == 'G')
            return 2;
        else if (c == 'C')
            return 3;
        else return -1;
    }

    public char getBase(int n){
        if(n == 0)
            return 'A';
        else if (n == 1)
            return 'T';
        else if (n == 2)
            return 'G';
        else if (n == 3)
            return 'C';
        else return 'X';
    }
}