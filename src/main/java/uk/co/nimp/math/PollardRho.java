package uk.co.nimp.math;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


class PollardRho {

    public static List<Integer> primeFactors(int n){
        List<Integer> factors = new ArrayList<Integer>();

        n=Primes.smallTrialDivision(n,factors);
        if(1==n) return factors;

        if(Primes.millerRabinPrimeTest(n)){
            factors.add(n);
            return factors;
        }

        int divisor=rhoBrent(n);
        factors.add(divisor);
        factors.add(n/divisor);
        return factors;
    }

    static int rhoStore(int n) {
        int divisor;
        int PR_cst  = Primes.PRIMES_LAST;
        long x  = 2;
        long xx = x;
        int trial=0;
        int[] fx=new int[2000];
        do {
            int prodModN=1;
            for(int i=0;i<15;i++){  //fine tuning: optimal value of i must be somewhere between 2 and 50
                xx  =  (xx*xx+ PR_cst)%n;
                fx[2*trial]=(int)xx;
                xx  =  (xx*xx+ PR_cst)%n;
                fx[2*trial+1]=(int)xx;
                x=fx[trial];

                trial++;

                int diff=(int)(x-xx);
                if(0==diff){
                    PR_cst  = PR_cst + Primes.PRIMES_LAST;//we closed the rho, change f(x)
                    //Primes.maxTrial=Math.max(Primes.maxTrial,trial);
                    trial=0;
                }else{

                    prodModN=(int)((((long)prodModN) * diff)%n);
                    if(0==prodModN){
                        divisor=Gcd.binaryGcd(Math.abs((int)diff),n);
                        //Primes.maxTrial=Math.max(Primes.maxTrial,trial);
                        return divisor;
                    }
                }
            }
            divisor=Gcd.binaryGcd(Math.abs(prodModN),n);
        } while(divisor==1);
        //Primes.maxTrial=Math.max(Primes.maxTrial,trial);
        assert(divisor!=n);
        return divisor;
    }


    static int rhoBase(int n) {
        int divisor;
        int PR_cst  = Primes.PRIMES_LAST;
        long x  = 2;
        long xx = x;
        //int trial=0;
        do {
            int prodModN=1;
            for(int i=0;i<15;i++){  //fine tuning: optimal value of i must be somewhere between 2 and 50
                x  =  (x*x+ PR_cst)%n;
                xx  =  (xx*xx+ PR_cst)%n;
                xx  =  (xx*xx+ PR_cst)%n;

                //trial++;

                int diff=(int)(x-xx);
                if(0==diff){
                    PR_cst  = PR_cst + Primes.PRIMES_LAST;//we closed the rho, change f(x)
                    //Primes.maxTrial=Math.max(Primes.maxTrial,trial);
                    //trial=0;
                }else{

                    prodModN=(int)((((long)prodModN) * diff)%n);
                    if(0==prodModN){
                        divisor=Gcd.binaryGcd(Math.abs((int)diff),n);
                        //Primes.maxTrial=Math.max(Primes.maxTrial,trial);
                        return divisor;
                    }
                }
            }
            divisor=Gcd.binaryGcd(Math.abs(prodModN),n);
        } while(divisor==1);
        //Primes.maxTrial=Math.max(Primes.maxTrial,trial);
        assert(divisor!=n);
        return divisor;
    }


    static int rhoBrent(int n){
        final int x0=2;
        final int m=25;
        int cst=Primes.PRIMES_LAST;
        int y=x0;
        int r=1;
        do{
            int x=y;
            for(int i=0;i<r;i++) {
                long y2=((long)y)*y;
                y=(int)((y2+cst)%n);
            }
            int k=0;
            do{
                final int bound=Math.min(m, r-k);
                int q=1;
                for(int i=-3;i<bound;i++){ //start at -3 to ensure we enter this loop at least 3 times
                    final long y2=((long)y)*y;
                    y=(int)((y2+cst)%n);
                    final long divisor=Math.abs(x-y);
                    if(0==divisor){
                        cst+=Primes.PRIMES_LAST;
                        k=-m;
                        y=x0;
                        r=1;
                        break;
                    }
                    final long prod=divisor*q;
                    q=(int)(prod%n);
                    if(0==q)
                        return Gcd.binaryGcd(Math.abs((int)divisor),n);
                }
                final int out=(int)Gcd.gcd(q,n);
                if(1!=out)
                    return out;
                k=k+m;
            }while(k<r);
            r=2*r;
        }while(true);
    }

    static int rhoCohen(int n){
        int x=2;
        int x1=2;
        int k=1;
        int l=1;
        int cst=Primes.PRIMES_LAST;
        final int m=20;
        final int kDelta=-1;
        //final int kDelta=-m;
        do{
            do{
                int p=1;
                for(int c=0;c<m;c++){ //fine tuning: the optimal value seems to be between 15 and 35
                    long x2=((long)x)*x;
                    x=(int)((x2+cst)%n);
                    int divisor=x1-x;
                    if(0==divisor){
                        cst+=Primes.PRIMES_LAST;
                        x=2;
                        x1=2;
                        k=1-kDelta;
                        l=1;
                        p=1;
                        break;
                    }
                    p=(int)((((long)p)*divisor)%n);
                    if(0==p){
                        int out=(int)Gcd.binaryGcd(Math.abs(divisor),n);
                        return out;
                    }
                }

                int out=(int)Gcd.binaryGcd(Math.abs(p),n);
                if(1!=out) return out;
                k+=kDelta;
            }while(k>0);
            x1=x;
            k=l;
            l=l<<1;
            for(int i=0;i<k;i++){
                long x2=((long)x)*x;
                x=(int)((x2+cst)%n);
            }
        }while(true);
    }




    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE  = new BigInteger("1");
    private final static BigInteger TWO  = new BigInteger("2");
    private final static SecureRandom random = new SecureRandom();
    private final static BigInteger c  = BigInteger.valueOf(1);

    static BigInteger rho(BigInteger N) {
        BigInteger divisor;
        //BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do {
            x  =  x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    static void factor(BigInteger N) {
        if (N.compareTo(ONE) == 0) return;
        if (N.isProbablePrime(20)) { System.out.println(N); return; }
        BigInteger divisor = rho(N);
        factor(divisor);
        factor(N.divide(divisor));
    }
}
