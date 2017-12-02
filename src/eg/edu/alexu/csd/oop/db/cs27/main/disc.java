package eg.edu.alexu.csd.oop.db.cs27.main;

import java.math.BigInteger;

public class disc{
    
    private BigInteger methodOne(BigInteger a,BigInteger b,BigInteger n){
	BigInteger res = new BigInteger("1");
	for(long i=0;i<b.longValue();i++){
	    res = res.multiply(a);
	}
	return res.mod(n);
    }
    
    private BigInteger methodTwo (BigInteger a,BigInteger b,BigInteger n){
	BigInteger res = new BigInteger("1");
	for(long i=0;i<b.longValue();i++){
	    res = res.multiply(a).mod(n);
	}
	return res;
    }
    
    private BigInteger methodThree(BigInteger a,BigInteger b,BigInteger n){
	
	//if it's the end of the recurrsion
	if(b.longValue() == 0)
	    return new BigInteger("1");
	
	//make the recurrsion
	BigInteger res = a.mod(n);
	a = methodThree(res,b.divide(new BigInteger("2")),n);
	a = a.multiply(a).mod(n);
	
	if(b.longValue() % 2 == 1)
	    a = a.multiply(res).mod(n);
	
	return a;
	
    }
    
    private void modInverse(int a , int n){
	
	//to hold x,y
	int x[] = new int[2];
	
	//pass them by reffrence
	int g = extendedEuclid(a, n, x);
	
	//if they are relative prime then print it , else print not existed
	if(g != 1)
	    System.out.println("Not Exists !!");
	else
	    System.out.println("Mod Ex Inverse is " + (x[0]%n+n)%n);
    }
    
    private int extendedEuclid(int a, int n, int[]x){
	
	//the base case
	if(a == 0){
	    x[0] = 0;
	    x[1] = 1;
	    return n;
	}
	
	//new Array list to pass by ref
	int y[] = new int[2];
	
	//recurencce solution
	int gcd = extendedEuclid(n%a, a, y);
	
	//reedit the parameters given
	x[0] = y[1] - (n/a) * y[0];
	x[1] = y[0];
	
	//return the gcd to check if they are relative prime or not
	return gcd;
    }
    
    public static void main(String args[]){
	disc x = new disc();
	long s1,s2,s3,s4;
	
	
	
	
	x.modInverse(3,11);
	x.modInverse(5,11);
	x.modInverse(5,17);
	x.modInverse(2,11);
	x.modInverse(6,11);
	x.modInverse(2,4);
	
	
	
	
	
	System.out.println("Test1");
	int n = 0;
	for(int i=0;i<1;i++){
	    s1 = System.nanoTime();
	    x.methodOne(new BigInteger("9999999999999999999"),
		    new BigInteger("99999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/1);

	n = 0;
	for(int i=0;i<1;i++){
	    s1 = System.nanoTime();
	    x.methodTwo(new BigInteger("9999999999999999999"),
		    new BigInteger("10000"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/1);
	
	n = 0;
	for(int i=0;i<1;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("10000"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/1);
	
	
	
	/*
	System.out.println("\nTest2");
	 n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodOne(new BigInteger("9999999999999999999"),
		    new BigInteger("9999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);

	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodTwo(new BigInteger("9999999999999999999"),
		    new BigInteger("9999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("9999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	
	System.out.println("\nTest3");
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodTwo(new BigInteger("9999999999999999999"),
		    new BigInteger("999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	
	System.out.println("\nTest4");
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodTwo(new BigInteger("9999999999999999999"),
		    new BigInteger("9999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("9999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	
	
	
	System.out.println("\nTest5");
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("99999999999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	

	
	System.out.println("\nTest6");
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("9999999999999999999999999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	
	
	
	
	System.out.println("\nTest7");
	n = 0;
	for(int i=0;i<10;i++){
	    s1 = System.nanoTime();
	    x.methodThree(new BigInteger("9999999999999999999"),
		    new BigInteger("9999999999999999999999999999999999999999999"
			    + "999999999999999999999999999999999999999999999999"
			    + "999999999"),new BigInteger((345*(i+1))+""));
	    s2 = System.nanoTime();
	    n += ((s2-s1)/1000);
	}
	System.out.println(n/10);
	*/
    }
}

/*

//b = 2,4,6 ,7 ,14 ,28 ,100

    Test1
    method1 : 1110
    method2 : 568
    method3 : 181

    Test2
    method1 : 911796
    method2 : 3610
    method3 : 68

    Test3
    method2 : 186614
    method3 : 55

    Test4
    method2 : 1938056
    method3 : 46

    Test5
    method3 : 66

    Test6
    method3 : 115

    Test7
    method3 : 333

    */