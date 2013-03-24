package uk.co.nimp.math;

import java.math.BigInteger;
import java.util.*;

/**
 * Static methods related to prime numbers. Only deals with <code>int</code> type.
 * @author Sebastien Riou
 * @version 1.0
 */
public class Primes {
    private Primes(){}
    /**
     * The 512 firsts prime numbers
     */
    public static final int[] PRIMES = {2,
            3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,
            79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,
            181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,
            293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,
            421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541,547,
            557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,
            673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,
            821,823,827,829,839,853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,
            953,967,971,977,983,991,997,1009,1013,1019,1021,1031,1033,1039,1049,1051,1061,1063,1069,1087,
            1091,1093,1097,1103,1109,1117,1123,1129,1151,1153,1163,1171,1181,1187,1193,1201,1213,1217,1223,1229,
            1231,1237,1249,1259,1277,1279,1283,1289,1291,1297,1301,1303,1307,1319,1321,1327,1361,1367,1373,1381,
            1399,1409,1423,1427,1429,1433,1439,1447,1451,1453,1459,1471,1481,1483,1487,1489,1493,1499,1511,1523,
            1531,1543,1549,1553,1559,1567,1571,1579,1583,1597,1601,1607,1609,1613,1619,1621,1627,1637,1657,1663,
            1667,1669,1693,1697,1699,1709,1721,1723,1733,1741,1747,1753,1759,1777,1783,1787,1789,1801,1811,1823,
            1831,1847,1861,1867,1871,1873,1877,1879,1889,1901,1907,1913,1931,1933,1949,1951,1973,1979,1987,1993,
            1997,1999,2003,2011,2017,2027,2029,2039,2053,2063,2069,2081,2083,2087,2089,2099,2111,2113,2129,2131,
            2137,2141,2143,2153,2161,2179,2203,2207,2213,2221,2237,2239,2243,2251,2267,2269,2273,2281,2287,2293,
            2297,2309,2311,2333,2339,2341,2347,2351,2357,2371,2377,2381,2383,2389,2393,2399,2411,2417,2423,2437,
            2441,2447,2459,2467,2473,2477,2503,2521,2531,2539,2543,2549,2551,2557,2579,2591,2593,2609,2617,2621,
            2633,2647,2657,2659,2663,2671,2677,2683,2687,2689,2693,2699,2707,2711,2713,2719,2729,2731,2741,2749,
            2753,2767,2777,2789,2791,2797,2801,2803,2819,2833,2837,2843,2851,2857,2861,2879,2887,2897,2903,2909,
            2917,2927,2939,2953,2957,2963,2969,2971,2999,3001,3011,3019,3023,3037,3041,3049,3061,3067,3079,3083,
            3089,3109,3119,3121,3137,3163,3167,3169,3181,3187,3191,3203,3209,3217,3221,3229,3251,3253,3257,3259,
            3271,3299,3301,3307,3313,3319,3323,3329,3331,3343,3347,3359,3361,3371,3373,3389,3391,3407,3413,3433,
            3449,3457,3461,3463,3467,3469,3491,3499,3511,3517,3527,3529,3533,3539,3541,3547,3557,3559,3571,3581,
            3583,3593,3607,3613,3617,3623,3631,3637,3643,3659,3671};
    /**
     * The last number in PRIMES
     */
    public static final int PRIMES_LAST=PRIMES[PRIMES.length-1];

    /**
     * Primality test
     * @param n number to test
     * @return true is n is prime. (All numbers <2 return false)
     */
    public static boolean isPrime(int n){
        if(n<2) return false;

        for(int p:PRIMES){
            if(0==(n%p)) return n==p;
        }
        return millerRabinPrimeTest(n);
    }

    /**
     * Return the prime superior or equal to n
     * @param n a positive number
     * @return
     */
    public static int nextPrime(int n){
        if(n<0) throw new IllegalArgumentException("n must be positive, n="+n);
        if(n==2) return 2;
        n=n|1;//make sur n is odd
        if(n==1) return 2;
        if(n==3) return 3;

        //prepare entry in the +2, +4 loop:
        //n should not be a multiple of 3
        final int rem=n%3;
        if(0==rem)// if n%3==0
            n+=2;//n%3==2
        else if(1==rem){//if n%3==1
            if(isPrime(n)) return n;
            n+=4;//n%3==2
        }
        while(true){ //this loop skips all multiple of 3
            if(isPrime(n)) return n;
            n+=2;//n%3==1
            if(isPrime(n)) return n;
            n+=4;//n%3==2
        }
    }

    /**
     * factorization by trial division: not efficient for large prime numbers
     * @param n number to factorize: must be >=2
     * @return list of prime factors of n
     */
    public static List<Integer> primeFactors(int n) {
        if(n<2) throw new IllegalArgumentException("n must be >=2. n="+n);
        //List<Integer> out= trialDivision(n);
        //List<Integer> out=primeFactorsPollardRho(n);
        List<Integer> out=PollardRho.primeFactors(n);
        //List<Integer> out=primeFactorsFermat1(n);
        //List<Integer> out=primeFactorsFermat2(n);
        return out;
    }


    /**
     * factorization by trial division: not efficient for large prime numbers
     * @param n number to factorize: must be >=2
     * @return list of prime factors of n
     */
    protected static List<Integer> trialDivision(int n){
        ArrayList<Integer> factors = new ArrayList<Integer>(32);
        while(0==n%2){n=n/2;factors.add(2);}
        while(0==n%3){n=n/3;factors.add(3);}
        int f=5;
        while (f*f <= n) {
            while(0==n%f){n=n/f;factors.add(f);}
            f+=2;
            while(0==n%f){n=n/f;factors.add(f);}
            f+=4;
        }
        if(n!=1) factors.add(n);
        return factors;
    }

    /**
     * Miller-Rabin probabilistic primality test for int type, used in such a way that result is always guaranteed
     * according to Handbook of applied cryptography by Menezes, algorithm 4.24
     * it uses the prime numbers as successive base therefore it is garanteed to be always correct.
     * @param n number to test: an odd integer >= 3
     * @return true if n is prime. false if n is definitely composite.
     */
    protected static boolean millerRabinPrimeTest(int n){
        final int nMinus1=n-1;
        final int s=Integer.numberOfTrailingZeros(nMinus1);
        final int r=nMinus1>>s;
        assert(1==r%2);//r must be odd
        int t=1;
        if(n>=2047) t=2;
        if(n>=1373653) t=3;
        if(n>=25326001) t=4;//works up to 3.2 billion, int range stops at 2.7 so we are safe :-)
        BigInteger br= BigInteger.valueOf(r);
        BigInteger bn=BigInteger.valueOf(n);

        for(int i=1;i<=t;i++){
            BigInteger a=BigInteger.valueOf(PRIMES[i-1]);
            BigInteger bPow=a.modPow(br,bn);
            int y=bPow.intValue();
            if((1!=y) && (y!=nMinus1)){
                int j=1;
                while((j<=s-1) && (nMinus1!=y)){
                    long square=((long)y)*y;
                    y=(int)(square % n);
                    if(1==y) return false;//definitely composite
                    j++;
                }
                if(nMinus1!=y) return false;//definitely composite
            }
        }
        return true;//definitely prime
    }

    protected static List<Integer> primeFactorsFermat1(int n){
        ArrayList<Integer> factors = new ArrayList<Integer>(32);
        for(int p:PRIMES){
            while(0==n%p){n=n/p;factors.add(p);}
        }
        if(1==n) return factors;
        if(millerRabinPrimeTest(n)){
            factors.add(n);
            return factors;
        }
        primeFactorsFermatCore(n,factors);
        return factors;
    }
    private static void primeFactorsFermatCore(int n,ArrayList<Integer> factors){
        int[] f=fermatFactorsOpt(n);
        assert(f[0]>=1);
        assert(f[1]>1);
        assert(f[1]>=f[0]);

        if(1==f[0]) factors.add(f[1]);
        else if(f[0]==f[1]){
            ArrayList<Integer> rootFactors=new ArrayList<Integer>(32);
            primeFactorsFermatCore(f[0],rootFactors);
            factors.addAll(rootFactors);
            factors.addAll(rootFactors);
        } else {
            primeFactorsFermatCore(f[0],factors);
            primeFactorsFermatCore(f[1],factors);
        }
    }
    /* basic fermat algorithm
        FermatFactor(N): // N should be odd
            a ← ceil(sqrt(N))
            b2 ← a*a - N
            while b2 isn't a square:
                a ← a + 1    // equivalently: b2 ← b2 + 2*a + 1
                b2 ← a*a - N //               a ← a + 1
            endwhile

            return a - sqrt(b2) // or a + sqrt(b2)
        */
    private static int[] fermatFactors(final int n){
        int[] factors=new int[2];
        int a=(int)Math.ceil(Math.sqrt(n));
        int minB2 = (int)(((long)a*a)-n);
        long b2=minB2;
        if(isSquare(b2)){
            int b=(int)Math.sqrt(b2);
            factors[0]=a-b;
            factors[1]=a+b;
            return factors;
        }
        while(true){
            a++;
            long a2=((long)a*a);
            b2=a2-n;
            int b=(int)Math.sqrt(b2);
            final long prod=((long)b)*b;
            if(prod==b2){
                final int f0=a-b;
                final int f1=a+b;
                factors[0]=f0;
                factors[1]=f1;
                return factors;
            }
            assert(b2>minB2);
        }
    }
    private static int[] fermatFactorsOpt(final int n){
        int[] factors=new int[2];
        int a=(int)Math.ceil(Math.sqrt(n));
        int minB2 = (int)(((long)a*a)-n);
        long b2=minB2;
        if(isSquare(b2)){
            int b=(int)Math.sqrt(b2);
            factors[0]=a-b;
            factors[1]=a+b;
            return factors;
        }
        int lastF0=Integer.MAX_VALUE;
        while(true){
            a++;
            long a2=((long)a*a);
            b2=a2-n;
            int b=(int)Math.sqrt(b2);
            final long prod=((long)b)*b;
            int f0=a-b;
            if(prod==b2){
                final int f1=a+b;
                factors[0]=f0;
                factors[1]=f1;
                return factors;
            }
            if(lastF0-f0<=4){ //better go with trial division
                if(0==(f0%2)) f0--;
                else f0-=2;
                while (f0 > PRIMES_LAST) {
                    if(0==n%f0){
                        factors[0]=f0;
                        factors[1]=n/f0;
                        return factors;
                    }
                    f0-=2;
                }
                factors[0]=1;
                factors[1]=n;
                return factors;
            }
            lastF0=f0;
            assert(b2>minB2);
        }
    }
    protected static boolean isSquare(long n2){
        long n=(long)Math.sqrt(n2);
        boolean out = n*n == n2;
        return out;
    }
    protected static List<Integer> primeFactorsFermat2(int n){
        ArrayList<Integer> factors = new ArrayList<Integer>(32);
        for(int p:PRIMES){
            while(0==n%p){n=n/p;factors.add(p);}
        }
        if(1==n) return factors;
        primeFactorsFermatCore2(n, factors);
        return factors;
    }
    private static void bigPrimeFactors(int n, List<Integer> factors){
        primeFactorsFermatCore2(n,factors);
    }
    private static void primeFactorsFermatCore2(int n,List<Integer> factors){
        if(millerRabinPrimeTest(n)){
            factors.add(n);
            return;
        }
        int[] f=fermatFactorsOpt2(n, factors);
        if(0==f[0]) return;
        assert(f[1]>1);
        assert(f[1]>=f[0]);

        /*if(f[0]==f[1]){
            ArrayList<Integer> rootFactors=new ArrayList<Integer>(32);
            bigPrimeFactors(f[0],rootFactors);
            factors.addAll(rootFactors);
            factors.addAll(rootFactors);
        } else {
            bigPrimeFactors(f[0],factors);
            bigPrimeFactors(f[1],factors);
        }  */
        //because we deal only with int, and because we tried all prime bigger than the cubic square of Integer.MAX_VALUE
        //we know for sur that n is either a prime or a semi prime: it has at most, two factors, so no need for recursion
        factors.add(f[0]);
        factors.add(f[1]);
    }
    private static int[] fermatFactorsOpt2(final int n,List<Integer> primeFactors){
        int[] factors=new int[2];
        int a=(int)Math.ceil(Math.sqrt(n));
        int minB2 = (int)(((long)a*a)-n);
        long b2=minB2;
        if(isSquare(b2)){
            int b=(int)Math.sqrt(b2);
            factors[0]=a-b;
            factors[1]=a+b;
            return factors;
        }
        int lastF0=Integer.MAX_VALUE;
        while(true){
            a++;
            long a2=((long)a*a);
            b2=a2-n;

            int b=(int)Math.sqrt(b2);
            final long prod=((long)b)*b;
            int f0=a-b;
            if(prod==b2){
                final int f1=a+b;
                factors[0]=f0;
                factors[1]=f1;
                return factors;
            }
            if(lastF0-f0<=4){ //better go with trial division
                if(0==(f0%2)) f0--;
                else f0-=2;
                boundedTrialDivision(n,f0,primeFactors);
                return factors;
            }
            lastF0=f0;
            assert(b2>minB2);
        }
    }
    //private static int[] computeDeltasMod9(final int n){}
    private static int[] fermatFactorsOpt2_2(final int n,List<Integer> primeFactors){
        int[] factors=new int[2];
        int a=(int)Math.ceil(Math.sqrt(n));
        int minB2 = (int)(((long)a*a)-n);
        long b2=minB2;
        if(isSquare(b2)){
            int b=(int)Math.sqrt(b2);
            factors[0]=a-b;
            factors[1]=a+b;
            return factors;
        }
        int lastF0=Integer.MAX_VALUE;

        int nMod9=n % 9;
        int[] squareMod9 = new int[]{0,1,4,7};
        ArrayList<Integer> a2Mod9List= new ArrayList<Integer>(4);
        for(int c:squareMod9){
            int res=(c-nMod9+9)%9;
            switch(res){
                case 0:
                case 1:
                case 4:
                case 7:
                    a2Mod9List.add(c);
                    break;
            }
        }
        if(a2Mod9List.size()>1){
            System.out.println("n="+n+": a2Mod9List.size()="+a2Mod9List.size());
            System.exit(0);
        }
        int a2Mod9Expected=a2Mod9List.get(0);

        HashSet<Integer> aMod9Set= new HashSet<Integer>(4);
        for(int c=0;c<9;c++){
            int res=(c*c)%9;
            if(res==a2Mod9Expected) aMod9Set.add(c);
        }
        ArrayList<Integer> aMod9List= new ArrayList<Integer>(4);
        aMod9List.addAll(aMod9Set);
        Collections.sort(aMod9List);
        final int aDeltasSize=aMod9Set.size();
        int[] aDeltas= new int[aDeltasSize];
        for(int i=0;i<aDeltasSize;i++){
            int d=aMod9List.get((i+1)%aDeltasSize)-aMod9List.get(i);
            if(d<0)
                d+=9;
            assert(d>0);
            aDeltas[i]=d;
        }
        a++;
        while(!aMod9Set.contains(a%9)) a++;
        int aDeltasIndex=aMod9List.indexOf(a%9);

        while(true){
            long a2=((long)a*a);
            b2=a2-n;
            int b=(int)Math.sqrt(b2);
            final long prod=((long)b)*b;
            int f0=a-b;
            if(prod==b2){
                final int f1=a+b;
                factors[0]=f0;
                factors[1]=f1;
                return factors;
            }
            if(lastF0-f0<=4){ //better go with trial division
                if(0==(f0%2)) f0--;
                else f0-=2;
                boundedTrialDivision(n,f0,primeFactors);
                //primeFactorsPollardRhoCore(n,primeFactors);
                return factors;
            }
            lastF0=f0;
            assert(b2>minB2);

            a+=aDeltas[aDeltasIndex];
            aDeltasIndex=(aDeltasIndex+1) % aDeltasSize;
        }
    }
    //way slower than Math.sqrt
    private static int sqrt1(long n){
        long op = n;
        long res = 0;
        // "one" starts at the highest power of four <= than the argument.
        long one = Long.highestOneBit(n);
        if(Long.numberOfTrailingZeros(one)%2!=0) one = one>>1;//one must be a power of 4
        while (one > op) one >>= 2;

        while (one != 0) {
            if (op >= res + one) {
                op = op - (res + one);
                res = res +  2 * one;
            }
            res /= 2;
            one /= 4;
        }
        /*long expected= (long)Math.sqrt(n);
        if(expected!=res){
            System.out.println("n="+n+", res="+res+", expected="+expected);
        }                                                                                                     */
        return (int)res;
    }
    //way slower than Math.sqrt
    protected static int sqrt2(long n){
        //int expected= (int)Math.sqrt(n);
        int msbPos=64-Long.numberOfLeadingZeros(n);
        int resMsbPos=msbPos/2;
        if(0==msbPos%2) resMsbPos--;
        int res = 1<<resMsbPos;

        int bitPos=resMsbPos-1;
        while(bitPos>=0){
            int candidate = res + (1<<bitPos);
            if(n>=candidate*candidate){
                res=candidate;
            }
            bitPos--;
        }
        /*if(expected!=res){
            System.out.println("n="+n+", res="+res+", expected="+expected);
        } */
        return res;
    }
    /*protected static int sqrt(long n){
        int expected= (int)Math.sqrt(n);
        return expected;
    } */
    protected static int smallTrialDivision(int n,List<Integer> factors){
        for(int p:PRIMES){
            while(0==n%p){n=n/p;factors.add(p);}
        }
        return n;
    }
    protected static int boundedTrialDivision(int n, int maxFactor,List<Integer> factors){
        int f=PRIMES_LAST+2;
        assert(n>=f);
        while (f <= maxFactor) {
            while(0==n%f){n=n/f;factors.add(f);}
            f+=4;
            while(0==n%f){n=n/f;factors.add(f);}
            f+=2;
        }
        if(n!=1) factors.add(n);
        return n;
    }


    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE  = new BigInteger("1");
    private final static BigInteger TWO  = new BigInteger("2");
    private final static Random random = new Random(0);
    //private final static BigInteger PR_cst  = BigInteger.valueOf(1);

    public static BigInteger rho(BigInteger N) {
        BigInteger divisor;
        //BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger PR_cst  = BigInteger.valueOf(PRIMES_LAST);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        int trial=0;
        while(true){
            do {
                x  =  x.multiply(x).mod(N).add(PR_cst).mod(N);
                xx = xx.multiply(xx).mod(N).add(PR_cst).mod(N);
                xx = xx.multiply(xx).mod(N).add(PR_cst).mod(N);
                divisor = x.subtract(xx).gcd(N);
            } while((divisor.compareTo(ONE)) == 0);
            trial++;
            if(divisor.compareTo(N)==0) {
                //throw new RuntimeException("n="+N);
                PR_cst  = PR_cst.add(TWO);
            }else break;
        }
        maxTrial=Math.max(maxTrial,trial);

        return divisor;
    }
    public static int maxTrial=0;
    static int depth=0;
    protected static void primeFactorsPollardRhoCore(BigInteger N, List<Integer> factors) {
        if (N.compareTo(ONE) == 0) return;
        //if (N.isProbablePrime(20)) { System.out.println(N); return; }
        if(millerRabinPrimeTest(N.intValue())){
            factors.add(N.intValue());
            return;
        }
        depth++;
        if(depth>32) throw new RuntimeException("n="+N);
        BigInteger divisor = rho(N);
        primeFactorsPollardRhoCore(divisor,factors);
        primeFactorsPollardRhoCore(N.divide(divisor),factors);
        depth--;
    }
    public static int rho(int n) {
        int divisor;
        int PR_cst  = PRIMES_LAST;
        long x  = n>>2;//Math.abs(random.nextInt(n));  //keep things deterministic
        long xx = x;

        int trial=0;
        while(true){
            do {
                x  =  (x*x+ PR_cst)%n;
                xx  =  (xx*xx+ PR_cst)%n;
                xx  =  (xx*xx+ PR_cst)%n;

                long diff=x-xx;
                if(0>diff){
                    //diff minimum should be -n +1 so it is always > Integer.MIN_VALUE
                    assert(diff!=Integer.MIN_VALUE);//if not we cannot fit the absolute value in an int.
                    diff=-diff;
                }
                //if(diff>Integer.MAX_VALUE) throw new RuntimeException("n="+n+", diff="+diff);

                //divisor = BigInteger.valueOf(x - xx).gcd(N).intValue();
                divisor=Gcd.binaryGcd((int)diff,n);
                //if(expected!=divisor) throw new RuntimeException("n="+n+", diff="+diff+", divisor="+divisor+", expected="+expected);
            } while(divisor==1);
            trial++;
            if(divisor==n) {
                PR_cst  = PR_cst + PRIMES_LAST;
            }else break;
        }
        maxTrial=Math.max(maxTrial,trial);

        return divisor;
    }
    protected static void primeFactorsPollardRhoCore(int n, List<Integer> factors) {
        if (1==n) return;
        if(millerRabinPrimeTest(n)){
            factors.add(n);
            return;
        }
        //depth++;
        //if(depth>32) throw new RuntimeException("n="+n);
        int divisor = rho(n);
        //bigPrimeFactors(divisor,factors);
        //bigPrimeFactors(n/divisor,factors);
        factors.add(divisor);
        factors.add(n/divisor);
        //depth--;
    }
    protected static List<Integer> primeFactorsPollardRho(int n) {
        try{
            List<Integer> factors = new ArrayList<Integer>(32);
            for(int p:PRIMES){
                while(0==n%p){n=n/p;factors.add(p);}
            }
            if(1==n) return factors;
            //primeFactorsPollardRhoCore(BigInteger.valueOf(n),factors);
            primeFactorsPollardRhoCore(n,factors);
            return factors;
        }catch(Throwable e){
            System.out.println(depth);
            throw new RuntimeException(e);
        }
    }

}
