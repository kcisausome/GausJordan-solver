import java.io.PrintStream;
import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Arrays;
//solves a system of equations using partial pivoting gaussian elimination

public class GausJor {
    private Scanner scn = new Scanner(System.in);

    public static void main(String[] args) throws Exception{
        new GausJor();
    }

    public GausJor()throws Exception{
    	System.out.println("type a number of linear equations to solve(2-10) and press enter");
        int num = scn.nextInt();
        if((num > 10) || (num == 0) || (num == 1)){
        	exit();
        }
        System.out.println("file? y/n");
        String f = scn.next();
        if(f.equals("y")){
        	partialPivot(readFile(num));
        }else{
        	System.out.println("enter coefficients of each equation");
        	partialPivot(createMatrix(num));
        }
    }

    //if user has already made file, creates a matrix
    public double[][] readFile(int size)throws Exception{
        //creates matrix including right hand column vector
    	double[][] mat = new double[size][size+1];
    	System.out.println("type file name");
    	String fileName = scn.next();
    	File presetMatrix = new File(fileName);
    	Scanner scanner = new Scanner(presetMatrix);
    	int row = 0;
    	while (scanner.hasNextLine()){
    		String[] ind = scanner.nextLine().split("\\s+");
    		for(int i = 0;i<size+1;i++){
    			mat[row][i] = Double.parseDouble(ind[i]);
    		}
    		row++;
    	}
    	return mat;
    }

    //creates matrix out of scratch
    public double[][] createMatrix(int size){
      	double[][] mat = new double[size][size+1];
    	for(int i = 0;i<size;i++){//rows
    		System.out.println("coefficients for row "+i);
    		for(int j = 0;j<size+1;j++){//columns
    			double coeff = scn.nextDouble();
    			mat[i][j] = coeff;
    		}
    	}
    	return mat;
    }

    public void partialPivot(double[][] mat){
    	//holds each rows largest number
    	double[] largestNum = new double[mat.length];
    	//i loop iterates thru row
    	for(int i = 0;i<mat.length;i++){
    		//jj loop finds largest number in a row(excludes last column)
            //@den holds largest num
    		double den = Double.MIN_VALUE;
    		for(int jj = 0;jj<mat[0].length-1;jj++){
    			if(mat[i][jj]>den){
    				den = Math.abs(mat[i][jj]);
    			}
    		}
    		largestNum[i] = den;
    	}
    	
    	//i keeps track of what column pivot we are at
    	for(int i = 0;i<mat[0].length-1;i++){
    		int pivotRow = largestRatioRow(i,largestNum, mat);
    		//basically cancels out that pivot row for future considerations
    		largestNum[pivotRow] = Double.MAX_VALUE;
    		int otherRows = 0;
    		while(otherRows<mat.length){
    			if(otherRows == pivotRow){
    				otherRows++;
    			}else{
    				double multiplier = ((-1)*mat[otherRows][i])/mat[pivotRow][i];
    				//changes each of 1 row's elements
    				for(int j = 0; j<mat[0].length;j++){
    					mat[otherRows][j] = (mat[pivotRow][j]*multiplier) + mat[otherRows][j];
    				}
    				otherRows++;
    			}
    		}
    		//prints out matrix at each step
    		System.out.println("-----------------------");
    		for(int x = 0; x<mat.length;x++){
    			for(int y=0;y<mat[0].length;y++){
    				System.out.printf("%2.1f  ",mat[x][y]);
    		}
    			System.out.println();
    		}
    	}
        //places variables in correct order
        double[] answers = new double[mat.length];
        for(int i = 0;i<mat.length;i++){
            for(int j = 0;j<mat[0].length;j++){
                if(mat[i][j] != 0){
                    answers[j] = mat[i][mat[0].length-1]/mat[i][j];
                    break;
                }
            }
        }
        System.out.println("answers:");
        for(int i = 0;i<answers.length;i++){
            System.out.printf("X"+i+" = %.2f\n",answers[i]);
        }	
    }

 
    //@return row number that has the biggest ratio
    public int largestRatioRow(int col, double[] denOfRow, double[][] mat){
    	double bigRatio = Double.MIN_VALUE;
    	int biggestRatioRow = -1;
    	for(int row = 0;row<mat.length;row++){
    		double num = Math.abs(mat[row][col]);
    		if((num/denOfRow[row])>bigRatio){
    			bigRatio = num/denOfRow[row];
    			biggestRatioRow = row; 
    		}
    	}
    	return biggestRatioRow;
    }

    public void exit(){
    	System.out.println("between 2 and 10 equations only");
    	System.exit(0);
    }
}
