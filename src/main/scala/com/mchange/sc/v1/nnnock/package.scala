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

package com.mchange.sc.v1;

import scala.language.implicitConversions;
import scala.annotation.tailrec;

/**
 *  An implementation of Nock
 *  See http://www.urbit.org/2013/08/22/Chapter-2-nock.html
 */

package object nnnock {

   // A noun is an atom or a cell.  An atom is any natural number.  
   // A cell is any ordered pair of nouns.

  sealed trait Noun;
  case class Atom( value : Int ) extends Noun;
  case class Cell( head : Noun, tail : Noun ) extends Noun;
  implicit def toAtom( value : Int ) : Atom = Atom( value );
  implicit def toInt( atom : Atom ) : Int = atom.value;

  // 1  ::    nock(a)           *a
  def nock( a : Noun ) : Noun = *(a)

  // 2  ::    [a b c]           [a [b c]]
  object Cell {
    private def apply( nl : List[Noun] ) : Cell = {
      nl match {
	case a :: b :: Nil => Cell(a, b);
	case a :: b :: c :: Nil => Cell(a, Cell(b, c));
	case a :: b :: c :: tail => Cell(a, this.apply( b :: c :: tail ) ); 
      }
    }
    def apply(a : Noun, b : Noun, tail : Noun*) : Cell = apply( a :: b :: tail.toList );
  }

  // 3  ::

  // 4  ::    ?[a b]            0
  // 5  ::    ?a                1  
  def ?( noun : Noun ) : Noun = noun match {
    case _ : Cell => 0;
    case _ : Atom => 1;
  }

  // 6  ::    +[a b]            +[a b]
  // 7  ::    +a                1 + a
  // annoyingly, can't just use symbol + here, compiler tries to interpret directly as unary_+
  @tailrec def plus( noun : Noun ) : Noun = noun match {
    case a : Atom => 1 + a;
    case c : Cell => plus( c ); //intentional endless spin
  }

  // 8  ::    =[a a]            0
  // 9  ::    =[a b]            1
  // 10 ::    =a                =a	
  // annoyingly, can't overload equals sign
  def heq( noun : Noun ) : Atom = noun match {
    case Cell( a : Atom, b : Atom ) => if ( a == b ) 0 else 1;
    case Cell( a : Cell, b : Atom ) => 1;
    case Cell( a : Atom, b : Cell ) => 1;
    case Cell( a : Cell, b : Cell ) => if ((heq( Cell( a.head, b.head ) ) | heq( Cell( a.tail, b.tail ) )) == 0) 0 else 1;
    case a : Atom => heq( a ); //intentional endless spin
  }

  // 11 ::

  // 12 ::    /[1 a]            a
  // 13 ::    /[2 a b]          a
  // 14 ::    /[3 a b]          b
  // 15 ::    /[(a + a) b]      /[2 /[a b]]
  // 16 ::    /[(a + a + 1) b]  /[3 /[a b]]
  // 17 ::    /a                /a
  def /( noun : Noun ) : Noun = noun match {
    case Cell(Atom(1), a) => a;
    case Cell(Atom(2), Cell(a, b)) => a;
    case Cell(Atom(3), Cell(a, b)) => b;
    case Cell(Atom(value), b ) => {
      val a = value / 2;
      val num = if ( value % a == 0 ) 2 else 3;
      /(Cell(num, /(Cell(a, b))));
    }
    case a => /( a ); //intentional endless spin
  } 

  // 18 ::

  // 19 ::    *[a [b c] d]      [*[a b c] *[a d]]
  // 20 ::
  // 21 ::    *[a 0 b]          /[b a]
  // 22 ::    *[a 1 b]          b
  // 23 ::    *[a 2 b c]        *[*[a b] *[a c]]
  // 24 ::    *[a 3 b]          ?*[a b]
  // 25 ::    *[a 4 b]          +*[a b]
  // 26 ::    *[a 5 b]          =*[a b]
  // 27 ::
  // 28 ::    *[a 6 b c d]      *[a 2 [0 1] 2 [1 c d] [1 0] 2 [1 2 3] [1 0] 4 4 b]
  // 29 ::    *[a 7 b c]        *[a 2 b 1 c]
  // 30 ::    *[a 8 b c]        *[a 7 [[7 [0 1] b] 0 1] c]
  // 31 ::    *[a 9 b c]        *[a 7 c 2 [0 1] 0 b]
  // 32 ::    *[a 10 [b c] d]   *[a 8 c 7 [0 3] d]
  // 33 ::    *[a 10 b c]       *[a c]
  // 34 ::
  // 35 ::    *a                *a

  def *( noun : Noun ) : Noun = noun match {
    case Cell( a, Cell(Cell(b, c), d) ) => Cell( *(Cell(a,b,c)), *(Cell(a,d)) );
    case Cell( a, Cell(Atom(value), tail) ) => {
      (value, tail) match {
	case (0, b) => /( Cell(b, a) );
	case (1, b) => b;
	case (2, Cell(b, c)) => *( Cell( *( Cell(a,b) ), *( Cell(a,c) ) ) );
	case (3, b) => ?( *( Cell(a,b) ) );
	case (4, b) => plus( *( Cell(a,b) ) );
	case (5, b) => heq( *( Cell(a,b) ) );
	case (6, Cell(b, Cell(c, d))) => *( Cell(a,2,Cell(0,1),2,Cell(1,c,d),Cell(1,0),2,Cell(1,2,3),Cell(1,0),4,4,b) ); //wtf?
	case (7, Cell(b, c)) => *( Cell(a,2,b,1,c) );
	case (8, Cell(b, c)) => *( Cell(a,7,Cell(Cell(7,Cell(0,1),b),0,1),c) ); //wtf2
	case (9, Cell(b, c)) => *( Cell(a,7,c,2,Cell(0,1),0,b) );
	case (10, Cell(Cell(b,c),d)) => *( Cell(a,8,c,7,Cell(0,3),d) );
	case (10, Cell(b, c)) => *( Cell(a,c) );
	case _ => *( noun ); //intentional endless spin
      }
    }
    case a => *( a ); //intentional endless spin
  }

}
