`renameOutput:test`
`createOutputs:impl`
``
/*
        TODO:
        boolsToIntsMemberwise stupid conversion of boolean array into int array of same length
        byteToBools
        bytesToBools short forms + other xsToYs short forms
         */
import uk.co.nimp.util.Conv
import uk.co.nimp.util.Util
import scala.collection.mutable.ListBuffer;

def boolsToArrayDecl(bools: Array[Boolean]) = {
    "new boolean[]{"+Util.arrayToString(bools,", ")+"}"
}
def intToBoolsDecl(src: Int, length: Int) = boolsToArrayDecl(Conv.intToBools(src,0, length))
def intToBoolsBeM0Decl(src: Int, length: Int) = boolsToArrayDecl(Conv.intToBools(src,0, length).reverse)
``
/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package uk.co.nimp.util;
`=test`
import junit.framework.TestCase;

import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Throwable;
import java.lang.reflect.Method;
`=impl`
`all`
import java.lang.String;

/**
 * Static methods to convert a type into another, with endianness and bit ordering awareness.<p>
 * The methods names follow a naming rule:
 * <pre>
 * &ltsource type&gt[source endianness][source bit ordering]To&ltdestination type&gt[destination endianness][destination bit ordering]
 * Source/Destination type fields: either of the following. An 's' added at the end indicate an array
 *  - "bool"
 *  - "byte"
 *  - "int"
 *  - "long"
 *  - "Hex": a String containing hexadecimal digits
 *  - "HexDigit": a Char containing an hexadecimal digit
 * Endianness field: little endian is the default, in this case the field is absent. In case of big endian, the field is "Be".
 * Bit ordering: Lsb0 is the default, in this case the field is absent. In case of Msb0, the field is "M0".
 *
 * Example: intBeM0ToHex convert an int with big endian byte order and Msb0 bit order into its hexadecimal string representation
 * </pre>
 * Most of the methods provide only default encoding for destination, this limits the number of ways to do one thing.
 * Unless you are dealing with data from/to outside of the JVM platform, you should not need to use "Be" and "M0" methods.
 * <p>Development status: work on going, only a part of the little endian, Lsb0 methods implemented so far</p>
 * @author Sebastien Riou
 * @version $Id:$
 * @since
 * gpp timestamp: `TIMESTAMP`
 */


`=impl`public class Conversion{

    private static String getInitializedString(int nChars){
        StringBuffer out=new java.lang.StringBuffer(nChars);
        for(int i=0;i<nChars;i++) out.append('0');
        return out.toString();
    }
`=test`public class ConversionTest extends TestCase{

    public ConversionTest(String name) {
        super(name);
    }

`all`
   /**
     * convert an hexadecimal digit into an int using the default (Lsb0) bit ordering.<p>
     * '1' is converted to 1
     * @param hexDigit the hexadecimal digit to convert
     * @return  an int equals to <code>hexDigit</code>
     */
    `=impl`public static int hexDigitToInt(char hexDigit) {
        switch(hexDigit){
            ``for(i <- 0 to 9){``
                case '`i`': return `i`;
            ``}``
            ``for(i <- 10 to 15){``
                case '`Conv.intToHexDigit(i).toLower`'://fall through
                case '`Conv.intToHexDigit(i)`': return `i`;
            ``}``
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    `=test`public void testHexDigitToInt() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals(`i`,Conversion.hexDigitToInt('`i.toHexString.toUpperCase`'));
            ``if(i>9){``
                assertEquals(`i`,Conversion.hexDigitToInt('`i.toHexString.toLowerCase`'));
            ``}``
        ``}``
    }`all`

   /**
     * convert an hexadecimal digit into an int using the Msb0 bit ordering.<p>
     * '1' is converted to 8
     * @param hexDigit the hexadecimal digit to convert
     * @return  an int equals to <code>hexDigit</code>
     */
    `=impl`public static int hexDigitM0ToInt(char hexDigit) {
        switch(hexDigit){
            ``for(i <- 0 to 9){``
                case '`i`': return 0x`Conv.intToHexDigitM0(i)`;
                ``}``
            ``for(i <- 10 to 15){``
                case '`Conv.intToHexDigit(i).toLower`'://fall through
                case '`Conv.intToHexDigit(i)`': return 0x`Conv.intToHexDigitM0(i)`;
            ``}``
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    `=test`public void testHexDigitM0ToInt() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals(0x`Conv.intToHexDigitM0(i)`,Conversion.hexDigitM0ToInt('`i.toHexString.toUpperCase`'));
            ``if(i>9){``
                assertEquals(0x`Conv.intToHexDigitM0(i)`,Conversion.hexDigitM0ToInt('`i.toHexString.toLowerCase`'));
            ``}``
        ``}``
    }`all`

    /**
     * convert an hexadecimal digit into binary using the default (Lsb0) bit ordering.<p>
     * '1' is converted as follow: (1, 0, 0, 0)
     * @param hexDigit the hexadecimal digit to convert
     * @return  a boolean array with the binary representation of <code>hexDigit</code>
     */
    `=impl`public static boolean[] hexDigitToBools(char hexDigit) {
        switch(hexDigit){
            ``for(i <- 0 to 9){``
                case '`i`': return new boolean[]{`Util.arrayToString(Conv.intToBools(i,0,4),",")`};
            ``}``
            ``for(i <- 10 to 15){``
                case '`Conv.intToHexDigit(i).toLower`'://fall through
                case '`Conv.intToHexDigit(i)`': return new boolean[]{`Util.arrayToString(Conv.intToBools(i,0,4),",")`};
            ``}``
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    `=test`public void testHexDigitToBools() throws Exception {
        ``for(i <- 0 to 15){``
            assertArrayEquals(`intToBoolsDecl(i, 4)`, Conversion.hexDigitToBools('`i.toHexString.toUpperCase`'));
            ``if(i>9){``
                assertArrayEquals(`intToBoolsDecl(i, 4)`, Conversion.hexDigitToBools('`i.toHexString.toLowerCase`'));
            ``}``
        ``}``
    }`all`

    /**
    * Convert an hexadecimal digit into binary using the Msb0 bit ordering.<p>
    * '1' is converted as follow: (0, 0, 0, 1)
    * @param hexDigit the hexadecimal digit to convert
    * @return  a boolean array with the binary representation of <code>hexDigit</code>
    */
    public static boolean[] hexDigitM0ToBools(char hexDigit) {
        switch(hexDigit){
            ``for(i <- 0 to 9){``
                case '`i`': return new boolean[]{`Util.arrayToString(Conv.intToBools(i,0,4).reverse,",")`};
            ``}``
            ``for(i <- 10 to 15){``
                case '`Conv.intToHexDigit(i).toLower`'://fall through
                case '`Conv.intToHexDigit(i)`': return new boolean[]{`Util.arrayToString(Conv.intToBools(i,0,4).reverse,",")`};
            ``}``
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    `=test`public void testHexDigitM0ToBools() throws Exception {
        ``for(i <- 0 to 15){``
            assertArrayEquals(`intToBoolsBeM0Decl(i, 4)`, Conversion.hexDigitM0ToBools('`i.toHexString.toUpperCase`'));
            ``if(i>9){``
                assertArrayEquals(`intToBoolsBeM0Decl(i, 4)`, Conversion.hexDigitM0ToBools('`i.toHexString.toLowerCase`'));
                ``}``
            ``}``
    }`all`

    /**
     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '1'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     */
    `=impl`public static char boolsToHexDigit(boolean[] src){return boolsToHexDigit(src,0);}
    `=test`public void testBoolsToHexDigit() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals('`i.toHexString.toUpperCase`', Conversion.boolsToHexDigit(`intToBoolsDecl(i, 4)`));
            ``}``
    }`all`
   `=impl`/**
     * Convert an array of boolean into a char using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit initial value of the destination char
     * @param dstPos the position of the lsb, in bits, in the result char
     * @param nBools the number of boolean to convert
     * @return a char containing the selected bits
     */
     public static char boolsToHex(
            boolean[] src,
            int srcPos,
            char dstInit,
            int dstPos,
            int nBools){
        if(0==nBools) return dstInit;
        int out = 0;
        if(nBools<4) out = hexDigitToInt(dstInit);
        int shift=0;
        for(int i = 0; i< nBools;i++){
            shift = i*1+dstPos;
            int bits = ((src[i+srcPos]) ? 1 : 0) << shift;
            int mask = 0x1 << shift;
            out = (out & ~mask) | bits;
        }
        if(shift>=4) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 4");
        return intToHexDigit(out);
    }
    /**
         * Convert a char into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
         *
         * @param src the char to convert
         * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position in <code>dst</code> where to copy the result
         * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
         * @return <code>dst</code>
         */
        public static boolean[] hexToBools(
                char src,
                int srcPos,
                boolean[] dst,
                int dstPos,
                int nBools){
            if(0==nBools) return dst;
            int shift=0;
            assert((nBools-1)*1<4-srcPos);
            int srcInt = hexDigitToInt(src);
            for(int i = 0; i< nBools;i++){
                shift = i*1+srcPos;
                int bit = 0x1 & (srcInt >> shift);
                dst[dstPos+i]= (bit!=0);
            }
            return dst;
        } `all`
    /**
     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '1'
     * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
     */
    `=impl`public static char boolsToHexDigit(boolean[] src, int srcPos){
        if(src.length>srcPos+3 && src[srcPos+3]){
            if(src.length>srcPos+2 && src[srcPos+2]){
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return 'F';
                    else return 'E';
                }else{
                    if(src[srcPos]) return 'D';
                    else return 'C';
                }
            }else{
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return 'B';
                    else return 'A';
                }else{
                    if(src[srcPos]) return '9';
                    else return '8';
                }
            }
        }else{
            if(src.length>srcPos+2 && src[srcPos+2]){
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return '7';
                    else return '6';
                }else{
                    if(src[srcPos]) return '5';
                    else return '4';
                }
            }else{
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return '3';
                    else return '2';
                }else{
                    if(src[srcPos]) return '1';
                    else return '0';
                }
            }
        }
    }
    `=test`public void testBoolsToHexDigit_2args() throws Exception {
        boolean[] shortArray = new boolean[]{false,true,true};
        assertEquals('6', Conversion.boolsToHexDigit(shortArray,0));
        assertEquals('3', Conversion.boolsToHexDigit(shortArray,1));
        assertEquals('1', Conversion.boolsToHexDigit(shortArray,2));
        boolean[] longArray = new boolean[]{true,false,true,false,false,true,true};
        assertEquals('5', Conversion.boolsToHexDigit(longArray,0));
        assertEquals('2', Conversion.boolsToHexDigit(longArray,1));
        assertEquals('9', Conversion.boolsToHexDigit(longArray,2));
        assertEquals('C', Conversion.boolsToHexDigit(longArray,3));
        assertEquals('6', Conversion.boolsToHexDigit(longArray,4));
        assertEquals('3', Conversion.boolsToHexDigit(longArray,5));
        assertEquals('1', Conversion.boolsToHexDigit(longArray,6));
    }`all`

    /**
     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     * @warning src.length must be >= 4.
     */
    `=impl`public static char boolsToHexDigitM0_4bits(boolean[] src){return boolsToHexDigitM0_4bits(src,0);}
    `=test`public void testBoolsToHexDigitM0_bits() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals('`i.toHexString.toUpperCase`', Conversion.boolsToHexDigitM0_4bits(`intToBoolsBeM0Decl(i, 4)`));
        ``}``
    }`all`
                
   /**
     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
    *  (1,0,0,1,1,0,1,0) with srcPos = 3 is converted to 'D'
    * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
    * @warning src.length must be 8 at most.
    * @warning srcPos+4 must be <= src.length.
    */
   `=impl`public static char boolsToHexDigitM0_4bits(boolean[] src, int srcPos){
        if(src.length>8) throw new IllegalArgumentException("src.length>8: src.length="+src.length);
        if(src.length-srcPos<4) throw new IllegalArgumentException("src.length-srcPos<4: src.length="+src.length+", srcPos="+srcPos);
        if(src[srcPos+3]){
            if(src[srcPos+2]){
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'F';
                    else return '7';
                }else{
                    if(src[srcPos]) return 'B';
                    else return '3';
                }
            }else{
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'D';
                    else return '5';
                }else{
                    if(src[srcPos]) return '9';
                    else return '1';
                }
            }
        }else{
            if(src[srcPos+2]){
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'E';
                    else return '6';
                }else{
                    if(src[srcPos]) return 'A';
                    else return '2';
                }
            }else{
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'C';
                    else return '4';
                }else{
                    if(src[srcPos]) return '8';
                    else return '0';
                }
            }
        }
    }
    `=test`public void testBoolsToHexDigitM0_4bits_2args() throws Exception {
        //boolean[] shortArray = new boolean[]{true,true,false};
        //assertEquals('6', Conversion.boolsToHexDigitM0(shortArray,0));
        //assertEquals('3', Conversion.boolsToHexDigitM0(shortArray,1));
        //assertEquals('1', Conversion.boolsToHexDigitM0(shortArray,2));
        boolean[] shortArray = new boolean[]{true,true,false,true};
        assertEquals('D', Conversion.boolsToHexDigitM0_4bits(shortArray,0));
        boolean[] longArray = new boolean[]{true,false,true,false,false,true,true};
        assertEquals('A', Conversion.boolsToHexDigitM0_4bits(longArray,0));
        assertEquals('4', Conversion.boolsToHexDigitM0_4bits(longArray,1));
        assertEquals('9', Conversion.boolsToHexDigitM0_4bits(longArray,2));
        assertEquals('3', Conversion.boolsToHexDigitM0_4bits(longArray,3));
        //assertEquals('6', Conversion.boolsToHexDigitM0(longArray,4));
        //assertEquals('3', Conversion.boolsToHexDigitM0(longArray,5));
        //assertEquals('1', Conversion.boolsToHexDigitM0(longArray,6));
        boolean[] maxLengthArray = new boolean[]{true,false,true,false,false,true,true,true};
        assertEquals('A', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,0));
        assertEquals('4', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,1));
        assertEquals('9', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,2));
        assertEquals('3', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,3));
        assertEquals('7', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,4));
        //assertEquals('7', Conversion.boolsToHexDigitM0(longArray,5));
        //assertEquals('3', Conversion.boolsToHexDigitM0(longArray,6));
        //assertEquals('1', Conversion.boolsToHexDigitM0(longArray,7));
        boolean[] javaDocCheck = new boolean[]{true,false,false,true,true,false,true,false};
        assertEquals('D', Conversion.boolsToHexDigitM0_4bits(javaDocCheck,3));

        }`all`

   /**
     * Convert the first 4 bits of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
    *  (1,0,0,0,0,0,0,0,   0,0,0,0,0,1,0,0) is converted to '4'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     */
    `=impl`public static char boolsBeM0ToHexDigit(boolean[] src){return boolsBeM0ToHexDigit(src,0);}
    `=test`public void testBoolsBeM0ToHexDigit() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals('`i.toHexString.toUpperCase`', Conversion.boolsBeM0ToHexDigit(`intToBoolsBeM0Decl(i, 4)`));
        ``}``
        assertEquals('4', Conversion.boolsBeM0ToHexDigit(`intToBoolsBeM0Decl(0x08004, 16)`));
    }`all`

    /**
     * Convert a part of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
     * (1, 0, 0, 0) with srcPos = 0 is converted as follow: '8'
     * (1,0,0,0,0,0,0,0,   0,0,0,1,0,1,0,0) with srcPos = 2 is converted to '5'
     * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
     */
    `=impl`public static char boolsBeM0ToHexDigit(boolean[] src, int srcPos){
        int beSrcPos = src.length-1-srcPos;
        int srcLen = Math.min(4,beSrcPos+1);
        boolean []paddedSrc = new boolean[4];
        System.arraycopy(src,beSrcPos+1-srcLen,paddedSrc,4-srcLen,srcLen);
        src = paddedSrc;
        srcPos=0;
        if(src[srcPos]){
            if(src.length>srcPos+1 && src[srcPos+1]){
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return 'F';
                    else return 'E';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return 'D';
                    else return 'C';
                }
            }else{
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return 'B';
                    else return 'A';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return '9';
                    else return '8';
                }
            }
        }else{
            if(src.length>srcPos+1 && src[srcPos+1]){
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return '7';
                    else return '6';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return '5';
                    else return '4';
                }
            }else{
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return '3';
                    else return '2';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return '1';
                    else return '0';
                }
            }
        }
    }
    `=test`public void testBoolsBeM0ToHexDigit_2args() throws Exception {
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(`intToBoolsBeM0Decl(0x08014, 16)`,2));

        boolean[] shortArray = new boolean[]{true,true,false};
        assertEquals('6', Conversion.boolsBeM0ToHexDigit(shortArray,0));
        assertEquals('3', Conversion.boolsBeM0ToHexDigit(shortArray,1));
        assertEquals('1', Conversion.boolsBeM0ToHexDigit(shortArray,2));
        boolean[] shortArray2 = `boolsToArrayDecl((Array(true,false,true,false,false,true,true,true)).reverse)`;
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(shortArray2,0));
        assertEquals('2', Conversion.boolsBeM0ToHexDigit(shortArray2,1));
        assertEquals('9', Conversion.boolsBeM0ToHexDigit(shortArray2,2));
        assertEquals('C', Conversion.boolsBeM0ToHexDigit(shortArray2,3));
        assertEquals('E', Conversion.boolsBeM0ToHexDigit(shortArray2,4));
        assertEquals('7', Conversion.boolsBeM0ToHexDigit(shortArray2,5));
        assertEquals('3', Conversion.boolsBeM0ToHexDigit(shortArray2,6));
        assertEquals('1', Conversion.boolsBeM0ToHexDigit(shortArray2,7));
        boolean[] multiBytesArray = new boolean[]{
             true, true,false,false,    true,false, true,false,
             true, true, true,false,   false, true,false, true
        };
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,0));
        assertEquals('2', Conversion.boolsBeM0ToHexDigit(multiBytesArray,1));
        assertEquals('9', Conversion.boolsBeM0ToHexDigit(multiBytesArray,2));
        assertEquals('C', Conversion.boolsBeM0ToHexDigit(multiBytesArray,3));
        assertEquals('E', Conversion.boolsBeM0ToHexDigit(multiBytesArray,4));
        assertEquals('7', Conversion.boolsBeM0ToHexDigit(multiBytesArray,5));
        assertEquals('B', Conversion.boolsBeM0ToHexDigit(multiBytesArray,6));
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,7));

        assertEquals('A', Conversion.boolsBeM0ToHexDigit(multiBytesArray,8));
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,9));
        assertEquals('2', Conversion.boolsBeM0ToHexDigit(multiBytesArray,10));
        assertEquals('9', Conversion.boolsBeM0ToHexDigit(multiBytesArray,11));
        assertEquals('C', Conversion.boolsBeM0ToHexDigit(multiBytesArray,12));
        assertEquals('6', Conversion.boolsBeM0ToHexDigit(multiBytesArray,13));
        assertEquals('3', Conversion.boolsBeM0ToHexDigit(multiBytesArray,14));
        assertEquals('1', Conversion.boolsBeM0ToHexDigit(multiBytesArray,15));

    }`all`

     /**
        * Convert the 4 lsb of an int to an hexadecimal digit.<p>
        * 0 returns '0'<p>
        * 1 returns '1'<p>
        * 10 returns 'A' and so on...
        * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
        * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
        */
    `=impl`public static char intToHexDigit(int nibble){
        switch(nibble){
            case 0x0: return '0';
            case 0x1: return '1';
            case 0x2: return '2';
            case 0x3: return '3';
            case 0x4: return '4';
            case 0x5: return '5';
            case 0x6: return '6';
            case 0x7: return '7';
            case 0x8: return '8';
            case 0x9: return '9';
            case 0xA: return 'A';
            case 0xB: return 'B';
            case 0xC: return 'C';
            case 0xD: return 'D';
            case 0xE: return 'E';
            case 0xF: return 'F';
            default: throw new java.lang.IllegalArgumentException("nibble value not between 0 and 15: "+nibble);
        }
    }
    `=test`public void testIntToHexDigit() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals('`Conv.intToHexDigit(i)`',Conversion.intToHexDigit(`i`));
        ``}``
    }`all`

    /**
     * Convert the 4 lsb of an int to an hexadecimal digit encoded using the Msb0 bit ordering.<p>
     * 0 returns '0'<p>
     * 1 returns '8'<p>
     * 10 returns '5' and so on...
     * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
     * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
     */
    `=impl`public static char intToHexDigitM0(int nibble){
        switch(nibble){
            case 0x0: return '0';
            case 0x1: return '8';
            case 0x2: return '4';
            case 0x3: return 'C';
            case 0x4: return '2';
            case 0x5: return 'A';
            case 0x6: return '6';
            case 0x7: return 'E';
            case 0x8: return '1';
            case 0x9: return '9';
            case 0xA: return '5';
            case 0xB: return 'D';
            case 0xC: return '3';
            case 0xD: return 'B';
            case 0xE: return '7';
            case 0xF: return 'F';
            default: throw new java.lang.IllegalArgumentException("nibble value not between 0 and 15: "+nibble);
        }
    }
    `=test`public void testIntToHexDigitM0() throws Exception {
        ``for(i <- 0 to 15){``
            assertEquals('`Conv.intToHexDigitM0(i)`',Conversion.intToHexDigitM0(`i`));
        ``}``
    }`all`
    `=impl`
    /**
     * Convert a boolean array into a String using the default (little endian, Lsb0) byte and bit order
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit the initial value of the destination array
     * @param dstPos the position of the lsb, in boolean unit, in the result array
     * @param nBools the number of boolean to convert
     * @return <code>dst</code>, updated with the selected bits
     */
   public static String boolsToHexs(
            boolean[] src,
            int srcPos,
            String dstInit,
            int dstPos,
            int nBools){
        if(0==nBools) return dstInit;
        final int inPerOut = 4;
        int inCnt = 0;
        int inIndex = dstPos % inPerOut;
        int outIndex;
        StringBuffer sb = new StringBuffer(dstInit);
        if(0 != inIndex){
            int l=java.lang.Math.min(inPerOut-inIndex, nBools);
            int bi = dstPos/inPerOut;
            sb.setCharAt(bi,boolsToHex(src,srcPos,dstInit.charAt(bi),inIndex*1,l)); //unaligned start
            inCnt += l;
            outIndex=bi+1;
        } else {
            outIndex=dstPos/inPerOut;
        }
        int nFullOut = (nBools - inCnt)/inPerOut;
        for(int i = 0 ;i< nFullOut;i++){
            sb.setCharAt(outIndex+i,boolsToHex(src,srcPos+inCnt,'0',0,inPerOut));
            inCnt += inPerOut;
        }
        int remaining = nBools-inCnt;
        if(0!=remaining) sb.setCharAt(outIndex+nFullOut,boolsToHex(src,srcPos+inCnt,dstInit.charAt(outIndex+nFullOut),0,remaining));//unaligned end
        return sb.toString();
    }`=test`
    static String newHexs(int size){
        char []out = new char[size];
        java.util.Arrays.fill(out,'0');
        return new String(out);
    }`all`
``
    abstract class JavaType(val name: String,
    val nickName: String,
    val width: Int,
    val granularity: Int,
    val indexesReversed: Boolean){
        def decl(varName: String) = name+" "+varName
        def at(varName: String,index: String) = varName+"["+index+"]"
        def arrayLen(varName:String) = varName+".length"
        def declArray = name+"[]"
        def declArray(varName: String) = name+"[] "+varName
        def declArray(varName: String,  size: Int) = name+"[] "+varName+" = new "+name+"["+size+"];"
        def declArray(varName: String,  size: String) = name+"[] "+varName+" = new "+name+"["+size+"];"
        def declAnonymousArray(size: String) = " new "+name+"["+size+"]"
        def litPostFix = ""
        def mask = "0x"+ java.lang.Long.toHexString((1L << width) - 1)+litPostFix;
        def toNum(varName: String) = varName
        def boxedName = name match{
            case "int" => "Integer"
            case _ => name.capitalize
        }
        def zeroValue = "0"+litPostFix
        def randomValue: String = { //bad randoms here!! (due to sign bit handling...)
            val rnd = new java.util.Random
            val len = width/4;
            val rndLong = math.abs(rnd.nextLong)
            val rndStr = "0000000000000000"+java.lang.Long.toString(rndLong,16)
            literal("0x"+rndStr.substring(rndStr.length-len-1,rndStr.length-1))
        }
        def randomArray(size: Int): String = {
            val sb = new StringBuffer()
            sb.append("{")
            for(i <- 0 until size){
                sb.append(randomValue)
                if(i<size-1) sb.append(", ")
            }
            sb.append("}")
            sb.toString
        }
        def literalHex(lit: Long) = literal("0x"+lit.toHexString)
        def literalDec(lit: Long) = literal(lit.toString)
        def literal(lit: String): String = name match{
            case "int" => lit
            case "long" => lit+"L"
            case "char" => "'"+lit+"'"
            case "Char" => "'"+lit+"'"
            case "boolean" => if(0==lit) "false" else "true"
            case "byte" => "(byte)"+lit
            case "short" => "(short)"+lit
        }
    }
    abstract class BasicType(_name: String,_width: Int) extends JavaType(_name,_name,_width,1,false)
    class bool_t() extends JavaType("boolean","bool",1,1,false){
        override def toNum(varName: String) = "(("+varName+") ? 1 : 0)"
        override def zeroValue = "false"
        override def randomValue: String = {
            val rnd = new java.util.Random
            val rndBool = rnd.nextBoolean
            rndBool.toString
        }

    }
    class hexDigit_t() extends JavaType("char","hex",4,4,true){
        override def at(varName: String,index: String) = varName+".charAt("+index+")"
        override def arrayLen(varName:String) = varName+".length()"
        override def declArray = "String"
        override def declArray(varName: String) = "String "+varName

        override def declArray(varName: String,  size: Int): String = {
            var decl = "String "+varName+" = \""
            for(i <- 0 until size) decl+="0"
            decl+="\";"
            return decl
        }
        override def declArray(varName: String,  size: String) = "String "+varName+" = getInitializedString("+size+");"
        override def declAnonymousArray(size: String) = "newHexs("+size+")"
        override def toNum(varName: String) = "hexDigitToInt("+varName+")"
        override def zeroValue = "'0'"
        override def randomValue: String = {
            val rnd = new java.util.Random
            val len = width/4;
            val rndLong = rnd.nextLong
            val rndStr = java.lang.Long.toString(rndLong,16)
            "'"+rndStr.charAt(rndStr.length-1)+"'"
        }
        override def randomArray(size: Int): String = {
            val sb = new StringBuffer()
            sb.append("\"")
            for(i <- 0 until size){
                sb.append(randomValue.charAt(1))
            }
            sb.append("\"")
            sb.toString
        }
    }
    class byte_t() extends BasicType("byte",8)
    class short_t() extends BasicType("short",16)
    class int_t() extends BasicType("int",32)
    class long_t() extends BasicType("long",64){
        override def litPostFix = "L"
    }
    def xToY(srcType: JavaType, srcName:String,  dstType: JavaType){
        dstType match{
            case t: bool_t => {``
                (`srcName`!=0)``
            }
            case _ => {``
                `srcName`.`dstType.name`Value()``
            }
        }
    }
    def typesToMethodName_ns2w(srcType: JavaType, dstType: JavaType) = srcType.nickName+"sTo"+dstType.nickName.capitalize
    def typesToMethodName_w2ns(srcType: JavaType, dstType: JavaType) = srcType.nickName+"To"+dstType.nickName.capitalize+"s"
    def typesToMethodName_ns2ws(srcType: JavaType, dstType: JavaType) = srcType.nickName+"sTo"+dstType.nickName.capitalize+"s"
    def typesToMethodName_ws2ns(srcType: JavaType, dstType: JavaType) = srcType.nickName+"sTo"+dstType.nickName.capitalize+"s"

    def typeToName_nTypes(aType: JavaType) = "n"+aType.nickName.capitalize+"s"
    val testsToCheck = new ListBuffer[String]

    def convenienceMethods_ns2w(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ns2w(srcType,dstType).capitalize+"ConvenienceMethods")
        val srcPerDst = dstType.width / srcType.width
        ``
        `=test`
        public void test`typesToMethodName_ns2w(srcType,dstType).capitalize`ConvenienceMethods() throws Exception {
            `srcType.declArray("src")` = `srcType.randomArray(srcPerDst)`;
            assertEquals(Conversion.`typesToMethodName_ns2w(srcType,dstType)`(src,1,`dstType.literal("0")`,0,1),Conversion.`typesToMethodName_ns2w(srcType,dstType)`(src,1,1));
            assertEquals(Conversion.`typesToMethodName_ns2w(srcType,dstType)`(src,0,`dstType.literal("0")`,0,1),Conversion.`typesToMethodName_ns2w(srcType,dstType)`(src,1));
            assertEquals(Conversion.`typesToMethodName_ns2w(srcType,dstType)`(src,0,`dstType.literal("0")`,0,`srcPerDst`),Conversion.`typesToMethodName_ns2w(srcType,dstType)`(src));
        }
        `=impl`
        public static `dstType.name` `typesToMethodName_ns2w(srcType,dstType)`(
                `srcType.declArray("src")`,
                int srcPos,
                int dstPos,
                int `typeToName_nTypes(srcType)`){
            return `typesToMethodName_ns2w(srcType,dstType)`(src,srcPos,`dstType.literal("0")`,dstPos,`typeToName_nTypes(srcType)`);
        }
        public static `dstType.name` `typesToMethodName_ns2w(srcType,dstType)`(
                `srcType.declArray("src")`,
                int srcPos,
                int `typeToName_nTypes(srcType)`){
            return `typesToMethodName_ns2w(srcType,dstType)`(src,srcPos,`dstType.literal("0")`,0,`typeToName_nTypes(srcType)`);
        }
        public static `dstType.name` `typesToMethodName_ns2w(srcType,dstType)`(
            `srcType.declArray("src")`,
            int `typeToName_nTypes(srcType)`){
            return `typesToMethodName_ns2w(srcType,dstType)`(src,0,`dstType.literal("0")`,0,`typeToName_nTypes(srcType)`);
        }
`none`
        public static `dstType.name` `typesToMethodName_ns2w(srcType,dstType)`(
                `srcType.declArray("src")`,
                int srcPos){
            return `typesToMethodName_ns2w(srcType,dstType)`(src,srcPos,`dstType.literal("0")`,0,`srcType.arrayLen("src")`-srcPos);
        }
`=impl`
        public static `dstType.name` `typesToMethodName_ns2w(srcType,dstType)`(
                `srcType.declArray("src")`){
            return `typesToMethodName_ns2w(srcType,dstType)`(src,0,`dstType.literal("0")`,0,`srcType.arrayLen("src")`);
        }
        `all```
    }
    def convenienceMethods_ns2ws(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ns2ws(srcType,dstType).capitalize+"ConvenienceMethods")
        val srcPerDst = dstType.width / srcType.width
        val sizeExpr="("+typeToName_nTypes(srcType)+"+"+(srcPerDst-1)+")/"+srcPerDst
        val sizeExpr2="("+srcType.arrayLen("src")+"+"+(srcPerDst-1)+")/"+srcPerDst
        ``
        `=test`
        public void test`typesToMethodName_ns2ws(srcType,dstType).capitalize`ConvenienceMethods() throws Exception {
            `srcType.declArray("src")` = `srcType.randomArray(srcPerDst*2)`;
            `dstType.declArray("dstInitPart",1)`
            `dstType.declArray("dstInitFull",2)`
            assertArrayEquals(Conversion.`typesToMethodName_ns2ws(srcType,dstType)`(src,1,dstInitPart,0,1),Conversion.`typesToMethodName_ns2ws(srcType,dstType)`(src,1,1));
            assertArrayEquals(Conversion.`typesToMethodName_ns2ws(srcType,dstType)`(src,0,dstInitPart,0,1),Conversion.`typesToMethodName_ns2ws(srcType,dstType)`(src,1));
            assertArrayEquals(Conversion.`typesToMethodName_ns2ws(srcType,dstType)`(src,0,dstInitFull,0,`srcPerDst*2`),Conversion.`typesToMethodName_ns2ws(srcType,dstType)`(src));
        }
        `=impl`
        public static `dstType.declArray` `typesToMethodName_ns2ws(srcType,dstType)`(
            `srcType.declArray("src")`,
            int srcPos,
            int dstPos,
            int `typeToName_nTypes(srcType)`){
            `dstType.declArray("dstInit",sizeExpr)`
            return `typesToMethodName_ns2ws(srcType,dstType)`(src,srcPos,dstInit,dstPos,`typeToName_nTypes(srcType)`);
        }
        public static `dstType.declArray` `typesToMethodName_ns2ws(srcType,dstType)`(
            `srcType.declArray("src")`,
            int srcPos,
            int `typeToName_nTypes(srcType)`){
            `dstType.declArray("dstInit",sizeExpr)`
            return `typesToMethodName_ns2ws(srcType,dstType)`(src,srcPos,dstInit,0,`typeToName_nTypes(srcType)`);
        }
        public static `dstType.declArray` `typesToMethodName_ns2ws(srcType,dstType)`(
            `srcType.declArray("src")`,
            int `typeToName_nTypes(srcType)`){
            `dstType.declArray("dstInit",sizeExpr)`
            return `typesToMethodName_ns2ws(srcType,dstType)`(src,0,dstInit,0,`typeToName_nTypes(srcType)`);
        }
        public static `dstType.declArray` `typesToMethodName_ns2ws(srcType,dstType)`(
            `srcType.declArray("src")`){
            `dstType.declArray("dstInit",sizeExpr2)`
            return `typesToMethodName_ns2ws(srcType,dstType)`(src,0,dstInit,0,`srcType.arrayLen("src")`);
        }
    `all```
    }
    def convenienceMethods_w2ns(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_w2ns(srcType,dstType).capitalize+"ConvenienceMethods")
        val dstPerSrc = srcType.width / dstType.width
        ``
        `=test`
        public void test`typesToMethodName_w2ns(srcType,dstType).capitalize`ConvenienceMethods() throws Exception {
                `srcType.name` src = `srcType.randomValue`;
        `dstType.declArray("dstInitPart",1)`
        `dstType.declArray("dstInitFull",dstPerSrc)`
        assertArrayEquals(Conversion.`typesToMethodName_w2ns(srcType,dstType)`(src,1,dstInitPart,0,1),Conversion.`typesToMethodName_w2ns(srcType,dstType)`(src,1,1));
        assertArrayEquals(Conversion.`typesToMethodName_w2ns(srcType,dstType)`(src,0,dstInitPart,0,1),Conversion.`typesToMethodName_w2ns(srcType,dstType)`(src,1));
        assertArrayEquals(Conversion.`typesToMethodName_w2ns(srcType,dstType)`(src,0,dstInitFull,0,`dstPerSrc`),Conversion.`typesToMethodName_w2ns(srcType,dstType)`(src));
        }
        `=impl`
        public static `dstType.declArray` `typesToMethodName_w2ns(srcType,dstType)`(
        `srcType.name` src,
        int srcPos,
        int `typeToName_nTypes(dstType)`){
            `dstType.declArray("dstInit",typeToName_nTypes(dstType))`
            return `typesToMethodName_w2ns(srcType,dstType)`(src,srcPos,dstInit,0,`typeToName_nTypes(dstType)`);
        }
        public static `dstType.declArray` `typesToMethodName_w2ns(srcType,dstType)`(
        `srcType.name` src,
        int `typeToName_nTypes(dstType)`){
            `dstType.declArray("dstInit",typeToName_nTypes(dstType))`
            return `typesToMethodName_w2ns(srcType,dstType)`(src,0,dstInit,0,`typeToName_nTypes(dstType)`);
        }
        public static `dstType.declArray` `typesToMethodName_w2ns(srcType,dstType)`(
        `srcType.name` src){
            `dstType.declArray("dstInit",dstPerSrc)`
            return `typesToMethodName_w2ns(srcType,dstType)`(src,0,dstInit,0,`dstPerSrc`);
        }
        `all```
    }
    def convenienceMethods_ws2ns(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ws2ns(srcType,dstType).capitalize+"ConvenienceMethods")
        val dstPerSrc = srcType.width / dstType.width
        ``
        `=test`
        public void test`typesToMethodName_ws2ns(srcType,dstType).capitalize`ConvenienceMethods() throws Exception {
            `srcType.declArray("src")` = `srcType.randomArray(1)`;
            `dstType.declArray("dstInitPart",1)`
            `dstType.declArray("dstInitFull",dstPerSrc)`
            assertArrayEquals(Conversion.`typesToMethodName_ws2ns(srcType,dstType)`(src,1,dstInitPart,0,1),Conversion.`typesToMethodName_ws2ns(srcType,dstType)`(src,1,1));
            assertArrayEquals(Conversion.`typesToMethodName_ws2ns(srcType,dstType)`(src,0,dstInitPart,0,1),Conversion.`typesToMethodName_ws2ns(srcType,dstType)`(src,1));
            assertArrayEquals(Conversion.`typesToMethodName_ws2ns(srcType,dstType)`(src,0,dstInitFull,0,`dstPerSrc`),Conversion.`typesToMethodName_ws2ns(srcType,dstType)`(src));
        }
        `=impl`
        public static `dstType.declArray` `typesToMethodName_ws2ns(srcType,dstType)`(
            `srcType.declArray("src")`,
            int srcPos,
            int `typeToName_nTypes(dstType)`){
            `dstType.declArray("dstInit",typeToName_nTypes(dstType))`
            return `typesToMethodName_ws2ns(srcType,dstType)`(src,srcPos,dstInit,0,`typeToName_nTypes(dstType)`);
        }
        public static `dstType.declArray` `typesToMethodName_ws2ns(srcType,dstType)`(
            `srcType.declArray("src")`,
            int `typeToName_nTypes(dstType)`){
            `dstType.declArray("dstInit",typeToName_nTypes(dstType))`
            return `typesToMethodName_ws2ns(srcType,dstType)`(src,0,dstInit,0,`typeToName_nTypes(dstType)`);
        }
        public static `dstType.declArray` `typesToMethodName_ws2ns(srcType,dstType)`(
            `srcType.declArray("src")`){
            `dstType.declArray("dstInit",dstPerSrc)`
            return `typesToMethodName_ws2ns(srcType,dstType)`(src,0,dstInit,0,`dstPerSrc`);
        }
        `all```
    }
    
    def narrowsToWide(srcType: JavaType, dstType: JavaType){
        val sMask = srcType.mask+dstType.litPostFix
        val nS = typeToName_nTypes(srcType)
        val methodName = srcType.nickName+"sTo"+dstType.nickName.capitalize
        testsToCheck.append("test"+methodName.capitalize)
    ``
    `=impl`/**
     * Convert an array of `srcType.name` into a `dstType.name` using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the `srcType.name` array to convert
     * @param srcPos the position in <code>src</code>, in `srcType.name` unit, from where to start the conversion
     * @param dstInit initial value of the destination `dstType.name`
     * @param dstPos the position of the lsb, in bits, in the result `dstType.name`
     * @param `nS` the number of `srcType.name` to convert
     * @return a `dstType.name` containing the selected bits
    */
    public static `dstType.name` `methodName`(
        `srcType.declArray("src")`,
        int srcPos,
        `dstType.name` dstInit,
        int dstPos,
        int `nS`){
        if(0==`nS`) return dstInit;
        `dstType.name` out = dstInit;
        int shift=0;
        for(int i = 0; i< `nS`;i++){
            shift = i*`srcType.width`+dstPos;
            `dstType.decl("bits")` = (`dstType.name`)(((`dstType.name`)(`sMask` & `srcType.toNum(srcType.at("src","i+srcPos"))`)) << shift);
            `dstType.decl("mask")` = (`dstType.name`)(((`dstType.name`) `sMask`) << shift);
            out = (`dstType.name`)((out & ~mask) | bits);
        }
        if(shift>=`dstType.width`) throw new IllegalArgumentException("(`nS`-1)*`srcType.width`+dstPos is greather or equal to than `dstType.width`");
        return out;
    }
    `all```}


    def narrowsToWides(srcType: JavaType, dstType: JavaType){
        val nS = "n"+srcType.nickName.capitalize+"s"
        val narrowsToWideMethodName = srcType.nickName+"sTo"+dstType.nickName.capitalize
        val methodName = narrowsToWideMethodName+"s"
        testsToCheck.append("test"+methodName.capitalize)
        ``
        `=impl`/**
         * Convert a byte array into a `dstType.name` array using the default (little endian, Lsb0) byte and bit order
         * @param src the `srcType.name` array to convert
         * @param srcPos the position in <code>src</code>, in `srcType.name` unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in `srcType.name` unit, in the result array
         * @param `nS` the number of `srcType.name` to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static `dstType.declArray` `methodName`(
        `srcType.declArray("src")`,
        int srcPos,
        `dstType.declArray("dst")`,
        int dstPos,
        int `nS`){
            if(0==`nS`) return dst;
            final int inPerOut = `dstType.width/srcType.width`;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, `nS`);
                int bi = dstPos/inPerOut;
                dst[bi]=`narrowsToWideMethodName`(src,srcPos,dst[bi],inIndex*`srcType.width`,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (`nS` - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=`narrowsToWideMethodName`(src,srcPos+inCnt,`dstType.literalDec(0)`,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = `nS`-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=`narrowsToWideMethodName`(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
        `all```}

    def wideToNarrows(srcType: JavaType, dstType: JavaType){
        val sMask = dstType.mask+dstType.litPostFix
        val nD = "n"+dstType.nickName.capitalize+"s"
        val methodName = srcType.nickName+"To"+dstType.nickName.capitalize+"s"
        testsToCheck.append("test"+methodName.capitalize)
        ``
        `=impl`
    ``dstType match{
            case c: hexDigit_t => {
        ``
        /**
                 * Convert a `srcType.name` into an array of  `dstType.name` using the default (little endian, Lsb0) byte and bit ordering.<p>
                 *
                 * @param src the `srcType.name` to convert
                 * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
                 * @param dstInit the initial value for the result String
                 * @param dstPos the position in <code>dst</code> where to copy the result
                 * @param `nD` the number of `dstType.name` to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
                 * @return <code>dst</code>
                 */
        public static `dstType.declArray` `methodName`(
            `srcType.name` src,
            int srcPos,
            `dstType.declArray("dstInit")`,
            int dstPos,
            int `nD`){
                if(0==`nD`) return dstInit;
                StringBuffer sb = new StringBuffer(dstInit);
                int shift=0;
                ``/*for(; `nD`>(`srcType.width`+`dstType.width/2`-srcPos)/`dstType.width`);`nD`--){
                                sb.setCharAt(dstPos+`nD`-1,'0');
                            }      */ ``
                assert((`nD`-1)*`dstType.width`<`srcType.width`-srcPos);
                for(int i = 0; i< `nD`;i++){
                    shift = i*4+srcPos;
                    int bits = (int)(0xF & (src >> shift));
                    sb.setCharAt(dstPos+i,intToHexDigit(bits));
                }
                return sb.toString();
            }
        ``}
        case _ => {
        ``
        /**
             * Convert a `srcType.name` into an array of  `dstType.name` using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the `srcType.name` to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param `nD` the number of `dstType.name` to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static `dstType.declArray` `methodName`(
            `srcType.name` src,
            int srcPos,
            `dstType.declArray("dst")`,
            int dstPos,
            int `nD`){
                if(0==`nD`) return dst;
                int shift=0;
                ``/*for(; `nD`>(`srcType.width`+`dstType.width/2`-srcPos)/`dstType.width`;`nD`--){
                                dst[dstPos+`nD`-1]=`dstType.zeroValue`;//need to do sign extension rather than writing always 0 -> keep it simple by using assertion
                            } */``
                assert((`nD`-1)*`dstType.width`<`srcType.width`-srcPos);
                for(int i = 0; i< `nD`;i++){
                    shift = i*`dstType.width`+srcPos;
                    `srcType.boxedName` bits = (`srcType.name`)(`sMask` & (src >> shift));
                    dst[dstPos+i]=`xToY(srcType,"bits",dstType)`;
                }
                return dst;
            }
        ``}
        }``
        `all```}

    def widesToNarrows(srcType: JavaType, dstType: JavaType){
        val outPerIn = dstType.name+"Per"+srcType.name.capitalize
        val outPerInVal = srcType.width/dstType.width
        val nD = "n"+dstType.nickName.capitalize+"s"
        val wideToNarrowsMethodName = srcType.nickName+"To"+dstType.nickName.capitalize+"s"
        val methodName = srcType.nickName+"sTo"+dstType.nickName.capitalize+"s"
        val testMethodName = "test"+methodName.capitalize
        val checkMethodName = "check"+methodName.capitalize
        val boolsToSrc = "boolsTo"+srcType.nickName.capitalize+"s"
        val boolsToDst = "boolsTo"+dstType.nickName.capitalize+"s"

        //testsToCheck.append("test"+methodName.capitalize)
        ``
        `=impl`
    /**
     * Convert part of a `srcType.name` array into an array of  `dstType.name` using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the `srcType.name` array to convert
     * @param srcPos the position in <code>src</code>:  the number of `dstType.name` from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param `nD` the number of `dstType.name` to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static `dstType.declArray` `methodName`(
            `srcType.declArray("src")`,
            int srcPos,
            `dstType.declArray("dst")`,
            int dstPos,
            int `nD`){
        if(0==`nD`) return dst;
        final int `outPerIn` = `outPerInVal`;
        int outCnt = 0;
        final int outIndex = srcPos % `outPerIn`;
        int inIndex=srcPos/`outPerIn`;
        if(0 != outIndex){
            final int nOutFirst=Math.min(`outPerIn`-outIndex, `nD`);
            dst=`wideToNarrowsMethodName`(`srcType.at("src","inIndex")`,outIndex*`dstType.width`,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (`nD` - outCnt)/`outPerIn`;
        for(int i = 0;i<nFullIns;i++){
            dst=`wideToNarrowsMethodName`(`srcType.at("src","inIndex+i")`,0,dst,dstPos+outCnt,`outPerIn`);
            outCnt += `outPerIn`;
        }
        final int remaining = `nD`-outCnt;
        if(0!=remaining) dst=`wideToNarrowsMethodName`(`srcType.at("src","inIndex+nFullIns")`,0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }`=test`
    void `checkMethodName`(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int `nD`){
        `srcType.declArray("src")`=Conversion.`boolsToSrc`(srcAsBools,0,`srcType.declAnonymousArray("srcAsBools.length/"+srcType.width)`,0,srcAsBools.length);
        ``if(dstType.name != "boolean"){``
            `dstType.declArray("dst")`=Conversion.`boolsToDst`(dstAsBools,0,`dstType.declAnonymousArray("dstAsBools.length/"+dstType.width)`,0,dstAsBools.length);
        ``}else{``
            `dstType.declArray("dst","dstAsBools.length")`
            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
            `dstType.declArray("expected")`;
            expected = dstAsBools;
        ``}``
        System.arraycopy(srcAsBools,srcPos*`dstType.width`,dstAsBools,dstPos*`dstType.width`, `nD`*`dstType.width`);
        ``if(dstType.name != "boolean"){``
            `dstType.declArray("expected")` = Conversion.`boolsToDst`(dstAsBools,0,`dstType.declAnonymousArray("dstAsBools.length/"+dstType.width)`,0,dstAsBools.length);
        ``}``
        dst=Conversion.`methodName`(src,srcPos,dst,dstPos,`nD`);
        assertArrayEquals(expected,dst);
    }
    public void `testMethodName`() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = `boolsToArrayDecl(Conv.hexToBools("0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"))`;
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/`dstType.width`;
        final int dstSize = `outPerInVal`+3;
        boolean[] dstAsBools = new boolean[dstSize*`dstType.width`];
        `checkMethodName`(srcAsBools, 0,dstAsBools, 0, 0);
        `checkMethodName`(srcAsBools, 0,dstAsBools, 0, dstSize);
        `checkMethodName`(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        `checkMethodName`(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        `checkMethodName`(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        `checkMethodName`(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        `checkMethodName`(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
    `all```}
    

    /*
    narrowsToWide(new int_t, new long_t)
    narrowsToWide(new short_t, new long_t)
    narrowsToWide(new short_t, new int_t)
    narrowsToWide(new byte_t, new long_t)
    narrowsToWide(new byte_t, new int_t)
    narrowsToWide(new byte_t, new short_t)

    narrowsToWide(new hexDigit_t, new long_t)
    narrowsToWide(new hexDigit_t, new int_t)
    narrowsToWide(new hexDigit_t, new short_t)
    narrowsToWide(new hexDigit_t, new byte_t)

    narrowsToWide(new bool_t, new long_t)
    narrowsToWide(new bool_t, new int_t)
    narrowsToWide(new bool_t, new short_t)
    narrowsToWide(new bool_t, new byte_t)   */

    /*val decls = Array[((JavaType,JavaType) => Unit,JavaType,JavaType)](
    (narrowsToWide _,new int_t, new long_t)     ,
    (narrowsToWide _,new short_t, new long_t)  ,
    (narrowsToWide _,new short_t, new int_t)       ,
    (narrowsToWide _,new byte_t, new long_t)       ,
    (narrowsToWide _,new byte_t, new int_t)        ,
    (narrowsToWide _,new byte_t, new short_t)      ,
    (narrowsToWide _,new hexDigit_t, new long_t)   ,
    (narrowsToWide _,new hexDigit_t, new int_t)    ,
    (narrowsToWide _,new hexDigit_t, new short_t)  ,
    (narrowsToWide _,new hexDigit_t, new byte_t)   ,
    (narrowsToWide _,new bool_t, new long_t)       ,
    (narrowsToWide _,new bool_t, new int_t)        ,
    (narrowsToWide _,new bool_t, new short_t)      ,
    (narrowsToWide _,new bool_t, new byte_t) )

    for(params <- decls){
        params._1(params._2,params._3)
    }           */

    val decls = Array[(JavaType,JavaType)](
            (new int_t, new long_t)     ,
            (new short_t, new long_t)  ,
            (new short_t, new int_t)       ,
            (new byte_t, new long_t)       ,
            (new byte_t, new int_t)        ,
            (new byte_t, new short_t)      ,
            (new hexDigit_t, new long_t)   ,
            (new hexDigit_t, new int_t)    ,
            (new hexDigit_t, new short_t)  ,
            (new hexDigit_t, new byte_t)   ,
            (new bool_t, new long_t)       ,
            (new bool_t, new int_t)        ,
            (new bool_t, new short_t)      ,
            (new bool_t, new byte_t)      /* ,
            (new bool_t, new hexDigit_t)   */
            )

    for(params <- decls){
        narrowsToWide(params._1,params._2)
        convenienceMethods_ns2w(params._1,params._2)
        narrowsToWides(params._1,params._2)
        convenienceMethods_ns2ws(params._1,params._2)
        }
    //narrowsToWide(new bool_t,new hexDigit_t)  //done manually
    //narrowsToWides(new bool_t,new hexDigit_t)  //done manually
    //wideToNarrows(new hexDigit_t,new bool_t) //done manually

    widesToNarrows(new hexDigit_t,new bool_t)
    convenienceMethods_ns2w(new bool_t,new hexDigit_t)
    convenienceMethods_ns2ws(new bool_t,new hexDigit_t)
    convenienceMethods_w2ns(new hexDigit_t,new bool_t)
    convenienceMethods_ws2ns(new hexDigit_t,new bool_t)

    for(params <- decls){
        wideToNarrows(params._2,params._1)
        convenienceMethods_w2ns(params._2,params._1)
        widesToNarrows(params._2,params._1)
        convenienceMethods_ws2ns(params._2,params._1)
    }

``

`=test`
``
    def dbgPrint(srcType: JavaType){``
        static String dbgPrint(`srcType.name`[] src) {
            StringBuilder sb = new StringBuilder();
            ``srcType match{
                case t: bool_t => {
                    ``
                    for(`srcType.name` e:src){
                        if(e) sb.append("1,");
                        else sb.append("0,");
                    }
                    ``}
                case _ => {
                    ``
                    for(`srcType.name` e:src){
                        sb.append("0x").append(Long.toHexString(e)).append(',');
                    }
                    ``}``
            ``}``
            String out = sb.toString();
            return out.substring(0,out.length()-1);
        }
    ``}
    def assertArrayEquals(srcType: JavaType){``
        static void assertArrayEquals(`srcType.name`[] expected, `srcType.name`[] actual){
            assertEquals(expected.length,actual.length);
            for(int i=0;i<expected.length;i++){
                try{
                    assertEquals(expected[i], actual[i]);
                }catch(Throwable e){
                    String msg = "Mismatch at index "+i+" between:\n"+
                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
                    TestCase.fail(msg+"\n"+e.getMessage());
                }
            }
        }
        ``}
    val allPrimitiveTypes = Array[JavaType](
            new long_t,
            new int_t,
            new short_t,
            new byte_t,
            new bool_t)
            for(params <- allPrimitiveTypes){
        dbgPrint(params)
        assertArrayEquals(params)
    }
    /*static String dbgPrint(boolean[] src) {
        StringBuilder sb = new StringBuilder();
        for(boolean b:src){
            if(b) sb.append("1,");
            else sb.append("0,");
        }
        String out = sb.toString();
        return out.substring(0,out.length()-1);
    }
    static void assertArrayEquals(boolean[] expected, boolean[] actual){
        assertEquals(expected.length,actual.length);
        for(int i=0;i<expected.length;i++){
            try{
                assertEquals(expected[i], actual[i]);
            }catch(Throwable e){
                String msg = "Mismatch at index "+i+" between:\n"+
                        dbgPrint(expected)+" and\n"+dbgPrint(actual);
                TestCase.fail(msg+"\n"+e.getMessage());
            }
        }
    } */
    ``
    static void assertArrayEquals(String expected, String actual){
        assertEquals(expected,actual);
    }
    public void testIntsToLong() throws Exception {
        int[] src = new int[]{
                0xCDF1F0C1,
                0x0F123456,
                0x78000000};
        assertEquals(0x0000000000000000L,Conversion.intsToLong(src,0,0L,0,0));
        assertEquals(0x0000000000000000L,Conversion.intsToLong(src,1,0L,0,0));
        assertEquals(0x00000000CDF1F0C1L,Conversion.intsToLong(src,0,0L,0,1));
        assertEquals(0x0F123456CDF1F0C1L,Conversion.intsToLong(src,0,0L,0,2));
        assertEquals(0x000000000F123456L,Conversion.intsToLong(src,1,0L,0,1));
        assertEquals(0x123456789ABCDEF0L,Conversion.intsToLong(src,0,0x123456789ABCDEF0L,0,0));
        assertEquals(0x1234567878000000L,Conversion.intsToLong(src,2,0x123456789ABCDEF0L,0,1));
        //assertEquals(0x0F12345678000000L,Conversion.intsToLong(src,1,0x123456789ABCDEF0L,32,2));
    }
    public void testShortsToLong() throws Exception {
        //TODO: implement this test. This is somehow low priority since intsToLong and bytesToLong are tested
    }
    public void testBytesToLong() throws Exception {
        byte[] src = new byte[]{
                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
                (byte)0x78};
        assertEquals(0x0000000000000000L,Conversion.bytesToLong(src,0,0L,0,0));
        assertEquals(0x00000000000000CDL,Conversion.bytesToLong(src,0,0L,0,1));
        assertEquals(0x00000000C1F0F1CDL,Conversion.bytesToLong(src,0,0L,0,4));
        assertEquals(0x000000000FC1F0F1L,Conversion.bytesToLong(src,1,0L,0,4));
        assertEquals(0x123456789ABCDEF0L,Conversion.bytesToLong(src,0,0x123456789ABCDEF0L,0,0));
        assertEquals(0x12345678CDBCDEF0L,Conversion.bytesToLong(src,0,0x123456789ABCDEF0L,24,1));
        //assertEquals(0x123456789A7856F0L,Conversion.bytesToLong(src,7,0x123456789ABCDEF0L,8,2));
    }
    public void testShortsToInt() throws Exception {
        short[] src = new short[]{
                (short)0xCDF1,(short)0xF0C1,
                (short)0x0F12,(short)0x3456,
                (short)0x7800};
        assertEquals(0x00000000,Conversion.shortsToInt(src, 0, 0, 0, 0));
        assertEquals(0x0000CDF1,Conversion.shortsToInt(src, 0, 0, 0, 1));
        assertEquals(0xF0C1CDF1,Conversion.shortsToInt(src, 0, 0, 0, 2));
        assertEquals(0x0F12F0C1,Conversion.shortsToInt(src, 1, 0, 0, 2));
        assertEquals(0x12345678,Conversion.shortsToInt(src, 0, 0x12345678, 0, 0));
        assertEquals(0xCDF15678,Conversion.shortsToInt(src, 0, 0x12345678, 16, 1));
        //assertEquals(0x34567800,Conversion.shortsToInt(src, 3, 0x12345678, 16, 2));

    }
    public void testBytesToInt() throws Exception {
        byte[] src = new byte[]{
                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
                (byte)0x78};
        assertEquals(0x00000000,Conversion.bytesToInt(src, 0, 0, 0, 0));
        assertEquals(0x000000CD,Conversion.bytesToInt(src, 0, 0, 0, 1));
        assertEquals(0xC1F0F1CD,Conversion.bytesToInt(src, 0, 0, 0, 4));
        assertEquals(0x0FC1F0F1,Conversion.bytesToInt(src, 1, 0, 0, 4));
        assertEquals(0x12345678,Conversion.bytesToInt(src, 0, 0x12345678, 0, 0));
        assertEquals(0xCD345678,Conversion.bytesToInt(src, 0, 0x12345678, 24, 1));
        //assertEquals(0x56341278,Conversion.bytesToInt(src, 5, 0x01234567, 8, 4));
    }
    public void testBytesToShort() throws Exception {
        byte[] src = new byte[]{
                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
                (byte)0x78};
        assertEquals((short)0x0000,Conversion.bytesToShort(src, 0, (short) 0, 0, 0));
        assertEquals((short)0x00CD,Conversion.bytesToShort(src, 0, (short) 0, 0, 1));
        assertEquals((short)0xF1CD,Conversion.bytesToShort(src, 0, (short) 0, 0, 2));
        assertEquals((short)0xF0F1,Conversion.bytesToShort(src, 1, (short) 0, 0, 2));
        assertEquals((short)0x1234,Conversion.bytesToShort(src, 0, (short) 0x1234, 0, 0));
        assertEquals((short)0xCD34,Conversion.bytesToShort(src, 0, (short) 0x1234, 8, 1));
        //assertEquals((short)0x5678,Conversion.bytesToShort(src, 7, (short) 0x0123, 8, 2));
    }
    public void testHexsToLong() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals(0x0000000000000000L,Conversion.hexsToLong(src,0,0L,0,0));
        assertEquals(0x000000000000000CL,Conversion.hexsToLong(src,0,0L,0,1));
        assertEquals(0x000000001C0F1FDCL,Conversion.hexsToLong(src,0,0L,0,8));
        assertEquals(0x0000000001C0F1FDL,Conversion.hexsToLong(src,1,0L,0,8));
        assertEquals(0x123456798ABCDEF0L,Conversion.hexsToLong(src, 0,0x123456798ABCDEF0L,0,0));
        assertEquals(0x1234567876BCDEF0L,Conversion.hexsToLong(src,15,0x123456798ABCDEF0L,24,3));
    }
    public void testHexsToInt() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals(0x00000000,Conversion.hexsToInt(src,0,0,0,0));
        assertEquals(0x0000000C,Conversion.hexsToInt(src,0,0,0,1));
        assertEquals(0x1C0F1FDC,Conversion.hexsToInt(src,0,0,0,8));
        assertEquals(0x01C0F1FD,Conversion.hexsToInt(src,1,0,0,8));
        assertEquals(0x12345679,Conversion.hexsToInt(src, 0,0x12345679,0,0));
        assertEquals(0x87645679,Conversion.hexsToInt(src,15,0x12345679,20,3));
    }
    public void testHexsToShort() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals((short)0x0000,Conversion.hexsToShort(src, 0,(short)0,0,0));
        assertEquals((short)0x000C,Conversion.hexsToShort(src, 0,(short)0,0,1));
        assertEquals((short)0x1FDC,Conversion.hexsToShort(src, 0,(short)0,0,4));
        assertEquals((short)0xF1FD,Conversion.hexsToShort(src, 1,(short)0,0,4));
        assertEquals((short)0x1234,Conversion.hexsToShort(src, 0,(short)0x1234,0,0));
        assertEquals((short)0x8764,Conversion.hexsToShort(src,15,(short)0x1234,4,3));
    }
    public void testHexsToByte() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals((byte)0x00,Conversion.hexsToByte(src, 0,(byte)0,0,0));
        assertEquals((byte)0x0C,Conversion.hexsToByte(src, 0,(byte)0,0,1));
        assertEquals((byte)0xDC,Conversion.hexsToByte(src, 0,(byte)0,0,2));
        assertEquals((byte)0xFD,Conversion.hexsToByte(src, 1,(byte)0,0,2));
        assertEquals((byte)0x34,Conversion.hexsToByte(src, 0,(byte)0x34,0,0));
        assertEquals((byte)0x84,Conversion.hexsToByte(src,17,(byte)0x34,4,1));
    }
    public void testBoolsToLong() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools

        assertEquals(0x0000000000000000L,Conversion.boolsToLong(src,0,0L,0,0));
        assertEquals(0x000000000000000CL,Conversion.boolsToLong(src,0,0L,0,1*4));
        assertEquals(0x000000001C0F1FDCL,Conversion.boolsToLong(src,0,0L,0,8*4));
        assertEquals(0x0000000001C0F1FDL,Conversion.boolsToLong(src,1*4,0L,0,8*4));
        assertEquals(0x123456798ABCDEF0L,Conversion.boolsToLong(src, 0,0x123456798ABCDEF0L,0,0));
        assertEquals(0x1234567876BCDEF0L,Conversion.boolsToLong(src,15*4,0x123456798ABCDEF0L,24,3*4));
    }
    public void testBoolsToInt() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals(0x00000000,Conversion.boolsToInt(src, 0*4,0,0,0*4));
        assertEquals(0x0000000C,Conversion.boolsToInt(src, 0*4,0,0,1*4));
        assertEquals(0x1C0F1FDC,Conversion.boolsToInt(src, 0*4,0,0,8*4));
        assertEquals(0x01C0F1FD,Conversion.boolsToInt(src, 1*4,0,0,8*4));
        assertEquals(0x12345679,Conversion.boolsToInt(src, 0*4,0x12345679, 0,0*4));
        assertEquals(0x87645679,Conversion.boolsToInt(src,15*4,0x12345679,20,3*4));
    }
    public void testBoolsToShort() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals((short)0x0000,Conversion.boolsToShort(src, 0*4,(short)0,0,0*4));
        assertEquals((short)0x000C,Conversion.boolsToShort(src, 0*4,(short)0,0,1*4));
        assertEquals((short)0x1FDC,Conversion.boolsToShort(src, 0*4,(short)0,0,4*4));
        assertEquals((short)0xF1FD,Conversion.boolsToShort(src, 1*4,(short)0,0,4*4));
        assertEquals((short)0x1234,Conversion.boolsToShort(src, 0*4,(short)0x1234,0,0*4));
        assertEquals((short)0x8764,Conversion.boolsToShort(src,15*4,(short)0x1234,4,3*4));
    }
    public void testBoolsToByte() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals((byte)0x00,Conversion.boolsToByte(src, 0*4,(byte)0,0,0*4));
        assertEquals((byte)0x0C,Conversion.boolsToByte(src, 0*4,(byte)0,0,1*4));
        assertEquals((byte)0xDC,Conversion.boolsToByte(src, 0*4,(byte)0,0,2*4));
        assertEquals((byte)0xFD,Conversion.boolsToByte(src, 1*4,(byte)0,0,2*4));
        assertEquals((byte)0x34,Conversion.boolsToByte(src, 0*4,(byte)0x34,0,0*4));
        assertEquals((byte)0x84,Conversion.boolsToByte(src,17*4,(byte)0x34,4,1*4));
    }
    public void testBoolsToHex() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals('F',Conversion.boolsToHex(src, 0*4,'F',0,0*4));
        assertEquals('C',Conversion.boolsToHex(src, 0*4,'F',0,1*4));
        assertEquals('D',Conversion.boolsToHex(src, 1*4,'F',0,1*4));
        assertEquals('8',Conversion.boolsToHex(src,17*4,'F',0,1*4));
        assertEquals('9',Conversion.boolsToHex(src, 3*4,'F',0,3));
        assertEquals('1',Conversion.boolsToHex(src, 3*4,'0',0,3));
        assertEquals('E',Conversion.boolsToHex(src,   1,'F',0,4));
        assertEquals('1',Conversion.boolsToHex(src,17*4+3,'0',0,1));
        assertEquals('2',Conversion.boolsToHex(src,17*4+3,'0',1,1));
        assertEquals('4',Conversion.boolsToHex(src,17*4+3,'0',2,1));
        assertEquals('8',Conversion.boolsToHex(src,17*4+3,'0',3,1));
    }
    public void testLongToInts() throws Exception {
        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 0,new int[]{},0,0));
        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 100,new int[]{},0,0));
        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 0,new int[]{},100,0));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,0));
        assertArrayEquals(new int[]{0x90ABCDEF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,1));
        assertArrayEquals(new int[]{0x90ABCDEF,0x12345678,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,2));
        //assertArrayEquals(new int[]{0x90ABCDEF,0x12345678,0x90ABCDEF,0x12345678},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,4));//rejected by assertion
        //assertArrayEquals(new int[]{0xFFFFFFFF,0x90ABCDEF,0x12345678,0x90ABCDEF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},1,3));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF,0x12345678},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},2,2));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},3,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x4855E6F7,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 1,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x242AF37B,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 2,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x121579BD,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 3,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x890ABCDE,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 4,new int[]{-1,-1,-1,-1},2,1));
        //assertArrayEquals(new int[]{0x4855E6F7,0x091A2B3C,0x4855E6F7,0x091A2B3C},Conversion.longToInts(0x1234567890ABCDEFL, 1,new int[]{-1,-1,-1,-1},0,4));//rejected by assertion
        assertArrayEquals(new int[]{0x091A2B3C},Conversion.longToInts(0x1234567890ABCDEFL,33,new int[]{0},0,1));
    }
    public void testLongToShorts() throws Exception {
        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 0,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 100,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 0,new short[]{},100,0));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,0));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,1));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,2));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0x5678,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,3));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0x5678,(short)0x1234},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,4));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xCDEF,(short)0x90AB,(short)0x5678},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},1,3));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xCDEF,(short)0x90AB},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},2,2));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xCDEF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xCDEF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},3,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xE6F7,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 1,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xF37B,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 2,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x79BD,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 3,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xBCDE,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 4,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xE6F7,(short)0x4855,(short)0x2B3C,(short)0x091A},Conversion.longToShorts(0x1234567890ABCDEFL, 1,new short[]{-1,-1,-1,-1},0,4));
        assertArrayEquals(new short[]{(short)0x2B3C},Conversion.longToShorts(0x1234567890ABCDEFL,33,new short[]{0},0,1));
    }
    public void testIntToShorts() throws Exception {
        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 0,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 100,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 0,new short[]{},100,0));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,0));
        assertArrayEquals(new short[]{(short)0x5678,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,1));
        assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,2));
        //assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0x5678,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,3));//rejected by assertion
        //assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0x5678,(short)0x1234},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,4));
        //assertArrayEquals(new short[]{(short)0xFFFF,(short)0x5678,(short)0x1234,(short)0x5678},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},1,3));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x5678,(short)0x1234},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},2,2));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x5678,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0x5678},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},3,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x2B3C,(short)0xFFFF},Conversion.intToShorts(0x12345678, 1,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x159E,(short)0xFFFF},Conversion.intToShorts(0x12345678, 2,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x8ACF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 3,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x4567,(short)0xFFFF},Conversion.intToShorts(0x12345678, 4,new short[]{-1,-1,-1,-1},2,1));
        //assertArrayEquals(new short[]{(short)0xE6F7,(short)0x4855,(short)0x2B3C,(short)0x091A},Conversion.intToShorts(0x12345678, 1,new short[]{-1,-1,-1,-1},0,4));//rejected by assertion
        //assertArrayEquals(new short[]{(short)0x2B3C},Conversion.intToShorts(0x12345678,33,new short[]{0},0,1));//rejected by assertion
        assertArrayEquals(new short[]{(short)0x091A},Conversion.intToShorts(0x12345678,17,new short[]{0},0,1));
    }
    public void testLongToBytes() throws Exception {
        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 0,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 100,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 0,new byte[]{},100,0));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,0));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,2));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,4));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,7));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,8));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,2));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,7));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));
        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00},Conversion.longToBytes(0x1234567890ABCDEFL, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));
        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00,(byte)0x00},Conversion.longToBytes(0x1234567890ABCDEFL,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));//rejected by assertion
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,7));
    }
    public void testIntToBytes() throws Exception {
        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 0,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 100,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 0,new byte[]{},100,0));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,0));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,2));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,4));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,2));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0x00,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));//rejected by assertion
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,3));
    }
    public void testShortToBytes() throws Exception {
        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 0,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 100,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 0,new byte[]{},100,0));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,0));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,2));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},3,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},3,2));
        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0xFE,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,2));
        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,2));//rejected by assertion
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0xFE,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,1));
    }
    public void testLongToHexs() throws Exception {
        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 0,"",0,0));
        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 100,"",0,0));
        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 0,"",100,0));
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDE3L, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
        assertEquals("FEDCBA098765432FFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,15));
        assertEquals("FEDCBA0987654321FFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,16));
        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDE3L, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
        assertEquals("FFFFEDCFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
        assertEquals("FFFFEDCBA098765432FFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,15));
        assertEquals("FFFFEDCBA0987654321FFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));
        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FFFDB975121FCA86420FFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));
        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL,4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));//rejected by assertion
        assertEquals("FFFEDCBA0987654321FFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL,4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,15));
    }
    public void testIntToHexs() throws Exception {
        assertEquals("",Conversion.intToHexs(0x00000000, 0,"",0,0));
        assertEquals("",Conversion.intToHexs(0x00000000, 100,"",0,0));
        assertEquals("",Conversion.intToHexs(0x00000000, 0,"",100,0));
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
        assertEquals("FEDCBA0FFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,7));
        assertEquals("FEDCBA09FFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,8));
        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
        assertEquals("FFFFEDCFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
        assertEquals("FFFFEDCBA0FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,7));
        assertEquals("FFFFEDCBA09FFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));
        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FFFDB97512FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));
        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));//rejected by assertion
        assertEquals("FFFEDCBA09FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,7));
    }
    public void testShortToHexs() throws Exception {
        assertEquals("",Conversion.shortToHexs((short)0x0000, 0,"",0,0));
        assertEquals("",Conversion.shortToHexs((short)0x0000, 100,"",0,0));
        assertEquals("",Conversion.shortToHexs((short)0x0000, 0,"",100,0));
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
        assertEquals("FEDFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,3));
        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FFFDB9FFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));//rejected by assertion
        assertEquals("FFFEDCFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,3));
    }
    public void testByteToHexs() throws Exception {
        assertEquals("",Conversion.byteToHexs((byte)0x00, 0,"",0,0));
        assertEquals("",Conversion.byteToHexs((byte)0x00, 100,"",0,0));
        assertEquals("",Conversion.byteToHexs((byte)0x00, 0,"",100,0));
        assertEquals("00000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,0));
        assertEquals("F0000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,1));
        assertEquals("FE000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,2));
        assertEquals("000F0",Conversion.byteToHexs((byte)0xEF, 0,"00000",3,1));
        assertEquals("000FE",Conversion.byteToHexs((byte)0xEF, 0,"00000",3,2));
        assertEquals("70000",Conversion.byteToHexs((byte)0xEF, 1,"00000",0,1));
        assertEquals("B0000",Conversion.byteToHexs((byte)0xEF, 2,"00000",0,1));
        assertEquals("000DF",Conversion.byteToHexs((byte)0xEF, 3,"00000",3,2));
        //assertEquals("00000",Conversion.byteToHexs((byte)0xEF, 4,"00000",3,2));//rejected by assertion
        assertEquals("000E0",Conversion.byteToHexs((byte)0xEF, 4,"00000",3,1));
    }
    public void testLongToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 0));
    ``/*  //scala code used to generate the long boolean array
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 0, 1))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 1));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 0, 2))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 2));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 0, 3))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 3));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 0,63))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,63));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 0,64))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,64));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 2, 1))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2, 1));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 0,new Array[Boolean](69), 2,64))`,Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2,64));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 1,new Array[Boolean](69), 0, 1))`,Conversion.longToBools(0x1234567890ABCDEFL, 1,new boolean[69], 0, 1));
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 2,new Array[Boolean](69), 0, 1))`,Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 0, 1));  */
    //exception :-S
        /*assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL, 5,new Array[Boolean](69), 3,64))`,Conversion.longToBools(0x1234567890ABCDEFL, 5,new boolean[69], 3,64));
        assertArrayEquals(new boolean[69],Conversion.longToBools(0x1234567890ABCDEFL,13,new boolean[69], 3,64));//rejected by assertion
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL,13,new Array[Boolean](69), 3,63))`,Conversion.longToBools(0x1234567890ABCDEFL,13,new boolean[69], 3,63));   */``

        assertArrayEquals(new boolean[]{true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 1));
        assertArrayEquals(new boolean[]{true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 2));
        assertArrayEquals(new boolean[]{true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 3));
        assertArrayEquals(new boolean[]{true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,63));
        assertArrayEquals(new boolean[]{true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,64));
        assertArrayEquals(new boolean[]{false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2, 1));
        assertArrayEquals(new boolean[]{false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2,64));
        assertArrayEquals(new boolean[]{true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 1,new boolean[69], 0, 63));
        assertArrayEquals(new boolean[]{true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 0, 62));

        //assertArrayEquals(new boolean[]{false,false,false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false    ,false,false,false,false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 3, 63));//rejected by assertion
        assertArrayEquals(new boolean[]{false,false,false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false    ,false,false,false,false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 3, 62));
    }
    public void testIntToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.intToBools(0x90ABCDEF, 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 1));
        assertArrayEquals(new boolean[]{ true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 2));
        assertArrayEquals(new boolean[]{ true,  true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 3));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0,31));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0,32));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false,    false,  true, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 2,32));
        assertArrayEquals(new boolean[]{ true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 1,new boolean[37], 0,31));
        assertArrayEquals(new boolean[]{ true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 0,30));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 3,31));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,     true, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 3,30));
    }
    public void testShortToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.shortToBools((short)0xCDEF, 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 1));
        assertArrayEquals(new boolean[]{ true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 2));
        assertArrayEquals(new boolean[]{ true,  true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 3));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0,15));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0,16));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,       true,  true, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 2,16));
        assertArrayEquals(new boolean[]{ true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 1,new boolean[21], 0,15));
        assertArrayEquals(new boolean[]{ true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 0,14));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 3,15));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,       true, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 3,14));
    }
    public void testByteToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.byteToBools((byte)0xEF, 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 1));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 2));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 3));
        assertArrayEquals(new boolean[]{ true, false,  true, false,  true, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 7));
        assertArrayEquals(new boolean[]{ true, false,  true, false,  true, false, false,  true,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 8));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true, false,  true, false,  true, false,      false,  true, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 2, 8));
        assertArrayEquals(new boolean[]{false,  true, false,  true, false, false,  true, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 1,new boolean[13], 0, 7));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false,  true, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 0, 6));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 3, 7));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true, false,  true, false, false,       true, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 3, 6));
    }
    public void testHexToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.hexToBools('F', 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 1));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 2));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 3));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 4));
        assertArrayEquals(new boolean[]{ true, false,  true, false,  true,  true,  true,  true},Conversion.hexToBools('5', 0,new boolean[]{true,true,true,true,true,true,true,true}, 0, 4));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true, false,  true, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 2, 4));
        assertArrayEquals(new boolean[]{false,  true, false, false, false, false, false, false},Conversion.hexToBools('5', 1,new boolean[8], 0, 3));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 2,new boolean[8], 0, 2));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true},Conversion.hexToBools('5', 2,new boolean[8], 3, 3));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true, false, false, false, false},Conversion.hexToBools('5', 2,new boolean[8], 3, 1));
    }
    public void testIntsToLongs() throws Exception{
        int[] src = new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.intsToLongs(src,0,dst,0,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.intsToLongs(src,0,dst,1,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.intsToLongs(src,0,dst,5,1));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.intsToLongs(src,0,dst,3,2));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.intsToLongs(src,1,dst,3,2));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.intsToLongs(src,2,dst,3,2));
    }
    public void testShortsToLongs() throws Exception{
        short[] src = new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.shortsToLongs(src,0,dst, 0,8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.shortsToLongs(src,0,dst, 2,8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.shortsToLongs(src,0,dst,10,2));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.shortsToLongs(src,0,dst, 6,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.shortsToLongs(src,2,dst, 6,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.shortsToLongs(src,4,dst, 6,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64AE64AL},Conversion.shortsToLongs(src,7,dst, 8,1));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE64A4567E64AE64AL},Conversion.shortsToLongs(src,7,dst,11,1));
        assertArrayEquals(new long[] {0x012345670123E64AL, 0xC539275B89ABCDEFL, 0xE64A4567E64AE64AL},Conversion.shortsToLongs(src,7,dst, 0,1));
    }
    public void testShortsToInts() throws Exception{
        short[] src = new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38,0x00000000, 0x00000000},Conversion.shortsToInts(src,0,dst, 0,8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x00000000},Conversion.shortsToInts(src,0,dst, 2,8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.shortsToInts(src,0,dst,10,2));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x01234567,0x89ABCDEF, 0x01234567},Conversion.shortsToInts(src,0,dst, 6,4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x89ABCDEF,0xC539275B, 0x01234567},Conversion.shortsToInts(src,2,dst, 6,4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.shortsToInts(src,4,dst, 6,4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0x01234567},Conversion.shortsToInts(src,7,dst, 8,1));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0xE64A4567},Conversion.shortsToInts(src,7,dst,11,1));
        assertArrayEquals(new int[] {0x0123E64A,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0xE64A4567},Conversion.shortsToInts(src,7,dst, 0,1));
    }
    public void testBytesToLongs() throws Exception{
        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.bytesToLongs(src, 0,dst, 0,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.bytesToLongs(src, 0,dst, 4,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.bytesToLongs(src, 0,dst,20, 4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.bytesToLongs(src, 0,dst,12, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.bytesToLongs(src, 4,dst,12, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.bytesToLongs(src, 8,dst,12, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst,16, 1));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE6234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst,23, 1));
        assertArrayEquals(new long[] {0x01234567012345E6L, 0xC539275B89ABCDEFL, 0xE6234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst, 0, 1));
    }
    public void testBytesToInts() throws Exception{
        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38,0x00000000, 0x00000000},Conversion.bytesToInts(src, 0,dst, 0,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x00000000},Conversion.bytesToInts(src, 0,dst, 4,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.bytesToInts(src, 0,dst,20, 4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x01234567,0x89ABCDEF, 0x01234567},Conversion.bytesToInts(src, 0,dst,12, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x89ABCDEF,0xC539275B, 0x01234567},Conversion.bytesToInts(src, 4,dst,12, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.bytesToInts(src, 8,dst,12, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0x01234567},Conversion.bytesToInts(src,15,dst,16, 1));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0xE6234567},Conversion.bytesToInts(src,15,dst,23, 1));
        assertArrayEquals(new int[] {0x012345E6,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0xE6234567},Conversion.bytesToInts(src,15,dst, 0, 1));
    }
    public void testBytesToShorts() throws Exception{
        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
        short[] dst = new short[12];
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.bytesToShorts(src, 0,dst, 0,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.bytesToShorts(src, 0,dst, 4,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 0,dst,20, 4));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 0,dst,12, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 4,dst,12, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 8,dst,12, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src,15,dst,16, 1));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0xE623},Conversion.bytesToShorts(src,15,dst,23, 1));
        assertArrayEquals(new short[] {(short)0x45E6,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0xE623},Conversion.bytesToShorts(src,15,dst, 0, 1));
    }
    public void testHexsToLongs() throws Exception{
        String src = "`"E64A8C38C539275B89ABCDEF01234567".reverse`";
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.hexsToLongs(src, 0,dst, 0,32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.hexsToLongs(src, 0,dst, 8,32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.hexsToLongs(src, 0,dst,40, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.hexsToLongs(src, 0,dst,24,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.hexsToLongs(src, 8,dst,24,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.hexsToLongs(src,16,dst,24,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE1234567E64A8C38L},Conversion.hexsToLongs(src,31,dst,47, 1));
    }
    public void testHexsToInts() throws Exception{
        String src = "`"E64A8C38C539275B89ABCDEF01234567".reverse`";
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF,0xC539275B, 0xE64A8C38,0x00000000, 0x00000000},Conversion.hexsToInts(src, 0,dst, 0,32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x00000000},Conversion.hexsToInts(src, 0,dst, 8,32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.hexsToInts(src, 0,dst,40, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x01234567,0x89ABCDEF, 0x01234567},Conversion.hexsToInts(src, 0,dst,24,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x89ABCDEF,0xC539275B, 0x01234567},Conversion.hexsToInts(src, 8,dst,24,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.hexsToInts(src,16,dst,24,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0xE1234567},Conversion.hexsToInts(src,31,dst,47, 1));
    }
    public void testHexsToShorts() throws Exception{
        String src = "`"E64A8C38C539275B89ABCDEF01234567".reverse`";
        short[] dst = new short[12];
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.hexsToShorts(src, 0,dst, 0,32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.hexsToShorts(src, 0,dst, 8,32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 0,dst,40, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 0,dst,24,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 8,dst,24,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src,16,dst,24,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0xE123},Conversion.hexsToShorts(src,31,dst,47, 1));
    }
    public void testHexsToBytes() throws Exception{
        String src = "`"E64A8C38C539275B89ABCDEF01234567".reverse`";
        byte[] dst = new byte[24];
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.hexsToBytes(src, 0,dst, 0,32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.hexsToBytes(src, 0,dst, 8,32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 0,dst,40, 8));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 0,dst,24,16));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 8,dst,24,16));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src,16,dst,24,16));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0xE1},Conversion.hexsToBytes(src,31,dst,47, 1));
    }
    public void testBoolsToLongs() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = `boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))`;
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.boolsToLongs(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.boolsToLongs(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.boolsToLongs(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.boolsToLongs(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.boolsToLongs(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.boolsToLongs(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x81234567E64A8C38L},Conversion.boolsToLongs(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new long[] {0x0123456701234566L, 0xC539275B89ABCDEFL, 0x81234567E64A8C38L},Conversion.boolsToLongs(src,1*32-1,dst,0,1));
    }
    public void testBoolsToInts() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = `boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))`;
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF,0xC539275B, 0xE64A8C38,0x00000000, 0x00000000},Conversion.boolsToInts(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x00000000},Conversion.boolsToInts(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.boolsToInts(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x01234567,0x89ABCDEF, 0x01234567},Conversion.boolsToInts(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x89ABCDEF,0xC539275B, 0x01234567},Conversion.boolsToInts(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.boolsToInts(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x81234567},Conversion.boolsToInts(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new int[] {0x01234566,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x81234567},Conversion.boolsToInts(src,1*32-1,dst,0,1));
    }
    public void testBoolsToShorts() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = `boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))`;
        short[] dst = new short[12];
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.boolsToShorts(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.boolsToShorts(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x8123},Conversion.boolsToShorts(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new short[] {(short)0x4566,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x8123},Conversion.boolsToShorts(src,1*32-1,dst,0,1));
    }
    public void testBoolsToBytes() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = `boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))`;
        byte[] dst = new byte[24];
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.boolsToBytes(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.boolsToBytes(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x81},Conversion.boolsToBytes(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new byte[] {(byte)0x66,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x81},Conversion.boolsToBytes(src,1*32-1,dst,0,1));
    }
    public void testBoolsToHexs() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};

        assertEquals("76543210FEDCBA98B572935C83C8A46E",Conversion.boolsToHexs(src,0*32,"00000000000000000000000000000000",0*32,4*32));
        assertEquals("000000007",Conversion.boolsToHexs(src, 0,"000000000",32, 4));
        assertEquals("000000006",Conversion.boolsToHexs(src, 4,"000000000",32, 4));
        assertEquals("000000003",Conversion.boolsToHexs(src, 1,"000000000",32, 4));
        assertEquals("000000006",Conversion.boolsToHexs(src, 1,"000000000",33, 3));
        assertEquals("00000000C",Conversion.boolsToHexs(src, 1,"000000000",34, 2));
        assertEquals("000000008",Conversion.boolsToHexs(src, 1,"000000000",35, 1));
        assertEquals("00000000B",Conversion.boolsToHexs(src, 1,"000000008",32, 3));
    }
`none`
    public void testIntsToLongConvenienceMethods() throws Exception {
        int []src=new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        assertEquals(Conversion.intsToLong(src,3,0L,2,1),Conversion.intsToLong(src,3,2,1));
        assertEquals(Conversion.intsToLong(src,3,0L,0,1),Conversion.intsToLong(src,3,1));
        assertEquals(Conversion.intsToLong(src,3,0L,0,src.length-3),Conversion.intsToLong(src,3));
        int []src2=new int[] {0x01234567,0x89ABCDEF};
        assertEquals(Conversion.intsToLong(src2,0,0L,0,src2.length),Conversion.intsToLong(src2));
    }
    public void testShortsToLongConvenienceMethods() throws Exception {
        short []src=new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0xBA47};
        assertEquals(Conversion.shortsToLong(src,6,0L,2,1),Conversion.shortsToLong(src,6,2,1));
        assertEquals(Conversion.shortsToLong(src,6,0L,0,1),Conversion.shortsToLong(src,6,1));
        assertEquals(Conversion.shortsToLong(src,6,0L,0,src.length-6),Conversion.shortsToLong(src,6));
        short []src2=new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB};
        assertEquals(Conversion.shortsToLong(src2,0,0L,0,src2.length),Conversion.shortsToLong(src2));
    }
    public void testShortsToIntConvenienceMethods() throws Exception {
        short []src=new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
        assertEquals(Conversion.shortsToInt(src,6,0,2,1),Conversion.shortsToInt(src,6,2,1));
        assertEquals(Conversion.shortsToInt(src,6,0,0,1),Conversion.shortsToInt(src,6,1));
        assertEquals(Conversion.shortsToInt(src,6,0,0,src.length-6),Conversion.shortsToInt(src,6));
        short []src2=new short[] {(short)0x4567,(short)0x0123};
        assertEquals(Conversion.shortsToInt(src2,0,0,0,src2.length),Conversion.shortsToInt(src2));
    }
    public void testBytesToLongConvenienceMethods() throws Exception {
        byte []src=new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27};
        assertEquals(Conversion.bytesToLong(src,3,0L,2,1),Conversion.bytesToLong(src,3,2,1));
        assertEquals(Conversion.bytesToLong(src,3,0L,0,1),Conversion.bytesToLong(src,3,1));
        assertEquals(Conversion.bytesToLong(src,3,0L,0,src.length-3),Conversion.bytesToLong(src,3));
        byte []src2=new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89};
        assertEquals(Conversion.bytesToLong(src2,0,0L,0,src2.length),Conversion.bytesToLong(src2));
    }
    public void testBytesToIntConvenienceMethods() throws Exception {
        byte []src=new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF};
        assertEquals(Conversion.bytesToInt(src,3,0,2,1),Conversion.bytesToInt(src,3,2,1));
        assertEquals(Conversion.bytesToInt(src,3,0,0,1),Conversion.bytesToInt(src,3,1));
        assertEquals(Conversion.bytesToInt(src,3,0,0,src.length-3),Conversion.bytesToInt(src,3));
        byte []src2=new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01};
        assertEquals(Conversion.bytesToInt(src2,0,0,0,src2.length),Conversion.bytesToInt(src2));
    }
    public void testBytesToShortConvenienceMethods() throws Exception {
        byte []src=new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF};
        assertEquals(Conversion.bytesToShort(src,3,(short)0,2,1),Conversion.bytesToShort(src,3,2,1));
        assertEquals(Conversion.bytesToShort(src,3,(short)0,0,1),Conversion.bytesToShort(src,3,1));
        assertEquals(Conversion.bytesToShort(src,3,(short)0,0,src.length-3),Conversion.bytesToShort(src,3));
        byte []src2=new byte[] {(byte)0x67,(byte)0x45};
        assertEquals(Conversion.bytesToShort(src2,0,(short)0,0,src2.length),Conversion.bytesToShort(src2));
    }
    public void testHexsToLongConvenienceMethods() throws Exception {
        String src="EFC539275BE64A8C38";
        assertEquals(Conversion.hexsToLong(src,3,0L,2,1),Conversion.hexsToLong(src,3,2,1));
        assertEquals(Conversion.hexsToLong(src,3,0L,0,1),Conversion.hexsToLong(src,3,1));
        assertEquals(Conversion.hexsToLong(src,3,0L,0,src.length()-3),Conversion.hexsToLong(src,3));
        String src2="C539275BE64A8C38";
        assertEquals(Conversion.hexsToLong(src2,0,0L,0,src2.length()),Conversion.hexsToLong(src2));
    }
    public void testHexsToIntConvenienceMethods() throws Exception {
        String src="EFC539275B";
        assertEquals(Conversion.hexsToInt(src,3,0,2,1),Conversion.hexsToInt(src,3,2,1));
        assertEquals(Conversion.hexsToInt(src,3,0,0,1),Conversion.hexsToInt(src,3,1));
        assertEquals(Conversion.hexsToInt(src,3,0,0,src.length()-3),Conversion.hexsToInt(src,3));
        String src2="C5392724";
        assertEquals(Conversion.hexsToInt(src2,0,0,0,src2.length()),Conversion.hexsToInt(src2));
    }
    public void testHexsToShortConvenienceMethods() throws Exception {
        String src="EFC539";
        String src2="C539";
        assertEquals(Conversion.hexsToShort(src ,3,(short)0,2,1),             Conversion.hexsToShort(src,3,2,1));
        assertEquals(Conversion.hexsToShort(src ,3,(short)0,0,1),             Conversion.hexsToShort(src,3,1));
        assertEquals(Conversion.hexsToShort(src ,3,(short)0,0,src.length()-3),Conversion.hexsToShort(src,3));
        assertEquals(Conversion.hexsToShort(src2,0,(short)0,0,src2.length()), Conversion.hexsToShort(src2));
    }
    public void testHexsToByteConvenienceMethods() throws Exception {
        String src="EFC5";
        String src2="C5";
        assertEquals(Conversion.hexsToByte(src ,3,(byte)0,2,1),             Conversion.hexsToByte(src,3,2,1));
        assertEquals(Conversion.hexsToByte(src ,3,(byte)0,0,1),             Conversion.hexsToByte(src,3,1));
        assertEquals(Conversion.hexsToByte(src ,3,(byte)0,0,src.length()-3),Conversion.hexsToByte(src,3));
        assertEquals(Conversion.hexsToByte(src2,0,(byte)0,0,src2.length()), Conversion.hexsToByte(src2));
    }
    public void testBoolsToLongConvenienceMethods() throws Exception {
        boolean[] src=new boolean[]{false,true,false,true, true, false, true, false, true, true, false,true, false, true,false, true,false,true, true, false, true, false,true, true, false, true, false, true, true, false,true, false, true,false, true, true, false,false, true, true, false,true, false, true,false, true, true, false, true, false, true, false, true, false,true, true, false, true, true, false, true,true, false, true, false,true, true};
        boolean[] src2=new boolean[]{false,true, false,true, true, false, true, false,true, true, false, true, false, true, true, false,true, false, true,false, true, true, false,false, true, true, false,true, false, true,false, true, true, false,true, false, true, false,true, true, false, true, false, true, true, false,true, false, true,false, true, true, false,false, true, true, false,true, false, true,false, true, true, false};
        assertEquals(Conversion.boolsToLong(src ,3,0L,2,1),             Conversion.boolsToLong(src,3,2,1));
        assertEquals(Conversion.boolsToLong(src ,3,0L,0,1),             Conversion.boolsToLong(src,3,1));
        assertEquals(Conversion.boolsToLong(src ,3,0L,0,src.length-3),Conversion.boolsToLong(src,3));
        assertEquals(Conversion.boolsToLong(src2,0,0L,0,src2.length), Conversion.boolsToLong(src2));
    }
    public void testBoolsToIntConvenienceMethods() throws Exception {
        boolean[] src=new boolean[]{false,true,false,true, true, false, true, false, true, true, false,true, false, true,false, true, true, false, true, false, true, false,true, true, false, true, true, false, true,true, false, true, false,true, true};
        boolean[] src2=new boolean[]{false,true, true, false, true, false,true, true, false, true, false, true, true, false,true, false, true,false, true, true, false,false, true, true, false,true, false, true,false, true, true, false};
        assertEquals(Conversion.boolsToInt(src ,3,0,2,1),             Conversion.boolsToInt(src,3,2,1));
        assertEquals(Conversion.boolsToInt(src ,3,0,0,1),             Conversion.boolsToInt(src,3,1));
        assertEquals(Conversion.boolsToInt(src ,3,0,0,src.length-3),Conversion.boolsToInt(src,3));
        assertEquals(Conversion.boolsToInt(src2,0,0,0,src2.length), Conversion.boolsToInt(src2));
    }
    public void testBoolsToShortConvenienceMethods() throws Exception {
        boolean[] src=new boolean[]{false,true, true, false, true, false,true, true, false, true, true, false, true,true, false, true, false,true, true};
        boolean[] src2=new boolean[]{false,true, true, false, true, false, true, true, false,true, false, true,false, true, true, false};
        assertEquals(Conversion.boolsToShort(src ,3,(short)0,2,1),             Conversion.boolsToShort(src,3,2,1));
        assertEquals(Conversion.boolsToShort(src ,3,(short)0,0,1),             Conversion.boolsToShort(src,3,1));
        assertEquals(Conversion.boolsToShort(src ,3,(short)0,0,src.length-3),Conversion.boolsToShort(src,3));
        assertEquals(Conversion.boolsToShort(src2,0,(short)0,0,src2.length), Conversion.boolsToShort(src2));
    }
    public void testBoolsToByteConvenienceMethods() throws Exception {
        boolean[] src=new boolean[]{false,true, true, false, true, true, false, true, false,true, true};
        boolean[] src2=new boolean[]{false,true, true, false, true, true, false, true};
        assertEquals(Conversion.boolsToByte(src ,3,(byte)0,2,1),             Conversion.boolsToByte(src,3,2,1));
        assertEquals(Conversion.boolsToByte(src ,3,(byte)0,0,1),             Conversion.boolsToByte(src,3,1));
        assertEquals(Conversion.boolsToByte(src ,3,(byte)0,0,src.length-3),Conversion.boolsToByte(src,3));
        assertEquals(Conversion.boolsToByte(src2,0,(byte)0,0,src2.length), Conversion.boolsToByte(src2));
    }
    public void testBoolsToHexConvenienceMethods() throws Exception {
        boolean[] src=new boolean[]{false,true, true, false, true};
        boolean[] src2=new boolean[]{false,true, true, false};
        assertEquals(Conversion.boolsToHex(src ,3,'0',2,1),             Conversion.boolsToHex(src,3,2,1));
        assertEquals(Conversion.boolsToHex(src ,3,'0',0,1),             Conversion.boolsToHex(src,3,1));
        assertEquals(Conversion.boolsToHex(src ,3,'0',0,src.length-3),Conversion.boolsToHex(src,3));
        assertEquals(Conversion.boolsToHex(src2,0,'0',0,src2.length), Conversion.boolsToHex(src2));
    }
`none`
//original version coded manualy, but now generated version is used
    public void testLongToIntsConvenienceMethods() throws Exception {
        long src=0x0123456789ABCDEFL;
        int []dstInit1=new int[1];
        int []dstInit2=new int[2];
        assertArrayEquals(Conversion.longToInts(src,7,dstInit2,0,2),Conversion.longToInts(src,7,2));
        assertArrayEquals(Conversion.longToInts(src,0,dstInit1,0,1),Conversion.longToInts(src,1));
        assertArrayEquals(Conversion.longToInts(src,0,dstInit2,0,2),Conversion.longToInts(src));
    }
    public void testLongToShortsConvenienceMethods() throws Exception {
        long src=0x0123456789ABCDEFL;
        short []dstInit3=new short[3];
        short []dstInit4=new short[4];
        assertArrayEquals(Conversion.longToShorts(src,7,dstInit4,0,4),Conversion.longToShorts(src,7,4));
        assertArrayEquals(Conversion.longToShorts(src,0,dstInit3,0,3),Conversion.longToShorts(src,3));
        assertArrayEquals(Conversion.longToShorts(src,0,dstInit4,0,4),Conversion.longToShorts(src));
    }
    public void testIntToShortsConvenienceMethods() throws Exception {
        int src=0x89ABCDEF;
        short []dstInit1=new short[1];
        short []dstInit2=new short[2];
        assertArrayEquals(Conversion.intToShorts(src,7,dstInit2,0,2),Conversion.intToShorts(src,7,2));
        assertArrayEquals(Conversion.intToShorts(src,0,dstInit1,0,1),Conversion.intToShorts(src,1));
        assertArrayEquals(Conversion.intToShorts(src,0,dstInit2,0,2),Conversion.intToShorts(src));
    }
    public void testLongToBytesConvenienceMethods() throws Exception {
        long src=0x0123456789ABCDEFL;
        byte []dstInit3=new byte[3];
        byte []dstInit8=new byte[8];
        assertArrayEquals(Conversion.longToBytes(src,7,dstInit8,0,8),Conversion.longToBytes(src,7,8));
        assertArrayEquals(Conversion.longToBytes(src,0,dstInit3,0,3),Conversion.longToBytes(src,3));
        assertArrayEquals(Conversion.longToBytes(src,0,dstInit8,0,8),Conversion.longToBytes(src));
    }
    public void testIntToBytesConvenienceMethods() throws Exception {
        int src=0x89ABCDEF;
        byte []dstInit3=new byte[3];
        byte []dstInit4=new byte[4];
        assertArrayEquals(Conversion.intToBytes(src,7,dstInit4,0,4),Conversion.intToBytes(src,7,4));
        assertArrayEquals(Conversion.intToBytes(src,0,dstInit3,0,3),Conversion.intToBytes(src,3));
        assertArrayEquals(Conversion.intToBytes(src,0,dstInit4,0,4),Conversion.intToBytes(src));
    }
    public void testShortToBytesConvenienceMethods() throws Exception {
        short src=0x7DEF;
        byte []dstInit1=new byte[1];
        byte []dstInit2=new byte[2];
        assertArrayEquals(Conversion.shortToBytes(src,7,dstInit2,0,2),Conversion.shortToBytes(src,7,2));
        assertArrayEquals(Conversion.shortToBytes(src,0,dstInit1,0,1),Conversion.shortToBytes(src,1));
        assertArrayEquals(Conversion.shortToBytes(src,0,dstInit2,0,2),Conversion.shortToBytes(src));
    }
    public void testLongToHexsConvenienceMethods() throws Exception {
        long src=0x0123456789ABCDEFL;
        String dstInit3="000";
        String dstInit16="0000000000000000";
        assertEquals(Conversion.longToHexs(src,3,dstInit16,0,16),Conversion.longToHexs(src,3,16));
        assertEquals(Conversion.longToHexs(src,0,dstInit3,0,3),Conversion.longToHexs(src,3));
        assertEquals(Conversion.longToHexs(src,0,dstInit16,0,16),Conversion.longToHexs(src));
    }
    public void testIntToHexsConvenienceMethods() throws Exception {
        int src=0x89ABCDEF;
        String dstInit3="000";
        String dstInit8="00000000";
        assertEquals(Conversion.intToHexs(src,3,dstInit8,0,8),Conversion.intToHexs(src,3,8));
        assertEquals(Conversion.intToHexs(src,0,dstInit3,0,3),Conversion.intToHexs(src,3));
        assertEquals(Conversion.intToHexs(src,0,dstInit8,0,8),Conversion.intToHexs(src));
    }
    public void testShortToHexsConvenienceMethods() throws Exception {
        short src=0x7DEF;
        String dstInit3="000";
        String dstInit4="0000";
        assertEquals(Conversion.shortToHexs(src,3,dstInit4,0,4),Conversion.shortToHexs(src,3,4));
        assertEquals(Conversion.shortToHexs(src,0,dstInit3,0,3),Conversion.shortToHexs(src,3));
        assertEquals(Conversion.shortToHexs(src,0,dstInit4,0,4),Conversion.shortToHexs(src));
    }
    public void testByteToHexsConvenienceMethods() throws Exception {
        byte src=0x7D;
        String dstInit1="0";
        String dstInit2="00";
        assertEquals(Conversion.byteToHexs(src,3,dstInit2,0,2),Conversion.byteToHexs(src,3,2));
        assertEquals(Conversion.byteToHexs(src,0,dstInit1,0,1),Conversion.byteToHexs(src,1));
        assertEquals(Conversion.byteToHexs(src,0,dstInit2,0,2),Conversion.byteToHexs(src));
    }
    public void testLongToBoolsConvenienceMethods() throws Exception {
        long src=0x0123456789ABCDEFL;
        boolean[] dstInit3=new boolean[3];
        boolean[] dstInit64=new boolean[64];
        assertArrayEquals(Conversion.longToBools(src,0,dstInit64,0,64),Conversion.longToBools(src,0,64));
        assertArrayEquals(Conversion.longToBools(src,0,dstInit3,0,3),Conversion.longToBools(src,3));
        assertArrayEquals(Conversion.longToBools(src,0,dstInit64,0,64),Conversion.longToBools(src));
    }
    public void testIntToBoolsConvenienceMethods() throws Exception {
        int src=0x89ABCDEF;
        boolean[] dstInit3=new boolean[3];
        boolean[] dstInit32=new boolean[32];
        assertArrayEquals(Conversion.intToBools(src,0,dstInit32,0,32),Conversion.intToBools(src,0,32));
        assertArrayEquals(Conversion.intToBools(src,0,dstInit3,0,3),Conversion.intToBools(src,3));
        assertArrayEquals(Conversion.intToBools(src,0,dstInit32,0,32),Conversion.intToBools(src));
    }
    public void testShortToBoolsConvenienceMethods() throws Exception {
        short src=0x7DEF;
        boolean[] dstInit3=new boolean[3];
        boolean[] dstInit16=new boolean[16];
        assertArrayEquals(Conversion.shortToBools(src,0,dstInit16,0,16),Conversion.shortToBools(src,0,16));
        assertArrayEquals(Conversion.shortToBools(src,0,dstInit3,0,3),Conversion.shortToBools(src,3));
        assertArrayEquals(Conversion.shortToBools(src,0,dstInit16,0,16),Conversion.shortToBools(src));
    }
    public void testByteToBoolsConvenienceMethods() throws Exception {
        byte src=0x7F;
        boolean[] dstInit3=new boolean[3];
        boolean[] dstInit8=new boolean[8];
        assertArrayEquals(Conversion.byteToBools(src,0,dstInit8,0,8),Conversion.byteToBools(src,0,8));
        assertArrayEquals(Conversion.byteToBools(src,0,dstInit3,0,3),Conversion.byteToBools(src,3));
        assertArrayEquals(Conversion.byteToBools(src,0,dstInit8,0,8),Conversion.byteToBools(src));
    }
    public void testHexToBoolsConvenienceMethods() throws Exception {
        char src='7';
        boolean[] dstInit3=new boolean[3];
        boolean[] dstInit4=new boolean[4];
        assertArrayEquals(Conversion.hexToBools(src,0,dstInit4,0,4),Conversion.hexToBools(src,0,4));
        assertArrayEquals(Conversion.hexToBools(src,0,dstInit3,0,3),Conversion.hexToBools(src,3));
        assertArrayEquals(Conversion.hexToBools(src,0,dstInit4,0,4),Conversion.hexToBools(src));
    }

    public void testLongsToIntsConvenienceMethods() throws Exception {
            long[] src={0x0123456789ABCDEFL};
    int []dstInit1=new int[1];
    int []dstInit2=new int[2];
    assertArrayEquals(Conversion.longsToInts(src,1,dstInit1,0,1),Conversion.longsToInts(src,1,1));
    assertArrayEquals(Conversion.longsToInts(src,0,dstInit1,0,1),Conversion.longsToInts(src,1));
    assertArrayEquals(Conversion.longsToInts(src,0,dstInit2,0,2),Conversion.longsToInts(src));
    }
    public void testLongsToShortsConvenienceMethods() throws Exception {
            long[] src={0x0123456789ABCDEFL};
    short []dstInit3=new short[3];
    short []dstInit4=new short[4];
    assertArrayEquals(Conversion.longsToShorts(src,1,dstInit3,0,3),Conversion.longsToShorts(src,1,3));
    assertArrayEquals(Conversion.longsToShorts(src,0,dstInit3,0,3),Conversion.longsToShorts(src,3));
    assertArrayEquals(Conversion.longsToShorts(src,0,dstInit4,0,4),Conversion.longsToShorts(src));
    }
    public void testIntsToShortsConvenienceMethods() throws Exception {
            int[] src={0x89ABCDEF};
    short []dstInit1=new short[1];
    short []dstInit2=new short[2];
    assertArrayEquals(Conversion.intsToShorts(src,1,dstInit1,0,1),Conversion.intsToShorts(src,1,1));
    assertArrayEquals(Conversion.intsToShorts(src,0,dstInit1,0,1),Conversion.intsToShorts(src,1));
    assertArrayEquals(Conversion.intsToShorts(src,0,dstInit2,0,2),Conversion.intsToShorts(src));
    }
    public void testLongsToBytesConvenienceMethods() throws Exception {
            long[] src={0x0123456789ABCDEFL};
    byte []dstInit3=new byte[3];
    byte []dstInit8=new byte[8];
    assertArrayEquals(Conversion.longsToBytes(src,5,dstInit3,0,3),Conversion.longsToBytes(src,5,3));
    assertArrayEquals(Conversion.longsToBytes(src,0,dstInit3,0,3),Conversion.longsToBytes(src,3));
    assertArrayEquals(Conversion.longsToBytes(src,0,dstInit8,0,8),Conversion.longsToBytes(src));
    }
    public void testIntsToBytesConvenienceMethods() throws Exception {
            int[] src={0x89ABCDEF};
    byte []dstInit3=new byte[3];
    byte []dstInit4=new byte[4];
    assertArrayEquals(Conversion.intsToBytes(src,1,dstInit3,0,3),Conversion.intsToBytes(src,1,3));
    assertArrayEquals(Conversion.intsToBytes(src,0,dstInit3,0,3),Conversion.intsToBytes(src,3));
    assertArrayEquals(Conversion.intsToBytes(src,0,dstInit4,0,4),Conversion.intsToBytes(src));
    }
    public void testShortsToBytesConvenienceMethods() throws Exception {
            short[] src={0x7DEF};
    byte []dstInit1=new byte[1];
    byte []dstInit2=new byte[2];
    assertArrayEquals(Conversion.shortsToBytes(src,1,dstInit1,0,1),Conversion.shortsToBytes(src,1,1));
    assertArrayEquals(Conversion.shortsToBytes(src,0,dstInit1,0,1),Conversion.shortsToBytes(src,1));
    assertArrayEquals(Conversion.shortsToBytes(src,0,dstInit2,0,2),Conversion.shortsToBytes(src));
    }
    public void testLongsToHexsConvenienceMethods() throws Exception {
            long[] src={0x0123456789ABCDEFL};
    String dstInit3="000";
    String dstInit16="0000000000000000";
    assertEquals(Conversion.longsToHexs(src,13,dstInit3,0,3),Conversion.longsToHexs(src,13,3));
    assertEquals(Conversion.longsToHexs(src,0,dstInit3,0,3),Conversion.longsToHexs(src,3));
    assertEquals(Conversion.longsToHexs(src,0,dstInit16,0,16),Conversion.longsToHexs(src));
    }
`=test`



        `` /*public void testIntsToBytes() throws Exception{
        int []src=new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        assertArrayEquals(new byte[2],Conversion.intsToBytes(src,0,new byte[2],0,0));
        assertArrayEquals(new byte[]{0x12,0x34},Conversion.intsToBytes(src,0,new byte[]{0x12,0x34},0,0));
        byte[] dst = new byte[24];
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.intsToBytes(src, 0 * 32, dst, 0 * 4, 4 * 4));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.intsToBytes(src, 0 * 32, dst, 1 * 4, 4 * 4));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.intsToBytes(src, 0 * 32, dst, 5 * 4, 1 * 4));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.intsToBytes(src, 0 * 32, dst, 3 * 4, 2 * 4));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.intsToBytes(src, 1 * 32, dst, 3 * 4, 2 * 4));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.intsToBytes(src, 2 * 32, dst, 3 * 4, 2 * 4));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0xE6},Conversion.intsToBytes(src, 4 * 32 - 8, dst, 3 * 8 - 1, 1));
        assertArrayEquals(new byte[] {(byte)0x01,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0xE6},Conversion.intsToBytes(src, 1 * 32 - 8, dst, 0, 1));
        byte[] dst2 = new byte[7];
        assertArrayEquals(new byte[] {(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B, (byte)0x00},Conversion.intsToBytes(src,24,dst2,0,6));
        assertArrayEquals(new byte[] {(byte)0x01,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB, (byte)0x89, (byte)0x5B},Conversion.intsToBytes(src,24,dst2,1,6));
        assertArrayEquals(new byte[] {(byte)0x01,(byte)0x23,(byte)0xEF,(byte)0xCD,(byte)0xAB, (byte)0x89, (byte)0x5B},Conversion.intsToBytes(src,16,dst2,1,1));
        assertArrayEquals(new byte[] {(byte)0x01,(byte)0x89,(byte)0x5B,(byte)0xCD,(byte)0xAB, (byte)0x89, (byte)0x5B},Conversion.intsToBytes(src,2*32 - 8,dst2,1,2));
        assertArrayEquals(new byte[] {(byte)0x01,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A, (byte)0xE6, (byte)0x5B},Conversion.intsToBytes(src,3*32 - 8,dst2,1,5));
    }  */    ``
    boolean hasMethod(String methodName){
        Method[] methods = this.getClass().getMethods();
        for(Method m:methods){
            if(m.getName().equals(methodName)) return true;
        }
        return false;
    }
    public void testTestsToCheckImplemented() throws Exception {
    ``for(t <- testsToCheck){``
        if(!hasMethod("`t`")) fail("test `t` not implemented");
    ``}``
}`all`

}
`renameOutput:test;"src/test/java/uk/co/nimp/util/ConversionTest.java"`
`renameOutput:impl;"src/main/java/uk/co/nimp/util/Conversion.java"`