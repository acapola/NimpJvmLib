package uk.co.nimp.math;

import junit.framework.TestCase;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: seb
 * Date: 7/28/12
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrimesDebug extends TestCase {

    public PrimesDebug(String name) {
        super(name);
    }

    public List<Integer> fermatVsTrialDiv(int n){
        try{
            List<Integer> fermat=Primes.primeFactorsFermat2((int)n);
            Collections.sort(fermat);
            List<Integer> trialDiv=Primes.trialDivision((int)n);
            if(fermat.size()!=trialDiv.size())
                System.out.println("Mismatch for n="+n+", fermat size "+fermat.size()+", TrialDiv size "+trialDiv.size());
            for(int j=0;j<fermat.size();j++){
                int f=fermat.get(j);
                int t=trialDiv.get(j);
                if(f!=t){
                    System.out.println("Mismatch for n="+n+", fermat say "+f+", TrialDiv say "+t);
                }
            }
            return fermat;
        }catch(Throwable e){
            System.out.println();
            System.out.println("Exception happened while n="+n);
            System.out.println();
            throw new RuntimeException(e);
        }
    }

    public List<Integer> fermatVsLehman(int n){
        try{
            List<Integer> fermat=Primes.primeFactorsFermat2((int)n);
            Collections.sort(fermat);
            List<Integer> lehman=Lehman.primeFactors((int)n);
            Collections.sort(lehman);
            if(fermat.size()!=lehman.size())
                System.out.println("Mismatch for n="+n+", fermat size "+fermat.size()+", lehman size "+lehman.size());
            for(int j=0;j<fermat.size();j++){
                int f=fermat.get(j);
                int t=lehman.get(j);
                if(f!=t){
                    System.out.println("Mismatch for n="+n+", fermat say "+f+", lehman say "+t);
                }
            }
            return fermat;
        }catch(Throwable e){
            System.out.println();
            System.out.println("Exception happened while n="+n);
            System.out.println();
            throw new RuntimeException(e);
        }
    }

    public List<Integer> fermatVsX(int n){
        try{
            List<Integer> fermat=Primes.primeFactorsFermat2((int)n);
            Collections.sort(fermat);
            List<Integer> primes=Primes.primeFactors((int)n);
            Collections.sort(primes);
            if(fermat.size()!=primes.size())
                System.out.println("Mismatch for n="+n+", fermat size "+fermat.size()+", Primes size "+primes.size());
            for(int j=0;j<fermat.size();j++){
                int f=fermat.get(j);
                int t=primes.get(j);
                if(f!=t){
                    System.out.println("Mismatch for n="+n+", fermat say "+f+", Primes say "+t);
                }
            }
            return fermat;
        }catch(Throwable e){
            System.out.println();
            System.out.println("Exception happened while n="+n);
            System.out.println();
            throw new RuntimeException(e);
        }
    }

    /*public void testSqrt(){
        assertEquals(135,Primes.sqrt(18225));
    }     */

    public void _test2_4(){
        int f=7;
        while (f <= 100) {
            assert((f+2)%3==0);
            f+=4;
            System.out.println(f+", "+f%3+", "+f%5);
            f+=2;
            System.out.println(f+", "+f%3+", "+f%5);
        }
    }

    public void _testSharedGcd(){
        final int p=12433;
        final int q=12959;
        final int n=p*q;
        BigInteger prod=BigInteger.valueOf(p);
        for(int i=0;i<20;i++){
            prod=prod.multiply(BigInteger.valueOf(p+45*i+1));
        }
        assertEquals(p,prod.gcd(BigInteger.valueOf(n)).intValue());
        int prodModN=p;
        for(int i=0;i<20;i++){
            prodModN=(int)((((long)prodModN) * (p+45*i+1))%n);
        }

        assertEquals(p,Gcd.gcd(prodModN,n));

        prodModN=(int)((((long)prodModN) * (p))%n); //it does not matter if the same factor is inserted twice
        assertEquals(p,Gcd.gcd(prodModN,n));

        prodModN=(int)((((long)prodModN) * (q))%n); //if the second factor is inserted, the product becomes 0
        assertEquals(0,prodModN);
        assertEquals(n,Gcd.gcd(prodModN,n));
    }

    public void testFermat(){
        fermatVsTrialDiv(2146483686);
        fermatVsTrialDiv(1073741341);
        fermatVsTrialDiv(2146483709);
    }
    public void _testLehman(){
        fermatVsLehman(2146697288);
        fermatVsLehman(2146483649);
        fermatVsLehman(2146483686);
        fermatVsLehman(1073741341);
        fermatVsLehman(2146483709);
    }
    public void testPrimeFactors(){
        //Primes.primeFactors(1073741341);
        fermatVsX(2146486227);
        fermatVsX(2146483671);
        fermatVsX(2146697288);
        fermatVsX(2146483649);
        fermatVsX(2146483686);
        fermatVsX(1073741341);
        fermatVsX(2146483709);
        //assertEquals(5,Lehman.primeFactors(32).size());
        //assertEquals(10,Lehman.primeFactors(2146483648).size());
        //Lehman.primeFactors(23220);
    }

    public void testFermatVsX(){
        long checksum=0;
        long isPrimeChecksum=0;
        long start=System.nanoTime();

        for(long i=Integer.MAX_VALUE-PrimesTest.SPEEDTEST_PARAMS[0];i<=Integer.MAX_VALUE;i++){
            List<Integer> factors=fermatVsX((int)i);
            //List<Integer> factors=Primes.primeFactors((int)i);
            checksum+=PrimesTest.sum(factors);
            isPrimeChecksum+= factors.size()==1 ? i : 0;
        }

        long end=System.nanoTime();
        long tns=end-start;
        long tms=(tns+500000)/1000000;
        long ts=(tms+500)/1000;
        System.out.println("exec time="+ts+"s, ("+tms+"ms), checksum="+checksum);
        assertEquals(PrimesTest.SPEEDTEST_PARAMS[2],checksum);
        assertEquals(PrimesTest.SPEEDTEST_PARAMS[1],isPrimeChecksum);
    }
}
