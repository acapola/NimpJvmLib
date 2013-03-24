inputFile=/home/seb/dev/GitHub/NimpJvmLib/ScalaGpp/Num.ScalaGpp.java
outputName before execution="test/uk/co/nimp/util/ConversionTest.java"
code:
scalaGppEnv.generateOutput(_=>{
/*
        TODO:
        boolsToIntsMemberwise stupid conversion of boolean array into int array of same length
        byteToBools
        bytesToBools short forms + other xsToYs short forms
         */
//import uk.co.nimp.util.Conv
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
""");scalaGppEnv.out.append("""import junit.framework.TestCase;
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""import java.lang.IllegalArgumentException;
""");scalaGppEnv.out.append("""import java.lang.Long;
""");scalaGppEnv.out.append("""import java.lang.Throwable;
""");scalaGppEnv.out.append("""import java.lang.reflect.Method;
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
""");scalaGppEnv.out.append("""public class ConversionTest extends TestCase{
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    public ConversionTest(String name) {
""");scalaGppEnv.out.append("""        super(name);
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * convert an hexadecimal digit into an int using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * '1' is converted to 1
""");scalaGppEnv.out.append("""     * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""     * @return  an int equals to <code>hexDigit</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testHexDigitToInt() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append(""",Conversion.hexDigitToInt('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""'));
""");if(i>9){

scalaGppEnv.out.append("""                assertEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append(""",Conversion.hexDigitToInt('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toLowerCase}));scalaGppEnv.out.append("""'));
""");}

}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * convert an hexadecimal digit into an int using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * '1' is converted to 8
""");scalaGppEnv.out.append("""     * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""     * @return  an int equals to <code>hexDigit</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testHexDigitM0ToInt() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals(0x""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigitM0(i)}));scalaGppEnv.out.append(""",Conversion.hexDigitM0ToInt('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""'));
""");if(i>9){

scalaGppEnv.out.append("""                assertEquals(0x""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigitM0(i)}));scalaGppEnv.out.append(""",Conversion.hexDigitM0ToInt('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toLowerCase}));scalaGppEnv.out.append("""'));
""");}

}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * convert an hexadecimal digit into binary using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * '1' is converted as follow: (1, 0, 0, 0)
""");scalaGppEnv.out.append("""     * @param hexDigit the hexadecimal digit to convert
""");scalaGppEnv.out.append("""     * @return  a boolean array with the binary representation of <code>hexDigit</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testHexDigitToBools() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertArrayEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsDecl(i, 4)}));scalaGppEnv.out.append(""", Conversion.hexDigitToBools('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""'));
""");if(i>9){

scalaGppEnv.out.append("""                assertArrayEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsDecl(i, 4)}));scalaGppEnv.out.append(""", Conversion.hexDigitToBools('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toLowerCase}));scalaGppEnv.out.append("""'));
""");}

}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
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
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testHexDigitM0ToBools() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertArrayEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsBeM0Decl(i, 4)}));scalaGppEnv.out.append(""", Conversion.hexDigitM0ToBools('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""'));
""");if(i>9){

scalaGppEnv.out.append("""                assertArrayEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsBeM0Decl(i, 4)}));scalaGppEnv.out.append(""", Conversion.hexDigitM0ToBools('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toLowerCase}));scalaGppEnv.out.append("""'));
""");}

}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '1'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testBoolsToHexDigit() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""', Conversion.boolsToHexDigit(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsDecl(i, 4)}));scalaGppEnv.out.append("""));
""");}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '1'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position of the lsb to start the conversion
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testBoolsToHexDigit_2args() throws Exception {
""");scalaGppEnv.out.append("""        boolean[] shortArray = new boolean[]{false,true,true};
""");scalaGppEnv.out.append("""        assertEquals('6', Conversion.boolsToHexDigit(shortArray,0));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsToHexDigit(shortArray,1));
""");scalaGppEnv.out.append("""        assertEquals('1', Conversion.boolsToHexDigit(shortArray,2));
""");scalaGppEnv.out.append("""        boolean[] longArray = new boolean[]{true,false,true,false,false,true,true};
""");scalaGppEnv.out.append("""        assertEquals('5', Conversion.boolsToHexDigit(longArray,0));
""");scalaGppEnv.out.append("""        assertEquals('2', Conversion.boolsToHexDigit(longArray,1));
""");scalaGppEnv.out.append("""        assertEquals('9', Conversion.boolsToHexDigit(longArray,2));
""");scalaGppEnv.out.append("""        assertEquals('C', Conversion.boolsToHexDigit(longArray,3));
""");scalaGppEnv.out.append("""        assertEquals('6', Conversion.boolsToHexDigit(longArray,4));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsToHexDigit(longArray,5));
""");scalaGppEnv.out.append("""        assertEquals('1', Conversion.boolsToHexDigit(longArray,6));
""");scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '8'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     * @warning src.length must be >= 4.
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testBoolsToHexDigitM0_bits() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""', Conversion.boolsToHexDigitM0_4bits(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsBeM0Decl(i, 4)}));scalaGppEnv.out.append("""));
""");}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
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
""");scalaGppEnv.out.append("""   """);scalaGppEnv.out.append("""public void testBoolsToHexDigitM0_4bits_2args() throws Exception {
""");scalaGppEnv.out.append("""        //boolean[] shortArray = new boolean[]{true,true,false};
""");scalaGppEnv.out.append("""        //assertEquals('6', Conversion.boolsToHexDigitM0(shortArray,0));
""");scalaGppEnv.out.append("""        //assertEquals('3', Conversion.boolsToHexDigitM0(shortArray,1));
""");scalaGppEnv.out.append("""        //assertEquals('1', Conversion.boolsToHexDigitM0(shortArray,2));
""");scalaGppEnv.out.append("""        boolean[] shortArray = new boolean[]{true,true,false,true};
""");scalaGppEnv.out.append("""        assertEquals('D', Conversion.boolsToHexDigitM0_4bits(shortArray,0));
""");scalaGppEnv.out.append("""        boolean[] longArray = new boolean[]{true,false,true,false,false,true,true};
""");scalaGppEnv.out.append("""        assertEquals('A', Conversion.boolsToHexDigitM0_4bits(longArray,0));
""");scalaGppEnv.out.append("""        assertEquals('4', Conversion.boolsToHexDigitM0_4bits(longArray,1));
""");scalaGppEnv.out.append("""        assertEquals('9', Conversion.boolsToHexDigitM0_4bits(longArray,2));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsToHexDigitM0_4bits(longArray,3));
""");scalaGppEnv.out.append("""        //assertEquals('6', Conversion.boolsToHexDigitM0(longArray,4));
""");scalaGppEnv.out.append("""        //assertEquals('3', Conversion.boolsToHexDigitM0(longArray,5));
""");scalaGppEnv.out.append("""        //assertEquals('1', Conversion.boolsToHexDigitM0(longArray,6));
""");scalaGppEnv.out.append("""        boolean[] maxLengthArray = new boolean[]{true,false,true,false,false,true,true,true};
""");scalaGppEnv.out.append("""        assertEquals('A', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,0));
""");scalaGppEnv.out.append("""        assertEquals('4', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,1));
""");scalaGppEnv.out.append("""        assertEquals('9', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,2));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,3));
""");scalaGppEnv.out.append("""        assertEquals('7', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,4));
""");scalaGppEnv.out.append("""        //assertEquals('7', Conversion.boolsToHexDigitM0(longArray,5));
""");scalaGppEnv.out.append("""        //assertEquals('3', Conversion.boolsToHexDigitM0(longArray,6));
""");scalaGppEnv.out.append("""        //assertEquals('1', Conversion.boolsToHexDigitM0(longArray,7));
""");scalaGppEnv.out.append("""        boolean[] javaDocCheck = new boolean[]{true,false,false,true,true,false,true,false};
""");scalaGppEnv.out.append("""        assertEquals('D', Conversion.boolsToHexDigitM0_4bits(javaDocCheck,3));
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""   /**
""");scalaGppEnv.out.append("""     * Convert the first 4 bits of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) is converted as follow: '8'
""");scalaGppEnv.out.append("""    *  (1,0,0,0,0,0,0,0,   0,0,0,0,0,1,0,0) is converted to '4'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testBoolsBeM0ToHexDigit() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals('""");scalaGppEnv.out.append(scalaGppEnv.toString({i.toHexString.toUpperCase}));scalaGppEnv.out.append("""', Conversion.boolsBeM0ToHexDigit(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsBeM0Decl(i, 4)}));scalaGppEnv.out.append("""));
""");}

scalaGppEnv.out.append("""        assertEquals('4', Conversion.boolsBeM0ToHexDigit(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsBeM0Decl(0x08004, 16)}));scalaGppEnv.out.append("""));
""");scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert a part of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
""");scalaGppEnv.out.append("""     * (1, 0, 0, 0) with srcPos = 0 is converted as follow: '8'
""");scalaGppEnv.out.append("""     * (1,0,0,0,0,0,0,0,   0,0,0,1,0,1,0,0) with srcPos = 2 is converted to '5'
""");scalaGppEnv.out.append("""     * @param src the boolean array to convert
""");scalaGppEnv.out.append("""     * @param srcPos the position of the lsb to start the conversion
""");scalaGppEnv.out.append("""     * @return  an hexadecimal digit representing the selected bits
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testBoolsBeM0ToHexDigit_2args() throws Exception {
""");scalaGppEnv.out.append("""        assertEquals('5', Conversion.boolsBeM0ToHexDigit(""");scalaGppEnv.out.append(scalaGppEnv.toString({intToBoolsBeM0Decl(0x08014, 16)}));scalaGppEnv.out.append(""",2));
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        boolean[] shortArray = new boolean[]{true,true,false};
""");scalaGppEnv.out.append("""        assertEquals('6', Conversion.boolsBeM0ToHexDigit(shortArray,0));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsBeM0ToHexDigit(shortArray,1));
""");scalaGppEnv.out.append("""        assertEquals('1', Conversion.boolsBeM0ToHexDigit(shortArray,2));
""");scalaGppEnv.out.append("""        boolean[] shortArray2 = """);scalaGppEnv.out.append(scalaGppEnv.toString({boolsToArrayDecl((Array(true,false,true,false,false,true,true,true)).reverse)}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        assertEquals('5', Conversion.boolsBeM0ToHexDigit(shortArray2,0));
""");scalaGppEnv.out.append("""        assertEquals('2', Conversion.boolsBeM0ToHexDigit(shortArray2,1));
""");scalaGppEnv.out.append("""        assertEquals('9', Conversion.boolsBeM0ToHexDigit(shortArray2,2));
""");scalaGppEnv.out.append("""        assertEquals('C', Conversion.boolsBeM0ToHexDigit(shortArray2,3));
""");scalaGppEnv.out.append("""        assertEquals('E', Conversion.boolsBeM0ToHexDigit(shortArray2,4));
""");scalaGppEnv.out.append("""        assertEquals('7', Conversion.boolsBeM0ToHexDigit(shortArray2,5));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsBeM0ToHexDigit(shortArray2,6));
""");scalaGppEnv.out.append("""        assertEquals('1', Conversion.boolsBeM0ToHexDigit(shortArray2,7));
""");scalaGppEnv.out.append("""        boolean[] multiBytesArray = new boolean[]{
""");scalaGppEnv.out.append("""             true, true,false,false,    true,false, true,false,
""");scalaGppEnv.out.append("""             true, true, true,false,   false, true,false, true
""");scalaGppEnv.out.append("""        };
""");scalaGppEnv.out.append("""        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,0));
""");scalaGppEnv.out.append("""        assertEquals('2', Conversion.boolsBeM0ToHexDigit(multiBytesArray,1));
""");scalaGppEnv.out.append("""        assertEquals('9', Conversion.boolsBeM0ToHexDigit(multiBytesArray,2));
""");scalaGppEnv.out.append("""        assertEquals('C', Conversion.boolsBeM0ToHexDigit(multiBytesArray,3));
""");scalaGppEnv.out.append("""        assertEquals('E', Conversion.boolsBeM0ToHexDigit(multiBytesArray,4));
""");scalaGppEnv.out.append("""        assertEquals('7', Conversion.boolsBeM0ToHexDigit(multiBytesArray,5));
""");scalaGppEnv.out.append("""        assertEquals('B', Conversion.boolsBeM0ToHexDigit(multiBytesArray,6));
""");scalaGppEnv.out.append("""        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,7));
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        assertEquals('A', Conversion.boolsBeM0ToHexDigit(multiBytesArray,8));
""");scalaGppEnv.out.append("""        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,9));
""");scalaGppEnv.out.append("""        assertEquals('2', Conversion.boolsBeM0ToHexDigit(multiBytesArray,10));
""");scalaGppEnv.out.append("""        assertEquals('9', Conversion.boolsBeM0ToHexDigit(multiBytesArray,11));
""");scalaGppEnv.out.append("""        assertEquals('C', Conversion.boolsBeM0ToHexDigit(multiBytesArray,12));
""");scalaGppEnv.out.append("""        assertEquals('6', Conversion.boolsBeM0ToHexDigit(multiBytesArray,13));
""");scalaGppEnv.out.append("""        assertEquals('3', Conversion.boolsBeM0ToHexDigit(multiBytesArray,14));
""");scalaGppEnv.out.append("""        assertEquals('1', Conversion.boolsBeM0ToHexDigit(multiBytesArray,15));
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""     /**
""");scalaGppEnv.out.append("""        * Convert the 4 lsb of an int to an hexadecimal digit.<p>
""");scalaGppEnv.out.append("""        * 0 returns '0'<p>
""");scalaGppEnv.out.append("""        * 1 returns '1'<p>
""");scalaGppEnv.out.append("""        * 10 returns 'A' and so on...
""");scalaGppEnv.out.append("""        * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
""");scalaGppEnv.out.append("""        * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
""");scalaGppEnv.out.append("""        */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testIntToHexDigit() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals('""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigit(i)}));scalaGppEnv.out.append("""',Conversion.intToHexDigit(""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append("""));
""");}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    /**
""");scalaGppEnv.out.append("""     * Convert the 4 lsb of an int to an hexadecimal digit encoded using the Msb0 bit ordering.<p>
""");scalaGppEnv.out.append("""     * 0 returns '0'<p>
""");scalaGppEnv.out.append("""     * 1 returns '8'<p>
""");scalaGppEnv.out.append("""     * 10 returns '5' and so on...
""");scalaGppEnv.out.append("""     * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
""");scalaGppEnv.out.append("""     * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
""");scalaGppEnv.out.append("""     */
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""public void testIntToHexDigitM0() throws Exception {
""");for(i <- 0 to 15){

scalaGppEnv.out.append("""            assertEquals('""");scalaGppEnv.out.append(scalaGppEnv.toString({Conv.intToHexDigitM0(i)}));scalaGppEnv.out.append("""',Conversion.intToHexDigitM0(""");scalaGppEnv.out.append(scalaGppEnv.toString({i}));scalaGppEnv.out.append("""));
""");}

scalaGppEnv.out.append("""    }""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    static String newHexs(int size){
""");scalaGppEnv.out.append("""        char []out = new char[size];
""");scalaGppEnv.out.append("""        java.util.Arrays.fill(out,'0');
""");scalaGppEnv.out.append("""        return new String(out);
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
""");scalaGppEnv.out.append("""        public void test""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType).capitalize}));scalaGppEnv.out.append("""ConvenienceMethods() throws Exception {
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""" = """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.randomArray(srcPerDst)}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""            assertEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,1));
""");scalaGppEnv.out.append("""            assertEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,1));
""");scalaGppEnv.out.append("""            assertEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.literal("0")}));scalaGppEnv.out.append(""",0,""");scalaGppEnv.out.append(scalaGppEnv.toString({srcPerDst}));scalaGppEnv.out.append("""),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2w(srcType,dstType)}));scalaGppEnv.out.append("""(src));
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    def convenienceMethods_ns2ws(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ns2ws(srcType,dstType).capitalize+"ConvenienceMethods")
        val srcPerDst = dstType.width / srcType.width
        val sizeExpr="("+typeToName_nTypes(srcType)+"+"+(srcPerDst-1)+")/"+srcPerDst
        val sizeExpr2="("+srcType.arrayLen("src")+"+"+(srcPerDst-1)+")/"+srcPerDst
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public void test""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType).capitalize}));scalaGppEnv.out.append("""ConvenienceMethods() throws Exception {
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""" = """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.randomArray(srcPerDst*2)}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInitPart",1)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInitFull",2)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,dstInitPart,0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,1));
""");scalaGppEnv.out.append("""            assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInitPart,0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,1));
""");scalaGppEnv.out.append("""            assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInitFull,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({srcPerDst*2}));scalaGppEnv.out.append("""),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ns2ws(srcType,dstType)}));scalaGppEnv.out.append("""(src));
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    def convenienceMethods_w2ns(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_w2ns(srcType,dstType).capitalize+"ConvenienceMethods")
        val dstPerSrc = srcType.width / dstType.width
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public void test""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType).capitalize}));scalaGppEnv.out.append("""ConvenienceMethods() throws Exception {
""");scalaGppEnv.out.append("""                """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" src = """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.randomValue}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInitPart",1)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInitFull",dstPerSrc)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,dstInitPart,0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInitPart,0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInitFull,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstPerSrc}));scalaGppEnv.out.append("""),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_w2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src));
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    def convenienceMethods_ws2ns(srcType: JavaType, dstType: JavaType){
        //testsToCheck.append("test"+typesToMethodName_ws2ns(srcType,dstType).capitalize+"ConvenienceMethods")
        val dstPerSrc = srcType.width / dstType.width
        

scalaGppEnv.out.append("""        """);scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        public void test""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType).capitalize}));scalaGppEnv.out.append("""ConvenienceMethods() throws Exception {
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append(""" = """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.randomArray(1)}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInitPart",1)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dstInitFull",dstPerSrc)}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,dstInitPart,0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,1,1));
""");scalaGppEnv.out.append("""            assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInitPart,0,1),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,1));
""");scalaGppEnv.out.append("""            assertArrayEquals(Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src,0,dstInitFull,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstPerSrc}));scalaGppEnv.out.append("""),Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({typesToMethodName_ws2ns(srcType,dstType)}));scalaGppEnv.out.append("""(src));
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        """);
    }
    
    def narrowsToWide(srcType: JavaType, dstType: JavaType){
        val sMask = srcType.mask+dstType.litPostFix
        val nS = typeToName_nTypes(srcType)
        val methodName = srcType.nickName+"sTo"+dstType.nickName.capitalize
        testsToCheck.append("test"+methodName.capitalize)
    

scalaGppEnv.out.append("""    """);}


    def narrowsToWides(srcType: JavaType, dstType: JavaType){
        val nS = "n"+srcType.nickName.capitalize+"s"
        val narrowsToWideMethodName = srcType.nickName+"sTo"+dstType.nickName.capitalize
        val methodName = narrowsToWideMethodName+"s"
        testsToCheck.append("test"+methodName.capitalize)
        

scalaGppEnv.out.append("""        """);}

    def wideToNarrows(srcType: JavaType, dstType: JavaType){
        val sMask = dstType.mask+dstType.litPostFix
        val nD = "n"+dstType.nickName.capitalize+"s"
        val methodName = srcType.nickName+"To"+dstType.nickName.capitalize+"s"
        testsToCheck.append("test"+methodName.capitalize)
        

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
""");scalaGppEnv.out.append("""    void """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""){
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declArray("src")}));scalaGppEnv.out.append("""=Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({boolsToSrc}));scalaGppEnv.out.append("""(srcAsBools,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.declAnonymousArray("srcAsBools.length/"+srcType.width)}));scalaGppEnv.out.append(""",0,srcAsBools.length);
""");if(dstType.name != "boolean"){

scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dst")}));scalaGppEnv.out.append("""=Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({boolsToDst}));scalaGppEnv.out.append("""(dstAsBools,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declAnonymousArray("dstAsBools.length/"+dstType.width)}));scalaGppEnv.out.append(""",0,dstAsBools.length);
""");}else{

scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("dst","dstAsBools.length")}));scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
""");scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("expected")}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""            expected = dstAsBools;
""");}

scalaGppEnv.out.append("""        System.arraycopy(srcAsBools,srcPos*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append(""",dstAsBools,dstPos*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append(""", """);scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append("""*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append(""");
""");if(dstType.name != "boolean"){

scalaGppEnv.out.append("""            """);scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declArray("expected")}));scalaGppEnv.out.append(""" = Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({boolsToDst}));scalaGppEnv.out.append("""(dstAsBools,0,""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.declAnonymousArray("dstAsBools.length/"+dstType.width)}));scalaGppEnv.out.append(""",0,dstAsBools.length);
""");}

scalaGppEnv.out.append("""        dst=Conversion.""");scalaGppEnv.out.append(scalaGppEnv.toString({methodName}));scalaGppEnv.out.append("""(src,srcPos,dst,dstPos,""");scalaGppEnv.out.append(scalaGppEnv.toString({nD}));scalaGppEnv.out.append(""");
""");scalaGppEnv.out.append("""        assertArrayEquals(expected,dst);
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void """);scalaGppEnv.out.append(scalaGppEnv.toString({testMethodName}));scalaGppEnv.out.append("""() throws Exception{
""");scalaGppEnv.out.append("""        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
""");scalaGppEnv.out.append("""        boolean[] srcAsBools = """);scalaGppEnv.out.append(scalaGppEnv.toString({boolsToArrayDecl(Conv.hexToBools("0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"))}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        //src and dst sizes in dst units
""");scalaGppEnv.out.append("""        final int srcSize = srcAsBools.length/""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        final int dstSize = """);scalaGppEnv.out.append(scalaGppEnv.toString({outPerInVal}));scalaGppEnv.out.append("""+3;
""");scalaGppEnv.out.append("""        boolean[] dstAsBools = new boolean[dstSize*""");scalaGppEnv.out.append(scalaGppEnv.toString({dstType.width}));scalaGppEnv.out.append("""];
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools, 0,dstAsBools, 0, 0);
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools, 0,dstAsBools, 0, dstSize);
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools, 0,dstAsBools, 1, dstSize-1);
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
""");scalaGppEnv.out.append("""        """);scalaGppEnv.out.append(scalaGppEnv.toString({checkMethodName}));scalaGppEnv.out.append("""(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    """);}
    

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
""");
    def dbgPrint(srcType: JavaType){

scalaGppEnv.out.append("""        static String dbgPrint(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append("""[] src) {
""");scalaGppEnv.out.append("""            StringBuilder sb = new StringBuilder();
""");srcType match{
                case t: bool_t => {
                    

scalaGppEnv.out.append("""                    for(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" e:src){
""");scalaGppEnv.out.append("""                        if(e) sb.append("1,");
""");scalaGppEnv.out.append("""                        else sb.append("0,");
""");scalaGppEnv.out.append("""                    }
""");}
                case _ => {
                    

scalaGppEnv.out.append("""                    for(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append(""" e:src){
""");scalaGppEnv.out.append("""                        sb.append("0x").append(Long.toHexString(e)).append(',');
""");scalaGppEnv.out.append("""                    }
""");}

}

scalaGppEnv.out.append("""            String out = sb.toString();
""");scalaGppEnv.out.append("""            return out.substring(0,out.length()-1);
""");scalaGppEnv.out.append("""        }
""");}
    def assertArrayEquals(srcType: JavaType){

scalaGppEnv.out.append("""        static void assertArrayEquals(""");scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append("""[] expected, """);scalaGppEnv.out.append(scalaGppEnv.toString({srcType.name}));scalaGppEnv.out.append("""[] actual){
""");scalaGppEnv.out.append("""            assertEquals(expected.length,actual.length);
""");scalaGppEnv.out.append("""            for(int i=0;i<expected.length;i++){
""");scalaGppEnv.out.append("""                try{
""");scalaGppEnv.out.append("""                    assertEquals(expected[i], actual[i]);
""");scalaGppEnv.out.append("""                }catch(Throwable e){
""");scalaGppEnv.out.append("""                    String msg = "Mismatch at index "+i+" between:\n"+
""");scalaGppEnv.out.append("""                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
""");scalaGppEnv.out.append("""                    TestCase.fail(msg+"\n"+e.getMessage());
""");scalaGppEnv.out.append("""                }
""");scalaGppEnv.out.append("""            }
""");scalaGppEnv.out.append("""        }
""");}
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
    

scalaGppEnv.out.append("""    static void assertArrayEquals(String expected, String actual){
""");scalaGppEnv.out.append("""        assertEquals(expected,actual);
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testIntsToLong() throws Exception {
""");scalaGppEnv.out.append("""        int[] src = new int[]{
""");scalaGppEnv.out.append("""                0xCDF1F0C1,
""");scalaGppEnv.out.append("""                0x0F123456,
""");scalaGppEnv.out.append("""                0x78000000};
""");scalaGppEnv.out.append("""        assertEquals(0x0000000000000000L,Conversion.intsToLong(src,0,0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x0000000000000000L,Conversion.intsToLong(src,1,0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x00000000CDF1F0C1L,Conversion.intsToLong(src,0,0L,0,1));
""");scalaGppEnv.out.append("""        assertEquals(0x0F123456CDF1F0C1L,Conversion.intsToLong(src,0,0L,0,2));
""");scalaGppEnv.out.append("""        assertEquals(0x000000000F123456L,Conversion.intsToLong(src,1,0L,0,1));
""");scalaGppEnv.out.append("""        assertEquals(0x123456789ABCDEF0L,Conversion.intsToLong(src,0,0x123456789ABCDEF0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x1234567878000000L,Conversion.intsToLong(src,2,0x123456789ABCDEF0L,0,1));
""");scalaGppEnv.out.append("""        //assertEquals(0x0F12345678000000L,Conversion.intsToLong(src,1,0x123456789ABCDEF0L,32,2));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortsToLong() throws Exception {
""");scalaGppEnv.out.append("""        //TODO: implement this test. This is somehow low priority since intsToLong and bytesToLong are tested
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBytesToLong() throws Exception {
""");scalaGppEnv.out.append("""        byte[] src = new byte[]{
""");scalaGppEnv.out.append("""                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
""");scalaGppEnv.out.append("""                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
""");scalaGppEnv.out.append("""                (byte)0x78};
""");scalaGppEnv.out.append("""        assertEquals(0x0000000000000000L,Conversion.bytesToLong(src,0,0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x00000000000000CDL,Conversion.bytesToLong(src,0,0L,0,1));
""");scalaGppEnv.out.append("""        assertEquals(0x00000000C1F0F1CDL,Conversion.bytesToLong(src,0,0L,0,4));
""");scalaGppEnv.out.append("""        assertEquals(0x000000000FC1F0F1L,Conversion.bytesToLong(src,1,0L,0,4));
""");scalaGppEnv.out.append("""        assertEquals(0x123456789ABCDEF0L,Conversion.bytesToLong(src,0,0x123456789ABCDEF0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x12345678CDBCDEF0L,Conversion.bytesToLong(src,0,0x123456789ABCDEF0L,24,1));
""");scalaGppEnv.out.append("""        //assertEquals(0x123456789A7856F0L,Conversion.bytesToLong(src,7,0x123456789ABCDEF0L,8,2));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortsToInt() throws Exception {
""");scalaGppEnv.out.append("""        short[] src = new short[]{
""");scalaGppEnv.out.append("""                (short)0xCDF1,(short)0xF0C1,
""");scalaGppEnv.out.append("""                (short)0x0F12,(short)0x3456,
""");scalaGppEnv.out.append("""                (short)0x7800};
""");scalaGppEnv.out.append("""        assertEquals(0x00000000,Conversion.shortsToInt(src, 0, 0, 0, 0));
""");scalaGppEnv.out.append("""        assertEquals(0x0000CDF1,Conversion.shortsToInt(src, 0, 0, 0, 1));
""");scalaGppEnv.out.append("""        assertEquals(0xF0C1CDF1,Conversion.shortsToInt(src, 0, 0, 0, 2));
""");scalaGppEnv.out.append("""        assertEquals(0x0F12F0C1,Conversion.shortsToInt(src, 1, 0, 0, 2));
""");scalaGppEnv.out.append("""        assertEquals(0x12345678,Conversion.shortsToInt(src, 0, 0x12345678, 0, 0));
""");scalaGppEnv.out.append("""        assertEquals(0xCDF15678,Conversion.shortsToInt(src, 0, 0x12345678, 16, 1));
""");scalaGppEnv.out.append("""        //assertEquals(0x34567800,Conversion.shortsToInt(src, 3, 0x12345678, 16, 2));
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBytesToInt() throws Exception {
""");scalaGppEnv.out.append("""        byte[] src = new byte[]{
""");scalaGppEnv.out.append("""                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
""");scalaGppEnv.out.append("""                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
""");scalaGppEnv.out.append("""                (byte)0x78};
""");scalaGppEnv.out.append("""        assertEquals(0x00000000,Conversion.bytesToInt(src, 0, 0, 0, 0));
""");scalaGppEnv.out.append("""        assertEquals(0x000000CD,Conversion.bytesToInt(src, 0, 0, 0, 1));
""");scalaGppEnv.out.append("""        assertEquals(0xC1F0F1CD,Conversion.bytesToInt(src, 0, 0, 0, 4));
""");scalaGppEnv.out.append("""        assertEquals(0x0FC1F0F1,Conversion.bytesToInt(src, 1, 0, 0, 4));
""");scalaGppEnv.out.append("""        assertEquals(0x12345678,Conversion.bytesToInt(src, 0, 0x12345678, 0, 0));
""");scalaGppEnv.out.append("""        assertEquals(0xCD345678,Conversion.bytesToInt(src, 0, 0x12345678, 24, 1));
""");scalaGppEnv.out.append("""        //assertEquals(0x56341278,Conversion.bytesToInt(src, 5, 0x01234567, 8, 4));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBytesToShort() throws Exception {
""");scalaGppEnv.out.append("""        byte[] src = new byte[]{
""");scalaGppEnv.out.append("""                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
""");scalaGppEnv.out.append("""                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
""");scalaGppEnv.out.append("""                (byte)0x78};
""");scalaGppEnv.out.append("""        assertEquals((short)0x0000,Conversion.bytesToShort(src, 0, (short) 0, 0, 0));
""");scalaGppEnv.out.append("""        assertEquals((short)0x00CD,Conversion.bytesToShort(src, 0, (short) 0, 0, 1));
""");scalaGppEnv.out.append("""        assertEquals((short)0xF1CD,Conversion.bytesToShort(src, 0, (short) 0, 0, 2));
""");scalaGppEnv.out.append("""        assertEquals((short)0xF0F1,Conversion.bytesToShort(src, 1, (short) 0, 0, 2));
""");scalaGppEnv.out.append("""        assertEquals((short)0x1234,Conversion.bytesToShort(src, 0, (short) 0x1234, 0, 0));
""");scalaGppEnv.out.append("""        assertEquals((short)0xCD34,Conversion.bytesToShort(src, 0, (short) 0x1234, 8, 1));
""");scalaGppEnv.out.append("""        //assertEquals((short)0x5678,Conversion.bytesToShort(src, 7, (short) 0x0123, 8, 2));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToLong() throws Exception {
""");scalaGppEnv.out.append("""        String src = "CDF1F0C10F12345678";
""");scalaGppEnv.out.append("""        assertEquals(0x0000000000000000L,Conversion.hexsToLong(src,0,0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x000000000000000CL,Conversion.hexsToLong(src,0,0L,0,1));
""");scalaGppEnv.out.append("""        assertEquals(0x000000001C0F1FDCL,Conversion.hexsToLong(src,0,0L,0,8));
""");scalaGppEnv.out.append("""        assertEquals(0x0000000001C0F1FDL,Conversion.hexsToLong(src,1,0L,0,8));
""");scalaGppEnv.out.append("""        assertEquals(0x123456798ABCDEF0L,Conversion.hexsToLong(src, 0,0x123456798ABCDEF0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x1234567876BCDEF0L,Conversion.hexsToLong(src,15,0x123456798ABCDEF0L,24,3));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToInt() throws Exception {
""");scalaGppEnv.out.append("""        String src = "CDF1F0C10F12345678";
""");scalaGppEnv.out.append("""        assertEquals(0x00000000,Conversion.hexsToInt(src,0,0,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x0000000C,Conversion.hexsToInt(src,0,0,0,1));
""");scalaGppEnv.out.append("""        assertEquals(0x1C0F1FDC,Conversion.hexsToInt(src,0,0,0,8));
""");scalaGppEnv.out.append("""        assertEquals(0x01C0F1FD,Conversion.hexsToInt(src,1,0,0,8));
""");scalaGppEnv.out.append("""        assertEquals(0x12345679,Conversion.hexsToInt(src, 0,0x12345679,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x87645679,Conversion.hexsToInt(src,15,0x12345679,20,3));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToShort() throws Exception {
""");scalaGppEnv.out.append("""        String src = "CDF1F0C10F12345678";
""");scalaGppEnv.out.append("""        assertEquals((short)0x0000,Conversion.hexsToShort(src, 0,(short)0,0,0));
""");scalaGppEnv.out.append("""        assertEquals((short)0x000C,Conversion.hexsToShort(src, 0,(short)0,0,1));
""");scalaGppEnv.out.append("""        assertEquals((short)0x1FDC,Conversion.hexsToShort(src, 0,(short)0,0,4));
""");scalaGppEnv.out.append("""        assertEquals((short)0xF1FD,Conversion.hexsToShort(src, 1,(short)0,0,4));
""");scalaGppEnv.out.append("""        assertEquals((short)0x1234,Conversion.hexsToShort(src, 0,(short)0x1234,0,0));
""");scalaGppEnv.out.append("""        assertEquals((short)0x8764,Conversion.hexsToShort(src,15,(short)0x1234,4,3));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToByte() throws Exception {
""");scalaGppEnv.out.append("""        String src = "CDF1F0C10F12345678";
""");scalaGppEnv.out.append("""        assertEquals((byte)0x00,Conversion.hexsToByte(src, 0,(byte)0,0,0));
""");scalaGppEnv.out.append("""        assertEquals((byte)0x0C,Conversion.hexsToByte(src, 0,(byte)0,0,1));
""");scalaGppEnv.out.append("""        assertEquals((byte)0xDC,Conversion.hexsToByte(src, 0,(byte)0,0,2));
""");scalaGppEnv.out.append("""        assertEquals((byte)0xFD,Conversion.hexsToByte(src, 1,(byte)0,0,2));
""");scalaGppEnv.out.append("""        assertEquals((byte)0x34,Conversion.hexsToByte(src, 0,(byte)0x34,0,0));
""");scalaGppEnv.out.append("""        assertEquals((byte)0x84,Conversion.hexsToByte(src,17,(byte)0x34,4,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToLong() throws Exception {
""");scalaGppEnv.out.append("""        boolean[] src = new boolean[]{
""");scalaGppEnv.out.append("""                false, false, true, true, true, false, true, true,
""");scalaGppEnv.out.append("""                true,  true,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                true,  true,  true,  true,false, false, false, false,
""");scalaGppEnv.out.append("""                false, false,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                false, false, false, false, true,  true,  true,  true,
""");scalaGppEnv.out.append("""                true, false, false, false,false,  true, false, false,
""");scalaGppEnv.out.append("""                true,  true, false, false,false, false,  true, false,
""");scalaGppEnv.out.append("""                true, false,  true, false,false,  true,  true, false,
""");scalaGppEnv.out.append("""                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        assertEquals(0x0000000000000000L,Conversion.boolsToLong(src,0,0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x000000000000000CL,Conversion.boolsToLong(src,0,0L,0,1*4));
""");scalaGppEnv.out.append("""        assertEquals(0x000000001C0F1FDCL,Conversion.boolsToLong(src,0,0L,0,8*4));
""");scalaGppEnv.out.append("""        assertEquals(0x0000000001C0F1FDL,Conversion.boolsToLong(src,1*4,0L,0,8*4));
""");scalaGppEnv.out.append("""        assertEquals(0x123456798ABCDEF0L,Conversion.boolsToLong(src, 0,0x123456798ABCDEF0L,0,0));
""");scalaGppEnv.out.append("""        assertEquals(0x1234567876BCDEF0L,Conversion.boolsToLong(src,15*4,0x123456798ABCDEF0L,24,3*4));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToInt() throws Exception {
""");scalaGppEnv.out.append("""        boolean[] src = new boolean[]{
""");scalaGppEnv.out.append("""                false, false, true, true, true, false, true, true,
""");scalaGppEnv.out.append("""                true,  true,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                true,  true,  true,  true,false, false, false, false,
""");scalaGppEnv.out.append("""                false, false,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                false, false, false, false, true,  true,  true,  true,
""");scalaGppEnv.out.append("""                true, false, false, false,false,  true, false, false,
""");scalaGppEnv.out.append("""                true,  true, false, false,false, false,  true, false,
""");scalaGppEnv.out.append("""                true, false,  true, false,false,  true,  true, false,
""");scalaGppEnv.out.append("""                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
""");scalaGppEnv.out.append("""        assertEquals(0x00000000,Conversion.boolsToInt(src, 0*4,0,0,0*4));
""");scalaGppEnv.out.append("""        assertEquals(0x0000000C,Conversion.boolsToInt(src, 0*4,0,0,1*4));
""");scalaGppEnv.out.append("""        assertEquals(0x1C0F1FDC,Conversion.boolsToInt(src, 0*4,0,0,8*4));
""");scalaGppEnv.out.append("""        assertEquals(0x01C0F1FD,Conversion.boolsToInt(src, 1*4,0,0,8*4));
""");scalaGppEnv.out.append("""        assertEquals(0x12345679,Conversion.boolsToInt(src, 0*4,0x12345679, 0,0*4));
""");scalaGppEnv.out.append("""        assertEquals(0x87645679,Conversion.boolsToInt(src,15*4,0x12345679,20,3*4));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToShort() throws Exception {
""");scalaGppEnv.out.append("""        boolean[] src = new boolean[]{
""");scalaGppEnv.out.append("""                false, false, true, true, true, false, true, true,
""");scalaGppEnv.out.append("""                true,  true,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                true,  true,  true,  true,false, false, false, false,
""");scalaGppEnv.out.append("""                false, false,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                false, false, false, false, true,  true,  true,  true,
""");scalaGppEnv.out.append("""                true, false, false, false,false,  true, false, false,
""");scalaGppEnv.out.append("""                true,  true, false, false,false, false,  true, false,
""");scalaGppEnv.out.append("""                true, false,  true, false,false,  true,  true, false,
""");scalaGppEnv.out.append("""                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
""");scalaGppEnv.out.append("""        assertEquals((short)0x0000,Conversion.boolsToShort(src, 0*4,(short)0,0,0*4));
""");scalaGppEnv.out.append("""        assertEquals((short)0x000C,Conversion.boolsToShort(src, 0*4,(short)0,0,1*4));
""");scalaGppEnv.out.append("""        assertEquals((short)0x1FDC,Conversion.boolsToShort(src, 0*4,(short)0,0,4*4));
""");scalaGppEnv.out.append("""        assertEquals((short)0xF1FD,Conversion.boolsToShort(src, 1*4,(short)0,0,4*4));
""");scalaGppEnv.out.append("""        assertEquals((short)0x1234,Conversion.boolsToShort(src, 0*4,(short)0x1234,0,0*4));
""");scalaGppEnv.out.append("""        assertEquals((short)0x8764,Conversion.boolsToShort(src,15*4,(short)0x1234,4,3*4));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToByte() throws Exception {
""");scalaGppEnv.out.append("""        boolean[] src = new boolean[]{
""");scalaGppEnv.out.append("""                false, false, true, true, true, false, true, true,
""");scalaGppEnv.out.append("""                true,  true,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                true,  true,  true,  true,false, false, false, false,
""");scalaGppEnv.out.append("""                false, false,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                false, false, false, false, true,  true,  true,  true,
""");scalaGppEnv.out.append("""                true, false, false, false,false,  true, false, false,
""");scalaGppEnv.out.append("""                true,  true, false, false,false, false,  true, false,
""");scalaGppEnv.out.append("""                true, false,  true, false,false,  true,  true, false,
""");scalaGppEnv.out.append("""                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
""");scalaGppEnv.out.append("""        assertEquals((byte)0x00,Conversion.boolsToByte(src, 0*4,(byte)0,0,0*4));
""");scalaGppEnv.out.append("""        assertEquals((byte)0x0C,Conversion.boolsToByte(src, 0*4,(byte)0,0,1*4));
""");scalaGppEnv.out.append("""        assertEquals((byte)0xDC,Conversion.boolsToByte(src, 0*4,(byte)0,0,2*4));
""");scalaGppEnv.out.append("""        assertEquals((byte)0xFD,Conversion.boolsToByte(src, 1*4,(byte)0,0,2*4));
""");scalaGppEnv.out.append("""        assertEquals((byte)0x34,Conversion.boolsToByte(src, 0*4,(byte)0x34,0,0*4));
""");scalaGppEnv.out.append("""        assertEquals((byte)0x84,Conversion.boolsToByte(src,17*4,(byte)0x34,4,1*4));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToHex() throws Exception {
""");scalaGppEnv.out.append("""        boolean[] src = new boolean[]{
""");scalaGppEnv.out.append("""                false, false, true, true, true, false, true, true,
""");scalaGppEnv.out.append("""                true,  true,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                true,  true,  true,  true,false, false, false, false,
""");scalaGppEnv.out.append("""                false, false,  true,  true, true, false, false, false,
""");scalaGppEnv.out.append("""                false, false, false, false, true,  true,  true,  true,
""");scalaGppEnv.out.append("""                true, false, false, false,false,  true, false, false,
""");scalaGppEnv.out.append("""                true,  true, false, false,false, false,  true, false,
""");scalaGppEnv.out.append("""                true, false,  true, false,false,  true,  true, false,
""");scalaGppEnv.out.append("""                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
""");scalaGppEnv.out.append("""        assertEquals('F',Conversion.boolsToHex(src, 0*4,'F',0,0*4));
""");scalaGppEnv.out.append("""        assertEquals('C',Conversion.boolsToHex(src, 0*4,'F',0,1*4));
""");scalaGppEnv.out.append("""        assertEquals('D',Conversion.boolsToHex(src, 1*4,'F',0,1*4));
""");scalaGppEnv.out.append("""        assertEquals('8',Conversion.boolsToHex(src,17*4,'F',0,1*4));
""");scalaGppEnv.out.append("""        assertEquals('9',Conversion.boolsToHex(src, 3*4,'F',0,3));
""");scalaGppEnv.out.append("""        assertEquals('1',Conversion.boolsToHex(src, 3*4,'0',0,3));
""");scalaGppEnv.out.append("""        assertEquals('E',Conversion.boolsToHex(src,   1,'F',0,4));
""");scalaGppEnv.out.append("""        assertEquals('1',Conversion.boolsToHex(src,17*4+3,'0',0,1));
""");scalaGppEnv.out.append("""        assertEquals('2',Conversion.boolsToHex(src,17*4+3,'0',1,1));
""");scalaGppEnv.out.append("""        assertEquals('4',Conversion.boolsToHex(src,17*4+3,'0',2,1));
""");scalaGppEnv.out.append("""        assertEquals('8',Conversion.boolsToHex(src,17*4+3,'0',3,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testLongToInts() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 0,new int[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 100,new int[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 0,new int[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0x90ABCDEF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0x90ABCDEF,0x12345678,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,2));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new int[]{0x90ABCDEF,0x12345678,0x90ABCDEF,0x12345678},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,4));//rejected by assertion
""");scalaGppEnv.out.append("""        //assertArrayEquals(new int[]{0xFFFFFFFF,0x90ABCDEF,0x12345678,0x90ABCDEF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},1,3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF,0x12345678},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},2,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x4855E6F7,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 1,new int[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x242AF37B,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 2,new int[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x121579BD,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 3,new int[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x890ABCDE,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 4,new int[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new int[]{0x4855E6F7,0x091A2B3C,0x4855E6F7,0x091A2B3C},Conversion.longToInts(0x1234567890ABCDEFL, 1,new int[]{-1,-1,-1,-1},0,4));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[]{0x091A2B3C},Conversion.longToInts(0x1234567890ABCDEFL,33,new int[]{0},0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testLongToShorts() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 0,new short[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 100,new short[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 0,new short[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xCDEF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0x5678,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0x5678,(short)0x1234},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xCDEF,(short)0x90AB,(short)0x5678},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},1,3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xCDEF,(short)0x90AB},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},2,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xCDEF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xCDEF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xE6F7,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 1,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xF37B,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 2,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x79BD,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 3,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xBCDE,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 4,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xE6F7,(short)0x4855,(short)0x2B3C,(short)0x091A},Conversion.longToShorts(0x1234567890ABCDEFL, 1,new short[]{-1,-1,-1,-1},0,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0x2B3C},Conversion.longToShorts(0x1234567890ABCDEFL,33,new short[]{0},0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testIntToShorts() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 0,new short[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 100,new short[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 0,new short[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0x5678,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,2));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0x5678,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,3));//rejected by assertion
""");scalaGppEnv.out.append("""        //assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0x5678,(short)0x1234},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,4));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new short[]{(short)0xFFFF,(short)0x5678,(short)0x1234,(short)0x5678},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},1,3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x5678,(short)0x1234},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},2,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x5678,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0x5678},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x2B3C,(short)0xFFFF},Conversion.intToShorts(0x12345678, 1,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x159E,(short)0xFFFF},Conversion.intToShorts(0x12345678, 2,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x8ACF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 3,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x4567,(short)0xFFFF},Conversion.intToShorts(0x12345678, 4,new short[]{-1,-1,-1,-1},2,1));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new short[]{(short)0xE6F7,(short)0x4855,(short)0x2B3C,(short)0x091A},Conversion.intToShorts(0x12345678, 1,new short[]{-1,-1,-1,-1},0,4));//rejected by assertion
""");scalaGppEnv.out.append("""        //assertArrayEquals(new short[]{(short)0x2B3C},Conversion.intToShorts(0x12345678,33,new short[]{0},0,1));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[]{(short)0x091A},Conversion.intToShorts(0x12345678,17,new short[]{0},0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testLongToBytes() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 0,new byte[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 100,new byte[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 0,new byte[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,7));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,7));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00},Conversion.longToBytes(0x1234567890ABCDEFL, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00,(byte)0x00},Conversion.longToBytes(0x1234567890ABCDEFL,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,7));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testIntToBytes() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 0,new byte[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 100,new byte[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 0,new byte[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0x00,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,3));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortToBytes() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 0,new byte[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 100,new byte[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 0,new byte[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},3,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0xFE,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,2));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,2));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0xFE,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testLongToHexs() throws Exception {
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 0,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 100,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 0,"",100,0));
""");scalaGppEnv.out.append("""        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
""");scalaGppEnv.out.append("""        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDE3L, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
""");scalaGppEnv.out.append("""        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
""");scalaGppEnv.out.append("""        assertEquals("FEDCBA098765432FFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,15));
""");scalaGppEnv.out.append("""        assertEquals("FEDCBA0987654321FFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,16));
""");scalaGppEnv.out.append("""        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDE3L, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEDCFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEDCBA098765432FFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,15));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEDCBA0987654321FFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));
""");scalaGppEnv.out.append("""        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FFFDB975121FCA86420FFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));
""");scalaGppEnv.out.append("""        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL,4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));//rejected by assertion
""");scalaGppEnv.out.append("""        assertEquals("FFFEDCBA0987654321FFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL,4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,15));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testIntToHexs() throws Exception {
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.intToHexs(0x00000000, 0,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.intToHexs(0x00000000, 100,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.intToHexs(0x00000000, 0,"",100,0));
""");scalaGppEnv.out.append("""        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
""");scalaGppEnv.out.append("""        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
""");scalaGppEnv.out.append("""        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
""");scalaGppEnv.out.append("""        assertEquals("FEDCBA0FFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,7));
""");scalaGppEnv.out.append("""        assertEquals("FEDCBA09FFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,8));
""");scalaGppEnv.out.append("""        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEDCFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEDCBA0FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,7));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEDCBA09FFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));
""");scalaGppEnv.out.append("""        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FFFDB97512FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));
""");scalaGppEnv.out.append("""        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));//rejected by assertion
""");scalaGppEnv.out.append("""        assertEquals("FFFEDCBA09FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,7));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortToHexs() throws Exception {
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.shortToHexs((short)0x0000, 0,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.shortToHexs((short)0x0000, 100,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.shortToHexs((short)0x0000, 0,"",100,0));
""");scalaGppEnv.out.append("""        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
""");scalaGppEnv.out.append("""        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
""");scalaGppEnv.out.append("""        assertEquals("FEDFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,3));
""");scalaGppEnv.out.append("""        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
""");scalaGppEnv.out.append("""        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
""");scalaGppEnv.out.append("""        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
""");scalaGppEnv.out.append("""        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FFFDB9FFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
""");scalaGppEnv.out.append("""        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));//rejected by assertion
""");scalaGppEnv.out.append("""        assertEquals("FFFEDCFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,3));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testByteToHexs() throws Exception {
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.byteToHexs((byte)0x00, 0,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.byteToHexs((byte)0x00, 100,"",0,0));
""");scalaGppEnv.out.append("""        assertEquals("",Conversion.byteToHexs((byte)0x00, 0,"",100,0));
""");scalaGppEnv.out.append("""        assertEquals("00000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,0));
""");scalaGppEnv.out.append("""        assertEquals("F0000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,1));
""");scalaGppEnv.out.append("""        assertEquals("FE000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,2));
""");scalaGppEnv.out.append("""        assertEquals("000F0",Conversion.byteToHexs((byte)0xEF, 0,"00000",3,1));
""");scalaGppEnv.out.append("""        assertEquals("000FE",Conversion.byteToHexs((byte)0xEF, 0,"00000",3,2));
""");scalaGppEnv.out.append("""        assertEquals("70000",Conversion.byteToHexs((byte)0xEF, 1,"00000",0,1));
""");scalaGppEnv.out.append("""        assertEquals("B0000",Conversion.byteToHexs((byte)0xEF, 2,"00000",0,1));
""");scalaGppEnv.out.append("""        assertEquals("000DF",Conversion.byteToHexs((byte)0xEF, 3,"00000",3,2));
""");scalaGppEnv.out.append("""        //assertEquals("00000",Conversion.byteToHexs((byte)0xEF, 4,"00000",3,2));//rejected by assertion
""");scalaGppEnv.out.append("""        assertEquals("000E0",Conversion.byteToHexs((byte)0xEF, 4,"00000",3,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testLongToBools() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 0,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 100,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 0,new boolean[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[69],Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 0));
""");/*  //scala code used to generate the long boolean array
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
        assertArrayEquals(`boolsToArrayDecl(Conv.longToBools(0x1234567890ABCDEFL,13,new Array[Boolean](69), 3,63))`,Conversion.longToBools(0x1234567890ABCDEFL,13,new boolean[69], 3,63));   */

scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,63));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,64));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2,64));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 1,new boolean[69], 0, 63));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 0, 62));
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        //assertArrayEquals(new boolean[]{false,false,false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false    ,false,false,false,false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 3, 63));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false,false,false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false    ,false,false,false,false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 3, 62));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testIntToBools() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 0,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 100,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 0,new boolean[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[69],Conversion.intToBools(0x90ABCDEF, 0,new boolean[69], 0, 0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0,31));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 2, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false,    false,  true, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 2,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 1,new boolean[37], 0,31));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 0,30));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 3,31));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,     true, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 3,30));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortToBools() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 0,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 100,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 0,new boolean[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[69],Conversion.shortToBools((short)0xCDEF, 0,new boolean[69], 0, 0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0,15));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 2, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,       true,  true, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 2,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 1,new boolean[21], 0,15));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 0,14));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 3,15));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,       true, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 3,14));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testByteToBools() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 0,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 100,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 0,new boolean[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[69],Conversion.byteToBools((byte)0xEF, 0,new boolean[69], 0, 0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false,  true, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 7));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false,  true, false, false,  true,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 2, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true, false,  true, false,  true, false,      false,  true, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 2, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false,  true, false,  true, false, false,  true, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 1,new boolean[13], 0, 7));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false, false,  true, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 0, 6));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 3, 7));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false, false,  true, false,  true, false, false,       true, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 3, 6));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexToBools() throws Exception {
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 0,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 100,new boolean[]{},0,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 0,new boolean[]{},100,0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[69],Conversion.hexToBools('F', 0,new boolean[69], 0, 0));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false,  true, false,  true,  true,  true,  true},Conversion.hexToBools('5', 0,new boolean[]{true,true,true,true,true,true,true,true}, 0, 4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 2, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false,  true, false,  true, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 2, 4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false,  true, false, false, false, false, false, false},Conversion.hexToBools('5', 1,new boolean[8], 0, 3));
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 2,new boolean[8], 0, 2));
""");scalaGppEnv.out.append("""        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true},Conversion.hexToBools('5', 2,new boolean[8], 3, 3));//rejected by assertion
""");scalaGppEnv.out.append("""        assertArrayEquals(new boolean[]{false, false, false,  true, false, false, false, false},Conversion.hexToBools('5', 2,new boolean[8], 3, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testIntsToLongs() throws Exception{
""");scalaGppEnv.out.append("""        int[] src = new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
""");scalaGppEnv.out.append("""        long[] dst = new long[3];
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.intsToLongs(src,0,dst,0,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.intsToLongs(src,0,dst,1,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.intsToLongs(src,0,dst,5,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.intsToLongs(src,0,dst,3,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.intsToLongs(src,1,dst,3,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.intsToLongs(src,2,dst,3,2));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortsToLongs() throws Exception{
""");scalaGppEnv.out.append("""        short[] src = new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
""");scalaGppEnv.out.append("""        long[] dst = new long[3];
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.shortsToLongs(src,0,dst, 0,8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.shortsToLongs(src,0,dst, 2,8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.shortsToLongs(src,0,dst,10,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.shortsToLongs(src,0,dst, 6,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.shortsToLongs(src,2,dst, 6,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.shortsToLongs(src,4,dst, 6,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64AE64AL},Conversion.shortsToLongs(src,7,dst, 8,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE64A4567E64AE64AL},Conversion.shortsToLongs(src,7,dst,11,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x012345670123E64AL, 0xC539275B89ABCDEFL, 0xE64A4567E64AE64AL},Conversion.shortsToLongs(src,7,dst, 0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testShortsToInts() throws Exception{
""");scalaGppEnv.out.append("""        short[] src = new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
""");scalaGppEnv.out.append("""        int[] dst = new int[6];
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38,0x00000000, 0x00000000},Conversion.shortsToInts(src,0,dst, 0,8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x00000000},Conversion.shortsToInts(src,0,dst, 2,8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.shortsToInts(src,0,dst,10,2));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x01234567,0x89ABCDEF, 0x01234567},Conversion.shortsToInts(src,0,dst, 6,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x89ABCDEF,0xC539275B, 0x01234567},Conversion.shortsToInts(src,2,dst, 6,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.shortsToInts(src,4,dst, 6,4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0x01234567},Conversion.shortsToInts(src,7,dst, 8,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0xE64A4567},Conversion.shortsToInts(src,7,dst,11,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x0123E64A,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0xE64A4567},Conversion.shortsToInts(src,7,dst, 0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBytesToLongs() throws Exception{
""");scalaGppEnv.out.append("""        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
""");scalaGppEnv.out.append("""        long[] dst = new long[3];
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.bytesToLongs(src, 0,dst, 0,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.bytesToLongs(src, 0,dst, 4,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.bytesToLongs(src, 0,dst,20, 4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.bytesToLongs(src, 0,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.bytesToLongs(src, 4,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.bytesToLongs(src, 8,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst,16, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE6234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst,23, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x01234567012345E6L, 0xC539275B89ABCDEFL, 0xE6234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst, 0, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBytesToInts() throws Exception{
""");scalaGppEnv.out.append("""        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
""");scalaGppEnv.out.append("""        int[] dst = new int[6];
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38,0x00000000, 0x00000000},Conversion.bytesToInts(src, 0,dst, 0,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x00000000},Conversion.bytesToInts(src, 0,dst, 4,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.bytesToInts(src, 0,dst,20, 4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x01234567,0x89ABCDEF, 0x01234567},Conversion.bytesToInts(src, 0,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x89ABCDEF,0xC539275B, 0x01234567},Conversion.bytesToInts(src, 4,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.bytesToInts(src, 8,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0x01234567},Conversion.bytesToInts(src,15,dst,16, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0xE6234567},Conversion.bytesToInts(src,15,dst,23, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x012345E6,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0xE6234567},Conversion.bytesToInts(src,15,dst, 0, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBytesToShorts() throws Exception{
""");scalaGppEnv.out.append("""        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
""");scalaGppEnv.out.append("""        short[] dst = new short[12];
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.bytesToShorts(src, 0,dst, 0,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.bytesToShorts(src, 0,dst, 4,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 0,dst,20, 4));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 0,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 4,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 8,dst,12, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src,15,dst,16, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0xE623},Conversion.bytesToShorts(src,15,dst,23, 1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x45E6,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0xE623},Conversion.bytesToShorts(src,15,dst, 0, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToLongs() throws Exception{
""");scalaGppEnv.out.append("""        String src = """");scalaGppEnv.out.append(scalaGppEnv.toString({"E64A8C38C539275B89ABCDEF01234567".reverse}));scalaGppEnv.out.append("""";
""");scalaGppEnv.out.append("""        long[] dst = new long[3];
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.hexsToLongs(src, 0,dst, 0,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.hexsToLongs(src, 0,dst, 8,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.hexsToLongs(src, 0,dst,40, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.hexsToLongs(src, 0,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.hexsToLongs(src, 8,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.hexsToLongs(src,16,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE1234567E64A8C38L},Conversion.hexsToLongs(src,31,dst,47, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToInts() throws Exception{
""");scalaGppEnv.out.append("""        String src = """");scalaGppEnv.out.append(scalaGppEnv.toString({"E64A8C38C539275B89ABCDEF01234567".reverse}));scalaGppEnv.out.append("""";
""");scalaGppEnv.out.append("""        int[] dst = new int[6];
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF,0xC539275B, 0xE64A8C38,0x00000000, 0x00000000},Conversion.hexsToInts(src, 0,dst, 0,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x00000000},Conversion.hexsToInts(src, 0,dst, 8,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.hexsToInts(src, 0,dst,40, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x01234567,0x89ABCDEF, 0x01234567},Conversion.hexsToInts(src, 0,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x89ABCDEF,0xC539275B, 0x01234567},Conversion.hexsToInts(src, 8,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.hexsToInts(src,16,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0xE1234567},Conversion.hexsToInts(src,31,dst,47, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToShorts() throws Exception{
""");scalaGppEnv.out.append("""        String src = """");scalaGppEnv.out.append(scalaGppEnv.toString({"E64A8C38C539275B89ABCDEF01234567".reverse}));scalaGppEnv.out.append("""";
""");scalaGppEnv.out.append("""        short[] dst = new short[12];
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.hexsToShorts(src, 0,dst, 0,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.hexsToShorts(src, 0,dst, 8,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 0,dst,40, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 0,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 8,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src,16,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0xE123},Conversion.hexsToShorts(src,31,dst,47, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testHexsToBytes() throws Exception{
""");scalaGppEnv.out.append("""        String src = """");scalaGppEnv.out.append(scalaGppEnv.toString({"E64A8C38C539275B89ABCDEF01234567".reverse}));scalaGppEnv.out.append("""";
""");scalaGppEnv.out.append("""        byte[] dst = new byte[24];
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.hexsToBytes(src, 0,dst, 0,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.hexsToBytes(src, 0,dst, 8,32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 0,dst,40, 8));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 0,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 8,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src,16,dst,24,16));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0xE1},Conversion.hexsToBytes(src,31,dst,47, 1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToLongs() throws Exception{
""");scalaGppEnv.out.append("""        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
""");scalaGppEnv.out.append("""        boolean[] src = """);scalaGppEnv.out.append(scalaGppEnv.toString({boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        long[] dst = new long[3];
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.boolsToLongs(src,0*32,dst,0*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.boolsToLongs(src,0*32,dst,1*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.boolsToLongs(src,0*32,dst,5*32,1*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.boolsToLongs(src,0*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.boolsToLongs(src,1*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.boolsToLongs(src,2*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x81234567E64A8C38L},Conversion.boolsToLongs(src,4*32-1,dst,3*64-1,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new long[] {0x0123456701234566L, 0xC539275B89ABCDEFL, 0x81234567E64A8C38L},Conversion.boolsToLongs(src,1*32-1,dst,0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToInts() throws Exception{
""");scalaGppEnv.out.append("""        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
""");scalaGppEnv.out.append("""        boolean[] src = """);scalaGppEnv.out.append(scalaGppEnv.toString({boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        int[] dst = new int[6];
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF,0xC539275B, 0xE64A8C38,0x00000000, 0x00000000},Conversion.boolsToInts(src,0*32,dst,0*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x00000000},Conversion.boolsToInts(src,0*32,dst,1*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.boolsToInts(src,0*32,dst,5*32,1*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x01234567,0x89ABCDEF, 0x01234567},Conversion.boolsToInts(src,0*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x89ABCDEF,0xC539275B, 0x01234567},Conversion.boolsToInts(src,1*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.boolsToInts(src,2*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x81234567},Conversion.boolsToInts(src,4*32-1,dst,3*64-1,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new int[] {0x01234566,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x81234567},Conversion.boolsToInts(src,1*32-1,dst,0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToShorts() throws Exception{
""");scalaGppEnv.out.append("""        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
""");scalaGppEnv.out.append("""        boolean[] src = """);scalaGppEnv.out.append(scalaGppEnv.toString({boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        short[] dst = new short[12];
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.boolsToShorts(src,0*32,dst,0*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.boolsToShorts(src,0*32,dst,1*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,0*32,dst,5*32,1*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,0*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,1*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,2*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x8123},Conversion.boolsToShorts(src,4*32-1,dst,3*64-1,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new short[] {(short)0x4566,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x8123},Conversion.boolsToShorts(src,1*32-1,dst,0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToBytes() throws Exception{
""");scalaGppEnv.out.append("""        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
""");scalaGppEnv.out.append("""        boolean[] src = """);scalaGppEnv.out.append(scalaGppEnv.toString({boolsToArrayDecl(Conv.intsToBools(Array(0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38)))}));scalaGppEnv.out.append(""";
""");scalaGppEnv.out.append("""        byte[] dst = new byte[24];
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.boolsToBytes(src,0*32,dst,0*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.boolsToBytes(src,0*32,dst,1*32,4*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,0*32,dst,5*32,1*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,0*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,1*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,2*32,dst,3*32,2*32));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x81},Conversion.boolsToBytes(src,4*32-1,dst,3*64-1,1));
""");scalaGppEnv.out.append("""        assertArrayEquals(new byte[] {(byte)0x66,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x81},Conversion.boolsToBytes(src,1*32-1,dst,0,1));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testBoolsToHexs() throws Exception{
""");scalaGppEnv.out.append("""        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
""");scalaGppEnv.out.append("""        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""        assertEquals("76543210FEDCBA98B572935C83C8A46E",Conversion.boolsToHexs(src,0*32,"00000000000000000000000000000000",0*32,4*32));
""");scalaGppEnv.out.append("""        assertEquals("000000007",Conversion.boolsToHexs(src, 0,"000000000",32, 4));
""");scalaGppEnv.out.append("""        assertEquals("000000006",Conversion.boolsToHexs(src, 4,"000000000",32, 4));
""");scalaGppEnv.out.append("""        assertEquals("000000003",Conversion.boolsToHexs(src, 1,"000000000",32, 4));
""");scalaGppEnv.out.append("""        assertEquals("000000006",Conversion.boolsToHexs(src, 1,"000000000",33, 3));
""");scalaGppEnv.out.append("""        assertEquals("00000000C",Conversion.boolsToHexs(src, 1,"000000000",34, 2));
""");scalaGppEnv.out.append("""        assertEquals("000000008",Conversion.boolsToHexs(src, 1,"000000000",35, 1));
""");scalaGppEnv.out.append("""        assertEquals("00000000B",Conversion.boolsToHexs(src, 1,"000000008",32, 3));
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
"""); /*public void testIntsToBytes() throws Exception{
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
    }  */    

scalaGppEnv.out.append("""    boolean hasMethod(String methodName){
""");scalaGppEnv.out.append("""        Method[] methods = this.getClass().getMethods();
""");scalaGppEnv.out.append("""        for(Method m:methods){
""");scalaGppEnv.out.append("""            if(m.getName().equals(methodName)) return true;
""");scalaGppEnv.out.append("""        }
""");scalaGppEnv.out.append("""        return false;
""");scalaGppEnv.out.append("""    }
""");scalaGppEnv.out.append("""    public void testTestsToCheckImplemented() throws Exception {
""");for(t <- testsToCheck){

scalaGppEnv.out.append("""        if(!hasMethod("""");scalaGppEnv.out.append(scalaGppEnv.toString({t}));scalaGppEnv.out.append("""")) fail("test """);scalaGppEnv.out.append(scalaGppEnv.toString({t}));scalaGppEnv.out.append(""" not implemented");
""");}

scalaGppEnv.out.append("""}""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""
""");scalaGppEnv.out.append("""}
""");"test/uk/co/nimp/util/ConversionTest.java"},0)