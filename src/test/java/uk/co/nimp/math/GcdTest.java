package uk.co.nimp.math;

import junit.framework.TestCase;
//import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: seb
 * Date: 7/30/12
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class GcdTest extends TestCase {

    public GcdTest(String name) {
        super(name);
    }
    static long gcd(int a,int b){
        //return Primes.binaryGcd(a,b);//5
        //return Primes.gcdEuclideIterative(a,b);//5
        //return Primes.gcdEuclideRecursive(a,b); //6
        //return ArithmeticUtils.gcd(a, b);
        return Gcd.gcd(a,b);
        //return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue(); //11s //reference for correctness
        //return Primes.egcd(a,b); //14s / fail on min / max
    }
    public void testGcdSpeed(){
        Random rng=new Random(0);
        long checksum=0;
        long start=System.nanoTime();
        checksum+=gcd(Integer.MIN_VALUE,Integer.MIN_VALUE/2);
        checksum+=gcd(Integer.MIN_VALUE/2,Integer.MIN_VALUE);
        for(int i=0;i<10000;i++) checksum+=gcd(0,Integer.MIN_VALUE);
        for(int i=0;i<10000;i++) checksum+=gcd(Integer.MIN_VALUE,0);
        for(int i=0;i<10000;i++) checksum+=gcd(0,Integer.MAX_VALUE);
        for(int i=0;i<10000;i++) checksum+=gcd(Integer.MAX_VALUE,0);
        for(int i=0;i<10000;i++) checksum+=gcd(Integer.MIN_VALUE,rng.nextInt());
        for(int i=0;i<10000;i++) checksum+=gcd(rng.nextInt(),Integer.MIN_VALUE);
        for(int i=0;i<10000;i++) checksum+=gcd(Integer.MAX_VALUE,rng.nextInt());
        for(int i=0;i<10000;i++) checksum+=gcd(rng.nextInt(),Integer.MAX_VALUE);
        checksum+=gcd(Integer.MAX_VALUE,Integer.MAX_VALUE);
        checksum+=gcd(Integer.MIN_VALUE,Integer.MAX_VALUE);
        checksum+=gcd(Integer.MAX_VALUE,Integer.MIN_VALUE);
        checksum+=gcd(Integer.MIN_VALUE,Integer.MIN_VALUE);
        checksum+=gcd(1<<31,1<<31);
        //assertEquals(3 * (1<<15), gcd(3 * (1<<20), 9 * (1<<15)));
        checksum+=gcd(3 * (1<<20),9 * (1<<15));
        for(int i=0;i<30000000;i++) checksum+=gcd(rng.nextInt(),rng.nextInt());
        long end=System.nanoTime();
        long tns=end-start;
        long tms=(tns+500000)/1000000;
        long ts=(tms+500)/1000;
        System.out.println("exec time="+ts+"s, ("+tms+"ms), checksum="+checksum);
        assertEquals(85908273861751L,checksum);
    }
    public void _testApache(){
        Random rng=new Random(0);
        long checksum=0;
        long start=System.nanoTime();
        checksum+=gcd(0,Integer.MAX_VALUE);
        checksum+=gcd(Integer.MAX_VALUE,0);
        checksum+=gcd(Integer.MAX_VALUE,rng.nextInt());
        for(int i=0;i<10000;i++) checksum+=gcd(rng.nextInt(),Integer.MAX_VALUE);
        checksum+=gcd(Integer.MAX_VALUE,Integer.MAX_VALUE);
        checksum+=gcd(Integer.MIN_VALUE,1<<30);
        checksum+=gcd(1<<30,1<<30);
        checksum+=gcd(3 * (1<<20),9 * (1<<15));
        for(int i=0;i<30000000;i++) checksum+=gcd(rng.nextInt(),rng.nextInt());
        long end=System.nanoTime();
        long tns=end-start;
        long tms=(tns+500000)/1000000;
        long ts=(tms+500)/1000;
        System.out.println("exec time="+ts+"s, ("+tms+"ms), checksum="+checksum);
        assertEquals(9023314441L,checksum);
    }
    public void _testGcdSpeedRnd(){
        Random rng=new Random(0);
        long checksum=0;
        long start=System.nanoTime();
        for(int i=0;i<60000000;i++) checksum+=gcd(rng.nextInt(),rng.nextInt());
        long end=System.nanoTime();
        long tns=end-start;
        long tms=(tns+500000)/1000000;
        long ts=(tms+500)/1000;
        System.out.println("exec time="+ts+"s, ("+tms+"ms), checksum="+checksum);
        assertEquals(652423816L,checksum);
    }
}
