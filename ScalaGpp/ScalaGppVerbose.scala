inputFile=/home/seb/dev/GitHub/NimpJvmLib/ScalaGpp/Num.ScalaGpp.java
outputName before execution="src/main/java/uk/co/nimp/util/Conversion.java"
code:
scalaGppEnv.generateOutput(_=>{
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


scalaGppEnv.out.append("""/*******************************************************************************
""");scalaGppEnv.out.append(""" * Licensed to the Apache Software Foundation (ASF) under one
""");scalaGppEnv.out.append(""" * or more contributor license agreements.  See the NOTICE file
""");scalaGppEnv.out.append(""" * distributed with this work for additional information
""");scalaGppEnv.out.append(""" * regarding copyright ownership.  The ASF licenses this file
""");scalaGppEnv.out.append(""" * to you under the Apache License, Version 2.0 (the
""");scalaGppEnv.out.append(""" * "License"); you may not use this file except in compliance
""");scalaGppEnv.out.append(""" * with the License.  You may obtain a copy of the License at
""");scalaGppEnv.out.append(""" *
""");scalaGppEnv.out.append(""" * http://www.apache.org/licenses/LICENSE-2.0
""");scalaGppEnv.out.append(""" *
""");scalaGppEnv.out.append(""" * Unless required by applicable law or agreed to in writing,
""");scalaGppEnv.out.append(""" * software distributed under the License is distributed on an
""");scalaGppEnv.out.append(""" * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
""");scalaGppEnv.out.append(""" * KIND, either express or implied.  See the License for the
""");scalaGppEnv.out.append(""" * specific language governing permissions and limitations
""");scalaGppEnv.out.append(""" * under the License.
""");scalaGppEnv.out.append(""" *******************************************************************************/
""");scalaGppEnv.out.append("""package uk.co.nimp.util;
""");scalaGppEnv.out.append("""import java.lang.String;
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""/**
""");scalaGppEnv.out.append(""" * Static methods to convert a type into another, with endianness and bit ordering awareness.<p>
""");scalaGppEnv.out.append(""" * The methods names follow a naming rule:
""");scalaGppEnv.out.append(""" * <pre>
""");scalaGppEnv.out.append(""" * &ltsource type&gt[source endianness][source bit ordering]To&ltdestination type&gt[destination endianness][destination bit ordering]
""");scalaGppEnv.out.append(""" * Source/Destination type fields: either of the following. An 's' added at the end indicate an array
""");scalaGppEnv.out.append(""" *  - "bool"
""");scalaGppEnv.out.append(""" *  - "byte"
""");scalaGppEnv.out.append(""" *  - "int"
""");scalaGppEnv.out.append(""" *  - "long"
""");scalaGppEnv.out.append(""" *  - "Hex": a String containing hexadecimal digits
""");scalaGppEnv.out.append(""" *  - "HexDigit": a Char containing an hexadecimal digit
""");scalaGppEnv.out.append(""" * Endianness field: little endian is the default, in this case the field is absent. In case of big endian, the field is "Be".
""");scalaGppEnv.out.append(""" * Bit ordering: Lsb0 is the default, in this case the field is absent. In case of Msb0, the field is "M0".
""");scalaGppEnv.out.append(""" *
""");scalaGppEnv.out.append(""" * Example: intBeM0ToHex convert an int with big endian byte order and Msb0 bit order into its hexadecimal string representation
""");scalaGppEnv.out.append(""" * </pre>
""");scalaGppEnv.out.append(""" * Most of the methods provide only default encoding for destination, this limits the number of ways to do one thing.
""");scalaGppEnv.out.append(""" * Unless you are dealing with data from/to outside of the JVM platform, you should not need to use "Be" and "M0" methods.
""");scalaGppEnv.out.append(""" * <p>Development status: work on going, only a part of the little endian, Lsb0 methods implemented so far</p>
""");scalaGppEnv.out.append(""" * @author Sebastien Riou
""");scalaGppEnv.out.append(""" * @version $Id:$
""");scalaGppEnv.out.append(""" * @since
""");scalaGppEnv.out.append(""" * gpp timestamp: """);scalaGppEnv.out.append(scalaGppEnv.toString({TIMESTAMP}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append(""" */
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""public class Conversion{
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    private static String getInitializedString(int nChars){
""");scalaGppEnv.out.append("""        StringBuffer out=new java.lang.StringBuffer(nChars);
""");scalaGppEnv.out.append("""        for(int i=0;i<nChars;i++) out.append('0');
""");scalaGppEnv.out.append("""        return out.toString();
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * convert an hexadecimal digit into an int using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * '1' is converted to 1
""");scalaGppEnv.out.append("""     * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""     * @return  an int equals to <code>hexDigit</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static int hexDigitToInt(char hexDigit) {
""");scalaGppEnv.out.append("""        switch(hexDigit){
""");for(i <- 0 to 9){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append("""': return """);scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append(""";
""");}

for(i <- 10 to 15){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i).toLower}));scalaGppEnv.out.append("""'://fall through
""");scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i)}));scalaGppEnv.out.append("""': return """);scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append(""";
""");}

scalaGppEnv.out.append("""            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * convert an hexadecimal digit into an int using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * '1' is converted to 8
""");scalaGppEnv.out.append("""     * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""     * @return  an int equals to <code>hexDigit</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static int hexDigitM0ToInt(char hexDigit) {
""");scalaGppEnv.out.append("""        switch(hexDigit){
""");for(i <- 0 to 9){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append("""': return 0x""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigitM0(i)}));scalaGppEnv.out.append(""";
""");}

for(i <- 10 to 15){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i).toLower}));scalaGppEnv.out.append("""'://fall through
""");scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i)}));scalaGppEnv.out.append("""': return 0x""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigitM0(i)}));scalaGppEnv.out.append(""";
""");}

scalaGppEnv.out.append("""            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * convert an hexadecimal digit into binary using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * '1' is converted as follow: (1, 0, 0, 0)
""");scalaGppEnv.out.append("""     * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""     * @return  a boolean array with the binary representation of <code>hexDigit</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static boolean[] hexDigitToBools(char hexDigit) {
""");scalaGppEnv.out.append("""        switch(hexDigit){
""");for(i <- 0 to 9){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append("""': return new boolean[]{""");scalaGppEnv.out.append(scalaGppEnv.toString({Util.arrayToString(Conv.intToBools(i,0,4),",")}));scalaGppEnv.out.append("""};
""");}

for(i <- 10 to 15){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i).toLower}));scalaGppEnv.out.append("""'://fall through
""");scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i)}));scalaGppEnv.out.append("""': return new boolean[]{""");scalaGppEnv.out.append(scalaGppEnv.toString({Util.arrayToString(Conv.intToBools(i,0,4),",")}));scalaGppEnv.out.append("""};
""");}

scalaGppEnv.out.append("""            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""    * Convert an hexadecimal digit into binary using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""    * '1' is converted as follow: (0, 0, 0, 1)
""");scalaGppEnv.out.append("""    * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""    * @return  a boolean array with the binary representation of <code>hexDigit</code>
""");scalaGppEnv.out.append("""    */
""");scalaGppEnv.out.append("""    public static boolean[] hexDigitM0ToBools(char hexDigit) {
""");scalaGppEnv.out.append("""        switch(hexDigit){
""");for(i <- 0 to 9){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append("""': return new boolean[]{""");scalaGppEnv.out.append(scalaGppEnv.toString({Util.arrayToString(Conv.intToBools(i,0,4).reverse,",")}));scalaGppEnv.out.append("""};
""");}

for(i <- 10 to 15){

scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i).toLower}));scalaGppEnv.out.append("""'://fall through
""");scalaGppEnv.out.append("""                case '""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i)}));scalaGppEnv.out.append("""': return new boolean[]{""");scalaGppEnv.out.append(scalaGppEnv.toString({Util.arrayToString(Conv.intToBools(i,0,4).reverse,",")}));scalaGppEnv.out.append("""};
""");}

scalaGppEnv.out.append("""            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '1'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char boolsToHexDigit(boolean[] src){return boolsToHexDigit(src,0);}
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   """);scalaGppEnv.out.append("""/**
""");scalaGppEnv.out.append("""     * Convert an array of boolean into a char using the default (little endian, Lsb0) byte and bit ordering.<p>
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
""");scalaGppEnv.out.append("""     * @param dstInit initial value of the destination char
""");scalaGppEnv.out.append("""     * @param dstPos the position of the lsb, in bits, in the result char
""");scalaGppEnv.out.append("""     * @param nBools the number of boolean to convert
""");scalaGppEnv.out.append("""     * @return a char containing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""     public static char boolsToHex(
""");scalaGppEnv.out.append("""            boolean[] src,
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            char dstInit,
""");scalaGppEnv.out.append("""            int dstPos,
""");scalaGppEnv.out.append("""            int nBools){
""");scalaGppEnv.out.append("""        if(0==nBools) return dstInit;
""");scalaGppEnv.out.append("""        int out = 0;
""");scalaGppEnv.out.append("""        if(nBools<4) out = hexDigitToInt(dstInit);
""");scalaGppEnv.out.append("""        int shift=0;
""");scalaGppEnv.out.append("""        for(int i = 0; i< nBools;i++){
""");scalaGppEnv.out.append("""            shift = i*1+dstPos;
""");scalaGppEnv.out.append("""            int bits = ((src[i+srcPos]) ? 1 : 0) << shift;
""");scalaGppEnv.out.append("""            int mask = 0x1 << shift;
""");scalaGppEnv.out.append("""            out = (out & ~mask) | bits;
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        if(shift>=4) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 4");
""");scalaGppEnv.out.append("""        return intToHexDigit(out);
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""         * Convert a char into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
""");scalaGppEnv.out.append("""         *
""");scalaGppEnv.out.append("""         * @param src the char to convert
""");scalaGppEnv.out.append("""         * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
""");scalaGppEnv.out.append("""         * @param dst the destination array
""");scalaGppEnv.out.append("""         * @param dstPos the position in <code>dst</code> where to copy the result
""");scalaGppEnv.out.append("""         * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
""");scalaGppEnv.out.append("""         * @return <code>dst</code>
""");scalaGppEnv.out.append("""         */
""");scalaGppEnv.out.append("""        public static boolean[] hexToBools(
""");scalaGppEnv.out.append("""                char src,
""");scalaGppEnv.out.append("""                int srcPos,
""");scalaGppEnv.out.append("""                boolean[] dst,
""");scalaGppEnv.out.append("""                int dstPos,
""");scalaGppEnv.out.append("""                int nBools){
""");scalaGppEnv.out.append("""            if(0==nBools) return dst;
""");scalaGppEnv.out.append("""            int shift=0;
""");scalaGppEnv.out.append("""            assert((nBools-1)*1<4-srcPos);
""");scalaGppEnv.out.append("""            int srcInt = hexDigitToInt(src);
""");scalaGppEnv.out.append("""            for(int i = 0; i< nBools;i++){
""");scalaGppEnv.out.append("""                shift = i*1+srcPos;
""");scalaGppEnv.out.append("""                int bit = 0x1 & (srcInt >> shift);
""");scalaGppEnv.out.append("""                dst[dstPos+i]= (bit!=0);
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""            return dst;
""");scalaGppEnv.out.append("""        } """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '1'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position of the lsb to start the conversion
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char boolsToHexDigit(boolean[] src, int srcPos){
""");scalaGppEnv.out.append("""        if(src.length>srcPos+3 && src[srcPos+3]){
""");scalaGppEnv.out.append("""            if(src.length>srcPos+2 && src[srcPos+2]){
""");scalaGppEnv.out.append("""                if(src.length>srcPos+1 && src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'F';
""");scalaGppEnv.out.append("""                    else return 'E';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'D';
""");scalaGppEnv.out.append("""                    else return 'C';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }else{
""");scalaGppEnv.out.append("""                if(src.length>srcPos+1 && src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'B';
""");scalaGppEnv.out.append("""                    else return 'A';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '9';
""");scalaGppEnv.out.append("""                    else return '8';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }else{
""");scalaGppEnv.out.append("""            if(src.length>srcPos+2 && src[srcPos+2]){
""");scalaGppEnv.out.append("""                if(src.length>srcPos+1 && src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '7';
""");scalaGppEnv.out.append("""                    else return '6';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '5';
""");scalaGppEnv.out.append("""                    else return '4';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }else{
""");scalaGppEnv.out.append("""                if(src.length>srcPos+1 && src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '3';
""");scalaGppEnv.out.append("""                    else return '2';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '1';
""");scalaGppEnv.out.append("""                    else return '0';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '8'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     * @warning src.length must be >= 4.
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char boolsToHexDigitM0_4bits(boolean[] src){return boolsToHexDigitM0_4bits(src,0);}
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""                
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '8'
""");scalaGppEnv.out.append("""    *  (1,0,0,1,1,0,1,0) with srcPos = 3 is converted to 'D'
""");scalaGppEnv.out.append("""    * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position of the lsb to start the conversion
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""    * @warning src.length must be 8 at most.
""");scalaGppEnv.out.append("""    * @warning srcPos+4 must be <= src.length.
""");scalaGppEnv.out.append("""    */
""");scalaGppEnv.out.append("""   """);scalaGppEnv.out.append("""public static char boolsToHexDigitM0_4bits(boolean[] src, int srcPos){
""");scalaGppEnv.out.append("""        if(src.length>8) throw new IllegalArgumentException("src.length>8: src.length="+src.length);
""");scalaGppEnv.out.append("""        if(src.length-srcPos<4) throw new IllegalArgumentException("src.length-srcPos<4: src.length="+src.length+", srcPos="+srcPos);
""");scalaGppEnv.out.append("""        if(src[srcPos+3]){
""");scalaGppEnv.out.append("""            if(src[srcPos+2]){
""");scalaGppEnv.out.append("""                if(src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'F';
""");scalaGppEnv.out.append("""                    else return '7';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'B';
""");scalaGppEnv.out.append("""                    else return '3';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }else{
""");scalaGppEnv.out.append("""                if(src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'D';
""");scalaGppEnv.out.append("""                    else return '5';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '9';
""");scalaGppEnv.out.append("""                    else return '1';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }else{
""");scalaGppEnv.out.append("""            if(src[srcPos+2]){
""");scalaGppEnv.out.append("""                if(src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'E';
""");scalaGppEnv.out.append("""                    else return '6';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'A';
""");scalaGppEnv.out.append("""                    else return '2';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }else{
""");scalaGppEnv.out.append("""                if(src[srcPos+1]){
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return 'C';
""");scalaGppEnv.out.append("""                    else return '4';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src[srcPos]) return '8';
""");scalaGppEnv.out.append("""                    else return '0';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * Convert the first 4 bits of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '8'
""");scalaGppEnv.out.append("""    *  (1,0,0,0,0,0,0,0,   0,0,0,0,0,1,0,0) is converted to '4'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char boolsBeM0ToHexDigit(boolean[] src){return boolsBeM0ToHexDigit(src,0);}
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a part of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) with srcPos = 0 is converted as follow: '8'
""");scalaGppEnv.out.append("""     * (1,0,0,0,0,0,0,0,   0,0,0,1,0,1,0,0) with srcPos = 2 is converted to '5'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position of the lsb to start the conversion
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char boolsBeM0ToHexDigit(boolean[] src, int srcPos){
""");scalaGppEnv.out.append("""        int beSrcPos = src.length-1-srcPos;
""");scalaGppEnv.out.append("""        int srcLen = Math.min(4,beSrcPos+1);
""");scalaGppEnv.out.append("""        boolean []paddedSrc = new boolean[4];
""");scalaGppEnv.out.append("""        System.arraycopy(src,beSrcPos+1-srcLen,paddedSrc,4-srcLen,srcLen);
""");scalaGppEnv.out.append("""        src = paddedSrc;
""");scalaGppEnv.out.append("""        srcPos=0;
""");scalaGppEnv.out.append("""        if(src[srcPos]){
""");scalaGppEnv.out.append("""            if(src.length>srcPos+1 && src[srcPos+1]){
""");scalaGppEnv.out.append("""                if(src.length>srcPos+2 && src[srcPos+2]){
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return 'F';
""");scalaGppEnv.out.append("""                    else return 'E';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return 'D';
""");scalaGppEnv.out.append("""                    else return 'C';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }else{
""");scalaGppEnv.out.append("""                if(src.length>srcPos+2 && src[srcPos+2]){
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return 'B';
""");scalaGppEnv.out.append("""                    else return 'A';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return '9';
""");scalaGppEnv.out.append("""                    else return '8';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }else{
""");scalaGppEnv.out.append("""            if(src.length>srcPos+1 && src[srcPos+1]){
""");scalaGppEnv.out.append("""                if(src.length>srcPos+2 && src[srcPos+2]){
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return '7';
""");scalaGppEnv.out.append("""                    else return '6';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return '5';
""");scalaGppEnv.out.append("""                    else return '4';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }else{
""");scalaGppEnv.out.append("""                if(src.length>srcPos+2 && src[srcPos+2]){
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return '3';
""");scalaGppEnv.out.append("""                    else return '2';
""");scalaGppEnv.out.append("""                }else{
""");scalaGppEnv.out.append("""                    if(src.length>srcPos+3 && src[srcPos+3]) return '1';
""");scalaGppEnv.out.append("""                    else return '0';
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""     /**
""");scalaGppEnv.out.append("""        * Convert the 4 lsb of an int to an hexadecimal digit.<p>
""");scalaGppEnv.out.append("""        * 0 returns '0'<p>
""");scalaGppEnv.out.append("""        * 1 returns '1'<p>
""");scalaGppEnv.out.append("""        * 10 returns 'A' and so on...
""");scalaGppEnv.out.append("""        * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
""");scalaGppEnv.out.append("""        * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
""");scalaGppEnv.out.append("""        */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char intToHexDigit(int nibble){
""");scalaGppEnv.out.append("""        switch(nibble){
""");scalaGppEnv.out.append("""            case 0x0: return '0';
""");scalaGppEnv.out.append("""            case 0x1: return '1';
""");scalaGppEnv.out.append("""            case 0x2: return '2';
""");scalaGppEnv.out.append("""            case 0x3: return '3';
""");scalaGppEnv.out.append("""            case 0x4: return '4';
""");scalaGppEnv.out.append("""            case 0x5: return '5';
""");scalaGppEnv.out.append("""            case 0x6: return '6';
""");scalaGppEnv.out.append("""            case 0x7: return '7';
""");scalaGppEnv.out.append("""            case 0x8: return '8';
""");scalaGppEnv.out.append("""            case 0x9: return '9';
""");scalaGppEnv.out.append("""            case 0xA: return 'A';
""");scalaGppEnv.out.append("""            case 0xB: return 'B';
""");scalaGppEnv.out.append("""            case 0xC: return 'C';
""");scalaGppEnv.out.append("""            case 0xD: return 'D';
""");scalaGppEnv.out.append("""            case 0xE: return 'E';
""");scalaGppEnv.out.append("""            case 0xF: return 'F';
""");scalaGppEnv.out.append("""            default: throw new java.lang.IllegalArgumentException("nibble value not between 0 and 15: "+nibble);
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert the 4 lsb of an int to an hexadecimal digit encoded using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * 0 returns '0'<p>
""");scalaGppEnv.out.append("""     * 1 returns '8'<p>
""");scalaGppEnv.out.append("""     * 10 returns '5' and so on...
""");scalaGppEnv.out.append("""     * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
""");scalaGppEnv.out.append("""     * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public static char intToHexDigitM0(int nibble){
""");scalaGppEnv.out.append("""        switch(nibble){
""");scalaGppEnv.out.append("""            case 0x0: return '0';
""");scalaGppEnv.out.append("""            case 0x1: return '8';
""");scalaGppEnv.out.append("""            case 0x2: return '4';
""");scalaGppEnv.out.append("""            case 0x3: return 'C';
""");scalaGppEnv.out.append("""            case 0x4: return '2';
""");scalaGppEnv.out.append("""            case 0x5: return 'A';
""");scalaGppEnv.out.append("""            case 0x6: return '6';
""");scalaGppEnv.out.append("""            case 0x7: return 'E';
""");scalaGppEnv.out.append("""            case 0x8: return '1';
""");scalaGppEnv.out.append("""            case 0x9: return '9';
""");scalaGppEnv.out.append("""            case 0xA: return '5';
""");scalaGppEnv.out.append("""            case 0xB: return 'D';
""");scalaGppEnv.out.append("""            case 0xC: return '3';
""");scalaGppEnv.out.append("""            case 0xD: return 'B';
""");scalaGppEnv.out.append("""            case 0xE: return '7';
""");scalaGppEnv.out.append("""            case 0xF: return 'F';
""");scalaGppEnv.out.append("""            default: throw new java.lang.IllegalArgumentException("nibble value not between 0 and 15: "+nibble);
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array into a String using the default (little endian, Lsb0) byte and bit order
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
""");scalaGppEnv.out.append("""     * @param dstInit the initial value of the destination array
""");scalaGppEnv.out.append("""     * @param dstPos the position of the lsb, in boolean unit, in the result array
""");scalaGppEnv.out.append("""     * @param nBools the number of boolean to convert
""");scalaGppEnv.out.append("""     * @return <code>dst</code>, updated with the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""   public static String boolsToHexs(
""");scalaGppEnv.out.append("""            boolean[] src,
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            String dstInit,
""");scalaGppEnv.out.append("""            int dstPos,
""");scalaGppEnv.out.append("""            int nBools){
""");scalaGppEnv.out.append("""        if(0==nBools) return dstInit;
""");scalaGppEnv.out.append("""        final int inPerOut = 4;
""");scalaGppEnv.out.append("""        int inCnt = 0;
""");scalaGppEnv.out.append("""        int inIndex = dstPos % inPerOut;
""");scalaGppEnv.out.append("""        int outIndex;
""");scalaGppEnv.out.append("""        StringBuffer sb = new StringBuffer(dstInit);
""");scalaGppEnv.out.append("""        if(0 != inIndex){
""");scalaGppEnv.out.append("""            int l=java.lang.Math.min(inPerOut-inIndex, nBools);
""");scalaGppEnv.out.append("""            int bi = dstPos/inPerOut;
""");scalaGppEnv.out.append("""            sb.setCharAt(bi,boolsToHex(src,srcPos,dstInit.charAt(bi),inIndex*1,l)); //unaligned start
""");scalaGppEnv.out.append("""            inCnt += l;
""");scalaGppEnv.out.append("""            outIndex=bi+1;
""");scalaGppEnv.out.append("""        } else {
""");scalaGppEnv.out.append("""            outIndex=dstPos/inPerOut;
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        int nFullOut = (nBools - inCnt)/inPerOut;
""");scalaGppEnv.out.append("""        for(int i = 0 ;i< nFullOut;i++){
""");scalaGppEnv.out.append("""            sb.setCharAt(outIndex+i,boolsToHex(src,srcPos+inCnt,'0',0,inPerOut));
""");scalaGppEnv.out.append("""            inCnt += inPerOut;
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        int remaining = nBools-inCnt;
""");scalaGppEnv.out.append("""        if(0!=remaining) sb.setCharAt(outIndex+nFullOut,boolsToHex(src,srcPos+inCnt,dstInit.charAt(outIndex+nFullOut),0,remaining));//unaligned end
""");scalaGppEnv.out.append("""        return sb.toString();
""");scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");
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
            case t: bool_t => {

scalaGppEnv.out.append("""                (""");scalaGppEnv.out.append(scalaGppEnv.toString({srcName}));scalaGppEnv.out.append("""!=0)""");
            }
            case _ => {

scalaGppEnv.out.append("""                """);scalaGppEnv.out.append(scalaGppEnv.toString({srcName}));scalaGppEnv.out.append(""".""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append("""Value()""");
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
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""                """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""                int srcPos,
""");scalaGppEnv.out.append("""                int dstPos,
""");scalaGppEnv.out.append("""                int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,srcPos,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",dstPos,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""                """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""                int srcPos,
""");scalaGppEnv.out.append("""                int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,srcPos,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""                """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",0,""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.arrayLen("src")}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    def convenienceMethods_ns2ws(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ns2ws(srcType,dstType).capitalize+"ConvenienceMethods")
        val srcPerDst = dstType.width / srcType.width
        val sizeExpr="("+typeToName_nTypes(srcType)+"+"+(srcPerDst-1)+")/"+srcPerDst
        val sizeExpr2="("+srcType.arrayLen("src")+"+"+(srcPerDst-1)+")/"+srcPerDst
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            int dstPos,
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",sizeExpr)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,srcPos,dstInit,dstPos,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",sizeExpr)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,srcPos,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",sizeExpr)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(srcType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",sizeExpr2)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.arrayLen("src")}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""    """);
    }
    def convenienceMethods_w2ns(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_w2ns(srcType,dstType).capitalize+"ConvenienceMethods")
        val dstPerSrc = srcType.width / dstType.width
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" src,
""");scalaGppEnv.out.append("""        int srcPos,
""");scalaGppEnv.out.append("""        int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",typeToName_nTypes(dstType))}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,srcPos,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" src,
""");scalaGppEnv.out.append("""        int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",typeToName_nTypes(dstType))}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" src){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",dstPerSrc)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstPerSrc}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    def convenienceMethods_ws2ns(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ws2ns(srcType,dstType).capitalize+"ConvenienceMethods")
        val dstPerSrc = srcType.width / dstType.width
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",typeToName_nTypes(dstType))}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,srcPos,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",typeToName_nTypes(dstType))}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({typeToName_nTypes(dstType)}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit",dstPerSrc)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            return """);scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInit,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstPerSrc}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    
    def narrowsToWide(srcType: JavaType, dstType: JavaType){
        val sMask = srcType.mask+dstType.litPostFix
        val nS = typeToName_nTypes(srcType)
        val methodName = srcType.nickName+"sTo"+dstType.nickName.capitalize
        testsToCheck.append("test"+methodName.capitalize)
    

scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""/**
""");scalaGppEnv.out.append("""     * Convert an array of """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" into a """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" using the default (little endian, Lsb0) byte and bit ordering.<p>
""");scalaGppEnv.out.append("""     * @param src the """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position in <code>src</code>, in """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" unit, from where to start the conversion
""");scalaGppEnv.out.append("""     * @param dstInit initial value of the destination """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""     * @param dstPos the position of the lsb, in bits, in the result """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""     * @param """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""" the number of """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" to convert
""");scalaGppEnv.out.append("""     * @return a """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" containing the selected bits
""");scalaGppEnv.out.append("""    */
""");scalaGppEnv.out.append("""    public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({methodName}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""        int srcPos,
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" dstInit,
""");scalaGppEnv.out.append("""        int dstPos,
""");scalaGppEnv.out.append("""        int """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""        if(0==""");scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""") return dstInit;
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" out = dstInit;
""");scalaGppEnv.out.append("""        int shift=0;
""");scalaGppEnv.out.append("""        for(int i = 0; i< """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""";i++){
""");scalaGppEnv.out.append("""            shift = i*""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.width}));scalaGppEnv.out.append("""+dstPos;
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.decl("bits")}));scalaGppEnv.out.append(""" = (""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""")(((""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""")(""");scalaGppEnv.out.append(scalaGppEnv.toString({sMask}));scalaGppEnv.out.append(""" & """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.toNum(srcType.at("src","i+srcPos"))}));scalaGppEnv.out.append(""")) << shift);
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.decl("mask")}));scalaGppEnv.out.append(""" = (""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""")(((""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""") """);scalaGppEnv.out.append(scalaGppEnv.toString({sMask}));scalaGppEnv.out.append(""") << shift);
""");scalaGppEnv.out.append("""            out = (""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""")((out & ~mask) | bits);
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        if(shift>=""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append(""") throw new IllegalArgumentException("(""");scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append("""-1)*""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.width}));scalaGppEnv.out.append("""+dstPos is greather or equal to than """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append("""");
""");scalaGppEnv.out.append("""        return out;
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);}


    def narrowsToWides(srcType: JavaType, dstType: JavaType){
        val nS = "n"+srcType.nickName.capitalize+"s"
        val narrowsToWideMethodName = srcType.nickName+"sTo"+dstType.nickName.capitalize
        val methodName = narrowsToWideMethodName+"s"
        testsToCheck.append("test"+methodName.capitalize)
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""/**
""");scalaGppEnv.out.append("""         * Convert a byte array into a """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" array using the default (little endian, Lsb0) byte and bit order
""");scalaGppEnv.out.append("""         * @param src the """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" array to convert
""");scalaGppEnv.out.append("""         * @param srcPos the position in <code>src</code>, in """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" unit, from where to start the conversion
""");scalaGppEnv.out.append("""         * @param dst the destination array
""");scalaGppEnv.out.append("""         * @param dstPos the position of the lsb, in """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" unit, in the result array
""");scalaGppEnv.out.append("""         * @param """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""" the number of """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" to convert
""");scalaGppEnv.out.append("""         * @return <code>dst</code>, updated with the selected bits
""");scalaGppEnv.out.append("""         */
""");scalaGppEnv.out.append("""    public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({methodName}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""        int srcPos,
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dst")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""        int dstPos,
""");scalaGppEnv.out.append("""        int """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""            if(0==""");scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""") return dst;
""");scalaGppEnv.out.append("""            final int inPerOut = """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width/srcType.width}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""            int inCnt = 0;
""");scalaGppEnv.out.append("""            int inIndex = dstPos % inPerOut;
""");scalaGppEnv.out.append("""            int outIndex;
""");scalaGppEnv.out.append("""            if(0 != inIndex){
""");scalaGppEnv.out.append("""                int l=java.lang.Math.min(inPerOut-inIndex, """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""                int bi = dstPos/inPerOut;
""");scalaGppEnv.out.append("""                dst[bi]=""");scalaGppEnv.out.append(scalaGppEnv.toString({narrowsToWideMethodName}));scalaGppEnv.out.append("""(src,srcPos,dst[bi],inIndex*""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.width}));scalaGppEnv.out.append(""",l); //unaligned start
""");scalaGppEnv.out.append("""                inCnt += l;
""");scalaGppEnv.out.append("""                outIndex=bi+1;
""");scalaGppEnv.out.append("""            } else {
""");scalaGppEnv.out.append("""                outIndex=dstPos/inPerOut;
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""            int nFullOut = (""");scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append(""" - inCnt)/inPerOut;
""");scalaGppEnv.out.append("""            for(int i = 0 ;i< nFullOut;i++){
""");scalaGppEnv.out.append("""                dst[outIndex+i]=""");scalaGppEnv.out.append(scalaGppEnv.toString({narrowsToWideMethodName}));scalaGppEnv.out.append("""(src,srcPos+inCnt,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literalDec(0)}));scalaGppEnv.out.append(""",0,inPerOut);
""");scalaGppEnv.out.append("""                inCnt += inPerOut;
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""            int remaining = """);scalaGppEnv.out.append(scalaGppEnv.toString({nS}));scalaGppEnv.out.append("""-inCnt;
""");scalaGppEnv.out.append("""            if(0!=remaining) dst[outIndex+nFullOut]=""");scalaGppEnv.out.append(scalaGppEnv.toString({narrowsToWideMethodName}));scalaGppEnv.out.append("""(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
""");scalaGppEnv.out.append("""            return dst;
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);}

    def wideToNarrows(srcType: JavaType, dstType: JavaType){
        val sMask = dstType.mask+dstType.litPostFix
        val nD = "n"+dstType.nickName.capitalize+"s"
        val methodName = srcType.nickName+"To"+dstType.nickName.capitalize+"s"
        testsToCheck.append("test"+methodName.capitalize)
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");dstType match{
            case c: hexDigit_t => {
        

scalaGppEnv.out.append("""        /**
""");scalaGppEnv.out.append("""                 * Convert a """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" into an array of  """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" using the default (little endian, Lsb0) byte and bit ordering.<p>
""");scalaGppEnv.out.append("""                 *
""");scalaGppEnv.out.append("""                 * @param src the """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" to convert
""");scalaGppEnv.out.append("""                 * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
""");scalaGppEnv.out.append("""                 * @param dstInit the initial value for the result String
""");scalaGppEnv.out.append("""                 * @param dstPos the position in <code>dst</code> where to copy the result
""");scalaGppEnv.out.append("""                 * @param """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""" the number of """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
""");scalaGppEnv.out.append("""                 * @return <code>dst</code>
""");scalaGppEnv.out.append("""                 */
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({methodName}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" src,
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInit")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int dstPos,
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""                if(0==""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""") return dstInit;
""");scalaGppEnv.out.append("""                StringBuffer sb = new StringBuffer(dstInit);
""");scalaGppEnv.out.append("""                int shift=0;
""");/*for(; `nD`>(`srcType.width`+`dstType.width/2`-srcPos)/`dstType.width`);`nD`--){
                                sb.setCharAt(dstPos+`nD`-1,'0');
                            }      */ 

scalaGppEnv.out.append("""                assert((""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""-1)*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append("""<""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.width}));scalaGppEnv.out.append("""-srcPos);
""");scalaGppEnv.out.append("""                for(int i = 0; i< """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""";i++){
""");scalaGppEnv.out.append("""                    shift = i*4+srcPos;
""");scalaGppEnv.out.append("""                    int bits = (int)(0xF & (src >> shift));
""");scalaGppEnv.out.append("""                    sb.setCharAt(dstPos+i,intToHexDigit(bits));
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""                return sb.toString();
""");scalaGppEnv.out.append("""            }
""");}
        case _ => {
        

scalaGppEnv.out.append("""        /**
""");scalaGppEnv.out.append("""             * Convert a """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" into an array of  """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" using the default (little endian, Lsb0) byte and bit ordering.<p>
""");scalaGppEnv.out.append("""             *
""");scalaGppEnv.out.append("""             * @param src the """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" to convert
""");scalaGppEnv.out.append("""             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
""");scalaGppEnv.out.append("""             * @param dst the destination array
""");scalaGppEnv.out.append("""             * @param dstPos the position in <code>dst</code> where to copy the result
""");scalaGppEnv.out.append("""             * @param """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""" the number of """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
""");scalaGppEnv.out.append("""             * @return <code>dst</code>
""");scalaGppEnv.out.append("""             */
""");scalaGppEnv.out.append("""        public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({methodName}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" src,
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dst")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int dstPos,
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""                if(0==""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""") return dst;
""");scalaGppEnv.out.append("""                int shift=0;
""");/*for(; `nD`>(`srcType.width`+`dstType.width/2`-srcPos)/`dstType.width`;`nD`--){
                                dst[dstPos+`nD`-1]=`dstType.zeroValue`;//need to do sign extension rather than writing always 0 -> keep it simple by using assertion
                            } */

scalaGppEnv.out.append("""                assert((""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""-1)*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append("""<""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.width}));scalaGppEnv.out.append("""-srcPos);
""");scalaGppEnv.out.append("""                for(int i = 0; i< """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""";i++){
""");scalaGppEnv.out.append("""                    shift = i*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append("""+srcPos;
""");scalaGppEnv.out.append("""                    """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.boxedName}));scalaGppEnv.out.append(""" bits = (""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""")(""");scalaGppEnv.out.append(scalaGppEnv.toString({sMask}));scalaGppEnv.out.append(""" & (src >> shift));
""");scalaGppEnv.out.append("""                    dst[dstPos+i]=""");scalaGppEnv.out.append(scalaGppEnv.toString({xToY(srcType,"bits",dstType)}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""                return dst;
""");scalaGppEnv.out.append("""            }
""");}
        }

scalaGppEnv.out.append("""        """);}

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
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert part of a """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" array into an array of  """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" using the default (little endian, Lsb0) byte and bit ordering.<p>
""");scalaGppEnv.out.append("""     *
""");scalaGppEnv.out.append("""     * @param src the """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position in <code>src</code>:  the number of """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" from where to start the conversion
""");scalaGppEnv.out.append("""     * @param dst the destination array
""");scalaGppEnv.out.append("""     * @param dstPos the position in <code>dst</code> where to copy the result
""");scalaGppEnv.out.append("""     * @param """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""" the number of """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.name}));scalaGppEnv.out.append(""" to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
""");scalaGppEnv.out.append("""     * @return <code>dst</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    public static """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray}));scalaGppEnv.out.append(""" """);scalaGppEnv.out.append(scalaGppEnv.toString({methodName}));scalaGppEnv.out.append("""(
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int srcPos,
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dst")}));scalaGppEnv.out.append(""",
""");scalaGppEnv.out.append("""            int dstPos,
""");scalaGppEnv.out.append("""            int """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""        if(0==""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""") return dst;
""");scalaGppEnv.out.append("""        final int """);scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append(""" = """);scalaGppEnv.out.append(scalaGppEnv.toString({outPerInVal}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        int outCnt = 0;
""");scalaGppEnv.out.append("""        final int outIndex = srcPos % """);scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        int inIndex=srcPos/""");scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        if(0 != outIndex){
""");scalaGppEnv.out.append("""            final int nOutFirst=Math.min(""");scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append("""-outIndex, """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""            dst=""");scalaGppEnv.out.append(scalaGppEnv.toString({wideToNarrowsMethodName}));scalaGppEnv.out.append("""(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.at("src","inIndex")}));scalaGppEnv.out.append(""",outIndex*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append(""",dst,dstPos,nOutFirst); //unaligned start
""");scalaGppEnv.out.append("""            outCnt += nOutFirst;
""");scalaGppEnv.out.append("""            inIndex++;
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        final int nFullIns = (""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""" - outCnt)/""");scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        for(int i = 0;i<nFullIns;i++){
""");scalaGppEnv.out.append("""            dst=""");scalaGppEnv.out.append(scalaGppEnv.toString({wideToNarrowsMethodName}));scalaGppEnv.out.append("""(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.at("src","inIndex+i")}));scalaGppEnv.out.append(""",0,dst,dstPos+outCnt,""");scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""            outCnt += """);scalaGppEnv.out.append(scalaGppEnv.toString({outPerIn}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        final int remaining = """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""-outCnt;
""");scalaGppEnv.out.append("""        if(0!=remaining) dst=""");scalaGppEnv.out.append(scalaGppEnv.toString({wideToNarrowsMethodName}));scalaGppEnv.out.append("""(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.at("src","inIndex+nFullIns")}));scalaGppEnv.out.append(""",0,dst,dstPos+outCnt,remaining);//unaligned end
""");scalaGppEnv.out.append("""        return dst;
""");scalaGppEnv.out.append("""    }""");}
    

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



scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""}
""");"src/main/java/uk/co/nimp/util/Conversion.java"},1)