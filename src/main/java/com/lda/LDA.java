package com.lda;


public class LDA implements Runnable{

	@Override
	public void run() {
		LDAOption option = new LDAOption();
		
		option.dir = ".";
		option.dfile = "lda.txt";
		option.est = true;  /////
		option.estc = false;
//		option.inf = false;
		option.modelName = "model";
		option.niters = 100;
		option.K = 50;
		Estimator estimator = new Estimator();
		estimator.init(option);
		estimator.estimate();
	}

	public static void main(String[] args) {
		new LDA().run();
	}
	
}
