/*
 *  Copyright (C) 2013 Machinery For Change, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */ 

package com.mchange.sc.v1.nnnock;

object Check {
  def main( argv : Array[String] ) : Unit = {
    println("A quick check of some examples from http://www.urbit.org/2013/08/22/Chapter-2-nock.html");
    println();
    val exampleTree = Cell(Cell(4,5),Cell(6,14,15));
    println("/[1 [[4 5] [6 14 15]]] ==> [[4 5] [6 14 15]]");
    println(s"check: ${ /( Cell(1, exampleTree) ) }");
    println("/[2 [[4 5] [6 14 15]]] ==> [4 5]");
    println(s"check: ${ /( Cell(2, exampleTree) ) }");
    println("/[3 [[4 5] [6 14 15]]] ==> [6 14 15]");
    println(s"check: ${ /( Cell(3, exampleTree) ) }");
    println("/[7 [[4 5] [6 14 15]]] ==> [14 15]");
    println(s"check: ${ /( Cell(7, exampleTree) ) }");
    println();
    println("*[[[4 5] [6 14 15]] [0 7]] ==> [14 15])");
    println(s"check: ${ *( Cell(Cell(Cell(4,5),Cell(6,14,15)),Cell(0,7)) ) }");
    println("*[42 [1 153 218]] ==> [153 218]");
    println(s"check: ${ *( Cell(42,Cell(1,153,218)) ) }");
    println("*[77 [2 [1 42] [1 1 153 218]]] ==> [153 218]");
    println(s"check: ${ *( Cell(77,Cell(2,Cell(1,42),Cell(1,1,153,218))) ) }");
    println();
    println("*[57 [0 1]] ==> 57");
    println(s"check: ${ *( Cell(57,Cell(0,1)) ) }");
    println("*[[132 19] [0 3]] ==> 19");
    println(s"check: ${ *( Cell(Cell(132,19),Cell(0,3)) ) }");
    println("*[57 [4 0 1]] ==> 58");
    println(s"check: ${ *( Cell(57,Cell(4,0,1)) ) }");
    println("*[[132 19] [4 0 3]] ==> 20");
    println(s"check: ${ *( Cell(Cell(132,19),Cell(4,0,3)) ) }");
    println();
    println("*[42 [4 0 1]] ==> 43");
    println(s"check: ${ *( Cell(42,Cell(4,0,1)) ) }");
    println("*[42 [3 0 1]] ==> 1");
    println(s"check: ${ *( Cell(42,Cell(3,0,1)) ) }");
    println("*[42 [[4 0 1] [3 0 1]]] ==> [43 1]");
    println(s"check: ${ *( Cell(42,Cell(Cell(4,0,1),Cell(3,0,1))) ) }");
    println();
    println("*[[132 19] [10 37 [4 0 3]]] ==> 20");
    println(s"check: ${ *( Cell(Cell(132,19),Cell(10,37,Cell(4,0,3))) ) }");
    println("*[42 [7 [4 0 1] [4 0 1]]] ==> 44");
    println(s"check: ${ *( Cell(42,Cell(7,Cell(4,0,1),Cell(4,0,1))) ) }");
    println("*[42 [8 [4 0 1] [0 1]]] ==> [43 42]");
    println(s"check: ${ *( Cell(42,Cell(8,Cell(4,0,1),Cell(0,1))) ) }");
    println("*[42 [8 [4 0 1] [4 0 3]]] ==> 44");
    println(s"check: ${ *( Cell(42,Cell(8,Cell(4,0,1),Cell(4,0,3))) ) }");
    println();
    println("*[42 [6 [1 0] [4 0 1] [1 233]]] ==> 43");
    println(s"check: ${ *( Cell(42,Cell(6,Cell(1,0),Cell(4,0,1),Cell(1,233))) ) }");
    println("*[42 [6 [1 1] [4 0 1] [1 233]]] ==> 233");
    println(s"check: ${ *( Cell(42,Cell(6,Cell(1,1),Cell(4,0,1),Cell(1,233))) ) }");
    println();
    println("*[42 [8 [1 0] 8 [1 6 [5 [0 7] 4 0 6] [0 6] 9 2 [0 2] [4 0 6] 0 7] 9 2 0 1]] ==> 41");
    println(s"check: ${ *( Cell(42,Cell(8,Cell(1,0),8,Cell(1,6,Cell(5,Cell(0,7),4,0,6),Cell(0,6),9,2,Cell(0,2),Cell(4,0,6),0,7),9,2,0,1)) ) }");
  }
}
