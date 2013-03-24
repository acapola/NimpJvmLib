package uk.co.nimp.math;


import junit.framework.TestCase;
import java.lang.Throwable;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrimesTest extends TestCase{

    public PrimesTest(String name) {
        super(name);
    }

    public static final int[] PRIMES = {//primes here have been verified one by one using Dario Alejandro Alpern's tool, see http://www.alpertron.com.ar/ECM.HTM
            2,3,5,7,11,13,17,19,23,29,31,43,47,53,71,73,79,89,97,
            107,137,151,157,271,293,331,409,607,617,683,829,
            1049,1103,1229,1657,
            2039,2053,//around first boundary in miller-rabin
            2251,2389,2473,2699,3271,3389,3449,5653,6449,6869,9067,9091,
            11251,12433,12959,22961,41047,46337,65413,80803,91577,92693,
            118423,656519,795659,
            1373639,1373677,//around second boundary in miller-rabin
            588977,952381,
            1013041,1205999,2814001,
            22605091,
            25325981,25326023,//around third boundary in miller-rabin
            100000007,715827881,
            2147483647//Integer.MAX_VALUE
            };

    public static final int[] NOT_PRIMES = {//composite chosen at random + particular values used in algorithmes such as boundaries for millerRabin
            4,6,8,9,10,12,14,15,16,18,20,21,22,24,25,
            275,
            2037,2041,2045,2046,2047,2048,2049,2051,2055,//around first boundary in miller-rabin
            9095,
            463465,
            1373637,1373641,1373651,1373652,1373653,1373654,1373655,1373673,1373675,1373679,//around second boundary in miller-rabin
            25325979,25325983,25325993,25325997,25325999,25326001,25326003,25326007,25326009,25326011,25326021,25326025,//around third boundary in miller-rabin
            100000005,
            1073741341,1073741823,2147473649,2147483641,2147483643,2147483645,2147483646};

    public static final int[] BELOW_2 = {
            Integer.MIN_VALUE,-1,0,1};

    void assertPrimeFactorsException(int n, Throwable expected){
        try{
            uk.co.nimp.math.Primes.primeFactors(n);
            fail("Exception not thrown");
        }catch(Throwable e){
            assertEquals(expected.getClass(),e.getClass());
            if(expected.getMessage()!=null)
                assertEquals(expected.getMessage(),e.getMessage());
        }
    }
    void assertNextPrimeException(int n, Throwable expected){
        try{
            Primes.nextPrime(n);
            fail("Exception not thrown");
        }catch(Throwable e){
            assertEquals(expected.getClass(),e.getClass());
            if(expected.getMessage()!=null)
                assertEquals(expected.getMessage(),e.getMessage());
        }
    }
    public void testNextPrime(){
        assertEquals(2,Primes.nextPrime(0));
        assertEquals(2,Primes.nextPrime(1));
        assertEquals(2,Primes.nextPrime(2));
        assertEquals(3,Primes.nextPrime(3));
        assertEquals(5,Primes.nextPrime(4));
        assertEquals(5,Primes.nextPrime(5));

        for(int i=0;i<Primes.PRIMES.length-1;i++){
            for(int j=Primes.PRIMES[i]+1;j<=Primes.PRIMES[i+1];j++){
                assertEquals(Primes.PRIMES[i+1],Primes.nextPrime(j));
            }
        }

        assertEquals(Integer.MAX_VALUE,Primes.nextPrime(Integer.MAX_VALUE-10));
        assertEquals(Integer.MAX_VALUE,Primes.nextPrime(Integer.MAX_VALUE-1));
        assertEquals(Integer.MAX_VALUE,Primes.nextPrime(Integer.MAX_VALUE));

        assertNextPrimeException(Integer.MIN_VALUE,new IllegalArgumentException());
        assertNextPrimeException(-1,new IllegalArgumentException());
        assertNextPrimeException(-13,new IllegalArgumentException());
    }

    public void testIsPrime() throws Exception {
        for(int i:BELOW_2)
            assertEquals(false,Primes.isPrime(i));
        for(int i:NOT_PRIMES)
            assertEquals(false,Primes.isPrime(i));
        for(int i:PRIMES)
            assertEquals(true,Primes.isPrime(i));
    }

    public static int sum(List<Integer> numbers){
        int out = 0;
        for(int i:numbers) out+=i;
        return out;
    }
    public static int product(List<Integer> numbers){
        int out = 1;
        for(int i:numbers) out*=i;
        return out;
    }
    static final HashSet<Integer> PRIMES_SET = new HashSet<Integer>();
    static {for(int p:PRIMES) PRIMES_SET.add(p);}
    static void checkPrimeFactors(List<Integer> factors){
        for(int p:factors){
            if(!PRIMES_SET.contains(p)) fail("Not found in primes list: "+p);
        }
    }
    public void testPrimeFactors() throws Exception {
        for(int i:BELOW_2)
            assertPrimeFactorsException(i, new IllegalArgumentException());
        for(int i:NOT_PRIMES){
            List<Integer> factors=Primes.primeFactors(i);
            checkPrimeFactors(factors);
            int prod=product(factors);
            assertEquals(i, prod);
        }
        for(int i:PRIMES)
            assertEquals(i,(int)Primes.primeFactors(i).get(0));
    }
    //final static long[] SPEEDTEST_PARAMS = new long[]{1000,100931706065L,169889691743L}; //verified by trial div
    final static long[] SPEEDTEST_PARAMS = new long[]{1000000,100055859759861L,169250874771251L};//verified by fermat
    public void testIsPrimeSpeed(){
        long checksum=0;
        long start=System.nanoTime();

        for(long i=Integer.MAX_VALUE-SPEEDTEST_PARAMS[0];i<=Integer.MAX_VALUE;i++){
            checksum += Primes.isPrime((int)i) ? i : 0;
            //checksum += trialDivision((int)i).size()==1 ? i : 0;
        }

        long end=System.nanoTime();
        long tns=end-start;
        long tms=(tns+500000)/1000000;
        long ts=(tms+500)/1000;
        System.out.println("exec time="+ts+"s, ("+tms+"ms), checksum="+checksum);
        assertEquals(SPEEDTEST_PARAMS[1],checksum);
    }
    static List<Integer> naivePrimeFactors(int n){
        if (n < 2) throw new IllegalArgumentException("n must be >=2. n=" + n);
        List<Integer> factors = new ArrayList<Integer>(32);
        while(0==n%2){n=n/2;factors.add(2);}
        int f=3;
        int bound = (int)Math.sqrt(n);
        while (f <= bound) {
            if(0==n%f){
                do{n=n/f;factors.add(f);}while(0==n%f);
                bound = (int)Math.sqrt(n);
            }
            f+=2;
        }
        if(n!=1) factors.add(n);
        return factors;
    }
    public static int boundedTrialDivision(int n, int maxFactor, List<Integer> factors) {
        int f = Primes.PRIMES_LAST + 2;
        assert (n >= f);
        while (f <= maxFactor) {
            if (0 == n % f) {
                n = n / f;
                factors.add(f);
                break;
            }
            f += 4;
            if (0 == n % f) {
                n = n / f;
                factors.add(f);
                break;
            }
            f += 2;
        }
        if (n != 1) factors.add(n);
        return n;
    }

    public static List<Integer> trialDivision(int n){
        List<Integer> factors = new ArrayList<Integer>(32);
        n=Primes.smallTrialDivision(n,factors);
        if(1==n) return factors;
        //here we are sure that n is either a prime or a semi prime
        final int bound = (int)Math.sqrt(n);
        boundedTrialDivision(n,bound, factors);
        return factors;
    }
    public void _testPrimeFactorsSpeed(){
        long checksum=0;
        long isPrimeChecksum=0;
        long start=System.nanoTime();

        for(long i=Integer.MAX_VALUE-SPEEDTEST_PARAMS[0];i<=Integer.MAX_VALUE;i++){
            //List<Integer> factors=naivePrimeFactors((int)i);
            List<Integer> factors=trialDivision((int)i);
            //List<Integer> factors=Primes.primeFactors((int)i);
            //List<Integer> factors=Lehman.primeFactors((int)i);
            checksum+=sum(factors);
            isPrimeChecksum+= factors.size()==1 ? i : 0;
        }

        long end=System.nanoTime();
        long tns=end-start;
        long tms=(tns+500000)/1000000;
        long ts=(tms+500)/1000;
        System.out.println("exec time="+ts+"s, ("+tms+"ms), checksum="+checksum);
        assertEquals(SPEEDTEST_PARAMS[1],isPrimeChecksum);
        assertEquals(SPEEDTEST_PARAMS[2],checksum);

        System.out.println("maxTrial="+Primes.maxTrial);
    }
}