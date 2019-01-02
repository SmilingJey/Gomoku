package ru.jcod.gomoku.mobile;

import java.util.Random;

public class Xoxo {

	public int cost_2=1;
	public int cost_x1=3;
	public int cost_x0=8;
	public int cost_xx1=20;
	public int cost_xx0=250;
	public int cost_xxx1=30;
	public int cost_xxxx1=500;
	public int cost_xxx0=4000;
	public int cost_xxxx0=16000;
	public int cost_win=40000;
	public int cost_border=300;

	public int n;
	public double cost[][];
	public byte xo;
	public Pole pole;

	public Xoxo(Pole pole){
		this.pole=pole;
		this.n=pole.n;
		cost=new double[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				cost[i][j]=0;
		}
	}

	public void hod(byte xo){
		this.xo=xo;
		do_hod();
	}

	public void do_hod(){
		cost_arr_calk();
		double max_cost=-10000;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if (cost[i][j]!=0)
				if (cost[i][j]>max_cost){
					max_cost=cost[i][j];
				}
			}
		}
		int cost_max[][]=new int[n*n][2];
		int count_max=0;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if (cost[i][j]==max_cost){
					cost_max[count_max][0]=i;
					cost_max[count_max][1]=j;
					count_max++;
				}
			}
		}
		int n=randomInt(0,count_max-1);
		pole.pole[cost_max[n][0]][cost_max[n][1]]=xo;

	}
    public static int randomInt(int minValue, int maxValue) {
        int rndValue;
        try {
            rndValue = new Random().nextInt()&(maxValue+minValue)-minValue; //maxValue-minValue -1)+minValue;
        } catch(Throwable t) {
            System.out.println("Exception was thrown during call Random().nextInt() method: "+t);
            System.out.println("value -1 will be return as Random integer variable.");
            rndValue = -1;
        }
        return rndValue;
    }



	public void cost_arr_calk(){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
			if (pole.pole[i][j]==0){
			  if (i==0 || i==n-1 || j==0 || j==n-1) cost[i][j]=cost_cell_calk(i,j)-cost_border;
			  else cost[i][j]=cost_cell_calk(i,j);
			}
			  else cost[i][j]=0;
			}
		}
	}

	public double cost_cell_calk(int i,int j){
		double cost=0;
		double me_cost=me_cost(i,j,xo);
		double em_cost=me_cost(i,j,xo==2?1:2);
		cost=1*me_cost + 1*em_cost;
		return cost;
	}

	public double me_cost(int x,int y,int c){
		int i=0, j=0;
		double cost=0;
		int len_h=0,len_v=0,len_dl=0,len_dr=0;
		int zakr_h=0,zakr_v=0,zakr_dl=0,zakr_dr=0;
		//влево по горизонтали
		i=x;j=y;
		do{
			len_h++;
			if (i==0) break;
			i--;
		} while (c==pole.pole[i][j]);
		if ((x==0) || (pole.pole[i][j]==(c==2?1:2))) zakr_h++;

		//вправо по горизонтали
		i=x;j=y;
		do{
			len_h++;
			if (i==n-1) break;
			i++;
		} while (c==pole.pole[i][j]);
		if ((x==n-1) || (pole.pole[i][j]==(c==2?1:2))) zakr_h++;

		//вверх по вертикали
		i=x;j=y;
		do{
			len_v++;
			if (j==0) break;
			j--;
		} while (c==pole.pole[i][j]);
		if ((y==0) || (pole.pole[i][j]==(c==2?1:2))) zakr_v++;

		//вниз по вертикали
		i=x;j=y;
		do{
			len_v++;
			if (j==n-1) break;
			j++;
		} while (c==pole.pole[i][j]);
		if ((y==n-1) || (pole.pole[i][j]==(c==2?1:2))) zakr_v++;

		//влево-вверх
		i=x;j=y;
		do{
			len_dl++;
			if (j==0) break;
			if (i==0) break;
			j--;
			i--;
		} while (c==pole.pole[i][j]);
		if ((y==0) || (x==0) || (pole.pole[i][j]==(c==2?1:2))) zakr_dl++;

		//вправо-вниз
		i=x;j=y;
		do{
			len_dl++;
			if (j==n-1) break;
			if (i==n-1) break;
			j++;
			i++;
		} while (c==pole.pole[i][j]);
		if ((y==n-1) || (x==n-1) || (pole.pole[i][j]==(c==2?1:2))) zakr_dl++;

		//влево-вниз
		i=x;j=y;
		do{
			len_dr++;
			if (j==n-1) break;
			if (i==0) break;
			j++;
			i--;
		} while (c==pole.pole[i][j]);
		if ((y==n-1) || (x==0) || (pole.pole[i][j]==(c==2?1:2))) zakr_dr++;
		//вправо - вверх
		i=x;j=y;
		do{
			len_dr++;
			if (j==0) break;
			if (i==n-1) break;
			j--;
			i++;
		} while (c==pole.pole[i][j]);
		if ((y==0) || (x==n-1) || (pole.pole[i][j]==(c==2?1:2))) zakr_dr++;

		cost=get_cost(len_h-1,zakr_h)+get_cost(len_v-1,zakr_v)+get_cost(len_dl-1,zakr_dl)+get_cost(len_dr-1,zakr_dr);
		cost=cost*cost;
		return cost;
	}

	public int get_cost(int len,int zakr){
		int cost=0;

		switch(len){
			case 1:
				if (zakr==0) cost=cost_x0;
				if (zakr==1) cost=cost_x1;
				if (zakr==2) cost=cost_2;
			break;
			case 2:
				if (zakr==0) cost=cost_xx0;
				if (zakr==1) cost=cost_xx1;
				if (zakr==2) cost=cost_2;
			break;
			case 3:
				if (zakr==0) cost=cost_xxx0;
				if (zakr==1) cost=cost_xxx1;
				if (zakr==2) cost=cost_2;
			break;
			case 4:
				if (zakr==0) cost=cost_xxxx0;
				if (zakr==1) cost=cost_xxxx1;
				if (zakr==2) cost=cost_2;
			break;
			default: cost=cost_win;
		}
		return cost;

	}
}